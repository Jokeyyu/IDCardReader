package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    private ImageView img_addMember_list;
    private ImageView img_delMember_list;
    private LinearLayout member_list;
    private TextView txt_username;
    private TextView txt_line;
    private CheckBox checkBox_username;

    private Users user;
    private List<Users> enterpriseMembers;
    private List<String> usernames;
    private static final String TAG = "===MemberViewActivity==";
    private static final String FONT_COLOR = "#717171";

    private String username;

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
    protected void onResume() {
        super.onResume();
        clearViewList();
        addViewList();
    }
    //    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        MenuInflater inflater = new MenuInflater(mContext);
//        inflater.inflate(R.menu.menu_context_edit_member, menu);
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.menu_delete_member :
//            {
//                Toast.makeText(mContext,"HI...", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
//        return true;
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_back_ViewM :
            {
                finish();
                break;
            }
            case R.id.img_addMember_list :
            {
                Intent intent = new Intent(mContext, MemberAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.img_delMember_list :
            {
                int count = 0;
                for (String username : usernames)
                {
                    dbUtils.changeInfo("affiliate", "", username);
                    count ++;
                    Log.e(TAG,username);
                }
                clearViewList();
                addViewList();
                img_addMember_list.setVisibility(View.VISIBLE);
                findViewById(R.id.txt_addMember).setVisibility(View.VISIBLE);

                img_delMember_list.setVisibility(View.GONE);
                findViewById(R.id.txt_delMember).setVisibility(View.GONE);
                Toast.makeText(mContext, "删除了" + count + "位成员", Toast.LENGTH_SHORT).show();
                count = 0;
                break;
            }
        }
    }

    private void bindViews()
    {
        img_back_ViewM = (ImageView) findViewById(R.id.img_back_ViewM);
        member_list = (LinearLayout) findViewById(R.id.member_list);
        img_addMember_list = (ImageView) findViewById(R.id.img_addMember_list);
        img_delMember_list = (ImageView) findViewById(R.id.img_delMember_list);

        img_back_ViewM.setOnClickListener(this);
        img_addMember_list.setOnClickListener(this);
        img_delMember_list.setOnClickListener(this);
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
//            Log.e(TAG, user.getUsername() + " ==> " + i);
            txt_username = new TextView(this);
            txt_line = new TextView(this);
            txt_line = setToLine(txt_line, 1);
            member_list.addView(setTextViewAttrs(txt_username, user.getUsername()));
            txt_username.setBackgroundResource(R.drawable.txt_could_change);
            txt_username.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v);
                    return true;
                }
            });
//            registerForContextMenu(txt_username);
            member_list.addView(txt_line);
        }
//        member_list.removeView(txt_line);

    }
    private void addCheckBoxList()
    {
        enterpriseMembers = new LinkedList<Users>();
        usernames = new LinkedList<String>();
        user = dbUtils.find(spHelper.getUsername());
        enterpriseMembers = dbUtils.findEnterpriseMembers(user.getEnterprise_name());
        int i = 0;
        for (Users user : enterpriseMembers)
        {
            i++;
            Log.e(TAG, user.getUsername() + " ==> " + i);
            checkBox_username = new CheckBox(mContext);
            txt_line = new TextView(this);
            txt_line = setToLine(txt_line, 1);

            checkBox_username.setText(user.getUsername());
            checkBox_username.setTextColor(Color.parseColor(FONT_COLOR));
            checkBox_username.setButtonDrawable(R.drawable.checkbox);
//            int checkBox_username_paddingLeft = getResources().getDrawable(R.drawable.unselected02).getIntrinsicWidth() + 5;
            checkBox_username.setPadding(20, 20, 0, 20);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 0, 20, 0);
            checkBox_username.setLayoutParams(lp);
//            username = checkBox_username.getText().toString();
            checkBox_username.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
//                        String username = buttonView.getText().toString();
//                        Log.e(TAG, username + " ==> ");
                        usernames.add(buttonView.getText().toString());
                    } else {
                        if (usernames.size() > 0) {
                            usernames.remove(buttonView.getText().toString());
                        }
                    }
                }
            });
            member_list.addView(checkBox_username);
            checkBox_username.setBackgroundResource(R.drawable.txt_could_change);
            member_list.addView(txt_line);
        }
    }
    private void clearViewList()
    {
        member_list.removeAllViews();
    }
    private TextView setTextViewAttrs(TextView textView, String txtValue)
    {
        textView.setText(txtValue);
        textView.setPadding(20, 20, 20, 20);
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
        lp.setMargins(20, 0, 20, 0);
        textView.setLayoutParams(lp);
        return textView;
    }

    private void showPopupWindow(View v)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
//        findViewById(R.id.txt_view_popup).
        final PopupWindow popupWindow = new PopupWindow(view, //
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        popupWindow.showAsDropDown(v);
//        popupWindow.showAtLocation(findViewById(R.id.activity_view_member), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        txt_username = (TextView) v;
        username = txt_username.getText().toString();

        view.findViewById(R.id.txt_delete_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbUtils.changeInfo("affiliate", "", username);
                clearViewList();
                addViewList();
                Toast.makeText(mContext, username + " 删除成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.txt_multiManage_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, TestActivity.class);
//                startActivity(intent);
                clearViewList();
                addCheckBoxList();
                popupWindow.dismiss();

                img_addMember_list.setVisibility(View.GONE);
                findViewById(R.id.txt_addMember).setVisibility(View.GONE);

                img_delMember_list.setVisibility(View.VISIBLE);
                findViewById(R.id.txt_delMember).setVisibility(View.VISIBLE);
            }
        });
    }

}
