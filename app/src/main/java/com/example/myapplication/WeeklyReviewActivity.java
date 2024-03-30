package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WeeklyReviewActivity extends AppCompatActivity {

    private RadioGroup mRG1;
    private CheckBox mCB1;
    private CheckBox mCP2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_review);

        mRG1 = findViewById(R.id.rg_1);
        mCB1 = findViewById(R.id.cb_1);
        mCP2 = findViewById(R.id.cb_2);

        mRG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                Toast.makeText(WeeklyReviewActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show(); //点什么，就getText对应的内容

            }
        });

        mCB1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(WeeklyReviewActivity.this,isChecked?"Selected":"Not Selected",Toast.LENGTH_SHORT).show();
            }
        });

    }
}