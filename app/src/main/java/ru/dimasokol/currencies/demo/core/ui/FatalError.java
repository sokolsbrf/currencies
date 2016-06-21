package ru.dimasokol.currencies.demo.core.ui;

/**
 * <p>Сообщение о критической ошибке (серьёзность
 * {@link ru.dimasokol.currencies.demo.core.ui.UIMessage.Severity#Fatal Severity.Fatal})</p>
 * <p>Важная особенность этого сообщения: {@link #isConsidered()} всегда возвращает
 * {@code false}, т.е. данное сообщение нельзя пометить как прочтённое. В этом и суть
 * фатальности ошибки: игнорировать её просто невозможно.</p>
 *
 * @author sokol @ 22.06.16
 */
public class FatalError extends UIMessage {
    /**
     * Создание нового экземпляра сообщения
     *
     * @param message  Текст сообщения
     */
    public FatalError(String message) {
        super(Severity.Fatal, message);
    }

    @Override
    public boolean isConsidered() {
        return false;
    }
}
