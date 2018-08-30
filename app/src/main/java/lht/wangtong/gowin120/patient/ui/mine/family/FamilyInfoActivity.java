package lht.wangtong.gowin120.patient.ui.mine.family;


import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.event.FamilyAddEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 成员信息
 *
 * @author Luoyc
 * @date 2018/3/15
 */
@Route(path = "/mine/family/FamilyInfoActivity")
public class FamilyInfoActivity extends BaseActivity<FamilyInfoPresenter> implements FamilyInfoContact.View, TextWatcher {
    private final int USER_HEAD_PIC = 108;
    //姓名输入
    @BindView(R.id.mine_name)
    EditText mName;
    //清除按钮
    @BindView(R.id.clean_btn)
    ImageView mClean;
    @BindView(R.id.head_img)
    CircleImageView mHeadView;
    @BindView(R.id.relationship)
    TextView mRelationship;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.delete_btn)
    TextView mDeleteBtn;
    @BindView(R.id.identification_layout)
    ConstraintLayout mIdentificationLayout;
    @BindView(R.id.identification)
    TextView mIdentificationView;
    @Autowired
    int type;
    @Autowired
    HomeFamilyInfo mFamilyInfo;
    @Autowired
    String mIdentificationId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_info;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mName.addTextChangedListener(this);
        setUserData();
        mPresenter.initData();
    }

    @Override
    public void setUserData() {
        switch (type) {
            case 1:
                mDeleteBtn.setVisibility(View.GONE);
                break;
            case 2:
                if (mFamilyInfo != null) {
                    Glide.with(this).load(Api.HOST_IMG + mFamilyInfo.getPicUrl())
                            .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                            .transition(withCrossFade())
                            .into(mHeadView);
                    mName.setText(mFamilyInfo.getName());
                    mName.setSelection(mFamilyInfo.getName().length());
                    mRelationship.setText(CommonInfoUtils.getCommnCdInfoName("FamilyRelationship", mFamilyInfo.getRelationship()));
                    age.setText(CommonInfoUtils.getCommnCdInfoName("AgeGroup", mFamilyInfo.getAgeGroup()));
                }
                mDeleteBtn.setVisibility(View.GONE);
                break;
            case 3:
                //扫码添加成员 (报告所需)
                mDeleteBtn.setVisibility(View.GONE);
                mIdentificationLayout.setVisibility(View.VISIBLE);
                mIdentificationView.setText(mIdentificationId);
                mFamilyInfo.setMobilePhone(mIdentificationId);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAge(CommonCdInfo info) {
        age.setText(info.getNameZh());
        mFamilyInfo.setAgeGroup(info.getCode());
    }

    @Override
    public Activity getThisContext() {
        return this;
    }

    @Override
    public void setRelationship(CommonCdInfo info) {
        mRelationship.setText(info.getNameZh());
        mFamilyInfo.setRelationship(info.getCode());
    }

    @Override
    public void changeFinsh() {
        EventBus.getDefault().postSticky(new FamilyAddEvent(true));
        Intent data = new Intent();
        data.putExtra("isRresh", true);
        setResult(RESULT_OK, data);
        finish();
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

    @OnClick({R.id.save_btn, R.id.personal_head_tab, R.id.clean_btn, R.id.cancel_btn, R.id.next_btn, R.id.relationship_next_btn, R.id.delete_btn, R.id.age, R.id.relationship})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                //返回
                finish();
                break;
            case R.id.save_btn:
                //保存
                if (TextUtils.isEmpty(mName.getText().toString())) {
                    ToastUtils.showShort("请输入昵称");
                    return;
                }
                if (mName.getText().toString().length() < 2) {
                    ToastUtils.showShort("昵称限制2-8个字");
                    return;
                }
                mFamilyInfo.setName(mName.getText().toString());
                mPresenter.saveInfo(mFamilyInfo);
                break;
            case R.id.delete_btn:
                mPresenter.deleteFamily(mFamilyInfo.getFamilyId());
                break;
            case R.id.personal_head_tab:
                //更换头像
                ARouter.getInstance().build("/mine/info/UserHeadActivity")
                        .withString("userPic", mFamilyInfo.getPicUrl())
                        .navigation(this, USER_HEAD_PIC);
                break;
            case R.id.clean_btn:
                //清除
                mName.setText("");
                break;
            case R.id.relationship:
            case R.id.relationship_next_btn:
                mPresenter.showRelationPicker();
                break;
            case R.id.age:
            case R.id.next_btn:
                //选择年龄段
                mPresenter.showAgePicker();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case USER_HEAD_PIC:
                String userPic = data.getStringExtra("headImg");
                Glide.with(this)
                        .load(new File(userPic))
                        .apply(new RequestOptions().error(R.drawable.mine_head_default_img))
                        .transition(withCrossFade())
                        .into(mHeadView);
                mFamilyInfo.setNewPicUrl(userPic);
                break;
            default:
                break;
        }
    }
}
