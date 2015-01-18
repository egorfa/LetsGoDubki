package com.dtd.letsgodubki;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Egor on 21.11.2014.
 */
public class LibraryFragment extends Fragment {

    Toast m_currentToast;

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;

    static LibraryFragment newInstance(int page) {
        LibraryFragment pageFragment = new LibraryFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        switch (pageNumber) {
            case 0:
                view = inflater.inflate(R.layout.fragment_library1, null);
                break;
            case 1:
                view = inflater.inflate(R.layout.fragment_library2, null);
                break;
        }
        return view;
    }

    void showToast(String text)
    {
        if(m_currentToast != null)
        {
            m_currentToast.cancel();
        }
        m_currentToast = Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG);
        m_currentToast.setGravity(Gravity.CENTER,0,0);
        m_currentToast.show();

    }


}