package lht.wangtong.gowin120.patient.ui.science;

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
import lht.wangtong.gowin120.patient.bean.CatalogInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.ui.science.category.CategoryFragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 科普fragment
 *
 * @author luoyc
 * @date 2018/3/5
 */

public class ScienceFragment extends BaseFragment<SciencePresenter> implements ScienceContract.View, BGABanner.Adapter<ImageView, BannerInfo>, BGABanner.Delegate<ImageView, BannerInfo> {
    @BindView(R.id.science_banner)
    BGABanner mBanner;
    @BindView(R.id.category_tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.science_pager)
    ViewPager mViewPager;
    @BindView(R.id.item_layout)
    LinearLayout mItemLayout;
    private ConstraintLayout.LayoutParams mParams;

    public static ScienceFragment newInstance() {
        return new ScienceFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_science;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        initBanner();
        mPresenter.loadScienceBanner();
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
    public void setScienceBanner(List<BannerInfo> bannerInfos, List<String> tips) {
        mBanner.setAutoPlayAble(bannerInfos.size() > 1);
        mBanner.setData(bannerInfos, tips);
    }

    @Override
    public void setCategorys(List<CatalogInfo> categorys) {
        String[] titles = new String[categorys.size()];
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < categorys.size(); i++) {
            titles[i] = categorys.get(i).getName();
            CategoryFragment fragment = CategoryFragment.newInstance();
            fragment.setCatalogId(categorys.get(i).getCatalogId());
            fragments.add(fragment);
        }
        mTabLayout.setViewPager(mViewPager, titles, getActivity(), fragments);
    }

    @OnClick(R.id.search_btn)
    @Override
    public void searchArticle() {
        ARouter.getInstance().build("/ui/search/SearchActivity")
                .withInt("mType", 1)
                .navigation();
    }

    @Override
    public void showGuide() {
        NewbieGuide.with(getActivity())
                .setLabel("science_guide" + Constant.APPVERSION)
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
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(mItemLayout, HighLight.Shape.ROUND_RECTANGLE, 20, 0)
                        .setLayoutRes(R.layout.science_guide_layout, R.id.kown_btn)
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                BGABanner mGuideBannerAds = view.findViewById(R.id.science_banner);
                                mGuideBannerAds.setLayoutParams(mParams);
                            }
                        }))
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
