package lht.wangtong.gowin120.patient.ui.consult.supposeask;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfo;

/**
 * 猜你想问详情
 *
 * @author luoyc
 */
@Route(path = "/consult/supposeask/SupposeDetailActivity")
public class SupposeDetailActivity extends BaseActivity<SupposeDetailPresenter> implements SupposeDetailContract.View {
    @BindView(R.id.question)
    TextView mQuestion;
    @BindView(R.id.answer)
    WebView mAnswer;
    @BindView(R.id.useful_text)
    TextView usefulText;
    @BindView(R.id.not_useful_text)
    TextView notUsefulText;
    @BindView(R.id.consult_btn)
    TextView consultBtn;
    @BindView(R.id.useful_layout)
    LinearLayout usefulLayout;
    @BindView(R.id.not_useful_layout)
    LinearLayout notUsefulLayout;
    @Autowired
    String illnessQuestionId;
    private IllnessQuestionInfo mQuestionInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suppose_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.loadDetail(illnessQuestionId);
    }

    @OnClick({R.id.useful_layout, R.id.not_useful_layout, R.id.consult_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.useful_layout:
                usefulLayout.setClickable(false);
                notUsefulLayout.setClickable(false);
                mPresenter.saveIsUseful(illnessQuestionId, 1);
                usefulText.setText("有用(" + (mQuestionInfo.getHelpfulCount() + 1) + ")");
                break;
            case R.id.not_useful_layout:
                usefulLayout.setClickable(false);
                notUsefulLayout.setClickable(false);
                mPresenter.saveIsUseful(illnessQuestionId, 2);
                notUsefulText.setText("没用(" + (mQuestionInfo.getNoHelpfulCount() + 1) + ")");
                break;
            case R.id.consult_btn:
                ARouter.getInstance().build("/consult/chat/AutoChatActivity").navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void setDetail(IllnessQuestionInfo detail) {
        mQuestionInfo = detail;
        mQuestion.setText(detail.getQuestion());
        mAnswer.loadDataWithBaseURL(null, detail.getAnswer(), "text/html", "utf-8", null);
        usefulText.setText("有用(" + detail.getHelpfulCount() + ")");
        notUsefulText.setText("没用(" + detail.getNoHelpfulCount() + ")");
    }
}
