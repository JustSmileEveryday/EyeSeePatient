package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import lht.wangtong.gowin120.patient.R;

/**
 * @author luoyc
 */
public class ReportDetailItemView extends LinearLayout {

    private TextView nameView;
    private TextView rightValueView;
    private TextView leftValueView;

    public ReportDetailItemView(Context context) {
        super(context);
        initView(context);
    }

    public ReportDetailItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportDetailItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.report_detail_view_layout, this);
        nameView = findViewById(R.id.name);
        rightValueView = findViewById(R.id.rightValue);
        leftValueView = findViewById(R.id.leftValue);
    }

    public void setInfo(String name, String rightValue, String leftValue) {
        nameView.setText(name);
        rightValueView.setText(rightValue);
        leftValueView.setText(leftValue);
    }
}
