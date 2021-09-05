document
  .getElementById("cameraFileInput")
  .addEventListener("change", function () {
    document
      .getElementById("pictureFromCamera")
      .setAttribute("src", window.URL.createObjectURL(this.files[0]));
  });

  function myFunction()
  {
      alert("Hello! I am an alert box!");
  }

  //<!-- Sending value to Android -->
  function showAndroidToast(toast) {
      AndroidInterface.showToast(toast);
  }

  //<!-- Getting value from Android -->
  function showVersion(msg) {
      var myVar = AndroidInterface.getAndroidVersion();
      document.getElementById("version").innerHTML = msg + " You are running API Version " + myVar;
  }

    //<!-- Getting value from Android -->
    function showResponse() {
      //  var path = AndroidInterface.getResponseMessage();
        
      //  var filename = file.replace(/^.*[\\\/]/, '')
       var filename = AndroidInterface.getResponseMessage();
       // fullPath = "file:///storage/emulated/0/Android/media/tk.cocoon/"+filename
        path = "http://127.0.0.1:6060/android/"+filename
       // path = "file:///storage/emulated/0/Android/media/tk.cocoon/"+filename
        document.getElementById("version").innerHTML = path;
        document
        .getElementById("pictureFromCamera")
        .setAttribute("src", path); // window.URL.createObjectURL(myVar)
        // content://media/external/images/media/62811
        // /file%3A%2F%2F%2Fstorage%2Femulated%2F0%2FAndroid%2Fdata%2Ftk.cocoon%2Ffiles%2FPictures%2F
        // /file:///storage/emulated/0/Android/data/tk.cocoon/files/Pictures/

    }

    function readURL(input) {
      if (input.files && input.files[0]) {
          var reader = new FileReader();
          
          reader.onload = function (e) {
            filename = e.target.result
            path = "http://127.0.0.1:6060/android/"+filename
            document
            .getElementById("pictureFromCamera")
            .setAttribute("src", path);
          };

          reader.readAsDataURL(input.files[0]);
      }
  }