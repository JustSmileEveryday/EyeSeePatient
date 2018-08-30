package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author luoyc
 */
public class ConsultInfo implements Comparable, Parcelable {

    /**
     * picUrl : /user_head/18300/original/18300_1116171922790770.png
     * consultEmployeeType : 2
     * employeeId : 7260
     * userName : 眼科医生二
     * videoUserId : 118300
     */

    private String picUrl;
    private int consultEmployeeType;
    private String employeeId;
    private String userName;
    private String videoUserId;
    private long LastMessageTime;
    private long UnreadNum;
    private String LastMessageSummary;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getConsultEmployeeType() {
        return consultEmployeeType;
    }

    public void setConsultEmployeeType(int consultEmployeeType) {
        this.consultEmployeeType = consultEmployeeType;
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

    public long getLastMessageTime() {
        return LastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        LastMessageTime = lastMessageTime;
    }

    public long getUnreadNum() {
        return UnreadNum;
    }

    public void setUnreadNum(long unreadNum) {
        UnreadNum = unreadNum;
    }

    public String getLastMessageSummary() {
        return LastMessageSummary;
    }

    public void setLastMessageSummary(String lastMessageSummary) {
        LastMessageSummary = lastMessageSummary;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof ConsultInfo) {
            ConsultInfo anotherConsultationInfo = (ConsultInfo) o;
            long timeGap = anotherConsultationInfo.getLastMessageTime() - getLastMessageTime();
            if (timeGap > 0) {
                return 1;
            } else if (timeGap < 0) {
                return -1;
            }
            return 0;
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picUrl);
        dest.writeInt(this.consultEmployeeType);
        dest.writeString(this.employeeId);
        dest.writeString(this.userName);
        dest.writeString(this.videoUserId);
        dest.writeLong(this.LastMessageTime);
        dest.writeLong(this.UnreadNum);
        dest.writeString(this.LastMessageSummary);
    }

    public ConsultInfo() {
    }

    protected ConsultInfo(Parcel in) {
        this.picUrl = in.readString();
        this.consultEmployeeType = in.readInt();
        this.employeeId = in.readString();
        this.userName = in.readString();
        this.videoUserId = in.readString();
        this.LastMessageTime = in.readLong();
        this.UnreadNum = in.readLong();
        this.LastMessageSummary = in.readString();
    }

    public static final Parcelable.Creator<ConsultInfo> CREATOR = new Parcelable.Creator<ConsultInfo>() {
        @Override
        public ConsultInfo createFromParcel(Parcel source) {
            return new ConsultInfo(source);
        }

        @Override
        public ConsultInfo[] newArray(int size) {
            return new ConsultInfo[size];
        }
    };
}
