package com.sneydr.roomrv2.App;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

public class ConnectionManager {

    public static ConnectionManager instance;

    private static String lease;

    public static ConnectionManager getInstance() {
        if (instance == null){
            return new ConnectionManager();
        }
        return instance;
    }

    public void setLeaseUrl(String url) {
        lease = url;
    }

    public String getLeaseUrl() {
        return lease;
    }

    public URL parseURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public URLConnection openConnection(URL url){
        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int getFileLength(URLConnection connection) {
        return connection.getContentLength();
    }


}
