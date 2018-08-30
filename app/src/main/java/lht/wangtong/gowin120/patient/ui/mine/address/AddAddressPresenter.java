package lht.wangtong.gowin120.patient.ui.mine.address;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vondear.rxtool.view.RxToast;

import org.json.JSONArray;

import java.util.ArrayList;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AddressBean;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.GetJsonDataUtil;

/**
 * @author luoyc
 */
public class AddAddressPresenter extends BasePresenter<AddAddressContact.View> implements AddAddressContact.Presenter {

    @Inject
    public AddAddressPresenter() {
    }

    @Override
    public void initData() {
        parseData();
    }

    @Override
    public void parseData() {
        //解析数据
        ArrayList<AddressBean> options1Items = new ArrayList<>();
        ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //获取assets目录下的json文件数据
        String jsonData = GetJsonDataUtil.getInstance().getJson(mView.getThisContext(), "province.json");
        //用Gson 转成实体
        ArrayList<AddressBean> jsonBean = parseData(jsonData);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            //遍历省份
            ArrayList<String> cityList = new ArrayList<>();
            //该省的城市列表（第二级）
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();
            //该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                //遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                //添加城市
                cityList.add(cityName);
                ArrayList<String> cityAreaList = new ArrayList<>();
                //该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    cityAreaList.add("");
                } else {
                    cityAreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                //添加该省所有地区数据
                provinceAreaList.add(cityAreaList);
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(provinceAreaList);
        }
        mView.setAreaList(options1Items, options2Items, options3Items);
    }

    @Override
    public boolean checkData(String receName, String mobilePhone, String street, String addrArea) {
        if (TextUtils.isEmpty(receName)) {
            RxToast.error("请填写收货人");
            return false;
        }
        if (TextUtils.isEmpty(mobilePhone)) {
            RxToast.error("请填写收货人手机号");
            return false;
        }
        if (TextUtils.isEmpty(street)) {
            RxToast.error("请填写相信地址");
            return false;
        }
        if (TextUtils.isEmpty(addrArea)) {
            RxToast.error("请选择区域");
            return false;
        }
        return true;
    }

    @Override
    public void addAddress(String addrId, String receName, String mobilePhone, String street, String addrArea) {
        if (checkData(receName, mobilePhone, street, addrArea)) {
            HttpUtil.getObject(Api.addAppPersonalAddr.mapClear()
                    .addMap("addrId", addrId)
                    .addMap("type", "0")
                    .addMap("receName", receName)
                    .addMap("mobilePhone", mobilePhone)
                    .addMap("street", street)
                    .addMap("addrArea", addrArea)
                    .addBody(), String.class, new HttpUtil.objectCallback() {
                @Override
                public void result(boolean b, @Nullable Object obj) {
                    if (b) {
                        mView.addSuccess();
                    }
                }
            });
        }
    }

    public ArrayList<AddressBean> parseData(String result) {
        //Gson 解析
        ArrayList<AddressBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AddressBean entity = gson.fromJson(data.optJSONObject(i).toString(), AddressBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
