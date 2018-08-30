package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author luoyc
 */
public class ConsultEmployeeInfo implements Parcelable {

    /**
     * picUrl : /user_head/18300/original/18300_1116171922790770.png
     * jobTitle : 主任医师
     * employeeId : 7260
     * userName : 眼科医生二
     * videoUserId : 118300
     */

    private String picUrl;
    private String jobTitle;
    private String employeeId;
    private String userName;
    private String videoUserId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVideoUserId() {
        return videoUserId;
    }

    public void setVideoUserId(String videoUserId) {
        this.videoUserId = videoUserId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picUrl);
        dest.writeString(this.jobTitle);
        dest.writeString(this.employeeId);
        dest.writeString(this.userName);
        dest.writeString(this.videoUserId);
    }

    public ConsultEmployeeInfo() {
    }

    protected ConsultEmployeeInfo(Parcel in) {
        this.picUrl = in.readString();
        this.jobTitle = in.readString();
        this.employeeId = in.readString();
        this.userName = in.readString();
        this.videoUserId = in.readString();
    }

    public static final Creator<ConsultEmployeeInfo> CREATOR = new Creator<ConsultEmployeeInfo>() {
        @Override
        public ConsultEmployeeInfo createFromParcel(Parcel source) {
            return new ConsultEmployeeInfo(source);
        }

        @Override
        public ConsultEmployeeInfo[] newArray(int size) {
            return new ConsultEmployeeInfo[size];
        }
    };
}
