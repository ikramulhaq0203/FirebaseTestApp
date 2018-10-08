package firebasetest.ikram.com.firebasetest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import utils.UtilsClass;

public class PeriodsEditDialogFragment extends DialogFragment implements View.OnClickListener {

    public interface PeriodsEditDialogFragmentInterfaceListener {
        public void onDialogPositiveClick(DialogFragment dialog, String start_date, String end_date);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    EditText start_date, end_date;
    PeriodsEditDialogFragmentInterfaceListener mListner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.periods_dialog, null);

        start_date = (EditText) v.findViewById(R.id.start_date);
        end_date = (EditText) v.findViewById(R.id.end_date);

        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v)
                .setTitle("Select Period")
                .setMessage("Select start and end Date")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListner.onDialogPositiveClick(PeriodsEditDialogFragment.this, start_date.getText().toString(), end_date.getText().toString());
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListner.onDialogNegativeClick(PeriodsEditDialogFragment.this);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListner = (PeriodsEditDialogFragmentInterfaceListener) context;
    }

    @Override
    public void onClick(View v) {

        String mDate;
        switch (v.getId()) {
            case R.id.start_date:
               getTimeFromCalander(start_date);
                break;
            case R.id.end_date:
                getTimeFromCalander(end_date);
                break;
        }
    }

    public void getTimeFromCalander(final EditText editText) {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String day_format;
                String month_format;
                if (dayOfMonth < 10) {
                    day_format = "0" + String.valueOf(dayOfMonth);
                } else {
                    day_format = String.valueOf(dayOfMonth);
                }

                if (monthOfYear < 9) {
                    month_format = "0" + String.valueOf(monthOfYear+1);
                } else {
                    month_format = String.valueOf(monthOfYear+1);
                }

                final String date = String.valueOf(year) + "/" + month_format
                        + "/" + day_format;
                editText.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }
}
