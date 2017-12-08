package tinkoff.fintech.cpstool.model.realm;

import io.realm.RealmObject;

public class Address extends RealmObject{
    private String value;
    private String unrestricted_value;

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public void setUnrestricted_value(String unrestricted_value){
        this.unrestricted_value = unrestricted_value;
    }

    public String getUnrestricted_value(){
        return unrestricted_value;
    }
}
