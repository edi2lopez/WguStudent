package com.wgu.el.wgustudent.ui.note.list;


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
import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.note.detail.NoteDetailActivity;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.ArrayList;

public class NotesListFragment extends LifecycleFragment {

    private static final String TAG = "NotesListFragment";
    private NotesListAdapter adapter;
    private NotesListViewModel notesListViewModel;

    private int courseId;
    private int noteId;

    RecyclerView recyclerView;
    private TextView emptyView;
    FloatingActionButton fab;

    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Note note = (Note) view.getTag();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.delete_note_message);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    notesListViewModel.deleteNote(note);
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
            Note note = (Note) view.getTag();
            noteId = note.getId();

            Bundle bundle = new Bundle();
            bundle.putInt(WguConst.NOTES_LIST_ACTIVITY_TAG, noteId);
            Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes_list, container, false);
        getActivity().setTitle(R.string.note_title);

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
                    Intent intent = new Intent(getContext(), NoteDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), NoteDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        WguApplication application = (WguApplication) getActivity().getApplication();
        notesListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(NotesListViewModel.class);

        if(containsCourseDetailActivityKey()) {
            notesListViewModel.setCourseId(courseId);
            notesListViewModel.getNotesFromCourse(courseId).observe(this, notes -> {
                Log.d(TAG, "Notes Changed:" + notes);
                adapter.setItems(notes);
            });
            notesListViewModel.setCourseId(courseId);

        } else {
            notesListViewModel.getNotes().observe(this, notes -> {

                if(notesListViewModel.getNotes().getValue().isEmpty() && containsMainActivityKey()) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }

                Log.d(TAG, "Notes Changed:" + notes);
                adapter.setItems(notes);
            });

        }

        return v;
    }

    private void setupRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.rv_list_notes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesListAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);

        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    // TODO show associated course in list, not implemented for this assignment
//    private void setupRecyclerViewNoteCourse(View v) {
//        recyclerView = v.findViewById(R.id.rv_list_notes);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        notesWithCourseAdapter = new NotesWithCourseAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
//        recyclerView.setAdapter(notesWithCourseAdapter);
//
//        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
//    }

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
