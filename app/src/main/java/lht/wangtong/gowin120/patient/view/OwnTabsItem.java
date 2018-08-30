package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lht.wangtong.gowin120.patient.R;


/**
 * Created by luoyc on 2016/6/16 0016.
 * 我的功能列表封装
 */
public class OwnTabsItem extends RelativeLayout {
    Context context;
    ImageView leftImg;
    TextView txtV;

    public OwnTabsItem(Context context) {
        this(context, null);
    }

    public OwnTabsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_owntab, this);
        leftImg = (ImageView) findViewById(R.id.own_tab_img);
        txtV = (TextView) findViewById(R.id.own_tab_txt);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OwnTabsItem);
        txtV.setText(typedArray.getString(R.styleable.OwnTabsItem_ownTabsLeftText));
        leftImg.setImageResource(typedArray.getResourceId(R.styleable.OwnTabsItem_ownTabsLeftImg, 0));
    }

    public void setElement(@DrawableRes int resId, String txt) {
//        if(leftImg != null && txtV != null){
        leftImg.setImageResource(resId);
        txtV.setText(txt);
//        }

    }
}
