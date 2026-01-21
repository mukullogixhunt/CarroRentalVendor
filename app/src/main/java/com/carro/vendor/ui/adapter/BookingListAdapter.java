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
//        holder.binding.tvDistance.setText(item.getmBkingKm() + "Km");
        if(item.getmBkingFrom().isEmpty()){
            holder.binding.tvFromLocation.setText("---");
        }else{
            holder.binding.tvFromLocation.setText(item.getmBkingFrom());
        }

        if(item.getmBkingTo().isEmpty()){
            holder.binding.tvToLocation.setText("---");
        }else{
            holder.binding.tvToLocation.setText(item.getmBkingTo());
        }



        if (item.getmBkingType().equals("4")){
            holder.binding.tvCarType.setText(item.getmBusTitle()+" \n( Bus )");
        }else {
            holder.binding.tvCarType.setText(item.getmCtypeTitle()+" \n("+item.getmCtypeNumber()+")");
        }

        double totalAmount = Double.parseDouble(item.getmBkingTotal());
        double eightyPercentAmount = totalAmount * 0.8;

        holder.binding.tvAmount.setText(
                new IndianCurrencyFormat().inCuFormatText(eightyPercentAmount+"")
        );

        String pick_date;
        String pick_time;
        pick_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingPickup());
        pick_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingPickupAt());
        holder.binding.tvFromDate.setText(pick_date + " " + pick_time);

        String drop_date;
        String drop_time;
        drop_date = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingReturn());
        drop_time = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingReturnAt());
        holder.binding.tvDropDate.setText(drop_date + " " + drop_time);

        String bookingDate = DateFormater.changeDateFormat(Constant.yyyyMMdd, Constant.ddMMyyyy, item.getmBkingPickup());
        String bookingTime = Utils.formatTimeString(Constant.HHMMSS, Constant.HHMMSSA, item.getmBkingPickupAt());
        holder.binding.tvBookingDate.setText(bookingDate + "\n" + bookingTime);
        holder.binding.tvPickupDetails.setText("Pickup : " + item.getmBkingFrom());

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

        holder.binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingClickListener.onBookingClick(item,"cancel");
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
