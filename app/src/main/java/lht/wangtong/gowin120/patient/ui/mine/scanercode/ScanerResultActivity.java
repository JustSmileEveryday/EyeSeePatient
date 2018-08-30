package lht.wangtong.gowin120.patient.ui.mine.scanercode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import lht.wangtong.gowin120.patient.R;

/**
 * 扫描结果
 *
 * @author luoyc
 */
@Route(path = "/mine/scanercode/ScanerResultActivity")
public class ScanerResultActivity extends AppCompatActivity {

    @BindView(R.id.result_text)
    TextView resultText;
    @Autowired
    String result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_scaner_result);
        ButterKnife.bind(this);
        resultText.setText(result);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
