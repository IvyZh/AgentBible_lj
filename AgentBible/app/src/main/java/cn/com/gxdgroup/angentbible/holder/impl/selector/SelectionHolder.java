package cn.com.gxdgroup.angentbible.holder.impl.selector;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.constant.MenuType;
import cn.com.gxdgroup.angentbible.holder.BaseHolder;
import cn.com.gxdgroup.angentbible.ui.SelectPopupWindow;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.SharedPreUtils;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/20.
 */

public class SelectionHolder extends BaseHolder {

    @BindView(R.id.tv_first)
    TextView mTvFirst;
    @BindView(R.id.iv_first_arrow)
    ImageView mIvFirstArrow;
    @BindView(R.id.ll_first)
    LinearLayout mLlFirst;
    @BindView(R.id.tv_second)
    TextView mTvSecond;
    @BindView(R.id.iv_second_arrow)
    ImageView mIvSecondArrow;
    @BindView(R.id.ll_second)
    LinearLayout mLlSecond;
    @BindView(R.id.third_weight_view)
    View mThirdWeightView;
    @BindView(R.id.tv_third)
    TextView mTvThird;
    @BindView(R.id.iv_third_arrow)
    ImageView mIvThirdArrow;
    @BindView(R.id.ll_third)
    LinearLayout mLlThird;
    @BindView(R.id.tv_last)
    TextView mTvLast;
    @BindView(R.id.iv_last_arrow)
    ImageView mIvLastArrow;
    @BindView(R.id.ll_last)
    LinearLayout mLlLast;
    @BindView(R.id.second_weight_view)
    View mSecondWeightView;
    @BindView(R.id.last_weight_view)
    View mLastWeightView;

    /**
     * -1 ：去掉AppTitleView下面的SelectionHolder
     * 0：首页-二手房Menu
     * 1：首页-租房Menu
     * 2：首页-客源Menu(购房)
     * 3：首页-客源Menu(租房)
     * 4：首页-最新成交Menu
     * 5：我-收藏房源-Item
     * 6：我-收藏客源-Item
     */
    private int mMenuType;

    private ArrayList<String[]> mSelectTabNames;
    List<String> parentData = new ArrayList<>();
    List<List<String>> childData = new ArrayList<List<String>>();


    private TextView[] tvArrs;// Tab TextView数组
    private int[] llTabIds;//Linarlayout Tab的Id 数组
    private ImageView[] ivArrows;// Tab Arrow 数组
    private int parentSelectPos, childSelectPos;
    private boolean isMoreSelect = false;//表示是否点击了更多的选择项
    private boolean isMoreMapDataNull = true;//表示更多选择的map是否为空
    private HashMap<String, String> moreSeletMap;

    public SelectionHolder(FragmentActivity activity, int menuType) {
        super(activity);
        this.mMenuType = menuType;
    }

    @Override
    public View setContentView() {
        return UIUtils.inflate(R.layout.holder_selection);
    }


    @Override
    public void initView() {
        tvArrs = new TextView[4];
        tvArrs[0] = mTvFirst;
        tvArrs[1] = mTvSecond;
        tvArrs[2] = mTvThird;
        tvArrs[3] = mTvLast;

        llTabIds = new int[4];
        llTabIds[0] = R.id.ll_first;
        llTabIds[1] = R.id.ll_second;
        llTabIds[2] = R.id.ll_third;
        llTabIds[3] = R.id.ll_last;

        ivArrows = new ImageView[4];

        ivArrows[0] = mIvFirstArrow;
        ivArrows[1] = mIvSecondArrow;
        ivArrows[2] = mIvThirdArrow;
        ivArrows[3] = mIvLastArrow;


    }

    @Override
    public void setData() {

    }


    /**
     * 因为还没有根据传入的Menu设置显示布局，所以要复写父类的getContentView方法
     *
     * @return
     */
    @Override
    public View getContentView() {
        if (mMenuType != -1) {//根据mMenuType来确定显示的选择Tab标签
            mSelectTabNames = new ArrayList<>();
            mSelectTabNames.add(new String[]{"区域", "价格", "来源", "更多"});// 二手房 0
            mSelectTabNames.add(new String[]{"区域", "租金", "方式", "更多"});// 租房   1
            mSelectTabNames.add(new String[]{"区域", "房型", "来源"});// 客源-购房      2
            mSelectTabNames.add(new String[]{"区域", "房型", "方式", "来源"});// 客源-租房 3
            mSelectTabNames.add(new String[]{"区域", "价格", "房型", "面积"});// 最新成交 4
            initDefaultSelectTab(mSelectTabNames.get(mMenuType));
        }
        return super.getContentView();
    }


    /**
     * 根据TAB的选择来显示下面的Selection,只有客源才会调用这个方法
     *
     * @param mMenuType
     */
    public void setSelectionByTabIndex(int mMenuType) {
        this.mMenuType = mMenuType;
        // 切换之前记录当前TAB的状态 index<--
        if (mMenuType == 3) {//购房-->租房，记录购房的条件
            String strBuy = tvArrs[0].getText().toString().trim() + "_" + tvArrs[1].getText().toString().trim() + "_" + tvArrs[3].getText().toString().trim();
            SharedPreUtils.putString("strBuy", strBuy);
        } else if (mMenuType == 2) {//购房<--租房，记录租房的条件
            String strRent = tvArrs[0].getText().toString().trim() + "_" + tvArrs[1].getText().toString().trim() + "_" + tvArrs[2].getText().toString().trim() + "_" + tvArrs[3].getText().toString().trim();
            SharedPreUtils.putString("strRent", strRent);
        }

        initDefaultSelectTab(mSelectTabNames.get(mMenuType));
        // 获取index对应的值，如果不为空设置
        if (mMenuType == 3) {//购房-->租房，
            String rent = SharedPreUtils.getString("strRent", "");
            if (!TextUtils.isEmpty(rent)) {
                isEochTextView(rent);
            }
        } else if (mMenuType == 2) {//购房<--租房
            String buy = SharedPreUtils.getString("strBuy", "");
            if (!TextUtils.isEmpty(buy)) {
                isEochTextView(buy);
            }
        }

    }

    /**
     * 客源，切换TAB是否回显
     *
     * @param rent
     */
    private void isEochTextView(String rent) {
        String[] split = rent.split("_");

        if (split.length == 3) {//购房
            for (int i = 0; i < split.length; i++) {
                int j = i;
                if (i == 2) {
                    j = 3;
                }
                if (!TextUtils.equals(split[i], mSelectTabNames.get(mMenuType)[i])) {
                    tvArrs[j].setText(split[i]);
                    tvArrs[j].setTextColor(UIUtils.getColor(R.color.common_blue));
                    ivArrows[j].setImageResource(R.drawable.btn_down_blue);
                }
            }
        } else if (split.length == 4) {//租房
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.equals(split[i], mSelectTabNames.get(mMenuType)[i])) {
                    tvArrs[i].setText(split[i]);
                    tvArrs[i].setTextColor(UIUtils.getColor(R.color.common_blue));
                    ivArrows[i].setImageResource(R.drawable.btn_down_blue);
                }
            }
        }

    }

    /**
     * 默认初始化Tab的个数、文字、箭头
     * 4：
     * 3：mLlThird隐藏，mTvLast取names[2]的值
     *
     * @param names
     */
    private void initDefaultSelectTab(String[] names) {
        //字体颜色-灰色
        int greyColor = UIUtils.getColor(R.color.text_grey);
        mTvFirst.setTextColor(greyColor);
        mTvSecond.setTextColor(greyColor);
        mTvThird.setTextColor(greyColor);
        mTvLast.setTextColor(greyColor);

        //箭头-灰色
        Drawable downArrow = UIUtils.getDrawable(R.drawable.btn_pulldown);
        mIvFirstArrow.setImageDrawable(downArrow);
        mIvSecondArrow.setImageDrawable(downArrow);
        mIvThirdArrow.setImageDrawable(downArrow);
        mIvLastArrow.setImageDrawable(downArrow);


        // 赋值
        mTvFirst.setText(names[0]);
        mTvSecond.setText(names[1]);
        mTvThird.setText(names[2]);

        if (names.length == 3) {
            mLlThird.setVisibility(View.GONE);
            mTvLast.setText(names[2]);
            // 需要的操作 gone 掉weightview llselection 的 weight 设置成1
            mSecondWeightView.setVisibility(View.GONE);
            mThirdWeightView.setVisibility(View.GONE);
            mLastWeightView.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
            params.weight = 1.0f;
            mLlFirst.setLayoutParams(params);
            mLlSecond.setLayoutParams(params);
            mLlLast.setLayoutParams(params);
        } else if (names.length == 4) {
            mTvLast.setText(names[3]);
            mLlThird.setVisibility(View.VISIBLE);
            mThirdWeightView.setVisibility(View.VISIBLE);
            // 需要的操作 visible 掉weightview llselection 的 weight 设置成0
            mSecondWeightView.setVisibility(View.VISIBLE);
            mThirdWeightView.setVisibility(View.VISIBLE);
            mLastWeightView.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
            params.weight = 0.0f;
            mLlFirst.setLayoutParams(params);
            mLlSecond.setLayoutParams(params);
            mLlLast.setLayoutParams(params);
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.ll_first, R.id.ll_second, R.id.ll_third, R.id.ll_last})
    public void onClick(View view) {
        int blueColor = UIUtils.getColor(R.color.common_blue);
        Drawable upArrow = UIUtils.getDrawable(R.drawable.btn_up_back);

        int index = -1;
        for (int i = 0; i < llTabIds.length; i++) {
            if (llTabIds[i] == view.getId()) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }

        tvArrs[index].setTextColor(blueColor);
        ivArrows[index].setImageDrawable(upArrow);

        //根据点击的位置和mMenuType准备数据
        preparePopData(index, mMenuType);


        if (isMoreSelect) {
            SelectPopupWindow popupWindow = new SelectPopupWindow(mActivity, moreSeletMap, new SelectPopupWindow.MoreSelectionInterface() {
                @Override
                public void clearData() {
                    isMoreMapDataNull = true;
                    moreSeletMap = null;
                    mTvLast.setTextColor(UIUtils.getColor(R.color.text_grey));
                    ivArrows[3].setImageResource(R.drawable.btn_pulldown);
                }

                @Override
                public void confirm(HashMap<String, String> map) {
                    moreSeletMap = map;
                    UIUtils.sopMap(map);
                    String houseLayout = map.get("houseLayout");
                    String houseOri = map.get("houseOri");
                    String houseArea = map.get("houseArea");
                    String houseYear = map.get("houseYear");

                    if (TextUtils.isEmpty(houseArea) && TextUtils.isEmpty(houseOri) && TextUtils.isEmpty(houseYear) && TextUtils.isEmpty(houseLayout)) {
                        isMoreMapDataNull = true;
                        mTvLast.setTextColor(UIUtils.getColor(R.color.text_grey));
                        ivArrows[3].setImageResource(R.drawable.btn_pulldown);
                    } else {
                        isMoreMapDataNull = false;
                        mTvLast.setTextColor(UIUtils.getColor(R.color.common_blue));
                        ivArrows[3].setImageResource(R.drawable.btn_down_blue);
                    }


                }
            });
            popupWindow.showAsDropDown(mLlFirst, 0, 1);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (isMoreMapDataNull) {//表示更多条件选项map为空，则恢复到原始状态
                        mTvLast.setTextColor(UIUtils.getColor(R.color.text_grey));
                        ivArrows[3].setImageResource(R.drawable.btn_pulldown);
                    } else {
                        mTvLast.setTextColor(UIUtils.getColor(R.color.common_blue));
                        ivArrows[3].setImageResource(R.drawable.btn_down_blue);
                    }
                }
            });
        } else {
            showPopUpWindow(index, mMenuType);
        }

    }


    /**
     * 根据点击位置和MenuType的值来准备数据
     *
     * @param index    013，0123
     * @param menuType 01234
     */
    private void preparePopData(int index, int menuType) {
        isMoreSelect = false;
        String[] regionParentSelections = {"区域", "距离"};
        String[] regionChildSelections1 = {"全部", "浦东", "闵行", "徐汇", "长宁", "普陀", "静安", "卢湾", "黄浦", "闸北", "虹口", "杨浦", "宝山", "嘉定", "青浦", "松江", "金山", "奉贤", "南汇", "崇明"};
        String[] regionChildSelections2 = {"全部", "500米以内", "1000米以内", "2000米以内", "3000米以内"};

        String[] priceParentSelections = {"全部", "200万以下", "200-250万", "250-300万", "300-400万", "400-500万", "500-800万", "800-1000万", "1000万以上"};
        String[] originParentSelections = {"全部", "个人", "经纪人"};


        String[] rentParentSelections = {"全部", "2000元以下", "2000-3000元", "3000-4000元", "4000-5000元", "5000-6000元", "6000元以上"};
        String[] wayParentSelections = {"全部", "整租", "合租"};

        String[] houseStructParentSelections = {"全部", "1室", "2室", "3室", "4室", "5室", "5室以上"};

        String[] areaParentSelections = {"50以下", "50-70", "70-90", "90-120", "120-150", "150-200", "200以上"};


//        parentData.clear();
        childData.clear();
        switch (menuType) {
            case 0://二手房
                if (index == 0) {//区域
                    parentData = Arrays.asList(regionParentSelections);
                    childData.add(Arrays.asList(regionChildSelections1));
                    childData.add(Arrays.asList(regionChildSelections2));
                } else if (index == 1) {//价格
                    parentData = Arrays.asList(priceParentSelections);
                } else if (index == 2) {//来源
                    parentData = Arrays.asList(originParentSelections);
                } else if (index == 3) {//更多 TODO
                    isMoreSelect = true;
                }

                break;
            case 1:// 租房

                if (index == 0) {//区域
                    parentData = Arrays.asList(regionParentSelections);
                    childData.add(Arrays.asList(regionChildSelections1));
                    childData.add(Arrays.asList(regionChildSelections2));
                } else if (index == 1) {//租金
                    parentData = Arrays.asList(rentParentSelections);
                } else if (index == 2) {//方式
                    parentData = Arrays.asList(wayParentSelections);
                } else if (index == 3) {//更多 TODO
                    isMoreSelect = true;
                }
                break;
            case 2://客源-购房
                if (index == 0) {//区域
                    parentData = Arrays.asList(regionParentSelections);
                    childData.add(Arrays.asList(regionChildSelections1));
                    childData.add(Arrays.asList(regionChildSelections2));
                } else if (index == 1) {//房型
                    parentData = Arrays.asList(houseStructParentSelections);
                } else if (index == 3) {//来源
                    parentData = Arrays.asList(originParentSelections);
                }
                break;
            case 3://客源-租房

                if (index == 0) {//区域
                    parentData = Arrays.asList(regionParentSelections);
                    childData.add(Arrays.asList(regionChildSelections1));
                    childData.add(Arrays.asList(regionChildSelections2));
                } else if (index == 1) {//房型
                    parentData = Arrays.asList(houseStructParentSelections);
                } else if (index == 2) {//方式
                    parentData = Arrays.asList(wayParentSelections);
                } else if (index == 3) {//来源
                    parentData = Arrays.asList(originParentSelections);
                }

                break;
            case 4://最新成交

                if (index == 0) {//区域
                    parentData = Arrays.asList(regionParentSelections);
                    childData.add(Arrays.asList(regionChildSelections1));
                    childData.add(Arrays.asList(regionChildSelections2));
                } else if (index == 1) {//价格
                    parentData = Arrays.asList(priceParentSelections);
                } else if (index == 2) {//房型
                    parentData = Arrays.asList(houseStructParentSelections);
                } else if (index == 3) {//面积
                    parentData = Arrays.asList(areaParentSelections);
                }

                break;
        }

    }

    /**
     * 显示PopUpWind
     *
     * @param index
     * @param menuType
     */
    private void showPopUpWindow(final int index, final int menuType) {

        boolean isFooterView = false;
        parentSelectPos = SharedPreUtils.getInt(menuType + "_" + index + "_p", 0);
        childSelectPos = SharedPreUtils.getInt(menuType + "_" + index + "_c", 0);

        if (index == 1) {
            if (menuType == MenuType.SENCOND_HAND || menuType == MenuType.RECENT_DEAL || menuType == MenuType.RENT_HOUSE) {
                isFooterView = true;
            }
        }

        SelectPopupWindow popupWindow = new SelectPopupWindow(parentData, childData, parentSelectPos, childSelectPos, isFooterView, mActivity, new SelectPopupWindow.SelectCategory() {
            @Override
            public void selectCategory(int pPos, String tvP, int cPos, String tvC) {
                L.v("selectCategory:" + pPos + "," + tvP + "," + cPos + "," + tvC);
                SharedPreUtils.putInt(menuType + "_" + index + "_p", pPos);
                SharedPreUtils.putInt(menuType + "_" + index + "_c", cPos);

                echoTextView(menuType, index, tvP, tvC);
            }

            @Override
            public void price(int max, int min) {
                tvArrs[1].setText(min + "-" + max + "万");
                tvArrs[1].setTextColor(UIUtils.getColor(R.color.common_blue));
                ivArrows[1].setImageResource(R.drawable.btn_down_blue);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivArrows[index].setImageResource(R.drawable.btn_down_blue);//针对点了同样的tab下面的选择条件，无论怎么样都要放下来

                L.v("onDismiss index:" + index + " type:" + menuType);

                int strTabIndex = index;
                if (index == 3 && menuType == 2) {
                    strTabIndex = 2;
                }

                if (TextUtils.equals(tvArrs[index].getText().toString(), mSelectTabNames.get(menuType)[strTabIndex])) {//如果两者相等，就要恢复到原来状态,针对第一步点击tab，第二部点击tab
                    recoverOriText(index, menuType);
                }
            }
        });


        popupWindow.showAsDropDown(mLlFirst, 0, 1);
    }

    /**
     * 回显数据
     *
     * @param type
     * @param index
     * @param p
     * @param c
     */
    private void echoTextView(int type, int index, String p, String c) {
        TextView v = tvArrs[index];
        ivArrows[index].setImageResource(R.drawable.btn_down_blue);
        v.setTextColor(UIUtils.getColor(R.color.common_blue));

        if (TextUtils.isEmpty(c)) {//只有父类，那么就显示p
            v.setText(p);
            if (p.equals("全部")) {
                recoverOriText(index, type);
            }
        } else {//有子类，显示c
            v.setText(c);
            if (c.equals("全部")) {
                recoverOriText(index, type);
            }
        }

    }

    /**
     * 依据type和index来恢复显示
     *
     * @param index
     * @param type
     */
    private void recoverOriText(int index, int type) {
        tvArrs[index].setTextColor(UIUtils.getColor(R.color.text_grey));
        ivArrows[index].setImageResource(R.drawable.btn_pulldown);
        L.v("recoverOriText index:" + index + " type:" + type);

        int strTabIndex = index;
        if (index == 3 && type == 2) {
            strTabIndex = 2;
        }
        tvArrs[index].setText(mSelectTabNames.get(type)[strTabIndex]);
    }


}
