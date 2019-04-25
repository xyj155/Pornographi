package com.example.pornographic.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.User;
import com.example.pornographic.util.CountDownTimerUtils;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class PasswordChangeActivity extends BaseActivity {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.tv_send_sms)
    Button tvSendSms;

    @Override
    public int initActivityLayout() {
        return R.layout.activity_password_change;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolbar("修改密码");
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    @Override
    public void initData() {

    }

    private CountDownTimerUtils countDownTimer;
    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            countDownTimer = new CountDownTimerUtils(tvSendSms, 60000, 1000);
                            countDownTimer.start();
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            ToastUtils.show("验证码发送失败");
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            final User user = BmobUser.getCurrentUser(User.class);
                            user.setPassword(etPassword.getText().toString());
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        ToastUtils.show("更新用户信息成功");
                                        finish();
                                        SharePreferenceUtil.clear();

                                        startActivity(new Intent(PasswordChangeActivity.this, SplashActivity.class));
//                    Snackbar.make(view, "更新用户信息成功：" + user.getAge(), Snackbar.LENGTH_LONG).show();
                                        removeActivity();
                                    } else {
                                        ToastUtils.show("更新用户信息失败");
//                    Snackbar.make(view, "更新用户信息失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                        Log.e("error", e.getMessage());
                                    }
                                }
                            });
                        } else {
                            // TODO 处理错误的结果
                            ToastUtils.show("验证码验证失败");
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }


    @OnClick({R.id.tv_send_sms, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_sms:
                SMSSDK.getVerificationCode("+86", etUsername.getText().toString());
                break;
            case R.id.btn_login:
                if (etPassword.getText().toString().equals(etPassword2.getText().toString())) {
                    SMSSDK.submitVerificationCode("+86", etUsername.getText().toString(), etCode.getText().toString());
                } else {
                    ToastUtils.show("前后密码不一致，请重新输入");
                }

                break;
        }
    }
}
