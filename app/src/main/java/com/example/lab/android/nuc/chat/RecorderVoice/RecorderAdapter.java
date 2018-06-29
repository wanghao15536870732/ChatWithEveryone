package com.example.lab.android.nuc.chat.RecorderVoice;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.Activity.VideoActivity;
import com.example.lab.android.nuc.chat.Base.Message.ChatMessageBean;
import com.example.lab.android.nuc.chat.R;
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment;


import java.util.List;

public class RecorderAdapter extends ArrayAdapter<ChatMessageBean> {

    //item 最小最大值
    private int mMinItemWidth;
    private int mMaxIItemWidth;

    private LayoutInflater mInflater;

    public RecorderAdapter(VideoActivity context, List<ChatMessageBean> datas) {
        super(context,-1, datas);
        mInflater = LayoutInflater.from(context);

        //获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        //item 设定最小最大值
        mMaxIItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate( R.layout.item_recorder, parent, false);
            holder = new ViewHolder();
            holder.seconds = (TextView) convertView.findViewById(R.id.id_recorder_time);
            holder.length = convertView.findViewById(R.id.id_recorder_length);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置时间  matt.round 四舍五入
        holder.seconds.setText( Math.round(getItem(position).getTime())+"\"");
        //设置背景的宽度
        ViewGroup.LayoutParams lp = holder.length.getLayoutParams();
        //getItem(position).time
        lp.width = (int) (mMinItemWidth + (mMaxIItemWidth / 60f*getItem(position).getTime()));

        return convertView;
    }

    private class ViewHolder{
        TextView seconds;
        View length;
    }


}
