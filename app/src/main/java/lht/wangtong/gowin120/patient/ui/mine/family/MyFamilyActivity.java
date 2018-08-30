package lht.wangtong.gowin120.patient.ui.mine.family;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.MyFamilyAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;

/**
 * @author luoyc
 * @date 2018/3/14
 */
@Route(path = "/mine/family/MyFamilyActivity")
public class MyFamilyActivity extends BaseActivity<MyFamilyPresenter> implements MyFamilyContact.View, MyFamilyAdapter.OnItemClickListener {
    private final int FAMILY_INFO = 105;
    @BindView(R.id.family_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.family_null_layout)
    ConstraintLayout mConstraintLayout;
    @Inject
    MyFamilyAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_family;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.initData();
    }

    @Override
    public void setFamilyLits(List<HomeFamilyInfo> infos) {
        if (infos.size() > 0) {
            setState(true);
        } else {
            setState(false);
        }
        mAdapter.setNewData(infos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setState(boolean isShow) {
        if (isShow) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mConstraintLayout.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mConstraintLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.cancel_btn, R.id.add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.add_btn:
                //添加成员
                ARouter.getInstance().build("/mine/family/FamilyInfoActivity")
                        .withInt("type", 1)
                        .withParcelable("mFamilyInfo", new HomeFamilyInfo())
                        .navigation(this, FAMILY_INFO);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build("/mine/family/FamilyInfoActivity")
                .withInt("type", 2)
                .withParcelable("mFamilyInfo", mAdapter.getData().get(position))
                .navigation(this, FAMILY_INFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case FAMILY_INFO:
                boolean isRresh = data.getBooleanExtra("isRresh", false);
                if (isRresh) {
                    mPresenter.loadFamilyLits();
                }
                break;
            default:
                break;
        }
    }
}
