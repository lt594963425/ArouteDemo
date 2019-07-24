package com.example.modulebase.data.entity;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/7/22 0022
 * Time: 下午 17:11
 */
public class Translation {

    /**
     * content : {"ciba_out":"","ciba_use":"来自机器翻译。","err_no":0,"from":"en-EU","out":"你好世界","to":"zh-CN","vendor":"tencent"}
     * status : 1
     */

    private ContentBean content;
    private int status;

    public Translation() {
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ContentBean {
        /**
         * ciba_out :
         * ciba_use : 来自机器翻译。
         * err_no : 0
         * from : en-EU
         * out : 你好世界
         * to : zh-CN
         * vendor : tencent
         */

        private String ciba_out;
        private String ciba_use;
        private int err_no;
        private String from;
        private String out;
        private String to;
        private String vendor;

        public String getCiba_out() {
            return ciba_out;
        }

        public void setCiba_out(String ciba_out) {
            this.ciba_out = ciba_out;
        }

        public String getCiba_use() {
            return ciba_use;
        }

        public void setCiba_use(String ciba_use) {
            this.ciba_use = ciba_use;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "ciba_out='" + ciba_out + '\'' +
                    ", ciba_use='" + ciba_use + '\'' +
                    ", err_no=" + err_no +
                    ", from='" + from + '\'' +
                    ", out='" + out + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    '}';
        }
    }

}
