package ru.dimasokol.currencies.demo.core;

import java.util.List;

import ru.dimasokol.currencies.demo.core.ui.UIMessage;

/**
 * <p></p>
 *
 * @author sokol @ 29.06.16
 */
public class OperationResultEmpty implements OperationResult {

    public static OperationResultEmpty INSTANCE = new OperationResultEmpty();

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
        return false;
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
        return false;
    }

    @Override
    public boolean isContentReady() {
        return false;
    }
}
