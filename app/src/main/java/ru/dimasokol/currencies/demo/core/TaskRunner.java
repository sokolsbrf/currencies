package ru.dimasokol.currencies.demo.core;

import ru.dimasokol.currencies.demo.core.exceptions.RequestException;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public interface TaskRunner {

    Content execute() throws RequestException;

}
