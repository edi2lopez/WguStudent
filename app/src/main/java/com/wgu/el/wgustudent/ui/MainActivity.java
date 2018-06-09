package com.wgu.el.wgustudent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.ui.assessment.list.AssessmentsListActivity;
import com.wgu.el.wgustudent.ui.course.list.CoursesListActivity;
import com.wgu.el.wgustudent.ui.note.list.NotesListActivity;
import com.wgu.el.wgustudent.ui.term.list.TermsListActivity;
import com.wgu.el.wgustudent.util.WguConst;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void termsClickHandler(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(WguConst.MAIN_ACTIVITY_TAG, WguConst.MAIN_ACTIVITY_CODE);
        Intent intent = new Intent(this, TermsListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void coursesClickHandler(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(WguConst.MAIN_ACTIVITY_TAG, WguConst.MAIN_ACTIVITY_CODE);
        Intent intent = new Intent(this, CoursesListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void assessmentsClickHandler(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(WguConst.MAIN_ACTIVITY_TAG, WguConst.MAIN_ACTIVITY_CODE);
        Intent intent = new Intent(this, AssessmentsListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void notesClickHandler(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(WguConst.MAIN_ACTIVITY_TAG, WguConst.MAIN_ACTIVITY_CODE);
        Intent intent = new Intent(this, NotesListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
