package action;

import dao.Landdao;

import java.sql.SQLException;

/**
 * Created by LSC333 on 2018/4/3.
 */
public class LandAction {
    public String UserLand(String name, String password) throws SQLException {
        Landdao dao=new Landdao();
        return dao.userLanding(name, password);
    }
}
