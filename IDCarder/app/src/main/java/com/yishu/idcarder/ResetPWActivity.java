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
public class ResetPWActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView img_backResetPW;
    private Button btn_submitResetPW;
    private EditText edit_usernameResetPW;
    private EditText edit_passwordResetPW;
    private EditText edit_password_verifyResetPW;
    private EditText edit_phoneNumberResetPW;
    private Context mContext;

    private Users user;
    private SQLiteDatabaseUtils dbUtils;
    private MyDBHelper myDBHelper;

    private String username, password, password_verify;
    private String phoneNumber;
    private static final String PASSWORD = "password";
    private static final String TAG = "===ResetPWActivity===";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mContext = getApplicationContext();

        bindViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_backResetPW :
            {
                finish();
                break;
            }
            case R.id.btn_submitResetPW :
            {
                username = edit_usernameResetPW.getText().toString();
                password = edit_passwordResetPW.getText().toString();
                password_verify = edit_password_verifyResetPW.getText().toString();
                phoneNumber = edit_phoneNumberResetPW.getText().toString();

                myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
                user = new Users();
                dbUtils = new SQLiteDatabaseUtils(myDBHelper);
                user = dbUtils.find(username);
                if (username.equals(user.getUsername()))
                {
                    if (phoneNumber.equals(user.getPhone_number()))
                    {
                        if (password.equals(password_verify))
                        {
                            Log.e(TAG, "password before reset ===> " + user.getPassword());
                            dbUtils.changeInfo(PASSWORD, password, username);

                            user = dbUtils.find(username);
                            Log.e(TAG, "password after reset ===> " + user.getPassword());

                            Toast.makeText(mContext, "密码重置成功", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(mContext,"用户名不存在", Toast.LENGTH_LONG).show();
                }

                break;
            }
        }
    }
    private void bindViews()
    {
        img_backResetPW = (ImageView) findViewById(R.id.img_backResetPW);
        btn_submitResetPW = (Button) findViewById(R.id.btn_submitResetPW);
        edit_usernameResetPW = (EditText) findViewById(R.id.edit_usernameResetPW);
        edit_passwordResetPW = (EditText) findViewById(R.id.edit_passwordResetPW);
        edit_password_verifyResetPW = (EditText) findViewById(R.id.edit_password_verifyResetPW);
        edit_phoneNumberResetPW = (EditText) findViewById(R.id.edit_phoneNumberResetPW);

        img_backResetPW.setOnClickListener(this);
        btn_submitResetPW.setOnClickListener(this);
    }
}
