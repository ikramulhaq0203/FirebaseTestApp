package firebasetest.ikram.com.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by lenovo on 20-02-2018.
 */
public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {

        Intent intent = new Intent();
        intent.putExtra("result", result.getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
