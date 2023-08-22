package com.lelong.ngungtinh;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class OpenScanner_CustomAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> originalSuggestions;

    public OpenScanner_CustomAutoCompleteAdapter(Context context, List<String> suggestions) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        this.originalSuggestions = new ArrayList<>(suggestions);
    }

    @Override
    public Filter getFilter() {
        return new CustomFilter();
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> filteredSuggestions = new ArrayList<>();

            for (String suggestion : originalSuggestions) {
                if (suggestion.contains(constraint)) {
                    filteredSuggestions.add(suggestion);
                }
            }

            results.values = filteredSuggestions;
            results.count = filteredSuggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results.count > 0) {
                addAll((List<String>) results.values);
                notifyDataSetChanged();
            }
        }
    }
}
