package client;
import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class ChatControl implements Initializable {
    @FXML private TextArea MessageList;
    @FXML private TextArea UserList;
    @FXML private TextArea messageBox;

    private static TextArea msgList ;
    private static TextArea userList ;

    private static String user;

    public ChatControl() {
    }

    public void sentBtn() {
        if (!getMessageBox().isEmpty()){
            //获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            String nowTime = sdf.format(new Date());
            System.out.println(nowTime);
            //发送给服务端
            Client.send(nowTime+","+getMessageBox());
            //在当前界面显示自己发送的信息
            setMyChat(user+"("+nowTime+"):"+getMessageBox()+'\n');
            messageBox.clear();
        }
    }
    public void setMyChat(String msglist) {
        msgList.appendText(msglist);
    }

    public void setUserList(String user){
        userList.appendText(user);
    }

    public void NullUserList(){
        userList.setText("");
        setUserList("UserList:\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        MessageList.setText("");
        UserList.setText("");
        msgList = MessageList;
        userList = UserList;
    }

    public String getMessageBox() {
        return messageBox.getText();
    }

    public static String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
