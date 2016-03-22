package com.yishu.idcarder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/22.
 */
public class StreamTool {

    public static byte[] read(InputStream is) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) != -1)
        {
            outputStream.write(buffer, 0, length);
        }
        is.close();
        return outputStream.toByteArray();
    }
}
