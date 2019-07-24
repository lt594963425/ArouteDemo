package com.example.modulebase.data.source.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.modulebase.data.entity.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserType = new Property(1, Integer.class, "userType", false, "USER_TYPE");
        public final static Property Tel = new Property(2, String.class, "tel", false, "TEL");
        public final static Property Pwd = new Property(3, String.class, "pwd", false, "PWD");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property Age = new Property(5, String.class, "age", false, "AGE");
        public final static Property Sex = new Property(6, String.class, "sex", false, "SEX");
        public final static Property IdCard = new Property(7, String.class, "idCard", false, "ID_CARD");
        public final static Property NickName = new Property(8, String.class, "nickName", false, "NICK_NAME");
        public final static Property ImagePath = new Property(9, String.class, "imagePath", false, "IMAGE_PATH");
        public final static Property City = new Property(10, String.class, "city", false, "CITY");
        public final static Property Industry = new Property(11, String.class, "industry", false, "INDUSTRY");
        public final static Property Interest = new Property(12, String.class, "interest", false, "INTEREST");
        public final static Property Token = new Property(13, String.class, "token", false, "TOKEN");
        public final static Property UserId = new Property(14, String.class, "userId", false, "USER_ID");
        public final static Property IsLogin = new Property(15, Boolean.class, "isLogin", false, "IS_LOGIN");
        public final static Property Withdraw_pass = new Property(16, boolean.class, "withdraw_pass", false, "WITHDRAW_PASS");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_TYPE\" INTEGER," + // 1: userType
                "\"TEL\" TEXT," + // 2: tel
                "\"PWD\" TEXT," + // 3: pwd
                "\"NAME\" TEXT," + // 4: name
                "\"AGE\" TEXT," + // 5: age
                "\"SEX\" TEXT," + // 6: sex
                "\"ID_CARD\" TEXT," + // 7: idCard
                "\"NICK_NAME\" TEXT," + // 8: nickName
                "\"IMAGE_PATH\" TEXT," + // 9: imagePath
                "\"CITY\" TEXT," + // 10: city
                "\"INDUSTRY\" TEXT," + // 11: industry
                "\"INTEREST\" TEXT," + // 12: interest
                "\"TOKEN\" TEXT," + // 13: token
                "\"USER_ID\" TEXT," + // 14: userId
                "\"IS_LOGIN\" INTEGER," + // 15: isLogin
                "\"WITHDRAW_PASS\" INTEGER NOT NULL );"); // 16: withdraw_pass
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer userType = entity.getUserType();
        if (userType != null) {
            stmt.bindLong(2, userType);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(3, tel);
        }
 
        String pwd = entity.getPwd();
        if (pwd != null) {
            stmt.bindString(4, pwd);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(6, age);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String idCard = entity.getIdCard();
        if (idCard != null) {
            stmt.bindString(8, idCard);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(9, nickName);
        }
 
        String imagePath = entity.getImagePath();
        if (imagePath != null) {
            stmt.bindString(10, imagePath);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(11, city);
        }
 
        String industry = entity.getIndustry();
        if (industry != null) {
            stmt.bindString(12, industry);
        }
 
        String interest = entity.getInterest();
        if (interest != null) {
            stmt.bindString(13, interest);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(14, token);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(15, userId);
        }
 
        Boolean isLogin = entity.getIsLogin();
        if (isLogin != null) {
            stmt.bindLong(16, isLogin ? 1L: 0L);
        }
        stmt.bindLong(17, entity.getWithdraw_pass() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer userType = entity.getUserType();
        if (userType != null) {
            stmt.bindLong(2, userType);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(3, tel);
        }
 
        String pwd = entity.getPwd();
        if (pwd != null) {
            stmt.bindString(4, pwd);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(6, age);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String idCard = entity.getIdCard();
        if (idCard != null) {
            stmt.bindString(8, idCard);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(9, nickName);
        }
 
        String imagePath = entity.getImagePath();
        if (imagePath != null) {
            stmt.bindString(10, imagePath);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(11, city);
        }
 
        String industry = entity.getIndustry();
        if (industry != null) {
            stmt.bindString(12, industry);
        }
 
        String interest = entity.getInterest();
        if (interest != null) {
            stmt.bindString(13, interest);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(14, token);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(15, userId);
        }
 
        Boolean isLogin = entity.getIsLogin();
        if (isLogin != null) {
            stmt.bindLong(16, isLogin ? 1L: 0L);
        }
        stmt.bindLong(17, entity.getWithdraw_pass() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // userType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tel
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pwd
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // age
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sex
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // idCard
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // nickName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // imagePath
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // city
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // industry
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // interest
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // token
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // userId
            cursor.isNull(offset + 15) ? null : cursor.getShort(offset + 15) != 0, // isLogin
            cursor.getShort(offset + 16) != 0 // withdraw_pass
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTel(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPwd(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAge(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSex(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIdCard(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setNickName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setImagePath(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCity(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setIndustry(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setInterest(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setToken(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUserId(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setIsLogin(cursor.isNull(offset + 15) ? null : cursor.getShort(offset + 15) != 0);
        entity.setWithdraw_pass(cursor.getShort(offset + 16) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}