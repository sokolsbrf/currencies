package ru.dimasokol.currencies.demo.core.ui;

/**
 * <p>Сообщение с обычной подсказкой (серьёзность
 * {@link ru.dimasokol.currencies.demo.core.ui.UIMessage.Severity#Hint Severity.Hint})</p>
 *
 * @author sokol
 */
public class Hint extends UIMessage {

    public Hint(String message) {
        super(Severity.Hint, message);
    }

}
