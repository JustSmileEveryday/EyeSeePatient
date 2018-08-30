package lht.wangtong.gowin120.patient.bean;

/**
 * 眼镜问题
 *
 * @author luoyc
 */
public class EyeAgeProblemInfo {

    private String eyeAgeProblemId;
    private String problem;
    private boolean isTrue = false;

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getEyeAgeProblemId() {
        return eyeAgeProblemId;
    }

    public void setEyeAgeProblemId(String eyeAgeProblemId) {
        this.eyeAgeProblemId = eyeAgeProblemId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
