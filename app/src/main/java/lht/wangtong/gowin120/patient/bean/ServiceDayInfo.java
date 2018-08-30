package lht.wangtong.gowin120.patient.bean;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/3/16
 */

public class ServiceDayInfo {

    //日期展示
    private String dayInfo;
    //周几展示
    private String weekInfo;
    //详细日期
    private String dateInfo;
    private List<ServiceTimeInfo> serviceTimeInfos;
    private boolean isChoose = false;

    public ServiceDayInfo(String dayInfo, String weekInfo, String dateInfo, List<ServiceTimeInfo> serviceTimeInfos, boolean isChoose) {
        this.dayInfo = dayInfo;
        this.weekInfo = weekInfo;
        this.dateInfo = dateInfo;
        this.serviceTimeInfos = serviceTimeInfos;
        this.isChoose = isChoose;
    }

    public List<ServiceTimeInfo> getServiceTimeInfos() {
        return serviceTimeInfos;
    }

    public void setServiceTimeInfos(List<ServiceTimeInfo> serviceTimeInfos) {
        this.serviceTimeInfos = serviceTimeInfos;
    }

    public String getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo = dayInfo;
    }

    public String getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(String weekInfo) {
        this.weekInfo = weekInfo;
    }

    public String getDateInfo() {
        return dateInfo;
    }

    public void setDateInfo(String dateInfo) {
        this.dateInfo = dateInfo;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
