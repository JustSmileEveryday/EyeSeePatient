package lht.wangtong.gowin120.patient.util;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.db.Initdb;

/**
 *
 * @author Luoyc
 * @date 2016/11/4
 * 数据字典 数据库查询 工具
 */
public class CommonInfoUtils {


    public static List<CommonCdInfo> getCommonCdInfo(String parentCode) {
        List<CommonCdInfo> commonCdInfos = new ArrayList<>();
        try {
            commonCdInfos = Initdb.db.selector(CommonCdInfo.class).where("parentCode", "=", parentCode).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commonCdInfos;
    }

    public static String getCommnCdInfoName(String parentCode, String chooseCode) {
        String commnCdInfoName = "";
        List<CommonCdInfo> commonCdInfos = new ArrayList<>();
        try {
            commonCdInfos = Initdb.db.selector(CommonCdInfo.class).where("parentCode", "=", parentCode).findAll();
            if (!TextUtils.isEmpty(chooseCode)) {
                for (int i = 0; i < commonCdInfos.size(); i++) {
                    if (TextUtils.equals(chooseCode, commonCdInfos.get(i).getCode())) {
                        commnCdInfoName = commonCdInfos.get(i).getNameZh();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commnCdInfoName;
    }
}
