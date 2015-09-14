package com.dtd.letsgodubki.MVP.presenter;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Egor on 13/09/15.
 */
public interface FirstActivityPresenter {

    void onCreate(Activity activity, Bundle savedInstanceState);

    void onDestroy();

}
