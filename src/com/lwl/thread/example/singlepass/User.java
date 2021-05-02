package com.lwl.thread.example.singlepass;


/**
 * @author liwenlong
 * @date 2021-05-01 23:04
 */
public class User extends Thread {

    private String name;
    private String address;

    private final Gate gate;

    public User(String name, String address, Gate gate) {
        this.name = name;
        this.address = address;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(name + "begin");
        while (true) {
            this.gate.pass(name, address);
        }
    }
}
