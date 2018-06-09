package com.wgu.el.wgustudent.ui.course.list;


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
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.course.detail.CourseDetailActivity;
import com.wgu.el.wgustudent.ui.term.list.TermsListViewModel;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.ArrayList;

public class CoursesListFragment extends LifecycleFragment {

    public static final String TAG = "CoursesListFragment";
    private CoursesListAdapter adapter;
    private TermsListViewModel termsListViewModel;
    private CoursesListViewModel coursesListViewModel;

    RecyclerView recyclerView;
    private TextView emptyView;
    FloatingActionButton fab;

    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Course course = (Course) view.getTag();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.delete_course_message);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    coursesListViewModel.deleteCourse(course);
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
            Course course = (Course) view.getTag();
            int courseId = course.getId();

            Bundle bundle = new Bundle();
            bundle.putInt(WguConst.COURSES_LIST_ACTIVITY_TAG, courseId);
            Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_courses_list, container, false);
        getActivity().setTitle(R.string.course_title);

        setupRecyclerView(v);

        fab = v.findViewById(R.id.fab_add);
        fab.setOnClickListener(v1 -> startActivity(new Intent(getContext(), CourseDetailActivity.class)));

        WguApplication application = (WguApplication) getActivity().getApplication();
        coursesListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(CoursesListViewModel.class);

        coursesListViewModel.getCourses().observe(this, courses -> {
            Log.d(TAG, "Courses Changed:" + courses);
            adapter.setItems(courses);
        });

        termsListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(TermsListViewModel.class);
        termsListViewModel.getTerms().observe(this, terms -> {
            if(termsListViewModel.getTerms().getValue().isEmpty()) {
//                Toast.makeText(getContext(), "terms empty", Toast.LENGTH_LONG).show();
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        });

        return v;
    }

    private void setupRecyclerView(View v) {

        recyclerView = v.findViewById(R.id.rv_list_courses);
        emptyView = v.findViewById(R.id.empty_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CoursesListAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);

        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    
}
