package app.sano.picchi.eegimage;

import io.realm.RealmObject;

public class Memo extends RealmObject {
    //日付
    public String updateDate;
    //画像
    public byte[] bitmap;

}
