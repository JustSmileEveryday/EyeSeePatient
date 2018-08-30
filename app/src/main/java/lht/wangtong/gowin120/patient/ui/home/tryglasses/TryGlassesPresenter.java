package lht.wangtong.gowin120.patient.ui.home.tryglasses;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.GlassesInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class TryGlassesPresenter extends BasePresenter<TryGlassesContact.View> implements TryGlassesContact.Presenter {

    @Inject
    public TryGlassesPresenter() {
    }

    @Override
    public void getGlassesList() {
        HttpUtil.getObjectList(Api.getGlassesList.mapClear()
                .addBody(), GlassesInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<GlassesInfo> glassesInfos = new ArrayList<>();
                    glassesInfos.addAll((Collection<? extends GlassesInfo>) obj);
                    mView.setGlasses(glassesInfos);
                }
            }
        });
    }
}
