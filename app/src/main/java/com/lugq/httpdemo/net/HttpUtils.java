package com.lugq.httpdemo.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description：
 * Creator: Created by peter.
 * Date: 2017/7/17.
 */
@SuppressWarnings("unused")
public class HttpUtils {

    /**
     * POST 请求 application/json方式
     * @param urlStr URL
     * @param json 参数，请求体积
     * @return json
     * @throws IOException IOException
     */
    @SuppressWarnings("unused")
    public static String postJson(String urlStr, String json) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.getOutputStream().write(json.getBytes());

        if (conn.getResponseCode() == 200) {
            BufferedReader
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
        return null;
    }

}
