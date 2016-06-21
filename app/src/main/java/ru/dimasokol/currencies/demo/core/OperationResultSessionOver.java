package ru.dimasokol.currencies.demo.core;

import java.util.List;

/**
 * <p></p>
 * <p>Добавлено: 21.06.16</p>
 *
 * @author sokol
 */
public class OperationResultSessionOver implements OperationResult {
    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void setDirty(boolean dirty) {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public void setLoading(boolean loading) {

    }

    @Override
    public void putError(String error) {

    }

    @Override
    public boolean hasErrors() {
        return false;
    }

    @Override
    public List<String> getErrors() {
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
