package com.zk.naming;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description
 * @date created on 2019/8/2
 */
public class TestIdMaker {

    public static void main(String[] args) throws Exception {
        IdMaker idMaker = new IdMaker("127.0.0.1:2181",  "/NameService/IdGen", "ID");
        try {
            idMaker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < 10; i++) {
                String id = idMaker.generateId(IdMaker.RemoveMethod.DELAY);
                System.out.println(id);
            }
        } catch (Exception ex){

        } finally {
            idMaker.stop();
        }
    }

}
