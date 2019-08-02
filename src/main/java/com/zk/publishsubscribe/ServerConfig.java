package com.zk.publishsubscribe;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 配置信息，每个服务器都有的配置信息（按理，保持一致）
 * @date created on 2019/8/2
 */
public class ServerConfig {

    private String dbUrl;
    private String dbPwd;
    private String dbUser;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public String toString() {
        return "ServerConfig [dbUrl=" + dbUrl + ", dbPwd=" + dbPwd
                + ", dbUser=" + dbUser + "]";
    }

}
