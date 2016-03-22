package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/18.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private static RequestQueue queue;
    private ImageView img_test;
    private TextView txt_html;
    private Bitmap image;
    private String html_content;
    private static final String TAG = "===TestActivity===";
    private static final String PIC_URL = "http://192.168.1.10:8080/EmployeePro/pic/tomcat.png";
    private static final String HTML_URL = "http://192.168.1.10:8080/EmployeePro/";
    private static final int MSG_LOAD_PICTURE = 0;
    private static final int MSG_LOAD_HTML = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mContext = getApplicationContext();
        bindViews();

        queue = Volley.newRequestQueue(mContext);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_send :
            {
                StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.e(TAG, s);
                            }
                        },
                        new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e(TAG, volleyError.getMessage(), volleyError);
                        }
                    });
                queue.add(stringRequest);
                break;
            }
            case R.id.btn_load :
            {
                try
                {

                    image = BitmapFactory.decodeByteArray(getImage(PIC_URL), 0, getImage(PIC_URL).length);
//                    upLoadImage(PIC_URL);
                    handler.sendEmptyMessage(MSG_LOAD_PICTURE);
//                    html_content = getHtml(HTML_URL);
//                    handler.sendEmptyMessage(MSG_LOAD_HTML);

                }catch (Exception e){e.printStackTrace();}

            }
        }
    }
    private void bindViews()
    {
        img_test = (ImageView) findViewById(R.id.img_test);
        txt_html = (TextView) findViewById(R.id.test_html);

        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_load).setOnClickListener(this);
    }

    private static byte[] getImage(String path) throws IOException
    {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200)
        {
            throw new RuntimeException("request failed...");
        }
        InputStream is = conn.getInputStream();
        byte[] bt = StreamTool.read(is);
        is.close();
        return bt;
    }
    private void upLoadImage(String path) throws IOException
    {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        OutputStream os = conn.getOutputStream();
        File img = new File(Environment.getExternalStorageDirectory(), "Marry0.jpg");
        FileInputStream fis = new FileInputStream(img);
        int length = 0;
        byte[] data = new byte[1024];
        while ((length = fis.read(data, 0, data.length)) != -1)
        {
            os.write(data, 0, length);

        }
        os.flush();
        os.close();
        fis.close();
        Log.e(TAG, "upload successful");

    }
    private static String getHtml(String path) throws IOException
    {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200)
        {
            InputStream is = conn.getInputStream();
            byte[] data = StreamTool.read(is);
            String html = new String(data, "UTF-8");
            return html;
        }
        return null;
    }
    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MSG_LOAD_PICTURE :
                {
                    img_test.setImageBitmap(image);
                    Log.e(TAG,"load picture successful");
                    break;
                }
                case MSG_LOAD_HTML :
                {
                    txt_html.setText(html_content);
                    Log.e(TAG, "load html successful");
                    break;
                }
            }
        }
    };
}
