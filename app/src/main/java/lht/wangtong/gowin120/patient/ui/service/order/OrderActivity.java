package lht.wangtong.gowin120.patient.ui.service.order;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.PayTypeAdapter;
import lht.wangtong.gowin120.patient.apppay.alipay.AlipayInfo;
import lht.wangtong.gowin120.patient.apppay.alipay.OrderInfoUtil;
import lht.wangtong.gowin120.patient.apppay.alipay.PayResult;
import lht.wangtong.gowin120.patient.apppay.weixinpay.PaySuccessInfo;
import lht.wangtong.gowin120.patient.apppay.weixinpay.WeiXinPayInfo;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.PayTypeInfo;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;

/**
 * 服务订单
 *
 * @author luoyc
 */
@Route(path = "/service/order/OrderActivity")
public class OrderActivity extends BaseActivity<OrderPresenter> implements OrderContract.View {
    private static final int SDK_PAY_FLAG = 1;
    @Autowired
    public String serviceRecordId;
    @BindView(R.id.pay_list)
    RecyclerView mPayRecyclerView;
    @BindView(R.id.service_name)
    TextView mServiceName;
    @BindView(R.id.service_price)
    TextView mServicePrice;
    @BindView(R.id.effective_count)
    TextView mEffectiveCount;
    @BindView(R.id.service_agent_name)
    TextView mServiceAgentName;
    @BindView(R.id.sure_pay_btn)
    TextView mSureBtn;
    @Inject
    PayTypeAdapter payTypeAdapter;
    private String payCode = "";
    private int typeFlag = 0;

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
        return R.layout.activity_order;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mPayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPayRecyclerView.setAdapter(payTypeAdapter);
        initItemClick();
        mPresenter.initData(serviceRecordId);
    }

    @Override
    public void initItemClick() {
        payTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSureBtn.setBackgroundResource(R.drawable.green_btn_bg_5);
                mSureBtn.setClickable(true);
                PayTypeInfo payTypeInfo = (PayTypeInfo) adapter.getData().get(position);
                payCode = payTypeInfo.getCode();
                typeFlag = payTypeInfo.getTypeFlag();
                if (payTypeInfo.isChecked()) {
                    return;
                } else {
                    payTypeInfo.setChecked(true);
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        if (i != position) {
                            ((PayTypeInfo) adapter.getData().get(i)).setChecked(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setPayType(List<PayTypeInfo> payTypeInfos) {
        payTypeAdapter.setNewData(payTypeInfos);
        payTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setServiceInfo(ServiceRecordInfo serviceInfo) {
        mServiceName.setText(serviceInfo.getServiceTitle());
        mServicePrice.setText("￥" + serviceInfo.getPrice());
        mEffectiveCount.setText(serviceInfo.getEffectiveCount() + "次");
        mSureBtn.setText("确认支付￥" + serviceInfo.getPrice());
        mServiceAgentName.setText("购买成功后到下列门店消费即可\n" + serviceInfo.getServiceAgentName());
    }

    @Override
    public void payService() {
        switch (typeFlag) {
            case 0:
                //余额支付
                mPresenter.balancePay(serviceRecordId);
                break;
            case 1:
                //微信支付
                if (!App.mWXAPI.isWXAppInstalled()) {
                    ToastUtils.showShort("您还未安装微信客户端");
                } else {
                    mPresenter.getWeiXinPayParms(serviceRecordId, payCode);
                }
                break;
            case 2:
                //支付宝支付
                mPresenter.getAliPayParms(serviceRecordId, payCode);
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
                PayTask alipay = new PayTask(OrderActivity.this);
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

    @Override
    public void payFailured() {
        new MaterialDialog.Builder(this)
                .title("支付说明")
                .content("用户支付失败或取消支付")
                .titleColor(Color.parseColor("#3C4045"))
                .contentColor(Color.parseColor("#797E83"))
                .positiveText(R.string.sure)
                .positiveColor(Color.parseColor("#01C1B4"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void paySuccess() {
        ARouter.getInstance().build("/mine/service/MyServiceDetailActivity")
                .withString("serviceRecordId", serviceRecordId)
                .navigation();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWeiXinEvent(PaySuccessInfo info) {
        if (info != null) {
            if (info.isSuccess()) {
                paySuccess();
            }
        }
    }


    @OnClick(R.id.sure_pay_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure_pay_btn:
                payService();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
