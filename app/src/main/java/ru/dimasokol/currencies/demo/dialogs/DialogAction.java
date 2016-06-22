package ru.dimasokol.currencies.demo.dialogs;

import android.support.v4.app.DialogFragment;

import java.io.Serializable;

/**
 * Действие, выполняемое диалогом. Может быть сохранено в Bundle за счёт Serializable. Естественно,
 * реализации не должны препятствовать сериализации или хранить жёсткие ссылки.
 *
 * @author sokol @ 22.06.16
 */
public abstract class DialogAction implements Serializable {

    /**
     * Выполнить действие
     * @param instance Актуальный инстанс некого {@link DialogFragment}-а, для которого выполняется
     *                 действие.
     */
    public abstract void execute(DialogFragment instance);

}
