package com.wgu.el.wgustudent.ui.note.list;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.ui.course.detail.CourseDetailViewModel;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListViewHolder>  {

    private final Context context;
    private List<Note> items;
    private View.OnClickListener deleteClickListener;
    private View.OnClickListener itemClickListener;

    public NotesListAdapter(List<Note> items, Context context, View.OnClickListener itemClickListener, View.OnClickListener  deleteClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public NotesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes_list, parent, false);
        return new NotesListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(NotesListViewHolder holder, int position) {
        Note item = items.get(position);

        holder.noteNameTv.setText(item.getNoteName());
        holder.noteAssociatedCourseTv.setText("");
        holder.itemView.setTag(item);
        holder.deleteButton.setTag(item);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(itemClickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<Note> notes) {
        this.items = notes;
        notifyDataSetChanged();
    }
    
}
