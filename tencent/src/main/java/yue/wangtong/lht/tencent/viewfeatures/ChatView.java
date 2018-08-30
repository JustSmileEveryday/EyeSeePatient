package yue.wangtong.lht.tencent.viewfeatures;


import com.tencent.TIMMessage;

import java.util.List;

/**
 * 聊天界面的接口
 */
public interface ChatView extends MvpView {

    void initHeadView();

    /**
     * 显示消息
     */
    void showMessage(TIMMessage message);

    /**
     * 显示消息
     */
    void showMessage(List<TIMMessage> messages, boolean isFrist);


    /**
     * 清除所有消息(离线恢复),并等待刷新
     */
    void clearAllMessage();

    /**
     * 发送消息成功
     *
     * @param message 返回的消息
     */
    void onSendMessageSuccess(TIMMessage message);

    /**
     * 发送消息失败
     *
     * @param code    返回码
     * @param desc    返回描述
     * @param message 发送的消息
     */
    void onSendMessageFail(int code, String desc, TIMMessage message);


    /**
     * 发送图片消息
     */
    void sendImage();


    /**
     * 发送照片消息
     */
    void sendPhoto();


    /**
     * 发送文字消息
     */
    void sendText();


    /**
     * 开始发送语音消息
     */
    void startSendVoice();


    /**
     * 结束发送语音消息
     */
    void endSendVoice();


    /**
     * 结束发送语音消息
     */
    void cancelSendVoice();

    /**
     * 正在发送
     */
    void sending();


    /**
     * 显示toast
     */
    void showToast(String msg);

    /**
     * 发送报告
     */
    void sendReport();

}
