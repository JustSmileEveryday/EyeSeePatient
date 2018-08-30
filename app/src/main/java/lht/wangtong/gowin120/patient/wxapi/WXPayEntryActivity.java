package lht.wangtong.gowin120.patient.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.apppay.weixinpay.PaySuccessInfo;
import lht.wangtong.gowin120.patient.config.Constant;

/**
 * @author luoyc
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //支付成功
                EventBus.getDefault().post(new PaySuccessInfo(true));
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                payFailured();
                break;
            default:
                break;
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 支付失败
     */
    private void payFailured() {
        new MaterialDialog.Builder(this)
                .title("支付说明")
                .content("用户支付失败或取消支付")
                .titleColor(Color.parseColor("#3C4045"))
                .contentColor(Color.parseColor("#797E83"))
                .backgroundColor(Color.parseColor("#ffffff"))
                .positiveText(R.string.sure)
                .positiveColor(Color.parseColor("#01C1B4"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }
}