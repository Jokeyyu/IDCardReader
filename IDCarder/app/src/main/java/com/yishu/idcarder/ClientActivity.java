package com.yishu.idcarder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ClientActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private Button btn_send;
    ImageView img_test;
    private ServerSocket serverSocket;
    private Socket socket;
    private InetAddress address;
    private String ip;
    private BufferedWriter writer;
    private BufferedReader reader;
    DataOutputStream dataOutput;
    DataInputStream dataInput;
    private static final int PORT = 3535;
    private static final String TAG = "==ClientActivity===";
    private static final String tag = "test";
    private String msgFrom = "12345678900";
    private String msgTo = "12345678901";
    private String name = "黄三";
    private String gender = "男";
    private String nation = "汉";
    private String birth = "19800808";
    private String IDCardAddress = "南京市雨花区软件大道180号大数据产业基地8栋409";
    private String IDCardNumber = "156321477556214592";
    private String department = "南京市公安局";
    private String lifecycle = "20100305-20200305";
    private byte[] img_head ;
    private int length = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        bindViews();
//        sendMessage();
//        sendPicture();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.btn_send :
//            {
//                try
//                {
//                    writer.write(msgFrom + "," + msgTo + "," + name + "," + gender + "," +   //
//                            nation + "," + birth + "," + IDCardAddress + "," +  //
//                            IDCardNumber + "," + department + "," + lifecycle  + "\n");
////                    writer.e
//                    writer.flush();
//                }catch (Exception e){e.printStackTrace();}
//            }
        }
    }

    private void bindViews()
    {
//        btn_send = (Button) findViewById(R.id.btn_send);
//        img_test = (ImageView) findViewById(R.id.testPIC);

//        btn_send.setOnClickListener(this);
    }

    private void sendMessage()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    socket = new Socket("192.168.1.49", PORT);
                    address = InetAddress.getLocalHost();
                    ip = address.getHostAddress();

                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer.write(msgFrom + "," + msgTo + "," + name + "," + gender + "," +   //
                            nation + "," + birth + "," + IDCardAddress + "," +  //
                            IDCardNumber + "," + department + "," + lifecycle + "," + tag + "\n");
                    writer.flush();
                    if (reader.ready())
                    {
                        String data = null;
                        while (true)
                        {
                            data = reader.readLine();
                            Log.e(TAG, "info from server ==>" + data);
                        }
                    }


                }catch (Exception e){e.printStackTrace();}
            }
        }).start();
    }
    private void sendPicture()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    socket = new Socket("192.168.1.49", PORT);
                    dataOutput = new DataOutputStream(socket.getOutputStream());
                    dataInput = new DataInputStream(socket.getInputStream());
                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/tempIDCard/img_head.jpg");
                    FileInputStream fileInput = new FileInputStream(file);
                    img_head = new byte[1024];
                    int length = 0;
                    while ((length = fileInput.read(img_head, 0, img_head.length)) > 0)
                    {
                        dataOutput.write(img_head, 0, length);
                        Log.e(TAG, "info from server ==>1 " + length);
                    }
                    Log.e(TAG, "info from server ==>2 " + length);
                    dataOutput.flush();
//                    img_head = new byte[41000];
                    length = 0;
                    while ((length = dataInput.read(img_head, 0, img_head.length)) > 0) {

                    }
//                    img_test.setImageBitmap();
//                    writeFile();
                }catch (Exception e){e.printStackTrace();}

            }
        }).start();
    }

    public void writeFile()
    {
        try
        {
            File file = new File("D:/tempIDCard");
            if (!file.exists())
            {
                file.mkdirs();
                System.out.println("dir is created");
            }
            File jpg = new File(file, "1.jpg");
            if (!jpg.exists())
            {
                jpg.createNewFile();
            }
//                Log.e(TAG,"path ==> " + Environment.getExternalStorageDirectory());
            FileOutputStream fos = new FileOutputStream(jpg);
//            int length = 0;
//            while ((length = dataInput.read(img_head, 0, img_head.length)) > 0)
//            {
//                fos.write(img_head,0, length);
//                fos.flush();
//            }
//            fos.write(img_head,0, img_head.length);
            fos.flush();
            fos.close();
            System.out.println("save pic successfully");
        }catch (Exception e){e.printStackTrace();}
    }
    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
    private Map<String, Map<String, Object>> testHashMap()
    {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Map<String, Object>> datas = new HashMap<String, Map<String, Object>>();
        String key = "key";
        for (int i = 0; i < 1000; i ++)
        {
            data.put(key + i, i);
        }
        datas.put("key1", data);
        return datas;
    }
}
