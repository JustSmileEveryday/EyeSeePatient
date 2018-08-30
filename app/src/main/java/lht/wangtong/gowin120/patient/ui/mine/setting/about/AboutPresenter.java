package lht.wangtong.gowin120.patient.ui.mine.setting.about;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.HospitalDetailInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 *
 * @author luoyc
 * @date 2018/3/26
 */

public class AboutPresenter extends BasePresenter<AboutContact.View> implements AboutContact.Presenter {

    @Inject
    public AboutPresenter() {
    }

    @Override
    public void loadAbout() {
        HttpUtil.getObject(Api.GETHOSPITALBYID.mapClear()
                .addBody(), HospitalDetailInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, Object obj) {
                if (b) {
                    HospitalDetailInfo info = (HospitalDetailInfo) obj;
                    mView.setAbout(info);
                }
            }
        });
    }
}
