package firebasetest.ikram.com.firebasetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 10-07-2017.
 *
 * utility class
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    LayoutInflater inflator;
    ArrayList<String> mlist;
    ArrayList<Integer> mDrawableList;


    public CustomListAdapter(Context mcontext, int mlist_row, ArrayList<String> mlist) {
        super(mcontext, 0, mlist);

        inflator = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mlist = mlist;

        mDrawableList = new ArrayList<>();
        mDrawableList.add(R.drawable.add_in);
        mDrawableList.add(R.drawable.sell);
        mDrawableList.add(R.drawable.in_icon);
        mDrawableList.add(R.drawable.out_icon);
        mDrawableList.add(R.drawable.current_stock);
        mDrawableList.add(R.drawable.due_amount);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflator.inflate(R.layout.mlist_row, parent, false);
        }
        TextView list_item_text = (TextView)view.findViewById(R.id.list_text);
        list_item_text.setText(mlist.get(position));

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(mDrawableList.get(position));

        return view;
    }
}
