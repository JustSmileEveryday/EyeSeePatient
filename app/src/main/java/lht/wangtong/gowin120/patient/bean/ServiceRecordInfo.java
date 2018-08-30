package lht.wangtong.gowin120.patient.bean;

import java.util.List;

/**
 * 服务model
 *
 * @author luoyc
 * @date 2017/4/28
 */

public class ServiceRecordInfo {

    /**
     * effectiveCount : 5
     * execStatus : 1
     * serviceTitle : 就医陪护
     * residueCount : 5
     * serviceRecordId : 1251
     * price : 25
     * serviceAgentName : 小艾眼管家双楠店
     * formNo : FW-180329-0008
     * ServiceRecordUseRecordList : [{"createdDate":"2018-04-13 15:41","agentName":"小艾"}]
     * payStatus : 1
     */

    private String effectiveCount;
    private String execStatus;
    private String serviceTitle;
    private String residueCount;
    private int serviceRecordId;
    private String price;
    private String serviceAgentName;
    private String formNo;
    private String payStatus;
    private int useStatus;
    private List<ServiceRecordUseRecordListBean> ServiceRecordUseRecordList;

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }

    public String getEffectiveCount() {
        return effectiveCount;
    }

    public void setEffectiveCount(String effectiveCount) {
        this.effectiveCount = effectiveCount;
    }

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getResidueCount() {
        return residueCount;
    }

    public void setResidueCount(String residueCount) {
        this.residueCount = residueCount;
    }

    public int getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(int serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceAgentName() {
        return serviceAgentName;
    }

    public void setServiceAgentName(String serviceAgentName) {
        this.serviceAgentName = serviceAgentName;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public List<ServiceRecordUseRecordListBean> getServiceRecordUseRecordList() {
        return ServiceRecordUseRecordList;
    }

    public void setServiceRecordUseRecordList(List<ServiceRecordUseRecordListBean> ServiceRecordUseRecordList) {
        this.ServiceRecordUseRecordList = ServiceRecordUseRecordList;
    }

    public static class ServiceRecordUseRecordListBean {
        /**
         * createdDate : 2018-04-13 15:41
         * agentName : 小艾
         */

        private String createdDate;
        private String agentName;

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }
    }
}
