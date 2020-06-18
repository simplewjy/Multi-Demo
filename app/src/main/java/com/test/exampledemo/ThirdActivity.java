package com.test.exampledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.exampledemo.service.ForthActivity;
import com.test.exampledemo.service.MyService;

public class ThirdActivity extends AppCompatActivity {

    private MyService myService;
    private TextView tvCount;
    //声明一个操作常量字符串
    public static final String ACTION_UPDATEUI = "action.updateUI";

    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //动态注册广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_UPDATEUI);
        myReceiver=new MyReceiver();
        registerReceiver(myReceiver,intentFilter);
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            tvCount.setText(intent.getExtras().getInt("count")+"");
        }
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        Button btnOpen = findViewById(R.id.btn_open1);
        Button btnClose = findViewById(R.id.btn_close1);
        tvCount = findViewById(R.id.tv_count);
        Button btnJump3=findViewById(R.id.btn_jump3);
        final ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyService.MyBinder binder = (MyService.MyBinder) iBinder;
                myService = binder.getService();
                tvCount.setText("count:" + myService.getCount());
                Log.i("tag", "onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i("tag", "onServiceDisconnected");
            }
        };
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, MyService.class);
                //BIND_AUTO_CREATE 自动创建Service
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
            }
        });
        btnJump3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThirdActivity.this, ForthActivity.class));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}