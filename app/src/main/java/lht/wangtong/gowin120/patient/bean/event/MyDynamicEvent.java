package lht.wangtong.gowin120.patient.bean.event;

/**
 * @author luoyc
 */
public class MyDynamicEvent {

    private boolean isNewMessage;

    public MyDynamicEvent(boolean isNewMessage) {
        this.isNewMessage = isNewMessage;
    }

    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }
}
