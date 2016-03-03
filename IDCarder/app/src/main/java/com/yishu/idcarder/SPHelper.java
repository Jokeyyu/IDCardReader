package com.yishu.idcarder;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
public class SPHelper
{
    private SharedPreferences sp;
    private Context mContext;
    private String TAG = "===SPHelper===";

    public SPHelper(){}
    public SPHelper(Context context)
    {
        this.mContext = context;
    }

    public void save(String username, String password)
    {
        sp = mContext.getSharedPreferences("idcardSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
        Log.e(TAG, "info has been written in idcardSP ");
    }

    public Map<String, String> readSP()
    {
        Map<String, String> data = new HashMap<String, String>();
        sp = mContext.getSharedPreferences("idcardSP", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        Log.e(TAG, "info has been read ==> " + data.get("username") + 1);
        if (data.get("username").equals(""))
        {
            Log.e(TAG, "info has been read ==> " + data.get("username") + 2);
        }
        return data ;
    }
    public String getUsername()
    {
        sp = mContext.getSharedPreferences("idcardSP", Context.MODE_PRIVATE);
        return sp.getString("username", "");
    }
}
