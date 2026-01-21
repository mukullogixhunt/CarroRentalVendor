package com.carro.vendor.ui.fragment.vehicle;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.gson.Gson;
import com.carro.vendor.BuildConfig;
import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.CarListResponse;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.FragmentCarDetailBinding;
import com.carro.vendor.databinding.UploadCarDocDialogBinding;
import com.carro.vendor.listener.CarItemClickListener;
import com.carro.vendor.model.CarListModel;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.ui.activity.AddCarActivity;
import com.carro.vendor.ui.adapter.CarListAdapter;
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
 * Use the {@link CarDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarDetailFragment extends BaseFragment implements CarItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarDetailFragment newInstance(String param1, String param2) {
        CarDetailFragment fragment = new CarDetailFragment();
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

    FragmentCarDetailBinding binding;
    UploadCarDocDialogBinding uploadCarDocDialogBinding;

    LoginModel loginModel = new LoginModel();
    private List<CarListModel> carListModels = new ArrayList<>();
    CarListAdapter carListAdapter;
    Dialog dialog, dialog1;
    int type;
    String carId = "";
    String reg = "";
    String iss= "";
    String permit = "";
    String ownerPic = "";
    String carImg = "";
    private Uri imageUri;
    private File uploadImg = null;
    private File regCertiImg = null;
    private File insuranceImg = null;
    private File permitImg = null;
    private File ownerCarImg = null;
    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarDetailBinding.inflate(getLayoutInflater());
        getUserPreferences();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);

        initialization();
    }

    private void initialization() {
        getCarListApi();

        binding.cvAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCarActivity.class);
                intent.putExtra(Constant.BundleExtras.CAR_TYPE, "add");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserPreferences();
        getCarListApi();
    }

    private void getCarListApi() {

        binding.rvCarDetail.setVisibility(View.GONE);
        binding.lvNoData.setVisibility(View.VISIBLE);

        showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CarListResponse> call = apiInterface.car(loginModel.getmVendorId());
        call.enqueue(new Callback<CarListResponse>() {
            @Override
            public void onResponse(Call<CarListResponse> call, Response<CarListResponse> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            binding.rvCarDetail.setVisibility(View.VISIBLE);
                            binding.lvNoData.setVisibility(View.GONE);

                            carListModels.clear();
                            carListModels.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            binding.rvCarDetail.setLayoutManager(linearLayoutManager);
                            carListAdapter = new CarListAdapter(requireActivity(), carListModels, CarDetailFragment.this);
                            binding.rvCarDetail.setAdapter(carListAdapter);

                            carListAdapter.notifyDataSetChanged();

                        } else {
                            hideLoader();
                            showError(response.message());
                            binding.rvCarDetail.setVisibility(View.GONE);
                            binding.lvNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        hideLoader();
                        showError(response.message());
                        binding.rvCarDetail.setVisibility(View.GONE);
                        binding.lvNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    binding.rvCarDetail.setVisibility(View.GONE);
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    hideLoader();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CarListResponse> call, Throwable t) {
                hideLoader();
                binding.rvCarDetail.setVisibility(View.GONE);
                binding.lvNoData.setVisibility(View.VISIBLE);
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

    private void uploadCarDocDialog() {

        uploadCarDocDialogBinding = UploadCarDocDialogBinding.inflate(getLayoutInflater());
        dialog1 = new Dialog(getContext(), R.style.my_dialog);
        dialog1.setContentView(uploadCarDocDialogBinding.getRoot());
        dialog1.setCancelable(true);
        dialog1.create();
        dialog1.show();

        if (reg != null && !reg.isEmpty()) {
            uploadCarDocDialogBinding.tvRegCertificate.setText(reg);
        }

        if (iss != null && !iss.isEmpty()) {
            uploadCarDocDialogBinding.tvInsurance.setText(iss);
        }

        if (ownerPic != null && !ownerPic.isEmpty()) {
            uploadCarDocDialogBinding.tvOwnerCar.setText(ownerPic);
        }

        if (permit != null && !permit.isEmpty()) {
            uploadCarDocDialogBinding.tvPermit.setText(permit);
        }

        Glide.with(getContext())
                .load(ImagePathDecider.getCarImagePath() +carImg)
                .error(R.drawable.image_car)
                .into(uploadCarDocDialogBinding.ivCar);


        uploadCarDocDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                getCarListApi();
            }
        });
        uploadCarDocDialogBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

                updateCarDocumentsAPi();

//                updateRegCertificateAPi();
//                updatePermitAPi();
//                updateOwnerCarAPi();
//                updateInsuranceAPi();
//                getCarListApi();
//                showToastDialog();

            }
        });

        uploadCarDocDialogBinding.tvUploadRegCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                imagePickerDialog();
            }
        });
        uploadCarDocDialogBinding.tvUploadInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                imagePickerDialog();
            }
        });
        uploadCarDocDialogBinding.tvUploadPermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                imagePickerDialog();
            }
        });
        uploadCarDocDialogBinding.tvUploadOwnerCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 4;
                imagePickerDialog();
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            openCamera();
        } else {
            openCamera();
        }
    }

    /// ////////////////////// check gallery permission///////////
    private void checkGalleryPermission() {
        if (dialog != null) {
            dialog.dismiss();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity()
                        , new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
                openGallery();
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
                openGallery();
            } else {
                openGallery();
            }
        }
    }

    /// //////////////// for open camera /////////////////////
    private void openCamera() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat(Constant.yyyyMMdd_HHmmss, Locale.getDefault()).format(new java.util.Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        try {
            File file = File.createTempFile("IMG_" + timeStamp, ".jpg", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
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

            uploadImg = new File(croppedImagePath);
            switch (type) {
                case 1:
                    uploadCarDocDialogBinding.tvRegCertificate.setText(getImageName(croppedImagePath));
                    regCertiImg = uploadImg;
                    break;
                case 2:
                    uploadCarDocDialogBinding.tvInsurance.setText(getImageName(croppedImagePath));
                    insuranceImg = uploadImg;
                    break;
                case 3:
                    uploadCarDocDialogBinding.tvPermit.setText(getImageName(croppedImagePath));
                    permitImg = uploadImg;
                    break;
                case 4:
                    uploadCarDocDialogBinding.tvOwnerCar.setText(getImageName(croppedImagePath));
                    ownerCarImg = uploadImg;
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

    private void updateRegCertificateAPi() {

        RequestBody rbCarId = RequestBody.create(MediaType.parse("text/plain"), carId);

        MultipartBody.Part regImagePart = null;
        if (regCertiImg != null) {
            regImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.REG_CERTI, regCertiImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), regCertiImg));


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiInterface.upd_reg_certi(rbCarId, regImagePart);
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
                    showError("something went wrong");


                }
            });
        } else {
//            Toast.makeText(getContext(), "Choose Registration certificate", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateInsuranceAPi() {

        RequestBody rbCarId = RequestBody.create(MediaType.parse("text/plain"), carId);

        MultipartBody.Part insuranceImagePart = null;
        if (insuranceImg != null) {
            insuranceImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.ISS_CERTI, insuranceImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), insuranceImg));


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiInterface.upd_iss_certi(rbCarId, insuranceImagePart);
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
                    showError("something went wrong");


                }
            });
        } else {
//            Toast.makeText(getContext(), "Choose Insurance Documents", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePermitAPi() {

        RequestBody rbCarId = RequestBody.create(MediaType.parse("text/plain"), carId);

        MultipartBody.Part permitImagePart = null;
        if (permitImg != null) {
            permitImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.PERMIT, permitImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), permitImg));

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiInterface.upd_car_permit(rbCarId, permitImagePart);
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
                    showError("something went wrong");


                }
            });
        } else {
//            Toast.makeText(getContext(), "Choose Permit certificate", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateOwnerCarAPi() {

        RequestBody rbCarId = RequestBody.create(MediaType.parse("text/plain"), carId);

        MultipartBody.Part ownerCarImagePart = null;
        if (ownerCarImg != null) {
            ownerCarImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.OWNERCAR_IMG, ownerCarImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), ownerCarImg));


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiInterface.upd_ownercar(rbCarId, ownerCarImagePart);
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
                    showError("something went wrong");


                }
            });
        } else {
//            Toast.makeText(getContext(), "Choose Car Photo With Owner", Toast.LENGTH_SHORT).show();
        }
    }

    private void showToastDialog() {
        new AlertDialog.Builder(getContext())
                //.setTitle("Download Complete")
                .setMessage("Document Uploaded Successfully..!")
                .setNegativeButton("Okay", null)
                .show();
    }

    @Override
    public void onCarClick(CarListModel carListModel, String type) {
        carId = carListModel.getmCarId();
        reg = carListModel.getmCarRegCerti();
        iss = carListModel.getmCarIssCerti();
        permit = carListModel.getmCarPermit();
        ownerPic = carListModel.getmCarOwnercarImg();
        carImg = carListModel.getmCarImg();
        if (type.equals("edit")) {
            Intent intent = new Intent(getContext(), AddCarActivity.class);
            intent.putExtra(Constant.BundleExtras.CAR_TYPE, "update");
            intent.putExtra(Constant.BundleExtras.CAR_DETAIL, new Gson().toJson(carListModel));
            startActivity(intent);
        } else {
            uploadCarDocDialog();
        }


    }

    private void updateCarDocumentsAPi() {

        RequestBody rbCarId = RequestBody.create(MediaType.parse("text/plain"), carId);

        MultipartBody.Part regImagePart = null;
        if (regCertiImg != null) {
            regImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.REG_CERTI, regCertiImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), regCertiImg));
        }

        MultipartBody.Part permitImagePart = null;
        if (permitImg != null) {
            permitImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.PERMIT, permitImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), permitImg));
        }
        MultipartBody.Part ownerCarImagePart = null;
        if (ownerCarImg != null) {
            ownerCarImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.OWNERCAR_IMG, ownerCarImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), ownerCarImg));
        }

        MultipartBody.Part insuranceImagePart = null;
        if (insuranceImg != null) {
            insuranceImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.ISS_CERTI, insuranceImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), insuranceImg));

        }


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.uploadCarDocuments(rbCarId, regImagePart, insuranceImagePart, permitImagePart, ownerCarImagePart);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            showToastDialog();
                            getCarListApi();
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
                showError("something went wrong");


            }
        });

    }
}

