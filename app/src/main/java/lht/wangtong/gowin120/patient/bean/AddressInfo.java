package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressInfo implements Parcelable {


    /**
     * addrId : 61626
     * type : 1
     * receName : 是多少
     * mobilePhone : 12533355885
     * street : 东街
     * addrArea : 清雅
     */

    private String addrId;
    private String type;
    private String receName;
    private String mobilePhone;
    private String street;
    private String addrArea;
    private boolean isChoosed = false;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceName() {
        return receName;
    }

    public void setReceName(String receName) {
        this.receName = receName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddrArea() {
        return addrArea;
    }

    public void setAddrArea(String addrArea) {
        this.addrArea = addrArea;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addrId);
        dest.writeString(this.type);
        dest.writeString(this.receName);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.street);
        dest.writeString(this.addrArea);
        dest.writeByte(this.isChoosed ? (byte) 1 : (byte) 0);
    }

    public AddressInfo() {
    }

    protected AddressInfo(Parcel in) {
        this.addrId = in.readString();
        this.type = in.readString();
        this.receName = in.readString();
        this.mobilePhone = in.readString();
        this.street = in.readString();
        this.addrArea = in.readString();
        this.isChoosed = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AddressInfo> CREATOR = new Parcelable.Creator<AddressInfo>() {
        @Override
        public AddressInfo createFromParcel(Parcel source) {
            return new AddressInfo(source);
        }

        @Override
        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };

    @Override
    public String toString() {
        return "AddressInfo{" +
                "addrId='" + addrId + '\'' +
                ", type='" + type + '\'' +
                ", receName='" + receName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", street='" + street + '\'' +
                ", addrArea='" + addrArea + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
