package com.dtd.letsgodubki.server;

import com.dtd.letsgodubki.ArrayDorm;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Egor on 13/09/15.
 */
public interface API {

    String API_URL = "https://letsgodubki-dtd.appspot.com/_ah/api/dubkiapi/v1";//TODO //

    @GET("/itemscount")
    public Observable<ArrayDorm> getItemsCount();

}
