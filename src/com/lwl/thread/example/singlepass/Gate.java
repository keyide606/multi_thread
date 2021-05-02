package com.lwl.thread.example.singlepass;

/**
 * @author liwenlong 共享资源 临界值
 * @date 2021-05-01 22:49
 */
public class Gate {
    private int counter = 0;
    private String name = "Nobody";
    private String address = "NoWhere";

    public synchronized void pass(String name, String address) {
        this.name = name;
        this.address = address;
        counter++;
        verify();
    }

    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("*********BROKEN********" + toString());
        }
    }

    public String toString() {
        return "No." + counter + ":" + name + "," + address;
    }
}
