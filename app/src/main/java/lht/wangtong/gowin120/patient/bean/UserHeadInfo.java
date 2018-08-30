package lht.wangtong.gowin120.patient.bean;

/**
 *
 * @author luoyc
 * @date 2018/3/30
 */

public class UserHeadInfo {

    /**
     * picUrl : /user_head/18481/original/18481_0330114611925552.jpeg
     * resultVO : {"code":"1","item":"","msg":"修改成功"}
     */

    private String picUrl;
    private ResultVOBean resultVO;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public ResultVOBean getResultVO() {
        return resultVO;
    }

    public void setResultVO(ResultVOBean resultVO) {
        this.resultVO = resultVO;
    }

    public static class ResultVOBean {
        /**
         * code : 1
         * item :
         * msg : 修改成功
         */

        private String code;
        private String item;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
