package com.example.pornographic.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.util.QRCodeUtil;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteUserActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;
    @BindView(R.id.iv_copy)
    ImageView ivCopy;
    @BindView(R.id.tv_service)
    TextView tvService;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_invite_user;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("邀请好友");
    }

    private ClipboardManager mClipboard = null;

    private void copyFromEditText1() {

        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        }
        ClipData clip = ClipData.newPlainText("simple", tvInviteCode.getText().toString().replace("邀请码：",""));
        mClipboard.setPrimaryClip(clip);
        ToastUtils.show("复制成功");
    }

    @Override
    public void initData() {
        ivCode.setImageBitmap(QRCodeUtil.generateBitmap((String) SharePreferenceUtil.getUser("id", "String"), 600, 600));
        tvInviteCode.setText("邀请码：" + (String) SharePreferenceUtil.getUser("id", "String"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }


    @OnClick({R.id.iv_copy, R.id.tv_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_copy:
                copyFromEditText1();
                break;
            case R.id.tv_service:
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=2934046679";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
        }
    }
}
