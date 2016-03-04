package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/21.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    private Context mContext;
    private Intent intent;
    private Button btn_login;
    private Button btn_register;
    private TextView txt_forgetPassword;
    private EditText edit_usernameL;
    private EditText edit_passwordL;
    private Spinner spin_username_phone;
    private int spin_position = 0; //0 for using username to login, 1 for using phone number to login.

//    Button btn_find;
    private SQLiteDatabaseUtils dbUtils;
    private MyDBHelper myDBHelper;
    private Users user;
    private SPHelper spHelper;
    private Map<String, String > data;
    private static final String TAG = "===LoginActivity===";
//    private static final int DELAY = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        bindViews();

    }
    @Override
    protected void onStart() {
        super.onStart();
        try
        {
            autoLogin();
        }catch (Exception e){}

    }
    @Override
    public void onClick(View v) {
        intent = null;
        switch (v.getId())
        {
            case R.id.btn_login :
//                System.out.println("========" + System.getProperty("java.library.path"));
//                intent = new Intent(mContext, MainActivity.class);
//                intent = new Intent(mContext, NFCActivity.class);
//                startActivity(intent);
                myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
                user = new Users();
                dbUtils = new SQLiteDatabaseUtils(myDBHelper);

                if (spin_position == 0)
                {
                    String username = edit_usernameL.getText().toString();
                    String password = edit_passwordL.getText().toString();
                    user = dbUtils.find(username);
                    if (username.equals(user.getUsername()))
                    {
                        if (password.equals(user.getPassword()))
                        {
                            intent = new Intent(mContext, NFCActivity.class);
                            startActivity(intent);
                            spHelper.save(username, password);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(mContext,"密码不正确", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext,"用户名不存在", Toast.LENGTH_LONG).show();
                    }
                }
                if (spin_position == 1)
                {
                    String phoneNumber = edit_usernameL.getText().toString();
                    String password = edit_passwordL.getText().toString();
                    user = dbUtils.findByPhone(phoneNumber);
                    if (phoneNumber.equals(user.getPhone_number()))
                    {
                        if (password.equals(user.getPassword()))
                        {
                            intent = new Intent(mContext, NFCActivity.class);
                            startActivity(intent);
                            spHelper.save(user.getUsername(), password);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(mContext,"密码不正确", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext,"手机号未注册或有误", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.btn_register :
                intent = new Intent(mContext, RegisterChoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_forgetPassword :
                intent = new Intent(mContext, ResetPWActivity.class);
                startActivity(intent);
                break;
//            case  R.id.btn_find :
//                myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
//                user = new Users();
//                dbUtils = new SQLiteDatabaseUtils(myDBHelper);
//                user = dbUtils.find("James");
//                Toast.makeText(mContext,user.getUsername(), Toast.LENGTH_LONG).show();
        }
//        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId())
        {
            case R.id.spin_username_phone :
            {
                spin_position = position;

                Log.e(TAG, "item selected ==>" + spin_position);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void autoLogin()
    {
        data = spHelper.readSP();
        String username = data.get("username");
        String password = data.get("password");
        edit_usernameL.setText(username);
        edit_passwordL.setText(password);
        Log.e(TAG, username);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        user = new Users();
        dbUtils = new SQLiteDatabaseUtils(myDBHelper);
        user = dbUtils.find(edit_usernameL.getText().toString());
        if (username.equals(user.getUsername()) && password.equals(user.getPassword()))
        {
            intent = new Intent(mContext, NFCActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            edit_usernameL.setText("");
            edit_passwordL.setText("");
        }
    }

    private void delay()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(2000);
                    setContentView(R.layout.activity_login);
                }
                catch (Exception e){e.printStackTrace();}
            }
        }).start();
    }
    private void bindViews()
    {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
//        btn_find = (Button) findViewById(R.id.btn_find);
        edit_usernameL = (EditText) findViewById(R.id.edit_usernameL);
        edit_passwordL = (EditText) findViewById(R.id.edit_passwordL);
        spin_username_phone = (Spinner) findViewById(R.id.spin_username_phone);
        txt_forgetPassword = (TextView) findViewById(R.id.txt_forgetPassword);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        spin_username_phone.setOnItemSelectedListener(this);
        txt_forgetPassword.setOnClickListener(this);
//        btn_find.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Destroy()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "Stop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume()");
    }
}
