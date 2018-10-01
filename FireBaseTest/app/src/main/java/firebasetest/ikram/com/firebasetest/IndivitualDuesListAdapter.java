package firebasetest.ikram.com.firebasetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 26-03-2018.
 */
public class IndivitualDuesListAdapter extends ArrayAdapter<DuesUtilsClass> {
    LayoutInflater inflator;
    ArrayList<DuesUtilsClass> duesUtilsClass;


    public IndivitualDuesListAdapter(Context mcontext, int row, ArrayList<DuesUtilsClass> arrayList) {
        super(mcontext, 0, arrayList);
        inflator = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        duesUtilsClass = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View view = convertView;
        if (view == null) {
            view = inflator.inflate(R.layout.indivitual_dues_row, parent, false);
        }
        TextView list_payment_date = (TextView)view.findViewById(R.id.list_payment_date);
        TextView list_payment_amount = (TextView)view.findViewById(R.id.list_payment_amount);
        TextView list_due_amount = (TextView)view.findViewById(R.id.list_due_amount);

        list_payment_date.setText(duesUtilsClass.get(position).getLastUpdate().substring(0, 10));
        list_payment_amount.setText(duesUtilsClass.get(position).getPaidAmount());
        list_due_amount.setText(duesUtilsClass.get(position).getDueAmount());
        return view;
    }
}

