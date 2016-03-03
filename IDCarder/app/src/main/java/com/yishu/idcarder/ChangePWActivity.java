package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ChangePWActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView img_backChangePW;
    private Button btn_submitChangePW;
    private EditText edit_currentPWChangePW;
    private EditText edit_newPWChangePW;
    private EditText edit_newPW_verifyChangePW;
    private EditText edit_phoneNumberChangePW;

    private Context mContext;
    private SPHelper spHelper;

    private Users user;
    private SQLiteDatabaseUtils dbUtils;
    private MyDBHelper myDBHelper;

    private String username,currentPW, newPW, newPW_verify;
    private String phoneNumber;
    private static final String PASSWORD = "password";
    private static final String TAG = "===ChangePWActivity===";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        try
        {
            bindViews();
        }catch (Exception e){e.printStackTrace();}

    }


    private void bindViews()
    {
        img_backChangePW = (ImageView) findViewById(R.id.img_backChangePW);
        btn_submitChangePW = (Button) findViewById(R.id.btn_submitChangePW);
        edit_currentPWChangePW = (EditText) findViewById(R.id.edit_currentPWChangePW);
        edit_newPWChangePW = (EditText) findViewById(R.id.edit_newPWChangePW);
        edit_newPW_verifyChangePW = (EditText) findViewById(R.id.edit_newPW_verifyChangePW);
        edit_phoneNumberChangePW = (EditText) findViewById(R.id.edit_phoneNumberChangePW);

        img_backChangePW.setOnClickListener(this);
        btn_submitChangePW.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_backChangePW :
            {
                finish();
                break;
            }
            case R.id.btn_submitChangePW :
            {
                username = spHelper.getUsername();
                Log.e(TAG, username);

                currentPW = edit_currentPWChangePW.getText().toString();
                newPW = edit_newPWChangePW.getText().toString();
                newPW_verify = edit_newPW_verifyChangePW.getText().toString();
                phoneNumber = edit_phoneNumberChangePW.getText().toString();

                myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
                user = new Users();
                dbUtils = new SQLiteDatabaseUtils(myDBHelper);
                user = dbUtils.find(username);
                if (currentPW.equals(user.getPassword()))
                {
                    if (phoneNumber.equals(user.getPhone_number()))
                    {
                        if (newPW.equals(newPW_verify))
                        {
                            Log.e(TAG, "password before reset ===> " + user.getPassword());
                            dbUtils.changeInfo(PASSWORD, newPW, username);

                            user = dbUtils.find(username);
                            Log.e(TAG, "password after reset ===> " + user.getPassword());

                            Toast.makeText(mContext, "密码修改成功，请重新登录", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(mContext, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext,"手机号码有误", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"当前密码输入错误", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
