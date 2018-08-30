package lht.wangtong.gowin120.patient.ui.service;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.ui.service.category.ServiceCategoryFragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 服务 fragment
 *
 * @author luoyc
 * @date 2018/3/5
 */

public class ServiceFragment extends BaseFragment<ServicePresenter> implements ServiceContract.View, BGABanner.Adapter<ImageView, BannerInfo>, BGABanner.Delegate<ImageView, BannerInfo> {
    @BindView(R.id.service_banner)
    BGABanner mBanner;
    @BindView(R.id.service_tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.service_pager)
    ViewPager mViewPager;
    @BindView(R.id.item_layout)
    LinearLayout mItemLayout;
    private ConstraintLayout.LayoutParams mParams;

    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        initBanner();
        mPresenter.loadServiceBanner();
        mPresenter.loadCategorys();
//        showGuide();
    }

    @Override
    public void initBanner() {
        float Rate; //宽高比
        int ImgH = 480; //广告图片高度
        int ImgW = 1080; //广告图片宽度
        Rate = ImgW * 1000 / ImgH; //当前比例
        WindowManager wm = (WindowManager) getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        mParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (int) (width * 1000 / Rate));
        mBanner.setLayoutParams(mParams);
        mBanner.setAdapter(this);
        mBanner.setDelegate(this);
        mBanner.setIsNeedShowIndicatorOnOnlyOnePage(false);
        mBanner.setAutoPlayInterval(5000);
        mBanner.setAllowUserScrollable(true);
    }

    @Override
    public void setServiceBanner(List<BannerInfo> bannerInfos) {
        mBanner.setAutoPlayAble(bannerInfos.size() > 1);
        mBanner.setData(bannerInfos, null);
    }

    @Override
    public void setCategorys(List<CommonCdInfo> categorys) {
        String[] titles = new String[categorys.size()];
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < categorys.size(); i++) {
            titles[i] = categorys.get(i).getNameZh();
            ServiceCategoryFragment fragment = ServiceCategoryFragment.newInstance();
            fragment.setCode(categorys.get(i).getCode());
            fragments.add(fragment);
        }
        mTabLayout.setViewPager(mViewPager, titles, getActivity(), fragments);
    }

    @OnClick(R.id.search_btn)
    @Override
    public void searchService() {
        ARouter.getInstance().build("/ui/search/SearchActivity")
                .withInt("mType", 2)
                .navigation();
    }

    @Override
    public void showGuide() {
        NewbieGuide.with(getActivity())
                .setLabel("service_guide_1"+ Constant.APPVERSION)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(mTabLayout, HighLight.Shape.RECTANGLE)
                        .setLayoutRes(R.layout.service_guide_layout_1, R.id.guide_next_btn)
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                BGABanner mGuideBannerAds = view.findViewById(R.id.service_banner);
                                mGuideBannerAds.setLayoutParams(mParams);
                            }
                        }))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(mItemLayout, HighLight.Shape.ROUND_RECTANGLE,20,0)
                        .setLayoutRes(R.layout.service_guide_layout_2, R.id.kown_btn)
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                BGABanner mGuideBannerAds = view.findViewById(R.id.service_banner);
                                mGuideBannerAds.setLayoutParams(mParams);
                            }
                        }))
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        mItemLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        mItemLayout.setVisibility(View.GONE);
                    }
                })
                .show();
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerInfo model, int position) {
        Glide.with(this)
                .load(Api.HOST_IMG + model.getImgUrl())
                .apply(new RequestOptions().error(R.drawable.home_banner_error_img))
                .transition(withCrossFade())
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerInfo model, int position) {

    }
}
