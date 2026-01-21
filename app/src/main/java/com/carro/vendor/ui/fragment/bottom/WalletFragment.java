package com.carro.vendor.ui.fragment.bottom;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import com.carro.vendor.R;
import com.carro.vendor.api.response.RecommendedResponse;
import com.carro.vendor.databinding.AddMoneyWalletDialogBinding;
import com.carro.vendor.databinding.FragmentWalletBinding;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.WalletCountResponse;
import com.carro.vendor.api.response.WalletTransResponse;

import com.carro.vendor.listener.RecommendedClickListener;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.model.RecommendedModel;
import com.carro.vendor.model.WalletTransModel;
import com.carro.vendor.ui.activity.AddWalletAmountActivity;
import com.carro.vendor.ui.adapter.RecommendedAdapter;
import com.carro.vendor.ui.adapter.WalletTransactionAdapter;
import com.carro.vendor.ui.common.BaseFragment;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.ImagePathDecider;
import com.carro.vendor.utils.PreferenceUtils;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends BaseFragment implements RecommendedClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentWalletBinding binding;
    LoginModel loginModel = new LoginModel();
    Dialog dialog;
    String amount;

    WalletTransactionAdapter walletTransactionAdapter;
    AddMoneyWalletDialogBinding addMoneyWalletDialogBinding;
    List<WalletTransModel> walletTransModelList = new ArrayList<>();
    List<RecommendedModel> recommendedModelList = new ArrayList<>();
    RecommendedAdapter recommendedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWalletBinding.inflate(getLayoutInflater());
        getUserPreferences();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);

        Glide.with(getContext())
                .load(ImagePathDecider.getUserImagePath() + loginModel.getmVendorImg())
                .error(R.drawable.img_no_profile)
                .into(binding.ivUser);

        initialization();
    }

    private void initialization(){
        getWalletCountApi();
        getWalletTransactionApi();
        binding.btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoneyBottomDialog();
            }
        });
    }

    private void addMoneyBottomDialog() {
        addMoneyWalletDialogBinding = AddMoneyWalletDialogBinding.inflate(getLayoutInflater());
        recommendedAmtApi();
        dialog = new BottomSheetDialog(getContext());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(addMoneyWalletDialogBinding.getRoot());
        dialog.show();

        addMoneyWalletDialogBinding.btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                amount=addMoneyWalletDialogBinding.etAmount.getText().toString();
                Intent intent=new Intent(getContext(), AddWalletAmountActivity.class);
                intent.putExtra(Constant.BundleExtras.WALLET_AMOUNT,amount);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserPreferences();
    }

    private void getWalletTransactionApi() {
        binding.lvNoData.setVisibility(View.VISIBLE);
        binding.rvTransaction.setVisibility(View.GONE);
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<WalletTransResponse> call = apiInterface.wallet(loginModel.getmVendorId());
        call.enqueue(new Callback<WalletTransResponse>() {
            @Override
            public void onResponse(Call<WalletTransResponse> call, Response<WalletTransResponse> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            hideLoader();
                            binding.lvNoData.setVisibility(View.GONE);
                            binding.rvTransaction.setVisibility(View.VISIBLE);

                            walletTransModelList.clear();
                            walletTransModelList.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            binding.rvTransaction.setLayoutManager(linearLayoutManager);
                            walletTransactionAdapter = new WalletTransactionAdapter(getContext(),walletTransModelList);
                            binding.rvTransaction.setAdapter(walletTransactionAdapter);
                            walletTransactionAdapter.notifyDataSetChanged();

                        } else {
                            hideLoader();
                            binding.lvNoData.setVisibility(View.VISIBLE);
                            binding.rvTransaction.setVisibility(View.GONE);
                        }
                    } else {
                        hideLoader();
                        binding.lvNoData.setVisibility(View.VISIBLE);
                        binding.rvTransaction.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    binding.rvTransaction.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<WalletTransResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                binding.lvNoData.setVisibility(View.VISIBLE);
                binding.rvTransaction.setVisibility(View.GONE);
                showError("Something went wrong");
            }
        });


    }
    private void getWalletCountApi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<WalletCountResponse> call = apiInterface.countWallet(loginModel.getmVendorId());
        call.enqueue(new Callback<WalletCountResponse>() {
            @Override
            public void onResponse(Call<WalletCountResponse> call, Response<WalletCountResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                           binding.tvAmount.setText("Rs. "+String.valueOf(response.body().getData()));

                        } else {

                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<WalletCountResponse> call, Throwable t) {
                Log.e("Failure", t.toString());

            }
        });


    }

    private void recommendedAmtApi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RecommendedResponse> call = apiInterface.recent_paid_wallet(loginModel.getmVendorId());
        call.enqueue(new Callback<RecommendedResponse>() {
            @Override
            public void onResponse(Call<RecommendedResponse> call, Response<RecommendedResponse> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            recommendedModelList.clear();
                            recommendedModelList.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            addMoneyWalletDialogBinding.rvRecommended.setLayoutManager(linearLayoutManager);
                            recommendedAdapter = new RecommendedAdapter(getContext(),recommendedModelList,WalletFragment.this);
                            addMoneyWalletDialogBinding.rvRecommended.setAdapter(recommendedAdapter);
                            recommendedAdapter.notifyDataSetChanged();

                        } else {

                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<RecommendedResponse> call, Throwable t) {
                Log.e("Failure", t.toString());

            }
        });


    }


    @Override
    public void onRecommendedClick(RecommendedModel recommendedModel) {
        String amt =recommendedModel.getmWalletAmt();
        addMoneyWalletDialogBinding.etAmount.setText(amt);
    }
}