package com.isoft.demo.airplane;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class FlashActivity extends AppCompatActivity {
    Handler handler=new Handler();//必须实例化
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(FlashActivity.this,GuideActivity.class);//设置从哪往哪跳
                startActivity(intent);
                finish();
            }
        },7000);//3秒跳转
    }
}
