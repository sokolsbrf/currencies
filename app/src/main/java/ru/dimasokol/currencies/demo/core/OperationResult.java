package ru.dimasokol.currencies.demo.core;

import java.util.List;

/**
 * <p></p>
 * <p>Добавлено: 21.06.16</p>
 *
 * @author sokol
 */
public interface OperationResult {
    boolean isDirty();

    void setDirty(boolean dirty);

    boolean isLoading();

    void setLoading(boolean loading);

    void putError(String error);

    boolean hasErrors();

    List<String> getErrors();

    Content getContent();

    void setContent(Content content);

    boolean isSessionOver();

    boolean isContentReady();
}
