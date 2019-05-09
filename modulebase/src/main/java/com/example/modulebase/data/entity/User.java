package com.example.modulebase.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Dian on 2018/12/23
 * 登录返回类
 */

@Entity
public class User implements Parcelable {
    @Id
    private Long id = 0L;
    private Integer userType = 0;
    private String tel = "";
    private String pwd = "";
    private String name = "";
    private String age = "0";
    private String sex = "1";
    private String idCard = "";
    private String nickName = "";
    private String imagePath = "";
    private String city = "";
    private String industry = "";
    private String interest = "";
    private String token = "";
    private String userId = "";
    private Boolean isLogin = false;
    private boolean withdraw_pass = false;


    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 2004173948)
    public User(Long id, Integer userType, String tel, String pwd, String name, String age, String sex, String idCard, String nickName, String imagePath, String city, String industry, String interest, String token, String userId, Boolean isLogin, boolean withdraw_pass) {
        this.id = id;
        this.userType = userType;
        this.tel = tel;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.idCard = idCard;
        this.nickName = nickName;
        this.imagePath = imagePath;
        this.city = city;
        this.industry = industry;
        this.interest = interest;
        this.token = token;
        this.userId = userId;
        this.isLogin = isLogin;
        this.withdraw_pass = withdraw_pass;
    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            userType = null;
        } else {
            userType = in.readInt();
        }
        tel = in.readString();
        pwd = in.readString();
        name = in.readString();
        age = in.readString();
        sex = in.readString();
        idCard = in.readString();
        nickName = in.readString();
        imagePath = in.readString();
        city = in.readString();
        industry = in.readString();
        interest = in.readString();
        token = in.readString();
        userId = in.readString();
        byte tmpIsLogin = in.readByte();
        isLogin = tmpIsLogin == 0 ? null : tmpIsLogin == 1;
        withdraw_pass = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (userType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userType);
        }
        dest.writeString(tel);
        dest.writeString(pwd);
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(sex);
        dest.writeString(idCard);
        dest.writeString(nickName);
        dest.writeString(imagePath);
        dest.writeString(city);
        dest.writeString(industry);
        dest.writeString(interest);
        dest.writeString(token);
        dest.writeString(userId);
        dest.writeByte((byte) (isLogin == null ? 0 : isLogin ? 1 : 2));
        dest.writeByte((byte) (withdraw_pass ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public boolean isWithdraw_pass() {
        return withdraw_pass;
    }

    public void setWithdraw_pass(boolean withdraw_pass) {
        this.withdraw_pass = withdraw_pass;
    }


    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public Boolean getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getWithdraw_pass() {
        return this.withdraw_pass;
    }


}
