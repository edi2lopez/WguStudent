package com.wgu.el.wgustudent.ui.course.detail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.database.Status;
import com.wgu.el.wgustudent.entity.Course;
import com.wgu.el.wgustudent.entity.Term;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.assessment.list.AssessmentsListActivity;
import com.wgu.el.wgustudent.ui.dialogs.DateDialogFragment;
import com.wgu.el.wgustudent.ui.note.list.NotesListActivity;
import com.wgu.el.wgustudent.ui.term.list.TermsListViewModel;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CourseDetailFragment extends LifecycleFragment implements DateDialogFragment.OnDateSetListener {

    private CourseDetailViewModel courseDetailViewModel;
    private TermsListViewModel termsListViewModel;
    private List<Term> termList = new ArrayList<>();
    private Course course;
    private int courseId;
    private Term term;
    private boolean reminder = false;
    private boolean isChecked;
    private Status status;

    private TextInputLayout textInputCourseStartDate;
    private TextInputLayout textInputCourseEndDate;
    private TextInputLayout textInputMentorName;
    private TextInputLayout textInputMentorEmail;
    private TextInputLayout textInputMentorPhone;

    private EditText editTextCourseName;
    private EditText editTextCourseStartDate;
    private EditText editTextCourseEndDate;
    private EditText editTextMentorName;
    private EditText editTextMentorEmail;
    private EditText editTextMentorPhone;

    private CheckBox checkBoxStartDateReminder;
    private CheckBox checkBoxEndDateReminder;

    private Spinner spinnerCourseStatus;
//    private Spinner spinnerTermList;

    private RadioGroup radioGroupTermsList;
    RadioButton termRadioButton;

    private Button buttonNotesList;
    private Button buttonAssessmentsList;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);

        setupViews(view);
        setupClickListeners();
        setupViewModel();

        return view;
    }

    private void setupViewModel() {

        WguApplication wguApplication = (WguApplication) getActivity().getApplication();
        courseDetailViewModel = ViewModelProviders.of(this, new WguFactory(wguApplication)).get(CourseDetailViewModel.class);

        if(  containsCoursesListActivityKey() || containsTermDetailActivityKey() ) {
            getActivity().setTitle(R.string.edit_course);
            enableEditScreen(false);
            courseDetailViewModel.getCourseById(courseId).observe(this, course -> {
                enableEditScreen(true);
                this.course = course;
                populateViews(course);
            });
        } else {
            getActivity().setTitle(R.string.add_course);
            editTextCourseName.setText(courseDetailViewModel.getCourseName());
            editTextMentorName.setText(courseDetailViewModel.getMentorName());
            editTextMentorEmail.setText(courseDetailViewModel.getMentorEmail());
            editTextMentorPhone.setText(courseDetailViewModel.getMentorPhone());
        }

    }

    private void setupClickListeners() {

        editTextCourseName.addTextChangedListener(new GenericTextWatcher(editTextCourseName));
        editTextMentorName.addTextChangedListener(new GenericTextWatcher(editTextMentorName));
        editTextMentorEmail.addTextChangedListener(new GenericTextWatcher(editTextMentorEmail));
        editTextMentorPhone.addTextChangedListener(new GenericTextWatcher(editTextMentorPhone));

        editTextCourseStartDate.setOnClickListener(v -> {
            DateDialogFragment dateDialogFragment = new DateDialogFragment();
            dateDialogFragment.setOnDateSetListener(this, WguConst.COURSE_START_DATE_TAG);
            dateDialogFragment.show(getFragmentManager(), "DatePicker");
        });

        editTextCourseEndDate.setOnClickListener(v -> {
            DateDialogFragment dateDialogFragment = new DateDialogFragment();
            dateDialogFragment.setOnDateSetListener(this, WguConst.COURSE_END_DATE_TAG);
            dateDialogFragment.show(getFragmentManager(), "DatePicker");
        });

        checkBoxStartDateReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                reminder = isChecked;
                courseDetailViewModel.setCourseStartDateReminder(reminder);
                checkBoxStartDateReminder.setChecked(courseDetailViewModel.isCourseStartDateReminder());
            }
        });

        checkBoxEndDateReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                reminder = isChecked;
                courseDetailViewModel.setCourseEndDateReminder(reminder);
                checkBoxEndDateReminder.setChecked(courseDetailViewModel.isCourseEndDateReminder());
            }
        });

        spinnerCourseStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                status = (Status) parentView.getItemAtPosition(position);
                courseDetailViewModel.setStatus(status);
                spinnerCourseStatus.setSelection(courseDetailViewModel.getStatus().ordinal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

//        spinnerTermList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//
//                term = (Term) parentView.getItemAtPosition(position);
//                int termId = term.getId();
//                courseDetailViewModel.setTermId(termId);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//
//            }
//
//        });

        radioGroupTermsList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = group.findViewById(checkedId);

                isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    courseDetailViewModel.setTermId(checkedId);
                    radioGroupTermsList.check(courseDetailViewModel.getTermId());
                }
            }
        });


        // View Notes associated with course
        buttonNotesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(WguConst.COURSE_ID_TAG, courseId);
                bundle.putInt(WguConst.COURSE_DETAIL_ACTIVITY_TAG, WguConst.COURSE_DETAIL_ACTIVITY_CODE);
                Intent intent = new Intent(getActivity(), NotesListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // View Assessments associated with course
        buttonAssessmentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(WguConst.COURSE_ID_TAG, courseId);
                bundle.putInt(WguConst.COURSE_DETAIL_ACTIVITY_TAG, WguConst.COURSE_DETAIL_ACTIVITY_CODE);
                Intent intent = new Intent(getActivity(), AssessmentsListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Save course
        fab.setOnClickListener(v -> {
            if(isInputValid()) {
                if (containsCoursesListActivityKey()) {
                    courseDetailViewModel.updateCourse(course);
                } else {
                    courseDetailViewModel.addCourse();
                }
                getActivity().finish();
            }
        });
    }

    private void setupViews(View view) {
        textInputCourseStartDate = view.findViewById(R.id.text_input_course_start_date);
        textInputCourseEndDate = view.findViewById(R.id.text_input_course_end_date);
        textInputMentorName = view.findViewById(R.id.text_input_mentor_name);
        textInputMentorEmail = view.findViewById(R.id.text_input_mentor_email);
        textInputMentorPhone = view.findViewById(R.id.text_input_mentor_phone);

        editTextCourseName = view.findViewById(R.id.edit_text_course_name);
        editTextCourseStartDate = view.findViewById(R.id.edit_text_course_start_date);
        editTextCourseEndDate = view.findViewById(R.id.edit_text_course_end_date);
        editTextMentorName = view.findViewById(R.id.edit_text_mentor_name);
        editTextMentorEmail = view.findViewById(R.id.edit_text_mentor_email);
        editTextMentorPhone = view.findViewById(R.id.edit_text_mentor_phone);

        checkBoxStartDateReminder = view.findViewById(R.id.cb_start_date_reminder);
        checkBoxEndDateReminder = view.findViewById(R.id.cb_end_date_reminder);

        spinnerCourseStatus = view.findViewById(R.id.spinner_course_status);
        spinnerCourseStatus.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, Status.values()));

//        spinnerTermList = view.findViewById(R.id.spinner_terms_list);
        radioGroupTermsList = view.findViewById(R.id.radio_group_terms_list);

        WguApplication application = (WguApplication) getActivity().getApplication();
        termsListViewModel = ViewModelProviders.of(this, new WguFactory(application)).get(TermsListViewModel.class);
        termsListViewModel.getTerms().observe(this, terms -> {

//            spinnerTermList.setAdapter(new ArrayAdapter<>(getActivity(),
//                    android.R.layout.simple_list_item_1, terms));
            // TODO Limit the number of terms, not implemented for this assignment
            termRadioButton = new RadioButton(getActivity());
            termList = terms;
            for(int i = 0; i < termList.size(); i++) {
                term = termList.get(i);
                termRadioButton = new RadioButton(getActivity());
                termRadioButton.setText(term.getTermName());
                termRadioButton.setId(term.getId());
                termRadioButton.setChecked(false);
                termRadioButton.setHorizontalFadingEdgeEnabled(true);
                radioGroupTermsList.addView(termRadioButton);
            }

        });

        buttonNotesList = view.findViewById(R.id.button_notes_list);
        buttonAssessmentsList = view.findViewById(R.id.button_assessments_list);

        buttonNotesList.setVisibility(View.GONE);
        buttonAssessmentsList.setVisibility(View.GONE);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.COURSES_LIST_ACTIVITY_TAG)) {
            buttonNotesList.setVisibility(View.VISIBLE);
            buttonAssessmentsList.setVisibility(View.VISIBLE);
        }

        fab = view.findViewById(R.id.fab_done);
    }

    private void populateViews(Course course) {
        editTextCourseName.setText(course.getCourseName());
        editTextCourseStartDate.setText(course.getCourseStartDate().toString());
        editTextCourseEndDate.setText(course.getCourseEndDate().toString());
        checkBoxStartDateReminder.setChecked(course.isCourseStartDateReminder());
        checkBoxEndDateReminder.setChecked(course.isCourseEndDateReminder());
        editTextMentorName.setText(course.getMentorName());
        editTextMentorEmail.setText(course.getMentorEmail());
        editTextMentorPhone.setText(course.getMentorPhone());
        spinnerCourseStatus.setSelection(course.getStatus().ordinal());
        radioGroupTermsList.check(course.getTermId());

        courseDetailViewModel.setCourseName(course.getCourseName());
        courseDetailViewModel.setCourseStartDate(course.getCourseStartDate());
        courseDetailViewModel.setCourseEndDate(course.getCourseEndDate());
        courseDetailViewModel.setCourseStartDateReminder(course.isCourseStartDateReminder());
        courseDetailViewModel.setCourseEndDateReminder(course.isCourseEndDateReminder());
        courseDetailViewModel.setMentorName(course.getMentorName());
        courseDetailViewModel.setMentorEmail(course.getMentorEmail());
        courseDetailViewModel.setMentorPhone(course.getMentorPhone());
        courseDetailViewModel.setStatus(course.getStatus());
        courseDetailViewModel.setTermId(course.getTermId());
    }

    private void enableEditScreen(boolean b) {
        editTextCourseName.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputCourseStartDate.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputCourseEndDate.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputMentorName.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputMentorEmail.setVisibility(b ? View.VISIBLE : View.GONE);
        textInputMentorPhone.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private boolean containsCoursesListActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.COURSES_LIST_ACTIVITY_TAG)) {
            courseId = extras.getInt(WguConst.COURSES_LIST_ACTIVITY_TAG);
            return true;
        }
        return false;
    }

    private boolean containsTermDetailActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && !extras.containsKey(WguConst.EMPTY_COURSE_ID_TAG) && extras.containsKey(WguConst.TERM_DETAIL_ACTIVITY_TAG)  ) {
            courseId = extras.getInt(WguConst.TERM_DETAIL_ACTIVITY_TAG);
            return true;
        }
        return false;
    }

    private boolean containsEmptyCourseIdTagKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.EMPTY_COURSE_ID_TAG)) {
            return true;
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker dialog, int year, int monthOfYear, int dayOfMonth, int reqCode) {
        if(reqCode == WguConst.COURSE_START_DATE_TAG) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();
            courseDetailViewModel.setCourseStartDate(date);
            editTextCourseStartDate.setText(courseDetailViewModel.getCourseStartDate().toString());
        } else if (reqCode == WguConst.COURSE_END_DATE_TAG) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();
            courseDetailViewModel.setCourseEndDate(date);
            editTextCourseEndDate.setText(courseDetailViewModel.getCourseEndDate().toString());
        }
        // TODO Implement method for date picker to open calendar with set date
    }

    /**
     * Single TextWatcher for multiple EditTexts
     * https://stackoverflow.com/questions/5702771/how-to-use-single-textwatcher-for-multiple-edittexts
     */
    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId()){
                case R.id.edit_text_course_name:
                    courseDetailViewModel.setCourseName(text);
                    break;
                case R.id.edit_text_mentor_name:
                    courseDetailViewModel.setMentorName(text);
                    break;
                case R.id.edit_text_mentor_email:
                    courseDetailViewModel.setMentorEmail(text);
                    break;
                case R.id.edit_text_mentor_phone:
                    courseDetailViewModel.setMentorPhone(text);
                    break;
            }
        }
    }

    /**
     * Validate inputs
     * @return
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (TextUtils.isEmpty(editTextCourseName.getText())) {
            errorMessage += "No valid curse name!\n";
        }

        if (TextUtils.isEmpty(editTextCourseStartDate.getText())) {
            errorMessage += "No valid course start date!\n";
        }

        if (TextUtils.isEmpty(editTextCourseEndDate.getText())) {
            errorMessage += "No valid course end date!\n";
        }

        if (!isChecked) {
            errorMessage += "Please check a term for this course!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Toast.makeText(getContext(), "Invalid fields: " + errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
