package com.yishu.idcarder;

import android.app.Activity;
//import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.os.Parcelable;
//import android.support.v7.app.AppCompatActivity;
//import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otg.idcard.OTGReadCardAPI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/26.
 */
public class NFCActivity extends AppCompatActivity
{
    private NfcAdapter nfcAdapter = null;
    private Context mContext;
    private Intent nfcIntent = null;
    private OTGReadCardAPI readCardAPI;
//    private IDCardInfo idCardInfo;
    private ArrayList IPArrayList;
//    private TextView txt_select_readCardMethod;
    private TextView txt_nfc_tips;
//    private ImageView img_backNFC;
    private ImageView img_home;
    private CheckBox push_data;

    private SQLiteDatabaseUtils dbUtils;
    private MyDBHelper myDBHelper;
    private Users user;
    private SPHelper spHelper;

    private ImageView img_head;
    private TextView txt_idcardName_value;
    private TextView txt_idcardGender_vlaue;
    private TextView txt_idcardNation_value;
    private TextView txt_idcardBirth_value;
    private TextView txt_idcardAddress_value;
    private TextView txt_idcardNumber_value;
    private TextView txt_idcardDepartment_value;
    private TextView txt_idcardLifecycle_value;

    private EditText edit_usernameOrPhoneNumber_dataPush;


//    private ServerSocket serverSocket;
    private Socket socket = null;
    private BufferedReader reader;
    private BufferedWriter writer;
    private static final String SERVER_IP = "192.168.1.32";
    private static final int PORT = 3535;


    private static final String TAG = "=====NFCActivity======";
    private long exitTime = 0;

    private IntentFilter tagDetected = null;
    private PendingIntent pi = null;
    private String[][] mTechLists;
    public static final int MESSAGE_VALID_NFCBUTTON=16;
    public static NFCActivity instance = null;
    private long time_use;


//    public NFCActivity(Context context, OTGReadCardAPI readCardAPI)
//    {
//        this.context = context;
//        this.readCardAPI = readCardAPI;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_nfc);
        setContentView(R.layout.fragment_idcard_reader);
        mContext = getApplicationContext();
        instance = this;
        spHelper = new SPHelper(mContext);

        myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
        dbUtils = new SQLiteDatabaseUtils(myDBHelper);


//        txt_select_readCardMethod = (TextView) findViewById(R.id.txt_select_readCardMethod);
//        txt_select_readCardMethod.setText("您在正在NFC读卡页面");
        img_head = (ImageView) findViewById(R.id.img_head);
        txt_idcardName_value = (TextView) findViewById(R.id.txt_idcardName_value);
        txt_idcardGender_vlaue = (TextView) findViewById(R.id.txt_idcardGender_value);
        txt_idcardNation_value = (TextView) findViewById(R.id.txt_idcardNation_value);
        txt_idcardBirth_value = (TextView) findViewById(R.id.txt_idcardBirth_value);
        txt_idcardAddress_value = (TextView) findViewById(R.id.txt_idcardAddress_value);
        txt_idcardNumber_value = (TextView) findViewById(R.id.txt_idcardNumber_value);
        txt_idcardDepartment_value = (TextView) findViewById(R.id.txt_idcardDepartment_value);
        txt_idcardLifecycle_value = (TextView) findViewById(R.id.txt_idcardLifecycle_value);
        push_data = (CheckBox) findViewById(R.id.checkbox_dataPush);
        edit_usernameOrPhoneNumber_dataPush = (EditText) findViewById(R.id.edit_usernameOrPhoneNumber_dataPush);


        txt_nfc_tips = (TextView) findViewById(R.id.txt_nfc_tips);
        push_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    edit_usernameOrPhoneNumber_dataPush.setVisibility(View.VISIBLE);
                }
                else
                {
                    edit_usernameOrPhoneNumber_dataPush.setVisibility(View.GONE);
                }
            }
        });
//        img_backNFC = (ImageView) findViewById(R.id.img_backNFC);

//        img_backNFC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        img_home = (ImageView) findViewById(R.id.img_home);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        IPArrayList = new ArrayList();
        IPArrayList.add("103.21.119.78");
        IPArrayList.add("103.21.119.78");
        IPArrayList.add("103.21.119.78");
//        IPArrayList.add("192.168.1.148");
//        IPArrayList.add("192.168.1.148");
//        IPArrayList.add("192.168.1.148");

        readCardAPI = new OTGReadCardAPI(mContext, IPArrayList);

        nfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
        testNFC();
        init_NFC();
//        cleanText();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        time_use = getTime();
        nfcIntent = intent;
//        Toast.makeText(mContext, "读卡中，请不要移动身份证...", Toast.LENGTH_LONG).show();
//        txt_select_readCardMethod.setText("000");
        user = new Users();
        user = dbUtils.find(spHelper.getUsername());
        if (user.getMoney() >= 1)
        {
            txt_nfc_tips.setVisibility(View.VISIBLE);
            cleanText();
            mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_NFCBUTTON, 0);
        }
        else
        {
            Toast.makeText(mContext,"余额不足，请充值", Toast.LENGTH_LONG).show();
        }

//        txt_idcardName_value.setText("");
//        txt_idcardGender_vlaue.setText("");
//        txt_idcardNation_value.setText("");
////        txt_idcardBirth_value.setText(readCardAPI.BornL());
//        txt_idcardBirth_value.setText("");
//        txt_idcardAddress_value.setText("");
//        txt_idcardNumber_value.setText("");
//        txt_idcardDepartment_value.setText("");
//        txt_idcardLifecycle_value.setText("");
//        img_head.setImageBitmap(null);


    }

    @Override
    protected void onResume() {
        super.onResume();
        testNFC();
        cleanText();
        if(nfcAdapter != null && nfcAdapter.isEnabled())
        {
            startNFC_Listener();
            Log.e(TAG, "onResume()");
        }
//        Log.e(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter != null && nfcAdapter.isEnabled())
        {
            stopNFC_Listener();
            Log.e(TAG, "onPause()");
        }
//        Log.e(TAG, "onPause()");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (System.currentTimeMillis() - exitTime > 2000)
            {
                Toast.makeText(mContext, "再按一下退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void testNFC()
    {
        if (nfcAdapter == null)
        {
            Toast.makeText(mContext, "设备不支持NFC功能！",Toast.LENGTH_LONG).show();
//            finish();
            return;
        }
        if (!nfcAdapter.isEnabled())
        {
            Toast.makeText(mContext, "NFC功能未启用，请先启用！", Toast.LENGTH_LONG).show();
//            finish();
            return;
        }
//        else
//            init_NFC();
//            Toast.makeText(mContext, "NFC启用！", Toast.LENGTH_LONG).show();
    }
    private void init_NFC() {
//		nfcIntent = new Intent(getApplicationContext(), interfaceActivity.class)
//				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
//		tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);//.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][] { new String[] { NfcB.class.getName() } };

    }

    private void processIntent(Intent intent)
    {
//        idCardInfo = new IDCardInfo();
        int tt = readCardAPI.NfcReadCard(intent);
        Log.e(TAG, intent.getAction() + " " + tt);
        if (tt == 2) {
            Toast.makeText(mContext, "接收数据超时！",Toast.LENGTH_LONG).show();
//            new AlertDialog.Builder(this)
//                    .setTitle("提示").setMessage("接收数据超时！")
//                    .setPositiveButton("确定", null).show();
        }
        if (tt == 41) {
            Toast.makeText(mContext, "读卡失败！",Toast.LENGTH_LONG).show();
//            new AlertDialog.Builder(this)
//                    .setTitle("提示").setMessage("读卡失败！")
//                    .setPositiveButton("确定", null).show();
        }
        if (tt == 42) {
            Toast.makeText(mContext, "没有找到服务器！",Toast.LENGTH_LONG).show();
//            new AlertDialog.Builder(this)
//                    .setTitle("提示").setMessage("没有找到服务器！")
//                    .setPositiveButton("确定", null).show();
        }
        if (tt == 43) {
            Toast.makeText(mContext, "服务器忙！",Toast.LENGTH_LONG).show();
//            new AlertDialog.Builder(this)
//                    .setTitle("提示").setMessage("服务器忙！")
//                    .setPositiveButton("确定", null).show();
        }
        if (tt == 90) {

//            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
//                Log.e(TAG, intent.getAction());
//            }
//            Log.e(TAG, readCardAPI.Name() + 11111);

            txt_idcardName_value.setText(readCardAPI.Name());
            txt_idcardGender_vlaue.setText(readCardAPI.SexL());
            txt_idcardNation_value.setText(readCardAPI.NationL());
//        txt_idcardBirth_value.setText(readCardAPI.BornL());
            txt_idcardBirth_value.setText(readCardAPI.BornL());
            txt_idcardAddress_value.setText(readCardAPI.Address());
            txt_idcardNumber_value.setText(readCardAPI.CardNo());
            txt_idcardDepartment_value.setText(readCardAPI.Police());
            txt_idcardLifecycle_value.setText(readCardAPI.Activity());
            img_head.setImageBitmap(Bytes2Bimap(readCardAPI.GetImage()));

            time_use = System.currentTimeMillis() - time_use;

//            myDBHelper = new MyDBHelper(mContext, "idCardReader.db", null, 1);
//            dbUtils = new SQLiteDatabaseUtils(myDBHelper);

//            Log.e(TAG, spHelper.getUsername());
            dbUtils.reduceMoney(spHelper.getUsername());
            Toast.makeText(mContext, "刷卡成功，消费1元 " + time_use, Toast.LENGTH_LONG).show();

            if (push_data.isChecked())
            {
                Log.e(TAG, "isChecked");
                listenMessage();
//                Intent intenttest = new Intent(mContext, ClientActivity.class);
//                startActivity(intenttest);

            }
            else
            {
                Log.e(TAG, "is NOT Checked");
            }

            Log.e(TAG, readCardAPI.Name());
        }
        txt_nfc_tips.setVisibility(View.GONE);


    }
    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what)
            {
                case MESSAGE_VALID_NFCBUTTON:
                    processIntent(nfcIntent);
            }
        }
    };

    private void startNFC_Listener()
    {
        nfcAdapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected}, mTechLists);
    }

    private void stopNFC_Listener() {
        nfcAdapter.disableForegroundDispatch(this);

    }

    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
    private void cleanText()
    {
        txt_idcardName_value.setText("");
        txt_idcardGender_vlaue.setText("");
        txt_idcardNation_value.setText("");
        txt_idcardBirth_value.setText("");
        txt_idcardAddress_value.setText("");
        txt_idcardNumber_value.setText("");
        txt_idcardDepartment_value.setText("");
        txt_idcardLifecycle_value.setText("");
//        img_head.setImageBitmap(null);
        img_head.setImageResource(R.drawable.head1);
    }

    private long getTime()
    {
        long time;
        time = System.currentTimeMillis();
        return time;
    }

    private void listenMessage()
    {
        try
        {
            socket = new Socket(SERVER_IP, PORT);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.write(readCardAPI.Name() + readCardAPI.CardNo() + "\n");
            writer.flush();
            String data = null;
//            if (reader.ready())
//            {
                data = reader.readLine();
                Log.e(TAG, data + " from server");
//            }

        }catch (Exception e){e.printStackTrace();}

    }
}
