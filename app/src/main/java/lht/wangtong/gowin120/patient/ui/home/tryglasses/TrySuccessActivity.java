package lht.wangtong.gowin120.patient.ui.home.tryglasses;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.util.ShareUtils;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 试镜间保存
 *
 * @author luoyc
 */

@Route(path = "/home/tryglasses/TrySuccessActivity")
public class TrySuccessActivity extends BaseActivity<TrySuccessPresenter> implements TrySuccessContact.View {

    @BindView(R.id.photo_view)
    ImageView photoView;
    @Autowired
    String photoUrl;
    private UMImage umImage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_try_success;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(photoUrl)) {
            Glide.with(this)
                    .load(new File(photoUrl))
                    .transition(withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            photoView.setImageDrawable(resource);
                        }
                    });
            umImage = new UMImage(this, new File(photoUrl));
        }
    }

    @OnClick({R.id.buy_btn, R.id.buck_home_btn, R.id.wx_friend, R.id.wx_friend_scene, R.id.qq_friend, R.id.sina_share_btn, R.id.qq_zone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_btn:
                buySomething();
                break;
            case R.id.buck_home_btn:
                //返回首页
                ActivityUtils.finishActivity(TryGlassesActivity.class);
                finish();
                break;
            case R.id.wx_friend:
                if (umImage != null) {
                    ShareUtils.getInstance().share(this, umImage, SHARE_MEDIA.WEIXIN);
                }
                break;
            case R.id.wx_friend_scene:
                if (umImage != null) {
                    ShareUtils.getInstance().share(this, umImage, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                break;
            case R.id.qq_friend:
                if (umImage != null) {
                    ShareUtils.getInstance().share(this, umImage, SHARE_MEDIA.QQ);
                }
                break;
            case R.id.sina_share_btn:
                if (umImage != null) {
                    ShareUtils.getInstance().share(this, umImage, SHARE_MEDIA.SINA);
                }
                break;
            case R.id.qq_zone:
                if (umImage != null) {
                    ShareUtils.getInstance().share(this, umImage, SHARE_MEDIA.QZONE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void buySomething() {
        //去购买
        //实例化店铺打开page
        AlibcBasePage shopPage = new AlibcShopPage("193271048");
        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);
        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(this, shopPage, showParams, null, null,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                        Log.e("luoyc", "onFailure: " + code + msg);
                    }
                });
    }
}
