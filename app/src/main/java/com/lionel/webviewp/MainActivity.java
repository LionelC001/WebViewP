package com.lionel.webviewp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lionel.webviewp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ViewDataBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        initWebView();
    }

    private void initWebView() {
        WebView webView = ((ActivityMainBinding) dataBinding).webView;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());https://www.youtube.com/watch?v=gYsXOKAfxTs
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "MyJavaScriptInterface");
        webView.loadUrl("https://www.google.com/");
    }



    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {

        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d("<>", "shouldOverrideUrlLoading");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d("<>", "shouldOverrideUrlLoading: " + request.getUrl().toString());
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("<>", "onPageStarted: " + url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("<>", "onPageFinished: " + url);
//            super.onPageFinished(view, url);
            view.loadUrl("javascript:MyJavaScriptInterface.showMeTheCode" +
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        }
    }


    private class MyJavaScriptInterface {

        @JavascriptInterface
        public void showMeTheCode(String code) {
            Log.d("<>", code);
        }
    }
}
