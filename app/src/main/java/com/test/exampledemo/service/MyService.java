package com.test.exampledemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.test.exampledemo.ThirdActivity.ACTION_UPDATEUI;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class MyService extends Service {
    private static ScheduledExecutorService mScheduledExecutorService;
    private static String TAG = MyService.class.getName();
    private static final long LOOP_TIME = 1;

    private int count = 0;

    private MyBinder myBinder=new MyBinder();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            count++;
            Intent intent=new Intent();
            intent.setAction(ACTION_UPDATEUI);
            intent.putExtra("count",count);
            sendBroadcast(intent);
            Log.i(TAG, "===count:" + count);
        }
    };

    public int getCount(){
        return count;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        mScheduledExecutorService = newScheduledThreadPool(2);
        mScheduledExecutorService.scheduleAtFixedRate(mRunnable, LOOP_TIME, LOOP_TIME, TimeUnit.SECONDS);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        mScheduledExecutorService.shutdown();
        mScheduledExecutorService = null;
        mRunnable = null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }
}
