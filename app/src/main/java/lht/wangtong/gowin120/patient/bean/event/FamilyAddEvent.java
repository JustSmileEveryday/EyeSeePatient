package lht.wangtong.gowin120.patient.bean.event;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public class FamilyAddEvent {

    private boolean isAdd = false;

    public FamilyAddEvent(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
