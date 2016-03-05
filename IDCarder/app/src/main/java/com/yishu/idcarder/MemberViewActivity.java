package com.yishu.idcarder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
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
    private LinearLayout member_list;
    private TextView txt_username;
    private TextView txt_line;

    private Users user;
    private List<Users> enterpriseMembers;
    private static final String TAG = "===MemberViewActivity==";

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
        member_list = (LinearLayout) findViewById(R.id.member_list);

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
            txt_username = new TextView(this);
            txt_line = new TextView(this);
            txt_line = setToLine(txt_line, 1);
            member_list.addView(setTextViewAttrs(txt_username, user.getUsername()));
            member_list.addView(txt_line);
        }
        member_list.removeView(txt_line);

    }
    private TextView setTextViewAttrs(TextView textView, String txtValue)
    {
        textView.setText(txtValue);
        textView.setPadding(0, 30, 0, 30);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( //
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);

        return textView;
    }
    private TextView setToLine(TextView textView, int height)
    {
        textView.setBackgroundColor(Color.parseColor("#b5b5b5"));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( //
                LinearLayout.LayoutParams.MATCH_PARENT, height);
        textView.setLayoutParams(lp);
        return textView;
    }
}
