package lht.wangtong.gowin120.patient.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;


/**
 * 评论弹窗
 *
 * @author Luoyc
 */
public class CommentDialog extends BaseDialog implements TextWatcher, View.OnClickListener {

    private TextView cancelBtn;
    private TextView commitBtn;
    private EditText mInput;
    private Context mContext;

    public CommentDialog(Context context, ICallbackTab callback) {
        super(context, callback);
        mContext = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment_layout, null);
        cancelBtn = mView.findViewById(R.id.cancel_btn);
        commitBtn = mView.findViewById(R.id.commit_btn);
        mInput = mView.findViewById(R.id.input);
        cancelBtn.setOnClickListener(this);
        commitBtn.setClickable(false);
        commitBtn.setOnClickListener(this);
        mInput.addTextChangedListener(this);
        mInput.requestFocus();
        setContentView(mView);
    }

    public void setInput(String content){
        mInput.setText(content);
    }

    @Override
    public void show() {
        super.show();
        setGravity(Gravity.BOTTOM);
        setSize(ScreenUtils.getScreenWidth(), 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        KeyboardUtils.hideSoftInput(mInput);
    }

    public String getContent() {
        return mInput.getText().toString();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setTextColor(Color.parseColor("#01C1B4"));
        } else {
            commitBtn.setClickable(false);
            commitBtn.setTextColor(Color.parseColor("#8001C1B4"));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.commit_btn:
                callback.callBackTab(1);
                break;
            default:
                break;
        }
    }
}
