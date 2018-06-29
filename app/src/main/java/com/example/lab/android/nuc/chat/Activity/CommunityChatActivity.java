package com.example.lab.android.nuc.chat.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.lab.android.nuc.chat.Adapter.MsgAdapter;
import com.example.lab.android.nuc.chat.Base.Message.Msg;
import com.example.lab.android.nuc.chat.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityChatActivity extends AppCompatActivity implements View.OnClickListener{


    private List<Msg> mMsgList = new ArrayList<>(  );

    private EditText mEditText;

    private Button send;

    private RecyclerView mRecyclerView;

    private MsgAdapter mMsgAdapter;

    private ImageButton sound_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat_community);
//        mEditText = (EditText)findViewById( R.id.chat_edit_Text );
        send = (Button )findViewById( R.id.msg_send );
        sound_button = (ImageButton) findViewById( R.id.message_sound );
        mRecyclerView = (RecyclerView) findViewById( R.id.msg_recycler_view );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( layoutManager );
        mMsgAdapter = new MsgAdapter( mMsgList );
        mRecyclerView.setAdapter( mMsgAdapter );
        send.setOnClickListener( this );
        sound_button.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_send:
                String content = mEditText.getText().toString();
                //如果发送的消息不为空
                if (! "".equals( content ) ){
                    Msg msg = new Msg( content,Msg.TYOE_SEND );
                    mMsgList.add( msg );
                    mMsgAdapter.notifyItemInserted( mMsgList.size() - 1 );
                    mRecyclerView.scrollToPosition( mMsgList.size() - 1 );
                    mEditText.setText( "" ); //清空输入框的内容
                }
                break;
            case R.id.message_sound:

                break;
            default:
        }
    }
}
