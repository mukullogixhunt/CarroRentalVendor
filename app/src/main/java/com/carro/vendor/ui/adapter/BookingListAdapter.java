/*
package com.logixhunt.carrorentalvendor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.logixhunt.carrorentalvendor.R;
import com.logixhunt.carrorentalvendor.databinding.BookingItemBinding;
import com.logixhunt.carrorentalvendor.listener.BookingClickListener;
import com.logixhunt.carrorentalvendor.model.BookingListModel;
import com.logixhunt.carrorentalvendor.utils.Constant;
import com.logixhunt.carrorentalvendor.utils.DateFormater;
import com.logixhunt.carrorentalvendor.utils.ImagePathDecider;
import com.logixhunt.carrorentalvendor.utils.IndianCurrencyFormat;
import com.logixhunt.carrorentalvendor.utils.Utils;

import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    Context context;
    List<BookingListModel> items;
    BookingClickListener bookingClickListener;


    boolean isActive;

    public BookingListAdapter(Context context, List<BookingListModel> items,boolean isActive, BookingClickListener bookingClickListener) {
        this.context = context;
        this.items = items;
        this.isActive = isActive;
        this.bookingClickListener = bookingClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookingItemBinding binding = BookingItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingListModel item = items.get(holder.getAdapterPosition());


        if (isActive){
            holder.binding.llLayout.setVisibility(View.GONE);
        }else {
            holder.binding.llLayout.setVisibility(View.VISIBLE);

        }

        holder.binding.tvFromLocation.setText(item.getmBkingPickupAddress());
        holder.binding.tvToLocation.setText(item.getmBkingDropAddress());
        if (item.getmBkingType().equals("4")){
            holder.binding.tvCarType.setText(item.getmBusTitle()+" \n( Bus )");
        }else {
            holder.binding.tvCarType.setText(item.getmCtypeTitle() + "\n(" + item.getmCtypeNumber().toUpperCase() + ")");
        }


        Glide.with(context)
                .load(ImagePathDecider.getCarImagePath() + item.getmCtypeImg())
                .error(R.drawable.img_no_profile)
                .into(holder.binding.ivCarImage);


        holder.binding.tvPickupDetails.setText("Pickup : " + item.getmBkingPickupAddress());
        holder.binding.tvAmount.setText(new IndianCurrencyFormat().inCuFormatText(item.getmBkingTotal()));

        String pick_date;
        String pick_time;
        pick_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingPickup());
        pick_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingPickupAt());
        holder.binding.tvFromDate.setText(pick_date + " " + pick_time);
        holder.binding.tvTripTime.setText(pick_date + " " + pick_time);

        String drop_date;
        String drop_time;
        drop_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingReturn());
        drop_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingReturnAt());
        holder.binding.tvToDate.setText(drop_date + " " + drop_time);

        if (item.getmBkingStatus().equals("1")) {
            holder.binding.btnAccept.setVisibility(View.VISIBLE);
            holder.binding.btnComplete.setVisibility(View.GONE);
            holder.binding.btnStatus.setText("Pending");
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary_dark));
        } else {
            holder.binding.btnAccept.setVisibility(View.GONE);
            holder.binding.btnComplete.setVisibility(View.VISIBLE);
            holder.binding.btnStatus.setText("Accepted");
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.green2));
        }

        switch (item.getmBkingType()) {
            case "1":
                if (!item.getmBkingRoadType().isEmpty()) {
                    switch (item.getmBkingRoadType()) {
                        case "1":
                            holder.binding.tvTripType.setText("One Way Trip");
                            holder.binding.ivTripType.setImageResource(R.drawable.icn_one_way);
                            break;
                        case "2":
                            holder.binding.tvTripType.setText("Round Trip");
                            holder.binding.ivTripType.setImageResource(R.drawable.ic_round_trip);
                            break;
                        case "3":
                            holder.binding.tvTripType.setText("Hourly Trip");
                            holder.binding.ivTripType.setImageResource(R.drawable.ic_round_trip);
                            break;
                        case "4":
                            holder.binding.tvTripType.setText("Airport");
                            holder.binding.ivTripType.setImageResource(R.drawable.ic_round_trip);
                            break;
                    }
                }
                break;

            case "2":
                holder.binding.tvTripType.setText("Self Drive Service");

                break;
            case "3":
                holder.binding.tvTripType.setText("Luxury Cars Service");
                holder.binding.ivView.setVisibility(View.GONE);
                holder.binding.ivLastDn.setVisibility(View.GONE);
                holder.binding.ivFirstDn.setVisibility(View.GONE);
                holder.binding.tvToDate.setVisibility(View.GONE);
                holder.binding.tvToLocation.setVisibility(View.GONE);
                break;
            case "4":
                holder.binding.tvTripType.setText("Bus Booking Service");
                holder.binding.ivView.setVisibility(View.GONE);
                holder.binding.ivLastDn.setVisibility(View.GONE);
                holder.binding.ivFirstDn.setVisibility(View.GONE);
                holder.binding.tvToDate.setVisibility(View.GONE);
                holder.binding.tvToLocation.setVisibility(View.GONE);
                break;
        }


        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"accept");
            }
        });
        holder.binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"cancel");
            }
        });
        holder.binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"complete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BookingItemBinding binding;

        public ViewHolder(BookingItemBinding binding) {
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.carro.vendor.R;
import com.carro.vendor.databinding.BookingItemBinding;
import com.carro.vendor.listener.BookingClickListener;
import com.carro.vendor.model.BookingListModel;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.DateFormater;
import com.carro.vendor.utils.IndianCurrencyFormat;
import com.carro.vendor.utils.Utils;

import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    Context context;
    List<BookingListModel> items;
    BookingClickListener bookingClickListener;


    boolean isActive;

    public BookingListAdapter(Context context, List<BookingListModel> items,boolean isActive, BookingClickListener bookingClickListener) {
        this.context = context;
        this.items = items;
        this.isActive = isActive;
        this.bookingClickListener = bookingClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookingItemBinding binding = BookingItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingListModel item = items.get(holder.getAdapterPosition());


        if (isActive){
            holder.binding.btnAccept.setVisibility(View.GONE);
        }else {
            holder.binding.btnAccept.setVisibility(View.VISIBLE);

        }

        holder.binding.tvDistance.setText(item.getmBkingKm() + "Km");

            holder.binding.tvFromLocation.setText(item.getmBkingFrom());



            holder.binding.tvFromLocation.setText(item.getmBkingTo());



        if (item.getmBkingType().equals("4")){
            holder.binding.tvCarType.setText(item.getmBusTitle()+" \n( Bus )");
        }else {
            holder.binding.tvCarType.setText(item.getmCtypeTitle());
        }

        if(item.getmBkingTo() != null && item.getmBkingTo().isEmpty()){
            holder.binding.viewDots.setVisibility(View.GONE);
            holder.binding.llDrop.setVisibility(View.GONE);
            holder.binding.divider4.setVisibility(View.GONE);
            holder.binding.llDropDate.setVisibility(View.GONE);
        }else{
            holder.binding.viewDots.setVisibility(View.VISIBLE);
            holder.binding.llDrop.setVisibility(View.VISIBLE);
            holder.binding.divider4.setVisibility(View.VISIBLE);
            holder.binding.llDropDate.setVisibility(View.VISIBLE);
        }


        holder.binding.tvAmount.setText(new IndianCurrencyFormat().inCuFormatText(item.getmBkingTotal()));

        String pick_date;
        String pick_time;
        pick_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingPickup());
        pick_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingPickupAt());
        holder.binding.tvPickupDate.setText(pick_date + " " + pick_time);

        String drop_date;
        String drop_time;
        drop_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingReturn());
        drop_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingReturnAt());
        holder.binding.tvDropDate.setText(drop_date + " " + drop_time);

        if (item.getmBkingStatus().equals("1")) {
            holder.binding.btnAccept.setVisibility(View.VISIBLE);
            holder.binding.btnStatus.setText("Pending");
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary_dark));
        } else {
            holder.binding.btnAccept.setVisibility(View.GONE);
            holder.binding.btnStatus.setText("Accepted");
            holder.binding.btnStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.green2));
        }


        holder.binding.tvTripType.setText(getServiceName(item));



        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"accept");
            }
        });


    }


    private String getServiceName(BookingListModel item) {
        switch (item.getmBkingType()) {
            case "1": // Cab Service
                switch (item.getmBkingTypeCat()) {
                    case "1": return "City Ride";
                    case "2": return "One Way Cab Service";
                    case "3": return "Outstation Cab Service";
                    case "4": return "Airport Cab Service";
                    default: return "Cab Service";
                }
            case "2":

                switch (item.getmBkingTypeCat()) {
                    case "1": return "Self Drive Service (Rental)";
                    case "2": return "Self Drive Service (Subscription)";
                    default: return "Self Drive Service";
                }


            case "3": return "Luxury Car Service";
            case "4": return "Bus Booking Service";
            default: return "Booking";
        }
    }




    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BookingItemBinding binding;

        public ViewHolder(BookingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
