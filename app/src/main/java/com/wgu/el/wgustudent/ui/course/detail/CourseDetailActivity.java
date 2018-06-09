package com.wgu.el.wgustudent.ui.course.detail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.ui.course.list.CoursesListActivity;

public class CourseDetailActivity extends AppCompatActivity {

    public static final String TAG = "CourseDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO add a better functionality to the back arrow button (for now all goes back
                // TODO to main screen or parent list activity). not implemented for this assignment
                Intent intent = new Intent(this, CoursesListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
