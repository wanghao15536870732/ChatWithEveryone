package com.example.lab.android.nuc.chat.Base.others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class AudioRecorderButton extends Button{


    public AudioRecorderButton(Context context) {
        super( context );
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super( context, attrs );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case FOCUS_DOWN:

                break;

        }
        return true;
    }
}
