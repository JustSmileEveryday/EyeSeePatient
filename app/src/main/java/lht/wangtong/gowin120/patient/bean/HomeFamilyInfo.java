package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 家庭人员信息
 *
 * @author luoyc
 * @date 2018/3/6
 */

public class HomeFamilyInfo implements MultiItemEntity ,Parcelable{
    public static final int FAMILY = 1;
    public static final int ADD = 2;
    private int itemType = 1;
    private boolean isChoose = false;
    /**
     * familyId : 48
     * ageGroupType : 3
     * picUrl : user_head/0/default.jpg
     * ageGroup : 5
     * relationship : 1
     */

    private String familyId;
    private String ageGroupType;
    private String picUrl;
    private String name;
    private String ageGroup;
    private String relationship;
    private String newPicUrl;
    private String mobilePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getAgeGroupType() {
        return ageGroupType;
    }

    public void setAgeGroupType(String ageGroupType) {
        this.ageGroupType = ageGroupType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getNewPicUrl() {
        return newPicUrl;
    }

    public void setNewPicUrl(String newPicUrl) {
        this.newPicUrl = newPicUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemType);
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
        dest.writeString(this.familyId);
        dest.writeString(this.ageGroupType);
        dest.writeString(this.picUrl);
        dest.writeString(this.name);
        dest.writeString(this.ageGroup);
        dest.writeString(this.relationship);
        dest.writeString(this.newPicUrl);
    }

    public HomeFamilyInfo() {
    }

    protected HomeFamilyInfo(Parcel in) {
        this.itemType = in.readInt();
        this.isChoose = in.readByte() != 0;
        this.familyId = in.readString();
        this.ageGroupType = in.readString();
        this.picUrl = in.readString();
        this.name = in.readString();
        this.ageGroup = in.readString();
        this.relationship = in.readString();
        this.newPicUrl = in.readString();
    }

    public static final Creator<HomeFamilyInfo> CREATOR = new Creator<HomeFamilyInfo>() {
        @Override
        public HomeFamilyInfo createFromParcel(Parcel source) {
            return new HomeFamilyInfo(source);
        }

        @Override
        public HomeFamilyInfo[] newArray(int size) {
            return new HomeFamilyInfo[size];
        }
    };

    @Override
    public String toString() {
        return "HomeFamilyInfo{" +
                "itemType=" + itemType +
                ", isChoose=" + isChoose +
                ", familyId='" + familyId + '\'' +
                ", ageGroupType='" + ageGroupType + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", relationship='" + relationship + '\'' +
                ", newPicUrl='" + newPicUrl + '\'' +
                '}';
    }
}
