package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/2/23.
 */
public class RegisterChoiceActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private TextView txt_enterprise;
    private TextView txt_personal;
    private ImageView img_backR_enterprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choice);

        mContext = getApplicationContext();
        bindViews();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_enterprise :
            {
                Intent intent = new Intent(mContext, EnterpriseRegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.txt_personal :
            {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.img_backRChoice :
            {
                finish();
                break;
            }
        }

    }
    private void bindViews()
    {
        txt_enterprise = (TextView) findViewById(R.id.txt_enterprise);
        txt_personal = (TextView) findViewById(R.id.txt_personal);
        img_backR_enterprise = (ImageView) findViewById(R.id.img_backRChoice);

        txt_enterprise.setOnClickListener(this);
        txt_personal.setOnClickListener(this);
        img_backR_enterprise.setOnClickListener(this);
    }
}
