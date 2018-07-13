package action;

import dao.Frienddao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by LSC333 on 2018/4/2.
 */
public class FriendAction {
    public boolean Add(String name_a,String name_b,String nickname) throws SQLException {
        //添加好友到数据库，添加成功则返回true
        Frienddao dao=new Frienddao();
        if(dao.addFriend(name_a, name_b, nickname)){
            return true;
        }else {
            return false;
        }
    }
    public boolean Delete(String name_a,String name_b) throws SQLException {
        //从数据库删除好友，删除成功则返回true
        Frienddao dao=new Frienddao();
        if(dao.delFriend(name_a, name_b)){
            return true;
        }else{
            return false;
        }
    }
    public List<String> getFriendList(String name) throws SQLException {
        //访问数据库，获得好友列表数据
        Frienddao dao=new Frienddao();
        List<String> FriendList=dao.getFriendList(name);
        return FriendList;
    }
}
