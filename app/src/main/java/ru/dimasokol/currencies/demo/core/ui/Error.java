package ru.dimasokol.currencies.demo.core.ui;

/**
 * <p>Сообщение об ошибке (серьёзность
 * {@link ru.dimasokol.currencies.demo.core.ui.UIMessage.Severity#Error Severity.Error})</p>
 *
 * @author sokol
 */
public class Error extends UIMessage {

    public Error(String message) {
        super(Severity.Error, message);
    }

}
