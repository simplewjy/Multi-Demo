package com.test.exampledemo.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.exampledemo.R;
import com.test.exampledemo.fragment.BlueFragment;
import com.test.exampledemo.fragment.RedFragment;

public class ForthActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);
        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        Button btnBlue = findViewById(R.id.btn_blue);
        Button btnRed = findViewById(R.id.btn_red);
        //获取fragment的实例
        final Fragment blueFragment = BlueFragment.newInstance();
        final Fragment redFragment = RedFragment.newInstance();
        //默认添加红色的fragment
        fragmentManager.beginTransaction().add(R.id.container, redFragment).commit();
        //初始化监听器
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, blueFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, redFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }
}