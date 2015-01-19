package com.dtd.letsgodubki;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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

        /*listViewMeetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meeting selectedMeeting = arrayMeetings.get(position);
                Toast.makeText("Selected Meeting:" + selectedMeeting.getTitle(), 1000);
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        switch (pageNumber) {
            case 0:
                view = inflater.inflate(R.layout.fragment_library1, null);
                ArrayList<Meeting> arrayMeetings;
                ListView listViewMeetings;

                arrayMeetings = new ArrayList<Meeting>();



                Meeting mafia = new Meeting(R.drawable.cards, "15:01", "Вечером играем в мафию");
                Meeting drink = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки");

                arrayMeetings.add(mafia);
                arrayMeetings.add(drink);

                listViewMeetings = (ListView)view.findViewById(R.id.LV1);
                ListMeetingsAdapter adapter = new ListMeetingsAdapter(this.getActivity(), arrayMeetings);
                listViewMeetings.setAdapter(adapter);


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