package cn.com.gxdgroup.angentbible.ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.HashMap;
import java.util.List;

import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.adapter.ChildrenCategoryAdapter;
import cn.com.gxdgroup.angentbible.adapter.ParentCategoryAdapter;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/24.
 *
 * @description: 自定义的PopupWindow, 在构造方法中设置内容, 设置背景等.给要显示的两个ListView设置适配器, 添加ListView点击事件, 点击子类别的时候回调选中的两个下标, 关闭PopupWindow。
 */

public class SelectPopupWindow extends PopupWindow {
    private SelectCategory selectCategory;

    private List<String> mParentData;
    private List<List<String>> mChildData;
    private ListView lvParentCategory = null;
    private ListView lvChildrenCategory = null;
    private ParentCategoryAdapter parentCategoryAdapter = null;
    private ChildrenCategoryAdapter childrenCategoryAdapter = null;
    private int pPos, cPos;
    private CheckBox[] houseLayout, houseOri, houseArea, houseYear;


    //--------------------------------------------------------------------


    public SelectPopupWindow(Activity activity, HashMap<String, String> map, final MoreSelectionInterface moreInterface) {
        View v = LayoutInflater.from(activity).inflate(R.layout.popup_more_selection, null);
        this.setContentView(v);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        int statusHeight = UIUtils.getStatusHeight(activity);
        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels - UIUtils.dip2px(94) - statusHeight);
        /* 设置背景显示 */
        setBackgroundDrawable(UIUtils.getDrawable(R.color.white));
        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        v.setFocusableInTouchMode(true);


        View clear = v.findViewById(R.id.ll_clear);
        View confirm = v.findViewById(R.id.ll_confirm);

        //checkbox的findviewbyid

        CheckBox cbLayout1 = (CheckBox) v.findViewById(R.id.cb_house_layout_1);
        CheckBox cbLayout2 = (CheckBox) v.findViewById(R.id.cb_house_layout_2);
        CheckBox cbLayout3 = (CheckBox) v.findViewById(R.id.cb_house_layout_3);
        CheckBox cbLayout4 = (CheckBox) v.findViewById(R.id.cb_house_layout_4);
        CheckBox cbLayout5 = (CheckBox) v.findViewById(R.id.cb_house_layout_5);
        CheckBox cbLayout6 = (CheckBox) v.findViewById(R.id.cb_house_layout_6);

        houseLayout = new CheckBox[6];
        houseLayout[0] = cbLayout1;
        houseLayout[1] = cbLayout2;
        houseLayout[2] = cbLayout3;
        houseLayout[3] = cbLayout4;
        houseLayout[4] = cbLayout5;
        houseLayout[5] = cbLayout6;

        CheckBox cbOrientation1 = (CheckBox) v.findViewById(R.id.cb_house_orientation_1);
        CheckBox cbOrientation2 = (CheckBox) v.findViewById(R.id.cb_house_orientation_2);
        CheckBox cbOrientation3 = (CheckBox) v.findViewById(R.id.cb_house_orientation_3);
        CheckBox cbOrientation4 = (CheckBox) v.findViewById(R.id.cb_house_orientation_4);
        CheckBox cbOrientation5 = (CheckBox) v.findViewById(R.id.cb_house_orientation_5);

        houseOri = new CheckBox[5];
        houseOri[0] = cbOrientation1;
        houseOri[1] = cbOrientation2;
        houseOri[2] = cbOrientation3;
        houseOri[3] = cbOrientation4;
        houseOri[4] = cbOrientation5;


        CheckBox cbArea1 = (CheckBox) v.findViewById(R.id.cb_house_area_1);
        CheckBox cbArea2 = (CheckBox) v.findViewById(R.id.cb_house_area_2);
        CheckBox cbArea3 = (CheckBox) v.findViewById(R.id.cb_house_area_3);
        CheckBox cbArea4 = (CheckBox) v.findViewById(R.id.cb_house_area_4);
        CheckBox cbArea5 = (CheckBox) v.findViewById(R.id.cb_house_area_5);
        CheckBox cbArea6 = (CheckBox) v.findViewById(R.id.cb_house_area_6);
        CheckBox cbArea7 = (CheckBox) v.findViewById(R.id.cb_house_area_7);


        houseArea = new CheckBox[7];
        houseArea[0] = cbArea1;
        houseArea[1] = cbArea2;
        houseArea[2] = cbArea3;
        houseArea[3] = cbArea4;
        houseArea[4] = cbArea5;
        houseArea[5] = cbArea6;
        houseArea[6] = cbArea7;

        CheckBox cbYear1 = (CheckBox) v.findViewById(R.id.cb_house_year_1);
        CheckBox cbYear2 = (CheckBox) v.findViewById(R.id.cb_house_year_2);
        CheckBox cbYear3 = (CheckBox) v.findViewById(R.id.cb_house_year_3);
        CheckBox cbYear4 = (CheckBox) v.findViewById(R.id.cb_house_year_4);


        houseYear = new CheckBox[4];
        houseYear[0] = cbYear1;
        houseYear[1] = cbYear2;
        houseYear[2] = cbYear3;
        houseYear[3] = cbYear4;


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moreInterface != null) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("houseLayout", getChecked(houseLayout));
                    map.put("houseOri", getChecked(houseOri));
                    map.put("houseArea", getChecked(houseArea));
                    map.put("houseYear", getChecked(houseYear));

                    moreInterface.confirm(map);
                }

                dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moreInterface != null) {
                    moreInterface.clearData();
                }
                dismiss();
            }
        });

        // 回显数据
        if (map != null && map.size() > 0) {
            String houseLayouts = map.get("houseLayout");
            String houseOris = map.get("houseOri");
            String houseAreas = map.get("houseArea");
            String houseYears = map.get("houseYear");

            if (!TextUtils.isEmpty(houseLayouts)) {
                String[] split = houseLayouts.split("_");
                for (int i = 0; i < split.length; i++) {
                    houseLayout[Integer.valueOf(split[i])].setChecked(true);
                }
            }
            if (!TextUtils.isEmpty(houseOris)) {
                String[] split = houseOris.split("_");
                for (int i = 0; i < split.length; i++) {
                    houseOri[Integer.valueOf(split[i])].setChecked(true);
                }
            }
            if (!TextUtils.isEmpty(houseAreas)) {
                String[] split = houseAreas.split("_");
                for (int i = 0; i < split.length; i++) {
                    houseArea[Integer.valueOf(split[i])].setChecked(true);
                }
            }
            if (!TextUtils.isEmpty(houseYears)) {
                String[] split = houseYears.split("_");
                for (int i = 0; i < split.length; i++) {
                    houseYear[Integer.valueOf(split[i])].setChecked(true);
                }
            }


        }
    }

    private String getChecked(CheckBox[] cbs) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < cbs.length; i++) {
            boolean checked = cbs[i].isChecked();
            if (checked) {
                sb.append(i).append("_");
            }
        }
        String s = sb.toString();
        if (s.endsWith("_")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }


    /**
     * 除更多之外都可以用的Popupwind
     */

    public SelectPopupWindow(List parentData, List<List<String>> childData, int parentSelectPos, int childSelectPos, boolean isFooterView, Activity activity, final SelectCategory selectCategory) {
        this.selectCategory = selectCategory;
        this.mParentData = parentData;
        this.mChildData = childData;
        this.pPos = parentSelectPos;
        this.cPos = childSelectPos;


        View contentView = LayoutInflater.from(activity).inflate(R.layout.layout_quyu_choose_view, null);

        this.setContentView(contentView);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小

        int bottomStatusHeight = UIUtils.getBottomStatusHeight(activity);
        int screenHeight = UIUtils.getScreenHeight(activity);
        int statusHeight = UIUtils.getStatusHeight(activity);

        L.v("screenHeight:" + screenHeight + ",bottomStatusHeight:" + bottomStatusHeight + ",statusHeight:" + statusHeight + ",dm.heightPixels:" + dm.heightPixels);


        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels - UIUtils.dip2px(94) - statusHeight);


//        this.setHeight(dm.heightPixels * 7 / 10);

		/* 设置背景显示 */
//        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.));
        setBackgroundDrawable(UIUtils.getDrawable(R.color.common_bg));
        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */

        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);

        //父类别适配器
        lvParentCategory = (ListView) contentView.findViewById(R.id.lv_parent_category);

        //是否给ListView加footerview
        if (isFooterView) {
            View footerView = UIUtils.inflate(R.layout.footer_view_select);
            lvParentCategory.addFooterView(footerView);

            View confirm = footerView.findViewById(R.id.bt_confirm);
            final EditText etMax = (EditText) footerView.findViewById(R.id.et_max);
            final EditText etMin = (EditText) footerView.findViewById(R.id.et_min);


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String max = etMax.getText().toString().trim();
                    String min = etMin.getText().toString().trim();
                    if (TextUtils.isEmpty(max) || TextUtils.isEmpty(min)) {
                        UIUtils.showToast("价格区间填写错误");
                        return;
                    }
                    int maxInt = Integer.valueOf(max);
                    int minInt = Integer.valueOf(min);


                    if (selectCategory != null) {
                        selectCategory.price(maxInt, minInt);
                    }

                    dismiss();
                }
            });
        }

        parentCategoryAdapter = new ParentCategoryAdapter(activity, parentData, R.layout.item_selection);
        lvParentCategory.setAdapter(parentCategoryAdapter);
        //设置默认选中的状态，父类和子类都需要
        L.v("select pos:" + parentSelectPos + "," + childSelectPos);

        parentCategoryAdapter.setSelectedPosition(parentSelectPos);
        parentCategoryAdapter.notifyDataSetInvalidated();


        //子类别适配器
        lvChildrenCategory = (ListView) contentView.findViewById(R.id.lv_children_category);

        if (mChildData != null && mChildData.size() > 0) {
            childrenCategoryAdapter = new ChildrenCategoryAdapter(activity, mChildData.get(parentSelectPos), R.layout.item_selection);
            lvChildrenCategory.setAdapter(childrenCategoryAdapter);
            childrenCategoryAdapter.setSelectedPosition(childSelectPos);
            childrenCategoryAdapter.notifyDataSetChanged();
        } else {
            lvChildrenCategory.setVisibility(View.GONE);
        }

        lvParentCategory.setOnItemClickListener(parentItemClickListener);
        lvChildrenCategory.setOnItemClickListener(childrenItemClickListener);


    }

    /**
     * 子类别点击事件
     */
    private AdapterView.OnItemClickListener childrenItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectCategory != null) {
                selectCategory.selectCategory(parentCategoryAdapter.getPos(), mParentData.get(parentCategoryAdapter.getPos()), position, mChildData.get(parentCategoryAdapter.getPos()).get(position));
            }
            dismiss();
        }
    };

    /**
     * 父类别点击事件
     */
    private AdapterView.OnItemClickListener parentItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (mChildData != null && mChildData.size() > 0) {

                parentCategoryAdapter.setSelectedPosition(position);
                parentCategoryAdapter.notifyDataSetChanged();

                if (position == pPos) {
                    childrenCategoryAdapter.setSelectedPosition(cPos);
                } else {
                    childrenCategoryAdapter.setSelectedPosition(0);
                }
                childrenCategoryAdapter.setDatas(mChildData.get(position));
                childrenCategoryAdapter.notifyDataSetChanged();

            } else {
                if (selectCategory != null) {
                    selectCategory.selectCategory(position, mParentData.get(position), -1, "");
                }
                dismiss();
            }

        }
    };

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategory {

        /**
         * @param pPos 父类选中的位置
         * @param tvP  父类选中的文字
         * @param cPos 子类选中的位置 -1
         * @param tvC  子类选中的文字 ""
         */
        public void selectCategory(int pPos, String tvP, int cPos, String tvC);

        void price(int max, int min);
    }

    public interface MoreSelectionInterface {
        void clearData();

        void confirm(HashMap<String, String> map);
    }

    public interface PriceInterface {
        void choicePrice(int max, int min);
    }

}
