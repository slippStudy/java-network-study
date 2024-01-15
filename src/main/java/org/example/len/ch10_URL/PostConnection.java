package org.example.len.ch10_URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class PostConnection {

    public static void main(String[] args) throws IOException {

        String u = "https://ceo.baemin.com";
        URL url = new URL(u);
        URLConnection connection = url.openConnection();
        HttpURLConnection hurlc = (HttpURLConnection) connection;
        hurlc.setRequestMethod("POST");
        hurlc.setDoOutput(true);
        hurlc.setDoInput(true);
        hurlc.setUseCaches(false);
        hurlc.setDefaultUseCaches(false);

        PrintWriter out = new PrintWriter(hurlc.getOutputStream());
        String query = "";
        out.println(query);
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(hurlc.getInputStream()));
        String inputLine = null;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    }
}
