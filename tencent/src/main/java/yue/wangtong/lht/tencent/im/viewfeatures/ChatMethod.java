package yue.wangtong.lht.tencent.im.viewfeatures;

/**
 * Created by sanmu on 2016/11/30 0030.
 */
public interface ChatMethod {
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
     * 发送小视频消息
     *
     * @param fileName 文件名
     */
    void sendVideo(String fileName);


    /**
     * 结束发送语音消息
     */
    void cancelSendVoice();


    /**
     * call发送
     */
    void sendCall();


    /**
     * call结束
     */
    void cancelCall();

    /**
     * 音频结束
     */
    void sendFaceVideo();

    /**
     * 音频结束
     */
    void cancelFaceVideo();

    //发送地理位置信息
    void sendmap();

}
