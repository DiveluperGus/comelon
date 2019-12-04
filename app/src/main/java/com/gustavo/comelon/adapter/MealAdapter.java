package com.gustavo.comelon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gustavo.comelon.R;
import com.gustavo.comelon.model.Meal;
import com.gustavo.comelon.utils.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private List<Meal> mealList;
    private Context ctx;
    private ItemClickListener mListener;

    public MealAdapter(Context ctx, List<Meal> meals) {
        this.mealList = meals;
        this.ctx = ctx;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_meal, parent, false);

        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal m = mealList.get(position);
        holder.nameMeal.setText(m.getNameMeal());
        holder.priceMeal.setText("" + m.getMealCost());
        holder.txtDescriptionMeal.setText(m.getDescription());
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name_meal)
        TextView nameMeal;
        @BindView(R.id.txt_price_meal)
        TextView priceMeal;
        @BindView(R.id.txt_description_meal)
        TextView txtDescriptionMeal;

        public ViewHolder(@NonNull View v, final ItemClickListener listener) {
            super(v);
            ButterKnife.bind(this, v);

            itemView.setOnClickListener(v2 -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
