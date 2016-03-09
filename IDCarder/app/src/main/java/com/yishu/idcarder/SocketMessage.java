package com.yishu.idcarder;

/**
 * Created by Administrator on 2016/3/8.
 */
public class SocketMessage {

    public int to;//socketID，指发送给谁
    public int from;//socketID，指谁发送过来的
    public String msg;//消息内容
    public String time;//接收时间
    public MyServer.SocketThread thread;//socketThread下面有介绍
}
