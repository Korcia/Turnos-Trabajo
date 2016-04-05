package es.net_tel.turnostrabajo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SplashBienvenida extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    public static final String CONFIG = "FicheroConfig";
    private static final String TAG = "SplashBienvenida";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_bienvenida);


        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //Intent i = new Intent(WelcomeScreen.this, ItemListActivity.class);

                SharedPreferences shared;
                shared = getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
                String pin = shared.getString("codigo", "fallo");
                //Toast.makeText(getBaseContext(), "Codigo: " + pin, Toast.LENGTH_LONG).show();

                if (shared.contains("codigo")) {
                    try {
                        new HttpGetTask(getApplicationContext()).execute(pin).get(2000, TimeUnit.MILLISECONDS);
                        //Toast.makeText(getBaseContext(), "Codigo1: " + pin, Toast.LENGTH_LONG).show();
                        if (HttpGetTask.getEstado_codigo() == "OK") {
                            //Toast.makeText(getBaseContext(), "Codigo2: " + pin, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SplashBienvenida.this, CalendarioActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashBienvenida.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (InterruptedException exception) {
                        Log.e(TAG, "InterruptedException");
                    } catch (TimeoutException exception) {
                        Log.e(TAG, "TimeOutException");
                    } catch (ExecutionException exception) {
                        Log.e(TAG, "ExecutionException");
                    }
                } else {
                    Intent i = new Intent(SplashBienvenida.this, LoginActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }
*/

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

/*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SplashScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://es.net_tel.turnostrabajo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
*/

/*
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SplashScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://es.net_tel.turnostrabajo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
*/
}
