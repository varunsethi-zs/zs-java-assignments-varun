package com.zopsmart.assignment1;

import java.io.File;

public class SystemInfo {

    public static void main(String []args)
    {
        File systemSpace= new File("/");

        System.out.println("Hello Varun");
        System.out.println("username = " + System.getProperty("user.name"));
        System.out.println("directory = "+System.getProperty("user.dir"));

        System.out.println("memory = " +Runtime.getRuntime().maxMemory());

        System.out.println("System Cpu/Cores = " + Runtime.getRuntime().availableProcessors());

        long totalCapacity = systemSpace.getTotalSpace();

        System.out.println("Total Disc size : " + totalCapacity);

        System.out.println("OS Build : " +System.getProperty("os.arch"));
        System.out.println("OS Version : " +System.getProperty("os.version"));


    }
}
