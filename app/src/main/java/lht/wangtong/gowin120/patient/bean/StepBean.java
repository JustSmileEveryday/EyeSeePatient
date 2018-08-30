package lht.wangtong.gowin120.patient.bean;

/**
 * 日期：16/9/3 00:36
 * <p/>
 * 描述：
 */
public class StepBean {
    public static final int STEP_UNDO = -1;//未完成  undo step
    public static final int STEP_CURRENT = 0;//正在进行 current step
    public static final int STEP_COMPLETED = 1;//已完成 completed step

    private String name;
    private int state;
    private boolean isRight;
    private int drawableId;
    private String chooseText1;
    private String chooseText2;
    private String chooseText3;
    private String rightText;

    public StepBean(int state, boolean isRight) {
        this.state = state;
        this.isRight = isRight;
    }

    public StepBean(int state, boolean isRight, int drawableId) {
        this.state = state;
        this.isRight = isRight;
        this.drawableId = drawableId;
    }

    public StepBean(String name, int state, boolean isRight) {
        this.name = name;
        this.state = state;
        this.isRight = isRight;
    }

    public StepBean(String name, int state, boolean isRight, int drawableId) {
        this.name = name;
        this.state = state;
        this.isRight = isRight;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getChooseText1() {
        return chooseText1;
    }

    public void setChooseText1(String chooseText1) {
        this.chooseText1 = chooseText1;
    }

    public String getChooseText2() {
        return chooseText2;
    }

    public void setChooseText2(String chooseText2) {
        this.chooseText2 = chooseText2;
    }

    public String getChooseText3() {
        return chooseText3;
    }

    public void setChooseText3(String chooseText3) {
        this.chooseText3 = chooseText3;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }
}
