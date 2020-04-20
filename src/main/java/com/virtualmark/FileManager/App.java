package com.virtualmark.FileManager;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException,Exception
    {
        FileManager fileManager = new FileManagerClass();
        
        fileManager.openConnection();
        
        System.out.println(fileManager.currentFolderPath());        
        System.out.println(fileManager.getFiles());
        System.out.println(fileManager.getFolders());
        
        fileManager.closeConnection();
    }
}
