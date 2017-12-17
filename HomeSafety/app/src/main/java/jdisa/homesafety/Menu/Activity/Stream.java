package jdisa.homesafety.Menu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import jdisa.homesafety.R;

public class Stream extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        WebView webView = (WebView)findViewById(R.id.web);
        // Set page content for webview
        //webView.loadData("<html><head><meta name='viewport' content='target-densitydpi=device-dpi,initial-scale=1,minimum-scale=1,user-scalable=yes'/></head><body><center><img src=\"http://10.0.0.160:5000/\" alt=\"Stream\" align=\"middle\"></center></body></html>", "text/html", null);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("http://10.0.0.160:5000/");
        webView.getSettings().setBuiltInZoomControls(true);
    }
}
