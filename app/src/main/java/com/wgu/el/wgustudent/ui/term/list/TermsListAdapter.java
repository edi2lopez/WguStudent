package com.wgu.el.wgustudent.ui.term.list;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Term;

import java.util.List;

public class TermsListAdapter extends RecyclerView.Adapter<TermsListViewHolder> {

    private final Context context;
    private List<Term> items;
    private View.OnClickListener deleteClickListener;
    private View.OnClickListener itemClickListener;

    TermsListAdapter(List<Term> items, Context context, View.OnClickListener itemClickListener, View.OnClickListener  deleteClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public TermsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_terms_list, parent, false);
        return new TermsListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(TermsListViewHolder holder, int position) {
        Term item = items.get(position);

        holder.termNameTv.setText(item.getTermName());
        holder.itemView.setTag(item);
        holder.editDeleteButton.setTag(item);

        holder.editDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, holder.editDeleteButton);
                popup.getMenuInflater().inflate(R.menu.menu_edit_delete_item, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                itemClickListener.onClick(holder.editDeleteButton);
                                return true;
                            case R.id.action_delete:
                                deleteClickListener.onClick(holder.editDeleteButton);
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Term> terms) {
        this.items = terms;
        notifyDataSetChanged();
    }

}
