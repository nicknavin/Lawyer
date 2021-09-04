package com.app.amanrow.activity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.app.amanrow.R;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Context context;
    Button btn_addProduct, btn_stackAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        initView();
    }

    private void initView()
    {

        btn_addProduct = (Button) findViewById(R.id.btn_addProduct);
        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_stackAvailable = (Button) findViewById(R.id.btn_stackAvailable);
        btn_stackAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }


}
