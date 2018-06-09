package com.wgu.el.wgustudent.ui.note.list;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgu.el.wgustudent.R;

public class NotesListViewHolder extends RecyclerView.ViewHolder {

    TextView noteNameTv;
    TextView noteAssociatedCourseTv;
    ImageView deleteButton;

    public NotesListViewHolder(View itemView) {
        super(itemView);
        noteNameTv = itemView.findViewById(R.id.tv_note_title);
        noteAssociatedCourseTv = itemView.findViewById(R.id.tv_note_associated_course);
        deleteButton = itemView.findViewById(R.id.ic_delete_button);
    }
}
