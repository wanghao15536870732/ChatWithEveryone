package com.example.lab.android.nuc.chat.Tools.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab.android.nuc.chat.R;

public abstract class DialogBase extends Dialog{

    //发送成功和发送失败
    protected OnClickListener onSuccessListener;
    protected Context mainContext;

    protected OnClickListener onCancelListener;
    protected OnDismissListener onDismissListener;

    protected View view;
    protected Button positiveButton, negativeButton;
    private boolean isFullScreen = false;

    private boolean hasTitle = true;

    //根据屏幕的大小设定Dialog的大小
    private int width = 0, height = 0, x = 0, y = 0;
    private int iconTitle = 0;
    private String message, title;
    private String namePositiveButton, nameNegativeButton;
    private final int MATCH_PARENT = android.view.ViewGroup.LayoutParams.MATCH_PARENT;

    //取消发送语音
    private boolean isCancel = true;


    public boolean isCancel(){
        return isCancel;
    }
    public void setCancel(boolean isCancel){
        this.isCancel = isCancel;
    }

    public DialogBase(@NonNull Context context) {
        super( context );
        this.mainContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.v2_dialog_base );
        this.onBuilding();
        LinearLayout dialog_top = (LinearLayout) findViewById( R.id.dialog_top );
        View title_red_line = (View ) findViewById( R.id.title_red_line );
        if(hasTitle){
            dialog_top.setVisibility(View.VISIBLE);
            title_red_line.setVisibility(View.VISIBLE);
        }else{
            dialog_top.setVisibility(View.GONE);
            title_red_line.setVisibility(View.GONE);
        }
        TextView titleTextView = (TextView) findViewById( R.id.dialog_title );
        titleTextView.setText( this.getMessage() );

        if (view != null){
            FrameLayout custom = (FrameLayout) findViewById( R.id.dialog_custom );
            custom.addView( view ,new FrameLayout.LayoutParams( MATCH_PARENT,MATCH_PARENT ));
            findViewById( R.id.dialog_contentPanel ).setVisibility( View.GONE );
        }else {
            findViewById( R.id.dialog_contentPanel ).setVisibility( View.GONE);
        }
        positiveButton = (Button) findViewById( R.id.dialog_positivebutton);
        negativeButton = (Button )findViewById( R.id.dialog_negativebutton );

        //同意按钮的点击事件
        if(namePositiveButton != null && namePositiveButton.length() > 0){
            positiveButton.setText( namePositiveButton );
            positiveButton.setOnClickListener( GetPositiveButtonOnClickListener() );
        }else {
            positiveButton.setVisibility( View.GONE );
            //左右的dialog按钮显示出来
            findViewById( R.id.dialog_leftspacer).setVisibility( View.VISIBLE );
            findViewById( R.id.dialog_rightspacer ).setVisibility( View.VISIBLE );
        }
        //拒绝按钮的点击事件
        if (nameNegativeButton != null  && nameNegativeButton.length() > 0){
            negativeButton.setText( namePositiveButton );
            negativeButton.setOnClickListener( GetNegativeButtonOnClickListener() );
        }else {
            negativeButton.setVisibility(View.GONE );
        }


        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        if (this.getWidth() > 0)
            params.width  = this.getWidth();
        if (this.getHeight() > 0)
            params.height = this.getHeight();
        if (this.getX() > 0)
            params.width = this.getX();
        if (this.getY() > 0)
            params.height = this.getY();

        if (isFullScreen){
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
        }

        if (isCancel){
            setCanceledOnTouchOutside( true );
            setCancelable( true );
        }else {
            setCanceledOnTouchOutside( false );
            setCancelable( false );
        }
        getWindow().setAttributes( params );
        this.setOnDismissListener( GetOnDismissListener() );
        this.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }


    //Dialog的点击事件
    protected OnDismissListener GetOnDismissListener(){
        return new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DialogBase.this.onDismiss();
                DialogBase.this.setOnDismissListener( null );
                view = null;
                mainContext = null;
                positiveButton = null;
                negativeButton = null;
                if (onDismissListener != null){
                    onDismissListener.onDismiss( null );
                }
            }
        };
    }

    ///Dialog的消失事件
    protected View.OnClickListener  GetPositiveButtonOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if(OnClickPositiveButton())
                    DialogBase.this.dismiss();
            }
        };
    }

    private View.OnClickListener GetNegativeButtonOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                OnClickNegativeButton();
                DialogBase.this.dismiss();
            }
        };
    }
    protected View.OnFocusChangeListener GetOnFocusChangeListener() {
        return new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && v instanceof EditText) {
                    ((EditText) v).setSelection(0, ((EditText) v).getText().length());
                }
            }
        };
    }




    public void SetOnSuccessListener(OnClickListener listener){
        onSuccessListener = listener;
    }


    public void SetOnDismissListener(OnDismissListener listener){
        onDismissListener = listener;
    }

    public void SetOnCancelListener(OnClickListener listener){
        onCancelListener = listener;
    }


    protected abstract void onBuilding();


    protected abstract boolean OnClickPositiveButton();

    protected abstract void OnClickNegativeButton();


    protected abstract void onDismiss();

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconTitle(int iconTitle) {
        this.iconTitle = iconTitle;
    }


    public int getIconTitle() {
        return iconTitle;
    }

    protected String getMessage() {
        return message;
    }


    protected void setMessage(String message) {
        this.message = message;
    }


    protected View getView() {
        return view;
    }


    protected void setView(View view) {
        this.view = view;
    }


    public boolean getIsFullScreen() {
        return isFullScreen;
    }


    public void setIsFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }


    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }


    protected int getWidth() {
        return width;
    }


    protected void setWidth(int width) {
        this.width = width;
    }

    protected int getHeight() {
        return height;
    }


    protected void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }



    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    protected String getNamePositiveButton() {
        return namePositiveButton;
    }

    protected void setNamePositiveButton(String namePositiveButton) {
        this.namePositiveButton = namePositiveButton;
    }


    protected String getNameNegativeButton() {
        return nameNegativeButton;
    }


    protected void setNameNegativeButton(String nameNegativeButton) {
        this.nameNegativeButton = nameNegativeButton;
    }
}
