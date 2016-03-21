package com.yishu.idcarder;


//import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
//import android.nfc.NfcAdapter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

//import com.otg.idcard.OTGReadCardAPI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    private TextView txt_idCardReader;
//    private TextView txt_settings;
//    private ImageView img_home;
    private ImageView img_backM;
    private ImageView img_headM;
    private Button btn_logout;
    private TextView txt_recharge;
    private TextView txt_usernameM;
    private TextView txt_money;
    private TextView txt_changePW;
//    private TextView txt_deleteMember;
    private TextView txt_viewMember;
    private TextView txt_userTag_personalM;
    private TextView txt_userTag_enterpriseM;
    private SPHelper spHelper;
    private SQLiteDatabaseUtils dbUtils;
    private MyDBHelper myDBHelper;
    private Users user;
    private String username;
//    private AppManager appManager;
//    private TextView txt_info;
//    private TextView txt_mine;
//    private IDCardReaderFragment fragment_idCardReader;
//    private FragmentManager fm;
//    private SettingsFragment fragment_settings;
    private HomeFragment fragment_home;
//    private NFCFragment fragment_nfc;
    private Context mContext;
    private static final String TAG = "===MainActivity===";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "head.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        spHelper = new SPHelper(mContext);
        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        dbUtils = new SQLiteDatabaseUtils(myDBHelper);
        user = new Users();

        username = spHelper.getUsername();
        user = dbUtils.find(username);

//        fm = getFragmentManager();
//        fragment_home = new HomeFragment();
//        fm.beginTransaction().replace(R.id.layout_content, fragment_home).commit();

        bindViews();
        if (user.getTag().equals("0"))
        {
            txt_userTag_enterpriseM.setVisibility(View.VISIBLE);
//            txt_deleteMember.setVisibility(View.VISIBLE);
            txt_viewMember.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_userTag_personalM.setVisibility(View.VISIBLE);
        }
    }

    private void bindViews()
    {
//        txt_idCardReader = (TextView) findViewById(R.id.txt_idCardReader);
//        txt_settings = (TextView) findViewById(R.id.txt_settings);
//        img_home = (ImageView) findViewById(R.id.img_home);
//        txt_info = (TextView) findViewById(R.id.txt_info);
//        txt_mine = (TextView) findViewById(R.id.txt_mine);
        img_backM = (ImageView) findViewById(R.id.img_backM);
        img_headM = (ImageView) findViewById(R.id.img_headM);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        txt_recharge = (TextView) findViewById(R.id.txt_recharge);
        txt_money = (TextView) findViewById(R.id.txt_money);
        txt_changePW = (TextView) findViewById(R.id.txt_change_password);
//        txt_deleteMember = (TextView) findViewById(R.id.txt_deleteMemberM);
        txt_viewMember = (TextView) findViewById(R.id.txt_viewMemberM);
        txt_userTag_personalM = (TextView) findViewById(R.id.txt_userTag_PersonalM);
        txt_userTag_enterpriseM = (TextView) findViewById(R.id.txt_userTag_EnterpriseM);

        txt_usernameM = (TextView) findViewById(R.id.txt_usernameM);
        txt_usernameM.setText(spHelper.getUsername());


        img_backM.setOnClickListener(this);
        img_headM.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        txt_recharge.setOnClickListener(this);
        txt_changePW.setOnClickListener(this);
//        txt_deleteMember.setOnClickListener(this);
        txt_viewMember.setOnClickListener(this);
//        txt_idCardReader.setOnClickListener(this);
//        txt_settings.setOnClickListener(this);
//        img_home.setOnClickListener(this);
//        txt_mine.setOnClickListener(this);
//
//        txt_mine.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        username = txt_usernameM.getText().toString();
//        username = spHelper.getUsername();
//        user = dbUtils.find(username);
        user = dbUtils.find(username);
        Log.e(TAG, username + " " + user.getEnterprise_name() + " " + user.getTag() + " " + user.getAffiliate() + " " + user.getMoney());
        txt_money.setText(String.valueOf(user.getMoney()));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.img_backM :
            {
                finish();
                break;
            }
            case R.id.img_headM :
            {
//                Intent intent = new Intent(mContext, TestActivity.class);
//                startActivity(intent);
                showEditHeadPicPopupWindow();
                break;
            }
            case R.id.btn_logout :
            {
                SPHelper spHelper = new SPHelper(mContext);
                spHelper.save("","");
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
//                appManager = new AppManager();
//                appManager.finishAll();
                NFCActivity.instance.finish();
                finish();
//                System.exit(0);
                break;
            }
            case R.id.txt_recharge :
            {
                Intent intent = new Intent(mContext, RechargeActivity.class);
                startActivity(intent);
//                try
//                {
//                    double money = Double.valueOf(edit_money.getText().toString());
//                    dbUtils.recharge(money, username);
////                    Log.e(TAG, username + " recharge");
//                    user = dbUtils.find(username);
//                    Log.e(TAG, user.getUsername()+user.getPhone_number() + " recharge");
//                    txt_money.setText(String.valueOf(user.getMoney()));
//                }
//                catch (Exception e)
//                {
//                    Toast.makeText(mContext, "输入格式不正确",Toast.LENGTH_LONG).show();
//                }
//                finally {
//                    edit_money.setText("");
//                }
//                Log.e(TAG, String.valueOf(user.getMoney()) + "recharge");

                break;
            }
            case R.id.txt_change_password :
            {
                Intent intent = new Intent(mContext, ChangePWActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.txt_viewMemberM :
            {
                Intent intent = new Intent(mContext, MemberViewActivity.class);
                startActivity(intent);
                break;
            }
//            case R.id.txt_deleteMemberM :
//            {
//                Intent intent = new Intent(mContext, MemberDeleteActivity.class);
//                startActivity(intent);
//                break;
//            }
//            case R.id.txt_idCardReader :
////                fragment_nfc = new NFCFragment();
////                fm.beginTransaction().replace(R.id.layout_content, fragment_nfc).commit();
//                Intent intent = new Intent(this, NFCActivity.class);
//                startActivity(intent);
////                fragment_idCardReader = new IDCardReaderFragment();
////                txt_info.setText("");
////                fm.beginTransaction().replace(R.id.layout_content, fragment_idCardReader).commit();
//                break;
////            case R.id.txt_settings :
////                fragment_settings = new SettingsFragment();
////                fm.beginTransaction().replace(R.id.layout_content, fragment_settings).commit();
////                break;
//            case R.id.img_home :
//                fragment_home = new HomeFragment();
//                fm.beginTransaction().replace(R.id.layout_content, fragment_home).commit();
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
        {
            return;
        }
        else
        {
            switch (requestCode)
            {
                case IMAGE_REQUEST_CODE :
                {
                    resizeImage(data.getData());
                    break;
                }
                case CAMERA_REQUEST_CODE :
                {
                    if (hasSDCard())
                    {
                        resizeImage(getImgUri());
                    }
                    else
                    {
                        Toast.makeText(mContext, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case RESIZE_REQUEST_CODE :
                {
                    if (data != null)
                    {
                        showResizeImage(data);
                    }
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showEditHeadPicPopupWindow()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_head_image_popup, null, false);
        final PopupWindow popupWindow = new PopupWindow(view, //
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        popupWindow.setTouchable(true);
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        popupWindow.setAnimationStyle(Animation.ZORDER_BOTTOM);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM, 0, 0);

        view.findViewById(R.id.txt_gallery_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImgIntent = new Intent(Intent.ACTION_PICK, null);
                pickImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickImgIntent, IMAGE_REQUEST_CODE);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.txt_takePhoto_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasSDCard()) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImgUri());
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
                else
                {
                    Toast.makeText(mContext, "没有找到SD卡", Toast.LENGTH_SHORT).show();
                }
                popupWindow.dismiss();
            }
        });

    }
    private void resizeImage(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        //width : height
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //real values of the outputs
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    private void showResizeImage(Intent data)
    {
        Bundle extras = data.getExtras();
        if (extras != null)
        {
            Bitmap photo = (Bitmap) extras.get("data");
            img_headM.setImageBitmap(photo);
        }
    }
    private Uri getImgUri()
    {
        return  Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME));
    }
    private boolean hasSDCard()
    {
        String _state = Environment.getExternalStorageState();
        if (_state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private void showTips(String tips)
    {
        Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
    }

}
