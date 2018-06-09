package com.wgu.el.wgustudent.ui.assessment.detail;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.database.AssessmentType;
import com.wgu.el.wgustudent.entity.Assessment;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.course.detail.CourseDetailViewModel;
import com.wgu.el.wgustudent.ui.dialogs.DateDialogFragment;
import com.wgu.el.wgustudent.util.WguAlarm;
import com.wgu.el.wgustudent.util.WguConst;

import java.util.Calendar;
import java.util.Date;

public class AssessmentDetailFragment extends LifecycleFragment implements DateDialogFragment.OnDateSetListener {

    private AssessmentDetailViewModel assessmentDetailViewModel;
    private CourseDetailViewModel courseDetailViewModel;
    private Assessment assessment;
    private AssessmentType assessmentType;
    private int assessmentId;
    private int courseId;
    private boolean reminder = false;

    private EditText editTextAssessmentName;
    private TextInputLayout textInputAssessmentDate;
    private EditText editTextAssessmentDate;
    private CheckBox checkBoxReminder;
    private Spinner spinnerAssessmentType;
    private TextView textViewAssociatedCourse;
    private FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assessment_detail, container, false);

        setupViews(view);
        setupClickListeners();
        setupViewModel();

        return view;
    }

    private void setupViewModel() {

        WguApplication wguApplication = (WguApplication) getActivity().getApplication();
        assessmentDetailViewModel = ViewModelProviders.of(this, new WguFactory(wguApplication)).get(AssessmentDetailViewModel.class);

        if (containsAssessmentsListActivityKey()) {
            getActivity().setTitle(R.string.edit_assessment);
            enableEditScreen(false);
            assessmentDetailViewModel.getAssessmentById(assessmentId).observe(this, assessment -> {
                enableEditScreen(true);
                this.assessment = assessment;
                populateViews(assessment);

                courseDetailViewModel = ViewModelProviders.of(this, new WguFactory(wguApplication)).get(CourseDetailViewModel.class);
                courseDetailViewModel.getCourseById(assessment.getCourseId()).observe(this, course -> {
                    courseDetailViewModel.setCourseName(course.getCourseName());
                    textViewAssociatedCourse.setText(courseDetailViewModel.getCourseName());
                });

            });
        } else {
            getActivity().setTitle(R.string.add_assessment);
            editTextAssessmentName.setText(assessmentDetailViewModel.getAssessmentName());
            if (containsCourseDetailActivityKey()) {
                assessmentDetailViewModel.setCourseId(courseId);
            }
        }

    }

    private void setupClickListeners() {

        editTextAssessmentName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                assessmentDetailViewModel.setAssessmentName(s.toString());
            }
        });

        editTextAssessmentDate.setOnClickListener(v -> {
            DateDialogFragment dateDialogFragment = new DateDialogFragment();
            dateDialogFragment.setOnDateSetListener(this, WguConst.ASSESSMENT_DATE);
            dateDialogFragment.show(getFragmentManager(), "DatePicker");
        });

        checkBoxReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                reminder = isChecked;
                assessmentDetailViewModel.setReminder(reminder);
                checkBoxReminder.setChecked(assessmentDetailViewModel.isReminder());

                if(reminder) {
                    scheduleNotification(getNotification(assessment.getAssessmentName() + " due", assessment.getAssessmentDate().getTime()), 5000);
                }

            }
        });

        spinnerAssessmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                assessmentType = (AssessmentType) parentView.getItemAtPosition(position);
                assessmentDetailViewModel.setAssessmentType(assessmentType);
                spinnerAssessmentType.setSelection(assessmentDetailViewModel.getAssessmentType().ordinal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        fab.setOnClickListener(v -> {
            if (isInputValid()) {
                if (containsAssessmentsListActivityKey()) {
                    assessmentDetailViewModel.updateAssessment(assessment);
                } else {
                    assessmentDetailViewModel.addAssessment();
                }
                getActivity().finish();
            }
        });
    }

    private void setupViews(View view) {
        editTextAssessmentName = view.findViewById(R.id.edit_text_assessment_name);
        textInputAssessmentDate = view.findViewById(R.id.text_input_assessment_date);
        editTextAssessmentDate = view.findViewById(R.id.edit_text_assessment_date);
        checkBoxReminder = view.findViewById(R.id.cb_assessment_reminder);

        spinnerAssessmentType = view.findViewById(R.id.spinner_assessment_type);
        spinnerAssessmentType.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, AssessmentType.values()));

        textViewAssociatedCourse = view.findViewById(R.id.tv_note_associated_course);

        fab = view.findViewById(R.id.fab_done);

    }

    @Override
    public void onDateSet(DatePicker dialog, int year, int monthOfYear, int dayOfMonth, int reqCode) {
        if (reqCode == WguConst.ASSESSMENT_DATE) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();
            assessmentDetailViewModel.setAssessmentDate(date);
            editTextAssessmentDate.setText(assessmentDetailViewModel.getAssessmentDate().toString());
        }
        // TODO Implement method for date picker to open calendar with set date
    }

    private void populateViews(Assessment assessment) {
        editTextAssessmentName.setText(assessment.getAssessmentName());
        editTextAssessmentDate.setText(assessment.getAssessmentDate().toString());
        checkBoxReminder.setChecked(assessment.isReminder());
        spinnerAssessmentType.setSelection(assessment.getAssessmentType().ordinal());

        assessmentDetailViewModel.setAssessmentName(assessment.getAssessmentName());
        assessmentDetailViewModel.setAssessmentDate(assessment.getAssessmentDate());
        assessmentDetailViewModel.setReminder(assessment.isReminder());
        assessmentDetailViewModel.setAssessmentType(assessment.getAssessmentType());
    }

    private void enableEditScreen(boolean b) {
        editTextAssessmentName.setVisibility(b ? View.VISIBLE : View.GONE);
        editTextAssessmentDate.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private boolean containsCourseDetailActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.COURSE_DETAIL_ACTIVITY_TAG)) {
            courseId = extras.getInt(WguConst.COURSE_ID_TAG);
            return true;
        }
        return false;
    }

    private boolean containsAssessmentsListActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(WguConst.ASSESSMENTS_LIST_ACTIVITY_TAG)) {
            assessmentId = extras.getInt(WguConst.ASSESSMENTS_LIST_ACTIVITY_TAG);
            return true;
        }
        return false;
    }

    /**
     * Validate inputs
     *
     * @return
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (TextUtils.isEmpty(editTextAssessmentName.getText())) {
            errorMessage += "No valid term name!\n";
        }

        if (TextUtils.isEmpty(editTextAssessmentDate.getText())) {
            errorMessage += "No valid assessment date!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Toast.makeText(getContext(), "Invalid fields: " + errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(getActivity(), WguAlarm.class);
        notificationIntent.putExtra(WguAlarm.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(WguAlarm.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String notificationText, long when) {

//        String notificationText = "Notification Text ...";
//        long when = System.currentTimeMillis();

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(getActivity(), Assessment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("WGU Notification")
            .setContentText(notificationText).setSound(alarmSound)
            .setAutoCancel(true).setWhen(when)
            .setLargeIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_launcher))
            .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
            .setContentIntent(pendingIntent);
        notificationManager.notify(5, builder.build());
        return builder.build();

    }

}
