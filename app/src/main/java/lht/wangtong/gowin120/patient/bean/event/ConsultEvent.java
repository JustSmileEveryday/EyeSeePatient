package lht.wangtong.gowin120.patient.bean.event;

/**
 * @author luoyc
 */
public class ConsultEvent {
    private boolean isSkip =false;

    public ConsultEvent(boolean isSkip) {
        this.isSkip = isSkip;
    }

    public boolean isSkip() {
        return isSkip;
    }

    public void setSkip(boolean skip) {
        isSkip = skip;
    }
}
