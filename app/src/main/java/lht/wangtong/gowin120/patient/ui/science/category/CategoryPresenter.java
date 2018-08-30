package lht.wangtong.gowin120.patient.ui.science.category;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/6
 */

public class CategoryPresenter extends BasePresenter<CategoryContract.View> implements CategoryContract.Presenter {
    private int pageNo = 1;
    private List<ScienceCategoryInfo> categoryInfos;

    @Inject
    public CategoryPresenter() {

    }


    @Override
    public void initData() {
        categoryInfos = new ArrayList<>();
    }

    @Override
    public void loadScienceArticle(String catalogId) {
        HttpUtil.getObjectListPage(Api.getHealthNewsByCatalog.mapClear()
                        .addPage(10, pageNo)
                        .addMap("catalogId", catalogId)
                        .addMap("catalogCode", "KP")
                        .addBody()
                , ScienceCategoryInfo.class, new HttpUtil.objectListPageCallback() {
                    @Override
                    public void resultPage(boolean b, @Nullable Object obj, int total) {
                        if (b){
                            categoryInfos.addAll((Collection<? extends ScienceCategoryInfo>) obj != null ? (Collection<? extends ScienceCategoryInfo>) obj : null);
                            mView.setScienceArticle(categoryInfos, total);
                        }
                    }
                });
    }

    @Override
    public void refresh(String catalogId) {
        pageNo = 1;
        categoryInfos.clear();
        loadScienceArticle(catalogId);
    }

    @Override
    public void loadMore(String catalogId) {
        pageNo++;
        loadScienceArticle(catalogId);
    }
}
