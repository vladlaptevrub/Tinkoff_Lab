package tinkoff.fintech.cpstool.model;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import tinkoff.fintech.cpstool.model.history.PartyModule;
import tinkoff.fintech.cpstool.model.query.DaDataRealmModule;
import tinkoff.fintech.cpstool.model.query.QueryRealmModule;

public class DaDataApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration queriesConfig = new RealmConfiguration.Builder(this)
                .setModules(new QueryRealmModule(), new PartyModule(), new DaDataRealmModule())
                .name("queries.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(queriesConfig);
    }
}