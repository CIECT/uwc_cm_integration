package coza.opencollab.sakai.cm.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import coza.opencollab.sakai.cm.SISException;

public class File {
	private boolean ignoreFirstLine = true;
	private boolean hasMore = true;
	private BufferedReader reader;
	private String[] lineTokens;
	
	public File(String baseDirectory, String fileName) {
		try {
			reader = new BufferedReader(new FileReader(new java.io.File(baseDirectory, fileName + ".cm")));
			if(ignoreFirstLine){
				hasLine();
			}
		} catch (FileNotFoundException e) {
			throw new SISException(e);
		}
	}
	
	public boolean hasLine(){
		if(!hasMore) return false;
		String line;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new SISException(e);
		}
		if(line != null && line.trim().length() != 0){
			lineTokens = line.split(",");
			return true;
		}else{
			hasMore = false;
			return false;
		}
	}
	
	public String get(int index){
		String value = lineTokens[index].trim();
		if("null".equals(value)){
			return null;
		}
		return value;
	}
	
	public void close(){
		if(reader != null){
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
