package lht.wangtong.gowin120.patient.bean;

/**
 *
 * @author luoyc
 * @date 2018/3/19
 */

public class ServiceTimeInfo {

    private String time;
    private boolean isChoosed = false;

    public ServiceTimeInfo(String time, boolean isChoosed) {
        this.time = time;
        this.isChoosed = isChoosed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
