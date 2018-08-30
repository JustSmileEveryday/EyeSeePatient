package lht.wangtong.gowin120.patient.ui.home.tryglasses;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.GlassesAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.GlassesInfo;
import lht.wangtong.gowin120.patient.tencent.utils.FileUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 试镜间
 *
 * @author luoyc
 */
@Route(path = "/home/tryglasses/TryGlassesActivity")
public class TryGlassesActivity extends BaseActivity<TryGlassesPresenter> implements TryGlassesContact.View
        , View.OnClickListener, GlassesAdapter.OnItemClickListener {

    public static final int PERM_RQST_CODE = 110;
    private static final String TAG = TryGlassesActivity.class.getSimpleName();
    @BindView(R.id.photo_view)
    ImageView photoView;
    StickerView stickerView;
    @BindView(R.id.glasses_list_view)
    RecyclerView glassesListView;
    @BindView(R.id.buy_btn)
    LinearLayout buyBtn;
    @Autowired
    String photoUrl;
    @Inject
    GlassesAdapter glassesAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_try_glasses;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        glassesListView.setLayoutManager(layoutManager);
        glassesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        glassesAdapter.setOnItemClickListener(this);
        glassesListView.setAdapter(glassesAdapter);
        TextView rightText = findViewById(R.id.right_text);
        stickerView = findViewById(R.id.sticker_view);
        rightText.setTextColor(Color.parseColor("#01C1B4"));
        rightText.setText("保存/分享");
        rightText.setVisibility(View.VISIBLE);
        initData();
        initStickerView();
        mPresenter.getGlassesList();
    }

    @Override
    public void initStickerView() {
        //currently you can config your own icons and icon event
        //the event you can custom
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_close_white), BitmapStickerIcon.RIGHT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());
        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_scale_white),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_flip_white),
                BitmapStickerIcon.LEFT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());
        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon));
        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(false);
    }

    @Override
    public void initData() {
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
                        Log.e(TAG, "onFailure: " + code + msg);
                    }
                });
    }

    @Override
    public void setGlasses(List<GlassesInfo> glassesInfos) {
        glassesAdapter.setNewData(glassesInfos);
    }

    @Override
    public void loadSticker(Drawable drawable) {
        stickerView.removeAllStickers();
        stickerView.addSticker(new DrawableSticker(drawable), Sticker.Position.CENTER);
    }

    @OnClick({R.id.right_text, R.id.buy_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_text:
                //保存
                File file = FileUtil.getNewFile(this, "Sticker");
                if (file != null) {
                    stickerView.save(file);
                    ARouter.getInstance()
                            .build("/home/tryglasses/TrySuccessActivity")
                            .withString("photoUrl", file.getAbsolutePath())
                            .navigation();
                } else {
                    Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buy_btn:
                buySomething();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GlassesInfo glassesInfo = (GlassesInfo) adapter.getData().get(position);
        loadSticker(glassesInfo.getDrawable());
    }
}
