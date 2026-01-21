package com.carro.vendor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carro.vendor.databinding.RecommendedItemBinding;
import com.carro.vendor.listener.RecommendedClickListener;
import com.carro.vendor.model.RecommendedModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    Context context;
    List<RecommendedModel> items;
    RecommendedClickListener recommendedClickListener;

    public RecommendedAdapter(Context context, List<RecommendedModel> items, RecommendedClickListener recommendedClickListener) {
        this.context = context;
        this.items = items;
        this.recommendedClickListener = recommendedClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecommendedItemBinding binding = RecommendedItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendedModel item = items.get(holder.getAdapterPosition());
        holder.binding.tvAmount.setText(item.getmWalletAmt());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendedClickListener.onRecommendedClick(item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecommendedItemBinding binding;

        public ViewHolder(RecommendedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
