package it.polimi.bookshelf.data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class StorageHandler {

	private Context context;
	String about_content = "about_content";

	/**
	* Constructor
	* 
	* @param context
	*/
	public StorageHandler(Context context) {
		this.context = context;
	}

	/**
	* Method that writes the content to the file
	* 
	* @param fileName the name of the file
	* @param content the string content to write
	* @return boolean success
	*/
	public boolean writeFile(String fileName, String content, boolean isTemp) {

		File file = null;
		if (isTemp) {
			file = new File(this.context.getCacheDir(), fileName);
		} else {
			file = new File(this.context.getFilesDir(), fileName);
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	* Method that reads the content of a file
	* 
	* @param fileName the name of the file
	* @param isTemp if the file is temporary or not
	* @return content
	*/
	public String readfile(String fileName, boolean isTemp) {

		StringBuilder fileContent;
		try {
			File file = null;
			if (isTemp) {
				file = new File(this.context.getCacheDir(), fileName);
			} else {
				file = new File(this.context.getFilesDir(), fileName);
			}
            FileReader fR = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fR);
			fileContent = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				fileContent.append(line).append("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return fileContent.toString();
	}

	/**
	* Method that deletes the file
	* 
	* @param fileName the name of the file
	* @param isTemp if the file is temporary or not
	* @return boolean success
	*/
	public boolean deleteFile(String fileName, boolean isTemp) {

		File file = null;
		try {
			if (isTemp) {
				file = new File(this.context.getCacheDir(), fileName);
			} else {
				file = new File(this.context.getFilesDir(), fileName);
			}
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
