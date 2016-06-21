package ru.dimasokol.currencies.demo.core;

/**
 * <p>Интерфейс чего-либо, хранящего ядро и фасады модулей</p>
 *
 * @author sokol
 */
public interface CoreHolder {

    Core getCore();
    ModuleFacade getFacade(String facadeId);

}
