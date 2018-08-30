package yue.wangtong.lht.tencent.im.viewfeatures;

import com.tencent.TIMMessage;

import java.util.List;

/**
 * 聊天界面的接口
 */
public interface ChatView extends MvpView {

    /**
     * 显示消息
     */
    void showMessage(TIMMessage message);

    /**
     * 显示消息
     */
    void showMessage(List<TIMMessage> messages,boolean isFrist);

    /**
     * 发送消息成功
     *
     * @param message 返回的消息
     */
    void onSendMessageSuccess(TIMMessage message);

    /**
     * 发送消息失败
     *
     * @param code 返回码
     * @param desc 返回描述
     */
    void onSendMessageFail(int code, String desc);




}
