package com.example.administrator.huorongdai.util;

import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.xframe.XFrame;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class CodeHttp {
    private OkHttpClient client = null;
    private int cacheSize = 10 * 1024 * 1024;
    private String session;
    private ResCodeBean codeBean;
    private String failInfo;

    public String getFailInfo() {
        return failInfo;
    }

    public ResCodeBean getCodeBean() {
        return codeBean;
    }

    public String getSession() {
        return session;
    }

    public CodeHttp() {
        client = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(new Cache(XFrame.getContext().getCacheDir(), cacheSize))
                .build();
    }

    public void post(String url, Map<String, Object> params) {
        FormBody formBody = null;
        if (null != params&&!params.isEmpty()) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            Set<String> keySet = params.keySet();
            for(String key:keySet) {
                String value = (String) params.get(key);
                formBodyBuilder.add(key,value);
            }
            formBody = formBodyBuilder.build();
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {

                    //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    if(cookies.size()>0){
                        String  s = cookies.get(0);
                        session = s.substring(0, s.indexOf(";"));
                    }

                    final String result = response.body().string();
                    XLog.e(result);
                    Gson gson=new Gson();
                    codeBean=gson.fromJson(result, ResCodeBean.class);
                } else {
                    failInfo=response.message().toString();
                }
            }
        });
    }

    public void post(String url, Map<String, Object> params,String coo) {
        FormBody formBody = null;
        if (null != params&&!params.isEmpty()) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            Set<String> keySet = params.keySet();
            for(String key:keySet) {
                String value = (String) params.get(key);
                formBodyBuilder.add(key,value);
            }
            formBody = formBodyBuilder.build();
        }

        Request request = new Request.Builder()
                .addHeader("cookie",coo)
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String result = response.body().string();
                    XLog.e(result);
                    Gson gson=new Gson();
                    codeBean=gson.fromJson(result, ResCodeBean.class);
                } else {
                    failInfo=response.message().toString();
                }
            }
        });
    }

    private String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null||map.isEmpty()) {
            return "";
        }
        StringBuffer params = new StringBuffer("?");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            params.append(entry.getKey());
            params.append("=");
            params.append(entry.getValue());
            params.append("&");
        }
        String str=params.toString();
        return str.substring(0,str.length()-1);
    }
}
