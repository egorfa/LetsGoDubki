package com.dtd.letsgodubki.MVP.model;

import com.dtd.letsgodubki.ArrayDorm;

import rx.Observable;

/**
 * Created by Egor on 13/09/15.
 */
public interface FirstActivityModel {

    Observable<ArrayDorm> request();

}
