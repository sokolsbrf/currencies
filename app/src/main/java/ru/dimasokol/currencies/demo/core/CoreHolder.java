package ru.dimasokol.currencies.demo.core;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public interface CoreHolder {

    Core getCore();
    ModuleFacade getFacade(String facadeId);

}
