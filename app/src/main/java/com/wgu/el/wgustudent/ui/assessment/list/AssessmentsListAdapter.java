package com.wgu.el.wgustudent.ui.assessment.list;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Assessment;

import java.util.List;

public class AssessmentsListAdapter extends RecyclerView.Adapter<AssessmentsListViewHolder> {

    private final Context context;
    private List<Assessment> items;
    private View.OnClickListener itemClickListener;
    private View.OnClickListener deleteClickListener;

    public AssessmentsListAdapter(List<Assessment> items, Context context, View.OnClickListener itemClickListener, View.OnClickListener  deleteClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public AssessmentsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assessments_list, parent, false);
        return new AssessmentsListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssessmentsListViewHolder holder, int position) {
        Assessment item = items.get(position);

        holder.assessmentNameTv.setText(item.getAssessmentName());
        holder.assessmentDateTv.setText(item.getAssessmentDate().toString());
        holder.itemView.setTag(item);
        holder.deleteButton.setTag(item);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<Assessment> assessments) {
        this.items = assessments;
        notifyDataSetChanged();
    }

}
