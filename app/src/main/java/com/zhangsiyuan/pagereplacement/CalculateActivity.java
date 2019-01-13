package com.zhangsiyuan.pagereplacement;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CalculateActivity extends AppCompatActivity {
    private String mS;
    private TextInputEditText mLeafBox;
    private TextInputEditText mTask;
    private Button mResultBt;
    private TextView mResultEt;
    private RadioGroup mTypeRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        initView();

        initOnClick();

    }

    private void initOnClick() {
        mResultBt.setOnClickListener(view ->{
            mS = "";
            switch (mTypeRg.getCheckedRadioButtonId()) {
                case R.id.rb_fifo:
                    if (!(TextUtils.isEmpty(mTask.getText().toString())
                            ||TextUtils.isEmpty(mLeafBox.getText().toString()))) {
                        List<Integer> integers = new ArrayList<>();
                        String taskString = mTask.getText().toString();
                        String[] tasks = taskString.split(" ");
                        for (String s: tasks) {
                            integers.add(Integer.parseInt(s));
                        }
                        int leafBoxNumber = Integer.parseInt(mLeafBox.getText().toString());
                        PagerReplaceUtils utils = new PagerReplaceUtils(integers, leafBoxNumber);
                        float fifo = utils.FIFO();
                        mS = integers.toString() + "\n" +
                                "作业框数: " + utils.getN() + "\n" +
                                "FIFO 缺页率: " + fifo + "\n"+
                                "最后作业框数: "+ utils.map.toString();
                    }
                    break;

                case R.id.rb_lru:
                    if (!(TextUtils.isEmpty(mTask.getText().toString())
                            ||TextUtils.isEmpty(mLeafBox.getText().toString()))) {
                        List<Integer> integers = new ArrayList<>();
                        String taskString = mTask.getText().toString();
                        String[] tasks = taskString.split(" ");
                        for (String s: tasks) {
                            integers.add(Integer.parseInt(s));
                        }
                        int leafBoxNumber = Integer.parseInt(mLeafBox.getText().toString());
                        PagerReplaceUtils utils = new PagerReplaceUtils(integers, leafBoxNumber);
                        float lru = utils.LRU();
                        mS = integers.toString() + "\n" +
                                "作业框数: " + utils.getN() + "\n" +
                                "LRU 缺页率: " + lru + "\n"+
                                "最后作业框数: "+ utils.map.toString();
                    }
                    break;

                case R.id.rb_opt:
                    if (!(TextUtils.isEmpty(mTask.getText().toString())
                            ||TextUtils.isEmpty(mLeafBox.getText().toString()))) {
                        List<Integer> integers = new ArrayList<>();
                        String taskString = mTask.getText().toString();
                        String[] tasks = taskString.split(" ");
                        for (String s: tasks) {
                            integers.add(Integer.parseInt(s));
                        }
                        int leafBoxNumber = Integer.parseInt(mLeafBox.getText().toString());
                        PagerReplaceUtils utils = new PagerReplaceUtils(integers, leafBoxNumber);
                        float opt = utils.OPT();
                        mS = integers.toString() + "\n" +
                                "作业框数: " + utils.getN() + "\n" +
                                "OPT 缺页率: " + opt + "\n"+
                                "最后作业框数: "+ utils.map.toString();
                    }
                    break;

                default:
                    break;
            }
            mResultEt.setText(mS);
        });
    }

    private void initView() {
        mLeafBox = findViewById(R.id.et_leaf_box_number);
        mTask = findViewById(R.id.et_task);
        mResultBt = findViewById(R.id.bt_result);
        mResultEt = findViewById(R.id.tv_result);
        mTypeRg = findViewById(R.id.rg_type);
    }
}
