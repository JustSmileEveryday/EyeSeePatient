package lht.wangtong.gowin120.patient.ui.mine;

import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CouponInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author Luoyc
 * @date 2018/3/5
 */

public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    @Inject
    public MinePresenter() {
    }


    @Override
    public void getMemberInfo() {
        User oldUser = SQLite.select().from(User.class).querySingle();
        if (oldUser != null) {
            mView.setUserData(oldUser);
        }
    }

    @Override
    public void getCanGetCouponList() {
        HttpUtil.getObject(Api.getCanGetCouponList.mapClear()
                .addBody(), CouponInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    CouponInfo couponInfo = (CouponInfo) obj;
                    mView.setCanGetCoupon(couponInfo.getCount());
                }
            }
        });
    }
}
