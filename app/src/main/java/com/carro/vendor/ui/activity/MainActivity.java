package com.carro.vendor.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.ActivityMainBinding;
import com.carro.vendor.databinding.LogoutBottomDialogBinding;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.ui.common.BaseActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.ImagePathDecider;
import com.carro.vendor.utils.PreferenceUtils;
import com.carro.vendor.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    public static NavController bottomNavController;
    Dialog dialog;
    LoginModel loginModel = new LoginModel();


    // --- NEW: DECLARE THE PERMISSION LAUNCHER ---
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "Notifications enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Notifications permission denied. You can enable it in App Settings.", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });


        askNotificationPermission();



        getUserPreferences();

    }


    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.d("Notifications", "Permission already granted.");
            }
            else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(this, "Please enable notifications to receive important updates about your bookings.", Toast.LENGTH_LONG).show();
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
            else {
                Log.d("Notifications", "Requesting notification permission.");
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }


    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, MainActivity.this);
        loginModel = new Gson().fromJson(userData, LoginModel.class);

        checkUserData();
        initialization();

    }

    private void checkUserData() {
        setUpToolBar(binding.toolbar, this, loginModel.getmVendorImg());

        if (loginModel.getmVendorName() != null && !loginModel.getmVendorName().isEmpty() /*&&
                loginModel.getmVendorPanNo() != null && !loginModel.getmVendorPanNo().isEmpty() &&
                loginModel.getmVendorEmail() != null && !loginModel.getmVendorEmail().isEmpty() &&
                loginModel.getmVendorAdharNo() != null && !loginModel.getmVendorAdharNo().isEmpty() &&
                loginModel.getmVendorAdharFront() != null && !loginModel.getmVendorAdharFront().isEmpty() &&
                loginModel.getmVendorAdharBack() != null && !loginModel.getmVendorAdharBack().isEmpty()*/) {

            // Profile is complete, proceed as required

        } else {
            Intent intent = new Intent(MainActivity.this, AfterSplashActivity.class);
            Toast.makeText(this, "Please Complete Profile Details..", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

    }


    private void initialization() {
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.bottom_nav_fragment);
        assert navHost != null;
        bottomNavController = navHost.getNavController();
        NavigationUI.setupWithNavController(binding.navBottom, bottomNavController);



    }

    public void navigateToFragment(int id) {
        bottomNavController.navigate(id);
    }

    public void navigateToFragment(int id, Bundle bundle) {
        bottomNavController.navigate(id, bundle);
    }

    public void removeFromBackStack(int fragmentCard) {
        bottomNavController.popBackStack(fragmentCard, true);
    }


    private void updateFCM() {

        String fcmToken = PreferenceUtils.getString(Constant.PreferenceConstant.FIREBASE_TOKEN, MainActivity.this);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.updateFCM(loginModel.getmVendorId(), fcmToken);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        } else {
                        }
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("Failure", t.toString());
            }
        });
    }


    private void logoutBottomDialog() {
        LogoutBottomDialogBinding logoutBottomDialogBinding;
        logoutBottomDialogBinding = LogoutBottomDialogBinding.inflate(getLayoutInflater());

        dialog = new BottomSheetDialog(MainActivity.this);
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
                PreferenceUtils.setBoolean(Constant.PreferenceConstant.IS_LOGIN, false, MainActivity.this);
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

  /*  private void getAdvertise() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AdvertiseModel> call = apiService.get_advertise();
        call.enqueue(new Callback<AdvertiseModel>() {
            @Override
            public void onResponse(Call<AdvertiseModel> call, Response<AdvertiseModel> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getData().get(0).getMAdvVendor().equalsIgnoreCase("1")&& Utils.isAdvShow)
                            showAdvertiseDialog(MainActivity.this, response.body().getData().get(0));
                    }

                } catch (Exception e) {


                }

            }

            @Override
            public void onFailure(Call<AdvertiseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failure", t.toString());

//                showError("Something went wrong");
            }
        });
    }*/


    public static void showAdvertiseDialog(
            Context context,
            String item
    ) {
        Utils.isAdvShow=false;
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_advertise);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        ImageView imgAdvertise = dialog.findViewById(R.id.imgAdvertise);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);


        // Load image from m_adv_image
        Glide.with(context)
                .load(ImagePathDecider.getBannerImagePath() + item )
                .placeholder(android.R.color.darker_gray)
                .dontTransform()
                .override(Target.SIZE_ORIGINAL)
                .into(imgAdvertise);

        // Close dialog
        imgClose.setOnClickListener(v -> dialog.dismiss());



        dialog.show();
    }
}