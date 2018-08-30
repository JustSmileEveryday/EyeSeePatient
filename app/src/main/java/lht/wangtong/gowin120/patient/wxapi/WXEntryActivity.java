package lht.wangtong.gowin120.patient.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.mWXAPI.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:

                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:

                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (!TextUtils.isEmpty(baseResp.transaction)) {
                    addShareTime(baseResp.transaction);
                }
                ToastUtils.showShort("分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.showShort("取消发送");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.showShort("分享失败");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                ToastUtils.showShort("不支持分享类型");
                break;
            default:
                break;
        }
        finish();
    }

    private void addShareTime(String id) {
        if (id.contains("-")) {
            String[] ids = id.split("-");
            HttpUtil.getObject(Api.addArticleShareCount.mapClear()
                    .addMap("articleId", ids[0])
                    .addBody(), String.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    super.result(b, obj);
                }
            });
            socialStatistics(ids[1], ids[0]);
        } else {
            socialStatistics(id, "");
        }

    }

    private void socialStatistics(String shareType, String articleId) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (shareType.equals("1")) {
            map.put("shareType", "WX");
        } else if (shareType.equals("2")) {
            map.put("shareType", "WXFriend");
        }
        if (TextUtils.isEmpty(articleId)) {
            map.put("articleId", articleId);
        }
        MobclickAgent.onEvent(this, "share", map);
    }
}