package com.example.modulebase.data.source.helper;

import com.android.utils.UIUtils;
import com.example.modulebase.data.source.db.DaoMaster;
import com.example.modulebase.data.source.db.DaoSession;
import com.example.modulebase.data.source.db.UserDao;


/**
 * $activityName
 * 数据库管理类
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class DBManager {
    public static String dbName = "paiwujie.db";
    private static DBManager mDBManager;
    private DaoSession mDaoWriteSession;
    private DaoSession mDaoReadSession;

    /**
     * 获取单例
     *
     * @return
     */
    public static DBManager getInstance() {
        if (mDBManager == null) {
            synchronized (DBManager.class) {
                if (mDBManager == null) {
                    mDBManager = new DBManager();
                }
            }
        }
        return mDBManager;
    }

    private DBManager() {
    }

    public void onCreate() {
        init();
    }

    public void init() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(UIUtils.getContext(), dbName);
        mDaoWriteSession = new DaoMaster(helper.getWritableDatabase()).newSession();
        mDaoReadSession = new DaoMaster(helper.getReadableDatabase()).newSession();
        //清空缓存
        mDaoWriteSession.clear();
        mDaoReadSession.clear();
    }

    //-----------------创建各种Dao-------------------
    public UserDao getWriteUserDao() {
        return mDaoWriteSession.getUserDao();
    }

    public UserDao getReadUserDao() {
        return mDaoReadSession.getUserDao();
    }
}
