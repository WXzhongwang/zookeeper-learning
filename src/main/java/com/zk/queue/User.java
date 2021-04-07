package com.zk.queue;

import java.io.Serializable;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description User
 * @date created on 2019/8/6
 */
public class User implements Serializable {

    String name;
    String id;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}