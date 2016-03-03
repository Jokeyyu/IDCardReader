package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/1/22.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn_submit;
    private Context mContext;
    private MyDBHelper myDBHelper;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private EditText edit_usernameR;
    private EditText edit_passwordR;
    private EditText edit_password_verify;
    private EditText edit_phoneNumberR;
    private ImageView img_back;
    private String username, password, password_verify;
    private String phoneNumber;
    private Users user;
    private static final String TAG = "===RegisterActivity ===";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);

        bindViews();
    }
    private void bindViews()
    {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        edit_usernameR = (EditText) findViewById(R.id.edit_usernameR);
        edit_passwordR = (EditText) findViewById(R.id.edit_passwordR);
        edit_password_verify = (EditText) findViewById(R.id.edit_password_verifyR);
        edit_phoneNumberR = (EditText) findViewById(R.id.edit_phoneNumberR);
        img_back = (ImageView) findViewById(R.id.img_backR);

        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit :
                username = edit_usernameR.getText().toString();
                password = edit_passwordR.getText().toString();
                password_verify = edit_password_verify.getText().toString();
                phoneNumber = edit_phoneNumberR.getText().toString();

                dbUtils = new SQLiteDatabaseUtils(myDBHelper);

                if (!username.equals("") && !password.equals("") && !password_verify.equals("") && !phoneNumber.equals("") )
                {
                    if (!dbUtils.isRegistered("username", username))
                    {
                        if (!dbUtils.isRegistered("phone_number", phoneNumber))
                        {
                            if (username.length() >= 2)
                            {
//                                if ( phoneNumber.length() == 11)
                                if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber) && phoneNumber.length() == 11)
                                {
                                    if (password.length() >= 6)
                                    {
                                        if (password.equals(password_verify))
                                        {
                                            user = new Users(username, password, phoneNumber, 0, "1", "/", "");//  1 for personal users,
                                            dbUtils.save(user);
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                startActivity(intent);
                                            Intent intent = new Intent(mContext, NFCActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(mContext, username + " 注册成功", Toast.LENGTH_LONG).show();
                                            spHelper.save(username, password);
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(mContext, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(mContext, "密码不能少于6个字符", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(mContext, "用户名不能少于2个字符", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "手机号已注册", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext, "用户名已注册", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext, "注册信息不完整，请重新注册", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.img_backR :
                finish();
                break;
        }
    }
}
