package com.zzz.haitouapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompanyRecyclerAdapter extends RecyclerView.Adapter<CompanyRecyclerAdapter.CompanyViewHolder> {

    private List<CompanyJobModel> companyList;
    private Function<CompanyJobModel, Void> onClickListener;

    public CompanyRecyclerAdapter(
            List<CompanyJobModel> companyList,
            Function<CompanyJobModel, Void> onClick
    ) {
        this.companyList = companyList;
        this.onClickListener = onClick;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        final CompanyJobModel companyJobModel = companyList.get(position);
        holder.tvCompany.setText(companyJobModel.getTitle());
        holder.tvCareer.setText(companyJobModel.getCareerList().toString());
        holder.tvCity.setText(companyJobModel.getCityList().toString());
        holder.tvTime.setText(companyJobModel.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.apply(companyJobModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    static class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCompany;
        TextView tvTime;
        TextView tvCareer;
        TextView tvCity;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCompany = itemView.findViewById(R.id.tv_company);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvCareer = itemView.findViewById(R.id.tv_career);
            tvCity = itemView.findViewById(R.id.tv_city);
        }
    }

    void replaceData(List<CompanyJobModel> newList) {
        companyList.clear();
        companyList.addAll(newList);
        notifyDataSetChanged();
    }
}
