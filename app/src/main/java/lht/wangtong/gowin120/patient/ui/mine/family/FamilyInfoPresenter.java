package lht.wangtong.gowin120.patient.ui.mine.family;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;


/**
 * @author Luoyc
 * @date 2018/3/15
 */

public class FamilyInfoPresenter extends BasePresenter<FamilyInfoContact.View> implements FamilyInfoContact.Presenter {
    private List<String> textInfos;
    private OptionsPickerView pvOptions;
    private List<CommonCdInfo> ageCdInfoList;
    private List<CommonCdInfo> relationshipCdInfoList;
    private int type = -1;

    @Inject
    public FamilyInfoPresenter() {
    }


    @Override
    public void initData() {
        textInfos = new ArrayList<>();
        if (pvOptions == null) {
            pvOptions = new OptionsPickerBuilder(mView.getThisContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    switch (type) {
                        case 1:
                            for (int i = 0; i < ageCdInfoList.size(); i++) {
                                if (TextUtils.equals(textInfos.get(options1), ageCdInfoList.get(i).getNameZh())) {
                                    mView.setAge(ageCdInfoList.get(i));
                                }
                            }
                            break;
                        case 2:
                            for (int i = 0; i < relationshipCdInfoList.size(); i++) {
                                if (TextUtils.equals(textInfos.get(options1), relationshipCdInfoList.get(i).getNameZh())) {
                                    mView.setRelationship(relationshipCdInfoList.get(i));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            })
                    .setSubmitText("确定")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setSubCalSize(18)//确定和取消文字大小
                    .setContentTextSize(20)//滚轮文字大小
                    .isCenterLabel(true)
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(mView.getThisContext().getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(mView.getThisContext().getResources().getColor(R.color.time_text_color))//取消按钮文字颜色
                    .build();
        }
    }

    @Override
    public void showAgePicker() {
        type = 1;
        textInfos.clear();
        ageCdInfoList = CommonInfoUtils.getCommonCdInfo("AgeGroup");
        for (CommonCdInfo info : ageCdInfoList) {
            textInfos.add(info.getNameZh());
        }
        pvOptions.setPicker(textInfos);
        KeyboardUtils.hideSoftInput(mView.getThisContext());
        pvOptions.show();
    }

    @Override
    public void showRelationPicker() {
        type = 2;
        textInfos.clear();
        relationshipCdInfoList = CommonInfoUtils.getCommonCdInfo("FamilyRelationship");
        for (CommonCdInfo info : relationshipCdInfoList) {
            textInfos.add(info.getNameZh());
        }
        pvOptions.setPicker(textInfos);
        KeyboardUtils.hideSoftInput(mView.getThisContext());
        pvOptions.show();
    }

    @Override
    public void saveInfo(final HomeFamilyInfo familyInfo) {
        if (checkData(familyInfo)) {
            HttpUtil.upLoad(Api.updateFamilyInfo.mapClear()
                    .addMap("familyId", familyInfo.getFamilyId())
                    .addMap("relationship", familyInfo.getRelationship())
                    .addMap("name", familyInfo.getName())
                    .addMap("ageGroup", familyInfo.getAgeGroup())
                    .addMap("mobilePhone", familyInfo.getMobilePhone())
                    .setFileTitle("headPortrait")
                    .setFileUrl(familyInfo.getNewPicUrl())
                    .addBody(), String.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        if (FileUtils.isFileExists(familyInfo.getNewPicUrl())) {
                            FileUtils.deleteFile(familyInfo.getNewPicUrl());
                        }
                        mView.changeFinsh();
                    }
                }
            });
        }
    }

    @Override
    public void deleteFamily(String familyId) {
        HttpUtil.getObject(Api.deleteFamilyInfo.mapClear()
                .addMap("familyId", familyId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.changeFinsh();
                }
            }
        });
    }

    private boolean checkData(HomeFamilyInfo familyInfo) {
        if (TextUtils.isEmpty(familyInfo.getName())) {
            ToastUtils.showShort("请填写昵称");
            return false;
        }
        if (TextUtils.isEmpty(familyInfo.getRelationship())) {
            ToastUtils.showShort("请选择成员关系");
            return false;
        }
        if (TextUtils.isEmpty(familyInfo.getAgeGroup())) {
            ToastUtils.showShort("请选择年龄段");
            return false;
        }
        return true;
    }
}
