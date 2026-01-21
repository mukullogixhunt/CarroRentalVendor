package com.carro.vendor.ui.fragment.bottom;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import com.carro.vendor.BuildConfig;
import com.carro.vendor.R;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.EditBankDetailDialogBinding;
import com.carro.vendor.databinding.EditBusinessDetailsDialogBinding;
import com.carro.vendor.databinding.EditVendorDocDialogBinding;
import com.carro.vendor.databinding.FragmentProfileBinding;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.UserDetailResponse;

import com.carro.vendor.model.LoginModel;
import com.carro.vendor.model.UserDetailModel;
import com.carro.vendor.ui.activity.EditProfileActivity;
import com.carro.vendor.ui.common.BaseFragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    FragmentProfileBinding binding;
    LoginModel loginModel = new LoginModel();
    private List<UserDetailModel> userDetailModel = new ArrayList<>();
    Dialog dialog,dialogDoc,dialogBank,dialogBusiness;
    String user_id;
    int type;
    EditVendorDocDialogBinding editVendorDocDialogBinding;
    EditBankDetailDialogBinding editBankDetailDialogBinding;
    EditBusinessDetailsDialogBinding editBusinessDetailsDialogBinding;
    
    private Uri imageUri;
    private File adharFrontImg = null;
    private File adharBackImg = null;
    private File panImg = null;
    private File licFrontImg = null;
    private File licBackImg = null;
    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(getLayoutInflater());
        getUserPreferences();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);
        user_id=loginModel.getmVendorId();

        initialization();
        getAppVersion();
    }


    private void initialization(){
        userDetailsApi();
        binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
     /*   binding.tvEditDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDocDialog();
            }
        });*/
        binding.tvEditBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBankDialog();
            }
        });
        binding.tvEditBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBusinessDialog();
            }
        });


    }

    private void setData(){
        binding.tvName.setText(userDetailModel.get(0).getmVendorName());
        binding.tvEmail.setText(userDetailModel.get(0).getmVendorEmail());
        binding.tvPhoneNumber.setText(userDetailModel.get(0).getmVendorMobile());
        binding.tvAadharNumber.setText(userDetailModel.get(0).getmVendorAdharNo());
        binding.tvPanNumber.setText(userDetailModel.get(0).getmVendorPanNo());
        binding.tvAltPhoneNumber.setText(userDetailModel.get(0).getmVendorAltMobile());
        binding.tvBranch.setText(userDetailModel.get(0).getmBranchTitle());
//        binding.tvPincode.setText(userDetailModel.get(0).getmVendorPincode());
//        binding.tvState.setText(userDetailModel.get(0).getmVendorState());
//        binding.tvCity.setText(userDetailModel.get(0).getmVendorCity());
        binding.tvAccountNumber.setText(userDetailModel.get(0).getmVendorBankAccNo());
        binding.tvAccountHolderName.setText(userDetailModel.get(0).getmVendorBankHolderName());
        binding.tvBankName.setText(userDetailModel.get(0).getmVendorBankName());
        binding.tvIfscCode.setText(userDetailModel.get(0).getmVendorBankIfscCode());
//        binding.tvAddress.setText(userDetailModel.get(0).getmVendorAddress());
//        binding.tvAdharFront.setText(userDetailModel.get(0).getmVendorAdharFront());
//        binding.tvAdharBack.setText(userDetailModel.get(0).getmVendorAdharBack());
//        binding.tvPanImg.setText(userDetailModel.get(0).getmVendorPanImg());
        binding.tvBankUpi.setText(userDetailModel.get(0).getmVendorBankUpi());
        binding.tvPhonePay.setText(userDetailModel.get(0).getmVendorBankPp());
        binding.tvGooglePay.setText(userDetailModel.get(0).getmVendorBankPp());

        binding.tvBusinessName.setText(userDetailModel.get(0).getmVendorBusinessName());
        binding.tvBusinessNumber.setText(userDetailModel.get(0).getmVendorBusinessPhone());
        binding.tvBusinessEmail.setText(userDetailModel.get(0).getmVendorBusinessEmail());
        binding.tvBusinessLicenceNo.setText(userDetailModel.get(0).getmVendorBusinessLicNo());
        binding.tvBusinessAddress.setText(userDetailModel.get(0).getmVendorBusinessAddress());
        binding.tvLicenceFrontImage.setText(userDetailModel.get(0).getmVendorBusinessLicImgFront());
        binding.tvLicenceBackImage.setText(userDetailModel.get(0).getmVendorBusinessLicImgBack());

        Glide.with(getContext())
                .load(ImagePathDecider.getUserImagePath() + loginModel.getmVendorImg())
                .error(R.drawable.img_no_profile)
                .into(binding.ivUser);


    }

    private void userDetailsApi() {
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserDetailResponse> call = apiInterface.user_details(loginModel.getmVendorId());
        call.enqueue(new Callback<UserDetailResponse>() {
            @Override
            public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            userDetailModel=response.body().getData();
                            PreferenceUtils.setString(Constant.PreferenceConstant.USER_DATA,
                                    new Gson().toJson(response.body().getData().get(0)), getContext());


                            setData();

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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserDetailResponse> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserPreferences();
        userDetailsApi();
    }

    private void updateDocDialog() {

        editVendorDocDialogBinding = EditVendorDocDialogBinding.inflate(getLayoutInflater());

        dialogDoc = new BottomSheetDialog(getContext());
        dialogDoc.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogDoc.setContentView(editVendorDocDialogBinding.getRoot());
        dialogDoc.show();

        editVendorDocDialogBinding.etAadharNumber.setText(userDetailModel.get(0).getmVendorAdharNo());
        editVendorDocDialogBinding.etPanNo.setText(userDetailModel.get(0).getmVendorPanNo());
        editVendorDocDialogBinding.tvAadhaarFront.setText(userDetailModel.get(0).getmVendorAdharFront());
        editVendorDocDialogBinding.tvAadhaarBack.setText(userDetailModel.get(0).getmVendorAdharBack());
        editVendorDocDialogBinding.tvPanCard.setText(userDetailModel.get(0).getmVendorPanImg());

        editVendorDocDialogBinding.tvUploadAadhaarFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                imagePickerDialog();
            }
        });

        editVendorDocDialogBinding.tvUploadAadhaarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                imagePickerDialog();
            }
        });

        editVendorDocDialogBinding.tvChoosePan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                imagePickerDialog();
            }
        });

        editVendorDocDialogBinding.btnUpdateDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adhar=editVendorDocDialogBinding.etAadharNumber.getText().toString();
                String pan=editVendorDocDialogBinding.etPanNo.getText().toString();
                updateDocsApi(adhar,pan);
                dialogDoc.dismiss();
            }
        });
    }
    private void updateBusinessDialog() {

        editBusinessDetailsDialogBinding = EditBusinessDetailsDialogBinding.inflate(getLayoutInflater());

        dialogBusiness = new BottomSheetDialog(getContext());
        dialogBusiness.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogBusiness.setContentView(editBusinessDetailsDialogBinding.getRoot());
        dialogBusiness.show();

        editBusinessDetailsDialogBinding.etBusinessName.setText(userDetailModel.get(0).getmVendorBusinessName());
        editBusinessDetailsDialogBinding.etBusinessNumber.setText(userDetailModel.get(0).getmVendorBusinessPhone());
        editBusinessDetailsDialogBinding.etBusinessEmail.setText(userDetailModel.get(0).getmVendorBusinessEmail());
        editBusinessDetailsDialogBinding.etBusinessLicenceNo.setText(userDetailModel.get(0).getmVendorBusinessLicNo());
        editBusinessDetailsDialogBinding.etBusinessAddress.setText(userDetailModel.get(0).getmVendorBusinessAddress());
        editBusinessDetailsDialogBinding.tvLicFront.setText(userDetailModel.get(0).getmVendorBusinessLicImgFront());
        editBusinessDetailsDialogBinding.tvLicBack.setText(userDetailModel.get(0).getmVendorBusinessLicImgBack());

        editBusinessDetailsDialogBinding.tvChooseLicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 4;
                imagePickerDialog();
            }
        });

        editBusinessDetailsDialogBinding.tvChooseLicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 5;
                imagePickerDialog();
            }
        });

        editBusinessDetailsDialogBinding.btnUpdateDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editBusinessDetailsDialogBinding.etBusinessName.getText().toString();
                String number=editBusinessDetailsDialogBinding.etBusinessNumber.getText().toString();
                String email=editBusinessDetailsDialogBinding.etBusinessEmail.getText().toString();
                String licNo=editBusinessDetailsDialogBinding.etBusinessLicenceNo.getText().toString();
                String address=editBusinessDetailsDialogBinding.etBusinessAddress.getText().toString();
                updateBusinessApi(name,address,email,number,licNo);
                dialogBusiness.dismiss();
            }
        });
    }

    private void updateBankDialog() {

        editBankDetailDialogBinding = EditBankDetailDialogBinding.inflate(getLayoutInflater());

        dialogBank = new BottomSheetDialog(getContext());
        dialogBank.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogBank.setContentView(editBankDetailDialogBinding.getRoot());
        dialogBank.show();

        editBankDetailDialogBinding.etAccountNo.setText(userDetailModel.get(0).getmVendorBankAccNo());
        editBankDetailDialogBinding.etAcHolderName.setText(userDetailModel.get(0).getmVendorBankHolderName());
        editBankDetailDialogBinding.etBankName.setText(userDetailModel.get(0).getmVendorBankName());
        editBankDetailDialogBinding.etIfsc.setText(userDetailModel.get(0).getmVendorBankIfscCode());
        editBankDetailDialogBinding.etBankUpi.setText(userDetailModel.get(0).getmVendorBankUpi());
        editBankDetailDialogBinding.etGooglePay.setText(userDetailModel.get(0).getmVendorBankGp());
        editBankDetailDialogBinding.etPhonePay.setText(userDetailModel.get(0).getmVendorBankPp());

        editBankDetailDialogBinding.btnUpdateBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBank.dismiss();
                String ac_no=editBankDetailDialogBinding.etAccountNo.getText().toString();
                String holder_name=editBankDetailDialogBinding.etAcHolderName.getText().toString();
                String bank_name=editBankDetailDialogBinding.etBankName.getText().toString();
                String ifsc=editBankDetailDialogBinding.etIfsc.getText().toString();
                String upi=editBankDetailDialogBinding.etBankUpi.getText().toString();
                String pp=editBankDetailDialogBinding.etPhonePay.getText().toString();
                String gp=editBankDetailDialogBinding.etGooglePay.getText().toString();
                updateBankDetailsApi(holder_name,bank_name,ac_no,ifsc,upi,pp,gp);
            }
        });
    }

    private void updateBankDetailsApi(String ac_holder_name,String bank_name,String ac_no,String ifsc_code,String upi,String pp,String gp) {
        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_bank_details(loginModel.getmVendorId(),ac_holder_name,bank_name,ac_no,ifsc_code,upi,pp,gp);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            Toast.makeText(getContext(), "Bank Details Updated Successfully..", Toast.LENGTH_SHORT).show();
                            userDetailsApi();

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

    private void updateDocsApi(String adhar, String pan) {
        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody rbAdharNo = RequestBody.create(MediaType.parse("text/plain"), adhar);
        RequestBody rbPanNo = RequestBody.create(MediaType.parse("text/plain"), pan);

        MultipartBody.Part adharFrontImagePart = null;
        if (adharFrontImg != null) {
            adharFrontImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.ADHAR_FRONT, adharFrontImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), adharFrontImg));
        }
        MultipartBody.Part adharBackImagePart = null;
        if (adharBackImg != null) {
            adharBackImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.ADHAR_BACK, adharBackImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), adharBackImg));
        }
        MultipartBody.Part panImagePart = null;
        if (panImg != null) {
            panImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.PAN_IMG, panImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), panImg));
        }

        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_doc_details(rbUserId,rbAdharNo,rbPanNo,adharFrontImagePart, adharBackImagePart, panImagePart);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            Toast.makeText(getContext(), "Documents Updated Successfully..", Toast.LENGTH_SHORT).show();
                            userDetailsApi();

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

    private void updateBusinessApi(String name, String address,String email,String number, String licNo) {
        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody rbName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody rbAddress = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody rbEmail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody rbNumber = RequestBody.create(MediaType.parse("text/plain"), number);
        RequestBody rbLicNo = RequestBody.create(MediaType.parse("text/plain"), licNo);

        MultipartBody.Part licFrontImagePart = null;
        if (licFrontImg != null) {
            licFrontImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.USER_BUSINESS_LIC_FRONT, licFrontImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), licFrontImg));
        }
        MultipartBody.Part licBackImagePart = null;
        if (licBackImg != null) {
            licBackImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.USER_BUSINESS_LIC_BACK, licBackImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), licBackImg));
        }


        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_business_details(rbUserId,rbName,rbAddress,rbEmail,rbNumber,rbLicNo, licFrontImagePart,licBackImagePart);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {
                            Toast.makeText(getContext(), "Details Updated Successfully..", Toast.LENGTH_SHORT).show();
                            userDetailsApi();

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


    private void imagePickerDialog() {
        dialog = new Dialog(getContext(), R.style.my_dialog);
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

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
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
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
                openGallery();
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
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
            File file = File.createTempFile("IMG_" + timeStamp, ".jpg", getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            imageUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
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
            String croppedImagePath = result.getUriFilePath(getContext(), true);

            switch (type) {
                case 1:
                    adharFrontImg = new File(croppedImagePath);
                    editVendorDocDialogBinding.tvAadhaarFront.setText(getImageName(croppedImagePath));
                    break;
                case 2:
                    adharBackImg = new File(croppedImagePath);
                    editVendorDocDialogBinding.tvAadhaarBack.setText(getImageName(croppedImagePath));
                    break;
                case 3:
                    panImg = new File(croppedImagePath);
                    editVendorDocDialogBinding.tvPanCard.setText(getImageName(croppedImagePath));
                    break;
                case 4:
                    licFrontImg = new File(croppedImagePath);
                    editBusinessDetailsDialogBinding.tvLicFront.setText(getImageName(croppedImagePath));
                    break;
                case 5:
                    licBackImg = new File(croppedImagePath);
                    editBusinessDetailsDialogBinding.tvLicBack.setText(getImageName(croppedImagePath));
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


    private void getAppVersion() {
        PackageManager packageManager = requireActivity().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(requireActivity().getPackageName(), 0);

            // Get version name and version code
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            // Display version name and version code in TextView
            String versionInfo = "Version Name: " + versionName + "\nVersion Code: " + versionCode;

            binding.tvVersion.setText(versionInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}