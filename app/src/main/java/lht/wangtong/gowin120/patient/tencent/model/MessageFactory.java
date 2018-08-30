package lht.wangtong.gowin120.patient.tencent.model;


import com.tencent.TIMMessage;

/**
 * 消息工厂
 *
 * @author luoyc
 */
public class MessageFactory {

    private MessageFactory() {
    }


    /**
     * 消息工厂方法
     */
    public static Message getMessage(TIMMessage message) {
        switch (message.getElement(0).getType()) {
            case Text:
            case Face:
                return new TextMessage(message);
            case Image:
                return new ImageMessage(message);
            case Sound:
                return new VoiceMessage(message);
            default:
                return null;
        }
    }


}
