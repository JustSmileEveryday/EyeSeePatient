package lht.wangtong.gowin120.patient.ui.home.store;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CatalogInfo;
import lht.wangtong.gowin120.patient.ui.science.category.CategoryFragment;

/**
 * 小艾商城
 *
 * @author luoyc
 */
public class StoreActivity extends BaseActivity<StorePresenter> implements StoreContact.View, BGABanner.Delegate<ImageView, BannerInfo> {

    @BindView(R.id.store_banner)
    BGABanner storeBanner;
    @BindView(R.id.search_store)
    EditText searchStore;
    @BindView(R.id.store_tab)
    SlidingTabLayout storeTab;
    @BindView(R.id.store_pager)
    ViewPager storePager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        initBanner();
        mPresenter.initData();
    }

    @Override
    public void initBanner() {
        //宽高比
        float rate;
        //广告图片高度
        int imgH = 120;
        //广告图片宽度
        int imgW = 340;
        //当前比例
        rate = imgW * 1000 / imgH;
        WindowManager wm = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams mBannerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (width * 1000 / rate));
        storeBanner.setLayoutParams(mBannerParams);
        storeBanner.setDelegate(this);
        storeBanner.setAutoPlayAble(true);
        storeBanner.setIsNeedShowIndicatorOnOnlyOnePage(false);
        storeBanner.setAutoPlayInterval(5000);
        storeBanner.setAllowUserScrollable(true);
    }

    @Override
    public void setBanners(List<BannerInfo> banners) {
        storeBanner.setAutoPlayAble(banners.size() > 1);
        storeBanner.setData(banners, null);
    }

    @Override
    public void setCategorys(List<CatalogInfo> categorys) {
        String[] titles = new String[categorys.size()];
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < categorys.size(); i++) {
            titles[i] = categorys.get(i).getName();
            StoreListFragment fragment = StoreListFragment.newInstance();
            fragments.add(fragment);
        }
        storeTab.setViewPager(storePager, titles, this, fragments);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerInfo model, int position) {

    }
}
