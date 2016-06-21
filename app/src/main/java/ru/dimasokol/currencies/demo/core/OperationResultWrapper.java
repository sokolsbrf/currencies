package ru.dimasokol.currencies.demo.core;

import java.util.List;

import ru.dimasokol.currencies.demo.core.ui.UIMessage;

/**
 * Обёртка для другого {@link OperationResult}, напрямую пробрасывающая все вызовы к нему. Этот
 * класс необходим потому, что стандартный LoaderManager требует именно изменения ссылки на контент,
 * а {@link Task} всегда возвращает один и тот же {@code OperationResult}.
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
    public void putMessage(UIMessage message) {
        mSource.putMessage(message);
    }

    @Override
    public boolean hasMessages() {
        return mSource.hasMessages();
    }

    @Override
    public List<UIMessage> getMessages() {
        return mSource.getMessages();
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
