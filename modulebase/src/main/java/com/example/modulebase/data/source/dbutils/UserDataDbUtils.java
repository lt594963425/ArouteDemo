package com.example.modulebase.data.source.dbutils;

import com.android.utils.LogUtil;
import com.data.source.db.UserDao;
import com.example.modulebase.data.entity.User;
import com.example.modulebase.data.source.helper.DBManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * $activityName
 * 用户数据库操作工具类
 *
 * @author LiuTao
 * @date 2018/12/19/019
 */

public class UserDataDbUtils {
    private static UserDataDbUtils dbUtils;

    /**
     * 获取单例
     *
     * @return
     */
    public static UserDataDbUtils getInstance() {
        if (dbUtils == null) {
            synchronized (UserDataDbUtils.class) {
                if (dbUtils == null) {
                    dbUtils = new UserDataDbUtils();
                }
            }
        }
        return dbUtils;
    }


    /**
     * 插入一条新数据
     *
     * @param user
     */
    public Long saveDataToDb(User user) {
      return DBManager.getInstance().getWriteUserDao().insertOrReplace(user);
    }

    /**
     * 删除一条数据
     *
     * @param user
     */
    public void deleteOneData(User user) {
        DBManager.getInstance().getWriteUserDao().delete(user);
    }

    /**
     * 查询所有数据
     */
    public List<User> quearyAllDataFromDb() {
        List<User> userList =DBManager.getInstance().getReadUserDao().loadAll();
        for (int i = 0; i < userList.size(); i++) {
            LogUtil.e("用户",userList.get(i).toString());
        }
        return userList;
    }

    public User quearyOneDataFromDb() {
        return DBManager.getInstance().getReadUserDao().queryBuilder().unique();
    }

    /**
     * 通过主键ID查询数据
     */
    public User quearyDataByIDFromDb(Long Id) throws Exception {
        User user;
        QueryBuilder<User> queryBuilder = DBManager.getInstance().getReadUserDao().queryBuilder();
        queryBuilder.where(UserDao.Properties.Id.eq(Id));
        user = queryBuilder.unique();
        if (user==null){
            user = new User();
        }
        return user;
    }

    /**
     * 修改一条数据
     */
    public void updateDataDb(User user) {
        DBManager.getInstance().getWriteUserDao().update(user);
    }

    /**
     * 删除用户基本数据
     */
    public void deleteUserData() {
        DBManager.getInstance().getWriteUserDao().deleteAll();
    }
}
