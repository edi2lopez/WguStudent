package com.wgu.el.wgustudent.ui.term.list;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgu.el.wgustudent.R;

public class TermsListViewHolder extends RecyclerView.ViewHolder {

    TextView termNameTv;
    TextView termStartDate;
    TextView termEndDate;
    ImageView editDeleteButton;

    public TermsListViewHolder(View itemView) {
        super(itemView);
        termNameTv = itemView.findViewById(R.id.tv_term_name);
//        termStartDate = itemView.findViewById(R.id.tv_term_start_date);
//        termEndDate = itemView.findViewById(R.id.tv_term_end_date);
        editDeleteButton = itemView.findViewById(R.id.overflow);
    }

}
