package com.virtualmark.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

public interface FileManager {
	
	void openConnection() throws IOException;
	
	void closeConnection() throws IOException;
	
	String currentFolderPath() throws IOException;
	
	boolean openFolder(String name) throws IOException;
	
	boolean goToPath(String path) throws IOException;
	
	List<String> getFolders() throws IOException;
	
	List<String> getFiles() throws FileNotFoundException, IOException;
	
	boolean uploadFile(File file) throws IOException;
	
	boolean downloadFile(String source, String destination) throws IOException;
	
	boolean deleteFile(String name) throws IOException;
	
	boolean renameFile(String from, String to) throws IOException;
	
	boolean createFolder(String name) throws IOException;
	
	boolean deleteFolder(String name) throws IOException;
	
	boolean renameFolder(String from, String to) throws IOException;
	
	FTPFile[] getAllFiles() throws IOException;

	boolean isLive();
}
