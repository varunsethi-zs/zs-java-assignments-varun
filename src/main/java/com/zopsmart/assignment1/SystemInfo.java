package com.zopsmart.assignment1;

import java.io.File;

/**
 * SystemInfo class for displaying the System information
 */
public class SystemInfo {

    public static void main(String[] args) {
        File systemSpace = new File("/");
        System.out.println("Hello Varun");

        /** system.get Property function to get username */
        String user = System.getProperty("user.name");
        System.out.println("username = " + user);

        /** system.get Property function to get user directory */
        String directory = System.getProperty("user.dir");
        System.out.println("directory = " + directory);

        /** Runtime.getRuntime().maxMemory function to get memory of system */
        long memory = Runtime.getRuntime().maxMemory() / 1024 / 1024 / 1024;
        System.out.println("memory = " + memory + "gb");

        /** Runtime.getRuntime().availableProcessors function to get Cpu Cores */
        int cpuCores = Runtime.getRuntime().availableProcessors();
        System.out.println("System Cpu/Cores = " + cpuCores);

        /** systemSpace.getTotalSpace function to get total disc space of system */
        long totalCapacity = systemSpace.getTotalSpace() / 1024 / 1024 / 1024;
        System.out.println("Total Disc size : " + totalCapacity + "gb");

        /** system.get Property function to os build */
        String osBuild = System.getProperty("os.arch");
        System.out.println("OS Build : " + osBuild);

        /** system.get Property function to Os version */
        String osVersion = System.getProperty("os.version");
        System.out.println("OS Version : " + osVersion);
    }
}
