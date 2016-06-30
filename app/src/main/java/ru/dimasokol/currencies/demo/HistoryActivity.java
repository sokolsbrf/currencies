package ru.dimasokol.currencies.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import java.util.Date;

import ru.dimasokol.currencies.demo.core.CoreHolder;
import ru.dimasokol.currencies.demo.modules.history.HistoryFacade;

public class HistoryActivity extends AppCompatActivity {

    private ImageView mTestImageView;

    private HistoryFacade mHistoryFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTestImageView = (ImageView) findViewById(R.id.imageView);
        ProgressToOkayDrawable drawable = new ProgressToOkayDrawable(mTestImageView, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, getResources().getDisplayMetrics()));
        mTestImageView.setImageDrawable(drawable);
        drawable.start();

        Log.d("History", "Started: " + getIntent().getData());

        mHistoryFacade = (HistoryFacade) ((CoreHolder) getApplication()).getFacade(HistoryFacade.NAME);

        if (getIntent().getData() != null && mHistoryFacade != null) {
            String currencyCode = getIntent().getData().getLastPathSegment();
            Date to = new Date();
            Date from = new Date(to.getTime() - (1000 * 60 * 60 * 24 * 7));

            mHistoryFacade.getCurrencyHistory(currencyCode, from, to);
        } else {
            finish();
        }
    }
}
