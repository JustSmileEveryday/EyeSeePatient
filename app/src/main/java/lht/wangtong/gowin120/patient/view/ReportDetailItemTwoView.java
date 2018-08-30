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
public class ReportDetailItemTwoView extends LinearLayout {

    private TextView nameView;
    private TextView valueView;

    public ReportDetailItemTwoView(Context context) {
        super(context);
        initView(context);
    }

    public ReportDetailItemTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportDetailItemTwoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.report_detail_view_layout_2, this);
        nameView = findViewById(R.id.name);
        valueView = findViewById(R.id.value);
    }

    public void setInfo(String name, String value) {
        nameView.setText(name);
        valueView.setText(value);
    }
}
