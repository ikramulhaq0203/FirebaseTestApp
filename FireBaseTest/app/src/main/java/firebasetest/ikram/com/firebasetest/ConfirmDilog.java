package firebasetest.ikram.com.firebasetest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class ConfirmDilog extends DialogFragment{


    interface ConfirmDilogInterface {
        public void onConfirmDialogPositiveClick(DialogFragment dialog);
        public void onConfirmDialogNegativeClick(DialogFragment dialog);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //builder.setMessage("Do you really want make payment")
        return null;
    }

    ConfirmDilogInterface mlistner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mlistner = (ConfirmDilogInterface) context;
    }
}
