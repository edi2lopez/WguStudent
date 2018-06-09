package com.wgu.el.wgustudent.ui.course.detail;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Note;

import java.util.List;

public class CourseNotesListAdapter extends RecyclerView.Adapter<CourseNotesListViewHolder> {

    private final Context context;
    private List<Note> items;
    private View.OnClickListener itemClickListener;
    private View.OnClickListener deleteClickListener;

    public CourseNotesListAdapter(List<Note> items, Context context, View.OnClickListener itemClickListener, View.OnClickListener deleteClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public CourseNotesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_courses_list_from_term, parent, false);
        return new CourseNotesListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseNotesListViewHolder holder, int position) {
        Note item = items.get(position);

        holder.noteNameTv.setText(item.getNoteName());
        holder.itemView.setTag(item);
        holder.itemView.setTag(item);
        holder.deleteButton.setTag(item);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(itemClickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<Note> courses) {
        this.items = courses;
        notifyDataSetChanged();
    }

}
