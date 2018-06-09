package com.wgu.el.wgustudent.ui.note.detail;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.el.wgustudent.R;
import com.wgu.el.wgustudent.entity.Note;
import com.wgu.el.wgustudent.injection.WguApplication;
import com.wgu.el.wgustudent.injection.WguFactory;
import com.wgu.el.wgustudent.ui.course.detail.CourseDetailViewModel;
import com.wgu.el.wgustudent.util.WguConst;
import com.wgu.el.wgustudent.util.WguPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.app.Activity.RESULT_OK;

public class NoteDetailFragment extends LifecycleFragment {

    private static final String TAG = "NoteDetailFragment";

    private NoteDetailViewModel noteDetailViewModel;
    private CourseDetailViewModel courseDetailViewModel;
    private Note note;
    private int noteId;
    private int courseId;
    private File photoFile;

    private EditText editTextNoteName;
    private EditText editTextNoteDetail;
    private TextView textViewAssociatedCourse;
    private TextView textViewNoteImageUri;
    private ImageView imageViewNotePicture;
    private Button buttonTakePicture;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);

        setupViews(view);
        setupClickListeners();
        setupViewModel();

        return view;
    }

    private void setupViewModel() {

        WguApplication wguApplication = (WguApplication) getActivity().getApplication();
        noteDetailViewModel = ViewModelProviders.of(this, new WguFactory(wguApplication)).get(NoteDetailViewModel.class);

        if (containsNotesListActivityKey()) {
            getActivity().setTitle(R.string.edit_note);
            enableEditScreen(false);
            noteDetailViewModel.getNoteById(noteId).observe(this, note -> {
                enableEditScreen(true);
                this.note = note;
                populateViews(note);

                courseDetailViewModel = ViewModelProviders.of(this, new WguFactory(wguApplication)).get(CourseDetailViewModel.class);
                courseDetailViewModel.getCourseById(note.getCourseId()).observe(this, course -> {
                    courseDetailViewModel.setCourseName(course.getCourseName());
                    textViewAssociatedCourse.setText(courseDetailViewModel.getCourseName());
                });

            });
        } else {
            getActivity().setTitle(R.string.add_note);
            editTextNoteName.setText(noteDetailViewModel.getNoteName());
            editTextNoteDetail.setText(noteDetailViewModel.getNoteDetail());
            if(containsCourseDetailActivityKey()){
                noteDetailViewModel.setCourseId(courseId);
            }
        }

    }

    private void setupClickListeners() {

        editTextNoteName.addTextChangedListener(new GenericTextWatcher(editTextNoteName));
        editTextNoteDetail.addTextChangedListener(new GenericTextWatcher(editTextNoteDetail));
        textViewNoteImageUri.addTextChangedListener(new GenericTextWatcher(textViewNoteImageUri));

        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                final CharSequence[] items = { "Take Photo", "Choose Photo", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        boolean result = WguPermissions.checkPermission(getActivity());
                        if (items[item].equals("Take Photo")) {
                            if(result) {
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, WguConst.REQUEST_IMAGE_CAPTURE);
                            }
                        } else if (items[item].equals("Choose Photo")) {
                            if(result) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , WguConst.REQUEST_IMAGE_GALLERY);
                            }
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });

                builder.show();
            }
        });

        fab.setOnClickListener(v -> {
            if(isInputValid()) {
                if (containsNotesListActivityKey()) {
                    noteDetailViewModel.updateNote(note);
                } else {
                    noteDetailViewModel.addNote();
                }
                getActivity().finish();
            }
        });
    }

    private void setupViews(View view) {

        editTextNoteName = view.findViewById(R.id.edit_text_note_name);
        editTextNoteDetail = view.findViewById(R.id.edit_text_note_detail);
        textViewAssociatedCourse = view.findViewById(R.id.tv_note_associated_course);

        imageViewNotePicture = view.findViewById(R.id.note_image);
        textViewNoteImageUri = view.findViewById(R.id.tv_note_image_uri);
//        textViewNoteImageUri.setVisibility(View.GONE);
        buttonTakePicture = view.findViewById(R.id.button_take_picture);

        fab = view.findViewById(R.id.fab_done);
    }

    private void populateViews(Note note) {
        editTextNoteName.setText(note.getNoteName());
        editTextNoteDetail.setText((note.getNoteDetail()));
        textViewNoteImageUri.setText(note.getNotePicture());
        imageViewNotePicture.setImageURI(Uri.parse(note.getNotePicture()));

        noteDetailViewModel.setNoteName(note.getNoteName());
        noteDetailViewModel.setNoteDetail(note.getNoteDetail());
        noteDetailViewModel.setNotePicture(note.getNotePicture());

    }

    private void enableEditScreen(boolean b) {
        editTextNoteName.setVisibility(b ? View.VISIBLE : View.GONE);
        editTextNoteDetail.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private boolean containsCourseDetailActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null && extras.containsKey(WguConst.COURSE_DETAIL_ACTIVITY_TAG)) {
            courseId = extras.getInt(WguConst.COURSE_ID_TAG);
            return true;
        }
        return false;
    }

    private boolean containsNotesListActivityKey() {
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null && extras.containsKey(WguConst.NOTES_LIST_ACTIVITY_TAG)) {
            noteId = extras.getInt(WguConst.NOTES_LIST_ACTIVITY_TAG);
            return true;
        }
        return false;
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
                case R.id.edit_text_note_name:
                    noteDetailViewModel.setNoteName(text);
                    break;
                case R.id.edit_text_note_detail:
                    noteDetailViewModel.setNoteDetail(text);
                    break;
                case R.id.tv_note_image_uri:
                    noteDetailViewModel.setNotePicture(text);
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

        if (TextUtils.isEmpty(editTextNoteName.getText())) {
            errorMessage += "No valid Note name!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Toast.makeText(getContext(), "Invalid fields: " + errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

    }

    // https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
    // https://stackoverflow.com/questions/15432592/get-file-path-of-image-on-android
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case WguConst.REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){

                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    Uri photoUri = getImageUri(getActivity().getApplicationContext(), photo);
                    photoFile = new File(getRealPathFromURI(photoUri));

                    noteDetailViewModel.setNotePicture(photoFile.toString());
                    textViewNoteImageUri.setText(noteDetailViewModel.getNotePicture());
                    imageViewNotePicture.setImageURI(Uri.parse(noteDetailViewModel.getNotePicture()));

                }

                break;
            case WguConst.REQUEST_IMAGE_GALLERY:
                if(resultCode == RESULT_OK){
                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    Uri photoUri = getImageUri(getActivity().getApplicationContext(), photo);
                    photoFile = new File(getRealPathFromURI(photoUri));

                    noteDetailViewModel.setNotePicture(photoFile.toString());
                    imageViewNotePicture.setImageURI(Uri.parse(noteDetailViewModel.getNotePicture()));
                }
                break;
        }

    }

    // https://stackoverflow.com/questions/15432592/get-file-path-of-image-on-android
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


}
