package lht.wangtong.gowin120.patient.ui.consult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;

/**
 * @author luoyc
 * @date 2018/3/5
 */

public class ConsultPresenter extends BasePresenter<ConsultContract.View> implements ConsultContract.Presenter {

    @Inject
    public ConsultPresenter() {
    }

    @Override
    public void loadTitles() {
        List<String> titleList = new ArrayList<>();
        titleList.add("新的咨询");
        titleList.add("历史咨询");
        mView.setTitles(titleList);
    }
}
