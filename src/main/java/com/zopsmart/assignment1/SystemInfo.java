package com.zopsmart.assignment1;

import java.io.File;

public class SystemInfo {

    public static void main(String []args)
    {
        File systemSpace= new File("/");

        System.out.println("Hello Varun");

        /** system.get Property function to get username  and user directory */

        String user =System.getProperty("user.name");
        System.out.println("username = " + user);
        String directory=System.getProperty("user.dir");
        System.out.println("directory = "+directory);

        /** Runtime.getRuntime().maxMemory function to get memory of system */

        long memory =Runtime.getRuntime().maxMemory()/1024/1024/1024;
        System.out.println("memory = " +memory+"gb");

        /** Runtime.getRuntime().availableProcessors function to get Cpu Cores */
        int Cores=Runtime.getRuntime().availableProcessors();
        System.out.println("System Cpu/Cores = " + Cores);

        /** systemSpace.getTotalSpace function  to get total disc space of system */

        long totalCapacity = systemSpace.getTotalSpace()/1024/1024/1024;
        System.out.println("Total Disc size : " + totalCapacity+"gb");

        /** system.get Property function to os build   and os version */

        String osbuild =System.getProperty("os.arch");
        System.out.println("OS Build : " +osbuild);
        String osversion =System.getProperty("os.version");
        System.out.println("OS Version : " +osversion);

    }
}
