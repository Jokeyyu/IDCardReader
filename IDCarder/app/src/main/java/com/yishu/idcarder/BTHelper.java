package com.yishu.idcarder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.Set;

/**
 * Created by Administrator on 2016/1/25.
 */
public class BTHelper
{
    BluetoothAdapter mBTAdapter;
    private static final String TAG = "====BTHelper Logs====::";

    public BTHelper(BluetoothAdapter mBTAdapter)
    {
        this.mBTAdapter = mBTAdapter;
    }
    public boolean hasBTDevice()
    {
        if (mBTAdapter != null)
        {
            Log.d(TAG, "this phone has bluetooth device");
            return true;
        }
        else
        {
            Log.d(TAG, "this phone has NOT bluetooth device");
            return false;
        }
    }

    public boolean isBTDeviceEnable()
    {
        if (! mBTAdapter.isEnabled())
        {
            Log.d(TAG, "this phones' bluetooth device is NOT enable");
            return false;
        }
        else
        {
            Log.d(TAG, "this phones' bluetooth device is enable");
            return true;
        }
    }
    public Set<BluetoothDevice> getPairedBTDevice()
    {
        Set<BluetoothDevice> devices = mBTAdapter.getBondedDevices();
        if (devices.size() > 0)
        {
            for(BluetoothDevice device : devices)
            {
                Log.d(TAG, device.getAddress());
            }
//            return devices;
        }
        return devices;
    }
}
