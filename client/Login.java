
import dao.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML private TextField hostname;
    @FXML private TextField portText;
    @FXML private TextField username;
    @FXML private Button login;

    private UserDAO dao = new UserDAO();

    public Login() {
    }

    public void loginButtonAction(){
        String host = hostname.getText();
        int port = Integer.parseInt(portText.getText());
        String  user = username.getText();
        System.out.println(user);

        //验证数据库中是否存在该用户
        if (dao.login_user(user)){
            dao.motify_user(user, String.valueOf(true));
            Client client = new Client(host,port,user);
            try {
                URL resource = getClass().getResource("view/ChatView.fxml");
                FXMLLoader loader = new FXMLLoader();
                Parent root = (Pane)loader.load(resource);
                Scene scene = new Scene(root,820,550);
                Stage primaryStage = (Stage) login.getScene().getWindow();
                primaryStage.setTitle("Socket Chat:"+user);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Platform.exit();
                        dao.motify_user(user, String.valueOf(false));
                        System.exit(0);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(client).start();
        }else {
            System.out.println("用户不存在！");
            Platform.exit();
            System.exit(0);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        username.addEventFilter(KeyEvent.KEY_PRESSED,ke->{
            if (ke.getCode().equals(KeyCode.ENTER)) {
                loginButtonAction();
                ke.consume();
            }
        });
    }

//    public void closeSystem(){
//        Platform.exit();
//        System.exit(0);
//    }

//    public void minimizeWindow(){
//        Index.getPrimaryStage().setIconified(true);
//    }

}
