
import client.CloseUtil;
import dao.UserDAO;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MyChannel implements Runnable{
    private DataInputStream dis;
    private DataOutputStream dos;
    private Boolean flag = true;

    private String firstText ;
    private static UserDAO dao = new UserDAO();

    public MyChannel(Socket client){
        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            flag = false;
            CloseUtil.closeAll(dis,dos);
        }
    }

    private String receive(){
        String str = "";
        try {
            str = dis.readUTF();
        } catch (IOException e){
            flag = false;
            CloseUtil.closeAll(dis,dos);
            Server.list.remove(this);
        }
        return str;
    }

    private void send(String str){
        if (str!=null&&str.length()!=0){
            try {
                dos.writeUTF(str);
                dos.flush();
            } catch (IOException e) {
                flag = false;
                CloseUtil.closeAll(dos,dis);
                Server.list.remove(this);
            }
        }
    }

    //转发给其他客户端
    private void sendOther(){
        String str = this.receive();
        List<User> list = Server.list;
        String[] msg = str.split(",");
        //将当前发送的信息存入数据库
        if (msg.length == 2) {
            System.out.println(firstText + "(" + msg[0] + "):" + msg[1]);
            dao.save_msg(firstText, msg[1], msg[0]);
            for (User other : list) {
                if (other.getChannel() == this) {
                    continue; //不发送给自己
                }
                other.getChannel().send(firstText + "(" + msg[0] + "):" + msg[1]);//只发送给其他客户端
            }
        }
    }

    @Override
    public void run(){
        try {
            firstText = dis.readUTF();
            dao.motify_user(firstText, String.valueOf(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (flag){
            sendOther();
        }
    }
}
