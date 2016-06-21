package ru.dimasokol.currencies.demo.core;

import java.util.List;

import ru.dimasokol.currencies.demo.core.ui.UIMessage;

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

    void putMessage(UIMessage message);

    boolean hasMessages();

    List<UIMessage> getMessages();

    Content getContent();

    void setContent(Content content);

    boolean isSessionOver();

    boolean isContentReady();
}
