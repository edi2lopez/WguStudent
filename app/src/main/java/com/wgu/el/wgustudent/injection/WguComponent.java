package com.wgu.el.wgustudent.injection;

import com.wgu.el.wgustudent.ui.assessment.detail.AssessmentDetailViewModel;
import com.wgu.el.wgustudent.ui.assessment.list.AssessmentsListViewModel;
import com.wgu.el.wgustudent.ui.course.detail.CourseDetailViewModel;
import com.wgu.el.wgustudent.ui.course.list.CoursesListViewModel;
import com.wgu.el.wgustudent.ui.note.detail.NoteDetailViewModel;
import com.wgu.el.wgustudent.ui.note.list.NotesListViewModel;
import com.wgu.el.wgustudent.ui.term.detail.TermDetailViewModel;
import com.wgu.el.wgustudent.ui.term.list.TermsListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {WguModule.class})
public interface WguComponent {

    void inject(AssessmentsListViewModel assessmentsListViewModel);
    void inject(AssessmentDetailViewModel assessmentDetailViewModel);

    void inject(CoursesListViewModel coursesListViewModel);
    void inject(CourseDetailViewModel courseDetailViewModel);

    void inject(NotesListViewModel notesListViewModel);
    void inject(NoteDetailViewModel noteDetailViewModel);

    void inject(TermsListViewModel termsListViewModel);
    void inject(TermDetailViewModel termDetailViewModel);

    interface Injectable {
        void inject(WguComponent wguComponent);
    }

}
