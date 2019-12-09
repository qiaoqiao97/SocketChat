
import java.io.IOException;
//import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    //创建集合对象，存储每一个连接进来的客户端
    public static List<User> list = new ArrayList<User>();
    public static User user;
    public static int num = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("------------服务器端已开启------------");
        ServerSocket server = new ServerSocket(9001);

        while (true){
            Socket socket = server.accept();
            //接收一个线程，客户端加一
            num++;
            System.out.println("第"+num+"个客户端已连接...");

            //创建线程类的对象
            MyChannel channel = new MyChannel(socket);
            //添加到集合中
            user = new User(num,channel);
            list.add(user);

            //启动线程
            new Thread(channel).start();
        }
    }

}
