package com.marko.notesandcourses;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    public static final String ORIGINAL_NOTE_COURSE_ID = "com.marko.notesandcourses.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_COURSE_TITLE = "com.marko.notesandcourses.ORIGINAL_NOTE_COURSE_TITLE";
    public static final String ORIGINAL_NOTE_COURSE_TEXT = "com.marko.notesandcourses.ORIGINAL_NOTE_COURSE_TEXT";


    public String mOriginalNoteCourseId;
    public String mMOriginalNoteTitle;
    public String mMOriginalNoteText;
    public boolean mIsNewlyCreated = true;

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE_ID, mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_COURSE_TITLE, mMOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_COURSE_TEXT, mMOriginalNoteText);
    }

    public void restorState(Bundle inState){
        mOriginalNoteCourseId = inState.getString(ORIGINAL_NOTE_COURSE_ID);
        mMOriginalNoteText = inState.getString(ORIGINAL_NOTE_COURSE_TEXT);
        mMOriginalNoteTitle = inState.getString(ORIGINAL_NOTE_COURSE_TITLE);
    }
}
