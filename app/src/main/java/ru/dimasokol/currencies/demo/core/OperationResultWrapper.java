package ru.dimasokol.currencies.demo.core;

import java.util.List;

/**
 * <p></p>
 * <p>Добавлено: 21.06.16</p>
 *
 * @author sokol
 */
public final class OperationResultWrapper implements OperationResult {

    private OperationResult mSource;

    public OperationResultWrapper(OperationResult source) {
        mSource = source;
    }

    @Override
    public boolean isDirty() {
        return mSource.isDirty();
    }

    @Override
    public void setDirty(boolean dirty) {
        mSource.setDirty(dirty);
    }

    @Override
    public boolean isLoading() {
        return mSource.isLoading();
    }

    @Override
    public void setLoading(boolean loading) {
        mSource.setLoading(loading);
    }

    @Override
    public void putError(String error) {
        mSource.putError(error);
    }

    @Override
    public boolean hasErrors() {
        return mSource.hasErrors();
    }

    @Override
    public List<String> getErrors() {
        return mSource.getErrors();
    }

    @Override
    public Content getContent() {
        return mSource.getContent();
    }

    @Override
    public void setContent(Content content) {
        mSource.setContent(content);
    }

    @Override
    public boolean isSessionOver() {
        return mSource.isSessionOver();
    }

    @Override
    public boolean isContentReady() {
        return mSource.isContentReady();
    }
}
