package ru.dimasokol.currencies.demo.modules.currencies;

import android.net.Uri;

import java.util.Date;
import java.util.List;

import ru.dimasokol.currencies.demo.core.Core;
import ru.dimasokol.currencies.demo.core.ModuleFacade;
import ru.dimasokol.currencies.demo.core.OperationResult;

/**
 * <p>Фасад модуля загрузки списка валют</p>
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

    /**
     * Начинает загрузку валют или возвращает данные, хранящиеся в ядре.
     * @param date Дата для получения списка валют
     * @return Результат операции
     */
    public OperationResult getCurrencies(Date date) {
        return getCore().runTask(URI_CURRENCIES, new LoadCurrenciesRunner(date));
    }
}
