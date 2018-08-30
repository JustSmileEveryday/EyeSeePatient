package lht.wangtong.gowin120.patient.bean.event;

/**
 * @author luoyc
 */
public class RecommendServiceEvent {

    private boolean isService;

    public RecommendServiceEvent(boolean isService) {
        this.isService = isService;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }
}
