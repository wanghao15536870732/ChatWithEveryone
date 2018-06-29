package com.example.lab.android.nuc.chat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.lab.android.nuc.chat.Base.Message.ChatMessageBean;

public class messageAdapter extends ArrayAdapter<ChatMessageBean> {
    public messageAdapter(@NonNull Context context, int resource) {
        super( context, resource );
    }
}
