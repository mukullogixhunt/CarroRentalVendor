package com.carro.vendor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.carro.vendor.R;
import com.carro.vendor.databinding.WalletTransactionItemBinding;
import com.carro.vendor.model.WalletTransModel;
import com.carro.vendor.utils.Constant;
import com.carro.vendor.utils.DateFormater;

import java.util.List;

public class WalletTransactionAdapter extends RecyclerView.Adapter<WalletTransactionAdapter.ViewHolder> {

    Context context;
    List<WalletTransModel> items;

    public WalletTransactionAdapter(Context context, List<WalletTransModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WalletTransactionItemBinding binding = WalletTransactionItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WalletTransModel item = items.get(holder.getAdapterPosition());

        String date;
        String time;
        date= DateFormater.changeDateFormat(Constant.yyyyMMdd,Constant.ddMMyyyy,item.getmWalletDate());
        time= DateFormater.changeDateFormat(Constant.HHMMSS,Constant.HHMMSSA,item.getmWalletTime());
        holder.binding.tvDateTime.setText(date+" "+time);

        if(item.getmWalletCd().equals("1")){
            holder.binding.tvStatus.setText("Credit");
            holder.binding.tvAmount.setText("+Rs. "+item.getmWalletAmt());
            holder.binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else{
            holder.binding.tvStatus.setText("Debit");
            holder.binding.tvAmount.setText("-Rs. "+item.getmWalletAmt());
            holder.binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WalletTransactionItemBinding binding;

        public ViewHolder(WalletTransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
