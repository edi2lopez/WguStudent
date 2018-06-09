package com.wgu.el.wgustudent.ui.assessment.list;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgu.el.wgustudent.R;

public class AssessmentsListViewHolder extends RecyclerView.ViewHolder  {

    TextView assessmentNameTv;
    TextView assessmentDateTv;
    ImageView deleteButton;

    public AssessmentsListViewHolder(View itemView) {
        super(itemView);
        assessmentNameTv = itemView.findViewById(R.id.tv_assessment_name);
        assessmentDateTv = itemView.findViewById(R.id.tv_assessment_date);
        deleteButton = itemView.findViewById(R.id.ic_delete_button);
    }
}
