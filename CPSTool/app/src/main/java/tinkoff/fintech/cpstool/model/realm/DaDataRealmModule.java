package tinkoff.fintech.cpstool.model.realm;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {RealmDaDataAnswer.class, RealmData.class, RealmDaDataSuggestion.class})
public class DaDataRealmModule {}