package com.example.modulehome.entity;

import java.util.List;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/6/26 0026
 * Time: 下午 14:50
 */
public class DJISetverAreoist {

    /**
     * data : {"list":[{"aeroscopeID":"xxxx","latitude":22.640995,"listenerID":"xxxx","longitude":113.802819,"remarkInfo":"深圳机场 1 号","soc":"xx","statusCode":0,"timestamp":193001351339,"version":0}],"totalCount":0}
     * message : {"chinese":"成功","code":100000,"english":"Success"}
     * success : true
     */

    private DataBean data;
    private MessageBean message;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * list : [{"aeroscopeID":"xxxx","latitude":22.640995,"listenerID":"xxxx","longitude":113.802819,"remarkInfo":"深圳机场 1 号","soc":"xx","statusCode":0,"timestamp":193001351339,"version":0}]
         * totalCount : 0
         */

        private int totalCount;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * aeroscopeID : xxxx
             * latitude : 22.640995
             * listenerID : xxxx
             * longitude : 113.802819
             * remarkInfo : 深圳机场 1 号
             * soc : xx
             * statusCode : 0
             * timestamp : 193001351339
             * version : 0
             */

            private String aeroscopeID;
            private double latitude;
            private String listenerID;
            private double longitude;
            private String remarkInfo;
            private String soc;
            private int statusCode;
            private long timestamp;
            private int version;

            public String getAeroscopeID() {
                return aeroscopeID;
            }

            public void setAeroscopeID(String aeroscopeID) {
                this.aeroscopeID = aeroscopeID;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getListenerID() {
                return listenerID;
            }

            public void setListenerID(String listenerID) {
                this.listenerID = listenerID;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getRemarkInfo() {
                return remarkInfo;
            }

            public void setRemarkInfo(String remarkInfo) {
                this.remarkInfo = remarkInfo;
            }

            public String getSoc() {
                return soc;
            }

            public void setSoc(String soc) {
                this.soc = soc;
            }

            public int getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(int statusCode) {
                this.statusCode = statusCode;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "aeroscopeID='" + aeroscopeID + '\'' +
                        ", latitude=" + latitude +
                        ", listenerID='" + listenerID + '\'' +
                        ", longitude=" + longitude +
                        ", remarkInfo='" + remarkInfo + '\'' +
                        ", soc='" + soc + '\'' +
                        ", statusCode=" + statusCode +
                        ", timestamp=" + timestamp +
                        ", version=" + version +
                        '}';
            }
        }
    }

    public static class MessageBean {
        /**
         * chinese : 成功
         * code : 100000
         * english : Success
         */

        private String chinese;
        private int code;
        private String english;

        public String getChinese() {
            return chinese;
        }

        public void setChinese(String chinese) {
            this.chinese = chinese;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }
    }
}
