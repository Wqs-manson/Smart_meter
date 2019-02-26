package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

import static com.example.admin.myapplication.MainActivity.activityList;

public class RegActivity extends AppCompatActivity {
    private static EditText edt_reg_user,edt_reg_password,edt_reg_password1;
    private static Button btn_reg;
    private static TextView tex_reg;
    public static int flag;
    String user,pass,pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //相应控件
        setContentView(R.layout.activity_reg);
        activityList.add(this);
        initView();

    }
    private void initView() {
        edt_reg_user = (EditText) findViewById(R.id.Edt_reg_user);
        edt_reg_password = (EditText) findViewById(R.id.Edt_reg_psw);
        edt_reg_password1 = (EditText) findViewById(R.id.Edt_reg_psw1);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        //注册
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = edt_reg_user.getText().toString().trim();
                pass = edt_reg_password.getText().toString().trim();
                pass1 = edt_reg_password1.getText().toString().trim();
                final String sql="insert into User values('"+user+"','"+pass+"')";
                final String reg="select * from user where user='"+user+"' ";//匹配
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();//创建新的消息队列
                        Connection conn= DBA.connection();
                        if (DBA.select(conn,reg)) {
                            flag=1;
                            Toast.makeText(RegActivity.this, "用户名不唯一，请重新输入", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegActivity.this,RegActivity.class));
                            flag=0;
                        }
                        else{
                            flag=0;
                        }
                        Looper.loop();//开始
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(flag==0){
                            Looper.prepare();//创建新的消息队列
                            Connection conn= DBA.connection();
                            if (DBA.excel(conn,sql)) {
                                Intent intent = new Intent(RegActivity.this, MainActivity.class);
                                Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            Looper.loop();//开始
                        }
                    }
                }).start();
                if(user.equals("")||pass.equals("")||pass1.equals(""))
                {
                    Toast.makeText(RegActivity.this,"用户名,密码不能为空",Toast.LENGTH_SHORT).show();
                    flag=2;
                    return;
                }
                if(!pass.equals(pass1))
                {
                    Toast.makeText(RegActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    flag=2;
                    return;
                }

            }
        });

    }

}
