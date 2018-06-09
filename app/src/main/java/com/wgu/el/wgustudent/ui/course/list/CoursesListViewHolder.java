package com.wgu.el.wgustudent.ui.course.list;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgu.el.wgustudent.R;

public class CoursesListViewHolder extends RecyclerView.ViewHolder {

    TextView courseNameTv;
    ImageView editDeleteButton;

    public CoursesListViewHolder(View itemView) {
        super(itemView);
        courseNameTv = itemView.findViewById(R.id.tv_course_name);
        editDeleteButton = itemView.findViewById(R.id.overflow);
    }
}
