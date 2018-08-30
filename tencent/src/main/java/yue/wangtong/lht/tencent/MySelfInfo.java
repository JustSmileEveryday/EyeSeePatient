package yue.wangtong.lht.tencent;



/**
 * 自己的状态数据
 */
public class MySelfInfo {
    private static final String TAG = MySelfInfo.class.getSimpleName();
    private String id;
    private String userSig;
    private String nickName;    // 呢称
    private String avatar;      // 头像
    private String sign;      // 签名
    private String CosSig;
    private static boolean isCreateRoom = false;

    private boolean bLiveAnimator;  // 渐隐动画


    private int id_status;

    private int myRoomNum = -1;

    private static MySelfInfo ourInstance = new MySelfInfo();

    public static MySelfInfo getInstance() {

        return ourInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getMyRoomNum() {
        return myRoomNum;
    }

    public void setMyRoomNum(int myRoomNum) {
        this.myRoomNum = myRoomNum;
    }

    public String getCosSig() {
        return CosSig;
    }

    public void setCosSig(String cosSig) {
        CosSig = cosSig;
    }

    public boolean isbLiveAnimator() {
        return bLiveAnimator;
    }

    public void setbLiveAnimator(boolean bLiveAnimator) {
        this.bLiveAnimator = bLiveAnimator;
    }









    public int getIdStatus() {
        return id_status;
    }

    public void setIdStatus(int id_status) {
        this.id_status = id_status;
    }

    public boolean isCreateRoom() {
        return isCreateRoom;
    }

    public void setJoinRoomWay(boolean isCreateRoom) {
        this.isCreateRoom = isCreateRoom;
    }
}