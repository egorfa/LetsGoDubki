package com.dtd.letsgodubki.MVP.presenterImpl;

import android.app.Activity;
import android.os.Bundle;

import com.dtd.letsgodubki.MVP.model.FirstActivityModel;
import com.dtd.letsgodubki.MVP.presenter.FirstActivityPresenter;
import com.dtd.letsgodubki.MVP.view.FirstActivityView;

import java.util.concurrent.TimeUnit;

import rx.Subscription;

/**
 * Created by Egor on 14/09/15.
 */
public class FirstActivityPresenterImpl implements FirstActivityPresenter {

    private final FirstActivityModel model;
    private final FirstActivityView view;
    private Subscription subscription;

    public FirstActivityPresenterImpl(FirstActivityModel model, FirstActivityView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        view.setClickListeners();//Устанавливаем лисенеров на кнопки
        subscription = model
                .request()//запрашиваем инфу по количеству встреч в общагах
                .retry(3)//количество повторений зароса
                .timeout(10, TimeUnit.SECONDS)//таймаут запроса
                .subscribe(result -> view.showResponse(result));//вывод информации на UI

    }

    @Override
    public void onDestroy() {
        if (subscription!=null){
            subscription.unsubscribe();
        }
    }

}
