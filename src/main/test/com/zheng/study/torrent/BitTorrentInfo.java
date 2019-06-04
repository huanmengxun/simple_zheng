package com.zheng.study.torrent;

import java.util.Arrays;
import java.util.List;

import org.jsoup.internal.StringUtil;

import lombok.Data;
@Data
public class BitTorrentInfo {
	public static List<String> keyList;
    static{
        String[] keys = {"announce", "announce-list", "creation date", "comment", "created by",
                "info", "length", "md5sum", "name", "piece length","pieces", "files", "path"};
        keyList = Arrays.asList(keys);
    }
 
    private String announce;
    private List<String> announceList;
    private long creationDate;
    private String comment;
    private String createBy;
    private TestTest.Info info;
 
    public BitTorrentInfo() {
    }
 
	public void setValue(String key, Object value) throws Exception {
        if(!keyList.contains(key)){
            throw new Exception("not contains this key: " + key);
        }else{
        	
            switch (key){
                case "announce":this.setAnnounce(value.toString());break;
                case "announce-list":this.getAnnounceList().add(value.toString());break;
                case "creation date":
                	if(StringUtil.isNumeric(value.toString())){
                		this.setCreationDate(Long.parseLong(value.toString()));
                	}else{
                		this.setCreationDate(0);
                	}
                	break;
                case "comment":this.setComment(value.toString());break;
                case "created by":this.setCreateBy(value.toString());break;
                case "length":
                    List<TestTest.Files> filesList1 = this.getInfo().getFiles();
                    if(filesList1 != null){
                    	TestTest.Files files = this.getInfo().getFiles().get(filesList1.size()-1);
                        files.setLength(Long.parseLong(value.toString()));
                    }else {
                        this.getInfo().setLength(Long.parseLong(value.toString()));
                    }
                    break;
                case "md5sum":
                    List<TestTest.Files> filesList2 = this.getInfo().getFiles();
                    if(filesList2 != null){
                    	TestTest.Files files = this.getInfo().getFiles().get(filesList2.size()-1);
                        files.setMd5sum(value.toString());
                    }else {
                        this.getInfo().setMd5sum(value.toString());
                    }
                    break;
                case "name":
                    this.getInfo().setName(value.toString());
                    break;
                case "piece length":
                    this.getInfo().setPiecesLength(Long.parseLong(value.toString()));
                    break;
                case "pieces":
                	if(StringUtil.isNumeric(value.toString())){
                		this.getInfo().setPieces(null);
                	}else{
                		this.getInfo().setPieces((byte[])value);
                	}
                    break;
                case "path":
                    List<TestTest.Files> filesList3 = this.getInfo().getFiles();
                    TestTest.Files files3 = filesList3.get(filesList3.size()-1);
                    files3.getPath().add(value.toString());
                    break;
            }
        }
    }    
}