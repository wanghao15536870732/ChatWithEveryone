package com.example.lab.android.nuc.chat.Activity;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.drawable.AnimationDrawable;
        import android.graphics.drawable.Drawable;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.v4.content.FileProvider;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.RecyclerView;
        import android.text.Editable;
        import android.text.Selection;
        import android.text.Spannable;
        import android.text.TextUtils;
        import android.text.TextWatcher;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.WindowManager;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.example.lab.android.nuc.chat.Adapter.EmoViewPagerAdapter;
        import com.example.lab.android.nuc.chat.Adapter.EmoteAdapter;
        import com.example.lab.android.nuc.chat.Base.Message.ChatMessageBean;
        import com.example.lab.android.nuc.chat.Base.others.CommonUtils;
        import com.example.lab.android.nuc.chat.Base.others.FaceText;
        import com.example.lab.android.nuc.chat.PhotoSelector.PhotoSelectorActivity;
        import com.example.lab.android.nuc.chat.R;
        import com.example.lab.android.nuc.chat.RecorderVoice.AudioRecorderButton;
        import com.example.lab.android.nuc.chat.RecorderVoice.Manager.MediaManager;
        import com.example.lab.android.nuc.chat.RecorderVoice.Msg;
        import com.example.lab.android.nuc.chat.RecorderVoice.MsgAdapter;
        import com.example.lab.android.nuc.chat.RecorderVoice.RecorderAdapter;
        import com.example.lab.android.nuc.chat.Tools.Util.FaceTextUtils;
        import com.example.lab.android.nuc.chat.Tools.widget.EmoticonsEditText;
        import com.example.lab.android.nuc.chat.Tools.widget.dialog.DialogTips;
        import com.example.lab.android.nuc.chat.Translation.activity.MainActivity_11;
        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 2;
    public static final int TAKE_LOCATION = 3;
    public static final String CHAT_PEOPLE_NAME = "chat_people_name";
    public static final String NOW_LOCAITON = "chat_people_location";
    public static final String TRANSLATION_RESULT = "translation_detail";
    private ListView mListView;
    private ArrayAdapter<ChatMessageBean> mAdapter;
    private MsgAdapter madapter;
    private List<ChatMessageBean> mDatas = new ArrayList<ChatMessageBean>();
    private List<ChatMessageBean> mMsgList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MsgAdapter adapter;
    private AudioRecorderButton mAudioRecorderButton;
    private View mAnimView;
    private Context mContext;
    private TextView chat_people_name;
    private TextView input_textview;
    //创建各种按钮的实例
    private EmoticonsEditText mEmoticonsEditText;
    private Button btn_chat_voice, btn_chat_emo, btn_speak;
    private Button btn_chat_add, btn_chat_keyboard, btn_chat_send;
    //隐藏加号隐藏的表情界面
    private LinearLayout layout_more, layout_emo;
    //隐藏的加号扩展界面
    private LinearLayout layout_add;
    //扩招功能的发送其他界面
    private TextView tv_picture, tv_camera, tv_location, btn_translation;
    private ViewPager pager_emo;
    RelativeLayout layout_record;
    TextView tv_voice_tips;
    ImageView iv_record,setBack;
    public LinearLayout mHeaderLayout;
    private Drawable[] drawable_Anims;
    private ImageView picture;
    private Uri imageUri;

    private SharedPreferences pref;

    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_0 );
        initView();
        Intent intent = getIntent();
        String translation_result = intent.getStringExtra( TRANSLATION_RESULT );
        String people_name = intent.getStringExtra( CHAT_PEOPLE_NAME );
        chat_people_name.setText( people_name );
        mEmoticonsEditText.setText( translation_result );
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        mHeaderLayout = (LinearLayout) findViewById( R.id.common_actionbar );
        initButtonView();
        initVoiceView();
        setListViewAdapter();
    }


    @SuppressLint("WrongViewCast")
    private void initButtonView() {
        btn_chat_voice = (Button) findViewById( R.id.btn_chat_voice );
        btn_chat_emo = (Button) findViewById( R.id.btn_chat_emo );
        btn_speak = (Button) findViewById( R.id.btn_speak );
        btn_chat_add = (Button) findViewById( R.id.btn_chat_add );
        btn_chat_keyboard = (Button) findViewById( R.id.btn_chat_keyboard );
        btn_chat_send = (Button) findViewById( R.id.btn_chat_send );
        btn_translation = (Button) findViewById( R.id.btn_translation );

        chat_people_name = (TextView) findViewById( R.id.chat_prople_name );
        input_textview = (TextView) findViewById( R.id.put_in_question );
        setBack = (ImageView) findViewById( R.id.question_add_back );


        input_textview.setVisibility( View.GONE);
        btn_chat_voice.setOnClickListener( this );
        btn_chat_emo.setOnClickListener( this );
        btn_speak.setOnClickListener( this );
        btn_chat_add.setOnClickListener( this );
        btn_chat_keyboard.setOnClickListener( this );
        btn_chat_send.setOnClickListener( this );
        btn_translation.setOnClickListener( this );



        initAddView();
        initEmotionView();

        setBack.setOnClickListener( this );

        mEmoticonsEditText = (EmoticonsEditText) findViewById( R.id.edit_user_comment );
        //加号的额外功能
        layout_more = (LinearLayout) findViewById( R.id.layout_more );
        layout_emo = (LinearLayout) findViewById( R.id.layout_emo );
        layout_add = (LinearLayout) findViewById( R.id.layout_add );

        mEmoticonsEditText.setOnClickListener( this );
        mEmoticonsEditText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //自动生成方法存根。
                if (!TextUtils.isEmpty( s )) {
                    btn_chat_send.setVisibility( View.VISIBLE );
                    btn_chat_keyboard.setVisibility( View.GONE );
                    btn_chat_voice.setVisibility( View.GONE );
                    btn_translation.setVisibility( View.VISIBLE );
                } else {
                    if (btn_chat_voice.getVisibility() != View.VISIBLE) {
                        btn_chat_voice.setVisibility( View.VISIBLE );
                        btn_chat_send.setVisibility( View.GONE );
                        btn_chat_keyboard.setVisibility( View.GONE );
                    }
                    btn_translation.setVisibility( View.GONE );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );


        mListView = (ListView) findViewById( R.id.id_listview );
        mAudioRecorderButton = (AudioRecorderButton) findViewById( R.id.btn_speak );

        mAudioRecorderButton.setAudioFinishRecorderListener( new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                //每完成一次录音
                ChatMessageBean chatMessageBean = new ChatMessageBean( seconds, filePath ,0,0,null,
                    null,null,null,null,
                        null,null,null,0);
                mDatas.add( chatMessageBean );
                //更新adapter
                mAdapter.notifyDataSetChanged();
                //设置listview 位置
                mListView.setSelection( mDatas.size() + mMsgList.size() - 1 );
            }
        } );
        initAddView();


    }

    private void initAddView() {
        tv_picture = (TextView) findViewById( R.id.tv_picture );
        tv_camera = (TextView) findViewById( R.id.tv_camera );
        tv_location = (TextView) findViewById( R.id.tv_location );
        tv_picture.setOnClickListener( this );
        tv_location.setOnClickListener( this );
        tv_camera.setOnClickListener( this );
    }


    //表情列表
    List<FaceText> emos;

    private void initEmotionView() {
        pager_emo = (ViewPager) findViewById( R.id.pager_emo );
        emos = FaceTextUtils.faceTexts;
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < 2; ++i) {
            //将获取的GridView添加到自定义View当中
            views.add( getGridView( i ) );
        }
        pager_emo.setAdapter( new EmoViewPagerAdapter( views ) );
    }

    private void initVoiceView() {
        layout_record = (RelativeLayout) findViewById( R.id.layout_record );
        tv_voice_tips = (TextView) findViewById( R.id.tv_voice_tips );
        iv_record = (ImageView) findViewById( R.id.iv_record );
        btn_speak.setOnTouchListener( new VoiceTouchListen() );
        initVoiceAnimRes();
        initRecordManager();
    }


    private void initRecordManager() {


    }


    private void initVoiceAnimRes() {
        drawable_Anims = new Drawable[]{
                getResources().getDrawable( R.drawable.chat_icon_voice2 ),
                getResources().getDrawable( R.drawable.chat_icon_voice3 ),
                getResources().getDrawable( R.drawable.chat_icon_voice4 ),
                getResources().getDrawable( R.drawable.chat_icon_voice5 ),
                getResources().getDrawable( R.drawable.chat_icon_voice6 )};
    }


    private View getGridView(final int i) {
        View view = View.inflate( this, R.layout.include_emo_gridview, null );
        GridView gridView = (GridView) view.findViewById( R.id.gridview );
        List<FaceText> list = new ArrayList<FaceText>();
        //在一页的界面上加载20个emotions
        if (i == 0) {
            list.addAll( emos.subList( 0, 20 ) );
        } else if (i == 1) {  //剩下的用下一页加载
            list.addAll( emos.subList( 21, emos.size() ) );
        }
        final EmoteAdapter gridAdapter = new EmoteAdapter( VideoActivity.this, list );

        gridView.setAdapter( gridAdapter );
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FaceText name = (FaceText) gridAdapter.getItem( position );
                String key = name.text.toString();
                try {
                    if (mEmoticonsEditText != null && !TextUtils.isEmpty( key )) {
                        int start = mEmoticonsEditText.getSelectionStart();
                        CharSequence content = mEmoticonsEditText.getText().
                                insert( start, key );
                        mEmoticonsEditText.setText( content );
                        CharSequence info = mEmoticonsEditText.getText();
                        if (info instanceof Spannable) {
                            Spannable spanText = (Spannable) info;
                            Selection.setSelection( spanText,
                                    start + key.length() );
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_user_comment:
                if (layout_more.getVisibility() == View.VISIBLE) {
                    layout_add.setVisibility( View.GONE );
                    layout_emo.setVisibility( View.GONE );
                    layout_more.setVisibility( View.GONE );
                }
                break;
            case R.id.btn_chat_emo:
                if (layout_more.getVisibility() == View.GONE) {
                    showEditState( true );
                } else {
                    if (layout_add.getVisibility() == View.VISIBLE) {
                        layout_add.setVisibility( View.GONE );
                        layout_emo.setVisibility( View.VISIBLE );
                    } else {
                        layout_more.setVisibility( View.GONE );
                    }
                }
                break;
            case R.id.btn_chat_add:

                if (layout_add.getVisibility() == View.VISIBLE) {
                    hideInput();
                    layout_add.setVisibility( View.GONE );
                    layout_emo.setVisibility( View.VISIBLE );
                } else {
                    hideInput();
                    layout_add.setVisibility( View.VISIBLE );
                    layout_more.setVisibility( View.VISIBLE );
                    layout_emo.setVisibility( View.GONE );
                }
                break;
            case R.id.btn_chat_voice:
                mEmoticonsEditText.setVisibility( View.GONE );
                layout_more.setVisibility( View.GONE );
                btn_chat_voice.setVisibility( View.GONE );
                btn_chat_keyboard.setVisibility( View.VISIBLE );
                btn_speak.setVisibility( View.VISIBLE );
                hideSoftInputView();
                break;
            case R.id.btn_chat_keyboard:
                showEditState( false );
                break;
            case R.id.btn_chat_send:
                final String msg = mEmoticonsEditText.getText().toString();
                //如过发送的消息为空
                if (msg.equals( "" )) {
                    ShowToast( "消息不能为空" );
                    return;
                } else {
                    boolean isNetConnected = CommonUtils.isNetworkAvailable( this );
                    if (!isNetConnected) {
                        ShowToast( "当前网络不可用,请检查您的网络" );
                    } else {
                        Msg msg1 = new Msg( msg,Msg.TYPE_SEND );

                    }
                }
                break;
            case R.id.tv_camera:
                takePhoto();
                break;
            case R.id.tv_picture:
                Intent intent_1 = new Intent(this, PhotoSelectorActivity.class);
                intent_1.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
                intent_1.putExtra( "limit",9 );
                startActivityForResult( intent_1,0 );
                startActivity( intent_1 );
                break;
            case R.id.tv_location:
                Intent intent_2 = new Intent( VideoActivity.this,LocationActivity.class );
               startActivityForResult( intent_2,TAKE_LOCATION );
                break;
            case R.id.btn_translation:
                String translation_detail = mEmoticonsEditText.getText().toString();
                Intent intent = new Intent( this, MainActivity_11.class );
                intent.putExtra( MainActivity_11.TRANSLATION_TEXT, translation_detail );
                startActivityForResult( intent, 1 );
                break;
            case R.id.question_add_back:
                finish();
                break;
            default:
                break;
        }

    }


    private void hideInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE );
        if (null != imm){
            //isOpen返回true,表示输入法打开
            boolean isOpen = imm.isActive();
            if (isOpen){
                //强制关闭软件盘
                if (getCurrentFocus() != null){
                    imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void takePhoto(){

        File outputImage = new File( getExternalCacheDir(),"output_image.jpg" );
        try{
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile( VideoActivity.this,
                    "com.example.cameraalbumtest.fileprovider",outputImage );
        }else {
            imageUri = Uri.fromFile( outputImage);
        }

        Intent intent = new Intent(  "android.media.action.IMAGE_CAPTURE");
        intent.putExtra( MediaStore.EXTRA_OUTPUT,imageUri );
        startActivityForResult( intent,0);
    }
    //Toast的封装
    Toast mToast;

    @SuppressLint("WrongConstant")
    private Toast showShortToast() {
        if (mToast == null) {
            mToast = new Toast( this );
        }
        View view = LayoutInflater.from( this ).inflate( R.layout.include_chat_voice_short, null );
        mToast.setView( view );
        mToast.setGravity( Gravity.CENTER, 0, 0 );
        mToast.setDuration( 50 );
        return mToast;
    }

    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty( text )) {
            runOnUiThread( new Runnable() {

                @SuppressLint("ShowToast")
                @Override
                public void run() {

                    if (mToast == null) {
                        mToast = Toast.makeText( getApplicationContext(), text,
                                Toast.LENGTH_LONG );
                    } else {
                        mToast.setText( text );
                    }
                    mToast.show();
                }
            } );

        }
    }

    public void ShowToast(final int resId) {
        runOnUiThread( new Runnable() {

            @SuppressLint("ShowToast")
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText( VideoActivity.this.getApplicationContext(), resId,
                            Toast.LENGTH_LONG );
                } else {
                    mToast.setText( resId );
                }
                mToast.show();
            }
        } );
    }


    class VoiceTouchListen implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!CommonUtils.checkSdCard()) {
                        ShowToast( "使用语音功能需要SD卡支持" );
                        return false;
                    }
                    try {
                        v.setPressed( true );

                    } catch (Exception e) {

                    }
            }
            return false;
        }

    }

    public void showResendDialog(final View parentV, View v, final Object values) {
        DialogTips dialog = new DialogTips( this, "重新发送该消息", "是",
                "不", "提示",
                true );

        dialog.SetOnSuccessListener( new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int userId) {

                dialogInterface.dismiss();
            }
        } );

        dialog.show();
        dialog = null;
    }


    private void showEditState(boolean isEmo) {
        mEmoticonsEditText.setVisibility( View.VISIBLE );
        btn_chat_keyboard.setVisibility( View.GONE );
        btn_chat_voice.setVisibility( View.VISIBLE );
        btn_speak.setVisibility( View.GONE );
        mEmoticonsEditText.requestFocus();
        if (isEmo) {
            layout_more.setVisibility( View.VISIBLE );
            layout_emo.setVisibility( View.VISIBLE );
            layout_add.setVisibility( View.GONE );
            hideSoftInputView();
        } else {
            layout_more.setVisibility( View.GONE );
            showSoftInputView();
        }
    }

    public void showSoftInputView() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                ((InputMethodManager) getSystemService( INPUT_METHOD_SERVICE ))
                        .showSoftInput( mEmoticonsEditText, 0 );
            }
        }
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService( Activity.INPUT_METHOD_SERVICE ));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initXListView() {

    }


    private void initOrRefresh() {
        if (mAdapter != null) {

        }
    }


    private void setListViewAdapter() {
        mAdapter = new RecorderAdapter( this, mDatas );
        mListView.setAdapter( mAdapter );

        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //如果第一个动画正在运行， 停止第一个播放其他的
                if (mAnimView != null) {
                    mAnimView.setBackgroundResource( R.drawable.adj );
                    mAnimView = null;
                }
                //播放动画
                mAnimView = view.findViewById( R.id.id_recorder_anim );
                mAnimView.setBackgroundResource( R.drawable.play_anim );
                AnimationDrawable animation = (AnimationDrawable) mAnimView.getBackground();
                animation.start();

                //播放音频  完成后改回原来的background
                MediaManager.playSound( mDatas.get( position ).getFilePath(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mAnimView.setBackgroundResource( R.drawable.adj );
                    }
                } );
            }
        } );
    }

    /**
     * 根据生命周期 管理播放录音
     */
    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    //数据类
    public class Recorder {

        public float time;
        String filePath;

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public Recorder(float time, String filePath) {
            super();
            this.time = time;
            this.filePath = filePath;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String translation_result = data.getStringExtra( TRANSLATION_RESULT );
                    mEmoticonsEditText.setText( translation_result );
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    //这里将拍好的照片拿出来
                    Intent intent = new Intent( this,PrePhototActivity.class );
                    intent.putExtra( PrePhototActivity.IMAGE_URI,imageUri.toString() );
                    startActivity( intent );
                }
                break;
            case TAKE_LOCATION:
                if (resultCode == RESULT_OK){
                    String position = data.getStringExtra( NOW_LOCAITON );
                    mEmoticonsEditText.setText( position );
                }
                break;
            default:

        }
    }
}
