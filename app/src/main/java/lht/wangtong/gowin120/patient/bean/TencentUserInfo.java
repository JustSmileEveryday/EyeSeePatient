package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author luoyc
 */
public class TencentUserInfo implements Parcelable {
    private String id;
    private String userName;
    private String picUrl;
    private String jobTitle;
    private String videoId;
    private String employeeId;
    private String operationRemoteConsultationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOperationRemoteConsultationId() {
        return operationRemoteConsultationId;
    }

    public void setOperationRemoteConsultationId(String operationRemoteConsultationId) {
        this.operationRemoteConsultationId = operationRemoteConsultationId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.picUrl);
        dest.writeString(this.jobTitle);
        dest.writeString(this.videoId);
        dest.writeString(this.employeeId);
        dest.writeString(this.operationRemoteConsultationId);
    }

    public TencentUserInfo() {
    }

    protected TencentUserInfo(Parcel in) {
        this.id = in.readString();
        this.userName = in.readString();
        this.picUrl = in.readString();
        this.jobTitle = in.readString();
        this.videoId = in.readString();
        this.employeeId = in.readString();
        this.operationRemoteConsultationId = in.readString();
    }

    public static final Parcelable.Creator<TencentUserInfo> CREATOR = new Parcelable.Creator<TencentUserInfo>() {
        @Override
        public TencentUserInfo createFromParcel(Parcel source) {
            return new TencentUserInfo(source);
        }

        @Override
        public TencentUserInfo[] newArray(int size) {
            return new TencentUserInfo[size];
        }
    };

    @Override
    public String toString() {
        return "TencentUserInfo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", videoId='" + videoId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", operationRemoteConsultationId='" + operationRemoteConsultationId + '\'' +
                '}';
    }
}
