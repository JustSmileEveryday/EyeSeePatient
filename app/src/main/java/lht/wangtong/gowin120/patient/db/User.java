package lht.wangtong.gowin120.patient.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * author: wqlin
 * time: 2016/6/30 16:14
 * @author LUOYC
 */
@Table(database = AppDatabase.class)
public class User extends BaseModel implements Parcelable{
    @Column
    @PrimaryKey(autoincrement = true)
    private long Id;
    @Column
    private long memberId;
    @Column
    private String isAuthentication;
    @Column
    private String credentialsType;
    @Column
    private String birthday;
    @Column
    private String formStatus;
    @Column
    private String isMarried;
    @Column
    private String sex;
    @Column
    private String idCard;
    @Column
    private String picUrl;
    @Column
    private String accessToken;
    @Column
    private String lastDate;
    @Column
    private String videoMd5;
    @Column
    private String memLevel;
    @Column
    private String videoUserId;
    @Column
    private String habit;
    @Column
    private String userId;
    @Column
    private String age;
    @Column
    private String accountType;
    @Column
    private String sdkAppId;
    @Column
    private String memberName;
    @Column
    private String loginName;
    @Column
    private String recentScreenTime;
    @Column
    private String recentScreenResult;
    @Column
    private String ageGroup;
    @Column
    private String ageGroupType;
    @Column
    private String signinTimes;
    @Column
    private String isLogined;

    public String getSigninTimes() {
        return signinTimes;
    }

    public void setSigninTimes(String signinTimes) {
        this.signinTimes = signinTimes;
    }

    public String getIsLogined() {
        return isLogined;
    }

    public void setIsLogined(String isLogined) {
        this.isLogined = isLogined;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getAgeGroupType() {
        return ageGroupType;
    }

    public void setAgeGroupType(String ageGroupType) {
        this.ageGroupType = ageGroupType;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getIsAuthentication() {
        return isAuthentication;
    }

    public void setIsAuthentication(String isAuthentication) {
        this.isAuthentication = isAuthentication;
    }

    public String getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getVideoMd5() {
        return videoMd5;
    }

    public void setVideoMd5(String videoMd5) {
        this.videoMd5 = videoMd5;
    }

    public String getMemLevel() {
        return memLevel;
    }

    public void setMemLevel(String memLevel) {
        this.memLevel = memLevel;
    }

    public String getVideoUserId() {
        return videoUserId;
    }

    public void setVideoUserId(String videoUserId) {
        this.videoUserId = videoUserId;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRecentScreenTime() {
        return recentScreenTime;
    }

    public void setRecentScreenTime(String recentScreenTime) {
        this.recentScreenTime = recentScreenTime;
    }

    public String getRecentScreenResult() {
        return recentScreenResult;
    }

    public void setRecentScreenResult(String recentScreenResult) {
        this.recentScreenResult = recentScreenResult;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Id);
        dest.writeLong(this.memberId);
        dest.writeString(this.isAuthentication);
        dest.writeString(this.credentialsType);
        dest.writeString(this.birthday);
        dest.writeString(this.formStatus);
        dest.writeString(this.isMarried);
        dest.writeString(this.sex);
        dest.writeString(this.idCard);
        dest.writeString(this.picUrl);
        dest.writeString(this.accessToken);
        dest.writeString(this.lastDate);
        dest.writeString(this.videoMd5);
        dest.writeString(this.memLevel);
        dest.writeString(this.videoUserId);
        dest.writeString(this.habit);
        dest.writeString(this.userId);
        dest.writeString(this.age);
        dest.writeString(this.accountType);
        dest.writeString(this.sdkAppId);
        dest.writeString(this.memberName);
        dest.writeString(this.loginName);
        dest.writeString(this.recentScreenTime);
        dest.writeString(this.recentScreenResult);
        dest.writeString(this.ageGroup);
        dest.writeString(this.ageGroupType);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.Id = in.readLong();
        this.memberId = in.readLong();
        this.isAuthentication = in.readString();
        this.credentialsType = in.readString();
        this.birthday = in.readString();
        this.formStatus = in.readString();
        this.isMarried = in.readString();
        this.sex = in.readString();
        this.idCard = in.readString();
        this.picUrl = in.readString();
        this.accessToken = in.readString();
        this.lastDate = in.readString();
        this.videoMd5 = in.readString();
        this.memLevel = in.readString();
        this.videoUserId = in.readString();
        this.habit = in.readString();
        this.userId = in.readString();
        this.age = in.readString();
        this.accountType = in.readString();
        this.sdkAppId = in.readString();
        this.memberName = in.readString();
        this.loginName = in.readString();
        this.recentScreenTime = in.readString();
        this.recentScreenResult = in.readString();
        this.ageGroup = in.readString();
        this.ageGroupType = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
