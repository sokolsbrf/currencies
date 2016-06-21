package ru.dimasokol.currencies.demo.core;

import ru.dimasokol.currencies.demo.core.exceptions.RequestException;

/**
 * <p>«Исполнитель задачи»: своеобразный Runnable для {@link Task}, но бросающий
 * специализированный {@link RequestException}.</p>
 *
 * @author sokol
 */
public interface TaskRunner {

    /**
     * Выполняет задачу и возвращает загруженный контент, либо бросает исключение
     * @throws RequestException
     */
    Content execute() throws RequestException;

}
