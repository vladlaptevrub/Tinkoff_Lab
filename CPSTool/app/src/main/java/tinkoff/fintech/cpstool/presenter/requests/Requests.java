package tinkoff.fintech.cpstool.presenter.requests;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import tinkoff.fintech.cpstool.model.history.Cache;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.model.history.PartyWorker;
import tinkoff.fintech.cpstool.model.realm.Query;
import tinkoff.fintech.cpstool.model.realm.Result;
import tinkoff.fintech.cpstool.presenter.interfaces.IRequests;

public class Requests implements IRequests {
    private PartyWorker mModel;

    public Requests(){
        mModel = new PartyWorker();
    }

    @Override
    public void setData(String title) {
        mModel.saveData(title);
    }

    @Override
    public List<Party> getData() {
        return mModel.getData();
    }

    @Override
    public Party findParty(String value) {
        return mModel.findParty(value);
    }

    @Override
    public void setFavourite(String value, boolean b) {
        mModel.doFavourite(value, b);
    }

    @Override
    public void deleteItem(String value) {
        mModel.deleteItem(value);
    }

    @Override
    public void clearHistoryData() {
        mModel.clearHistoryData();
    }

    @Override
    public void saveCache(String title, String address, String inn){
        mModel.saveCache(title, address, inn);
    }

    @Override
    public void saveQuery(String queryFromUser, RealmList<Result> resultsRealm) {
        mModel.saveQuery(queryFromUser, resultsRealm);
    }

    @Override
    public RealmResults<Query> getQueries(String queryFromUser) {
        return mModel.getQueries(queryFromUser);
    }

    @Override
    public RealmResults<Cache> getCache(String suggestion) {
        return mModel.getCache(suggestion);
    }

    @Override
    public Result saveResult(String result) {
        return mModel.saveResult(result);
    }
}
