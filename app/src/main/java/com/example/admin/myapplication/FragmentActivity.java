package com.example.admin.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    private static Button btn_Show_start,btn_Show_end;
    private static RadioButton main_tab,wave_tab,history_tab;
    private RadioGroup radioGroup; private FrameLayout frameLayout;
    private Fragment_show fragment_1;    private Fragment_chart fragment_2;    private Fragment_history fragment_3;
    private List<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        MainActivity.activityList.add(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //不同按钮对应着不同的Fragment对象页面
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //找到按钮
        main_tab=(RadioButton)findViewById(R.id.main_tab);
        wave_tab=(RadioButton)findViewById(R.id.wave_tab);
        history_tab=(RadioButton)findViewById(R.id.history_tab);
        //创建Fragment对象及集合
        fragment_1 = new Fragment_show();
        fragment_2 = new Fragment_chart();
        fragment_3 = new Fragment_history();
        //将Fragment对象添加到list中
        list = new ArrayList<>();
        list.add(fragment_1);
        list.add(fragment_2);
        list.add(fragment_3);
        main_tab.setOnClickListener((View.OnClickListener) this);
        wave_tab.setOnClickListener((View.OnClickListener) this);
        history_tab.setOnClickListener((View.OnClickListener) this);
        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.main_tab);
        //初始时向容器中添加第一个Fragment对象
        fragmentTransaction.replace(R.id.framelayout,fragment_1);
        fragmentTransaction.commit();
    }
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    public void onClick(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.main_tab:
                fragmentTransaction.replace(R.id.framelayout,fragment_1);
                fragmentTransaction.commit();
                break;
            case R.id.wave_tab:
                fragmentTransaction.replace(R.id.framelayout,fragment_2);
                fragmentTransaction.commit();
                break;
            case R.id.history_tab:
                fragmentTransaction.replace(R.id.framelayout,fragment_3);
                fragmentTransaction.commit();
                break;
            default:
                finish();
                break;
        }
    }
}
