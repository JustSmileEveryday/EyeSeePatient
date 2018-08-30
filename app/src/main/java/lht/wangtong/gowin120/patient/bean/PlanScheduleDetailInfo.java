package lht.wangtong.gowin120.patient.bean;

import java.util.List;

/**
 * Created by luoyc on 2018/3/22.
 */

public class PlanScheduleDetailInfo {

    /**
     * remindValue : ["N","N","N","N","N","Y","N"]
     * name : test1
     * planScheduleId : 1
     * remindDate : 15:00
     */

    private String name;
    private int planScheduleId;
    private String remindDate;
    private List<String> remindValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlanScheduleId() {
        return planScheduleId;
    }

    public void setPlanScheduleId(int planScheduleId) {
        this.planScheduleId = planScheduleId;
    }

    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    public List<String> getRemindValue() {
        return remindValue;
    }

    public void setRemindValue(List<String> remindValue) {
        this.remindValue = remindValue;
    }
}
