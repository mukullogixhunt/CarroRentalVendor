package com.carro.vendor.ui.activity;

import static com.carro.vendor.utils.Constant.QUICKEKYC_API_KEY;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.carro.vendor.api.ApiClientVerification;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.BranchResponse;
import com.carro.vendor.api.response.LoginResponse;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.ActivityCompleteProfileBinding;
import com.carro.vendor.databinding.UploadAdharDialogBinding;
import com.carro.vendor.databinding.VerifyAdharDialogBinding;
import com.carro.vendor.model.AadharOtpSendModel;
import com.carro.vendor.model.AadharVerificationModel;
import com.carro.vendor.model.BranchModel;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.ui.common.BaseActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

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


public class CompleteProfileActivity extends BaseActivity {

    ActivityCompleteProfileBinding binding;

    private Dialog dialog;
    private Uri imageUri;
    private File uploadImg = null;

    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;
    LoginModel loginModel = new LoginModel();
    List<BranchModel> branchModelList = new ArrayList<>();

    int type;
    String user_id;
    String branch = "";
    String branch_name = "",requestId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getUserPreferences();

    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, CompleteProfileActivity.this);
        loginModel = new Gson().fromJson(userData, LoginModel.class);
        



        initialization();

    }

    private void initialization() {

        getBranchAPi();


        binding.ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                imagePickerDialog();
            }
        });
        binding.tvChooseAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAdharDialog();
            }
        });



        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    callUpdateProfileApi();
                }

            }
        });
    }
    


    private void getBranchAPi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BranchResponse> call = apiInterface.get_branch();
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            branchModelList.clear();
                            branchModelList.add(new BranchModel("Select Branch"));
                            branchModelList.addAll(response.body().getData());


                            ArrayAdapter<BranchModel> itemAdapter = new ArrayAdapter<>(CompleteProfileActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, branchModelList);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            binding.spBranch.setAdapter(itemAdapter);



                                if (loginModel.getmVendorBranch() != null && !loginModel.getmVendorBranch().isEmpty()) {
                                    int branchSelected = 0;
                                    for (int i = 0; i < branchModelList.size(); i++) {
                                        if (branchModelList.get(i).getmBranchId().equals(loginModel.getmVendorBranch())) {
                                            branchSelected = i;
                                        }
                                    }
                                    binding.spBranch.setSelection(branchSelected);
                                }


                            binding.spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    branch = branchModelList.get(position).getmBranchId();
                                    branch_name = branchModelList.get(position).getmBranchTitle();
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
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                showError("something went wrong");


            }
        });
    }



    private void imagePickerDialog() {
        dialog = new Dialog(CompleteProfileActivity.this, R.style.my_dialog);
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

        if (ContextCompat.checkSelfPermission(CompleteProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CompleteProfileActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
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
            if (ContextCompat.checkSelfPermission(CompleteProfileActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CompleteProfileActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
                openGallery();
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(CompleteProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CompleteProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
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
            File file = File.createTempFile("IMG_" + timeStamp, ".jpg", CompleteProfileActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            imageUri = FileProvider.getUriForFile(CompleteProfileActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
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
        cropImageOptions.fixAspectRatio = false;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(imageUri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            String croppedImagePath = result.getUriFilePath(CompleteProfileActivity.this, true);

            switch (type) {
                case 1:
                    uploadImg = new File(croppedImagePath);
                    Glide.with(CompleteProfileActivity.this)
                            .load(uploadImg)
                            .into(binding.ivProfile);
                    break;

                default:
                    Log.d("TypeInvalid", "Invalid Type");
                    break;
            }
        }
    });

    private String getImageName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }


    private void uploadAdharDialog() {
        UploadAdharDialogBinding  uploadAdharDialogBinding = UploadAdharDialogBinding.inflate(getLayoutInflater());
        Dialog dialogAdhar = new Dialog(CompleteProfileActivity.this, R.style.my_dialog);
        dialogAdhar.setContentView(uploadAdharDialogBinding.getRoot());
        dialogAdhar.setCancelable(true);
        dialogAdhar.create();
        dialogAdhar.show();

        uploadAdharDialogBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callAadharSendOtp(uploadAdharDialogBinding.etAdharNumber.getText().toString(),dialogAdhar);

            }
        });

    }

    private void VerifyAadharDialog() {
        VerifyAdharDialogBinding verifyAdharDialogBinding = VerifyAdharDialogBinding.inflate(getLayoutInflater());
        Dialog dialogVerifyAdhar = new Dialog(CompleteProfileActivity.this, R.style.my_dialog);
        dialogVerifyAdhar.setContentView(verifyAdharDialogBinding.getRoot());
        dialogVerifyAdhar.setCancelable(true);
        dialogVerifyAdhar.create();
        dialogVerifyAdhar.show();


        verifyAdharDialogBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyAdharDialogBinding.otpview.getOTP().length() == 6)
                    callAadharSubmitOtp(verifyAdharDialogBinding.otpview.getOTP(),dialogVerifyAdhar);
            }
        });

    }

    private void callAadharSendOtp(String aadhar,Dialog dialogAdhar) {

        showLoader();

        JSONObject json = new JSONObject();

        try {
            json.put("key", QUICKEKYC_API_KEY);
            json.put("id_number", aadhar);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                okhttp3.MediaType.parse("application/json")
        );
        ApiInterface apiService = ApiClientVerification.getClient().create(ApiInterface.class);
        Call<AadharOtpSendModel> call = apiService.aadhaar_generate_otp(body);
        call.enqueue(new Callback<AadharOtpSendModel>() {
            @Override
            public void onResponse(Call<AadharOtpSendModel> call, Response<AadharOtpSendModel> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getStatusCode() == 200) {
                            requestId = String.valueOf(response.body().getRequestId());
                            VerifyAadharDialog();
                            dialogAdhar.dismiss();


                        } else {
                            hideLoader();
                            showError(response.body().getMessage());

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
            public void onFailure(Call<AadharOtpSendModel> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

    AadharVerificationModel.Data aadharData;
    private void callAadharSubmitOtp(String otp,Dialog dialogVerifyAdhar) {

        showLoader();

        JSONObject json = new JSONObject();

        try {
            json.put("key", QUICKEKYC_API_KEY);
            json.put("otp", otp);
            json.put("request_id", String.valueOf(requestId));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                okhttp3.MediaType.parse("application/json")
        );
        ApiInterface apiService = ApiClientVerification.getClient().create(ApiInterface.class);
        Call<AadharVerificationModel> call = apiService.aadhaar_submit_otp(body);
        call.enqueue(new Callback<AadharVerificationModel>() {
            @Override
            public void onResponse(Call<AadharVerificationModel> call, Response<AadharVerificationModel> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getStatusCode() == 200) {
                            requestId = "";

                            aadharData= response.body().getData();
                            dialogVerifyAdhar.dismiss();

                            binding.tvAadhar.setText(aadharData.getAadhaarNumber());

                            updateAadhar(response.body().getData());

                        } else {
                            hideLoader();
                            showError(response.body().getMessage());
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
            public void onFailure(Call<AadharVerificationModel> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

    private void updateAadhar(AadharVerificationModel.Data data) {
        showLoader();

        String address=data.getAddress().getLandmark()+","+data.getAddress().getHouse()+","+data.getAddress().getVtc()+","+data.getAddress().getPo()
                +","+data.getAddress().getSubdist()+","+data.getAddress().getDist()+","+data.getAddress().getState()+","+data.getAddress().getCountry();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.update_adhar(user_id, data.getAadhaarNumber(), data.getFullName(), data.getDob(),
                address  ,data.getGender(), data.getZip());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try {
                    if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                        binding.tvChooseAadhar.setText("Edit");
//                        binding.btnUploadAdhar.setBackgroundTintList(ContextCompat.getColorStateList(ProfileVerificationActivity.this, R.color.green));
                        hideLoader();
                    } else {
                        hideLoader();

                    }

                } catch (Exception e) {
                    hideLoader();

                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failure", t.toString());
                hideLoader();
                showError("Something went wrong");
            }
        });
    }

    private void callUpdateProfileApi() {

        String userName = binding.etName.getText().toString();
        String userEmail = binding.etEmail.getText().toString();

        String altMobile = binding.etAltMobile.getText().toString();
        String altPan= binding.tvPanCard.getText().toString();

        user_id = loginModel.getmVendorId();

        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody rbUserName = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody rbUserEmail = RequestBody.create(MediaType.parse("text/plain"), userEmail);
        RequestBody rbBranch = RequestBody.create(MediaType.parse("text/plain"), branch);
        RequestBody rbALtMobile = RequestBody.create(MediaType.parse("text/plain"), altMobile);
        RequestBody rbALtPan = RequestBody.create(MediaType.parse("text/plain"), altPan);


        MultipartBody.Part profileImagePart = null;
        if (uploadImg != null) {
            profileImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.USER_PIC, uploadImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), uploadImg));
        }


        showLoader();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.update_profile(rbUserId, rbUserName, rbUserEmail,
                rbALtPan, rbALtMobile,rbBranch, profileImagePart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            PreferenceUtils.setString(Constant.PreferenceConstant.USER_DATA, new Gson().toJson(response.body().getData().get(0)), CompleteProfileActivity.this);

                            Intent intent = new Intent(CompleteProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

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

    private boolean validate() {
        boolean valid = true;

        // Validate Name
        if (binding.etName.getText().toString().isEmpty()) {
            binding.etName.setError("Please enter your name!");
            valid = false;
        } else {
            binding.etName.setError(null);
        }

        // Validate Email
        if (binding.etEmail.getText().toString().isEmpty()) {
            binding.etEmail.setError("Please enter your email!");
            valid = false;
        } else {
            binding.etEmail.setError(null);
        }

        // Validate License Number
        if (binding.tvAadhar.getText().toString().isEmpty()) {
            binding.tvAadhar.setError("Please enter your aadhar number!");
            valid = false;
        } else {
            binding.tvAadhar.setError(null);
        }

        // Validate License Number
        if (binding.tvPanCard.getText().toString().isEmpty()) {
            binding.tvPanCard.setError("Please enter your pan number!");
            valid = false;
        } else {
            binding.tvPanCard.setError(null);
        }


        return valid;
    }

}