package com.carro.vendor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import com.carro.vendor.R;
import com.carro.vendor.databinding.VehicleDetailItemBinding;
import com.carro.vendor.listener.CarItemClickListener;
import com.carro.vendor.model.CarListModel;
import com.carro.vendor.utils.ImagePathDecider;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    Context context;
    List<CarListModel> items;
    CarItemClickListener carItemClickListener;


    public CarListAdapter(Context context, List<CarListModel> items, CarItemClickListener carItemClickListener) {
        this.context = context;
        this.items = items;
        this.carItemClickListener = carItemClickListener;
    }

    @NonNull
    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VehicleDetailItemBinding binding = VehicleDetailItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListAdapter.ViewHolder holder, int position) {
        CarListModel item = items.get(holder.getAdapterPosition());

        holder.binding.tvName.setText(item.getmCarName());
        holder.binding.tvCarType.setText(item.getmCarTypeName());
        holder.binding.tvFuel.setText(item.getmCarFuel());
        holder.binding.btnVehicleNo.setText(item.getmCarNumber());
        holder.binding.tvSeat.setText(item.getmCarSeat()+" Seater");
        holder.binding.tvLuggage.setText(item.getmCarLuggage()+" Luggage");


                if(item.getmCarDrivetype() !=null ){
            if (item.getmCarDrivetype().equals("1")) {
                holder.binding.tvDriveType.setText("Manual");
            } else {
                holder.binding.tvDriveType.setText("Automatic");
            }
        }


//        if(item.getmCarAc() !=null ){
//            if (item.getmCarAc().equals("1")) {
//                holder.binding.tvAc.setText("Ac");
//            } else {
//                holder.binding.tvAc.setText("Non-Ac");
//            }
//        }

        int greenColor = ContextCompat.getColor(context, R.color.green); // Define your green color resource
        int defaultColor = ContextCompat.getColor(context, R.color.black); // Default text color

        if(item.getmCarPermit() !=null ){
            if (!item.getmCarPermit().isEmpty()) {
                holder.binding.tvPermit.setText(item.getmCarPermit());
                holder.binding.tvPermit.setTextColor(greenColor);
                holder.binding.ivPermit.setColorFilter(greenColor);
            } else {
                holder.binding.tvPermit.setText("Not Submitted");
                holder.binding.tvPermit.setTextColor(defaultColor);
                holder.binding.ivPermit.setColorFilter(defaultColor);
            }
        }

        if(item.getmCarOwnercarImg() !=null ){
            if (!item.getmCarOwnercarImg().isEmpty()) {
                holder.binding.tvCarPhotoWithOwner.setText(item.getmCarOwnercarImg());
                holder.binding.tvCarPhotoWithOwner.setTextColor(greenColor);
                holder.binding.ivOwner.setColorFilter(greenColor);
            } else {
                holder.binding.tvCarPhotoWithOwner.setText("Not Submitted");
                holder.binding.tvCarPhotoWithOwner.setTextColor(defaultColor);
                holder.binding.ivOwner.setColorFilter(defaultColor);
            }
        }

        if(item.getmCarIssCerti() !=null){
            if (!item.getmCarRegCerti().isEmpty()) {
                holder.binding.tvCertificateNo.setText(item.getmCarRegCerti());
                holder.binding.tvCertificateNo.setTextColor(greenColor);
                holder.binding.ivReg.setColorFilter(greenColor);
            } else {
                holder.binding.tvCertificateNo.setText("Not Submitted");
                holder.binding.tvCertificateNo.setTextColor(defaultColor);
                holder.binding.ivReg.setColorFilter(defaultColor);
            }
        }

        if(item.getmCarIssCerti()!=null){
            if (!item.getmCarIssCerti().isEmpty()) {
                holder.binding.tvInsurance.setText(item.getmCarIssCerti());
                holder.binding.tvInsurance.setTextColor(greenColor);
                holder.binding.ivIss.setColorFilter(greenColor);
            } else {
                holder.binding.tvInsurance.setText("Not Submitted");
                holder.binding.tvInsurance.setTextColor(defaultColor);
                holder.binding.ivIss.setColorFilter(defaultColor);
            }
        }



        Glide.with(context)
                .load(ImagePathDecider.getCarImagePath() +item.getmCarImg())
                .error(R.drawable.image_car)
                .into(holder.binding.ivVehicle);

        if(item.getmCarStatus() !=null){
            if (item.getmCarStatus().equals("1")) {
                holder.binding.btnStatus.setText("Active");
                holder.binding.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.green2));
                holder.binding.ivEdit.setVisibility(View.GONE);
                holder.binding.tvEditDoc.setVisibility(View.GONE);
            } else {
                holder.binding.btnStatus.setText("In-Active");
                holder.binding.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }


        holder.binding.tvEditDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carItemClickListener.onCarClick(item,"docEdit");
            }
        });

        holder.binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carItemClickListener.onCarClick(item,"edit");
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VehicleDetailItemBinding binding;

        public ViewHolder(VehicleDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
