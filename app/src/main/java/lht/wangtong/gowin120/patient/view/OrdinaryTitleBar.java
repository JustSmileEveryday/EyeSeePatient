package lht.wangtong.gowin120.patient.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lht.wangtong.gowin120.patient.R;


/**
 * Created by sanmu on 2016/6/21 0021.
 * 常规titlebar
 * 默认是返回箭头
 */
public class OrdinaryTitleBar extends RelativeLayout implements View.OnClickListener {
    private TextView leftText;
    private Context context;
    private TextView titleText;
    private ImageView titleImg;
    private TextView rightText;
    private ImageView shareBtn;

    public OrdinaryTitleBar(Context context) {
        this(context, null);
    }

    public OrdinaryTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.titlebar_ordinary, this);
        titleText = findViewById(R.id.ordinary_titlebar_txt);
        leftText = findViewById(R.id.ordinary_titlebar_left_txt);
        rightText = findViewById(R.id.right_text);
        shareBtn = findViewById(R.id.share_btn);
        rightText.setOnClickListener(this);
        leftText.setOnClickListener(this);
        titleImg = findViewById(R.id.ordinary_titlebar_msg_img);
        titleImg.setOnClickListener(this);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OrdinaryTitleBar);
        titleText.setText(typedArray.getString(R.styleable.OrdinaryTitleBar_ordinaryTitle));
        boolean showBackImg = typedArray.getBoolean(R.styleable.OrdinaryTitleBar_showBackImg, true);
        boolean showRightTitle = typedArray.getBoolean(R.styleable.OrdinaryTitleBar_showRightTitle, false);
        boolean showShareImg = typedArray.getBoolean(R.styleable.OrdinaryTitleBar_showShareImg, false);
        if (!showBackImg) {
            titleImg.setVisibility(GONE);
        }
        if (showRightTitle) {
            rightText.setVisibility(VISIBLE);
        }
        if (showShareImg) {
            shareBtn.setVisibility(VISIBLE);
        }
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setLeftText(String text) {
        leftText.setText(text);
    }

    public void setRightText(String text) {
        rightText.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ordinary_titlebar_msg_img:
                //返回上一级   也可以是带参返回
                ((Activity) context).finish();
                break;
            default:
                break;
        }
    }
}
