package com.carro.vendor.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.carro.vendor.api.RazorPayApiClient;
import com.carro.vendor.api.response.CreateOrderResponse;
import com.google.gson.Gson;
import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.ActivityAddWalletAmountBinding;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.ui.common.BaseActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.PreferenceUtils;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import de.mateware.snacky.Snacky;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWalletAmountActivity extends BaseActivity implements PaymentResultListener {

    ActivityAddWalletAmountBinding binding;
    LoginModel loginModel = new LoginModel();
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddWalletAmountBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });        getPreference();

    }
    private void getPreference(){
        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA,AddWalletAmountActivity.this);
        loginModel = new Gson().fromJson(userData, LoginModel.class);
        if(getIntent().getStringExtra(Constant.BundleExtras.WALLET_AMOUNT).isEmpty()){
            amount="0";
        }else{
            amount=getIntent().getStringExtra(Constant.BundleExtras.WALLET_AMOUNT);
        }
        ProgressDialog pdLoading = new ProgressDialog(AddWalletAmountActivity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.show();
        RazorPayApiClient.getRazorpayClient().create(ApiInterface.class)
                .createRazorpayOrderTest(String.valueOf(amount))
                .enqueue(new Callback<CreateOrderResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CreateOrderResponse> call, @NonNull Response<CreateOrderResponse> response) {
                        pdLoading.dismiss();
                        if (response.isSuccessful() && response.body() != null && "success".equalsIgnoreCase(response.body().getResponseStatus())) {
                            String razorpayOrderId = response.body().getOrderId();
                            if (razorpayOrderId != null && !razorpayOrderId.isEmpty()) {
                                double rupeeToPaisa = Integer.parseInt(amount) * 100;
                                startPayment(rupeeToPaisa, razorpayOrderId);
                            } else {
                                showError("Server did not provide an Order ID.");
                            }
                        } else {
                            showError("Payment initialization failed on server.");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CreateOrderResponse> call, @NonNull Throwable t) {
                        pdLoading.dismiss();
                        Log.e("ConfirmBookingActivity", "createRazorpayOrder API call failed", t);
                        showError("An error occurred. Please check your connection.");
                    }
                });
    }


    private void insertWalletApi(String amount) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.insert_wallet(loginModel.getmVendorId(),amount);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            Toast.makeText(AddWalletAmountActivity.this, "Amount Added Successfully..!", Toast.LENGTH_SHORT).show();
                            getOnBackPressedDispatcher().onBackPressed();

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

    private void startPayment(double netPrice, String razorPayOrderId) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(getString(R.string.razor_pay_key));
        checkout.setImage(R.mipmap.ic_launcher);
        final Activity activity = AddWalletAmountActivity.this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Reference No. #" + System.currentTimeMillis());
            options.put("order_id", razorPayOrderId);
            options.put("currency", "INR");
            options.put("amount", netPrice);
            options.put("prefill.email", loginModel.getmVendorEmail());
            options.put("prefill.contact", loginModel.getmVendorMobile());
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TaG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        //TODO call API for payment
//        insertSubscriptionPlan(s);
        insertWalletApi(amount);
        Checkout.clearUserData(AddWalletAmountActivity.this);
    }

    @Override
    public void onPaymentError(int i, String s) {
        setMessageForSnackbar("Payment Failed", false);
        Checkout.clearUserData(AddWalletAmountActivity.this);
        getOnBackPressedDispatcher().onBackPressed();
       Toast.makeText(AddWalletAmountActivity.this, "Payment Failed", Toast.LENGTH_LONG).show();

//        Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
//        intent.putExtra(Constant.BundleExtras.PAYMENT_STATUS, "2");
//        startActivity(intent);
    }

    private void setMessageForSnackbar(String msg, boolean flag) {
        if (flag) {
            Snacky.builder()
                    .setActivity(AddWalletAmountActivity.this)
                    .setActionText("Ok")
                    .setActionClickListener(v -> {
                        //do something
                    })
                    .setText(msg)
                    .setDuration(Snacky.LENGTH_INDEFINITE)
                    .success()
                    .show();
        } else {
            Snacky.builder()
                    .setActivity(AddWalletAmountActivity.this)
                    .setActionText("Ok")
                    .setActionClickListener(v -> {
                        //do something
                    })
                    .setText(msg)
                    .setDuration(Snacky.LENGTH_INDEFINITE)
                    .error()
                    .show();
        }
    }

}