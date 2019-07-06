package com.isoft.demo.airplane;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

public class RegisterActivity extends AppCompatActivity {
    //必须要在这里声明这些东西，必须和布局里面的一致
    EditText uname;//用户名
    EditText passwd;//用户密码
    EditText repasswd;
    Button btn_exit;//退出按钮
    Button btn_register;//注册按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        //初始化定义控件变量，为了后面的使用
        uname = (EditText) findViewById(R.id.uname);
        passwd = (EditText) findViewById(R.id.passwd);
        repasswd = (EditText) findViewById(R.id.repasswd);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_uname = RegisterActivity.this.uname.getText().toString();
                String str_passwd = passwd.getText().toString();
                String str_repasswd = repasswd.getText().toString();
                if (str_uname.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "用户不能为空！", Toast.LENGTH_SHORT).show();
                    //给出一定时间长的时间提示，提示文本为登录，第一个参数为自己的类.this
                    Toast.makeText(RegisterActivity.this, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_passwd.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_passwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "密码长度不能少于六位！", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!str_passwd.equals(str_repasswd)) {
                    Toast.makeText(RegisterActivity.this, "密码输入不一致！", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                try{
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this,Class.forName("com.isoft.demo.airplane.LoginActivity"));
                    startActivity(intent);
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
            }//end onclick
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, Class.forName("com.isoft.demo.airplane.LoginActivity"));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}