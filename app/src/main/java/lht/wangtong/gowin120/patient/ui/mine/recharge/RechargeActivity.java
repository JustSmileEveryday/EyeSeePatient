package lht.wangtong.gowin120.patient.ui.mine.recharge;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.RechargeNumAdapter;
import lht.wangtong.gowin120.patient.adapter.RechargeTypeAdapter;
import lht.wangtong.gowin120.patient.apppay.alipay.AlipayInfo;
import lht.wangtong.gowin120.patient.apppay.alipay.OrderInfoUtil;
import lht.wangtong.gowin120.patient.apppay.alipay.PayResult;
import lht.wangtong.gowin120.patient.apppay.weixinpay.PaySuccessInfo;
import lht.wangtong.gowin120.patient.apppay.weixinpay.WeiXinPayInfo;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.bean.RechargeNumInfo;

/**
 * 充值余额
 *
 * @author luoyc
 */
@Route(path = "/mine/recharge/RechargeActivity")
public class RechargeActivity extends BaseActivity<RechargePresenter> implements RechargeContract.View {
    private static final int SDK_PAY_FLAG = 1001;
    @BindView(R.id.recharge_rv)
    RecyclerView mRechargeRv;
    @BindView(R.id.pay_mode_rv)
    RecyclerView mPayModeRv;
    @BindView(R.id.recharge_btn)
    TextView mRechargeBtn;
    @Inject
    RechargeNumAdapter mNumAdapter;
    @Inject
    RechargeTypeAdapter mTypeAdapter;
    private String payCode = "";
    /**
     * 充值积分主键
     */
    private String rechargeIntegralId = "";
    private int typeFlag = 0;
    /**
     * 充值金额
     */
    private String payNum = "";


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        paySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        payFailured();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mNumAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRechargeRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRechargeRv.setAdapter(mNumAdapter);
        mTypeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mPayModeRv.setLayoutManager(new GridLayoutManager(this, 2));
        mPayModeRv.setAdapter(mTypeAdapter);
        initAdapter();
        mPresenter.initData();
    }

    @Override
    public void initAdapter() {
        mNumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (Object info : adapter.getData()) {
                    ((RechargeNumInfo) info).setChoose(false);
                }
                ((RechargeNumInfo) adapter.getData().get(position)).setChoose(true);
                rechargeIntegralId = ((RechargeNumInfo) adapter.getData().get(position)).getRechargeIntegralId();
                payNum = ((RechargeNumInfo) adapter.getData().get(position)).getMoney();
                mRechargeBtn.setClickable(true);
                mRechargeBtn.setText("确认充值￥" + ((RechargeNumInfo) adapter.getData().get(position)).getMoney());
                adapter.notifyDataSetChanged();
            }
        });
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (Object info : adapter.getData()) {
                    ((PayTypeInfo) info).setChecked(false);
                }
                ((PayTypeInfo) adapter.getData().get(position)).setChecked(true);
                payCode = ((PayTypeInfo) adapter.getData().get(position)).getCode();
                typeFlag = ((PayTypeInfo) adapter.getData().get(position)).getTypeFlag();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setRechargeList(List<RechargeNumInfo> rechargeNumInfos) {
        mNumAdapter.setNewData(rechargeNumInfos);
    }

    @Override
    public void setPayType(List<PayTypeInfo> payTypeInfos) {
        mTypeAdapter.setNewData(payTypeInfos);
    }

    @OnClick(R.id.recharge_btn)
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(rechargeIntegralId)) {
            RxToast.error("请选择充值金额");
            return;
        }
        if (TextUtils.isEmpty(payCode)){
            RxToast.error("请选择充值方式");
            return;
        }
        switch (view.getId()) {
            case R.id.recharge_btn:
                //充值
                if (typeFlag == 1) {
                    //微信支付
                    if (!App.mWXAPI.isWXAppInstalled()) {
                        ToastUtils.showShort("您还未安装微信客户端");
                    } else {
                        mPresenter.getWeiXinPayParms(rechargeIntegralId, payCode);
                    }
                } else if (typeFlag == 2) {
                    //支付宝支付
                    mPresenter.getAliPayParms(rechargeIntegralId, payCode);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void weixinPay(WeiXinPayInfo payInfo) {
        PayReq request = new PayReq();

        request.appId = payInfo.getAppid();

        request.partnerId = payInfo.getPartnerid();

        request.prepayId = payInfo.getPrepayid();

        request.packageValue = payInfo.getPackageValue();

        request.nonceStr = payInfo.getNoncestr();

        request.timeStamp = payInfo.getTimestamp();

        request.sign = payInfo.getSign();

        App.mWXAPI.sendReq(request);
    }

    @Override
    public void aliPay(AlipayInfo alipayInfo) {
        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(alipayInfo);
        final String orderParam = OrderInfoUtil.buildOrderParam(params) + "&sign=" + alipayInfo.getSign();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderParam, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSuccessInfo(PaySuccessInfo info) {
        if (info != null) {
            if (info.isSuccess()) {
                paySuccess();
            }
        }
    }

    @Override
    public void payFailured() {
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

    @Override
    public void paySuccess() {
        switch (typeFlag) {
            case 1:
                //微信支付
                ARouter.getInstance().build("/mine/recharge/RechargeSuccActivity")
                        .withString("payType", "微信")
                        .withString("payNum", payNum)
                        .navigation();
                break;
            case 2:
                //支付宝支付
                ARouter.getInstance().build("/mine/recharge/RechargeSuccActivity")
                        .withString("payType", "支付宝")
                        .withString("payNum", payNum)
                        .navigation();
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
