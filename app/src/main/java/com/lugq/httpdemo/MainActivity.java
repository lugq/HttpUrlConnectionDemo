package com.lugq.httpdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lugq.httpdemo.net.HttpUtils;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String CHARGE_URL = "http://www.mocky.io/v2/596c7afc10000049027e2111";

    private Button button;
    private TextView tv_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        tv_json = (TextView) findViewById(R.id.tv_json);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute(new MyRequest("param1", "param2"));
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<MyRequest, Void, String> {

        @Override
        protected void onPreExecute() {
            //按键点击之后的禁用，防止重复点击
            button.setOnClickListener(null);
        }

        @Override
        protected String doInBackground(MyRequest... pr) {
            MyRequest paymentRequest = pr[0];
            String data = null;
            try {
                JSONObject object = new JSONObject();
                object.put("channel", paymentRequest.param1);
                object.put("amount", paymentRequest.param1);
                String json = object.toString();

                data = HttpUtils.postJson(CHARGE_URL, json);
                Log.i(TAG, "data:" + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            tv_json.setText(data);
            if (null == data) {
                Log.i("请求出错", "请检查URL");
            }
        }

    }

    class MyRequest {
        String param1;
        String param2;

        MyRequest(String param1, String param2) {
            this.param1 = param1;
            this.param2 = param2;
        }
    }
}
