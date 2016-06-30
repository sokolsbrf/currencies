package ru.dimasokol.currencies.demo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dimasokol.currencies.demo.modules.currencies.parsers.CurrenciesList;
import ru.dimasokol.currencies.demo.modules.currencies.parsers.Currency;
import ru.dimasokol.currencies.demo.modules.history.HistoryFacade;


/**
 * <p></p>
 * <p>Добавлено: 15.06.16</p>
 *
 * @author sokol
 */
public class CurrenciesListAdapter extends RecyclerView.Adapter<CurrenciesListAdapter.CurrencyHolder> {

    private CurrenciesList mCurrenciesList;

    public CurrenciesListAdapter(CurrenciesList currenciesList) {
        mCurrenciesList = currenciesList;
    }

    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_currency, parent, false);
        return new CurrencyHolder(root);
    }

    @Override
    public void onBindViewHolder(CurrencyHolder holder, int position) {
        Currency currency = mCurrenciesList.getCurrencies().get(position);
        holder.mCurrencyNameView.setText(holder.itemView.getContext().getString(R.string.format_currency_name_and_count, currency.getNominal(), currency.getName()));
        holder.mCurrencyValueView.setText(holder.itemView.getContext().getString(R.string.format_currency_value, currency.getValue()));
        holder.itemView.setOnClickListener(new ItemClickListenerImpl(mCurrenciesList.getCurrencies().get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mCurrenciesList == null? 0 : mCurrenciesList.getCurrencies().size();
    }

    public static class CurrencyHolder extends RecyclerView.ViewHolder {

        private TextView mCurrencyNameView;
        private TextView mCurrencyValueView;

        public CurrencyHolder(View itemView) {
            super(itemView);
            mCurrencyNameView = (TextView) itemView.findViewById(R.id.text_currency_name_and_count);
            mCurrencyValueView = (TextView) itemView.findViewById(R.id.text_currency_value);
        }
    }

    private static class ItemClickListenerImpl implements View.OnClickListener {

        private String mCurrencyCode;

        public ItemClickListenerImpl(String currencyCode) {
            mCurrencyCode = currencyCode;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.withAppendedPath(HistoryFacade.URI_ALL_HISTORIES, mCurrencyCode));
            v.getContext().startActivity(intent);
        }
    }

}
