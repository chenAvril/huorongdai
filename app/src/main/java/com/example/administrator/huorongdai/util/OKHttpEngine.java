package com.example.administrator.huorongdai.util;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.xframe.XFrame;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.IHttpEngine;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
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
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.internal.Util.EMPTY_REQUEST;

/**
 * OKHttp
 */
public class OKHttpEngine implements IHttpEngine {

    private static OkHttpClient client = null;
    private int cacheSize = 10 * 1024 * 1024;

    public OKHttpEngine() {
        if(client==null){
            synchronized (OKHttpEngine.class){
                if(client==null){
                    client = new OkHttpClient().newBuilder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .cache(new Cache(XFrame.getContext().getCacheDir(), cacheSize))
                            //.connectionPool(new ConnectionPool(5,1,TimeUnit.SECONDS))
                            .build();
                }
            }
        }
    }

    @Override
    public void get(String url, Map<String, Object> params, final HttpCallBack callBack) {
        Request request = new Request.Builder().url(url+getUrlParamsByMap(params)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    XHttp.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String json="{\"data\":"+result+"}";
                            //XLog.e(json);
                            //gson
                            Gson gson=new Gson();
                            callBack.onSuccess(gson.fromJson(json, XHttp.analysisClassInfo(callBack)));
                        }
                    });
                } else {
                    XHttp.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(response.message().toString());
                        }
                    });
                }
                if(response.body()!=null){
                    response.body().close();
                }
            }
        });
    }


    @Override
    public void post(final String url, Map<String, Object> params, final HttpCallBack callBack) {
        RequestBody body = EMPTY_REQUEST;
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

//        if (null != params&&!params.isEmpty()) {
//            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//            for (String key : params.keySet()) {
//                Object value = params.get(key);
//                if (value instanceof File) {
//                    File file = (File) value;
//                    builder.addFormDataPart(key, file.getName(),
//                            RequestBody.create(MediaType.parse("image/png"), file));//"application/octet-stream"
//                }
//                if (value instanceof ArrayList) {
//                    ArrayList<Object> lists = (ArrayList) value;
//                    for (Object obj : lists) {
//                        if (obj instanceof File) {
//                            File file = (File) obj;
//                            builder.addFormDataPart(key, file.getName(),
//                                    RequestBody.create(MediaType.parse("image/png"), file));
//                        }
//                    }
//                } else {
//                    builder.addFormDataPart(key, value.toString());
//                }
//            }
//            body = builder.build();
//        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(url.equals(Path.login_url)){
                    //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    if(cookies.size()>0&&!XEmptyUtils.isEmpty(cookies)){
                        XPreferencesUtils.put("login_cookies",cookies.get(0));
                    }
                }
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    XLog.e(result);
                    XHttp.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //String json="{\"data\":"+result+"}";
                            //gson
                            Gson gson=new Gson();
                            callBack.onSuccess(gson.fromJson(result, XHttp.analysisClassInfo(callBack)));
                        }
                    });
                } else {
                    XHttp.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailed(response.message().toString());
                        }
                    });
                }
                if(response.body()!=null){
                    response.body().close();
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
