package ru.dimasokol.currencies.demo.core;

import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;

import ru.dimasokol.currencies.demo.core.exceptions.RequestException;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public final class Task implements Runnable {

    private final Uri mContentUri;
    private final Context mContext;

    private volatile boolean mRunning = false;
    private final OperationResult mOperationResult;
    private TaskRunner mTaskRunner;

    private WeakReference<Core> mCoreWeakReference;

    public Task(Core core, Uri contentUri, TaskRunner taskRunner) {
        mContentUri = contentUri;
        mContext = core.getContext();
        mOperationResult = new OperationResultImpl(this);
        mTaskRunner = taskRunner;

        mCoreWeakReference = new WeakReference<>(core);
    }

    public final OperationResult getOperationResult() {
        synchronized (mOperationResult) {
            return mOperationResult;
        }
    }

    public TaskRunner getTaskRunner() {
        return mTaskRunner;
    }

    @Override
    public void run() {
        // Невероятный случай, но всё же
        if (mRunning)
            return;

        try {
            mRunning = true;
            mOperationResult.setLoading(true);

            Content content = mTaskRunner.execute();
            mOperationResult.setContent(content);

        } catch (RequestException e) {
            mOperationResult.putError(e.getString(mContext));
        } finally {
            mRunning = false;
            mOperationResult.setLoading(false);
            mContext.getContentResolver().notifyChange(mContentUri, null);
        }
    }

    void markAsDirty() {
        Core core = mCoreWeakReference.get();

        if (core != null) {
            core.recallDirtyTask(mContentUri);
        }
    }
}
