package com.example.pornographic.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.User;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.toast.ToastUtils;
import com.payelves.sdk.EPay;
import com.payelves.sdk.enums.EPayResult;
import com.payelves.sdk.listener.PayResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MemberActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.ll_rank1)
    LinearLayout llRank1;
    @BindView(R.id.ll_rank2)
    LinearLayout llRank2;
    @BindView(R.id.ll_rank3)
    LinearLayout llRank3;
    @BindView(R.id.ll_rank4)
    LinearLayout llRank4;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_member;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("会员充值");
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.ll_rank1, R.id.ll_rank2, R.id.ll_rank3, R.id.ll_rank4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_rank1:
                memberRechage("年度会员", 180 * 100, 360);
                break;
            case R.id.ll_rank2:
                memberRechage("季度会员", 90 * 100, 180);
                break;
            case R.id.ll_rank3:
                memberRechage("包月会员", 45 * 100, 30);
                break;
            case R.id.ll_rank4:
                memberRechage("三天体验", 20 * 100, 3);
                break;
        }
    }

    private void memberRechage(final String rank, int money, final int date) {
        /**
         * @param context
         * @param payId   支付精灵支付id
         * @param orderId   商户系统订单id
         * @param payUserId 商户系统用户ID
         * @param payResult
         * @param payType   支付类型:1 支付宝，2 微信 3 银联
         * @param amount    支付金额
         * @see EPayResult#FAIL_CODE
         * @see EPayResult#SUCCESS_CODE
         * 1支付成功，2支付失败
         */
        EPay.getInstance(this).pay(rank, "会员充值", 1,
                "", "1789780841", "", new PayResultListener() {

                    @Override
                    public void onFinish(Context context, Long payId, String orderId, String payUserId,
                                         EPayResult payResult, int payType, Integer amount) {
                        EPay.getInstance(context).closePayView();//关闭快捷支付页面
                        if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
                            //支付成功逻辑处理
                            getMember(rank, date);
//                            Toast.makeText(MainActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                        } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                            //支付失败逻辑处理
                            ToastUtils.show("充值失败，请联系客服");
//                            Toast.makeText(MainActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getMember(String rank, int date) {
        User user = new User();
        user.setMemerRank(rank);
        int i = (int) (System.currentTimeMillis() / 1000);
        int z = i + date * 60 * 60 * 24;
        user.setMemerTime(String.valueOf(z));
        user.update((String) SharePreferenceUtil.getUser("id", "String"), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.show("充值成功");
                } else {
                    ToastUtils.show("充值失败");
                }
            }
        });
    }
}
