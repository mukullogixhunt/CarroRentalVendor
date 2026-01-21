package com.carro.vendor.ui.fragment.bookings;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.FragmentActiveBookingsBinding;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.BookingListResponse;
import com.carro.vendor.listener.BookingClickListener;
import com.carro.vendor.model.BookingListModel;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.ui.adapter.BookingListAdapter;
import com.carro.vendor.ui.common.BaseFragment;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActiveBookingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiveBookingsFragment extends BaseFragment implements BookingClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActiveBookingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiveBookingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiveBookingsFragment newInstance(String param1, String param2) {
        ActiveBookingsFragment fragment = new ActiveBookingsFragment();
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

    FragmentActiveBookingsBinding binding;
    LoginModel loginModel = new LoginModel();
    BookingListAdapter bookingListAdapter;
    String booking_id = "";
    List<BookingListModel> bookingListModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentActiveBookingsBinding.inflate(getLayoutInflater());
        getUserPreferences();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);

        initialization();
    }

    private void initialization(){

        getActiveBookingApi();
    }
    private void getActiveBookingApi() {

        showLoader();

        binding.lvNoData.setVisibility(View.VISIBLE);
        binding.rvActiveBookings.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BookingListResponse> call = apiInterface.active_booking(loginModel.getmVendorId());
        call.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            binding.lvNoData.setVisibility(View.GONE);
                            binding.rvActiveBookings.setVisibility(View.VISIBLE);

                            bookingListModelList.clear();
                            bookingListModelList.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            binding.rvActiveBookings.setLayoutManager(linearLayoutManager);
                            bookingListAdapter = new BookingListAdapter(getContext(),bookingListModelList,true,ActiveBookingsFragment.this);
                            binding.rvActiveBookings.setAdapter(bookingListAdapter);

                        } else {
                            hideLoader();
                            binding.lvNoData.setVisibility(View.VISIBLE);
                            binding.rvActiveBookings.setVisibility(View.GONE);
                        }
                    } else {
                        hideLoader();
                        binding.lvNoData.setVisibility(View.VISIBLE);
                        binding.rvActiveBookings.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    hideLoader();
                    e.printStackTrace();
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    binding.rvActiveBookings.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BookingListResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                binding.lvNoData.setVisibility(View.VISIBLE);
                binding.rvActiveBookings.setVisibility(View.GONE);
                showError("Something went wrong");
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getActiveBookingApi();
    }

    @Override
    public void onBookingClick(BookingListModel bookingListModel, String b_status) {
        booking_id = bookingListModel.getmBkingId();
        if (b_status.equals("complete")) {
            completeBookingAPi();
        } else if (b_status.equals("cancel")) {
            cancelBookingAPi();

        }
    }

    private void cancelBookingAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.cancel_booking(booking_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            alertMessage("Booking Cancelled Successfully!...");
                            getActiveBookingApi();

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

            }
        });
    }
    private void completeBookingAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.complete_booking(booking_id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            alertMessage("Booking Completed Successfully!...");
                            getActiveBookingApi();

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

            }
        });
    }
    private void alertMessage(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(msg);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}