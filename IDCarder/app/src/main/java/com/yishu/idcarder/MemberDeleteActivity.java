package com.yishu.idcarder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/24.
 */
public class MemberDeleteActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private MyDBHelper myDBHelper;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private Users user ;
    private ImageView img_back_deleteM;
    private EditText edit_usernameOrPhoneNumber_deleteM;
    private Button btn_submit_deleteM;

    private String usernameOrPhoneNumber;
    private static final String TAG = "===MemberDelActivity===";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_member);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        dbUtils = new SQLiteDatabaseUtils(myDBHelper);
        user = new Users();

        bindViews();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_back__deleteM :
            {
                finish();
                break;
            }
            case R.id.btn_submit_deleteM :
            {
                usernameOrPhoneNumber = edit_usernameOrPhoneNumber_deleteM.getText().toString();
                if (!usernameOrPhoneNumber.equals(""))
                {
                    user = dbUtils.find(usernameOrPhoneNumber);
                    if (user == null)
                    {
                        user = dbUtils.findByPhone(usernameOrPhoneNumber);
                        if (user == null)
                        {
                            showWindowTips("删除失败，用户名或手机输入有误");
                        }
                        else
                        {
                            if (usernameOrPhoneNumber.equals(dbUtils.find(spHelper.getUsername()).getPhone_number()))
                            {
                                showWindowTips("不能删除管理员本人");
                            }
                            else
                            {
                                if ((dbUtils.find(spHelper.getUsername()).getEnterprise_name()).equals(user.getAffiliate()))
                                {
                                    dbUtils.deleteByPhoneNumber(usernameOrPhoneNumber);
                                    Log.e(TAG, "phoneNumber = " + usernameOrPhoneNumber);
                                    showWindowTips("删除成功");
                                }
                                else
                                {
                                    showWindowTips(usernameOrPhoneNumber + "不是本企业成员号码");
                                }
                            }
                        }
                    }
                    else
                    {
                        if (usernameOrPhoneNumber.equals(spHelper.getUsername()))
                        {
                            showWindowTips("不能删除管理员本人");
                        }
                        else
                        {
                            if ((dbUtils.find(spHelper.getUsername()).getEnterprise_name()).equals(user.getAffiliate()))
                            {
                                dbUtils.delete(usernameOrPhoneNumber);
                                Log.e(TAG, "username = " + usernameOrPhoneNumber);
                                showWindowTips("删除成功");
                            }
                            else
                            {
                                showWindowTips(usernameOrPhoneNumber + "不是本企业成员");
                            }
                        }
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
    private void showWindowTips(String tips)
    {
        Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
    }

    private void bindViews()
    {
        img_back_deleteM = (ImageView) findViewById(R.id.img_back__deleteM);
        btn_submit_deleteM = (Button) findViewById(R.id.btn_submit_deleteM);
        edit_usernameOrPhoneNumber_deleteM = (EditText) findViewById(R.id.edit_usernameOrPhoneNumber_deleteM);

        img_back_deleteM.setOnClickListener(this);
        btn_submit_deleteM.setOnClickListener(this);
//        edit_usernameOrPhoneNumber_deleteM.addTextChangedListener(watcher);
//        edit_usernameOrPhoneNumber_deleteM.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
//            @Override
//            public void onViewAttachedToWindow(View v) {
//                edit_usernameOrPhoneNumber_deleteM.setText("");
//                edit_usernameOrPhoneNumber_deleteM.setTextColor(Color.BLACK);
//            }
//
//            @Override
//            public void onViewDetachedFromWindow(View v) {
//
//            }
//        });
    }

//    private TextWatcher watcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            Log.e(TAG,"beforeTextChanged");
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Log.e(TAG,"onTextChanged");
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            Log.e(TAG,"afterTextChanged");
////            edit_usernameOrPhoneNumber_deleteM.setText("");
////            edit_usernameOrPhoneNumber_deleteM.setTextColor(Color.BLACK);
//        }
//    };
}
