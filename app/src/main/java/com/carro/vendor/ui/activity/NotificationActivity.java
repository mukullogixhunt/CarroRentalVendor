package com.carro.vendor.ui.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.NotificationResponse;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.ActivityNotificationBinding;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.model.NotificationModel;
import com.carro.vendor.ui.adapter.NotificationAdapter;
import com.carro.vendor.ui.common.BaseActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class NotificationActivity extends BaseActivity {

    private static final String TAG = "NotificationActivity";
    private ActivityNotificationBinding binding;
    private String userId;
    private NotificationAdapter notificationAdapter;
    private final List<NotificationModel> notificationModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getNotificationPreference();
    }

    private void getNotificationPreference() {
        userId = PreferenceUtils.getString(Constant.PreferenceConstant.USER_ID, this);
        initiateNotification();
    }

    private void initiateNotification() {
        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, this);
        LoginModel loginModel = new Gson().fromJson(userData, LoginModel.class);
        setUpToolBar(binding.toolbar, this, loginModel.getmVendorImg());
        binding.toolbar.ivNofification.setVisibility(GONE);
        binding.toolbar.ivClearAll.setVisibility(VISIBLE);


        binding.toolbar.ivClearAll.setOnClickListener(v -> showClearConfirmationDialog());

        setupRecyclerView();
        getUserNotificationApi();
    }

    private void setupRecyclerView() {
        notificationAdapter = new NotificationAdapter(this, notificationModelList);
        binding.rvNotification.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotification.setAdapter(notificationAdapter);
    }

    private void showClearConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Notifications")
                .setMessage("Are you sure you want to delete all notifications? This action cannot be undone.")
                .setPositiveButton("Clear All", (dialog, which) -> deleteNotificationsApi())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteNotificationsApi() {
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.deleteNotification(userId);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                hideLoader();
                if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                    Toast.makeText(NotificationActivity.this, "All notifications cleared", Toast.LENGTH_SHORT).show();
                    // Clear the list and update the UI
                    notificationModelList.clear();
                    notificationAdapter.notifyDataSetChanged();
                    updateUIState();
                } else {
                    showError("Failed to clear notifications. Please try again.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                hideLoader();
                Log.e(TAG, "deleteNotificationsApi failed", t);
                showError("Something went wrong");
            }
        });
    }

    private void getUserNotificationApi() {
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationResponse> call = apiInterface.notification(userId);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<NotificationResponse> call, @NonNull Response<NotificationResponse> response) {
                hideLoader();
                try {
                    if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        notificationModelList.clear();
                        notificationModelList.addAll(response.body().getData());
                        notificationAdapter.notifyDataSetChanged();
                    } else {
                        // Handle cases where the API returns success=false but a 200 code
                        notificationModelList.clear();
                        notificationAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "getUserNotificationApi onResponse exception", e);
                    notificationModelList.clear();
                    notificationAdapter.notifyDataSetChanged();
                }
                updateUIState();
            }

            @Override
            public void onFailure(@NonNull Call<NotificationResponse> call, @NonNull Throwable t) {
                hideLoader();
                Log.e(TAG, "getUserNotificationApi onFailure", t);
                showError("Something went wrong");
                updateUIState();
            }
        });
    }

    /**
     * A central method to update the visibility of UI elements based on the list content.
     */
    private void updateUIState() {
        if (notificationModelList.isEmpty()) {
            binding.rvNotification.setVisibility(View.GONE);
            binding.lvNoData.setVisibility(VISIBLE);
            binding.toolbar.ivClearAll.setVisibility(View.GONE); // Hide "Clear All" when no data
        } else {
            binding.rvNotification.setVisibility(VISIBLE);
            binding.lvNoData.setVisibility(View.GONE);
            binding.toolbar.ivClearAll.setVisibility(VISIBLE); // Show "Clear All" when there is data
        }
    }
}



/*
public class NotificationActivity extends BaseActivity implements NotificationClickListener {

    ActivityNotificationBinding binding;

    NotificationAdapter notificationAdapter;

    List<NotificationModel> notificationModelList = new ArrayList<>();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });        getNotificationPreference();

    }


    private void getNotificationPreference() {
        userId = PreferenceUtils.getString(Constant.PreferenceConstant.USER_ID, NotificationActivity.this);

        initialization();
    }

    private void initialization() {

        getUserNotificationApi();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.tvClearAll.setOnClickListener(v -> showClearConfirmationDialog());



    }



    private void showClearConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Notifications")
                .setMessage("Are you sure you want to delete all notifications? This action cannot be undone.")
                .setPositiveButton("Clear All", (dialog, which) -> deleteNotificationsApi())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteNotificationsApi() {
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.deleteNotification(userId);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                hideLoader();
                if (response.isSuccessful() && response.body() != null && response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                    Toast.makeText(NotificationActivity.this, "All notifications cleared", Toast.LENGTH_SHORT).show();
                    // Clear the list and update the UI
                    notificationModelList.clear();
                    notificationAdapter.notifyDataSetChanged();
                    updateUIState();
                } else {
                    showError("Failed to clear notifications. Please try again.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                hideLoader();
                Log.e(TAG, "deleteNotificationsApi failed", t);
                showError("Something went wrong");
            }
        });
    }


    private void getUserNotificationApi() {

        showLoader();

        binding.lvNoData.setVisibility(View.VISIBLE);
        binding.rvNotification.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationResponse> call = apiInterface.notification(userId);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            binding.lvNoData.setVisibility(View.GONE);
                            binding.rvNotification.setVisibility(View.VISIBLE);

                            notificationModelList.clear();
                            notificationModelList.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
                            binding.rvNotification.setLayoutManager(linearLayoutManager);
                            notificationAdapter = new NotificationAdapter(NotificationActivity.this, notificationModelList,NotificationActivity.this);
                            binding.rvNotification.setAdapter(notificationAdapter);

                            notificationAdapter.notifyDataSetChanged();

                        } else {
                            hideLoader();
                            binding.lvNoData.setVisibility(View.VISIBLE);
                            binding.rvNotification.setVisibility(View.GONE);
                        }
                    } else {
                        hideLoader();
                        binding.lvNoData.setVisibility(View.VISIBLE);
                        binding.rvNotification.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    binding.rvNotification.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                binding.lvNoData.setVisibility(View.VISIBLE);
                binding.rvNotification.setVisibility(View.GONE);
                showError("Something went wrong");
            }
        });


    }
    private void getAcceptNotificationApi(String booking_id) {

        showLoader();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_notify_booking(userId,booking_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            getUserNotificationApi();
                            showAlert("Data Updated Successfully!");


                        } else {
                            hideLoader();

                        }
                    } else {
                        hideLoader();
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });


    }

    @Override
    public void onNotifClick(NotificationModel notificationModel) {
        getAcceptNotificationApi(notificationModel.getmNotifBooking());
    }
}*/