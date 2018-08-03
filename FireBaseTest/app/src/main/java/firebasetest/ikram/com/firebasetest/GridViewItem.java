package firebasetest.ikram.com.firebasetest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by lenovo on 28-03-2018.
 */

public class GridViewItem extends GridView {

    public GridViewItem(Context context) {
        super(context);
    }

    public GridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
