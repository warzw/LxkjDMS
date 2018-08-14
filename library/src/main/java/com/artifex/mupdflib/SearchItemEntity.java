package com.artifex.mupdflib;

/**
 * Created by dell on 2018/7/11.
 */

public class SearchItemEntity {
    private int index;
    private String content;
    private String keyWord;
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SearchItemEntity(){
    }
    public SearchItemEntity(int index){
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
