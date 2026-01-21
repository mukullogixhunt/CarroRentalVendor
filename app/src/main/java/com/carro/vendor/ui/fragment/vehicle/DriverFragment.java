package com.carro.vendor.ui.fragment.vehicle;

import static com.carro.vendor.utils.Constant.QUICKEKYC_API_KEY;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.google.gson.Gson;
import com.carro.vendor.BuildConfig;
import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiClientVerification;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.DriverListResponse;
import com.carro.vendor.api.response.DriverOtpResponse;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.AddDriverBasicDialogBinding;
import com.carro.vendor.databinding.AddDriverDocumentDialogBinding;
import com.carro.vendor.databinding.AddDriverOtpDialogBinding;
import com.carro.vendor.databinding.FragmentDriverBinding;
import com.carro.vendor.databinding.OtpVerifiedDialogBinding;
import com.carro.vendor.databinding.UploadDlDialogBinding;
import com.carro.vendor.databinding.UploadSuccessDialogBinding;
import com.carro.vendor.listener.DriverClickListener;
import com.carro.vendor.model.DLVerificationModel;
import com.carro.vendor.model.DriverListModel;
import com.carro.vendor.model.DriverOtpModel;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.ui.adapter.DriverListAdapter;
import com.carro.vendor.ui.common.BaseFragment;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.DateFormater;
import com.carro.vendor.utils.PreferenceUtils;
import com.carro.vendor.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Use the {@link DriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverFragment extends BaseFragment implements DriverClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;
    private final Calendar myCalendar = Calendar.getInstance();
    FragmentDriverBinding binding;
    AddDriverDocumentDialogBinding addDriverDocumentDialogBinding;
    AddDriverBasicDialogBinding addDriverBasicDialogBinding;
    Dialog dialog, dialog1, dialog2, dialog3, dialog4, dialog5;
    LoginModel loginModel = new LoginModel();
    DriverListAdapter driverListAdapter;
    DriverOtpModel driverOtpModel = new DriverOtpModel();
    String driver_id;
    String cexp_date;
    String cdriverName;
    String cdriverNumber;
    String cdriverLic;
    int type;
    DLVerificationModel.Data dlData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<DriverListModel> driverListModels = new ArrayList<>();
    private File addDriverLicenceImg = null;
    private File driverImage = null;
    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            String croppedImagePath = result.getUriFilePath(getContext(), true);

            if (type == 1) {
                driverImage = new File(croppedImagePath);
                addDriverDocumentDialogBinding.etDriverImage.setText(getImageName(croppedImagePath));
            } else {
                addDriverLicenceImg = new File(croppedImagePath);
                addDriverBasicDialogBinding.tvLicence.setText(getImageName(croppedImagePath));
            }


        }
    });
    private Uri imageUri;
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

    public DriverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverFragment newInstance(String param1, String param2) {
        DriverFragment fragment = new DriverFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDriverBinding.inflate(getLayoutInflater());
        getUserPreferences();
        return binding.getRoot();
    }

    private void getUserPreferences() {

        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, getContext());
        loginModel = new Gson().fromJson(userData, LoginModel.class);

        initialization();
    }

    private void initialization() {
        getDriverListApi();
        binding.cvAddDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriverBasicInfoDialog("add");
            }
        });
    }

    private void getDriverListApi() {

        binding.rvDriver.setVisibility(View.GONE);
        binding.lvNoData.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DriverListResponse> call = apiInterface.driver(loginModel.getmVendorId());
        call.enqueue(new Callback<DriverListResponse>() {
            @Override
            public void onResponse(Call<DriverListResponse> call, Response<DriverListResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {


                            binding.lvNoData.setVisibility(View.GONE);
                            binding.rvDriver.setVisibility(View.VISIBLE);

                            driverListModels.clear();
                            driverListModels.addAll(response.body().getData());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            binding.rvDriver.setLayoutManager(linearLayoutManager);
                            driverListAdapter = new DriverListAdapter(requireActivity(), driverListModels, DriverFragment.this);
                            binding.rvDriver.setAdapter(driverListAdapter);

                            driverListAdapter.notifyDataSetChanged();

                        } else {
                            binding.rvDriver.setVisibility(View.GONE);
                            binding.lvNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.rvDriver.setVisibility(View.GONE);
                        binding.lvNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    binding.rvDriver.setVisibility(View.GONE);
                    binding.lvNoData.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DriverListResponse> call, Throwable t) {
                binding.rvDriver.setVisibility(View.GONE);
                binding.lvNoData.setVisibility(View.VISIBLE);
                Log.e("Failure", t.toString());

            }
        });
    }

    private void addDriverBasicInfoDialog(String screenType) {
        addDriverBasicDialogBinding = AddDriverBasicDialogBinding.inflate(getLayoutInflater());
        dialog1 = new Dialog(getContext(), R.style.my_dialog);
        dialog1.setContentView(addDriverBasicDialogBinding.getRoot());
        dialog1.setCancelable(true);
        dialog1.create();
        dialog1.show();

        if (screenType.equals("update")) {

            addDriverBasicDialogBinding.etDMobile.setText(cdriverNumber);
            addDriverBasicDialogBinding.tvLicence.setText(cdriverLic);
        }

        addDriverBasicDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


        addDriverBasicDialogBinding.tvUploadLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadDlDialog();

            }
        });

        addDriverBasicDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    dialog1.dismiss();
                    String driverNumber = addDriverBasicDialogBinding.etDMobile.getText().toString();

                    if (screenType.equals("update")) {
                       // updDriverAPi(driverName, driverNumber,"driverLicExpDate");
                    } else {
                        addDriverAPi( );
                    }

                }
            }
        });
    }

    boolean validate() {
        boolean valid = true;
        if (addDriverBasicDialogBinding.etDMobile.getText().toString().isEmpty()) {
            addDriverBasicDialogBinding.etDMobile.setError("Please enter your mobile no..!");
            valid = false;
        } else {
            if (addDriverBasicDialogBinding.etDMobile.getText().toString().length() != 10) {
                addDriverBasicDialogBinding.etDMobile.setError("Please enter valid mobile no..!");
                valid = false;
            } else {
                addDriverBasicDialogBinding.etDMobile.setError(null);
            }
        }

        if (addDriverBasicDialogBinding.tvLicence.getText().toString().isEmpty()) {
            addDriverBasicDialogBinding.tvLicence.setError("Please Enter Driver Licence..!");
            valid = false;
        } else {
            addDriverBasicDialogBinding.tvLicence.setError(null);
        }


        return valid;
    }

    private void addDriverOtpDialog() {
        AddDriverOtpDialogBinding addDriverOtpDialogBinding;
        addDriverOtpDialogBinding = AddDriverOtpDialogBinding.inflate(getLayoutInflater());
        dialog2 = new Dialog(getContext(), R.style.my_dialog);
        dialog2.setContentView(addDriverOtpDialogBinding.getRoot());
        dialog2.setCancelable(true);
        dialog2.create();
        dialog2.show();
        addDriverOtpDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        addDriverOtpDialogBinding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                verifyDriverOtpAPi(driver_id, addDriverOtpDialogBinding.otpview.getOTP().toString());


            }
        });
    }

    private void addDriverDocumentDialog() {

        addDriverDocumentDialogBinding = AddDriverDocumentDialogBinding.inflate(getLayoutInflater());
        dialog3 = new Dialog(getContext(), R.style.my_dialog);
        dialog3.setContentView(addDriverDocumentDialogBinding.getRoot());
        dialog3.setCancelable(true);
        dialog3.create();
        dialog3.show();
        addDriverDocumentDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });

        addDriverDocumentDialogBinding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(addDriverDocumentDialogBinding.etDate);
            }
        });
        addDriverDocumentDialogBinding.etDriverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                imagePickerDialog();
            }
        });
        addDriverDocumentDialogBinding.etLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                imagePickerDialog();
            }
        });

        addDriverDocumentDialogBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
                uploadDriverDocAPi();


            }
        });
    }

    private void otpVerifiedDialog() {
        OtpVerifiedDialogBinding otpVerifiedDialogBinding;
        otpVerifiedDialogBinding = OtpVerifiedDialogBinding.inflate(getLayoutInflater());
        dialog4 = new Dialog(getContext(), R.style.my_dialog);
        dialog4.setContentView(otpVerifiedDialogBinding.getRoot());
        dialog4.setCancelable(true);
        dialog4.create();
        dialog4.show();
        otpVerifiedDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            }
        });

        otpVerifiedDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
                addDriverDocumentDialog();

            }
        });
    }

    private void uploadSuccessDialog() {
        UploadSuccessDialogBinding uploadSuccessDialogBinding;
        uploadSuccessDialogBinding = UploadSuccessDialogBinding.inflate(getLayoutInflater());
        dialog5 = new Dialog(getContext(), R.style.my_dialog);
        dialog5.setContentView(uploadSuccessDialogBinding.getRoot());
        dialog5.setCancelable(true);
        dialog5.create();
        dialog5.show();
        uploadSuccessDialogBinding.imgCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });


        uploadSuccessDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
                getDriverListApi();


            }
        });
    }

    private void openDatePickerDialog(EditText editText) {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editText.setText(DateFormater.getDate(myCalendar.getTimeInMillis(), Constant.ddMMyyyy));

            }

        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void addDriverAPi( ) {



        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.insert_driver(loginModel.getmVendorId(),dlData.getName(),addDriverBasicDialogBinding.etDMobile.getText().toString(),dlData.getLicenseNumber(),dlData.getName(),
                dlData.getTemporaryAddress(),dlData.getTemporaryAddress(),dlData.getPermanentAddress(),dlData.getPermanentZip(),
                dlData.getState(),dlData.getOlaCode(),dlData.getOlaName(),dlData.getGender(),dlData.getFatherOrHusbandName(),dlData.getDob(),dlData.getBloodGroup(),dlData.getVehicleClasses(),
                dlData.getCurrentStatus(),dlData.getDoi(),dlData.getDoe());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            getDriverListApi();

                            // sendDriverOtpAPi(d_mobile);
                            //  addDriverOtpDialog();

                        } else {

                            Toast.makeText(getContext(), "Driver Already Exist!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Driver Already Exist!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updDriverAPi(String d_name, String d_mobile, String driverLicEXpDate) {

        String expDate = Utils.changeDateFormat(Constant.ddMMyyyy, Constant.yyyyMMdd, driverLicEXpDate);
        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), loginModel.getmVendorId());
        RequestBody rbDriverId = RequestBody.create(MediaType.parse("text/plain"), driver_id);
        RequestBody rbDriverName = RequestBody.create(MediaType.parse("text/plain"), d_name);
        RequestBody rbDriverMobile = RequestBody.create(MediaType.parse("text/plain"), d_mobile);
        RequestBody rbDriverLicenceExpDate = RequestBody.create(MediaType.parse("text/plain"), expDate);

        MultipartBody.Part driverLicenceImagePart = null;
        if (addDriverLicenceImg != null) {
            driverLicenceImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.LIC_IMAGE, addDriverLicenceImg.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), addDriverLicenceImg));
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.upd_driver(rbDriverId, rbDriverName, rbDriverMobile, rbDriverLicenceExpDate, rbUserId, driverLicenceImagePart);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            getDriverListApi();

                            // sendDriverOtpAPi(d_mobile);
                            //  addDriverOtpDialog();

                        } else {

                            Toast.makeText(getContext(), "Driver Already Exist!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Driver Already Exist!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendDriverOtpAPi(String mobile) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DriverOtpResponse> call = apiInterface.send_driver_otp(mobile);
        call.enqueue(new Callback<DriverOtpResponse>() {
            @Override
            public void onResponse(Call<DriverOtpResponse> call, Response<DriverOtpResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            driverOtpModel = response.body().getUser();
                            driver_id = driverOtpModel.getmDriverId();
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG" + response.body().getMessage(), "onResponse: ");
                        }
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG" + response.message(), "onResponse: ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG" + response.message(), "onResponse: ");
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverOtpResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void verifyDriverOtpAPi(String driverId, String otp) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.verify_driver_otp(driverId, otp);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {


                            otpVerifiedDialog();


                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDriverDocAPi() {

        String date = "";
//        String date= DateFormater.changeDateFormat(Constant.ddMMyyyy,Constant.yyyyMMdd,exp_date);
        RequestBody rbDriverId = RequestBody.create(MediaType.parse("text/plain"), driver_id);
        RequestBody rbExpDate = RequestBody.create(MediaType.parse("text/plain"), date);


        MultipartBody.Part driverImagePart = null;
        if (driverImage != null) {
            driverImagePart = MultipartBody.Part.createFormData(Constant.ApiKey.DRIVER_IMAGE, driverImage.getPath(), RequestBody.create(MediaType.parse("multipart/form-data"), driverImage));
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.update_driver(rbDriverId, driverImagePart, rbExpDate);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            uploadSuccessDialog();

                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
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
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
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

    private String getImageName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserPreferences();
        getDriverListApi();
    }

    @Override
    public void onDriverClick(DriverListModel driverListModel) {
        driver_id = driverListModel.getmDriverId();
        cdriverName = driverListModel.getmDriverName();
        cdriverNumber = driverListModel.getmDriverMobile();
        cexp_date = driverListModel.getmDriverDrivelicExpdate();
        cdriverLic = driverListModel.getmDriverDrivelic();
        addDriverBasicInfoDialog("update");
    }

    private void uploadDlDialog() {
        UploadDlDialogBinding uploadDlDialogBinding = UploadDlDialogBinding.inflate(getLayoutInflater());
        Dialog dialogDl = new Dialog(getActivity(), R.style.my_dialog);
        dialogDl.setContentView(uploadDlDialogBinding.getRoot());
        dialogDl.setCancelable(true);
        dialogDl.create();
        dialogDl.show();

        uploadDlDialogBinding.etDOB.setOnClickListener(v -> {

            openDatePickerDialog(uploadDlDialogBinding.etDOB);

        });

        uploadDlDialogBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dlNo = uploadDlDialogBinding.etDlNumber.getText().toString();

                callDlGetDataApi(uploadDlDialogBinding.etDlNumber.getText().toString(), uploadDlDialogBinding.etDOB.getText().toString(), addDriverBasicDialogBinding.tvLicence);
                dialogDl.dismiss();


            }
        });

    }

    private void callDlGetDataApi(String dlNo, String dob, TextView dlno) {

        showLoader();

        JSONObject json = new JSONObject();

        try {
            json.put("key", QUICKEKYC_API_KEY);
            json.put("id_number", dlNo);
            json.put("dob", dob);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                okhttp3.MediaType.parse("application/json")
        );
        ApiInterface apiService = ApiClientVerification.getClient().create(ApiInterface.class);
        Call<DLVerificationModel> call = apiService.driving_license(body);
        call.enqueue(new Callback<DLVerificationModel>() {
            @Override
            public void onResponse(Call<DLVerificationModel> call, Response<DLVerificationModel> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getStatusCode() == 200) {

                            dlData = response.body().getData();
                            dlno.setText(dlData.getLicenseNumber());

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
            public void onFailure(Call<DLVerificationModel> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

    private void openDatePickerDialog(TextView textView) {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                textView.setText(DateFormater.getDate(myCalendar.getTimeInMillis(), Constant.yyyyMMdd));
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


}