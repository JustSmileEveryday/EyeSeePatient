package lht.wangtong.gowin120.patient.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import lht.wangtong.gowin120.patient.R;


/**
 *
 * @author luoyc
 * @date 2016/10/13
 */
public class PromptDialog extends Dialog implements View.OnClickListener {

    ICallback callback;
    private TextView sureBtn;
    private TextView contentT;
    private TextView title;
    private Context context;
    private TextView cancleBtn;


    public PromptDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
        setCustomDialog();
    }

    public PromptDialog(Context context, ICallback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_prompt, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_prompt2_sure);
        cancleBtn = (TextView) mView.findViewById(R.id.dialog_prompt2_cancle);
        contentT = (TextView) mView.findViewById(R.id.dialog_prompt2_content);
        title = (TextView) mView.findViewById(R.id.dialog_title);
        sureBtn.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        super.setContentView(mView);
    }

    public PromptDialog setTitle(String titleText) {
        title.setText(titleText);
        title.setVisibility(View.VISIBLE);
        return this;
    }

    public PromptDialog setContent(String content) {
        contentT.setText(content);
        return this;
    }

    public PromptDialog setRightText(String rightText) {
        sureBtn.setText(rightText);
        return this;
    }

    public PromptDialog showCancleBtn(boolean show) {
        if (show) cancleBtn.setVisibility(View.VISIBLE);
        else cancleBtn.setVisibility(View.GONE);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_prompt2_sure:
                dismiss();
                if (callback != null) {
                    callback.callback();
                }
                break;
            case R.id.dialog_prompt2_cancle:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface ICallback {
        void callback();
    }

}
