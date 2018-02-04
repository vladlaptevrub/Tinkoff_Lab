package tinkoff.fintech.cpstool.view.interfaces;

import java.util.List;

public interface ISuggestionsListener {
    void onSuggestionsReady(List<String> suggestions);
    void onError(String message);
}
