package com.dtd.letsgodubki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Egor on 21.11.2014.
 */
public class LibraryFragment extends Fragment {

    Toast m_currentToast;
    ProgressDialog progress;

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private static final String TAG_TITLE = "title";
    private static final String TAG_SRC = "src";
    private static final String TAG_LINK = "link";
    private static final String TAG_IMGLINK = "img_link";
    private static final String TAG_CODE = "code";
    //private static final String url = "http://xn--d1achwgkbn7a.xn--p1ai/api/get_article.php?type=buklety";

    public class JSON_element {
        String title;
        String src;
    }

    public class JSON_video{
        String title;
        String src;
        String code;
    }

    public class JSON_Stati {
        String title;
        String link;
        String img_link;
    }

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

                ListView lvVideo = (ListView) view.findViewById(R.id.lvFrag1);

                JSONVideo videoTask = new JSONVideo();
                videoTask.execute("http://xn--d1achwgkbn7a.xn--p1ai/api/get_article.php?type=videomaterialy");
                try {
                    ArrayList<JSON_video> array_this = videoTask.get();
                    if (array_this != null) {
                        ArrayList<String> video_titles = new ArrayList<String>();
                        final ArrayList<String> video_link = new ArrayList<String>();
                        final ArrayList<String> video_code = new ArrayList<String>();
                        for (int i = 0; i < array_this.size(); i++) {
                            video_titles.add(array_this.get(i).title);
                            video_link.add(array_this.get(i).src);
                            video_code.add(array_this.get(i).code);
                        }

                        LibAdapter adapter3 = new LibAdapter(getActivity().getBaseContext(), null, video_titles);

                        lvVideo.setAdapter(adapter3);

                        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent intent = new Intent(getActivity(), ThisVideo.class);
                                intent.putExtra("code", video_code.get(position));
                                intent.putExtra("URL", video_link.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                break;
            case 1:
                view = inflater.inflate(R.layout.fragment_library2, null);

                ListView lvStati = (ListView) view.findViewById(R.id.lvFrag2);

                JSONStati StatiTask = new JSONStati();
                StatiTask.execute("http://xn--d1achwgkbn7a.xn--p1ai/api/get_article.php?type=stati");
                try {
                    ArrayList<JSON_Stati> array_this = StatiTask.get();
                    if (array_this != null) {
                        ArrayList<String> title_array = new ArrayList<String>();
                        final ArrayList<String> link_array = new ArrayList<String>();
                        final ArrayList<String> img_link_urls = new ArrayList<String>();
                        for (int i = 0; i < array_this.size(); i++) {
                            title_array.add(array_this.get(i).title);
                            link_array.add(array_this.get(i).link);
                            img_link_urls.add(array_this.get(i).img_link);
                        }

                        LibAdapter adapter3 = new LibAdapter(getActivity().getBaseContext(), img_link_urls, title_array);

                        lvStati.setAdapter(adapter3);

                        lvStati.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent intent = new Intent(getActivity(), ThisStati.class);
                                intent.putExtra("link", link_array.get(position));
                                startActivity(intent);
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_library2, null);

                ListView lvBooks = (ListView) view.findViewById(R.id.lvFrag2);
                final ProgressBar pB = (ProgressBar) view.findViewById(R.id.pB);

                JSONParse bookTask = new JSONParse();
                bookTask.execute("http://xn--d1achwgkbn7a.xn--p1ai/api/get_article.php?type=buklety");
                try {
                    ArrayList<JSON_element> array_this = bookTask.get();
                    if (array_this != null) {
                        final ArrayList<String> books_array = new ArrayList<String>();
                        final ArrayList<String> pdf_urls = new ArrayList<String>();
                        for (int i = 0; i < array_this.size(); i++) {
                            books_array.add(array_this.get(i).title);
                            pdf_urls.add(array_this.get(i).src);
                        }

                        LibAdapter adapter3 = new LibAdapter(getActivity().getBaseContext(), null, books_array);

                        lvBooks.setAdapter(adapter3);

                        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                                //String name = LOAD(pdf_urls.get(position),books_array.get(position));

                                LoadFile loadTask = new LoadFile();
                                loadTask.execute(pdf_urls.get(position), Integer.toString(position));

                            }
                        });
                    }

                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }catch(ExecutionException e){
                        e.printStackTrace();
                    }

                break;
        }
        return view;
    }

    public boolean downloadFile(String fileURL, String pos)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(fileURL);
        boolean flag = false;
        try {

            HttpGet hGet = new HttpGet(fileURL);

            //Now hit the site for the file
            HttpResponse response= httpclient.execute(hGet);

            InputStream is = response.getEntity().getContent();
            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), pos + ".pdf"));

            int read = 0;
            byte[] buffer = new byte[32768];
            while((read = is.read(buffer)) > 0){
                fos.write(buffer, 0, read);
            }

            fos.close();
            is.close();
            flag = true;

        } catch (ClientProtocolException e) {
            flag = false;
        } catch (IOException e) {
            flag = false;
        }
        return flag;
    }

    public void showPdf(String name)
    {
        File file = new File(Environment.getExternalStorageDirectory()+ "/" + name +  ".pdf");
        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

    class JSONParse extends AsyncTask<String, String, ArrayList<JSON_element>> {

        @Override
        protected ArrayList<JSON_element> doInBackground(String... args) {
            JSONParser news_jParser = new JSONParser();
            JSONArray json = news_jParser.getJSONFromUrl(args[0]);
            ArrayList<JSON_element> JSON_array = null;
            if (json!=null) {
                JSON_array = new ArrayList<JSON_element>();
                try {
                    for (int i = 0; i < json.length(); i++) {
                        JSON_element buf = new JSON_element();
                        buf.title = json.getJSONObject(i).getString(TAG_TITLE);             /////////Need to check Integer Type!!!
                        buf.src = json.getJSONObject(i).getString(TAG_SRC);
                        JSON_array.add(buf);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return JSON_array;
        }

        @Override
        protected void onPostExecute(ArrayList<JSON_element> news_array) {
        }

    }

    class JSONVideo extends AsyncTask<String, String, ArrayList<JSON_video>> {

        @Override
        protected ArrayList<JSON_video> doInBackground(String... args) {
            JSONParser news_jParser = new JSONParser();
            JSONArray json = news_jParser.getJSONFromUrl(args[0]);
            ArrayList<JSON_video> JSON_array = null;
            if (json!=null) {
                JSON_array = new ArrayList<JSON_video>();
                try {
                    for (int i = 0; i < json.length(); i++) {
                        JSON_video buf = new JSON_video();
                        buf.title = json.getJSONObject(i).getString(TAG_TITLE);             /////////Need to check Integer Type!!!
                        buf.src = json.getJSONObject(i).getString(TAG_SRC);
                        buf.code = json.getJSONObject(i).getString(TAG_CODE);
                        JSON_array.add(buf);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return JSON_array;
        }

        @Override
        protected void onPostExecute(ArrayList<JSON_video> news_array) {
        }

    }

    class JSONStati extends AsyncTask<String, String, ArrayList<JSON_Stati>> {

        @Override
        protected ArrayList<JSON_Stati> doInBackground(String... args) {
            JSONParser news_jParser = new JSONParser();
            JSONArray json = news_jParser.getJSONFromUrl(args[0]);
            ArrayList<JSON_Stati> JSON_array = null;
                if (json!=null) {
                    JSON_array = new ArrayList<JSON_Stati>();
                    try {
                        for (int i = 0; i < json.length(); i++) {
                            JSON_Stati buf = new JSON_Stati();
                            buf.title = json.getJSONObject(i).getString(TAG_TITLE);             /////////Need to check Integer Type!!!
                            buf.link = json.getJSONObject(i).getString(TAG_LINK);
                            buf.img_link = json.getJSONObject(i).getString(TAG_IMGLINK);
                            JSON_array.add(buf);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
            return JSON_array;
        }

        @Override
        protected void onPostExecute(ArrayList<JSON_Stati> news_array) {
        }

    }

    class LoadFile extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(getActivity(), R.style.CustomDialog);
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setMessage("Подождите, пожалуйста...");

            progress.show();
        }

        @Override
        protected String doInBackground(String... args) {

            boolean flag = downloadFile(args[0], args[1]);
            if (flag==true)    return args[1];
            else return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            progress.dismiss();
            if (result!=null)     showPdf(result);
            else showToast("Проверьте подключение к интернету");
            //openPdfIntent(Environment.getExternalStorageDirectory() + "/" + result + ".pdf");
        }

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