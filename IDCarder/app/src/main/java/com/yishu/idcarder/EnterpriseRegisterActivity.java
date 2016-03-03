package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/23.
 */
public class EnterpriseRegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private MyDBHelper myDBHelper;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private Users user;
    private ImageView img_backR_enterprise;
    private Button btn_submit_enterprise;
    private EditText edit_enterpriseNameR;
    private EditText edit_usernameR_enterprise;
    private EditText edit_passwordR_enterprise;
    private EditText edit_password_verifyR_enterprise;
    private EditText edit_phoneNumberR_enterprise;

    private String username, password, password_verify;
    private String phoneNumber, enterpriseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_enterprise);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        bindViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_backR_enterprise :
            {
                finish();
                break;
            }
            case R.id.btn_submit_enterprise :
            {
                username = edit_usernameR_enterprise.getText().toString();
                password = edit_passwordR_enterprise.getText().toString();
                password_verify = edit_password_verifyR_enterprise.getText().toString();
                phoneNumber = edit_phoneNumberR_enterprise.getText().toString();
                enterpriseName = edit_enterpriseNameR.getText().toString();

                dbUtils = new SQLiteDatabaseUtils(myDBHelper);

                if (!username.equals("") && !password.equals("") && !password_verify.equals("") && !phoneNumber.equals("") && !enterpriseName.equals(""))
                {
                    if (!dbUtils.isRegistered("enterprise_name", enterpriseName))
                    {
                        if (!dbUtils.isRegistered("username", username))
                        {
                            if (!dbUtils.isRegistered("phone_number", phoneNumber))
                            {
                                if (username.length() >= 2)
                                {
                                    if ( phoneNumber.length() == 11)
                                    {
                                        if (password.length() >= 6)
                                        {
                                            if (password.equals(password_verify))
                                            {
                                                user = new Users(username, password, phoneNumber, 0, "0", enterpriseName, "/"); //0 for enterprise users,
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
                        Toast.makeText(mContext, "企业名称已注册", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext, "注册信息不完整，请重新注册", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void bindViews()
    {
        img_backR_enterprise = (ImageView) findViewById(R.id.img_backR_enterprise);
        btn_submit_enterprise = (Button) findViewById(R.id.btn_submit_enterprise);
        edit_enterpriseNameR = (EditText) findViewById(R.id.edit_enterpriseNameR);
        edit_usernameR_enterprise = (EditText) findViewById(R.id.edit_usernameR_enterprise);
        edit_passwordR_enterprise = (EditText) findViewById(R.id.edit_passwordR_enterprise);
        edit_password_verifyR_enterprise = (EditText) findViewById(R.id.edit_password_verifyR_enterprise);
        edit_phoneNumberR_enterprise = (EditText) findViewById(R.id.edit_phoneNumberR_enterprise);

        img_backR_enterprise.setOnClickListener(this);
        btn_submit_enterprise.setOnClickListener(this);
    }
}
