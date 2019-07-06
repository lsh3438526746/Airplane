package com.isoft.demo.airplane;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoginActivity extends AppCompatActivity {
    //必须要在这里声明这些东西，必须和布局里面的一致
    EditText uname;//用户名
    EditText passwd;//用户密码
    CheckBox rememberpwd;//记住密码复选框
    CheckBox showpwd;//显示密码复选框
    Button login;//登录按钮
    Button register;//注册按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActionBar();
        init();
    }

    public void init() {
        //初始化定义控件变量，为了后面的使用
        uname = (EditText) findViewById(R.id.uname);
        passwd = (EditText) findViewById(R.id.passwd);
        rememberpwd = (CheckBox) findViewById(R.id.rememberpwd);
        showpwd = (CheckBox) findViewById(R.id.showpwd);
        //设置记住密码之后，取出存储过的信息，显示在登录界面
        /**********************************/
        SharedPreferences loginInformation = getSharedPreferences("loginInformation", MODE_PRIVATE);
        String temp_uname = loginInformation.getString("uname", "");//从文件里面读出相应信息，存入相关变量
        String temp_passwd = loginInformation.getString("passwd", "");//包括两个参数，第一个：是将信息存入的时候命的名字
        // 第二个是设置默认值，如果读出数据则应用读出的数据，否则变量的值为默认值
        int temp_status = loginInformation.getInt("status", 0);
        uname.setText(temp_uname);
        passwd.setText(temp_passwd);
        if (temp_status == 1)
            rememberpwd.setChecked(true);
        else
            rememberpwd.setChecked(false);
        /***********************************/
        showpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//isChecked  勾选状态
                if (isChecked) {//如果勾选
                    passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                } else {
                    passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
                }
            }
        });
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);//括号里面的名字要和activity_login里面的控件命名一致
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_uname = LoginActivity.this.uname.getText().toString();
                String str_passwd = passwd.getText().toString();
                if (str_uname.length() == 0) {
                    Toast.makeText(LoginActivity.this, "用户不能为空！", Toast.LENGTH_LONG).show();
                    //给出一定时间长的时间提示，提示文本为登录，第一个参数为自己的类.this
                    return;
                }//end if
                if (str_passwd.length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }//end if
                if (str_passwd.length() < 6) {
                    Toast.makeText(LoginActivity.this, "密码长度不能少于六位！", Toast.LENGTH_LONG).show();
                    return;
                }//end if
                //硬编码
                if (str_uname.equals("admin") && str_passwd.equals("123456")) {
                    //记住密码
                    /**************************************/
                    //将数据存入文件，类型都可以
                    SharedPreferences loginInformation = getSharedPreferences("loginInformation", MODE_PRIVATE);//得到共享的引用，1参数.文件名 2.参数 设置模式
                    SharedPreferences.Editor editor = loginInformation.edit();
                    if (rememberpwd.isChecked()) {//记住密码被勾选
                        editor.putString("uname", str_uname);
                        editor.putString("passwd", str_passwd);
                        editor.putInt("status", 1);//只是代表被勾选，1换成什么代表被勾选都可以
                    } else {//没被勾选
                        editor.clear();//清楚文件的数据
                    }
                    editor.commit();//必须提交，才能把值存储
                    /****************************************/
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(LoginActivity.this, "登录失败，请重试！", Toast.LENGTH_LONG).show();
            }//end onclick
        });
    }

    public void setActionBar() {//设置标题
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("航班查询系统");
        actionBar.setSubtitle("登录");
        actionBar.setIcon(R.mipmap.bg2);//设置logo
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(getDrawable(R.mipmap.bg_color_blue));
        actionBar.setIcon(getDrawable(R.mipmap.qq_logo));//显示logo图片
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu!=null){
            if(menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")){
                try {
                    Method method=menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu,true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getMenuInflater().inflate(R.menu.login_menu, menu);//创建菜单，此处新建立了文件夹menu，下面写了xml文件
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //定义一个提示框
        /*********************************/
        // EditText//定义文本框
        if (item.getItemId() == R.id.menu_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);//定义一个对话框
            builder.setTitle("提示");
            builder.setMessage("是否退出系统？");
            builder.setCancelable(true);//显示取消，即右上角有一个X
            builder.setIcon(R.mipmap.qq_logo);//加不加logo要灵活运用
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);//退出系统
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
            /*******************************************************/
        }
        if (item.getItemId() == R.id.menu_register) {
            try{
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,Class.forName("com.isoft.demo.airplane.RegisterActivity"));
                startActivity(intent);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        if (item.getItemId() == android.R.id.home) {//android.R.id.home为系统自带的
            Toast.makeText(this, "我是返回按钮", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}

