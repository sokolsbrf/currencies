package ru.dimasokol.currencies.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dimasokol.currencies.demo.currencies.parsers.CurrenciesList;
import ru.dimasokol.currencies.demo.currencies.parsers.Currency;


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

}
