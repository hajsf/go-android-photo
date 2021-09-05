package tk.anative

interface WebSettings {
    companion object {
        fun setUp() {
            with(Global.webView) {
                addJavascriptInterface(
                    WebAppInterface(context),
                    "AndroidInterface"
                )
                settings.apply {
                    javaScriptEnabled = true
                    Global.serverJNI()
                    loadUrl("http://127.0.0.1:6060/")
                }
            }
        }
    }
}