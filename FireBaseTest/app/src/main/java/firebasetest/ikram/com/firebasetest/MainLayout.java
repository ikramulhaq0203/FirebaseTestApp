package firebasetest.ikram.com.firebasetest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 28-03-2018.
 */

public class MainLayout extends LinearLayout {

    public MainLayout(Context context) {
        super(context);
    }

    public MainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
