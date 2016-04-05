package es.net_tel.turnostrabajo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static final String CONFIG = "FicheroConfig";

/*
    public static String getEstado_codigo() {
        return estado_codigo;
    }

    public static void setEstado_codigo(String estado_codigo) {
        LoginActivity.estado_codigo = estado_codigo;
    }

    private static String estado_codigo = "";
*/

    //@Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String password = _passwordText.getText().toString();
                try {
                    new HttpGetTask(getApplicationContext()).execute(password).get(2000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException exception) {
                    Log.e(TAG, "InterruptedException");
                } catch (TimeoutException exception) {
                    Log.e(TAG, "TimeOutException");
                } catch (ExecutionException exception) {
                    Log.e(TAG, "ExecutionException");
                }

                //login();
                if (HttpGetTask.getEstado_codigo() == "OK") {
                    SharedPreferences settings = getSharedPreferences(CONFIG, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("codigo", password);
                    editor.commit();

                    //SharedPreferences shared;
                    //shared = getSharedPreferences("es.net_tel", Context.MODE_PRIVATE);
                    //shared.edit().putBoolean("first_time",true).apply();
                    //shared.edit().putString("codigo", password);
                    //Toast.makeText(getBaseContext(), "Codigo: " + password, Toast.LENGTH_LONG).show();
                    //shared.edit().apply();

                    Intent i = new Intent(LoginActivity.this, CalendarioActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    _passwordText.setError("Código Incorrecto");
                    _passwordText.requestFocus();
                    _passwordText.setText("");
                }
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Identificando...");
        progressDialog.show();

        //String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        //String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

/*
    private class HttpGetTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "HttpGetTask";

        // Get your own user name at http://www.geonames.org/login
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
//                httpUrlConnection.setRequestProperty("Content-Type", "application/json");

                httpUrlConnection.setRequestProperty("Authorization", token);
                httpUrlConnection.connect();
                int statuscode = httpUrlConnection.getResponseCode();
                Log.e(TAG, " " + statuscode);
                if (statuscode == 200) {
                    data = "OK";
                    setEstado_codigo(data);
                    shared=getSharedPreferences("es.net_tel.turnostrabajo", Context.MODE_PRIVATE);
                    shared.edit().putBoolean("first_time",false).apply();
                    shared.edit().putString("codigo", "6278");
                    shared.edit().apply();
                    Log.d(TAG, "HttpGetTask: OK " + token);
                } else {
                    data = "NO";
                    setEstado_codigo(data);
                    Log.d(TAG, "HttpGetTask: NO OK " + token);
                }

                //InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
                //data = readStream(in);

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
            //return;
        }
    }
*/
}

