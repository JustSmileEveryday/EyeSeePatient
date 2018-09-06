package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lht.wangtong.gowin120.patient.R;

/**
 * 阳性效果 item
 *
 * @author luoyc
 */
public class ReportPositiveItemView extends LinearLayout implements View.OnClickListener {

    private boolean isShow = false;
    private TextView name;
    private TextView detail;
    private ImageView openImg;

    public ReportPositiveItemView(Context context) {
        super(context);
        initView(context);
    }

    public ReportPositiveItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportPositiveItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.report_detail_positive_item_layout, this);
        name = findViewById(R.id.name);
        detail = findViewById(R.id.detail);
        openImg = findViewById(R.id.open_img);
        openImg.setOnClickListener(this);
    }

    public void setInfo(String nameValue, String detailValue) {
        name.setText(nameValue);
        detail.setText(detailValue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_img:
                if (isShow) {
                    isShow = false;
                    detail.setVisibility(View.GONE);
                    openImg.setImageResource(R.drawable.report_detail_open_img);
                } else {
                    isShow = true;
                    detail.setVisibility(View.VISIBLE);
                    openImg.setImageResource(R.drawable.report_detail_close_img);
                }
                break;
            default:
                break;
        }
    }
}
