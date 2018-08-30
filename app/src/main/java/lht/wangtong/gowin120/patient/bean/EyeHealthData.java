package lht.wangtong.gowin120.patient.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author luoyc
 * @date 2018/3/20
 */

public class EyeHealthData implements Parcelable{

    private int dataType;
    private String dataValue;
    private String familyId;

    public EyeHealthData(int dataType, String dataValue, String familyId) {
        this.dataType = dataType;
        this.dataValue = dataValue;
        this.familyId = familyId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dataType);
        dest.writeString(this.dataValue);
        dest.writeString(this.familyId);
    }

    protected EyeHealthData(Parcel in) {
        this.dataType = in.readInt();
        this.dataValue = in.readString();
        this.familyId = in.readString();
    }

    public static final Creator<EyeHealthData> CREATOR = new Creator<EyeHealthData>() {
        @Override
        public EyeHealthData createFromParcel(Parcel source) {
            return new EyeHealthData(source);
        }

        @Override
        public EyeHealthData[] newArray(int size) {
            return new EyeHealthData[size];
        }
    };
}
