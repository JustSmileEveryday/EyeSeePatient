package lht.wangtong.gowin120.patient.ui.search;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.db.HistoryInfo;

/**
 * @author luoyc
 */
public interface SearchContact extends BaseContract {

    interface View extends BaseView {

        void search(String content);

        void setHotSearchs(List<CommentLabelInfo> hotSearchInfos);

        void setHistoryInfo(List<HistoryInfo> historyInfos);

        void setReports(List<ReportInfo> reportInfos);

        void setCatelogs(List<ScienceCategoryInfo> categoryInfoList);

        void setServices(List<ServiceCategoryInfo> serviceCategoryInfos);

        void setVideos(List<CourseInfo> courseInfos);

        void setState(boolean isSearch);

        void setEditPosition(String content);

        void setNullLayout(boolean isShow);

    }

    interface Presenter extends BasePresenter<View> {

        void initData(int type);

        void getHotSearchContent();

        void getHistorySearch();

        void deleteHistory(HistoryInfo historyInfo);

        void deleteAllHistory();

        void search(String content, int type);

        void searchCatelog(String keyword);

        void searchService(String keyword);

        void searchVideo(String keyword);

        void searchReport(String keyword);


    }

}
