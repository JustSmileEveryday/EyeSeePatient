package lht.wangtong.gowin120.patient.ui.mine.collection;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/14
 */
public class MyCollectionPresenter extends BasePresenter<MyCollectionContact.View> implements MyCollectionContact.Presenter {
    private int mPageNo = 1;
    private List<ScienceCategoryInfo> infoList;

    @Inject
    public MyCollectionPresenter() {
    }

    @Override
    public void loadCollectionLits() {
        HttpUtil.getObjectListPage(Api.myCollect
                .mapClear()
                .addPage(10, mPageNo)
                .addBody(), ScienceCategoryInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b){
                    infoList.addAll((List<ScienceCategoryInfo>) obj);
                    mView.setCollectionLits(infoList,total);
                }
            }
        });
    }

    @Override
    public void initData() {
        infoList = new ArrayList<>();
        loadCollectionLits();
    }

    @Override
    public void loadMore() {
        mPageNo++;
        loadCollectionLits();
    }

    @Override
    public void onRefresh() {
        mPageNo = 1;
        infoList.clear();
        loadCollectionLits();
    }
}
