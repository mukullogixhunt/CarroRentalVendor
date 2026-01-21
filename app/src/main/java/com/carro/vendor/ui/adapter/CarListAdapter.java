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
import com.carro.vendor.utils.Utils;

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

        Glide.with(context)
                .load(ImagePathDecider.getCarImagePath() +item.getmCarImg())
                .error(R.drawable.image_car)
                .into(holder.binding.ivVehicle);

        if(item.getmCarStatus() !=null){
            if (item.getmCarStatus().equals("1")) {
                holder.binding.btnStatus.setText("Active");
                holder.binding.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.green2));
            } else {
                holder.binding.btnStatus.setText("In-Active");
                holder.binding.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        }

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
