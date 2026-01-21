package com.carro.vendor.ui.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import com.carro.vendor.BuildConfig;
import com.carro.vendor.R;
import com.carro.vendor.databinding.LayoutToolbarBinding;
import com.carro.vendor.databinding.LogoutBottomDialogBinding;
import com.carro.vendor.ui.activity.EditProfileActivity;
import com.carro.vendor.ui.activity.LoginActivity;
import com.carro.vendor.ui.activity.NotificationActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.ImagePathDecider;
import com.carro.vendor.utils.PreferenceUtils;
import com.carro.vendor.widgets.CustomProgressDialog;


public class BaseActivity extends AppCompatActivity {

    Dialog mProgressDialog;
    public static BaseActivity baseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;
        //progress dialog
        mProgressDialog = new CustomProgressDialog(this);

    }

    /**
     * show loader
     */
    public void showLoader() {
        try {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Hide Loader
     */
    public void hideLoader() {
        try {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Setup toolbar
     */
    public void setUpToolBar(LayoutToolbarBinding binding, Activity activity, String image) {

        Glide.with(activity)
                .load(ImagePathDecider.getUserImagePath() + image)
                .error(R.drawable.img_no_profile)
                .into(binding.ivUser);


        binding.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditProfileActivity.class);
                startActivity(intent);
            }
        });
        binding.ivNofification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NotificationActivity.class);
                startActivity(intent);
            }
        });
        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBottomDialog(activity);
            }
        });
    }
    /**
     * Show alert
     */

    private void logoutBottomDialog(Activity activity) {
        LogoutBottomDialogBinding logoutBottomDialogBinding;
        logoutBottomDialogBinding = LogoutBottomDialogBinding.inflate(getLayoutInflater());

        Dialog dialog = new BottomSheetDialog(activity);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(logoutBottomDialogBinding.getRoot());
        dialog.show();

        logoutBottomDialogBinding.ivCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        logoutBottomDialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logoutBottomDialogBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PreferenceUtils.setBoolean(Constant.PreferenceConstant.IS_LOGIN, false, activity);
                Intent intent=new Intent(activity, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
    /**
     * Show Error
     */
    public void showError(String msg) {
        if (msg == null) return;
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Show alert
     */
    public void showAlert(String msg) {
        if (msg == null) return;
        Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void log_d(String className, String message) {
        if (BuildConfig.DEBUG)
            Log.d(className, "" + message);
    }

    public void log_e(String className, String message, Exception e) {
        if (BuildConfig.DEBUG)
            Log.e(className, "" + message, e);
    }


}
