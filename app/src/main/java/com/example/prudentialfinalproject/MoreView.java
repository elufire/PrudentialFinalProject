package com.example.prudentialfinalproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreView extends LinearLayout {
//    private ImageView ivThreeDots;
//    private TextView tvMore;


    public MoreView(Context context) {
        this(context, null);
    }

    public MoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.more_layout, this);

//        ivThreeDots = findViewById(R.id.ivThreeDots);
//        tvMore = findViewById(R.id.tvMore);
//

    }
}
