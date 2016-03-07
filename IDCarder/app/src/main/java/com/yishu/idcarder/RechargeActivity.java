package com.yishu.idcarder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/5.
 */
public class RechargeActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private Button btn_submit_recharge;
    private ImageView img_back_recharge;
    private EditText edit_money_recharge;

    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private MyDBHelper myDBHelper;
    private Users user;
    private String username;
    private static final String TAG = "===RechargeAct===";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        mContext = getApplicationContext();

        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        dbUtils = new SQLiteDatabaseUtils(myDBHelper);
        user = new Users();
        username = spHelper.getUsername();

        bindViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit_recharge :
            {
                try
                {
                    double money = Double.valueOf(edit_money_recharge.getText().toString());
                    dbUtils.recharge(money, username);
//                    Log.e(TAG, username + " recharge");
                    user = dbUtils.find(username);
                    Log.e(TAG, user.getUsername() + user.getPhone_number() + " recharge");
//                    txt_money.setText(String.valueOf(user.getMoney()));
                }
                catch (Exception e)
                {
                    Toast.makeText(mContext, "输入格式不正确", Toast.LENGTH_LONG).show();
                }
                finally {
                    edit_money_recharge.setText("");
                }
                Log.e(TAG, String.valueOf(user.getMoney()) + "recharge");
                break;
            }
            case R.id.img_back_recharge :
            {
                finish();
                break;
            }
        }

    }

    private void bindViews()
    {
        btn_submit_recharge = (Button) findViewById(R.id.btn_submit_recharge);
        edit_money_recharge = (EditText) findViewById(R.id.edit_money_recharge);
        img_back_recharge = (ImageView) findViewById(R.id.img_back_recharge);

        btn_submit_recharge.setOnClickListener(this);
        img_back_recharge.setOnClickListener(this);
    }
}
