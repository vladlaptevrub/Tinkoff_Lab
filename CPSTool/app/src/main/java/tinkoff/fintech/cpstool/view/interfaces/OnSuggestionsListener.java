package tinkoff.fintech.cpstool.view.interfaces;

import java.util.List;

public interface OnSuggestionsListener {
    /**
     * Success in getting suggestions.
     *
     * @param suggestions suggestions either from cache or server.
     */
    void onSuggestionsReady(List<String> suggestions);

    /**
     * Something went wrong.
     *
     * @param message error that occurred during getting suggestions.
     */
    void onError(String message);
}
