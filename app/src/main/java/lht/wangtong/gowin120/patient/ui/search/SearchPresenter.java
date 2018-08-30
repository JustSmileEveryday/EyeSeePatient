package lht.wangtong.gowin120.patient.ui.search;

import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.HistoryInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class SearchPresenter extends BasePresenter<SearchContact.View> implements SearchContact.Presenter {

    private List<CommentLabelInfo> hotSearchInfoList;
    private List<HistoryInfo> historyInfoList;
    private List<ScienceCategoryInfo> scienceCategoryInfos;
    private List<ServiceCategoryInfo> serviceCategoryInfos;
    private List<CourseInfo> courseInfos;
    private List<ReportInfo> reportInfos;
    private int mType;

    @Inject
    public SearchPresenter() {
    }

    @Override
    public void initData(int type) {
        mType = type;
        getHotSearchContent();
        getHistorySearch();
        switch (type) {
            case 1:
                //首页
                scienceCategoryInfos = new ArrayList<>();
                serviceCategoryInfos = new ArrayList<>();
                courseInfos = new ArrayList<>();
                reportInfos = new ArrayList<>();
                break;
            case 2:
                //服务
                serviceCategoryInfos = new ArrayList<>();
                break;
            case 3:
                //科普
                scienceCategoryInfos = new ArrayList<>();
                break;
            default:
                break;
        }
    }

    @Override
    public void getHotSearchContent() {
        hotSearchInfoList = new ArrayList<>();
        HttpUtil.getObjectList(Api.getCommentLabelList.mapClear()
                .addMap("keyType", 1)
                .addBody(), CommentLabelInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    hotSearchInfoList.addAll((Collection<? extends CommentLabelInfo>) obj);
                    mView.setHotSearchs(hotSearchInfoList);
                }
            }
        });
    }

    @Override
    public void getHistorySearch() {
        historyInfoList = new ArrayList<>();
        historyInfoList.addAll(SQLite.select().from(HistoryInfo.class).queryList());
        mView.setHistoryInfo(historyInfoList);
    }

    @Override
    public void deleteHistory(HistoryInfo historyInfo) {
        historyInfo.delete();
    }

    @Override
    public void deleteAllHistory() {
        //清空所有
        SQLite.delete(HistoryInfo.class).execute();
        historyInfoList.clear();
        mView.setHistoryInfo(historyInfoList);
    }

    @Override
    public void search(String content, int type) {
        HistoryInfo historyInfo = new HistoryInfo();
        historyInfo.setHistory(content);
        historyInfoList.add(historyInfo);
        mView.setHistoryInfo(historyInfoList);
        historyInfo.save();
        switch (type) {
            case 1:
                //首页
                searchReport(content);
                searchCatelog(content);
                searchService(content);
                searchVideo(content);
                break;
            case 2:
                //服务
                searchService(content);
                break;
            case 3:
                //科普
                searchCatelog(content);
                break;
            default:
                break;
        }
    }

    @Override
    public void searchCatelog(String keyword) {
        HttpUtil.getObjectListPage(Api.getHealthNewsByCatalog.mapClear()
                        .addPage(100, 1)
                        .addMap("keyword", keyword)
                        .addMap("catalogCode", "KP")
                        .addBody()
                , ScienceCategoryInfo.class, new HttpUtil.objectListPageCallback() {
                    @Override
                    public void resultPage(boolean b, @Nullable Object obj, int total) {
                        if (b) {
                            scienceCategoryInfos.clear();
                            scienceCategoryInfos.addAll((Collection<? extends ScienceCategoryInfo>) obj != null ? (Collection<? extends ScienceCategoryInfo>) obj : null);
                            mView.setCatelogs(scienceCategoryInfos);
                            setIsShowNull();
                        }
                    }
                });
    }

    @Override
    public void searchService(String keyword) {
        HttpUtil.getObjectListPage(Api.getAllService.mapClear()
                .addMap("keyword", keyword)
                .addPage(100, 1)
                .addBody(), ServiceCategoryInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    serviceCategoryInfos.clear();
                    serviceCategoryInfos.addAll(obj != null ? (Collection<? extends ServiceCategoryInfo>) obj : null);
                    mView.setServices(serviceCategoryInfos);
                    setIsShowNull();
                }
            }
        });
    }

    @Override
    public void searchVideo(String keyword) {
        HttpUtil.getObjectListPage(Api.getHealthNewsByCatalog.mapClear()
                        .addPage(100, 1)
                        .addMap("keyword", keyword)
                        .addMap("catalogCode", "XAKT")
                        .addBody()
                , CourseInfo.class, new HttpUtil.objectListPageCallback() {
                    @Override
                    public void resultPage(boolean b, @Nullable Object obj, int total) {
                        if (b) {
                            courseInfos.clear();
                            courseInfos.addAll((Collection<? extends CourseInfo>) obj != null ? (Collection<? extends CourseInfo>) obj : null);
                            mView.setVideos(courseInfos);
                            setIsShowNull();
                        }
                    }
                });
    }

    @Override
    public void searchReport(String keyword) {
        HttpUtil.getObjectList(Api.historyRadiographScreen.mapClear()
                .addMap("keyword", keyword)
                .addBody(), ReportInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    reportInfos.clear();
                    reportInfos.addAll((Collection<? extends ReportInfo>) obj);
                    mView.setReports(reportInfos);
                    setIsShowNull();
                }
            }
        });
    }

    private void setIsShowNull() {
        if (mType == 1) {
            if (reportInfos.size() == 0 && scienceCategoryInfos.size() == 0) {
                if (serviceCategoryInfos.size() == 0 && courseInfos.size() == 0){
                    mView.setNullLayout(true);
                }else {
                    mView.setNullLayout(false);
                }
            }else {
                mView.setNullLayout(false);
            }
        }
    }

}
