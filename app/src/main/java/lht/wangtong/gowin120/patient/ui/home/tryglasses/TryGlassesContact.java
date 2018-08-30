package lht.wangtong.gowin120.patient.ui.home.tryglasses;

import android.graphics.drawable.Drawable;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.GlassesInfo;

/**
 * @author luoyc
 */
public interface TryGlassesContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initStickerView();

        void initData();

        void buySomething();

        void setGlasses(List<GlassesInfo> glassesInfos);

        void loadSticker(Drawable drawable);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getGlassesList();

    }
}
