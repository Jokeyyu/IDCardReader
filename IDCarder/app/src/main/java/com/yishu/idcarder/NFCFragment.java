package com.yishu.idcarder;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2016/2/18.
 */
public class NFCFragment extends Fragment
{
    private Button btn_nfc_reader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);

        btn_nfc_reader = (Button) view.findViewById(R.id.btn_nfc_reader);

        btn_nfc_reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NFCActivity.class);//.getApplicationContext()
                startActivity(intent);
            }
        });

        return view;
    }
}
