package ru.dimasokol.currencies.demo.currencies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.Date;
import java.util.List;

import ru.dimasokol.currencies.demo.core.Core;
import ru.dimasokol.currencies.demo.core.ModuleFacade;
import ru.dimasokol.currencies.demo.core.OperationResult;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public class CurrenciesFacade extends ModuleFacade {

    public static final String NAME = "currencies";

    public static final Uri URI_CURRENCIES = Uri.parse("sbol://ru.dimasokol.demo/currencies");

    public CurrenciesFacade(Core core) {
        super(core);
    }

    @Override
    public List<String> getSupportedUriTemplates() {
        return null;
    }

    public OperationResult getCurrencies(Date date) {
        return getCore().runTask(URI_CURRENCIES, new LoadCurrenciesRunner(date));
    }
}
