package action;

import dao.Userdao;
import model.User;

import java.sql.SQLException;

/**
 * Created by LSC333 on 2018/4/2.
 */
public class UserAction {
    public boolean Add_User(User user) throws SQLException {
        Userdao dao=new Userdao();
        if(dao.addUser(user)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean Del_User(String name) throws SQLException {
        Userdao dao=new Userdao();
        if(dao.delUser(name)){
            return true;
        }
        else{
            return false;
        }
    }
}
