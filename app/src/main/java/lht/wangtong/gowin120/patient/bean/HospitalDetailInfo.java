package lht.wangtong.gowin120.patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 明星医院model
 * Created by luoyc on 2017/2/27.
 */

public class HospitalDetailInfo {


    /**
     * keywords : [{"content":"2222","resume":"2","title":"2","picUrl":"2","keywordId":1001}]
     * photoUrlI : /comp_logo/WESSE.png
     * hospitalLevel : 三级甲等综合医院
     * reimbursementPolicy : 报销政策
     * hospitalId : 3140
     * treatmentProcessText : 就诊流程
     * addr : 四川省成都市一环路西二段
     * photoUrlA : /comp_logo/WESSE.png
     * ophthalmologyIntroduction : 眼科介绍
     * treatmentProcess : /comp_logo/WESSE.png
     * queuingTime : 一天一夜
     * hospitalName : 卫视
     * doctors : [{"deptName":"五官科","picUrl":"user_head/0/default.jpg","gootAt":"1","doctorId":7281,"doctorName":"眼科医生四","hosName":"卫视","jobTitle":"主任医师"},{"deptName":"五官科","picUrl":"/user_head/18300/original/18300_1116171922790770.png","gootAt":"1","doctorId":7260,"doctorName":"眼科医生二","hosName":"卫视","jobTitle":"主任医师"},{"deptName":"五官科","picUrl":"user_head/0/default.jpg","gootAt":"1","doctorId":7240,"doctorName":"罗强测试","hosName":"卫视","jobTitle":"副主任医师"},{"deptName":"眼科","picUrl":"user_head/0/default.jpg","gootAt":"1","doctorId":7300,"doctorName":"眼科医生五","hosName":"卫视","jobTitle":"主任医师"},{"deptName":"五官科","picUrl":"user_head/0/default.jpg","gootAt":"1","doctorId":7280,"doctorName":"眼科医生三","hosName":"卫视","jobTitle":"主任医师"}]
     * introduction : 公司介绍
     */

    private String photoUrlI;
    private String hospitalLevel;
    private String reimbursementPolicy;
    private int hospitalId;
    private String treatmentProcessText;
    private String addr;
    private String photoUrlA;
    private String ophthalmologyIntroduction;
    private String treatmentProcess;
    private String queuingTime;
    private String hospitalName;
    private String introduction;
    private List<KeywordsBean> keywords;
    private List<DoctorsBean> doctors;

    public String getPhotoUrlI() {
        return photoUrlI;
    }

    public void setPhotoUrlI(String photoUrlI) {
        this.photoUrlI = photoUrlI;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getReimbursementPolicy() {
        return reimbursementPolicy;
    }

    public void setReimbursementPolicy(String reimbursementPolicy) {
        this.reimbursementPolicy = reimbursementPolicy;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getTreatmentProcessText() {
        return treatmentProcessText;
    }

    public void setTreatmentProcessText(String treatmentProcessText) {
        this.treatmentProcessText = treatmentProcessText;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhotoUrlA() {
        return photoUrlA;
    }

    public void setPhotoUrlA(String photoUrlA) {
        this.photoUrlA = photoUrlA;
    }

    public String getOphthalmologyIntroduction() {
        return ophthalmologyIntroduction;
    }

    public void setOphthalmologyIntroduction(String ophthalmologyIntroduction) {
        this.ophthalmologyIntroduction = ophthalmologyIntroduction;
    }

    public String getTreatmentProcess() {
        return treatmentProcess;
    }

    public void setTreatmentProcess(String treatmentProcess) {
        this.treatmentProcess = treatmentProcess;
    }

    public String getQueuingTime() {
        return queuingTime;
    }

    public void setQueuingTime(String queuingTime) {
        this.queuingTime = queuingTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<KeywordsBean> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeywordsBean> keywords) {
        this.keywords = keywords;
    }

    public List<DoctorsBean> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorsBean> doctors) {
        this.doctors = doctors;
    }

    public static class KeywordsBean implements Serializable {
        /**
         * content : 2222
         * resume : 2
         * title : 2
         * picUrl : 2
         * keywordId : 1001
         */

        private String content;
        private String resume;
        private String title;
        private String picUrl;
        private int keywordId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getKeywordId() {
            return keywordId;
        }

        public void setKeywordId(int keywordId) {
            this.keywordId = keywordId;
        }

        @Override
        public String toString() {
            return "KeywordsBean{" +
                    "content='" + content + '\'' +
                    ", resume='" + resume + '\'' +
                    ", title='" + title + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", keywordId=" + keywordId +
                    '}';
        }
    }

    public static class DoctorsBean {
        /**
         * deptName : 五官科
         * picUrl : user_head/0/default.jpg
         * gootAt : 1
         * doctorId : 7281
         * doctorName : 眼科医生四
         * hosName : 卫视
         * jobTitle : 主任医师
         */

        private String deptName;
        private String picUrl;
        private String gootAt;
        private int doctorId;
        private String doctorName;
        private String hosName;
        private String jobTitle;
        private String guaHaoFee;
        private String reservationFee;

        public String getGuaHaoFee() {
            return guaHaoFee;
        }

        public void setGuaHaoFee(String guaHaoFee) {
            this.guaHaoFee = guaHaoFee;
        }

        public String getReservationFee() {
            return reservationFee;
        }

        public void setReservationFee(String reservationFee) {
            this.reservationFee = reservationFee;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getGootAt() {
            return gootAt;
        }

        public void setGootAt(String gootAt) {
            this.gootAt = gootAt;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getHosName() {
            return hosName;
        }

        public void setHosName(String hosName) {
            this.hosName = hosName;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }
    }
}
