package tinkoff.fintech.cpstool.presenter.rest.interfaces;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import tinkoff.fintech.cpstool.model.query.RealmDaDataSuggestion;
import tinkoff.fintech.cpstool.presenter.rest.DaDataBody;

public interface DaDataService {
    @POST("/api/v2/suggest/party")
    RealmDaDataSuggestion getSuggestionSync(@Body DaDataBody body);

    @POST("/api/v2/suggest/party")
    void getSuggestionAsync(@Body DaDataBody body, Callback<RealmDaDataSuggestion> callback);
}