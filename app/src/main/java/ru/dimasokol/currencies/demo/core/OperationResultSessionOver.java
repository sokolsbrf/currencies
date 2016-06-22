package ru.dimasokol.currencies.demo.core;

import java.util.List;

import ru.dimasokol.currencies.demo.core.ui.UIMessage;

/**
 * <p>Результат операции для завершившейся сессии</p>
 *
 * @author sokol
 */
public class OperationResultSessionOver implements OperationResult {
    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void markAsDirty() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean isFailed() {
        return true;
    }

    @Override
    public void setLoading(boolean loading) {

    }

    @Override
    public void putMessage(UIMessage message) {

    }

    @Override
    public boolean hasMessages() {
        return false;
    }

    @Override
    public List<UIMessage> getMessages() {
        return null;
    }

    @Override
    public Content getContent() {
        return null;
    }

    @Override
    public void setContent(Content content) {

    }

    @Override
    public boolean isSessionOver() {
        return true;
    }

    @Override
    public boolean isContentReady() {
        return false;
    }
}
