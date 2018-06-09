package com.wgu.el.wgustudent.ui.term.detail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.course.detail.CourseDetailActivity;
import com.wgu.el.wgustudent.ui.course.list.CoursesListActivity;
import com.wgu.el.wgustudent.ui.course.list.CoursesListViewModel;
import com.wgu.el.wgustudent.ui.dialogs.DateDialogFragment;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TermDetailFragment extends LifecycleFragment implements DateDialogFragment.OnDateSetListener {

    private static final String TAG = "TermDetailFragment";
    private TermCoursesListAdapter adapter;

    private TermDetailViewModel termDetailViewModel;
    private CoursesListViewModel coursesListViewModel;
    private Term term;
    private int termId;
    private Calendar calendar;
    private Date date;

    private TextInputLayout textInputTermStartDate;
    private TextInputLayout textInputTermEndDate;
    private EditText editTextTermName;
    private EditText editTextTermStartDate;
    private EditText editTextTermEndDate;
    private Button buttonViewCourses;
    private Button buttonAddCourse;
    private FloatingActionButton fab;

    View.OnClickListener deleteClickListener;
    View.OnClickListener itemClickListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_detail, container, false);

        setupViews(view);
        setupClickListeners();
        setupRecyclerView(view);
        setupViewModel();

        return view;
    }

    private void setupViewModel() {

        WguApplication wguApplication = (WguApplication) getActivity().getApplication();
        termDetailViewModel = ViewModelProviders.of(this, new WguFactory(wguApplication)).get(TermDetailViewModel.class);

        if (containsTermsListActivityKey()) {
            getActivity().setTitle(R.string.edit_term);
            enableEditScreen(false);
            termDetailViewModel.getTermById(termId).observe(this, term -> {
                enableEditScreen(true);
                this.term = term;
                populateViews(term);
            });
        } else {
            getActivity().setTitle(R.string.add_term);
            editTextTermName.setText(termDetailViewModel.getTermName());
        }

        WguApplication application = (WguApplication) getActivity().getApplication();
        coursesListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(CoursesListViewModel.class);

        if (containsTermsListActivityKey()) {
            coursesListViewModel.setTermId(termId);
            coursesListViewModel.getCoursesFromTerm(termId).observe(this, courses -> {
                Log.d(TAG, "Courses Changed:" + courses);
                adapter.setItems(courses);
            });
        }

    }

    private void setupClickListeners() {

        editTextTermName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                termDetailViewModel.setTermName(s.toString());
            }
        });

        editTextTermStartDate.setOnClickListener(v -> {
            DateDialogFragment dateDialogFragment = new DateDialogFragment();
            dateDialogFragment.setOnDateSetListener(this, WguConst.TERM_START_DATE_TAG);
            dateDialogFragment.show(getFragmentManager(), "DatePicker");
        });

        editTextTermEndDate.setOnClickListener(v -> {
            DateDialogFragment dateDialogFragment = new DateDialogFragment();
            dateDialogFragment.setOnDateSetListener(this, WguConst.TERM_END_DATE_TAG);
            dateDialogFragment.show(getFragmentManager(), "DatePicker");
        });

        buttonViewCourses.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity(), CoursesListActivity.class);
            startActivity(intent);
        });

        buttonAddCourse.setOnClickListener( v -> {
            int termId = term.getId();

            Bundle bundle = new Bundle();
            bundle.putInt(WguConst.TERM_DETAIL_ACTIVITY_TAG, termId);
            bundle.putString(WguConst.EMPTY_COURSE_ID_TAG, WguConst.EMPTY_COURSE_ID_TAG);
            Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // Associated Courses
        deleteClickListener = new View.OnClickListener() {
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

        itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course course = (Course) view.getTag();
                int courseId = course.getId();

                Bundle bundle = new Bundle();
                bundle.putInt(WguConst.TERM_DETAIL_ACTIVITY_TAG, courseId);
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        fab.setOnClickListener(v -> {
            if(isInputValid()) {
                if (containsTermsListActivityKey()) {
                    termDetailViewModel.updateTerm(term);
                } else {
                    termDetailViewModel.addTerm();
                }
                getActivity().finish();
            }
        });

    }

    private void setupViews(View view) {
        textInputTermStartDate = view.findViewById(R.id.text_input_term_start_date);
        textInputTermEndDate = view.findViewById(R.id.text_input_term_end_date);

        editTextTermName = view.findViewById(R.id.edit_text_term_name);
        editTextTermStartDate = view.findViewById(R.id.edit_text_term_start_date);
        editTextTermEndDate = view.findViewById(R.id.edit_text_term_end_date);

        buttonViewCourses = view.findViewById(R.id.button_courses_list);
        buttonAddCourse = view.findViewById(R.id.button_add_course);

        if(!containsTermsListActivityKey()) {
            buttonViewCourses.setVisibility(View.GONE);
            buttonAddCourse.setVisibility(View.GONE);
        }

        fab = view.findViewById(R.id.fab_done);
    }

    private void populateViews(Term term) {
        editTextTermName.setText(term.getTermName());
        editTextTermStartDate.setText(term.getTermStartDate().toString());
        editTextTermEndDate.setText(term.getTermEndDate().toString());
        // TODO change the date format
//        editTextTermStartDate.setText(Utils.getDateFormatted(term.getTermStartDate()));
//        editTextTermEndDate.setText(Utils.getDateFormatted(term.getTermEndDate()));

        termDetailViewModel.setTermName(term.getTermName());
        termDetailViewModel.setTermStartDate(term.getTermStartDate());
        termDetailViewModel.setTermEndDate(term.getTermEndDate());

    }

    private void enableEditScreen(boolean b) {
        editTextTermName.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputTermStartDate.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputTermEndDate.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private boolean containsTermsListActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.TERMS_LIST_ACTIVITY_TAG)) {
            termId = extras.getInt(WguConst.TERMS_LIST_ACTIVITY_TAG);
            return true;
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker dialog, int year, int monthOfYear, int dayOfMonth, int reqCode) {
        if(reqCode == WguConst.TERM_START_DATE_TAG) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date = calendar.getTime();
            termDetailViewModel.setTermStartDate(date);
            editTextTermStartDate.setText(termDetailViewModel.getTermStartDate().toString());
//            editTextTermStartDate.setText(Utils.getDateFormatted(termDetailViewModel.getTermStartDate()));
        } else if (reqCode == WguConst.TERM_END_DATE_TAG) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date = calendar.getTime();
            termDetailViewModel.setTermEndDate(date);
            editTextTermEndDate.setText(termDetailViewModel.getTermEndDate().toString());
//            editTextTermEndDate.setText(Utils.getDateFormatted(termDetailViewModel.getTermEndDate()));
        }

    }

    /**
     * Validate inputs
     * @return
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (TextUtils.isEmpty(editTextTermName.getText())) {
            errorMessage += "No valid term name!\n";
        }

        if (TextUtils.isEmpty(editTextTermStartDate.getText())) {
            errorMessage += "No valid term start date!\n";
        }

        if (TextUtils.isEmpty(editTextTermEndDate.getText())) {
            errorMessage += "No valid term end date!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Toast.makeText(getContext(), "Invalid fields: " + errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

    }

    /**
     * Set up associated courses list
     */
    private void setupRecyclerView(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.rv_courses_list_by_term);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TermCoursesListAdapter(new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recyclerView.setAdapter(adapter);

        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
