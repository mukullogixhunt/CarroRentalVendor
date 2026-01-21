package com.carro.vendor.ui.activity;

import static com.carro.vendor.utils.Constant.QUICKEKYC_API_KEY;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.carro.vendor.R;
import com.carro.vendor.api.ApiClient;
import com.carro.vendor.api.ApiClientVerification;
import com.carro.vendor.api.ApiInterface;
import com.carro.vendor.api.response.CarTypeResponse;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.databinding.ActivityAddCarBinding;
import com.carro.vendor.model.CarListModel;
import com.carro.vendor.model.CarTypeModel;
import com.carro.vendor.model.LoginModel;
import com.carro.vendor.model.RcDataModel;
import com.carro.vendor.ui.common.BaseActivity;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCarActivity extends BaseActivity {

    private static final int PERMISSION_CAMERA = 221;
    private static final int PERMISSION_WRITE_EXTERNAL = 222;
    private static final int PERMISSION_READ_MEDIA_IMAGES = 223;
    ActivityAddCarBinding binding;
    List<CarTypeModel> carTypeModelList = new ArrayList<>();
    String carType = "";
    String carTypeName = "";
//    String carFuel = "";
    String userId = "";
    String acType = "";
    String driverTye = "";
    String formType = "";
    String carId = "";
    CarListModel carListModel;
    private List<String> driveList = new ArrayList<>();
    private List<String> fuelList = new ArrayList<>();
    private List<String> acTypeList = new ArrayList<>();
    private Dialog dialog;
    private Uri imageUri;
    private File uploadImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getPreference();

    }

    private void getPreference() {
        userId = PreferenceUtils.getString(Constant.PreferenceConstant.USER_ID, AddCarActivity.this);


        if (getIntent().hasExtra(Constant.BundleExtras.CAR_DETAIL)) {
            String carJson = getIntent().getStringExtra(Constant.BundleExtras.CAR_DETAIL);
            carListModel = new Gson().fromJson(carJson, CarListModel.class);
        }


        formType = getIntent().getStringExtra(Constant.BundleExtras.CAR_TYPE);
        initialization();
    }

    private void initialization() {


        //initiateDriveType();
        initiateCarType();
//        initiateFuel();
       // initiateAcType();

        if (formType.equals("update")) {
            setData();
        }
        String userData = PreferenceUtils.getString(Constant.PreferenceConstant.USER_DATA, this);
        LoginModel loginModel = new Gson().fromJson(userData, LoginModel.class);
        setUpToolBar(binding.toolbar, this, loginModel.getmVendorImg());

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    callDlGetDataApi();
                }
            }
        });

      /*  binding.tvUploadCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerDialog();
            }
        });
*/

    }

    private void setData() {
        carId = carListModel.getmCarId();
//        binding.etCarName.setText(carListModel.getmCarName());
        binding.etCarNumber.setText(carListModel.getmCarNumber());
//        binding.tvCarImg.setText(carListModel.getmCarImg());
//        binding.etSeats.setText(carListModel.getmCarSeat());
//        binding.etBags.setText(carListModel.getmCarLuggage());
    }

    /*private void initiateFuel() {
        fuelList.clear();
        fuelList.add("Select Fuel");
        fuelList.add("Petrol");
        fuelList.add("Diesel");
        ArrayAdapter genderAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, fuelList);
        binding.spFuelType.setAdapter(genderAdapter);

        if (carListModel != null && carListModel.getmCarFuel() != null && !carListModel.getmCarFuel().isEmpty()) {
            if (carListModel.getmCarFuel().equalsIgnoreCase("Petrol")) {
                binding.spFuelType.setSelection(1);
            } else {
                binding.spFuelType.setSelection(2);
            }
        }


        binding.spFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carFuel = binding.spFuelType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

    private void initiateCarType() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CarTypeResponse> call = apiInterface.carType();
        call.enqueue(new Callback<CarTypeResponse>() {
            @Override
            public void onResponse(Call<CarTypeResponse> call, Response<CarTypeResponse> response) {

                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            carTypeModelList.clear();
                            carTypeModelList.add(new CarTypeModel("Select Car Type"));
                            carTypeModelList.addAll(response.body().getData());

                            ArrayAdapter<CarTypeModel> itemAdapter = new ArrayAdapter<>(AddCarActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, carTypeModelList);
                            itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            binding.spCarType.setAdapter(itemAdapter);

                            binding.spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    carType = carTypeModelList.get(position).getmCtypeId();
                                    carTypeName = carTypeModelList.get(position).getmCtypeTitle();
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
            public void onFailure(Call<CarTypeResponse> call, Throwable t) {

                Log.e("Failure", t.toString());

            }
        });
    }

   /* private void initiateDriveType() {
        driveList.clear();
        driveList.add("Select Drive Type");
        driveList.add("Manual");
        driveList.add("Automatic");
//        ArrayAdapter driverAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, driveList);
//        binding.spDriverType.setAdapter(driverAdapter);


        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(AddCarActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, driveList);
        itemAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        binding.spDriverType.setAdapter(itemAdapter);


        if (carListModel != null && carListModel.getmCarDrivetype() != null && !carListModel.getmCarDrivetype().isEmpty()) {
            if (carListModel.getmCarDrivetype().equalsIgnoreCase("Manual")) {
                binding.spDriverType.setSelection(1);
            } else {
                binding.spDriverType.setSelection(2);
            }
        }


        binding.spDriverType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driverTye = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

   /* private void initiateAcType() {
        acTypeList.clear();
        acTypeList.add("Select Ac Type");
        acTypeList.add("Ac");
        acTypeList.add("Non Ac");
        ArrayAdapter acAdapter = new ArrayAdapter(AddCarActivity.this, android.R.layout.simple_dropdown_item_1line, acTypeList);
        binding.spAcType.setAdapter(acAdapter);

        if (carListModel != null && carListModel.getmCarAc() != null && !carListModel.getmCarAc().isEmpty()) {
            if (carListModel.getmCarAc().equalsIgnoreCase("1")) {
                binding.spAcType.setSelection(1);
            } else {
                binding.spAcType.setSelection(2);
            }
        }

        binding.spAcType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                acType = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/


    private void addCarAPi(String carName,String carFuel) {

        String carNumber = binding.etCarNumber.getText().toString().toUpperCase();

//        String carName = binding.etCarName.getText().toString();


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.insertCar(
                userId, carType, carName, carNumber, carFuel
        );
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                //  hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getResult().equalsIgnoreCase(Constant.SUCCESS_RESPONSE)) {

                            showError(response.body().getMessage());
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
                showError("something went wrong");


            }
        });
    }


    private void callDlGetDataApi(  ) {

        showLoader();

        JSONObject json = new JSONObject();

        try {
            json.put("key", QUICKEKYC_API_KEY);
            json.put("id_number", binding.etCarNumber.getText().toString());


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                okhttp3.MediaType.parse("application/json")
        );
        ApiInterface apiService = ApiClientVerification.getClient().create(ApiInterface.class);
        Call<RcDataModel> call = apiService.rc_full(body);
        call.enqueue(new Callback<RcDataModel>() {
            @Override
            public void onResponse(Call<RcDataModel> call, Response<RcDataModel> response) {
                hideLoader();
                try {
                    if (String.valueOf(response.code()).equalsIgnoreCase(Constant.SUCCESS_RESPONSE_CODE)) {
                        if (response.body().getStatusCode() == 200) {

                            addCarAPi(response.body().getData().getMakerModel(),response.body().getData().getFuelType());

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
            public void onFailure(Call<RcDataModel> call, Throwable t) {
                hideLoader();
                Log.e("Failure", t.toString());
                showError("Something went wrong");
            }
        });
    }

   /* private void imagePickerDialog() {
        dialog = new Dialog(AddCarActivity.this, R.style.my_dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.image_selection_dialog);
        dialog.show();

        ImageView ivCamera = dialog.findViewById(R.id.ivCamera);
        ImageView ivGallery = dialog.findViewById(R.id.ivGallery);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        ivCamera.setOnClickListener(view -> checkCameraPermission());
        ivGallery.setOnClickListener(view -> checkGalleryPermission());
        tvCancel.setOnClickListener(view -> dialog.dismiss());
    }*/

  /*  private void checkCameraPermission() {

        if (dialog != null) {
            dialog.dismiss();
        }

        if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
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
            if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_READ_MEDIA_IMAGES);
                openGallery();
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL);
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
            File file = File.createTempFile("IMG_" + timeStamp, ".jpg", AddCarActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            imageUri = FileProvider.getUriForFile(AddCarActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
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
//        cropImageOptions.aspectRatioX = 1;
//        cropImageOptions.aspectRatioY = 1;
        cropImageOptions.fixAspectRatio = false;
        CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(imageUri, cropImageOptions);
        cropImage.launch(cropImageContractOptions);
    }

    ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
        if (result.isSuccessful()) {
            String croppedImagePath = result.getUriFilePath(AddCarActivity.this, true);

            uploadImg = new File(croppedImagePath);
            binding.tvCarImg.setText(getImageName(croppedImagePath));

        }
    });*/

    private String getImageName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }


    private boolean validate() {
        boolean valid = true;

      /*  if (binding.etCarName.getText().toString().isEmpty()) {
            binding.etCarName.setError("Please enter car name..!");
            valid = false;
        } else {
            binding.etCarName.setError(null);
        }*/


        if (binding.etCarNumber.getText().toString().isEmpty()) {
            binding.etCarNumber.setError("Please enter your car number..!");
            valid = false;
        } else {
            binding.etCarNumber.setError(null);
        }

      /*  if (binding.etSeats.getText().toString().isEmpty()) {
            binding.etSeats.setError("Please enter number seats..!");
            valid = false;
        } else {
            binding.etSeats.setError(null);
        }*/

        if (binding.spCarType.getSelectedItemPosition() < 1) {
            Toast.makeText(this, "Please Choose Car Type", Toast.LENGTH_SHORT).show();
            valid = false;
        }

       /* if (binding.spDriverType.getSelectedItemPosition() < 1) {
            Toast.makeText(this, "Please Choose Drive Type", Toast.LENGTH_SHORT).show();
            valid = false;
        }*/

      /*  if (binding.spFuelType.getSelectedItemPosition() < 1) {
            Toast.makeText(this, "Please Choose Fuel Type", Toast.LENGTH_SHORT).show();
            valid = false;
        }*/


//        if (uploadImg == null) {
//            Toast.makeText(this, "Please Upload Your Car Image", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }


        return valid;
    }


}