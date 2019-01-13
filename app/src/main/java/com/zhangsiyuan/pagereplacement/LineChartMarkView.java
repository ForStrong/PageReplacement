package com.zhangsiyuan.pagereplacement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.List;

public class LineChartMarkView extends MarkerView {

    private TextView tvData;
    private List<IncomeBean> mIncomeBeans;
    public LineChartMarkView(Context context,List<IncomeBean> incomeBeans) {
        super(context, R.layout.markview_layout);
        this.mIncomeBeans = incomeBeans;
        tvData = findViewById(R.id.tv_data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        //展示自定义X轴值 后的X轴内容
        float x = e.getX();
        IncomeBean incomeBean = mIncomeBeans.get((int) x);
        tvData.setText(incomeBean.getDataS());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}

