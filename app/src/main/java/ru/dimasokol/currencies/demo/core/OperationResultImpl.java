package ru.dimasokol.currencies.demo.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.dimasokol.currencies.demo.core.ui.UIMessage;

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
    private List<UIMessage> mMessages;

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
    public void putMessage(UIMessage message) {
        if (mMessages == null)
            mMessages = new CopyOnWriteArrayList<>();

        if (mMessages.indexOf(message) < 0)
            mMessages.add(message);
    }

    @Override
    public synchronized boolean hasMessages() {
        return mMessages != null && mMessages.size() > 0;
    }

    @Override
    public synchronized List<UIMessage> getMessages() {
        if (mMessages == null)
            return null;

        return filterUnreadMessages();
    }

    @Override
    public synchronized Content getContent() {
        return mContent;
    }

    @Override
    public synchronized void setContent(Content content) {
        mContent = content;
        mMessages = null;
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

        if (mMessages == null) {
            builder.append(", mMessages = null");
        } else {
            builder.append(", mMessages.size() = ")
                    .append(mMessages.size());
        }

        builder.append("}");

        return builder.toString();
    }

    private List<UIMessage> filterUnreadMessages() {
        if (mMessages == null)
            return null;

        ArrayList<UIMessage> copy = new ArrayList<>(mMessages.size());

        for (UIMessage message: mMessages) {
            if (message.isConsidered())
                continue;

            copy.add(message);
        }

        return Collections.unmodifiableList(copy);
    }
}
