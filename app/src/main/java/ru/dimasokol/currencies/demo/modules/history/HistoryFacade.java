package ru.dimasokol.currencies.demo.modules.history;

import android.net.Uri;

import java.util.Date;
import java.util.List;

import ru.dimasokol.currencies.demo.core.Core;
import ru.dimasokol.currencies.demo.core.ModuleFacade;
import ru.dimasokol.currencies.demo.core.OperationResult;

/**
 * <p></p>
 * <p>Добавлено: 14.06.16</p>
 *
 * @author sokol
 */
public class HistoryFacade extends ModuleFacade {

    public static final String NAME = "history";
    private static final String FORMAT_URI_STRING = "sbol://ru.dimasokol.demo/history";

    public static final Uri URI_ALL_HISTORIES = Uri.parse(FORMAT_URI_STRING);

    public HistoryFacade(Core core) {
        super(core);
    }

    @Override
    public List<String> getSupportedUriTemplates() {
        return null;
    }

    public OperationResult getCurrencyHistory(String currencyCode, Date from, Date to) {
        Uri uri = Uri.withAppendedPath(URI_ALL_HISTORIES, currencyCode);
        return getCore().runTask(uri, new LoadHistoryRunner(from, to, currencyCode));
    }
}
