package ru.dimasokol.currencies.demo;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

import ru.dimasokol.currencies.demo.core.CoreHolder;
import ru.dimasokol.currencies.demo.core.OperationResult;
import ru.dimasokol.currencies.demo.core.OperationResultWrapper;
import ru.dimasokol.currencies.demo.dialogs.AlertDialogFragment;
import ru.dimasokol.currencies.demo.dialogs.DialogAction;
import ru.dimasokol.currencies.demo.modules.currencies.CurrenciesFacade;
import ru.dimasokol.currencies.demo.modules.currencies.LoadCurrenciesContent;
import ru.dimasokol.currencies.demo.modules.currencies.parsers.CurrenciesList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<OperationResult>,
        MessageVisualizer {

    CurrenciesFacade mCurrenciesFacade;
    private RecyclerView mCurrenciesRecyclerView;
    private View mProgressView;
    private View mRetryButton;

    private final Queue<String> mPendingErrorMessages = new ArrayDeque<>();
    private final Queue<String> mPendingWarningMessages = new ArrayDeque<>();
    private final Queue<String> mPendingHints = new ArrayDeque<>();

    private OperationResultHelper mResultHelper = new OperationResultHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrenciesRecyclerView = (RecyclerView) findViewById(R.id.recycler_currencies);
        mProgressView = findViewById(R.id.progress);
        mRetryButton = findViewById(R.id.button_retry);

        mCurrenciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showProgress(true);

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationResult result = mCurrenciesFacade.getCurrencies(new Date());
                if (result.isFailed()) {
                    result.markAsDirty();
                }

                showRetry(false);
                showProgress(true);
            }
        });

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

    @Override
    public void showAsError(@NonNull String errorMessage, @Nullable String contextTag) {
        if (contextTag == null) {
            showNotTaggedMessageInternal(errorMessage, R.string.dialog_title_error, mPendingErrorMessages);
        }
    }

    @Override
    public void showAsWarning(@NonNull String warningMessage, @Nullable String contextTag) {
        if (contextTag == null) {
            showNotTaggedMessageInternal(warningMessage, R.string.dialog_title_warning, mPendingWarningMessages);
        }
    }

    @Override
    public void showAsHint(@NonNull String hintMessage, @Nullable String contextTag) {
        if (contextTag == null) {
            showNotTaggedMessageInternal(hintMessage, 0, mPendingHints);
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        showNextPendingMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAbout() {
        AlertDialogFragment aboutDialog = AlertDialogFragment.newInstance(getString(R.string.message_about),
                R.string.dialog_title_about, null);
        aboutDialog.show(getSupportFragmentManager(), AlertDialogFragment.TAG);
    }

    private void showNextPendingMessage() {
        showAsError(mPendingErrorMessages.poll(), null);
        showAsWarning(mPendingWarningMessages.poll(), null);
        showAsHint(mPendingHints.poll(), null);
    }


    private void displayResult(OperationResult result) {
        showProgress(result.isLoading());

        mResultHelper.handleSessionOver(result, this);
        mResultHelper.handleCommonAlerts(result, this);

        if (result.isContentReady()) {
            CurrenciesList currenciesList = ((LoadCurrenciesContent) result.getContent()).get();
            if (currenciesList != null) {
                showProgress(false);
                showRetry(false);
                mCurrenciesRecyclerView.setAdapter(new CurrenciesListAdapter(currenciesList));
            }
        }

        if (result.isFailed()) {
            showRetry(true);
        }
    }

    private void showProgress(boolean progress) {
        mProgressView.setVisibility(progress? View.VISIBLE : View.INVISIBLE);
        mCurrenciesRecyclerView.setEnabled(!progress);
    }

    private void showRetry(boolean show) {
        mRetryButton.setVisibility(show? View.VISIBLE : View.INVISIBLE);

        if (show)
            showProgress(false);
    }

    private void showNotTaggedMessageInternal(final String message, @StringRes final int titleRes, final Queue<String> queue) {
        if (message == null)
            return;

        mProgressView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (getSupportFragmentManager().findFragmentByTag(AlertDialogFragment.TAG) != null)
                        throw new IllegalStateException("Dialog is already shown");

                    AlertDialogFragment fragment = AlertDialogFragment.newInstance(message, titleRes, new NextMessageRunner());
                    fragment.show(getSupportFragmentManager(), AlertDialogFragment.TAG);
                } catch (IllegalStateException stateLossOrDialogExists) {
                    if (!queue.contains(message))
                        queue.offer(message);
                }
            }
        });
    }

    /**
     * Синхронный загрузчик списка валют
     */
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

    /**
     * Отображает следующее ожидающее сообщение после сокрытия предыдущего
     */
    public static class NextMessageRunner extends DialogAction {
        @Override
        public void execute(DialogFragment instance) {
            FragmentActivity activity = instance.getActivity();

            if (activity instanceof MainActivity) {
                ((MainActivity) activity).showNextPendingMessage();
            }
        }
    }
}
