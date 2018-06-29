package com.example.lab.android.nuc.chat.RecorderVoice;

public class Msg {

    public static final int TYPE_REVICED = 0;

    public static final int TYPE_SEND = 1;

    private String content;

    private int type;

    public Msg(String content,int type){
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
