package lht.wangtong.gowin120.patient.ui.mine.info;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 我的信息
 *
 * @author luoyc
 * @date 2018/3/8
 */
@Route(path = "/mine/info/MineInfoActivity")
public class MineInfoActivity extends BaseActivity<MineInfoPresenter> implements MineInfoContract.View, View.OnClickListener, TextWatcher {
    private final int USER_HEAD_PIC = 108;
    @BindView(R.id.mine_name)
    //姓名输入
            EditText mName;
    @BindView(R.id.clean_btn)
    //清除按钮
            ImageView mClean;
    @BindView(R.id.head_img)
    CircleImageView mHeadView;
    @BindView(R.id.mine_phone)
    TextView mPhone;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.cancel_btn)
    TextView mCancel;
    @BindView(R.id.sure_btn)
    TextView mSure;
    @BindView(R.id.start_use_btn)
    TextView mStartBtn;
    @Autowired
    User mInfo;
    @Autowired
    int mType;
    //修改过的图片
    private String userPic = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_info;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mName.addTextChangedListener(this);
        setUserData(mInfo);
        mPresenter.initData();
    }

    @OnClick({R.id.personal_head_tab, R.id.clean_btn, R.id.cancel_btn, R.id.next_btn, R.id.sure_btn, R.id.start_use_btn, R.id.age})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                //返回
                finish();
                break;
            case R.id.sure_btn:
                mInfo.setMemberName(mName.getText().toString());
                mPresenter.saveMemberInfo(mInfo);
                break;
            case R.id.personal_head_tab:
                //更换头像
                ARouter.getInstance().build("/mine/info/UserHeadActivity").withString("userPic", mInfo.getPicUrl()).navigation(this, USER_HEAD_PIC);
                break;
            case R.id.clean_btn:
                //清除
                mName.setText("");
                break;
            case R.id.age:
            case R.id.next_btn:
                //选择年龄段
                mPresenter.showAgePicker();
                break;
            case R.id.start_use_btn:
                //开始使用
                mInfo.setMemberName(mName.getText().toString());
                mPresenter.startUse(mInfo);
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mClean.setVisibility(View.VISIBLE);
        } else {
            mClean.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void setUserData(User memberInfo) {
        if (mType == 1) {
            mCancel.setVisibility(View.VISIBLE);
            mSure.setVisibility(View.VISIBLE);
            mStartBtn.setVisibility(View.GONE);
            if (memberInfo != null) {
                age.setText(CommonInfoUtils.getCommnCdInfoName("AgeGroup", memberInfo.getAgeGroup()));
            }
        } else {
            mCancel.setVisibility(View.INVISIBLE);
            mSure.setVisibility(View.INVISIBLE);
            mStartBtn.setVisibility(View.VISIBLE);
            memberInfo.setMemberName("");
            mStartBtn.setClickable(false);
        }
        Glide.with(this)
                .load(Api.HOST_IMG + memberInfo.getPicUrl())
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mHeadView.setImageDrawable(resource);
                    }
                });
        if (memberInfo.getMemberName().length() > 8) {
            mName.setText(memberInfo.getMemberName().subSequence(0, 8));
            mName.setSelection(8);
        } else {
            mName.setText(memberInfo.getMemberName());
            mName.setSelection(memberInfo.getMemberName().length());
        }
        mPhone.setText(memberInfo.getLoginName());
    }

    @Override
    public void setAge(CommonCdInfo info) {
        if (info != null) {
            mStartBtn.setClickable(true);
            mStartBtn.setBackgroundResource(R.drawable.green_btn_bg_5);
        }
        age.setText(info.getNameZh());
        mInfo.setAgeGroup(info.getCode());
    }

    @Override
    public Context getThisContext() {
        return this;
    }

    @Override
    public void finishThisActivity() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case USER_HEAD_PIC:
                userPic = data.getStringExtra("headImg");
                Glide.with(this)
                        .load(userPic)
//                        .placeholder(R.drawable.mine_head_default_img)
                        .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                        .transition(withCrossFade())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                mHeadView.setImageDrawable(resource);
                            }
                        });
                if (!TextUtils.isEmpty(userPic)) {
                    mPresenter.saveHeadPic(userPic);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (!TextUtils.isEmpty(userPic)) {
            if (FileUtils.isFileExists(userPic)) {
                FileUtils.deleteFile(userPic);
            }
        }
    }
}
