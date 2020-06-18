package com.test.exampledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.exampledemo.service.MyService;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener(){
        Button btnOpen=findViewById(R.id.btn_open);
        Button btnClose=findViewById(R.id.btn_close);
        Button btnJump2=findViewById(R.id.btn_jump2);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SecondActivity.this, MyService.class);
                intent.putExtra("test","111");
                startService(intent);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(SecondActivity.this,MyService.class));
            }
        });
        btnJump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
            }
        });

    }
}