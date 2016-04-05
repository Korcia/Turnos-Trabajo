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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TurnosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fecha = getIntent().getExtras().getString("segundaKey", "defaultKey");
        String[] datos = getIntent().getExtras().getStringArray("terceraKey");

        setContentView(R.layout.activity_turnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //t.setText(fecha);
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
                TextView t2=(TextView)findViewById(R.id.fechaView);
                String fecha_larga2 = t2.getText().toString();
                openScreenshot(imagePath, fecha_larga2);
                //Snackbar.make(view, "Enviar los turnos", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
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

}
