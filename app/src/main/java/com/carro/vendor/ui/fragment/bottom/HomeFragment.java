package com.carro.vendor.ui.fragment.bottom;

import static com.carro.vendor.ui.activity.MainActivity.showAdvertiseDialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.SliderResponse;
import com.carro.vendor.databinding.FragmentHomeBinding;
import com.carro.vendor.model.HomeData;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.model.SliderModel;
import com.carro.vendor.ui.adapter.SliderAdapter;
import com.carro.vendor.ui.adapter.TabAdapter;
import com.carro.vendor.ui.fragment.bookings.ActiveBookingsFragment;
import com.carro.vendor.ui.fragment.bookings.NewBookingsFragment;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.ImagePathDecider;
import com.carro.vendor.utils.PreferenceUtils;
import com.carro.vendor.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    FragmentHomeBinding binding;
    LoginModel loginModel = new LoginModel();
    private SliderAdapter sliderAdapter;
    private List<SliderModel> sliderList = new ArrayList<>();
    private Handler sliderHandler = new Handler();
    private int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(getLayoutInflater());
        getUserPreferences();
        initiateTabLayout();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);

//        binding.tvUserName.setText(loginModel.getmVendorName());
//        binding.tvNumber.setText(loginModel.getmVendorMobile());
//        binding.tvEmail.setText(loginModel.getmVendorEmail());

        initiateSlider();
    }

    private void initiateTabLayout() {

        binding.tab.setupWithViewPager(binding.viewpager);
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        NewBookingsFragment newBookingsFragment = new NewBookingsFragment();

        ActiveBookingsFragment activeBookingsFragment = new ActiveBookingsFragment();



        tabAdapter.clearFragment();

        tabAdapter.addFragment(newBookingsFragment, "New Bookings");
        tabAdapter.addFragment(activeBookingsFragment, "Active Bookings");

        binding.viewpager.setAdapter(tabAdapter);

//        if (AllMentorModuleFragment.allMentorModuleFragment != null){
//            AllMentorModuleFragment.allMentorModuleFragment.courseDetailsModel = courseDetailsModel;
//            if(AllMentorModuleFragment.allMentorModuleFragment.moduleAdapter!= null){
//                AllMentorModuleFragment.allMentorModuleFragment.setDataToRecycle();
//            }
//        }

    }

    private void initiateSlider() {
        getSlider();
        getHomeImage();

        sliderAdapter = new SliderAdapter(getContext(), sliderList, binding.vpBanner);
        binding.vpBanner.setAdapter(sliderAdapter);

        binding.vpBanner.setClipToPadding(false);
        binding.vpBanner.setClipChildren(false);
        binding.vpBanner.setOffscreenPageLimit(3);
        binding.vpBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(15));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.85f + r * 0.15f);
//            }
//        });
        binding.vpBanner.setPageTransformer(compositePageTransformer);

        binding.vpBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
            }
        });


    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {

            if (binding.vpBanner.getCurrentItem() == (sliderList.size() - 1)) {
                pos = 0;
            } else {
                pos = binding.vpBanner.getCurrentItem() + 1;
            }

            binding.vpBanner.setCurrentItem(pos);
        }
    };


    private void getHomeImage() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<HomeData> call = apiService.home_page();
        call.enqueue(new Callback<HomeData>() {
            @Override
            public void onResponse(Call<HomeData> call, Response<HomeData> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResponse().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            HomeData.DataItem data=response.body().getData().get(0);



                            binding.textView.setText(data.getMHvTagline());

                            Glide.with(getActivity())
                                    .load(ImagePathDecider.getBannerImagePath() + data.getMHvIcon())
                                    .error(R.drawable.img_car)
                                    .into(binding.imageView);




                            if (data.getMHvAdvImgSh().equalsIgnoreCase("1") && Utils.isAdvShow)
                                showAdvertiseDialog(getActivity(), data.getMHvAdvImg());



                        } else {


                        }
                    } else {

                    }
                } catch (Exception e) {
//                    log_e(this.getClass().getSimpleName(), "onResponse: ", e);

                }

            }

            @Override
            public void onFailure(Call<HomeData> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failure", t.toString());

//                showError("Something went wrong");
            }
        });
    }

    private void getSlider() {

        binding.materialCardView.setVisibility(View.GONE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SliderResponse> call = apiService.get_slider();
        call.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            binding.materialCardView.setVisibility(View.VISIBLE);

                            sliderList.clear();
                            sliderList.addAll(response.body().getData());
                            binding.vpBanner.setVisibility(View.VISIBLE);
                            //  new pager
                            sliderAdapter.notifyDataSetChanged();

                        } else {

                            binding.materialCardView.setVisibility(View.GONE);
                        }
                    } else {
                        binding.materialCardView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
//                    log_e(this.getClass().getSimpleName(), "onResponse: ", e);
                    binding.materialCardView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failure", t.toString());
                binding.materialCardView.setVisibility(View.GONE);
//                showError("Something went wrong");
            }
        });
    }

}