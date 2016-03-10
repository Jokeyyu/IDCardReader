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
    private boolean isServerStart;
    private static final int PORT_NUMBER = 3535;
    private String ip;
    private InetAddress address;
    private ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
    private ArrayList<Message> msgList = new ArrayList<Message>();
//    private Message msg;


    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket(PORT_NUMBER);
            isServerStart = true;
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            int clientID = 1;
            System.out.println("Server " + ip + ":" + PORT_NUMBER + " is ready...");
            Socket socket = null;
            startSendMessage();
            while (isServerStart)
            {
                socket = serverSocket.accept();
//                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String phoneNumber = br.readLine();
//                String data = br.readLine();
//                System.out.println("phoneNumber==> " + phoneNumber);
//                System.out.println("data==> " + data);
//                ClientThread client = new ClientThread(socket, clientID);
                ClientThread client = new ClientThread(socket);
                clientList.add(client);
                clientID++;
                client.start();
            }

        }catch (Exception e){e.printStackTrace();}

    }
    private void startSendMessage()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    while (isServerStart)
                    {
                        if (msgList.size() > 0)
                        {
                            Message msg = msgList.get(0);
                            for (ClientThread client : clientList)
                            {
                                if (msg.get_to().equals(client.getPhoneNumber()))
                                {
                                    client.getWriter().write(msg.get_from() + "," + msg.get_to() + "," + msg.getName() + "," + msg.getGender() + "," +   //
                                            msg.getNation() + "," + msg.getBirth() + "," + msg.getIDCardAddress() + "," +  //
                                            msg.getIDCardNumber() + "," + msg.getDepartment() + "," + msg.getLifecycle() + "\n");
                                    client.getWriter().flush();
                                    System.out.println("send message " + msg.getName());
                                    break;
                                }
                            }
                            msgList.remove(0);
                        }
                        Thread.sleep(200);
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        }).start();
    }


    public static void main(String[] args)
    {
        DataPushServer mServer = new DataPushServer();
        mServer.startServer();
    }

    public class Message
    {
//        private int _from;
//        private int _to;
        private String _from;
        private String _to;
        private String name;
        private String gender;
        private String nation;
        private String birth;
        private String IDCardAddress;
        private String IDCardNumber;
        private String department;
        private String lifecycle;

        public Message(){}

        public String get_from() {
            return _from;
        }

        public void set_from(String _from) {
            this._from = _from;
        }

        public String get_to() {
            return _to;
        }

        public void set_to(String _to) {
            this._to = _to;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getIDCardAddress() {
            return IDCardAddress;
        }

        public void setIDCardAddress(String IDCardAddress) {
            this.IDCardAddress = IDCardAddress;
        }

        public String getIDCardNumber() {
            return IDCardNumber;
        }

        public void setIDCardNumber(String IDCardNumber) {
            this.IDCardNumber = IDCardNumber;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getLifecycle() {
            return lifecycle;
        }

        public void setLifecycle(String lifecycle) {
            this.lifecycle = lifecycle;
        }
    }

    //add a new thread to receive data from client and every new client gets one own thread
    public class ClientThread extends Thread
    {
        private int clientID;
        private BufferedWriter writer;
        private BufferedReader reader;
        private Socket socket;
        private String phoneNumber;
        public ClientThread(Socket socket, int clientID)
        {
            this.socket = socket;
            this.clientID = clientID;
            System.out.println("add one new client ==> " + clientID);
        }
        public ClientThread(Socket socket)
        {
            this.socket = socket;
        }

        public int getClientID() {
            return clientID;
        }

        public void setClientID(int clientID) {
            this.clientID = clientID;
        }

        public BufferedWriter getWriter() {
            return writer;
        }

        public void setWriter(BufferedWriter writer) {
            this.writer = writer;
        }

        public BufferedReader getReader() {
            return reader;
        }

        public void setReader(BufferedReader reader) {
            this.reader = reader;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void run() {
            super.run();
            try
            {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                msg = new Message(_to);
                while (isServerStart)
                 {
                    String dataFromClient = null;
                     if (reader.ready())
                    {
                        dataFromClient = reader.readLine();
                        Message msg = new Message();
                        String[] dataSplit = dataFromClient.split(",");
//                        int _from = clientID;
                        String _from = dataSplit[0];
                        phoneNumber = _from;
                        System.out.println("received data == " + dataFromClient + " ==from client " + phoneNumber);
                        String _to = dataSplit[1];
                        String name = dataSplit[2];
                        String gender = dataSplit[3];
                        String nation = dataSplit[4];
                        String birth = dataSplit[5];
                        String IDCardAddress = dataSplit[6];
                        String IDCardNumber = dataSplit[7];
                        String department = dataSplit[8];
                        String lifecycle = dataSplit[9];
                        msg.set_from(_from);
                        msg.set_to(_to);
                        msg.setName(name);
                        msg.setGender(gender);
                        msg.setNation(nation);
                        msg.setBirth(birth);
                        msg.setIDCardAddress(IDCardAddress);
                        msg.setIDCardNumber(IDCardNumber);
                        msg.setDepartment(department);
                        msg.setLifecycle(lifecycle);
                        msgList.add(msg);
//                        System.out.println(msg.getName() + "==>");
                    }
                     Thread.sleep(100);
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
}
