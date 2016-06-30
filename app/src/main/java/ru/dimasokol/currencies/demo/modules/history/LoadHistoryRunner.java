package ru.dimasokol.currencies.demo.modules.history;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

import ru.dimasokol.currencies.demo.core.Content;
import ru.dimasokol.currencies.demo.core.networking.NetworkTaskRunner;
import ru.dimasokol.currencies.demo.core.utils.DateFormatUtils;
import ru.dimasokol.currencies.demo.core.utils.DoubleTransformer;
import ru.dimasokol.currencies.demo.modules.history.parsers.CurrencyHistory;

/**
 * <p></p>
 * <p>Добавлено: 21.06.16</p>
 *
 * @author sokol
 */
public class LoadHistoryRunner extends NetworkTaskRunner {

    private Date mDateFrom;
    private Date mDateTo;
    private String mCurrencyCode;

    public LoadHistoryRunner(Date from, Date to, String currencyCode) {
        super(buildUrl(from, to, currencyCode));

        mDateFrom = from;
        mDateTo = to;
        mCurrencyCode = currencyCode;
    }

    private static String buildUrl(Date from, Date to, String currencyCode) {
        String fromString = DateFormatUtils.dateForRequest(from);
        String toString = DateFormatUtils.dateForRequest(to);

        String result = "http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=" + fromString + "&date_req2=" + toString + "&VAL_NM_RQ=" + currencyCode;
        return result;
    }

    @Override
    protected Content executeNetworkOperations(InputStream source) throws Exception {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Double.class, new DoubleTransformer());
        Serializer serializer = new Persister(m);

        CurrencyHistory result = serializer.read(CurrencyHistory.class, new BufferedReader(new InputStreamReader(source, Charset.forName("windows-1251"))));
        HistoryContent content = new HistoryContent();
        content.set(result);

        Log.d("Bloody", result.toString());

        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadHistoryRunner that = (LoadHistoryRunner) o;

        String dateAsString = DateFormatUtils.dateForRequest(mDateFrom);
        String thatDateAsString = DateFormatUtils.dateForRequest(that.mDateFrom);

        if (!dateAsString.equals(thatDateAsString)) return false;

        dateAsString = DateFormatUtils.dateForRequest(mDateTo);
        thatDateAsString = DateFormatUtils.dateForRequest(that.mDateTo);

        if (!dateAsString.equals(thatDateAsString)) return false;

        return mCurrencyCode.equals(that.mCurrencyCode);

    }

    @Override
    public int hashCode() {
        String dateAsString = DateFormatUtils.dateForRequest(mDateFrom);
        int result = dateAsString.hashCode();
        dateAsString = DateFormatUtils.dateForRequest(mDateTo);
        result = 31 * result + dateAsString.hashCode() + mCurrencyCode.hashCode();
        return result;
    }
}
