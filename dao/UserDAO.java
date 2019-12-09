import java.sql.*;

public class UserDAO {
    public boolean login_user(String name){
        Boolean result = false;
        String sql = "select name from users";
        try(Connection c = DAO.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("name").equals(name)){
                    result = true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public void save_msg(String name, String msg, String date){
        String sql = "insert into messages(`name`,`msg`,`date`) values (?,?,?)";
        try(Connection c = DAO.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,msg);
            ps.setString(3,date);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String[] get_msg(){
        String name,msg,date;
        String[] messagesList = new String[100];
        int i=0;
        String sql = "select name,msg,date from messages";
        try(Connection c = DAO.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()&&i<messagesList.length){
                name = rs.getString("name");
                msg = rs.getString("msg");
                date = rs.getString("date");
                messagesList[i] = name+"("+date+"):"+msg+"\n";
                i++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return messagesList;
    }

    public String[] get_user(){
        String name,status;
        String[] usersList = new String[100];
        int i=0;
        String sql = "select name,status from users";
        try(Connection c = DAO.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()&&i<usersList.length){
                name = rs.getString("name");
                status = rs.getString("status");
                usersList[i] = name+"("+status+")"+"\n";
                i++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usersList;
    }

    public void motify_user(String name,String a){
        String sql = "update users set status=? where name =?";
        try(Connection c = DAO.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, a);
            ps.setString(2,name);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
