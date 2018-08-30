package lht.wangtong.gowin120.patient.ui.consult.doctor;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;

/**
 * 评论界面
 *
 * @author luoyc
 */
@Route(path = "/consult/doctor/CommentActivity")
public class CommentActivity extends BaseActivity<CommentPresenter> implements CommentContract.View, TextWatcher {
    @BindView(R.id.tag)
    TagFlowLayout mTag;
    @BindView(R.id.comment_edit)
    EditText commentEdit;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @Autowired
    String commentRemoteConsultationId;
    private String keywordIds = "";
    private TagAdapter<CommentLabelInfo> mAdapter;
    private List<CommentLabelInfo> tags;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        tags = new ArrayList<>();
        commitBtn.setClickable(false);
        commentEdit.addTextChangedListener(this);
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mAdapter = new TagAdapter<CommentLabelInfo>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, CommentLabelInfo s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.comment_tag_layout,
                        mTag, false);
                tv.setText(s.getKeyword());
                return tv;
            }
        };
        mTag.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTag.setNestedScrollingEnabled(false);
        }
        mTag.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (tags.size() > 0) {
                    for (Integer integer : selectPosSet) {
                        keywordIds = keywordIds + (tags.get(integer).getKeywordId() + ",");
                    }
                }
            }
        });
        mPresenter.getCommentLabelList();
    }

    @OnClick(R.id.commit_btn)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(keywordIds)) {
            keywordIds = keywordIds.substring(0, keywordIds.length() - 1);
        }
        mPresenter.saveRemoteConsultationComment(keywordIds, commentEdit.getText().toString(), commentRemoteConsultationId);
    }

    @Override
    public void setCommentList(List<CommentLabelInfo> labelInfos) {
        tags.addAll(labelInfos);
        mAdapter.notifyDataChanged();
    }

    @Override
    public void saveSuccess() {
        new MaterialDialog.Builder(this)
                .title("评价成功")
                .content("您的评价已经提交成功！")
                .titleColor(Color.parseColor("#333333"))
                .contentColor(Color.parseColor("#333333"))
                .positiveText(R.string.sure)
                .positiveColor(Color.parseColor("#4A90E2"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                }).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            commitBtn.setClickable(true);
            commitBtn.setBackgroundResource(R.drawable.green_btn_bg_5);
        } else {
            commitBtn.setClickable(false);
            commitBtn.setBackgroundResource(R.drawable.green_btn_bg_5_unclick);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
