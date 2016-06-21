package ru.dimasokol.currencies.demo.core;

import java.util.List;

/**
 * <p>Базовый фасад модуля приложения. Каждый фасад имеет ссылку на ядро и поддерживает несколько
 * URI контента, за загрузку которого на том ядре собственно и отвечает.</p>
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
