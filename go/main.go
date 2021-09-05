package main

import "C"

// other imports should be seperate from the special Cgo import
import (
	"bytes"
	"embed"
	"fmt"
	"html/template"
	"image/jpeg"
	"io"
	"log"
	"mime/multipart"
	"net/http"
	"net/textproto"

	"github.com/pion/mediadevices"
	"github.com/pion/mediadevices/pkg/prop"

	//	"myapp/mjpeg"
	//	"gocv.io/x/gocv"

	// Note: If you don't have a camera or microphone or your adapters are not supported,
	//       you can always swap your adapters with our dummy adapters below.
	// _ "github.com/pion/mediadevices/pkg/driver/videotest"
	_ "github.com/pion/mediadevices/pkg/driver/camera" // This is required to register camera adapter
)

func must(err error) {
	if err != nil {
		panic(err)
	}
}

// Embed the file content as string.
//go:embed title.txt
var title string

// Embed the entire directory.
//go:embed templates
var indexHTML embed.FS

//go:embed static
var staticFiles embed.FS

//export server
func server() {
	//root, e := ioutil.TempDir(os.TempDir(), "api-")
	//if e != nil {
	//
	//	}
	c := make(chan bool)

	// http.FS can be used to create a http Filesystem
	var staticFS = http.FS(staticFiles)
	fs := http.FileServer(staticFS)

	// Serve static files
	http.Handle("/static/", fs)

	//fsAndroid := http.FileServer(http.Dir("/storage/emulated/0/Android/media/tk.cocoon/"))
	fsAndroid := http.FileServer(http.Dir("/storage/emulated/0/")) // Pictures/tk.cocoon/
	//fsAndroid := http.FileServer(http.Dir("/external/images/media/"))
	http.Handle("/android/", http.StripPrefix("/android/", fsAndroid))

	go func() {
		log.Println(http.ListenAndServe("127.0.0.1:6060", nil))
		<-c
	}()

	http.HandleFunc("/", handler)
	http.HandleFunc("/stream", streamhandler)
	http.HandleFunc("/Sayhi", HelloHandler)

}

func HelloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, there from go\n")
}

func handler(w http.ResponseWriter, r *http.Request) {
	//	http.FileServer(http.Dir("./static/")).ServeHTTP(w, r)
	var path = r.URL.Path
	// Note the call to ParseFS instead of Parse
	t, err := template.ParseFS(indexHTML, "templates/index.html")
	if err != nil {
		log.Fatal(err)
	}
	w.Header().Add("Content-Type", "text/html")

	// respond with the output of template execution
	t.Execute(w, struct {
		Title    string
		Response string
	}{Title: title, Response: path})

}

func streamhandler(w http.ResponseWriter, r *http.Request) {
	mediaStream, err := mediadevices.GetUserMedia(mediadevices.MediaStreamConstraints{
		Video: func(constraint *mediadevices.MediaTrackConstraints) {
			constraint.Width = prop.Int(600)
			constraint.Height = prop.Int(400)
		},
	})
	must(err)

	track := mediaStream.GetVideoTracks()[0]
	videoTrack := track.(*mediadevices.VideoTrack)
	defer videoTrack.Close()

	//	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
	var buf bytes.Buffer
	videoReader := videoTrack.NewReader(false)
	mimeWriter := multipart.NewWriter(w)

	contentType := fmt.Sprintf("multipart/x-mixed-replace;boundary=%s", mimeWriter.Boundary())
	w.Header().Add("Content-Type", contentType)

	partHeader := make(textproto.MIMEHeader)
	partHeader.Add("Content-Type", "image/jpeg")

	for {
		frame, release, err := videoReader.Read()
		if err == io.EOF {
			return
		}
		must(err)

		err = jpeg.Encode(&buf, frame, nil)
		// Since we're done with img, we need to release img so that that the original owner can reuse
		// this memory.
		release()
		must(err)

		partWriter, err := mimeWriter.CreatePart(partHeader)
		must(err)

		_, err = partWriter.Write(buf.Bytes())
		buf.Reset()
		must(err)
	}
	//	})
}

func main() {}
