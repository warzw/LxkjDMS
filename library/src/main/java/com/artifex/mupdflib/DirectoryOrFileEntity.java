package com.artifex.mupdflib;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by dell on 2018/6/12.
 */

public  class DirectoryOrFileEntity {

    private String type; //是文件还是文件夹
    private int readTag;  // 读过标记
    private String path;  //绝对路径
    private String name;  // 名称
    private int image;   //对应图像
    private int page = 1;
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public String getTime() {
        if (name.contains("lxkj")) { //是不是有标注
            String[] names = name.split("\\.");
            if (names.length>1) {
                String strTime = names[0].substring(4,names[0].length());
                time = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.getDefault()).format(Long.parseLong(strTime));
            }
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    private  List<DirectoryOrFileEntity> directoryOrFileEntities;  //如果是文件夹

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReadTag() {
        return readTag;
    }

    public void setReadTag(int readTag) {
        this.readTag = readTag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<DirectoryOrFileEntity> getDirectoryOrFileEntities() {
        return directoryOrFileEntities;
    }

    public void setDirectoryOrFileEntities(List<DirectoryOrFileEntity> directoryOrFileEntities) {
        this.directoryOrFileEntities = directoryOrFileEntities;
    }

}