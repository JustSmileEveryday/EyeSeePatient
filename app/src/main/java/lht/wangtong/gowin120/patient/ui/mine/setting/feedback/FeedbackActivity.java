package lht.wangtong.gowin120.patient.ui.mine.setting.feedback;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * 意见反馈
 *
 * @author luoyc
 * @date 2018/3/26
 */
@Route(path = "/setting/feedback/FeedbackActivity")
public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContact.View, TextWatcher {
    @BindView(R.id.feedback_edit)
    EditText mFeedback;
    @BindView(R.id.contact)
    EditText mContact;
    @BindView(R.id.commit_btn)
    TextView mCommitBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mFeedback.addTextChangedListener(this);
    }

    @OnClick(R.id.commit_btn)
    public void commit() {
        mPresenter.commitFeedback(mFeedback.getText().toString(), mContact.getText().toString());
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            mCommitBtn.setClickable(true);
            mCommitBtn.setBackgroundResource(R.drawable.green_btn_bg_5);
        } else {
            mCommitBtn.setClickable(false);
            mCommitBtn.setBackgroundResource(R.drawable.green_btn_bg_5_unclick);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void close() {
        finish();
    }
}
