package ru.dimasokol.currencies.demo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Некая визуальная сущность, способная отображать сообщения пользователю. Каким образом это будет
 * делаться в каждом случае — решает сама эта сущность, в том числе в зависимости от контекста
 * сообщения.
 *
 * @author sokol @ 22.06.16
 */
public interface MessageVisualizer {

    /**
     * Показывает сообщение с указанным контекстом как ошибку.
     * @param errorMessage Сообщение об ошибке
     * @param contextTag Контекст; произвольная информация, дающая сообщению некий scope. Может быть
     *                   {@code null} при отсутствии контекста.
     */
    void showAsError(@NonNull String errorMessage, @Nullable String contextTag);

    /**
     * Показывает сообщение с указанным контекстом как предупреждение.
     * @param warningMessage Текст предупреждения
     * @param contextTag Контекст; произвольная информация, дающая сообщению некий scope. Может быть
     *                   {@code null} при отсутствии контекста.
     */
    void showAsWarning(@NonNull String warningMessage, @Nullable String contextTag);

    /**
     * Показывает сообщение с указанным контекстом как подсказку
     * @param hintMessage Текст подсказки
     * @param contextTag Контекст; произвольная информация, дающая сообщению некий scope. Может быть
     *                   {@code null} при отсутствии контекста.
     */
    void showAsHint(@NonNull String hintMessage, @Nullable String contextTag);

}
