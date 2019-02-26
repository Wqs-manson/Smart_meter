package com.example.admin.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.admin.myapplication.Head_layout.head_about;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.myapplication.MainActivity.activityList;
import static com.example.admin.myapplication.MainActivity.pref;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener , ViewPager.OnPageChangeListener{
    private static RadioButton main_tab,wave_tab,history_tab;
    private RadioGroup radioGroup; private FrameLayout frameLayout;
    private Fragment_show fragment_1;    private Fragment_chart fragment_2;    private Fragment_history fragment_3;
    private List<Fragment> list;
    private long mExitTime;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        activityList.add(this);
        //显示图片本身的颜色 item
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        //引入header和menu
       // navigationView.inflateHeaderView(R.layout.nav_header);
     //   navigationView.inflateMenu(R.menu.main);
        View headerView = navigationView.getHeaderView(0);//获取头布局
        //跳转
        View head_user = headerView.findViewById(R.id.head_user);
        head_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.edit().putBoolean("fir", false).commit();
                Intent intent = new Intent(FragmentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //获取Item的事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()) {
                    case R.id.about:
                        Intent intent = new Intent(FragmentActivity.this, head_about.class);
                        startActivity(intent);
                        break;
                    case R.id.quit:

                        AlertDialog.Builder builder = new AlertDialog.Builder(FragmentActivity.this);
                        // 设置参数
                        builder.setMessage("确定要注销此账号吗？")
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                for(Activity act:activityList)
                                {
                                    pref.edit().putBoolean("fir", true).commit();
                                    act.finish();
                                }
                                System.exit(0);
                            }
                        });
                        builder.create().show();
                        break;
                }
                return true;
            }
        });
        viewPager = (ViewPager) findViewById(R.id.framelayout);
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
        //给viewPager设置适配器，以显示内容
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.main_tab);
        //设置Viewpager第一次显示的页面
        viewPager.setCurrentItem(0,true);
        main_tab.setOnClickListener( this);
        wave_tab.setOnClickListener(this);
        history_tab.setOnClickListener( this);
        //设置ViewPager页面监听
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //根据当前展示的ViewPager页面，使RadioGroup对应的按钮被选中
        switch (position) {
            case 0:
                radioGroup.check(R.id.main_tab);
                break;
            case 1:
                radioGroup.check(R.id.wave_tab);
                break;
            case 2:
                radioGroup.check(R.id.history_tab);
                break;
            default:
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //创建ViewPager盛放Fragment的适配器类
    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //返回每个position对应的Fragment对象
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        //返回list的长度，也就是Fragment对象的个数
        @Override
        public int getCount() {
            return list.size();
        }
    }
   //退出
    public void exit() {

      if ((System.currentTimeMillis() - mExitTime) > 2000) {
          Toast.makeText(FragmentActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
          mExitTime = System.currentTimeMillis();
      } else {
          for(Activity act:activityList)
          {
              act.finish();
          }
          System.exit(0);
      }
  }
  //底部按钮的事件
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.main_tab:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.wave_tab:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.history_tab:
                viewPager.setCurrentItem(2,true);
                break;
            default:
                break;
        }
    }
    //返回两次 提示
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
