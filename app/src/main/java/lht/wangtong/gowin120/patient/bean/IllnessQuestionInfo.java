package lht.wangtong.gowin120.patient.bean;

public class IllnessQuestionInfo {

    /**
     * helpfulCount : 0
     * illnessQuestionId : 14
     * question : 肠胃疾病修改
     * answer : <p>肠
     * noHelpfulCount : 0
     */

    private int helpfulCount;
    private String illnessQuestionId;
    private String question;
    private String answer;
    private int noHelpfulCount;

    public int getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }

    public String getIllnessQuestionId() {
        return illnessQuestionId;
    }

    public void setIllnessQuestionId(String illnessQuestionId) {
        this.illnessQuestionId = illnessQuestionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getNoHelpfulCount() {
        return noHelpfulCount;
    }

    public void setNoHelpfulCount(int noHelpfulCount) {
        this.noHelpfulCount = noHelpfulCount;
    }
}
