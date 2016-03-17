package com.yishu.idcarder;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/3/17.
 */
public class test {
    public static void main(String[] args)
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
//            fos.write(img_head,0, length);
            fos.flush();
            fos.close();
            System.out.println("save pic successfully");
        }catch (Exception e){e.printStackTrace();}
    }
}
