package com.wgu.el.wgustudent.ui.term.detail;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Course;

import java.util.List;

public class TermCoursesListAdapter extends RecyclerView.Adapter<TermCoursesListViewHolder> {

    private final Context context;
    private List<Course> items;
    private View.OnClickListener itemClickListener;
    private View.OnClickListener deleteClickListener;

    public TermCoursesListAdapter(List<Course> items, Context context, View.OnClickListener itemClickListener, View.OnClickListener deleteClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public TermCoursesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_courses_list_from_term, parent, false);
        return new TermCoursesListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TermCoursesListViewHolder holder, int position) {
        Course item = items.get(position);

        holder.courseNameTv.setText(item.getCourseName());
        holder.itemView.setTag(item);
        holder.deleteButton.setTag(item);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<Course> courses) {
        this.items = courses;
        notifyDataSetChanged();
    }

}
