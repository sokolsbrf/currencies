package ru.dimasokol.currencies.demo.modules.history;

import android.net.Uri;

import java.util.List;

import ru.dimasokol.currencies.demo.core.Core;
import ru.dimasokol.currencies.demo.core.ModuleFacade;

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

    public Uri buildHistoryUri(String currencyId) {
        return Uri.parse(FORMAT_URI_STRING + "/" + currencyId);
    }
}
