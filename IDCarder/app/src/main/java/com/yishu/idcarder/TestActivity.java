package com.yishu.idcarder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/3/18.
 */
public class TestActivity extends AppCompatActivity
{
    private Context mContext;
    private LinearLayout test_checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mContext = getApplicationContext();
        bindViews();
    }

    private void bindViews()
    {
        test_checkBox = (LinearLayout) findViewById(R.id.text_checkBox);

        CheckBox checkBox = new CheckBox(mContext);
        checkBox.setText("555555");
        checkBox.setTextColor(Color.BLACK);
        checkBox.setBackgroundResource(R.drawable.txt_could_change);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        checkBox.setLayoutParams(lp);
        test_checkBox.addView(checkBox);

    }
}
