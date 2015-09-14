package com.dtd.letsgodubki.application;

import android.app.Application;

import com.dtd.letsgodubki.server.API;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Egor on 09.03.2015.
 */
public class LetsGoDubki extends Application {

    public static String sDefSystemLanguage;
    public static Integer days;
    public static API api;

    @Override
    public void onCreate() {
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "KtubqRHEfFmfSf24Cr5xkLykMbwID0pa9Ew83RdI", "OwphBEzCe7CyRVfg8PYSyhjQXWMppVhM8ZNHO1yf");

        OkClient client = getOkClient();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(API.API_URL)
                .build();

        api = restAdapter.create(API.class);

        super.onCreate();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO Auto-generated method stub
                    if (hostname.equals("letsgodubki-dtd.appspot.com"))
                        return true;
                    else
                        return false;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkClient getOkClient() {
        OkHttpClient client1 = new OkHttpClient();
        client1 = getUnsafeOkHttpClient();
        OkClient _client = new OkClient(client1);
        return _client;
    }

}
