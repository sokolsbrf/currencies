package ru.dimasokol.currencies.demo;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import ru.dimasokol.currencies.demo.core.Core;
import ru.dimasokol.currencies.demo.core.CoreHolder;
import ru.dimasokol.currencies.demo.core.ModuleFacade;
import ru.dimasokol.currencies.demo.modules.currencies.CurrenciesFacade;
import ru.dimasokol.currencies.demo.modules.history.HistoryFacade;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public class CurrenciesApplication extends Application implements CoreHolder {

    private Core mCore;
    private Map<String, ModuleFacade> mFacades = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();

        mCore = new Core(this);
        mCore.init();

        mFacades.clear();
        mFacades.put(CurrenciesFacade.NAME, new CurrenciesFacade(mCore));
        mFacades.put(HistoryFacade.NAME, new HistoryFacade(mCore));
    }

    @Override
    public Core getCore() {
        return mCore;
    }

    @Override
    public ModuleFacade getFacade(String facadeId) {
        return mFacades.get(facadeId);
    }
}
