package ru.dimasokol.currencies.demo;

import java.util.List;

import ru.dimasokol.currencies.demo.core.OperationResult;
import ru.dimasokol.currencies.demo.core.ui.UIMessage;

/**
 * Вспомогательный класс для самой общей обработки результатов операции {@link OperationResult}
 *
 * @author sokol @ 22.06.16
 */
public class OperationResultHelper {

    /**
     * Обрабатывает сообщения об ошибках, показывая диалог. Будут обработаны только ошибки
     * {@link UIMessage.Severity#Error} и только при отсутствии тега, т.е. никак не
     * специализированные.
     * @param operationResult Результат операции
     * @param visualizer Визуализатор сообщений пользователю
     * @return {@code true}, если обработаны все сообщения; {@code false} если остались непоказанные
     */
    public void handleCommonAlerts(OperationResult operationResult, MessageVisualizer visualizer) {
        if (operationResult == null || !operationResult.hasMessages())
            return;

        List<UIMessage> messages = operationResult.getMessages();

        StringBuilder errorPopup = new StringBuilder();
        StringBuilder warningPopup = new StringBuilder();
        StringBuilder hintPopup = new StringBuilder();

        for (UIMessage message: messages) {
            switch (message.getSeverity()) {
                case Hint:
                    handleMessageInternal(message, visualizer, hintPopup);
                    break;
                case Warning:
                    handleMessageInternal(message, visualizer, warningPopup);
                    break;
                case Error:
                    handleMessageInternal(message, visualizer, errorPopup);
                    break;
            }

        }

        if (errorPopup.length() > 0) {
            visualizer.showAsError(errorPopup.toString(), null);
        }

        if (warningPopup.length() > 0) {
            visualizer.showAsWarning(warningPopup.toString(), null);
        }

        if (hintPopup.length() > 0) {
            visualizer.showAsHint(hintPopup.toString(), null);
        }
    }

    public boolean handleSessionOver(OperationResult operationResult, MessageVisualizer visualizer) {
        return false;
    }

    private void handleMessageInternal(UIMessage message, MessageVisualizer visualizer, StringBuilder builder) {
        if (message.getTag() == null) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append(message.getText());
            message.markAsConsidered();
        } else {
            switch (message.getSeverity()) {
                case Error:
                    visualizer.showAsError(message.getText(), message.getTag());
                    break;
                case Warning:
                    visualizer.showAsWarning(message.getText(), message.getTag());
                    break;
                case Hint:
                    visualizer.showAsHint(message.getText(), message.getTag());
                    break;
            }
        }
    }
}
