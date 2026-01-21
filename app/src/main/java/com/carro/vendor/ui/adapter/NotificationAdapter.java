/*
package com.logixhunt.carrorentalvendor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.logixhunt.carrorentalvendor.R;
import com.logixhunt.carrorentalvendor.databinding.NotificationItemBinding;
import com.logixhunt.carrorentalvendor.listener.NotificationClickListener;
import com.logixhunt.carrorentalvendor.model.NotificationModel;
import com.logixhunt.carrorentalvendor.utils.Constant;
import com.logixhunt.carrorentalvendor.utils.DateFormater;
import com.logixhunt.carrorentalvendor.utils.ImagePathDecider;
import com.logixhunt.carrorentalvendor.utils.Utils;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<NotificationModel> items;
    NotificationClickListener notificationClickListener;

    public NotificationAdapter(Context context, List<NotificationModel> items, NotificationClickListener notificationClickListener) {
        this.context = context;
        this.items = items;
        this.notificationClickListener = notificationClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationItemBinding binding = NotificationItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel item = items.get(holder.getAdapterPosition());
        holder.binding.tvMsg.setText(item.getmNotifMessage());
        holder.binding.tvTime.setText(Utils.formatTimeString(Constant.HHMMSS,Constant.HHMMSSA,item.getmNotifTime()));
        holder.binding.tvDate.setText(DateFormater.changeDateFormat(Constant.yyyyMMdd,Constant.ddMMyyyy,item.getmNotifDate()));

        if (position != 0) {
            String prevDate = items.get(position - 1).getmNotifDate();
            if (prevDate.equals(item.getmNotifDate())) {
                holder.binding.tvDate.setVisibility(View.GONE);
            } else {
                holder.binding.tvDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.binding.tvDate.setVisibility(View.VISIBLE);
        }

        if (item.getmNotifBooking() != null && !item.getmNotifBooking().isEmpty() &&
                (item.getmBkingVendor() == null || item.getmBkingVendor().isEmpty())) {
            holder.binding.tvAccept.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvAccept.setVisibility(View.GONE);
        }


        holder.binding.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationClickListener.onNotifClick(item);
            }
        });

        Glide.with(context)
                .load(ImagePathDecider.getNotificationImagePath()+item.getmNotifImage())
                .error(R.drawable.img_no_profile)
                .into(holder.binding.ivNotification);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding binding;

        public ViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
*/


package com.carro.vendor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.carro.vendor.R;
import com.carro.vendor.databinding.NotificationItemBinding;
import com.carro.vendor.model.NotificationModel;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.DateFormater;
import com.carro.vendor.utils.ImagePathDecider;
import com.carro.vendor.utils.Utils;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<NotificationModel> items;

    public NotificationAdapter(Context context, List<NotificationModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationItemBinding binding = NotificationItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel item = items.get(holder.getAdapterPosition());
        holder.binding.tvMsg.setText(item.getmNotifMessage());
        holder.binding.tvTime.setText(Utils.formatTimeString(Constant.HHMMSS,Constant.HHMMSSA,item.getmNotifTime()));
        holder.binding.tvDate.setText(DateFormater.changeDateFormat(Constant.yyyyMMdd,Constant.ddMMyyyy,item.getmNotifDate()));

        if (position != 0) {
            String prevDate = items.get(position - 1).getmNotifDate();
            if (prevDate.equals(item.getmNotifDate())) {
                holder.binding.tvDate.setVisibility(View.GONE);
            } else {
                holder.binding.tvDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.binding.tvDate.setVisibility(View.VISIBLE);
        }


        Glide.with(context)
                .load(ImagePathDecider.getNotificationImagePath()+item.getmNotifImage())
                .error(R.drawable.img_no_profile)
                .into(holder.binding.ivNotification);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding binding;

        public ViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

