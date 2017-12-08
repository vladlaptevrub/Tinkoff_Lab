package tinkoff.fintech.cpstool.presenter.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit.RetrofitError;
import tinkoff.fintech.cpstool.model.history.Cache;
import tinkoff.fintech.cpstool.model.realm.RealmDaDataAnswer;
import tinkoff.fintech.cpstool.view.interfaces.OnSuggestionsListener;
import tinkoff.fintech.cpstool.model.realm.Query;
import tinkoff.fintech.cpstool.model.realm.Result;
import tinkoff.fintech.cpstool.model.realm.RealmDaDataSuggestion;
import tinkoff.fintech.cpstool.presenter.rest.DaDataBody;
import tinkoff.fintech.cpstool.presenter.rest.DaDataRestClient;

public class ServerUtils {
    private final static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void query(final String query, final OnSuggestionsListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Trim current query for ignoring whitespaces
                String queryFromUser = query.replaceAll("\\s+", " ").trim();

                if (!queryFromUser.isEmpty()) {
                    Realm realm = Realm.getDefaultInstance();

                    RealmResults<Query> queryRealmResults = realm.where(Query.class).equalTo("query", queryFromUser).findAll();

                    final List<String> suggestions = new ArrayList<>(10);

                    boolean success = false;

                    /** If we have no cache on this query,
                        we have to query over the Internet */

                    if (queryRealmResults.size() == 0) {
                        RealmDaDataSuggestion suggestion = null;
                        try {
                            suggestion = DaDataRestClient.getInstance().suggestSync(new DaDataBody(queryFromUser, 10));

                            success = true;
                        } catch (RetrofitError e) {
                            e.printStackTrace();
                            dispatchError(e.getMessage(), listener);
                        } catch (Exception e) {
                            e.printStackTrace();
                            dispatchError(e.getMessage(), listener);
                        }

                        if (success) {
                            cacheUserQueryWithServerResult(queryFromUser, realm, suggestions, suggestion);
                        }
                    } else {
                        fillSuggestionsFromCache(queryRealmResults, suggestions);
                    }

                    realm.close();

                    dispatchUpdate(suggestions, listener);
                }
            }
        };
        executor.submit(runnable);
    }

    private static void dispatchError(final String message, final OnSuggestionsListener listener) {
        if (listener != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(message);
                }
            });
        }
    }

    private static void fillSuggestionsFromCache(RealmResults<Query> queryRealmResults, List<String> suggestions) {
        for (int i = 0; i < queryRealmResults.size(); i++) {
            RealmList<Result> result = queryRealmResults.get(i).getResult();

            for (int j = 0; j < result.size(); j++) {
                suggestions.add(result.get(j).getResult());
            }
        }
    }

    /** CAAAAAAAACHE **/
    private static void cacheUserQueryWithServerResult(String queryFromUser, Realm realm, List<String> suggestions, RealmDaDataSuggestion suggestion) {
        RealmList<Result> resultsRealm = new RealmList<>();
        if (suggestion != null) {
            for (int i = 0; i < suggestion.getSuggestions().size(); i++) {
                String suggestionResult = suggestion.getSuggestions().get(i).getValue();

                suggestions.add(suggestionResult);

                RealmResults<Cache> cacheData = realm.where(Cache.class).equalTo("title", suggestionResult).findAll();

                if (cacheData.isEmpty()) {
                    realm.beginTransaction();
                    Cache cache = realm.createObject(Cache.class);
                    cache.setTitle(suggestionResult);
                    cache.setAddress(suggestion.getSuggestions().get(i).getRealmData().getAddress().getUnrestricted_value());
                    cache.setInn(suggestion.getSuggestions().get(i).getRealmData().getInn());
                    realm.commitTransaction();
                }

                realm.beginTransaction();

                Result result = realm.createObject(Result.class);
                result.setResult(suggestionResult);
                resultsRealm.add(result);

                realm.commitTransaction();
            }

            realm.beginTransaction();

            Query query = realm.createObject(Query.class);

            query.setId(UUID.randomUUID().toString());
            query.setQuery(queryFromUser);
            query.setResult(resultsRealm);

            realm.commitTransaction();
        }
    }

    private static void dispatchUpdate(final List<String> suggestions, final OnSuggestionsListener listener) {
        if (listener != null && suggestions.size() > 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onSuggestionsReady(suggestions);
                }
            });
        }
    }
}