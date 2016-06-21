package ru.dimasokol.currencies.demo.modules.currencies;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

import ru.dimasokol.currencies.demo.R;
import ru.dimasokol.currencies.demo.core.Content;
import ru.dimasokol.currencies.demo.core.TaskRunner;
import ru.dimasokol.currencies.demo.core.exceptions.RequestException;
import ru.dimasokol.currencies.demo.core.networking.NetworkTaskRunner;
import ru.dimasokol.currencies.demo.core.utils.DateFormatUtils;
import ru.dimasokol.currencies.demo.core.utils.DoubleTransformer;
import ru.dimasokol.currencies.demo.modules.currencies.parsers.CurrenciesList;

/**
 * <p>Задача загрузки списка валют за указанную дату</p>
 *
 * @author sokol
 */
public class LoadCurrenciesRunner extends NetworkTaskRunner {

    private Date mDate;

    /**
     * @param date Дата, на которую нужно грузить валюты; учитывается только день, месяц и год.
     */
    public LoadCurrenciesRunner(Date date) {
        super("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + DateFormatUtils.dateForRequest(date));
        mDate = date;
    }

    @Override
    protected Content executeNetworkOperations(InputStream source) throws Exception {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Double.class, new DoubleTransformer());
        Serializer serializer = new Persister(m);

        CurrenciesList result = serializer.read(CurrenciesList.class, new BufferedReader(new InputStreamReader(source, Charset.forName("windows-1251"))));
        LoadCurrenciesContent content = new LoadCurrenciesContent();
        content.set(result);
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadCurrenciesRunner that = (LoadCurrenciesRunner) o;
        String myDateAsString = DateFormatUtils.dateForRequest(mDate);
        String thatDateAsString = DateFormatUtils.dateForRequest(that.mDate);

        return myDateAsString.equals(thatDateAsString);

    }

    @Override
    public int hashCode() {
        String myDateAsString = DateFormatUtils.dateForRequest(mDate);
        return myDateAsString.hashCode();
    }
}
