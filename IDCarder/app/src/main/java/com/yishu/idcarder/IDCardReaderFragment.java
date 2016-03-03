package com.yishu.idcarder;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otg.idcard.OTGReadCardAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/1/21.
 */
public class IDCardReaderFragment extends Fragment implements View.OnClickListener
{
//    private Button btn_read_IDCard_byBT;
//    private Button btn_read_IDcard_byOTG;
//    private Button btn_read_IDcard_byNFC;

    private BluetoothAdapter mBTAdapter;
    private BTHelper mBTHelper;
    private NFCActivity nfcHelper;
    private Set<BluetoothDevice> devices;
    private String[] device_names = {"BTxxxxx", "APPPPP"};
    private String[] device_address;
    private int i = 0;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private String macAddress = "";
    private Map devices_name_address;
    private ArrayList IPArrayList;
    MenuInflater inflater;
    private Intent nfcIntent;

    private  OTGReadCardAPI readCardAPI;

    private Context mContext;
    private ImageView img_head;
    private TextView txt_idcardName_value;
    private TextView txt_idcardGender_vlaue;
    private TextView txt_idcardNation_value;
    private TextView txt_idcardBirth_value;
    private TextView txt_idcardAddress_value;
    private TextView txt_idcardNumber_value;
    private TextView txt_idcardDepartment_value;
    private TextView txt_idcardLifecycle_value;
//    private TextView txt_select_readCardMethod;

    private int tt;
    private int readFlag = 0;// 1 for bluetooth, 2 for OTG, 3 for NFC
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idcard_reader, container, false);
        mContext = getActivity();

        img_head = (ImageView) view.findViewById(R.id.img_head);
        txt_idcardName_value = (TextView) view.findViewById(R.id.txt_idcardName_value);
        txt_idcardGender_vlaue = (TextView) view.findViewById(R.id.txt_idcardGender_value);
        txt_idcardNation_value = (TextView) view.findViewById(R.id.txt_idcardNation_value);
        txt_idcardBirth_value = (TextView) view.findViewById(R.id.txt_idcardBirth_value);
        txt_idcardAddress_value = (TextView) view.findViewById(R.id.txt_idcardAddress_value);
        txt_idcardNumber_value = (TextView) view.findViewById(R.id.txt_idcardNumber_value);
        txt_idcardDepartment_value = (TextView) view.findViewById(R.id.txt_idcardDepartment_value);
        txt_idcardLifecycle_value = (TextView) view.findViewById(R.id.txt_idcardLifecycle_value);

//        txt_select_readCardMethod = (TextView) view.findViewById(R.id.txt_select_readCardMethod);
//        registerForContextMenu(txt_select_readCardMethod);

//        btn_read_IDCard_byBT = (Button) view.findViewById(R.id.btn_readIDCard_byBT);
//        btn_read_IDcard_byOTG = (Button) view.findViewById(R.id.btn_readIDCard_byOTG);
//        btn_read_IDcard_byNFC = (Button) view.findViewById(R.id.btn_readIDCard_byNFC);
//        btn_read_IDCard_byBT.setOnClickListener(this);
//        btn_read_IDcard_byOTG.setOnClickListener(this);
//        btn_read_IDcard_byNFC.setOnClickListener(this);

        IPArrayList = new ArrayList();
        IPArrayList.add("103.21.119.78");
        IPArrayList.add("103.21.119.78");
        IPArrayList.add("103.21.119.78");
        readCardAPI = new OTGReadCardAPI(mContext, IPArrayList);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        mBTHelper = new BTHelper(mBTAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //蓝牙读取
//            case R.id.btn_readIDCard_byBT:
//            {
//                if (mBTHelper.hasBTDevice())
//                {
//                    if (mBTHelper.isBTDeviceEnable())
//                    {
//                        devices = mBTHelper.getPairedBTDevice();
//                        for(BluetoothDevice device : devices)
//                        {
//                            i ++;
//                        }
//                        device_names = new String[i];
//                        device_address = new String[i];
//                        devices_name_address = new HashMap();
//                        i = 0;
//                        for(BluetoothDevice device : devices)
//                        {
//                            device_names[i] = device.getName();
//                            device_address[i] = device.getAddress();
//                            devices_name_address.put(device_names[i], device_address[i]);
//                            i ++;
//                        }
//                        btn_read_IDCard_byBT.setVisibility(View.GONE);
//                        showAlertDialog();
//                    }
//                    else
//                    {
//                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                        startActivity(intent);
//                    }
//                }
//                else
//                {
////            showAlertDialog();
//                    Toast.makeText(getActivity(), "设备不支持蓝牙功能", Toast.LENGTH_SHORT).show();
//                }
//                Log.d("btn_IDCardReader: ", "===================Worked==================");
//                break;
//            }
            //NFC读取
            //OTG读取
        }


    }

    private void showAlertDialog()
    {
        alert = null;
//        devices_name_address = new HashMap();
//        devices_name_address.put("BTxxxxx", "male");
        builder = new AlertDialog.Builder(mContext);
        alert = builder.setTitle("蓝牙选择列表").setItems(device_names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                macAddress = devices_name_address.get(device_names[which]).toString();
                Log.v("macAddress:", macAddress);
                readCardAPI.setmac(macAddress);
                setText();
            }
        }).create();
        alert.show();
    }

    private void setText() {
        if (readFlag == 1)
        {
            tt = readCardAPI.BtReadCard(mBTAdapter);
        }
        if (readFlag == 2)
        {

        }
        if (readFlag == 3)
        {
            tt = readCardAPI.NfcReadCard(nfcIntent);
        }
        Log.e("=============For Test", " ReadCard TT=" + tt);
            if (tt == 2) {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示").setMessage("接收数据超时！")
                        .setPositiveButton("确定", null).show();
            }
            if (tt == 41) {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示").setMessage("读卡失败！")
                        .setPositiveButton("确定", null).show();
            }
            if (tt == 42) {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示").setMessage("没有找到服务器！")
                        .setPositiveButton("确定", null).show();
            }
            if (tt == 43) {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示").setMessage("服务器忙！")
                        .setPositiveButton("确定", null).show();
            }
            if (tt == 90) {
                Log.d("90=====", "successful");
                txt_idcardName_value.setText(readCardAPI.Name());
                txt_idcardGender_vlaue.setText(readCardAPI.SexL());
                txt_idcardNation_value.setText(readCardAPI.NationL());
                txt_idcardBirth_value.setText(readCardAPI.BornL());
                txt_idcardAddress_value.setText(readCardAPI.Address());
                txt_idcardNumber_value.setText(readCardAPI.CardNo());
                txt_idcardDepartment_value.setText(readCardAPI.Police());
                txt_idcardLifecycle_value.setText(readCardAPI.Activity());
                img_head.setImageBitmap(Bytes2Bimap(readCardAPI.GetImage()));
            }
        }

    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        inflater = new MenuInflater(mContext);
//        inflater.inflate(R.menu.context_menu_select_readcardmethod, menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        txt_select_readCardMethod.setVisibility(View.GONE);
//        switch (item.getItemId())
//        {
//            case R.id.menu_bt_readCard :
//                readFlag = 1;
//                btn_read_IDCard_byBT.setVisibility(View.VISIBLE);
//                break;
//            case R.id.menu_otg_readCard :
//                readFlag = 2;
//                btn_read_IDcard_byOTG.setVisibility(View.VISIBLE);
//                break;
//            case R.id.menu_nfc_readCard :
//                readFlag = 3;
//                Intent intent = new Intent(mContext, NFCActivity.class);
//                startActivity(intent);
//                Log.e("nfc=======", "in============");
//                btn_read_IDcard_byNFC.setVisibility(View.VISIBLE);
//                break;
//        }
//        return true;
//    }

}
