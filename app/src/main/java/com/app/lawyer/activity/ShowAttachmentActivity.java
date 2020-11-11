package com.app.lawyer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.app.lawyer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

public class ShowAttachmentActivity extends AppCompatActivity {

    String url;
    ImageView imgAttachment;
    Context context;
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attachment);
        context=this;
        initView();
    }

    private void initView()
    {
        webview=(WebView)findViewById(R.id.webview);
        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgAttachment=(ImageView)findViewById(R.id.imgAttachment);

        if(getIntent().getStringExtra("URL")!=null) {
            url = getIntent().getStringExtra("URL");
            System.out.println("url: " + url);

            if (url.toLowerCase().contains(".jpg") || url.toLowerCase().contains(".png")) {
                imgAttachment.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);
                Picasso.with(context)
                        .load(url)
                        .error(R.mipmap.placeholder)
                        .placeholder(R.mipmap.placeholder)
                        .into(imgAttachment);
            } else {
                imgAttachment.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                webview.getSettings().setJavaScriptEnabled(true);
                String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
                webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            }
        }



    }




}