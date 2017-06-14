package cn.com.gxdgroup.angentbible.activities.temp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.common.CommonAdapter;
import cn.com.gxdgroup.angentbible.adapter.common.ViewHolder;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.db.ProductEntity;
import cn.com.gxdgroup.angentbible.db.UserEntity;
import cn.com.gxdgroup.angentbible.interfaces.SimpleAppTitleListener;
import cn.com.gxdgroup.angentbible.ui.AppTitleView;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * 人员管理页面
 */
public class PeopleManagerActivity extends BaseActivity {

    @BindView(R.id.titleView)
    AppTitleView mTitleView;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.bt_update)
    Button btUpdate;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.lv_users)
    ListView lvUsers;
    UserAdapter userAdapter;
    List<UserEntity> mData;

    public static void startActivity(Activity a) {
        Intent intent = new Intent(a, PeopleManagerActivity.class);
        a.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_people_manager);
    }

    @Override
    public void initView() {
        mTitleView.showMode(AppTitleView.MODE.TITLE, -1, null)
                .setTitleMsg("人员管理")
                .setListener(new SimpleAppTitleListener(PeopleManagerActivity.this) {
                    @Override
                    public void onBack() {
                        finish();
                    }
                });

        mData = new ArrayList<>();
        userAdapter = new UserAdapter(this, mData, R.layout.item_user);
        lvUsers.setAdapter(userAdapter);

    }


    @Override
    protected void loadData() {
        super.loadData();
        queryAllPeople();
    }

    private void queryAllPeople() {
        Select select = new Select();
        List<UserEntity> list = select.from(UserEntity.class).execute();
        if (list.size() > 0) {
            for (UserEntity u : list) {
                L.v("query all:" + u.toString());
            }
            mData.clear();
            mData.addAll(list);
            userAdapter.notifyDataSetChanged();
        } else {
            mData.clear();
            userAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.bt_add, R.id.bt_delete, R.id.bt_update, R.id.bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                showAddDialog();
                break;
            case R.id.bt_delete:
                deletSelectPeople();
                break;
            case R.id.bt_update:
                addFakeData();
                break;
            case R.id.bt_query:
                queryAllPeople();
                break;
        }
    }

    /**
     * 添加测试数据
     */
    private void addFakeData() {
        for (int i = 1; i < 50; i++) {
            addPeople("张三 " + i);
        }
    }

    /**
     * 删除选中的人
     */
    private void deletSelectPeople() {
        if (mData != null && mData.size() > 0) {
            boolean hasDelete = false;
            for (UserEntity u : mData) {
                if (u.isCheckDelete()) {
                    hasDelete = true;
                    break;
                }
            }
            if (!hasDelete) {
                UIUtils.showToast("请先选中要删除的人");
                return;
            }

            for (UserEntity u : mData) {
                if (u.isCheckDelete()) {
                    String username = u.getUsername();
                    Delete delete = new Delete();
                    delete.from(UserEntity.class).where("username='" + username + "'").execute();
                    // 还要删除该人对应的产品表
                    Delete delete2 = new Delete();
                    delete2.from(ProductEntity.class).where("productUsername='" + username + "'").execute();
                }
            }
            UIUtils.showToast("删除成功");
            queryAllPeople();
        } else {
            UIUtils.showToast("当前用户列表为空，无法删除");
        }
    }

    /**
     * 弹出添加人员对话框
     */
    private void showAddDialog() {
        new CircleDialog.Builder(this).configInput(new ConfigInput() {
            @Override
            public void onConfig(InputParams params) {
                params.hintText = "请输入人员姓名";
            }
        })
                .setTitle("提示")
                .setNegative("取消", null)
                .setPositiveInput("确定", new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        if (TextUtils.isEmpty(text)) {
                            UIUtils.showToast("输入姓名不能为空");
                        } else {
                            if (TextUtils.equals("全部", text)) {
                                UIUtils.showToast("不能输入这个名字");
                                return;
                            }
                            addPeople(text);
                        }
                    }
                })
                .show();


    }

    private void addPeople(String userName) {
        Select select = new Select();
        List<UserEntity> list = select.from(UserEntity.class).where("username = ?", userName).execute();

        if (list.size() > 0) {// 存在同名的
            UIUtils.showToast("添加失败，不能重复添加" + userName);
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userName);
            userEntity.save();
            UIUtils.showToast("添加成功");
            queryAllPeople();
        }

    }


    class UserAdapter extends CommonAdapter<UserEntity> {

        public UserAdapter(Context context, List<UserEntity> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder holder, final UserEntity item) {
            holder.setText(R.id.tv_username, item.getUsername());
            holder.setChecked(R.id.cb_delete, item.isCheckDelete());


            holder.setOnCheckedChangeListener(R.id.cb_delete, new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setCheckDelete(isChecked);
                }
            });

        }
    }
}
