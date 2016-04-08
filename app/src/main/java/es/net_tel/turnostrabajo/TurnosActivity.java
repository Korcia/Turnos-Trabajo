package es.net_tel.turnostrabajo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class TurnosActivity extends AppCompatActivity {

    static String fecha;
    static String[] datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String fechaprincipal = getIntent().getExtras().getString("segundaKey", "defaultKey");
        String[] datosprincipal = getIntent().getExtras().getStringArray("terceraKey");
        fecha = fechaprincipal;
        datos = Arrays.copyOf(datosprincipal,datosprincipal.length);

        setContentView(R.layout.activity_turnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rellenaCampos(fecha, datos);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v1 = getWindow().getDecorView().getRootView();
                Bitmap bmap = screenShot(v1);
                File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.jpg");
                try {
                    FileOutputStream outputStream = new FileOutputStream(imagePath);
                    int quality = 50;
                    bmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    Log.e("GREC", e.getMessage(), e);
                } catch (IOException e) {
                    Log.e("GREC", e.getMessage(), e);
                }
                TextView t2 = (TextView) findViewById(R.id.fechaView);
                String fecha_larga2 = t2.getText().toString();
                openScreenshot(imagePath, fecha_larga2);
                //Snackbar.make(view, "Enviar los turnos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button btn = (Button) findViewById(R.id.boton_dia_anterior);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCalendarioActivity mat = new MaterialCalendarioActivity();
                String fec = unDiaMenos(fecha);
                fecha = fec;
                long dias_desde = 1 + mat.Daybetween("7/3/2016", fec, "dd/MM/yyyy");
                String[] res = mat.CalcularTurno((int) dias_desde);
                datos = res;
                rellenaCampos(fec, res);
                //onRestart();
            }
        });

        Button btn2 = (Button) findViewById(R.id.boton_dia_siguiente);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCalendarioActivity mat = new MaterialCalendarioActivity();
                String fec = unDiaMas(fecha);
                fecha = fec;
                long dias_desde = 1 + mat.Daybetween("7/3/2016", fec, "dd/MM/yyyy");
                String[] res = mat.CalcularTurno((int)dias_desde);
                datos = res;
                rellenaCampos(fec, res);
                //onRestart();
            }
        });

    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        //finish();
        //startActivity(getIntent());
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    private void openScreenshot(File imageFile, String fecha_larga) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(imageFile);
        //intent.setDataAndType(uri, "image/*");
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, "Estos son los turnos del " + fecha_larga);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //Intent.createChooser(intent, "Enviar");
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            //ToastHelper.MakeShortText("Whatsapp have not been installed.");
            Toast.makeText(getBaseContext(), "Whatsapp no ha sido instalado", Toast.LENGTH_LONG).show();
        }
        //startActivity(Intent.createChooser(intent, "Enviar"));
    }
    private void rellenaCampos(String fecha, String[] datos) {

        TextView t=(TextView)findViewById(R.id.fechaView);
        SimpleDateFormat sdf = new SimpleDateFormat(" EEEE dd 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha2 = formateador.parse(fecha);
            String fecha_larga = sdf.format(fecha2);
            t.setText(fecha_larga);
        } catch (ParseException e) {
            Log.e("TAG", "error");
        }
        TextView emp01 = (TextView)findViewById(R.id.mananaempleado1View);
        TextView emp02 = (TextView)findViewById(R.id.mananaempleado2View);
        TextView emp03 = (TextView)findViewById(R.id.mananaempleado3View);
        TextView emp04 = (TextView)findViewById(R.id.mananaempleado4View);
        TextView emp05 = (TextView)findViewById(R.id.mananaempleado5View);
        TextView emp06 = (TextView)findViewById(R.id.mananaempleado6View);
        TextView emp07 = (TextView)findViewById(R.id.mananaempleado7View);
        TextView emp08 = (TextView)findViewById(R.id.mananaempleado8View);
        TextView emp09 = (TextView)findViewById(R.id.mananaempleado9View);
        TextView emp10 = (TextView)findViewById(R.id.mananaempleado10View);
        if((datos != null ? datos[0] : null) != null) emp01.setText(datos[0]);
        if((datos != null ? datos[1] : null) != null) emp02.setText(datos[1]);
        if((datos != null ? datos[2] : null) != null) emp03.setText(datos[2]);
        if((datos != null ? datos[3] : null) != null) emp04.setText(datos[3]);
        if((datos != null ? datos[4] : null) != null) emp05.setText(datos[4]);
        if((datos != null ? datos[5] : null) != null) emp06.setText(datos[5]);
        if((datos != null ? datos[6] : null) != null) emp07.setText(datos[6]);
        if((datos != null ? datos[7] : null) != null) emp08.setText(datos[7]);
        if((datos != null ? datos[8] : null) != null) emp09.setText(datos[8]);
        if((datos != null ? datos[9] : null) != null) emp10.setText(datos[9]);
    }
    private String unDiaMenos(String fec) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date Date1 = null,Date2 = null;

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(fec));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -1);  // number of days to add
        return sdf.format(c.getTime());
    }

    private String unDiaMas(String fec) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date Date1 = null,Date2 = null;

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(fec));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        return sdf.format(c.getTime());
    }

}
