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
 * Created by Administrator on 2016/2/24.
 */
public class MemberAddActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private MyDBHelper myDBHelper;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private ImageView img_back_addMember;
    private EditText edit_username_addM;
    private EditText edit_password_addM;
    private EditText edit_password_verify_addM;
    private EditText edit_phoneNumber_addM;
    private Button btn_submit_addM;

    private String username, password, password_verify;
    private String phoneNumber, affiliate;
    private Users user;
    private static final String TAG = "===MemberAddActivity===";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);

        bindViews();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_back_addMember :
            {
                finish();
                break;
            }
            case R.id.btn_submit_addM :
            {
                username = edit_username_addM.getText().toString();
                password = edit_password_addM.getText().toString();
                password_verify = edit_password_verify_addM.getText().toString();
                phoneNumber = edit_phoneNumber_addM.getText().toString();

                dbUtils = new SQLiteDatabaseUtils(myDBHelper);
                user = new Users();
                user = dbUtils.find(spHelper.getUsername());
                affiliate = user.getEnterprise_name();

                if (!username.equals("") && !password.equals("") && !password_verify.equals("") && !phoneNumber.equals("") )
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
                                            user = new Users(username, password, phoneNumber, 0, "2", "/", affiliate); //2 for enterprise added member users
                                            dbUtils.save(user);
                                            Toast.makeText(mContext, username + " 添加成功", Toast.LENGTH_LONG).show();
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
            }
        }
    }

    private void bindViews()
    {
        img_back_addMember = (ImageView) findViewById(R.id.img_back_addMember);
        edit_username_addM = (EditText) findViewById(R.id.edit_username_addM);
        edit_password_addM = (EditText) findViewById(R.id.edit_password_addM);
        edit_password_verify_addM = (EditText) findViewById(R.id.edit_password_verify_addM);
        edit_phoneNumber_addM = (EditText) findViewById(R.id.edit_phoneNumber_addM);
        btn_submit_addM = (Button) findViewById(R.id.btn_submit_addM);

        img_back_addMember.setOnClickListener(this);
        btn_submit_addM.setOnClickListener(this);
    }
}
