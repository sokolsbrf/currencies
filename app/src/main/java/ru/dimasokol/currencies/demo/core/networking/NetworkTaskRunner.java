package ru.dimasokol.currencies.demo.core.networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.dimasokol.currencies.demo.R;
import ru.dimasokol.currencies.demo.core.Content;
import ru.dimasokol.currencies.demo.core.TaskRunner;
import ru.dimasokol.currencies.demo.core.exceptions.RequestException;
import ru.dimasokol.currencies.demo.core.utils.DateFormatUtils;

/**
 * <p>Сетевой исполнитель запроса. Содержит уже готовые методы для отправки GET-запроса и получения
 * результата.</p>
 *
 * @author sokol
 */
public abstract class NetworkTaskRunner implements TaskRunner {

    private String mSourceUrl;

    public NetworkTaskRunner(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    @Override
    public final Content execute() throws RequestException {
        try {

            URL url = new URL(mSourceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Content content = executeNetworkOperations(connection.getInputStream());

            connection.disconnect();
            return content;

        } catch (RequestException rethrowMe) {
            throw rethrowMe;
        } catch (IOException ioe) {
            throw new RequestException(ioe, R.string.error_network_generic);
        } catch (Exception e) {
            throw new RequestException(e, R.string.error_network_parser);
        }
    }

    /**
     * Обработка результата запроса
     * @param source Источник данных
     * @return Загруженный контент
     * @throws Exception
     */
    protected abstract Content executeNetworkOperations(InputStream source) throws Exception;
}
