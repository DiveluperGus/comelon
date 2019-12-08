package com.gustavo.comelon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.gustavo.comelon.R;
import com.gustavo.comelon.model.Commensal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommensalAdapter extends RecyclerView.Adapter<CommensalAdapter.ViewHolder> {
    private List<Commensal> commensals;
    private Context ctx;

    public CommensalAdapter(List<Commensal> commensals, Context ctx) {
        this.commensals = commensals;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_name_commensal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commensal commensal = commensals.get(position);
        holder.txtNameCommensal.setText(commensal.getName() + " " + commensal.getSurname());
        if (commensal.isSelected()) {
            holder.constraintNameCommensal.setBackgroundResource(R.color.colorPrimary);
        } else {
            holder.constraintNameCommensal.setBackgroundResource(R.color.backgroundLight);
        }

    }

    @Override
    public int getItemCount() {
        return commensals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.constraint_name_commensal)
        ConstraintLayout constraintNameCommensal;
        @BindView(R.id.txt_name_commensal)
        TextView txtNameCommensal;

        public ViewHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
