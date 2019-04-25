package com.example.pornographic.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pornographic.R;
import com.example.pornographic.adapter.HomePagerAdapter;
import com.example.pornographic.base.BaseFragment;
import com.example.pornographic.gson.User;
import com.example.pornographic.gson.UserInvite;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.ColorFlipPagerTitleView;
import com.example.pornographic.weight.CustomViewPager;
import com.example.pornographic.weight.toast.ToastUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.github.xudaojie.qrcodelib.CaptureActivity;

import static android.app.Activity.RESULT_OK;
import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * @author Xuyijie
 */
public class HomeFragment extends BaseFragment {
    private static final int REQUEST_QR_CODE = 0xff;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.mg_title)
    MagicIndicator mgTitle;
    @BindView(R.id.vp_home)
    CustomViewPager vpHome;
    Unbinder unbinder;
    Unbinder unbinder1;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] title = {"推荐", "亚洲遗风", "欧美混血", "动漫卡通"};
    private HomePagerAdapter homePagerAdapter;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            final String result = data.getStringExtra("result");
            if (result.isEmpty()) {
                ToastUtils.show("邀请码不可为空");
            } else {
                final BmobQuery<User> userBmobQuery = new BmobQuery<>();
                userBmobQuery.addWhereEqualTo("objectId", result);
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list.size() == 0) {
                                ToastUtils.show("邀请码错误");
                            } else {
                                BmobQuery<UserInvite> bmobQuery = new BmobQuery<>();
                                bmobQuery.addWhereEqualTo("UserId", SharePreferenceUtil.getUser("id", "String"));
                                bmobQuery.findObjects(new FindListener<UserInvite>() {
                                    @Override
                                    public void done(List<UserInvite> list, BmobException e) {
                                        if (e == null) {
                                            Log.i(TAG, "done: " + list.size());
                                            if (list.size() == 0) {
                                                UserInvite userInvite = new UserInvite();
                                                userInvite.setUserId((String) SharePreferenceUtil.getUser("id", "String"));
                                                userInvite.setInviteId(result);
                                                userInvite.save(new SaveListener<String>() {
                                                    @Override
                                                    public void done(String s, BmobException e) {
                                                        if (e == null) {
                                                            ToastUtils.show("邀请码填写成功");
                                                        } else {
                                                            ToastUtils.show("系统错误");
                                                        }
                                                    }
                                                });
                                            } else {
                                                ToastUtils.show("你已填写邀请码");
                                            }
                                        } else {
                                            ToastUtils.show("系统出错");
                                        }

                                    }
                                });
                            }
                        } else {
                            ToastUtils.show("系统出错");
                        }

                    }
                });
            }
//            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        fragmentList.add(new HomePageFragment());
        fragmentList.add(new HomeAsiaFragment());
        fragmentList.add(new HomeEuropeFragment());
        fragmentList.add(new HomeComicFragment());
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), fragmentList);
        vpHome.setScanScroll(false);
        vpHome.setAdapter(homePagerAdapter);
        mgTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator7 = new CommonNavigator(getContext());
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdjustMode(true);
        vpHome.setOffscreenPageLimit(4);

        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title == null ? 0 : title.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(title[index]);
                TextPaint tp = simplePagerTitleView.getPaint();
                tp.setFakeBoldText(true);
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setNormalColor(Color.parseColor("#8a8a8a"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpHome.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#FDDF4D"));
                return indicator;
            }
        });
        mgTitle.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(mgTitle, vpHome);
    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_scan, R.id.tv_location, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                Intent i = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(i, REQUEST_QR_CODE);
                break;
            case R.id.tv_location:
                break;
            case R.id.iv_search:
                break;
        }
    }
}
