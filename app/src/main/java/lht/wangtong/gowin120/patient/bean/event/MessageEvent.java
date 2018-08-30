package lht.wangtong.gowin120.patient.bean.event;

/**
 * @author luoyc
 */
public class MessageEvent {
    private boolean isNewMessage = false;
    private int num;

    public MessageEvent(boolean isNewMessage, int num) {
        this.isNewMessage = isNewMessage;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "isNewMessage=" + isNewMessage +
                ", num=" + num +
                '}';
    }
}
