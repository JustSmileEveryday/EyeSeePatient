package lht.wangtong.gowin120.patient.bean.event;

/**
 * 眼保健操打卡
 *
 * @author luoyc
 * @date 2018/3/30
 */

public class SignEvent {

    private boolean isUpdate = false;

    public SignEvent(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
