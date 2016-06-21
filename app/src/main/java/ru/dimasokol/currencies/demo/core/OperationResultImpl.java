package ru.dimasokol.currencies.demo.core;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public final class OperationResultImpl implements OperationResult {

    private volatile boolean mDirty;
    private volatile boolean mLoading;
    private volatile boolean mSessionOver;
    private volatile Content mContent;
    private List<String> mErrors;

    private Task mTask;

    public OperationResultImpl(Task task) {
        mTask = task;
    }

    @Override
    public synchronized boolean isDirty() {
        return mDirty;
    }

    @Override
    public synchronized void setDirty(boolean dirty) {
        mDirty = dirty;

        if (dirty)
            mTask.markAsDirty();
    }

    @Override
    public synchronized boolean isLoading() {
        return mLoading;
    }

    @Override
    public synchronized void setLoading(boolean loading) {
        mLoading = loading;
    }

    @Override
    public synchronized void putError(String error) {
        if (mErrors == null)
            mErrors = new CopyOnWriteArrayList<>();

        if (mErrors.indexOf(error) < 0)
            mErrors.add(error);
    }

    @Override
    public synchronized boolean hasErrors() {
        return mErrors != null;
    }

    @Override
    public synchronized List<String> getErrors() {
        if (mErrors == null)
            return null;

        return Collections.unmodifiableList(mErrors);
    }

    @Override
    public synchronized Content getContent() {
        return mContent;
    }

    @Override
    public synchronized void setContent(Content content) {
        mContent = content;
        mErrors = null;
    }

    @Override
    public boolean isSessionOver() {
        return mSessionOver;
    }

    void setSessionOver(boolean sessionOver) {
        mSessionOver = sessionOver;
    }

    @Override
    public boolean isContentReady() {
        return getContent() != null && !isSessionOver();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("OperationResult ");
        builder
                .append(System.identityHashCode(this))
                .append(" {mDirty = ")
                .append(mDirty)
                .append(", mLoading = ")
                .append(mLoading)
                .append(", mSessionOver = ")
                .append(mSessionOver)
                .append(", mContent = ")
                .append(System.identityHashCode(mContent));

        if (mErrors == null) {
            builder.append(", mErrors = null");
        } else {
            builder.append(", mErrors.size() = ")
                    .append(mErrors.size());
        }

        builder.append("}");

        return builder.toString();
    }
}
