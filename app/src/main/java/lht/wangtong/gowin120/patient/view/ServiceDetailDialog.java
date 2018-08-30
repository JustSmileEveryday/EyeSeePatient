package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;

import lht.wangtong.gowin120.patient.R;

/**
 * 服务详情介绍
 *
 * @author luoyc
 */
public class ServiceDetailDialog extends BaseDialog implements View.OnClickListener {
    private Context mContext;
    private TextView titleView;
    private TextView contentView;

    public ServiceDetailDialog(Context context) {
        super(context);
        this.mContext = context;
        setCustomDialog();
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setContent(String content) {
        contentView.setText(content);
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_service_detail_layout, null);
        titleView = mView.findViewById(R.id.title);
        contentView = mView.findViewById(R.id.content);
        TextView sureBtn = mView.findViewById(R.id.sure);
        sureBtn.setOnClickListener(this);
        setContentView(mView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        setGravity(Gravity.BOTTOM);
        setSize(ScreenUtils.getScreenWidth(), 0);
    }

}
