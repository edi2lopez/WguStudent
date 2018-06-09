package com.wgu.el.wgustudent.ui.term.list;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.term.detail.TermDetailActivity;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.ArrayList;

public class TermsListFragment extends LifecycleFragment {

    private static final String TAG = "TermsListFragment";
    private TermsListAdapter adapter;
    private TermsListViewModel termsListViewModel;

    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Term term = (Term) view.getTag();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.delete_course_message);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    termsListViewModel.deleteTerm(term);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
    };

    public View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Term term = (Term) view.getTag();
            int termId = term.getId();

            Bundle bundle = new Bundle();
            bundle.putInt(WguConst.TERMS_LIST_ACTIVITY_TAG, termId);
            Intent intent = new Intent(getActivity(), TermDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_terms_list, container, false);
        getActivity().setTitle(R.string.all_terms);

        setupRecyclerView(v);

        FloatingActionButton fab = v.findViewById(R.id.fab_add);
        fab.setOnClickListener(v1 -> startActivity(new Intent(getContext(), TermDetailActivity.class)));

        WguApplication application = (WguApplication) getActivity().getApplication();
        termsListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(TermsListViewModel.class);

        termsListViewModel.getTerms().observe(this, terms -> {
            Log.d(TAG, "Terms Changed:" + terms);
            adapter.setItems(terms);
        });

        return v;
    }

    private void setupRecyclerView(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.rv_list_terms);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TermsListAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);

        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
