package cn.com.gxdgroup.angentbible.holder.impl.village;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/20.
 *
 * @description:
 */

public class EquipmentMapDetialsHolder extends BaseHolder {
    @BindView(R.id.expandableListView)
    ExpandableListView mExpandableListView;
    //定义两个List用来控制Group和Child中的String;
    private List<String> groupArray;//组列表
    private List<List<String>> childArray;//子列表

    public EquipmentMapDetialsHolder(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_equipment_details);
    }

    @Override
    public void initView() {
        groupArray = new ArrayList<String>();
        childArray = new ArrayList<List<String>>();
    }

    @Override
    public void setData() {

        initdate();
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setAdapter(new ExpandableListViewaAdapter(mActivity));
        mExpandableListView.expandGroup(0);
    }

    private void initdate() {
        addInfo("语言", new String[]{"Oracle", "Java", "Linux", "Jquery"});
        addInfo("男人的需求", new String[]{"金钱", "事业", "权力", "女人", "房子", "车", "球"});
    }

    private void addInfo(String group, String[] child) {

        groupArray.add(group);

        List<String> childItem = new ArrayList<String>();

        for (int index = 0; index < child.length; index++) {
            childItem.add(child[index]);
        }
        childArray.add(childItem);
    }


    class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
        Activity activity;

        public ExpandableListViewaAdapter(Activity a) {
            activity = a;
        }

        /*-----------------Child */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childArray.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String name = childArray.get(groupPosition).get(childPosition);


            View childView = UIUtils.inflate(R.layout.expendlist_item);
            TextView tvChildName = (TextView) childView.findViewById(R.id.tv_child_name);
            tvChildName.setText(name);
            return childView;

        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childArray.get(groupPosition).size();
        }

        /* ----------------------------Group */
        @Override
        public Object getGroup(int groupPosition) {
            return getGroup(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return groupArray.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            String name = groupArray.get(groupPosition);

            View groupView = UIUtils.inflate(R.layout.expendlist_group);
            TextView tvGroupName = (TextView) groupView.findViewById(R.id.tv_group_name);
            tvGroupName.setText(name);

            return groupView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
