package com.carro.vendor.ui.activity;

import static android.view.View.GONE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.gson.Gson;


import com.carro.vendor.BuildConfig;
import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.LoginResponse;
import com.carro.vendor.databinding.ActivityEditProfileBinding;
import com.carro.vendor.model.CityModel;
import com.carro.vendor.model.StateModel;
import com.carro.vendor.model.UserDetailModel;
import com.carro.vendor.ui.common.BaseActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.ImagePathDecider;
import com.carro.vendor.utils.PreferenceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity {

    ActivityEditProfileBinding binding;
    UserDetailModel userDetailModel = new UserDetailModel();
    private List<String> genderList = new ArrayList<>();

    private Dialog dialog;
    private Uri imageUri;
    private File uploadImg = null;
    private File adharFrontImg = null;
    private File adharBackImg = null;
    private File licenceImg = null;
    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;

    List<StateModel> stateModelList = new ArrayList<>();
    List<CityModel> cityModelList = new ArrayList<>();
  
    String state = "";
    String state_name = "";
    String city = "";

    String user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });        getUserPreferences();
    }

    private void getUserPreferences() {
        user_id = PreferenceUtils.getString(Constant.PreferenceConstant.USER_ID, EditProfileActivity.this);
        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, EditProfileActivity.this);
        userDetailModel = new Gson().fromJson(userData, UserDetailModel.class);

        initialization();
    }

    private void initialization() {

        setData();
//        getStateAPi();
//        getCityAPi("CT");

        setUpToolBar(binding.toolbar, this, userDetailModel.getmVendorImg());
        binding.toolbar.ivUser.setVisibility(GONE);


        binding.ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerDialog();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateProfileApi();
            }
        });
    }


    private void setData(){
        binding.etName.setText(userDetailModel.getmVendorName());
        binding.etEmail.setText(userDetailModel.getmVendorEmail());
//        binding.etAadharNumber.setText(userDetailModel.getmVendorAdharNo());
//        binding.etPanNumber.setText(userDetailModel.getmVendorPanNo());
        binding.etAltMobile.setText(userDetailModel.getmVendorAltMobile());
//        binding.etPinCode.setText(userDetailModel.getmVendorPincode());

//        binding.etAddress.setText(userDetailModel.getmVendorAddress());
//        binding.tvPanCard.setText(userDetailModel.getmVendorPanImg());
//        binding.tvAadhaarFront.setText(userDetailModel.getmVendorAdharFront());
//        binding.tvAadhaarBack.setText(userDetailModel.getmVendorAdharBack());

        Glide.with(EditProfileActivity.this)
                .load(ImagePathDecider.getUserImagePath() + userDetailModel.getmVendorImg())
                .error(R.drawable.img_no_profile)
                .into(binding.ivProfile);
    }


/*    private void getStateAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StateResponse> call = apiInterface.get_country_state("IN");
        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            stateModelList.clear();
                            stateModelList.add(new StateModel("Select State"));
                            stateModelList.addAll(response.body().getData());


                            ArrayAdapter<StateModel> itemAdapter = new ArrayAdapter<>(EditProfileActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stateModelList);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            binding.spState.setAdapter(itemAdapter);


                            if (userDetailModel.getmVendorState() != null && !userDetailModel.getmVendorState().isEmpty()) {
                                int stateSelected = 0;
                                for (int i = 0; i < stateModelList.size(); i++) {
                                    if (stateModelList.get(i).getName().equals(userDetailModel.getmVendorState())) {
                                        stateSelected = i;
                                    }
                                }
                                binding.spState.setSelection(stateSelected);
                            }


                            binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    state = stateModelList.get(position).getIso2();
                                    state_name = stateModelList.get(position).getName();
                                    getCityAPi(state);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } else {
                        }
                    } else {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {



            }
        });
    }
    private void getCityAPi(String iso) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CityResponse> call = apiInterface.get_state_cities("IN",iso);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            cityModelList.clear();
                            cityModelList.add(new CityModel("Select City"));
                            cityModelList.addAll(response.body().getData());


                            ArrayAdapter<CityModel> itemAdapter = new ArrayAdapter<>(EditProfileActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cityModelList);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            binding.spCity.setAdapter(itemAdapter);


                            if (userDetailModel.getmVendorCity() != null && !userDetailModel.getmVendorCity().isEmpty()) {
                                int citySelected = 0;
                                for (int i = 0; i < cityModelList.size(); i++) {
                                    if (cityModelList.get(i).getName().equals(userDetailModel.getmVendorCity())) {
                                        citySelected = i;
                                    }
                                }
                                binding.spCity.setSelection(citySelected);
                            }


                            binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    city = cityModelList.get(position).getName();


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } else {
                        }
                    } else {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {



            }
        });
    }*/


    private void imagePickerDialog() {
        dialog = new Dialog(EditProfileActivity.this, R.style.my_dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.image_selection_dialog);
        dialog.show();

        ImageView ivCamera = dialog.findViewById(R.id.ivCamera);
        ImageView ivGallery = dialog.findViewById(R.id.ivGallery);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        ivCamera.setOnClickListener(view -> checkCameraPermission());
        ivGallery.setOnClickListener(view -> checkGalleryPermission());
        tvCancel.setOnClickListener(view -> dialog.dismiss());
    }

    private void checkCameraPermission() {

        if (dialog != null) {
            dialog.dismiss();
        }

        if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            openCamera();
        } else {
            openCamera();
        }
    }

    ///////////////////////// check gallery permission///////////
    private void checkGalleryPermission() {
        if (dialog != null) {
            dialog.dismiss();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
                openGallery();
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
                openGallery();
            } else {
                openGallery();
            }
        }
    }

    /////////////////// for open camera /////////////////////
    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat(Constant.yyyyMMdd_HHmmss, Locale.getDefault()).format(new java.util.Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        try {
            File file = File.createTempFile("IMG_" + timeStamp, ".jpg", EditProfileActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            imageUri = FileProvider.getUriForFile(EditProfileActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra(Constant.FILENAME, imageFileName);
            cameraActivityResultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); // Set the MIME type to allow any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            galleryActivityResultLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            launchImageCropper();
        }
    });


    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // File selected successfully
            imageUri = result.getData().getData();
            launchImageCropper();
            // Now you can use the selectedFileUri as needed
        }
    });


    private void launchImageCropper() {
        CropImageOptions cropImageOptions = new CropImageOptions();
        cropImageOptions.imageSourceIncludeGallery = false;
        cropImageOptions.imageSourceIncludeCamera = true;
        cropImageOptions.outputCompressQuality = 60;
        cropImageOptions.aspectRatioX = 1;
        cropImageOptions.aspectRatioY = 1;
        cropImageOptions.fixAspectRatio = true;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(imageUri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            String croppedImagePath = result.getUriFilePath(EditProfileActivity.this, true);

            uploadImg = new File(croppedImagePath);
            Glide.with(EditProfileActivity.this)
                    .load(uploadImg)
                    .into(binding.ivProfile);
        }
    });

    private void callUpdateProfileApi() {

        String user_name = binding.etName.getText().toString();
        String userEmail = binding.etEmail.getText().toString();
//        String userAddress = binding.etAddress.getText().toString();
        String userAltMobile = binding.etAltMobile.getText().toString();
//        String userPincode = binding.etPinCode.getText().toString();

        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody rbUserName = RequestBody.create(MediaType.parse("text/plain"), user_name);
        RequestBody rbUserEmail = RequestBody.create(MediaType.parse("text/plain"), userEmail);
//        RequestBody rbAddress = RequestBody.create(MediaType.parse("text/plain"), userAddress);
        RequestBody rbAltMobile = RequestBody.create(MediaType.parse("text/plain"), userAltMobile);
//        RequestBody rbPinCode = RequestBody.create(MediaType.parse("text/plain"), userPincode);
//        RequestBody rbState = RequestBody.create(MediaType.parse("text/plain"), state_name);
//        RequestBody rbCity = RequestBody.create(MediaType.parse("text/plain"), city);

        MultipartBody.Part profileImagePart = null;
        if (uploadImg != null) {
            profileImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.USER_PIC, uploadImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), uploadImg));
        }
        MultipartBody.Part adharFrontImagePart = null;
        if (adharFrontImg != null) {
            adharFrontImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.ADHAR_FRONT, adharFrontImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), adharFrontImg));
        }
        MultipartBody.Part adharBackImagePart = null;
        if (adharBackImg != null) {
            adharBackImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.ADHAR_BACK, adharBackImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), adharBackImg));
        }
        MultipartBody.Part licenceImagePart = null;
        if (licenceImg != null) {
            licenceImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.LIC_IMG, licenceImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), licenceImg));
        }

        showLoader();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.update_basic_details(rbUserId, rbUserName, rbUserEmail, rbAltMobile,profileImagePart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            getOnBackPressedDispatcher().onBackPressed();

                        } else {
                            hideLoader();
                            showError(response.message());
                        }
                    } else {
                        hideLoader();
                        showError(response.message());
                    }
                } catch (Exception e) {
                    hideLoader();
                    log_e(this.getClass().getSimpleName(), "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

}