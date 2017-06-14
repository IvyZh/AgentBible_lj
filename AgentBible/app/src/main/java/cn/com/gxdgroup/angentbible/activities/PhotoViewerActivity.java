package cn.com.gxdgroup.angentbible.activities;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.gxdgroup.angentbible.R;
import cn.com.gxdgroup.angentbible.base.BaseActivity;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;

/**
 * Created by Ivy on 2016/10/21.
 *
 * @description:
 */

public class PhotoViewerActivity extends BaseActivity {

    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;
    private List<String> urlList = new ArrayList<>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_photo_viewer);
    }

    @Override
    public void initView() {


        urlList.add("http://cdn1.dooioo.com/gimage10/M00/4F/BF/ChYCZ1crapaAItWeAAAphhDd-DE261.jpg");
        urlList.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160902/ee555266-74b0-47b2-903f-ebe1b09b37d0.jpg_600x450.jpg");
        urlList.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160519/657e62ef-ea3d-48df-b7cd-a7e917cea088.jpg_600x450.jpg");
        urlList.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160519/449997ea-502a-468f-b1e2-c760cf1d1c9f.jpg_600x450.jpg");
        urlList.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160906/1e3a7037-c14b-483c-9946-0013aa28f4b0.jpg_600x450.jpg");
        urlList.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160916/096818ff-dc56-4827-9caa-955bfdee03f2.jpg_600x450.jpg");
        urlList.add("http://cdn1.dooioo.com/fetch/vp/fy/gi/20160914/b0eadb9f-7a49-409f-bab1-3a3fa3f5d446.jpg_600x450.jpg");

        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            View v = UIUtils.inflate(R.layout.item_photo_viewer);
            views.add(v);
        }

        mVpPhoto.setAdapter(new PhotoViewAdapter(views));
    }

    class PhotoViewAdapter extends PagerAdapter {

        private List<View> viewlist;

        public PhotoViewAdapter(List<View> viewlist) {
            this.viewlist = viewlist;
        }

        @Override
        public int getCount() {
            return viewlist.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(viewlist.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = viewlist.get(position);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }

//            L.v("--instantiateItem--" + position);

            PhotoView pv = (PhotoView) view.findViewById(R.id.photoView);
            pv.enable();
            Glide.with(PhotoViewerActivity.this).load(urlList.get(position)).into(pv);
            container.addView(view);
            //add listeners here if necessary
            return view;
        }
    }


}
