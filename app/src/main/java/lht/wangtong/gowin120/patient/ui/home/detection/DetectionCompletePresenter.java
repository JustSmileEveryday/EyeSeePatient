package lht.wangtong.gowin120.patient.ui.home.detection;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeHealthData;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/20
 */

public class DetectionCompletePresenter extends BasePresenter<DetectionCompleteContact.View> implements DetectionCompleteContact.Presenter {

    @Inject
    public DetectionCompletePresenter() {
    }

    @Override
    public void saveEyeHealthData(EyeHealthData healthData) {
        HttpUtil.getObject(Api.saveEyeHealthData.mapClear()
                .addMap("dataType", healthData.getDataType() + "")
                .addMap("dataValue", healthData.getDataValue())
                .addMap("familyId", healthData.getFamilyId())
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    ToastUtils.showShort("保存成功");
                }
            }
        });
    }
}
