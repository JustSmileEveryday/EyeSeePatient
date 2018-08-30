package lht.wangtong.gowin120.patient.bean;

/**
 *
 *
 * @author Luoyc
 * @date 2018/3/22
 */

public class WeekInfo {
    private String weekName;
    private boolean isChoosed =false;
    private String remindValue;

    public WeekInfo(String weekName, boolean isChoosed, String remindValue) {
        this.weekName = weekName;
        this.isChoosed = isChoosed;
        this.remindValue = remindValue;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getRemindValue() {
        return remindValue;
    }

    public void setRemindValue(String remindValue) {
        this.remindValue = remindValue;
    }
}
