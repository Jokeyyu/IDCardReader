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
 * Created by Administrator on 2016/2/24.
 */
public class MemberAddActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private MyDBHelper myDBHelper;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private ImageView img_back_addMember;
    private EditText edit_usernameOrPhoneNumber_addM;

    private Button btn_submit_addM;

    private String usernameOrPhoneNumber, affiliate;
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
                usernameOrPhoneNumber = edit_usernameOrPhoneNumber_addM.getText().toString();

                dbUtils = new SQLiteDatabaseUtils(myDBHelper);
                user = new Users();
                user = dbUtils.find(spHelper.getUsername());
                affiliate = user.getEnterprise_name();

                if (!usernameOrPhoneNumber.equals(""))
                {
                    user = dbUtils.find(usernameOrPhoneNumber);
                    if (user == null)
                    {
                        user = dbUtils.findByPhone(usernameOrPhoneNumber);
                        if (user == null)
                        {
                            showWindowTips("用户名或手机未注册，请先注册");
                        }
                        else if (user != null && !user.getAffiliate().equals(dbUtils.find(spHelper.getUsername()).getEnterprise_name()))
                        {
                            if (usernameOrPhoneNumber.equals(dbUtils.find(spHelper.getUsername()).getPhone_number()))
                            {
                                showWindowTips("不能添加管理员本人");
                            }
                            else
                            {
                                dbUtils.changeInfo("affiliate", affiliate, user.getUsername());
                                showWindowTips("添加成功");
                            }
                        }
                        else
                        {
                            showWindowTips(user.getPhone_number() + "已是本公司成员");
                        }
                    }
                    else if (user != null && !user.getAffiliate().equals(dbUtils.find(spHelper.getUsername()).getEnterprise_name()))
                    {
                        if (usernameOrPhoneNumber.equals(spHelper.getUsername()))
                        {
                            showWindowTips("不能添加管理员本人");
                        }
                        else
                        {
                            dbUtils.changeInfo("affiliate", affiliate, user.getUsername());
                            showWindowTips("添加成功");
                        }
                    }
                    else
                    {
                        showWindowTips(user.getUsername() + "已是本公司成员");
                    }
                }
                else
                {
                    showWindowTips("请输入用户名或手机");
                }
                break;
            }
        }
    }

    private void bindViews()
    {
        img_back_addMember = (ImageView) findViewById(R.id.img_back_addMember);
        edit_usernameOrPhoneNumber_addM = (EditText) findViewById(R.id.edit_usernameOrPhoneNumber_addM);
        btn_submit_addM = (Button) findViewById(R.id.btn_submit_addM);

        img_back_addMember.setOnClickListener(this);
        btn_submit_addM.setOnClickListener(this);
    }

    private void showWindowTips(String tips)
    {
        Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
    }
}
