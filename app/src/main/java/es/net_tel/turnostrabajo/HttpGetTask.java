package es.net_tel.turnostrabajo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private static final String TAG = "HttpGetTask";
    public static final String CONFIG = "FicheroConfig";
    public static String estado_codigo = "";

    public HttpGetTask(Context context) {
        mContext = context;
    }
    public static String getEstado_codigo() {
        return estado_codigo;
    }

    public static void setEstado_codigo(String estado_codigo) {
        HttpGetTask.estado_codigo = estado_codigo;
    }

    private static final String URL = "http://turnos.sites.djangoeurope.com/users/";

    @Override
    protected String doInBackground(String... params) {
        String kuku = params[0];
        String data = "";
        String token = "Token " + kuku;
        HttpURLConnection httpUrlConnection = null;
        SharedPreferences shared;

        try {
            httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();
            httpUrlConnection.setRequestMethod("GET");

            httpUrlConnection.setRequestProperty("Authorization", token);
            httpUrlConnection.connect();
            int statuscode = httpUrlConnection.getResponseCode();
            Log.e(TAG, " " + statuscode);
            if (statuscode == 200) {
                data = "OK";
                setEstado_codigo(data);
                //SharedPreferences settings =  mContext.getSharedPreferences(CONFIG, 0);
                //shared = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
                //String pin = shared.getString("codigo", "fallo");

                Log.d(TAG, "HttpGetTask: OK " + token);
            } else {
                data = "NO";
                setEstado_codigo(data);
                Log.d(TAG, "HttpGetTask: NO OK " + token);
            }

        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
        } finally {
            if (null != httpUrlConnection)
                httpUrlConnection.disconnect();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        setEstado_codigo(result);
    }

}
