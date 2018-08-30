package lht.wangtong.gowin120.patient.ui.consult.chat;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.AutoMessage;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;

/**
 * @author luoyc
 */
public interface AutoChatContract extends BaseContract {

    interface View extends BaseContract.BaseView {

        void initHeadView();

        /**
         * 正在发送
         */
        void sending();

        /**
         * 发送文字消息
         */
        void sendText();

        void initData();

        void setMessage(AutoMessage message);

        void skipChatActivity(ConsultEmployeeInfo employeeInfo);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 135 获取机器人回答信息
         */
        void getRobotAnswer(String question);

        void getConsultEmployeeInfo(String consultEmployeeType);

    }

}
