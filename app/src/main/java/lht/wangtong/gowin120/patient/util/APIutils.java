package lht.wangtong.gowin120.patient.util;

import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.util.List;

import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.db.Initdb;
import lht.wangtong.gowin120.patient.db.VersionInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;


/**
 * @author luoyc
 * @date 2016/11/8
 */
public class APIutils {
    ;

    /**
     * 获取公共数据字典
     */
    public static synchronized void getCommonCd() {
        HttpUtil.getObjectList(Api.COMMONCD.mapClear().addBody(), CommonCdInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    try {
                        Initdb.db.delete(CommonCdInfo.class);
                        Initdb.db.save((List<CommonCdInfo>) obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 版本检查
     */
    public static void getVersion(final ICallbackTab callback) {
        HttpUtil.getObject(Api.GETVERSION.mapClear().addBody(), VersionInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    try {
                        Initdb.db.delete(VersionInfo.class);
                        Initdb.db.save((VersionInfo) obj);
                        callback.callBackTab(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
