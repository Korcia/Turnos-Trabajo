package es.net_tel.turnostrabajo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TurnosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fecha = getIntent().getExtras().getString("segundaKey","defaultKey");
        String[] datos = getIntent().getExtras().getStringArray("terceraKey");
        setContentView(R.layout.activity_turnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView t=(TextView)findViewById(R.id.fechaView);
        t.setText(fecha);
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
                Snackbar.make(view, "Enviar los turnos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

}
