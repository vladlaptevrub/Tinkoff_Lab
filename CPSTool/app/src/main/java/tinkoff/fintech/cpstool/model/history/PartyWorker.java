package tinkoff.fintech.cpstool.model.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import tinkoff.fintech.cpstool.model.interfaces.IPartyWorker;
import tinkoff.fintech.cpstool.model.realm.Query;
import tinkoff.fintech.cpstool.model.realm.Result;

public class PartyWorker implements IPartyWorker{

    private final static String FALSE = "false";
    private final static String TRUE = "true";

    private Realm mRealm;

    public PartyWorker(){
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void saveData(String title) {
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", title).findAll();
        if (parties.isEmpty()) {
            Cache data = findData(title);
            if (data != null) {
                doTransaction(data);
            } else {
                Log.i("Model", "Didn't find '" + title + "' in cache");
            }
        }
    }

    @Override
    public List<Party> getData() {
        return getSortedData();
    }

    private Cache findData(String value){

        RealmResults<Cache> data =
                mRealm.where(Cache.class).equalTo("title", value).findAll();

        if (!data.isEmpty()){
            return data.get(0);
        } else {
            return null;
        }
    }

    private void doTransaction(Cache data){
        mRealm.beginTransaction();
        Party newParty = mRealm.createObject(Party.class);
        newParty.setTitle(data.getTitle());
        newParty.setInn(data.getInn());
        newParty.setAddress(data.getAddress());
        newParty.setFavourite(FALSE);
        mRealm.commitTransaction();
    }

    @Override
    public Party findParty(String value) {
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", value).findAll();
        if (!parties.isEmpty()){
            return parties.get(0);
        } else {
            Log.i("Model", "Didn't find '" + value + "' in storage");
            return null;
        }
    }

    @Override
    public void doFavourite(String value, boolean sign) {
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", value).findAll();
        if (!parties.isEmpty()){
            mRealm.beginTransaction();
            if (sign) {
                parties.get(0).setFavourite(TRUE);
            } else {
                parties.get(0).setFavourite(FALSE);
            }
            mRealm.commitTransaction();
        }
    }

    @Override
    public void deleteItem(String value) {
        mRealm.beginTransaction();
        RealmResults<Party> parties = mRealm.where(Party.class).equalTo("title", value).findAll();
        parties.clear();
        mRealm.commitTransaction();
    }

    @Override
    public void clearHistoryData() {
        mRealm.beginTransaction();
        RealmResults<Party> parties = mRealm.where(Party.class).findAll();
        parties.clear();
        mRealm.commitTransaction();
    }

    @Override
    public void saveCache(String title, String address, String inn) {
        mRealm.beginTransaction();
        Cache cache = mRealm.createObject(Cache.class);
        cache.setTitle(title);
        cache.setAddress(address);
        cache.setInn(inn);
        mRealm.commitTransaction();
    }

    @Override
    public void saveQuery(String queryFromUser, RealmList<Result> resultsRealm) {
        mRealm.beginTransaction();

        Query query = mRealm.createObject(Query.class);

        query.setId(UUID.randomUUID().toString());
        query.setQuery(queryFromUser);
        query.setResult(resultsRealm);

        mRealm.commitTransaction();
    }

    @Override
    public RealmResults<Query> getQueries(String queryFromUser) {
        return mRealm.where(Query.class).equalTo("query", queryFromUser).findAll();
    }

    @Override
    public RealmResults<Cache> getCache(String title) {
        return mRealm.where(Cache.class).equalTo("title", title).findAll();
    }

    @Override
    public Result saveResult(String result) {
        mRealm.beginTransaction();

        Result realmResult = mRealm.createObject(Result.class);
        realmResult.setResult(result);

        mRealm.commitTransaction();

        return realmResult;
    }

    private List<Party> getSortedData(){
        RealmResults<Party> partiesResults = mRealm.where(Party.class).findAll();
        List<Party> favouriteParties = new ArrayList<>();
        List<Party> simpleParties = new ArrayList<>();
        List<Party> sortedList = new ArrayList<>();

        for (int i = 0; i < partiesResults.size(); i++){
            if (partiesResults.get(i).getFavourite().equals(TRUE)){
                favouriteParties.add(partiesResults.get(i));
            } else {
                simpleParties.add(partiesResults.get(i));
            }
        }

        sortedList.addAll(favouriteParties);
        sortedList.addAll(simpleParties);

        return sortedList;
    }
}
