package ru.dimasokol.currencies.demo.currencies;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

import ru.dimasokol.currencies.demo.R;
import ru.dimasokol.currencies.demo.core.Content;
import ru.dimasokol.currencies.demo.core.TaskRunner;
import ru.dimasokol.currencies.demo.core.exceptions.RequestException;
import ru.dimasokol.currencies.demo.core.utils.DateFormatUtils;
import ru.dimasokol.currencies.demo.core.utils.DoubleTransformer;
import ru.dimasokol.currencies.demo.currencies.parsers.CurrenciesList;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public class LoadCurrenciesRunner implements TaskRunner {

    private Date mDate;

    public LoadCurrenciesRunner(Date date) {
        mDate = date;
    }

    @Override
    public Content execute() throws RequestException {
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + DateFormatUtils.dateForRequest(mDate));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            RegistryMatcher m = new RegistryMatcher();
            m.bind(Double.class, new DoubleTransformer());
            Serializer serializer = new Persister(m);

            CurrenciesList result = serializer.read(CurrenciesList.class, new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("windows-1251"))));
            connection.disconnect();

            LoadCurrenciesContent content = new LoadCurrenciesContent();
            content.set(result);
            return content;

        } catch (IOException e) {
            throw new RequestException(e, R.string.error_network_generic);
        } catch (Exception e) {
            throw new RequestException(e, R.string.error_network_parser);
        }
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
