package lht.wangtong.gowin120.patient.ui.mine.info;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AgeGroupTypeInfo;
import lht.wangtong.gowin120.patient.bean.UserHeadInfo;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.ui.login.AuthLoginActivity;
import lht.wangtong.gowin120.patient.ui.login.LoginActivity;
import lht.wangtong.gowin120.patient.ui.login.RegisterActivity;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;
import lht.wangtong.gowin120.patient.util.LoginUtil;

/**
 * @author luoyc
 * @date 2018/3/8
 */

public class MineInfoPresenter extends BasePresenter<MineInfoContract.View> implements MineInfoContract.Presenter {
    private List<String> textInfos;
    private OptionsPickerView pvOptions;
    private List<CommonCdInfo> cdInfoList;

    @Inject
    public MineInfoPresenter() {

    }


    @Override
    public void initData() {
        textInfos = new ArrayList<>();
        cdInfoList = new ArrayList<>();
        cdInfoList = CommonInfoUtils.getCommonCdInfo("AgeGroup");
        for (CommonCdInfo info : cdInfoList) {
            textInfos.add(info.getNameZh());
        }
    }

    @Override
    public void showAgePicker() {
        //条件选择器
        if (pvOptions == null) {
            //条件选择器
            pvOptions = new OptionsPickerBuilder(mView.getThisContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    for (int i = 0; i < cdInfoList.size(); i++) {
                        if (TextUtils.equals(textInfos.get(options1), cdInfoList.get(i).getNameZh())) {
                            mView.setAge(cdInfoList.get(i));
                        }
                    }
                }
            }).setSubmitText("确定")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setSubCalSize(18)//确定和取消文字大小
                    .setContentTextSize(20)//滚轮文字大小
                    .isCenterLabel(true)
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(mView.getThisContext().getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(mView.getThisContext().getResources().getColor(R.color.time_text_color))//取消按钮文字颜色;
                    .build();
            pvOptions.setPicker(textInfos);
            pvOptions.show();
        }else {
            pvOptions.show();
        }
    }

    @Override
    public void saveHeadPic(final String userPic) {
        HttpUtil.upLoad(Api.UPDATEAVATAR.mapClear()
                .setFileTitle("avatar")
                .setFileUrl(userPic)
                .addBody(), UserHeadInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    UserHeadInfo headInfo = (UserHeadInfo) obj;
                    User oldUser = SQLite.select().from(User.class).querySingle();
                    if (oldUser != null) {
                        oldUser.setPicUrl(headInfo.getPicUrl());
                        oldUser.update();
                    }
                    LoginUtil.user.setPicUrl(headInfo.getPicUrl());
                    EventBus.getDefault().postSticky(new UserLoginEvent(false, true));
                    ToastUtils.showShort(headInfo.getResultVO().getMsg());
                }
            }
        });
    }

    @Override
    public void saveMemberInfo(final User memberInfo) {
        if (checkData(memberInfo)) {
            HttpUtil.getObject(Api.UPDATEMEMBER.mapClear()
                    .addMap("memberName", memberInfo.getMemberName())
                    .addMap("ageGroup", memberInfo.getAgeGroup())
                    .addBody(), AgeGroupTypeInfo.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        AgeGroupTypeInfo groupTypeInfo = (AgeGroupTypeInfo) obj;
                        User oldUser = SQLite.select().from(User.class).querySingle();
                        if (oldUser != null) {
                            oldUser.setMemberName(memberInfo.getMemberName());
                            oldUser.setAgeGroup(memberInfo.getAgeGroup());
                            oldUser.setAgeGroupType(groupTypeInfo.getAgeGroupType());
                            oldUser.update();
                        }
                        LoginUtil.user.setAgeGroup(memberInfo.getAgeGroup());
                        LoginUtil.user.setMemberName(memberInfo.getMemberName());
                        LoginUtil.user.setAgeGroupType(groupTypeInfo.getAgeGroupType());
                        EventBus.getDefault().postSticky(new UserLoginEvent(false, true));
                        mView.finishThisActivity();
                    }
                }
            });
        }
    }

    @Override
    public void startUse(final User memberInfo) {
        if (checkData(memberInfo)) {
            HttpUtil.getObject(Api.UPDATEMEMBER.mapClear()
                    .addMap("memberName", memberInfo.getMemberName())
                    .addMap("ageGroup", memberInfo.getAgeGroup())
                    .addBody(), AgeGroupTypeInfo.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        JPushInterface.setAlias(mView.getThisContext(), 40, memberInfo.getLoginName());
                        AgeGroupTypeInfo groupTypeInfo = (AgeGroupTypeInfo) obj;
                        memberInfo.setAgeGroupType(groupTypeInfo.getAgeGroupType());
                        memberInfo.save();
                        LoginUtil.user.setAgeGroup(memberInfo.getAgeGroup());
                        LoginUtil.user.setMemberName(memberInfo.getMemberName());
                        LoginUtil.user.setAgeGroupType(groupTypeInfo.getAgeGroupType());
                        EventBus.getDefault().postSticky(new UserLoginEvent(true, false));
                        if (TextUtils.equals(LoginUtil.user.getIsLogined(), "Y")) {
                            ARouter.getInstance().build("/ui/MainActivity").navigation();
                        } else if (TextUtils.equals(LoginUtil.user.getIsLogined(), "N")) {
                            ARouter.getInstance().build("/mine/coupons/ReceiveCouponActivity")
                                    .withInt("mType", 1)
                                    .navigation();
                        }
                        ActivityUtils.finishActivity(LoginActivity.class);
                        ActivityUtils.finishActivity(AuthLoginActivity.class);
                        ActivityUtils.finishActivity(RegisterActivity.class);
                        mView.finishThisActivity();
                    }
                }
            });
        }
    }

    private boolean checkData(User memberInfo) {
        if (TextUtils.isEmpty(memberInfo.getMemberName())) {
            ToastUtils.showShort("请填写昵称");
            return false;
        }
        if (memberInfo.getMemberName().length() < 2) {
            ToastUtils.showShort("昵称限制2-8个字");
            return false;
        }
        if (TextUtils.isEmpty(memberInfo.getAgeGroup())) {
            ToastUtils.showShort("请选择年龄段");
            return false;
        }
        return true;
    }

}
