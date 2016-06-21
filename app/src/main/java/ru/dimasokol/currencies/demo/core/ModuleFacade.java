package ru.dimasokol.currencies.demo.core;

import java.util.List;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public abstract class ModuleFacade {

    private final Core mCore;

    public ModuleFacade(Core core) {
        mCore = core;
    }

    public Core getCore() {
        return mCore;
    }

    public abstract List<String> getSupportedUriTemplates();

}
