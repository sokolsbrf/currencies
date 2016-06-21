package ru.dimasokol.currencies.demo.modules.history;

import java.io.InputStream;
import java.util.Date;

import ru.dimasokol.currencies.demo.core.Content;
import ru.dimasokol.currencies.demo.core.networking.NetworkTaskRunner;
import ru.dimasokol.currencies.demo.core.utils.DateFormatUtils;

/**
 * <p></p>
 * <p>Добавлено: 21.06.16</p>
 *
 * @author sokol
 */
public class LoadHistoryRunner extends NetworkTaskRunner {

    private Date mDate;
    private String mCurrencyCode;

    public LoadHistoryRunner(Date date, String currencyCode) {
        super("http://www.cbr.ru/scripts/XML_dynamic.asp?");

        mDate = date;
        mCurrencyCode = currencyCode;
    }

    @Override
    protected Content executeNetworkOperations(InputStream source) throws Exception {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadHistoryRunner that = (LoadHistoryRunner) o;

        String dateAsString = DateFormatUtils.dateForRequest(mDate);
        String thatDateAsString = DateFormatUtils.dateForRequest(that.mDate);

        if (!dateAsString.equals(thatDateAsString)) return false;
        return mCurrencyCode.equals(that.mCurrencyCode);

    }

    @Override
    public int hashCode() {
        String dateAsString = DateFormatUtils.dateForRequest(mDate);
        int result = dateAsString.hashCode();
        result = 31 * result + mCurrencyCode.hashCode();
        return result;
    }
}
