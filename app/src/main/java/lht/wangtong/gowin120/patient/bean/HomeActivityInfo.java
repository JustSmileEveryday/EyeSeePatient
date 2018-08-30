package lht.wangtong.gowin120.patient.bean;

/**
 * @author luoyc
 * @date 2018/3/5
 */

public class HomeActivityInfo {


    /**
     * activityId : 17050
     * activityPicUrl : /activity/17050/activityPic/17050_0522153558851078.jpg
     * activityName : 筛查大优惠
     * activityStatus : 1
     * activityCrowd : 1,2
     */

    private String activityId;
    private String activityPicUrl;
    private String activityName;
    private int activityStatus;
    private String activityCrowd;
    private String activityContent;

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityPicUrl() {
        return activityPicUrl;
    }

    public void setActivityPicUrl(String activityPicUrl) {
        this.activityPicUrl = activityPicUrl;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityCrowd() {
        return activityCrowd;
    }

    public void setActivityCrowd(String activityCrowd) {
        this.activityCrowd = activityCrowd;
    }
}
