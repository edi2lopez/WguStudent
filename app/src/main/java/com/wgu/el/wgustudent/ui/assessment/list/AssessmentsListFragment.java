package com.wgu.el.wgustudent.ui.assessment.list;


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
import android.widget.TextView;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.assessment.detail.AssessmentDetailActivity;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.ArrayList;

public class AssessmentsListFragment extends LifecycleFragment {

    private static final String TAG = "AssessmentsListFragment";
    private AssessmentsListAdapter adapter;
    private AssessmentsListViewModel assessmentsListViewModel;

    int courseId;
    int assessmentId;

    RecyclerView recyclerView;
    private TextView emptyView;
    FloatingActionButton fab;

    private View.OnClickListener deleteClickListener = v -> {
        Assessment assessment = (Assessment) v.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.delete_note_message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                assessmentsListViewModel.deleteAssessment(assessment);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    };

    private View.OnClickListener itemClickListener = v -> {
        Assessment assessment = (Assessment) v.getTag();
        assessmentId = assessment.getId();

        Bundle bundle = new Bundle();
        bundle.putInt(WguConst.ASSESSMENTS_LIST_ACTIVITY_TAG, assessmentId);
        Intent intent = new Intent(getActivity(), AssessmentDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assessments_list, container, false);

        setupRecyclerView(v);

        emptyView = v.findViewById(R.id.empty_view);

        fab = v.findViewById(R.id.fab_add);
        if(containsMainActivityKey()) { fab.setVisibility(View.GONE); }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(containsCourseDetailActivityKey()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(WguConst.COURSE_DETAIL_ACTIVITY_TAG, WguConst.COURSE_DETAIL_ACTIVITY_CODE);
                    bundle.putInt(WguConst.COURSE_ID_TAG, courseId);
                    Intent intent = new Intent(getContext(), AssessmentDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), AssessmentDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        WguApplication application = (WguApplication) getActivity().getApplication();
        assessmentsListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(AssessmentsListViewModel.class);

        if(containsCourseDetailActivityKey()) {
            assessmentsListViewModel.setCourseId(courseId);
            assessmentsListViewModel.getAssessmentsFromCourse(courseId).observe(this, assessments -> {
                Log.d(TAG, "Assessments Changed:" + assessments);
                adapter.setItems(assessments);
            });

        } else {
            assessmentsListViewModel.getAssessments().observe(this, assessments -> {

                if(assessmentsListViewModel.getAssessments().getValue().isEmpty() && containsMainActivityKey()) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }

                Log.d(TAG, "Assessments Changed:" + assessments);
                adapter.setItems(assessments);
            });
        }

        return v;
    }

    private void setupRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.rv_list_assessments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AssessmentsListAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private boolean containsMainActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.MAIN_ACTIVITY_TAG)) {
            return true;
        }
        return false;
    }

    private boolean containsCourseDetailActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.COURSE_DETAIL_ACTIVITY_TAG)) {
            courseId = extras.getInt(WguConst.COURSE_ID_TAG);
            return true;
        }
        return false;
    }

}