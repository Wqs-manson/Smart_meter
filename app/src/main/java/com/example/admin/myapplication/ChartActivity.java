package com.example.admin.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ChartActivity extends AppCompatActivity {
    private GraphicalView view;
    private int i = 0;
    private int TIME = 1000;
    public int obj2;
    //以下属性用于initLine(XYSeries series)方法中更新数据
    private double[] x, y;
    private int count;
    private int xTemp, yTemp;


    private LinearLayout chart;
    private View viewShow;
    private XYSeries xySeries, xySeries2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_chart);
        MainActivity.activityList.add(this);
     //   chart= (LinearLayout) findViewById(R.id.id_layout);
        x=new double[20];
        y=new double[20];
        view= ChartFactory.getLineChartView(this,getDataSet(),getRenderer());
        setContentView(view);
        refreshChart();
    }

    //动态赋值，
    private void initLine(XYSeries series) {

        Random r = new Random();
        xTemp = 0;
        yTemp = r.nextInt(10);

        count = series.getItemCount();//返回本系列的条目的数量,只有2条，一开始
        Log.e("ssssss", count + "");
        if (count > 20) {
            count = 20;
        }


        //这里是给第几个点赋值坐标，并不是多个赋值，仅仅是一个，如第九个，x[9]=series.getX(9);
        for (int i = 0; i < count; i++) {
            x[i] = series.getX(i);//这个地方是getX（i）来获取的是再次调用series对象时，取出的数据，存放在x[],y[]数组里
            //为下面赋值做铺垫。
            y[i] = series.getY(i);
            Log.e("x[i]:", "" + x[i]);
            Log.e("Y[i]:", "" + x[i]);
        }
        series.clear();

        series.add(xTemp, yTemp);

        for (int i = 0; i < count; i++) { //取出上一个的值，存放在下一个坐标上
            series.add(x[i] + 1, y[i]);
        }
    }

    private void refreshChart() {
        Timer timer = new Timer();
        timer.schedule(task, 1, 1000);//一秒执行一次
    }
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            initLine(xySeries);
            initLine(xySeries2);

            view.postInvalidate();
        }
    };
    public XYMultipleSeriesDataset getDataSet() {
        //创建数据源
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        //定义线，
        xySeries = new XYSeries("111");
        xySeries2 = new XYSeries("222");
        dataset.addSeries(xySeries);
        dataset.addSeries(xySeries2);
        return dataset;
    }
    public XYMultipleSeriesRenderer getRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setXTitle("时间");
        renderer.setYTitle("温度");
        renderer.setAxisTitleTextSize(30);//设置轴标题文本大小
        renderer.setChartTitle("测试");//设置图表标题
        renderer.setChartTitleTextSize(30);//设置图表标题文字的大小
        renderer.setLabelsTextSize(18);//设置标签的文字大小
        renderer.setLegendTextSize(30);//设置图例文本大小
        renderer.setPointSize(3f);//设置点的大小

        renderer.setYAxisMin(0);//设置y轴最小值是0
        renderer.setYAxisMax(15);//设置Y轴的最大值为15，   0--15

        renderer.setYLabels(10);//设置Y轴刻度个数（貌似不太准确）
        renderer.setXAxisMax(20);  //设置X轴的屏幕显示长度，
        renderer.setShowGrid(true);//显示网格
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);

        //线的属性
        XYSeriesRenderer r = new XYSeriesRenderer();//(类似于一条线对象)
        r.setColor(Color.BLUE);//设置颜色
        r.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        r.setFillPoints(true);//填充点（显示的点是空心还是实心）
        r.setDisplayChartValues(true);//将点的值显示出来
        r.setChartValuesSpacing(10);//显示的点的值与图的距离
        r.setChartValuesTextSize(25);//点的值的文字大小

        XYSeriesRenderer r2 = new XYSeriesRenderer();//(类似于一条线对象)
        r2.setColor(Color.RED);//设置颜色
        r2.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        r2.setFillPoints(true);//填充点（显示的点是空心还是实心）
        r2.setDisplayChartValues(true);//将点的值显示出来
        r2.setChartValuesSpacing(10);//显示的点的值与图的距离
        r2.setChartValuesTextSize(25);//点的值的文字大小

        renderer.addSeriesRenderer(r);
        renderer.addSeriesRenderer(r2);
        return renderer;
    }

}
