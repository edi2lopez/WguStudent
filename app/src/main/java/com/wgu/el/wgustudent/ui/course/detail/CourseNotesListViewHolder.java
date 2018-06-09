package com.wgu.el.wgustudent.ui.course.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgu.el.wgustudent.R;


public class CourseNotesListViewHolder extends RecyclerView.ViewHolder {

    TextView noteNameTv;
    ImageView deleteButton;

    public CourseNotesListViewHolder(View itemView) {
        super(itemView);
        noteNameTv = itemView.findViewById(R.id.tv_note_name);
        deleteButton = itemView.findViewById(R.id.ic_delete_button);
    }
}
