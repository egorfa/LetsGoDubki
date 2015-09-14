package com.dtd.letsgodubki.MVP.modelImpl;


import com.dtd.letsgodubki.ArrayDorm;
import com.dtd.letsgodubki.MVP.model.FirstActivityModel;
import com.dtd.letsgodubki.MVP.view.viewholders.FirstActivityViewHolder;
import com.dtd.letsgodubki.application.LetsGoDubki;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Egor on 13/09/15.
 */



public class FirstActivityModelImpl implements FirstActivityModel {

    private FirstActivityViewHolder viewHolder;

    public FirstActivityModelImpl(final FirstActivityViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public Observable<ArrayDorm> request() {
        return LetsGoDubki.api.getItemsCount()
                .subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, ArrayDorm>() {//TODO refactoring with method
                    @Override
                    public ArrayDorm call(Throwable throwable) {
                        ArrayDorm temp = new ArrayDorm();
                        temp.setError(true);
                        return temp;
                    }
                });
    }

}
