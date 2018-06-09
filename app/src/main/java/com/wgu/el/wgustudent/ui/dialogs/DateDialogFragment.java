package com.wgu.el.wgustudent.ui.dialogs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**
     * Adapted from How to achieve multiple datepicker functionality with two buttons
     * https://stackoverflow.com/questions/30431250/how-to-achieve-multiple-datepicker-functionality-with-two-buttons-and-save-those
     */

    public interface OnDateSetListener {

        /**
         * @param dialog The view associated with this listener.
         * @param year The year that was set.
         * @param monthOfYear The monthOfYear that was set.
         * @param dayOfMonth The dayOfMonth that was set.
         */
        void onDateSet(DatePicker dialog, int year, int monthOfYear, int dayOfMonth, int reqCode);
    }

    private OnDateSetListener mListener;
    private int reqCode;
    public void setOnDateSetListener(OnDateSetListener mListener, int reqCode){
        this.mListener  = mListener;
        this.reqCode = reqCode;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year, month, day;
        final Calendar c = Calendar.getInstance();
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            // Use the given date as the default date in the picker
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else{
            // Use the current date as the default date in the picker
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (mListener != null)
            mListener.onDateSet(view, year, monthOfYear, dayOfMonth, reqCode);
        this.dismiss();
    }

}
