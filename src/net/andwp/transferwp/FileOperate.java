package net.andwp.transferwp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

/**
 * 文件的操作类</br> 
 * Operate File
 * @author andwp
 * 
 */
public class FileOperate {

	private File file;
	/** 
	 * @param 相对路径
	 */
	public FileOperate(String fileName) {
		this.file =  new File(fileName);
	}

	public FileOperate(URI uri) {
		this.file = new File(uri);
	}
	
	/**
	 *  保存指定内容到文件
	 */
	public void saveFile(String content) throws IOException{
		FileWriter writer = new FileWriter(file, false);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	/**
	 * 以字符串的形式读取文件的内容
	 * 
	 */
	public String getFileContentAsStr() throws IOException{ 
		String ret = "";
		BufferedReader reader = null;
		String  tempStr = null;
		try{
			reader = new BufferedReader( new FileReader(file));  
		    // 分行读取文件
		    while ((tempStr = reader.readLine()) != null) { 
		    	ret +=  new StringBuilder(tempStr + "\n");
		    }
		    
            }catch(Exception ex){
            	ex.printStackTrace();
            }finally{
            	if(reader != null){
            		reader.close();
            	} 
            }
		return ret;
	}
}
