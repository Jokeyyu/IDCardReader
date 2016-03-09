package com.yishu.idcarder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/8.
 */
public class DataPushServer
{
    private ServerSocket serverSocket;
    private Socket socket = null;
    private boolean isServerStart;
    private static final int PORT_NUMBER = 3535;
    private BufferedWriter writer;
    private BufferedReader reader;
    private String ip;
    private InetAddress address;
    private int clientID = 0;
    private int _to = 1;
    private ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
    private ArrayList<Message> msgList = new ArrayList<Message>();
    private Message msg;


    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket(PORT_NUMBER);
            isServerStart = true;
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            System.out.println("Server " + ip + ":" + PORT_NUMBER + " is ready...");

            while (isServerStart)
            {
                socket = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ClientThread client = new ClientThread(clientID);
                clientList.add(client);
                clientID++;
                client.start();
            }

        }catch (Exception e){e.printStackTrace();}

    }
    private void startSendMessage(String data)
    {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                try
                {
                    if (isServerStart)
                    {
//                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        writer.write( data + " from server " + ip + "\n");
                        writer.flush();
//                socket.shutdownOutput();
                    }
                }catch (Exception e){e.printStackTrace();}
//            }
//        }).start();


    }
    private void startReceiveMessage()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
//                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    msg = new Message(_to);
                    String data = null;
//            if (reader.ready())
//            {
                    while ((data = reader.readLine()) != null)
                    {
                        System.out.println("received data == " + data + " ==from client " + clientID);
                        msg.setData(data);
                        msgList.add(msg);
                        for (Message msg : msgList)
                        {
//                    System.out.println(msg.getData() + " ");
//                    if (msg.get_to() == clientID)
//                    {
                            startSendMessage(msg.getData());
                            System.out.println("send message " + msg.getData());
//                        break;
//                    }
                        }
                    }
                    System.out.println(msg.getData() + "==>");
//            }

                }catch (Exception e){e.printStackTrace();}
            }
        }).start();

    }

    public static void main(String[] args)
    {
        DataPushServer mServer = new DataPushServer();
        mServer.startServer();
//        try
//        {
//            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
//            InetAddress address = InetAddress.getLocalHost();
//            String ip = address.getHostAddress();
//            System.out.println("Server " + ip + ":" + PORT_NUMBER + " is ready...");
//            Socket socket = null;
//            String data = null;
//            BufferedReader reader;
//            while (true)
//            {
//                socket = serverSocket.accept();
//                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                while (true)
//                {
//                    data = reader.readLine();
//                    System.out.println("data from client====> " + data);
//                }
//
//            }
//
//        }catch (Exception e){e.printStackTrace();}
    }

    public class Message
    {
        private int _from;
        private int _to;
        private String data;

        public Message(){}
        public Message(int _to)
        {
            this._to = _to;
        }
        public int get_from() {
            return _from;
        }

        public void set_from(int _from) {
            this._from = _from;
        }

        public int get_to() {
            return _to;
        }

        public void set_to(int _to) {
            this._to = _to;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    public class ClientThread extends Thread
    {
        private int clientID;
        public ClientThread(int clientID)
        {
            this.clientID = clientID;
        }

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }
        @Override
        public void run() {
            super.run();
            try
            {
                startReceiveMessage();
//                for (Message msg : msgList)
//                {
////                    System.out.println(msg.getData() + " ");
////                    if (msg.get_to() == clientID)
////                    {
//                        startSendMessage(msg.getData());
//                    System.out.println("send message");
////                        break;
////                    }
//                }

//                socket.close();
            }catch (Exception e){e.printStackTrace();}


        }
    }
}
