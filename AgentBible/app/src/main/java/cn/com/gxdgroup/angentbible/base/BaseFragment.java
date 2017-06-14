package cn.com.gxdgroup.angentbible.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Ivy on 2016/10/14.
 *
 * @description:
 */

public abstract class BaseFragment extends Fragment {
    String TAG = "MyFragment";
    protected FragmentActivity mActivity;

    /**
     * 此方法可以得到上下文对象
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        mActivity = getActivity();
    }

    /*
     * 返回一个需要展示的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        View view = setContentView(inflater);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    /**
     * 这里可以放置子类加载网络数据的内容
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
//        loadData();
    }



    /**
     * 子类实现此抽象方法返回View进行展示
     *
     * @return
     */
    public abstract View setContentView(LayoutInflater inflater);

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    public abstract void loadData();

    //
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.v(TAG, "onAttach");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");
    }


}
