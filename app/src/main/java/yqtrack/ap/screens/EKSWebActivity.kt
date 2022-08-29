package yqtrack.ap.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import yqtrack.ap.R
import yqtrack.ap.file.FileManager
import yqtrack.ap.utils.const.Link
import javax.inject.Inject

@AndroidEntryPoint
class EKSWebActivity : AppCompatActivity() {
    @Inject
    lateinit var fileManager: FileManager
    private lateinit var web: WebView
    private lateinit var chooserCallback: ValueCallback<Array<Uri?>>

    val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        chooserCallback.onReceiveValue(it.toTypedArray())
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_eks)

        window.statusBarColor = getColor(R.color.black)
        web = findViewById(R.id.webViewEKS)

        web.loadUrl(intent.getStringExtra(LINK_EXTRA)!!)

        with(web.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = false
            userAgentString = System.getProperty(STRING_AGENT)
        }

        with(CookieManager.getInstance()) {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(web, true)
        }

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (web.canGoBack()) {
                        web.goBack()
                    }
                }
            })

        web.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Log.e("TAG", error.description.toString())
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
                if (url == Link.DOMAIN) {
                    startActivity(
                        Intent(this@EKSWebActivity, EKSGame::class.java)
                    )
                    finish()
                } else {
                    fileManager.putFileData(url)
                }
            }
        }

        web.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri?>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                chooserCallback = filePathCallback
                getContent.launch(IMAGE_MIME_TYPE)
                return true
            }

            @SuppressLint("SetJavaScriptEnabled")
            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(this@EKSWebActivity)
                newWebView.settings.javaScriptEnabled = true
                newWebView.webChromeClient = this
                newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                newWebView.settings.domStorageEnabled = true
                newWebView.settings.setSupportMultipleWindows(true)
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }

    companion object {
        private const val IMAGE_MIME_TYPE = "image/*"
        private const val STRING_AGENT = "http.agent"
        const val LINK_EXTRA = "link"
    }
}