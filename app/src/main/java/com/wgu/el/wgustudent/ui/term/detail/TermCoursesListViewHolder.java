package com.wgu.el.wgustudent.ui.term.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgu.el.wgustudent.R;


public class TermCoursesListViewHolder extends RecyclerView.ViewHolder  {

    TextView courseNameTv;
    ImageView deleteButton;

    public TermCoursesListViewHolder(View itemView) {
        super(itemView);
        courseNameTv = itemView.findViewById(R.id.tv_course_name);
        deleteButton = itemView.findViewById(R.id.ic_delete_button);
    }
}
