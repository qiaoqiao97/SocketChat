package client;

import dao.UserDAO;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client implements Runnable{

    private static String host;
    private static int port;
    private static String user;

    private static UserDAO dao = new UserDAO();
    private static String[] messages = new String[100];
    private static String[] users = new String[100];


    public static DataOutputStream dos;
    public static DataInputStream dis;
    public static Boolean flag = true;

    public static ChatControl chat;

    public Client(String host, int port, String user) {
        this.host = host;
        this.port = port;
        this.user = user;
    }

    @Override
    public void run(){
        System.out.println("first:"+host+","+port+","+user);

        Socket client = null;

        try {
            client = new Socket(host,port);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            dos.writeUTF(user);

            chat = new ChatControl();
            chat.setUser(user);
            chat.setUserList("UserList:\n");
            chat.setMyChat("Welcome to Chat!\n\n");

            messages = dao.get_msg();
            for (String msg : messages){
                if (msg!=null&&msg!=""){
                    chat.setMyChat(msg);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        while (client.isConnected()) {
            users = dao.get_user();
            chat.NullUserList();
            for (String Auser : users){
                if (Auser!=null&&Auser!=""){
                    chat.setUserList(Auser);
                }
            }
            System.out.println(">>>"+client.isConnected());
            String str = "";
            try {
                str = dis.readUTF();

                if (str != null&&str != ""){
                    chat.setMyChat(str+'\n');
                }else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void send(String str) {
        try {
            dos.writeUTF(str);
            dos.flush();//清空缓存
        } catch (IOException e) {
            flag = false;
            CloseUtil.closeAll(dos);
        }
    }

//    public void closeSystem(){
//        Platform.exit();
//        System.exit(0);
//    }
//
//    public void minimizeWindow(){
//        Index.getPrimaryStage().setIconified(true);
//    }
}
