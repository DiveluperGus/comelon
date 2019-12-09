package com.gustavo.comelon.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gustavo.comelon.R;
import com.gustavo.comelon.model.Commensal;
import com.gustavo.comelon.ui.manage.commensal.consult.ModifyStatusCommensal;
import com.gustavo.comelon.utils.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusCommensalAdapter extends RecyclerView.Adapter<StatusCommensalAdapter.VH> {

    private List<Commensal> commensals;
    private Context ctx;
    private ItemClickListener mListener;

    public StatusCommensalAdapter(List<Commensal> commensals, Context ctx) {
        this.commensals = commensals;
        this.ctx = ctx;
    }

    public void setListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_satus_commensal, parent, false);

        return new VH(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Commensal commensal = commensals.get(position);

        holder.txtNumberCommensal.setText((position+1) + ".");
        holder.txtNameCommensal.setText(commensal.getName() + " " + commensal.getSurname());
        if (commensal.getStatus() == 1) {
            holder.imgviewStatusCommensal.setBackgroundColor(ctx.getResources().getColor(R.color.greenNormal));
        } else if (commensal.getStatus() == 2) {
            holder.imgviewStatusCommensal.setBackgroundColor(ctx.getResources().getColor(R.color.yellowNormal));
        } else if (commensal.getStatus() == 3) {
            holder.imgviewStatusCommensal.setBackgroundColor(ctx.getResources().getColor(R.color.brownNormal));
        } else if (commensal.getStatus() == 4) {
            holder.imgviewStatusCommensal.setBackgroundColor(ctx.getResources().getColor(R.color.redNormal));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(ctx.getApplicationContext(), ModifyStatusCommensal.class);
            Bundle args = new Bundle();
            String name = commensal.getName();
            String surname = commensal.getSurname();
            args.putString("nameCommensal",name);
            args.putString("surname",surname);
            i.putExtras(args);
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return commensals.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_number_commensal)
        TextView txtNumberCommensal;
        @BindView(R.id.txt_name_commensal)
        TextView txtNameCommensal;
        @BindView(R.id.imgview_status_commensal)
        ImageView imgviewStatusCommensal;

        public VH(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            });
        }
    }
}
