package ru.dimasokol.currencies.demo;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Date;

import ru.dimasokol.currencies.demo.core.CoreHolder;
import ru.dimasokol.currencies.demo.core.OperationResult;
import ru.dimasokol.currencies.demo.core.OperationResultImpl;
import ru.dimasokol.currencies.demo.core.OperationResultWrapper;
import ru.dimasokol.currencies.demo.currencies.CurrenciesFacade;
import ru.dimasokol.currencies.demo.currencies.LoadCurrenciesContent;
import ru.dimasokol.currencies.demo.currencies.parsers.CurrenciesList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<OperationResult> {

    CurrenciesFacade mCurrenciesFacade;
    private RecyclerView mCurrenciesRecyclerView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrenciesRecyclerView = (RecyclerView) findViewById(R.id.recycler_currencies);
        mProgressView = findViewById(R.id.progress);

        mCurrenciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showProgress(true);

        mCurrenciesFacade = (CurrenciesFacade) ((CoreHolder) getApplication()).getFacade(CurrenciesFacade.NAME);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<OperationResult> onCreateLoader(int id, Bundle args) {
        return new CurrenciesLoader(this, mCurrenciesFacade);
    }

    @Override
    public void onLoadFinished(Loader<OperationResult> loader, OperationResult data) {
        displayResult(data);
    }

    @Override
    public void onLoaderReset(Loader<OperationResult> loader) {

    }

    private void displayResult(OperationResult result) {
        Log.d("Bloody", result.toString());

        if (result.isContentReady()) {
            CurrenciesList currenciesList = ((LoadCurrenciesContent) result.getContent()).get();
            if (currenciesList != null) {
                showProgress(false);
                mCurrenciesRecyclerView.setAdapter(new CurrenciesListAdapter(currenciesList));
            }
        } else {
            showProgress(true);
            // TODO: ошибки и завершение сессии
        }
    }

    private void showProgress(boolean progress) {
        mProgressView.setVisibility(progress? View.VISIBLE : View.INVISIBLE);
        mCurrenciesRecyclerView.setEnabled(!progress);
    }

    public static class CurrenciesLoader extends Loader<OperationResult> {

        private final ContentObserver mObserver;
        private CurrenciesFacade mCurrenciesFacade;
        private OperationResult mLastResult;

        public CurrenciesLoader(Context context, CurrenciesFacade currenciesFacade) {
            super(context);
            mCurrenciesFacade = currenciesFacade;
            mObserver = new ForceLoadContentObserver();

            getContext().getContentResolver().registerContentObserver(CurrenciesFacade.URI_CURRENCIES, true, mObserver);
        }

        @Override
        protected void onStartLoading() {
            if (takeContentChanged() || mLastResult == null) {
                forceLoad();
            }
        }

        @Override
        public void onContentChanged() {
            super.onContentChanged();
        }

        @Override
        protected void onForceLoad() {
            OperationResult result = new OperationResultWrapper(mCurrenciesFacade.getCurrencies(new Date()));
            mLastResult = result;
            deliverResult(result);
        }

        @Override
        protected void onAbandon() {
            super.onAbandon();
            getContext().getContentResolver().unregisterContentObserver(mObserver);
        }
    }
}
