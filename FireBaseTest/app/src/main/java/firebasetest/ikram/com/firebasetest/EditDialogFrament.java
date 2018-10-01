package firebasetest.ikram.com.firebasetest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditDialogFrament extends DialogFragment{


    public interface EditDialogFramentInterfaceListener {
        public void onDialogPositiveClick(DialogFragment dialog, String payment);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.payment_file, null);

        final EditText payment_amount = (EditText) v.findViewById(R.id.payment_edit_text);
                builder.setView(v)
                .setPositiveButton("paid", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListner.onDialogPositiveClick(EditDialogFrament.this, payment_amount.getText().toString());
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListner.onDialogNegativeClick(EditDialogFrament.this);
                    }
                })
                .setTitle("Payment");

                return builder.create();
    }


    EditDialogFramentInterfaceListener mListner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListner = (EditDialogFramentInterfaceListener) context;
    }
}
