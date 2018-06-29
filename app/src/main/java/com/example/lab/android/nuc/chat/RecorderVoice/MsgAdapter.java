package com.example.lab.android.nuc.chat.RecorderVoice;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.R;

import java.util.List;

public class MsgAdapter  extends RecyclerView.Adapter<MsgAdapter.ViewHolder> implements ListAdapter {


    private List<Msg> mMsgList;

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;

        LinearLayout rightLayout;

        TextView leftMsg;

        TextView rightMsg;


        @SuppressLint("CutPasteId")
        public ViewHolder(View itemView) {
            super( itemView );
            leftLayout = (LinearLayout) itemView.findViewById( R.id.left_layout );
            rightLayout = (LinearLayout) itemView.findViewById( R.id.right_layout );
            leftMsg = (TextView) itemView.findViewById( R.id.left_layout );
            rightMsg = (TextView) itemView.findViewById( R.id.right_layout );
        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }



    @NonNull
    @Override
    //用于创建ViewHouder实例
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext() ).inflate
                (R.layout.item_text,parent,false);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg = mMsgList.get( position );
        if (msg.getType() == Msg.TYPE_REVICED){

//            如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏掉
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else if (msg.getType() == Msg.TYPE_SEND){

//            如果是发送的消息，则显示右边的消息布局，将左边的消息布局隐藏掉
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
