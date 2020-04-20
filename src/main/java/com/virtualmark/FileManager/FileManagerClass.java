package com.virtualmark.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FileManagerClass implements FileManager{
	
	private FTPClient ftp;
	
	public FileManagerClass() {
	}

	@Override
	public void openConnection() throws IOException {
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(FTPConfig.HOST, FTPConfig.PORT);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(FTPConfig.USER, FTPConfig.PASSWORD);
	}

	@Override
	public void closeConnection() throws IOException {
		ftp.disconnect();	
	}
	
	@Override
	public String currentFolderPath() throws IOException {
		return ftp.printWorkingDirectory();
	}

	@Override
	public boolean openFolder(String name) throws IOException {
		return ftp.changeWorkingDirectory(ftp.printWorkingDirectory() + "/" + name);
	}

	@Override
	public boolean goToPath(String path) throws IOException {
		return ftp.changeWorkingDirectory(path);		
	}

	@Override
	public List<String> getFolders() throws IOException {
		FTPFile[] files = ftp.listDirectories();
		return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
	}

	@Override
	public List<String> getFiles() throws IOException {
		FTPFile[] files = ftp.listFiles();
		return Arrays.stream(files).filter(f-> f.isFile()).map(FTPFile::getName).collect(Collectors.toList());
	}

	@Override
	public boolean uploadFile(File file) throws FileNotFoundException, IOException {
		return ftp.storeFile(file.getName(), new FileInputStream(file));
	}

	@Override
	public boolean downloadFile(String source, String destination) throws IOException {
		FileOutputStream out = new FileOutputStream(destination);
        boolean status = ftp.retrieveFile(source, out);
        out.close();	
        return status;
	}

	@Override
	public boolean deleteFile(String name) throws IOException {
		return ftp.deleteFile(ftp.printWorkingDirectory() + "/" + name);
	}

	@Override
	public boolean renameFile(String from, String to) throws IOException {
		return ftp.rename(from, to);
	}

	@Override
	public boolean createFolder(String name) throws IOException {
		return ftp.makeDirectory(name);		
	}

	@Override
	public boolean deleteFolder(String name) throws IOException {
		return deleteFile(name);	
	}

	@Override
	public boolean renameFolder(String from, String to) throws IOException {
	  return renameFile(from, to);		
	}

	@Override
	public FTPFile[] getAllFiles() throws IOException {
		return ftp.listFiles();
	}
	
	@Override
	public boolean isLive() {
		return ftp.isConnected();	
	}

}
