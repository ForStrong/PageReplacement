package com.zhangsiyuan.pagereplacement;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    //标记视图 即点击xy轴交点时弹出展示信息的View 需自定义
    private List<IncomeBean> mIncomeBeans = new ArrayList<>();
    private List<Float> fifoDatas = new ArrayList<>();
    private List<Float> optDatas = new ArrayList<>();
    private List<Float> lruDatas = new ArrayList<>();
    private List<ILineDataSet> mLineDataSets = new ArrayList<>();
    private TextInputEditText workEt, randomEt, rangeEt;
    private Button mGetDataBt;
    private String mRandomS;
    private String mWorkS;
    private String mRangeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mGetDataBt.setOnClickListener(view ->{
            mRandomS = randomEt.getText().toString();
            mWorkS = workEt.getText().toString();
            mRangeS = rangeEt.getText().toString();
            if (!(TextUtils.isEmpty(mRandomS)||TextUtils.isEmpty(mWorkS)
                    ||TextUtils.isEmpty(mRangeS)))
                initLine();
            else
                Toast.makeText(this, "请填入完整数据", Toast.LENGTH_SHORT).show();

        });

        initChart(lineChart);
    }

    private void initView() {
        lineChart = findViewById(R.id.line_chart);
        workEt = findViewById(R.id.et_work_number);
        randomEt = findViewById(R.id.et_random_number);
        rangeEt = findViewById(R.id.et_range);
        mGetDataBt = findViewById(R.id.getData_bt);
    }

    private void initLine() {
        mIncomeBeans.clear();
        fifoDatas.clear();
        optDatas.clear();
        lruDatas.clear();
        mLineDataSets.clear();
        for (int i = 0; i < 20; i++) {
            IncomeBean incomeBean = getIncomeBean();
            mIncomeBeans.add(incomeBean);
            fifoDatas.add(incomeBean.getFifoValue());
            optDatas.add(incomeBean.getOptValue());
            lruDatas.add(incomeBean.getLruValue());
        }
        //为mLineDataSets添加每一条折线的lineDataSet
        LineDataSet lineDataSet = initDataSet(fifoDatas, "fifo");
        initLineDataSet(lineDataSet, Color.CYAN,null);
        mLineDataSets.add(lineDataSet);
        lineDataSet = initDataSet(optDatas,"opt");
        initLineDataSet(lineDataSet,getResources().getColor(R.color.opt),null);
        mLineDataSets.add(lineDataSet);
        lineDataSet = initDataSet(lruDatas,"lru");
        initLineDataSet(lineDataSet,getResources().getColor(R.color.lru),null);
        mLineDataSets.add(lineDataSet);
        //初始化lineData
        LineData lineData = new LineData(mLineDataSets);
        lineChart.setData(lineData);
        //设置XY轴动画效果
        lineChart.animateY(2500);
        lineChart.animateX(1500);
        setMarkerView();
    }

    private IncomeBean getIncomeBean() {
        List<Integer> list = initData();
        PagerReplaceUtils utils = new PagerReplaceUtils(list,Integer.parseInt(mWorkS));
        float fifo = utils.FIFO();
        float lru = utils.LRU();
        float opt = utils.OPT();
        String s = list.toString() + "\n" +
                "作业框数: " + utils.getN() + "\n" +
                "FIFO 缺页率: " + fifo + "\n" +
                "LRU 缺页率: " + lru + "\n" +
                "OPT缺页率: " + opt;
        return new IncomeBean(fifo, opt, lru, s);
    }


    private List<Integer> initData() {
        // TODO Auto-generated method stub
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < Integer.parseInt(mRandomS); i++) {
            int a = random.nextInt(Integer.parseInt(mRangeS)) + 1;
            list.add(a);
        }
        return list;
    }

    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(true);
        //是否可以拖动
        lineChart.setDragEnabled(true);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置XY轴动画效果
        lineChart.animateY(2500);
        lineChart.animateX(1500);

        lineChart.setBackgroundColor(Color.WHITE);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        /***XY轴的设置***/
        XAxis xAxis = lineChart.getXAxis();

        YAxis leftYAxis = lineChart.getAxisLeft();

        YAxis rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);

        xAxis.setDrawGridLines(false);
        rightYaxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(false);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        //        去掉右侧Y轴
        rightYaxis.setEnabled(false);

        /***折线图例 标签 设置***/

        Legend legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }




    @NonNull
    private LineDataSet initDataSet(List<Float> dataList, String name) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Float data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, data);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        return new LineDataSet(entries, name);
    }


    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode 线的模式是折线还是曲线
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
        lineDataSet.setDrawValues(false);
    }

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView() {
        LineChartMarkView mv = new LineChartMarkView(this, mIncomeBeans);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mian, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calculate:
                Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }
}
