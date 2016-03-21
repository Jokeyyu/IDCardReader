package com.yishu.idcarder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/3/18.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context mContext;
    private ImageView img_header;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mContext = getApplicationContext();
        bindViews();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_header_pickImg :
            {
//                Intent pickImgIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
//                pickImgIntent.addCategory(Intent.CATEGORY_OPENABLE);
                Intent pickImgIntent = new Intent(Intent.ACTION_PICK, null);

                pickImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                pickImgIntent.setType("image/*");
                startActivityForResult(pickImgIntent, IMAGE_REQUEST_CODE);
                break;
            }
            case R.id.btn_header_takePhoto :
            {
                if (isSDCardExisting())
                {
//                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
                else
                {
                    Toast.makeText(mContext, "没有找到SD卡", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
            {
                break;
            }
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
                    if (isSDCardExisting())
                    {
                        resizeImage(getImageUri());
                    }
                    else
                    {
                        Toast.makeText(mContext, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case RESIZE_REQUEST_CODE :
                {
                    if (data != null)
                    {
                        showResizeImg(data);
                    }
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bindViews()
    {
        img_header = (ImageView) findViewById(R.id.img_head_test);
        Button btn_header_pickImg = (Button) findViewById(R.id.btn_header_pickImg);
        Button btn_header_takePhoto = (Button) findViewById(R.id.btn_header_takePhoto);

        btn_header_pickImg.setOnClickListener(this);
        btn_header_takePhoto.setOnClickListener(this);

    }
    private boolean isSDCardExisting()
    {
        final String _state = Environment.getExternalStorageState();
        if (_state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else {
            return false;
        }
    }
    private void resizeImage(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");   //com.android.camera.action.
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    private void showResizeImg(Intent data)
    {

        Bundle extras = data.getExtras();
        if (extras != null)
        {
            Bitmap photo = (Bitmap) extras.get("data");
//            Bitmap photo = extras.getParcelable("data");
//            Drawable drawable = new BitmapDrawable(photo);
            img_header.setImageBitmap(photo);
//            img_header.setImageURI(getImageUri());
        }
    }
    private Uri getImageUri()
    {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME));
    }
}
