package com.bung.bungapp.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bung.bungapp.BaseApplication;
import com.bung.bungapp.R;
import com.bung.bungapp.model.ChickenStoreInformation;
import com.bung.bungapp.model.ChickenStoreResult;
import com.bung.bungapp.model.LoginResult;
import com.bung.bungapp.model.Party;
import com.bung.bungapp.model.User;
import com.bung.bungapp.util.Async;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.ContentType;

/**
 * Created by rok on 2015. 11. 14..
 */
public class ApiCaller {

    private static Gson gson = new Gson();
    private static String GCM_SENDER_ID = "363210708388";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String accessToken;

    public static void checkSessionAlive(Callback<User> callback) {
        if (accessToken != null) try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", accessToken);

            HttpEntity httpEntity = new ByteArrayEntity(jsonObject.toString().getBytes(), ContentType.APPLICATION_JSON);
            client.post(BaseApplication.getInstance(), "http://52.68.214.255:8000/get_user/", httpEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("checkSessionAlive", "onSuccess" + responseString);
                    User user = gson.fromJson(responseString, User.class);
                    if (user != null) {
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("checkSessionAlive", "onFailure");
                    callback.onFailure();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        else {
            callback.onFailure();
        }
    }

    public static void registerUser(String nickname, String username, String password, Callback<User> callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("last_name", nickname);
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            HttpEntity httpEntity = new ByteArrayEntity(jsonObject.toString().getBytes(), ContentType.APPLICATION_JSON);
            client.post(BaseApplication.getInstance(), "http://52.68.214.255:8000/register/", httpEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString.contains("ok")) {
                        User user = new User(username, nickname);
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    callback.onFailure();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void authenticate(String username, String password, Callback<Boolean> callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            HttpEntity httpEntity = new ByteArrayEntity(jsonObject.toString().getBytes(), ContentType.APPLICATION_JSON);
            client.post(BaseApplication.getInstance(), "http://52.68.214.255:8000/login_user/", httpEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        LoginResult loginResult = gson.fromJson(responseString, LoginResult.class);
                        if (loginResult != null) {
                            ApiCaller.setAccessToken(loginResult.access_token);
                            BaseApplication.getSharedPreferences().edit().putString("access_token", accessToken).apply();
                            callback.onSuccess(true);
                        } else {
                            callback.onSuccess(false);
                        }
                    } catch (Exception e) {
                        callback.onSuccess(false);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    callback.onFailure();
                }
            });
        } catch (Exception e) {
            callback.onFailure();
        }
    }

    public static void setAccessToken(String accessToken) {
        ApiCaller.accessToken = accessToken;
    }

    public static void createParty(Party party, Callback<Party> callback) {
        JsonElement jsonElement = gson.toJsonTree(party, Party.class);
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        asJsonObject.addProperty("access_token", accessToken);
        String json = asJsonObject.toString();

        HttpEntity httpEntity = new ByteArrayEntity(json.getBytes(), ContentType.APPLICATION_JSON);
        client.post(BaseApplication.getInstance(), "http://52.68.214.255:8000/save_party/", httpEntity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    Log.d("ApiCaller", "onSuccess" + response);
                    Party party = gson.fromJson(response, Party.class);
                    callback.onSuccess(party);
                } catch (Exception e) {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure();
            }
        });
    }

    public static void getParty(String partyId, Callback<Party> callback) {
        client.post("http://52.68.214.255:8000/load_party/" + partyId, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Party party = gson.fromJson(response, Party.class);
                callback.onSuccess(party);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure();
            }
        });
    }

    public static void getParties(Callback<List<Party>> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("access_token", accessToken);
        String json = jsonObject.toString();
        HttpEntity httpEntity = new ByteArrayEntity(json.getBytes(), ContentType.APPLICATION_JSON);
        client.post(BaseApplication.getInstance(), "http://52.68.214.255:8000/load_parties/", httpEntity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                if (response != null) {
                    try {
                        JSONArray array = new JSONArray(response);
                        List<Party> partyList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Party party = gson.fromJson(object.toString(), Party.class);
                            partyList.add(party);
                        }
                        callback.onSuccess(partyList);
                    } catch (JSONException e) {
                        callback.onFailure();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure();
            }
        });
    }

    public static void getChickenStores(double latitude, double longitude, Callback<ChickenStoreResult> callback) {
        String apiKey = BaseApplication.getInstance().getString(R.string.google_maps_key);
        String url = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&types=food&name=치킨&key=%s&language=ko",
                latitude, longitude, 3000, apiKey);
        client.get(url, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ChickenStoreResult chickenStoreResult = gson.fromJson(responseString, ChickenStoreResult.class);
                callback.onSuccess(chickenStoreResult);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure();
            }
        });
    }

    public static void getChickenStoreInformation(String placeId, Callback<ChickenStoreInformation> callback) {
        String apiKey = BaseApplication.getInstance().getString(R.string.google_maps_key);
        String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=%s&language=ko",
                placeId, apiKey);

        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ChickenStoreInformation chickenStoreInformation = gson.fromJson(responseString, ChickenStoreInformation.class);
                callback.onSuccess(chickenStoreInformation);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure();
            }
        });
    }

    /**
     * Register current device to the GCM server asynchronously
     *
     * @param context application's context.
     */
    public static void getGcmRegistrationId(final Context context, Callback<String> callback) {
        Async.background(() -> {
            SharedPreferences prefs = context.getSharedPreferences("gcm", Context.MODE_PRIVATE);
            String regid = prefs.getString("gcm_regid", null);
            if (regid == null) try {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                regid = gcm.register(GCM_SENDER_ID);
                String msg = "Device registered, registration ID=" + regid;

                // For this demo: we don't need to send it because the device
                // will send upstream messages to a server that echo back the
                // message using the 'from' address in the message.


            } catch (IOException ex) {
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }

            if (regid != null) {
                // Persist the regID - no need to register again.
                prefs.edit().putString("gcm_regid", regid).apply();
                callback.onSuccess(regid);
            } else {
                callback.onFailure();
            }
        });
    }
}
