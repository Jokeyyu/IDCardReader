package com.yishu.idcarder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
public class MemberViewActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private MyDBHelper myDBHelper;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private ImageView img_back_ViewM;
    private EditText edit_username_addM;
    private EditText edit_password_addM;
    private EditText edit_password_verify_addM;
    private EditText edit_phoneNumber_addM;
    private Button btn_submit_addM;
    private TableLayout table_memberList;
    private TableRow tableRow_memberInfo;
    private TextView txt_username, txt_password, txt_phoneNumber;
    private Drawable attr1;

    private String username, password, password_verify;
    private String phoneNumber, affiliate;
    private Users user;
    private List<Users> enterpriseMembers;
    private static final String TAG = "===MemberViewActivity==";
    private static final int MARGIN_LEFT_NOT_FIRST_CELL = 0;
    private static final int MARGIN_LEFT_FIRST_CELL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member);

        mContext = getApplicationContext();

        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        dbUtils = new SQLiteDatabaseUtils(myDBHelper);
        user = new Users();

        bindViews();
        addViewList();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        addViewList();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_back_ViewM :
            {
                finish();
                break;
            }
        }
    }

    private void bindViews()
    {
        img_back_ViewM = (ImageView) findViewById(R.id.img_back_ViewM);
        table_memberList = (TableLayout) findViewById(R.id.table_memberList);

        img_back_ViewM.setOnClickListener(this);
    }

    private void addViewList()
    {
        enterpriseMembers = new LinkedList<Users>();
        user = dbUtils.find(spHelper.getUsername());
        enterpriseMembers = dbUtils.findEnterpriseMembers(user.getEnterprise_name());
        int i = 0;
        for (Users user : enterpriseMembers)
        {
            i++;
            Log.e(TAG, user.getUsername() + i);
            tableRow_memberInfo = new TableRow(this);
            tableRow_memberInfo.addView(setTextViewAttrs(txt_username, user.getUsername(), MARGIN_LEFT_FIRST_CELL));
            tableRow_memberInfo.addView(setTextViewAttrs(txt_password, user.getPassword(), MARGIN_LEFT_NOT_FIRST_CELL));
            tableRow_memberInfo.addView(setTextViewAttrs(txt_phoneNumber, user.getPhone_number(), MARGIN_LEFT_NOT_FIRST_CELL));
            table_memberList.addView(tableRow_memberInfo);
        }

    }
    private TextView setTextViewAttrs(TextView textView, String txtValue, int marginLeft)
    {
        textView = new TextView(this);
        textView.setText(txtValue);
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        textView.setPadding(0, 20, 0, 20);
        textView.setGravity(Gravity.CENTER);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(marginLeft, 2, 0, 0);
        textView.setLayoutParams(lp);

        return textView;
    }
}
