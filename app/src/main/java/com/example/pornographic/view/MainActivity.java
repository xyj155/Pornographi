package com.example.pornographic.view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.pornographic.R;
import com.example.pornographic.base.BaseActivity;
import com.example.pornographic.gson.User;
import com.example.pornographic.util.MobileInfoUtil;
import com.example.pornographic.util.SharePreferenceUtil;
import com.example.pornographic.weight.toast.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author Xuyijie
 */
public class MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private RadioButton rbHome;
    private RadioButton rbKind;
    private RadioButton rbShopcar;
    private RadioButton rbUser;
    private RadioGroup rgHome;
    private static final String TAG = "MainActivity";


    private FragmentManager supportFragmentManager;
    private Fragment homeFragment;
    private Fragment kindFragment;
    private Fragment shopCarFragment;
    private Fragment userFragment;


    @Override
    public int initActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        rbHome = findViewById(R.id.rb_home);
        rbKind = findViewById(R.id.rb_kind);
        rbShopcar = findViewById(R.id.rb_shopcar);
        rbUser = findViewById(R.id.rb_user);
        rgHome = findViewById(R.id.rg_home);
    }

    @Override
    public void initData() {

        supportFragmentManager = getSupportFragmentManager();
        rbHome.setChecked(true);
        showFirstPosition();
        rbHome.setOnCheckedChangeListener(this);
        rbKind.setOnCheckedChangeListener(this);
        rbShopcar.setOnCheckedChangeListener(this);
        rbUser.setOnCheckedChangeListener(this);
        rgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                hideAllFragment(transaction);
                switch (checkedId) {
                    case R.id.rb_home:
                        Log.i(TAG, "onCheckedChanged4: ");
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            transaction.add(R.id.flContainer, homeFragment);
                        } else {
                            transaction.show(homeFragment);
                        }
                        break;
                    case R.id.rb_kind:
                        Log.i(TAG, "onCheckedChanged:3 ");
                        if (kindFragment == null) {
                            kindFragment = new SortedFragment();
                            transaction.add(R.id.flContainer, kindFragment);
                        } else {
                            transaction.show(kindFragment);
                        }
                        break;
                    case R.id.rb_shopcar:
                        Log.i(TAG, "onCheckedChanged: 2");
                        if (shopCarFragment == null) {
                            shopCarFragment = new SelfVideoFragment();
                            transaction.add(R.id.flContainer, shopCarFragment);
                        } else {
                            transaction.show(shopCarFragment);
                        }
                        break;
                    case R.id.rb_user:

                        Log.i(TAG, "onCheckedChanged: 1");
                        if (userFragment == null) {
                            userFragment = new UserFragment();
                            transaction.add(R.id.flContainer, userFragment);
                        } else {
                            transaction.show(userFragment);
                        }
                        break;
                }
                transaction.commit();
            }
        });
        if (!(boolean) SharePreferenceUtil.getUser("login","boolean")){
            BmobQuery<User> bmobQuery = new BmobQuery<>();
            Log.i(TAG, "initData: " + MobileInfoUtil.getIMEI(MainActivity.this));
            bmobQuery.addWhereEqualTo("IME", MobileInfoUtil.getIMEI(MainActivity.this));
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object, BmobException e) {
                    if (e == null) {
                        Log.i(TAG, "done: " + object.toString());
                        if (object.size() > 0) {
                            showLoginDialog();
                        } else {
                            showRegisterDialog();
                        }
                    } else {
                        Log.i(TAG, "done: " + e.getMessage());
                    }
                }
            });
        }

    }


    private void showFirstPosition() {
        supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.flContainer, homeFragment);
        transaction.commit();
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (kindFragment != null) {
            transaction.hide(kindFragment);
        }
        if (shopCarFragment != null) {
            transaction.hide(shopCarFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    private void showRegisterDialog() {

        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_user_register, null);
        final EditText etUsername = dialogView.findViewById(R.id.et_username);
        final EditText etNickName = dialogView.findViewById(R.id.et_nickname);
        final EditText etPassword = dialogView.findViewById(R.id.et_password);
        Button btnRegister = dialogView.findViewById(R.id.btn_register);
        alertDialog.setTitle("用户注册");
        alertDialog.setCancelable(false);
        alertDialog.setView(dialogView);
        alertDialog.show();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                Log.i(TAG, "onClick: " + etUsername.getText().toString());
                Log.i(TAG, "onClick: " + etPassword.getText().toString());
                user.setPhoneIME(MobileInfoUtil.getIMEI(MainActivity.this));
                user.setUsername(etUsername.getText().toString());
                user.setNickName(etNickName.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            ToastUtils.show("注册成功");
                            alertDialog.dismiss();
                            Map<String, Object> map = new HashMap<>();
                            map.put("nikeName", user.getNickName());
                            map.put("userName", user.getUsername());
                            map.put("id", user.getObjectId());
                            map.put("login",true);
                            SharePreferenceUtil.saveUser(map);
                        } else {
                            ToastUtils.show("注册失败" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    private void showLoginDialog() {
        final AlertDialog customizeDialog = new AlertDialog.Builder(MainActivity.this).create();
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_user_login, null);
        customizeDialog.setTitle("用户登陆");
        customizeDialog.setCancelable(false);
        customizeDialog.setView(dialogView);
        customizeDialog.show();
        final EditText etUsername = dialogView.findViewById(R.id.et_username);
        final EditText etPassword = dialogView.findViewById(R.id.et_password);
        Button btnRegister = dialogView.findViewById(R.id.btn_login);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setPhoneIME(MobileInfoUtil.getIMEI(MainActivity.this));
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            ToastUtils.show("登陆成功");
                            Map<String, Object> map = new HashMap<>();
                            map.put("nikeName", user.getNickName());
                            map.put("userName", user.getUsername());
                            map.put("id", user.getObjectId());
                            map.put("login",true);
                            SharePreferenceUtil.saveUser(map);
                            customizeDialog.dismiss();
                        } else {
                            ToastUtils.show("登陆失败" + e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
