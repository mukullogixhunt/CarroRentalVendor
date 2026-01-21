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
import com.carro.vendor.databinding.DriverItemBinding;
import com.carro.vendor.listener.DriverClickListener;
import com.carro.vendor.model.DriverListModel;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.DateFormater;
import com.carro.vendor.utils.ImagePathDecider;

import java.util.List;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ViewHolder> {

    Context context;
    List<DriverListModel> items;
    DriverClickListener driverClickListener;

    public DriverListAdapter(Context context, List<DriverListModel> items, DriverClickListener driverClickListener) {
        this.context = context;
        this.items = items;
        this.driverClickListener = driverClickListener;
    }

    @NonNull
    @Override
    public DriverListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DriverItemBinding binding = DriverItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverListAdapter.ViewHolder holder, int position) {
        DriverListModel item = items.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(item.getmDriverName());
        holder.binding.tvMobileNumber.setText(item.getmDriverMobile());

        holder.binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverClickListener.onDriverClick(item);
            }
        });

//        holder.binding.tvDLicence.setText(item.getmDriverDrivelic());
        if (!item.getmDriverDrivelicExpdate().isEmpty()&&item.getmDriverDrivelicExpdate()!=null){
            String date;
            date= DateFormater.changeDateFormat(Constant.yyyyMMdd,Constant.ddMMyyyy,item.getmDriverDrivelicExpdate());
            holder.binding.tvExpireyDate.setText(date);
        }


        if (!item.getmDriverPoliceVerify().isEmpty()&&item.getmDriverPoliceVerify()!=null){
            holder.binding.tvPVNo.setText(item.getmDriverPoliceVerify());
            holder.binding.ivPV.setVisibility(View.VISIBLE);
            holder.binding.ivUploadDoc.setVisibility(View.GONE);
        }

        if (!item.getmDriverDrivelic().isEmpty()&&item.getmDriverDrivelic()!=null){
            holder.binding.tvNotSubmit.setText(item.getmDriverDrivelic());
            holder.binding.ivUploadLicence.setVisibility(View.GONE);
        }else {
            holder.binding.ivUploadLicence.setVisibility(View.GONE);

        }


        Glide.with(context)
                .load(ImagePathDecider.getDriverImagePath() +item.getmDriverDrivelic())
                .error(R.drawable.ic_no_notification)
                .into(holder.binding.ivDriver);

        if (item.getmDriverStatus().equals("1")){
            holder.binding.btnStatus.setText("Verified");
            holder.binding.btnStatus.setIcon(ContextCompat.getDrawable(context, R.drawable.icn_verified));
            holder.binding.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.green2));
            holder.binding.ivEdit.setVisibility(View.GONE);

        }else {
            holder.binding.btnStatus.setText("Not-Verified");
            holder.binding.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.binding.btnStatus.setIcon(ContextCompat.getDrawable(context, R.drawable.icn_pending));

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DriverItemBinding binding;

        public ViewHolder(DriverItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
