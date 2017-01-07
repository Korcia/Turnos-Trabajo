package es.net_tel.turnostrabajo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView calendario;
    private static final String TAG = "CalendarioActivity";
    private int GRUPOS = 8;
    //private int PORGRUPO = 2;
    private int LONGPATRON = 56;
    private int TIPOSTURNOS = 13;
    private int MAXYEAR = 0x834;
    private String diauno = "01/11/2016";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        calendario = (CalendarView) findViewById(R.id.calendarView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                //long dias_desde = 0;
                String fecha_seleccionada;
                String[] resultado_turnos; // = new String[16];
                //Toast.makeText(getBaseContext(), "Fecha seleccionada es\n\n" + dayOfMonth + " / " + (month + 1)
                //                + " / " + year, Toast.LENGTH_LONG).show();
                fecha_seleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                long dias_desde = 1 + Daybetween("7/3/2016", fecha_seleccionada, "dd/MM/yyyy");
                //Toast.makeText(getBaseContext(), "Dias:\n\n" + dias_desde, Toast.LENGTH_LONG).show();
                resultado_turnos = CalcularTurno((int)dias_desde);
                Log.i(TAG, "Hello " + (month + 1));
                Intent i = new Intent(CalendarioActivity.this, TurnosActivity.class);
                i.putExtra("primeraKey", dias_desde);
                i.putExtra("segundaKey", fecha_seleccionada);
                i.putExtra("terceraKey", resultado_turnos);
                startActivity(i);
            }
        });

    }
    public long Daybetween(String date1,String date2,String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null,Date2 = null;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        } catch(Exception e) {
            e.printStackTrace();
        }
        long mili_date2 = Date2.getTime();
        long mili_date1 = Date1.getTime();
        long diferencia = (mili_date2 - mili_date1)/(24*60*60*1000);
        //return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
        return diferencia;
    }
    public String[] CalcularTurno(int diaspasados) {

        //String[] tipoturno = {"Mañana", "Tarde", "Noche"};
        String[] miembros = { "Ismael / Juani", "Mariló / Sonia", "Paqui / Alicia", "Chon / Fran", "Carmen / Sandra", "Antonia / Mari Carmen", "Eli / Marta", "Mara / Mónica"};
        String[] resultado = new String[16];
        String[] tipoturno = {"Libranza (24 h.)","Libranza (18 h.)","Libranza (15 h.)","08:00 a 15:00","06:00 a 14:00","14:00 a 22:00","15:00 a 21:00","22:00 a 06:00","21:00 a 09:00","21:00 a 06:00","08:00 a 12:00 / 18:00 a 21:00","08:00 a 14:00 / 20:00 a 22:00","09:00 a 21:00"};
        //String [] resultado_tarde = new resultado_tarde [4];
        //String [] resultado_noche = new resultado_noche [2];
        int[][] patron =
                {{0,0,6,6,6,12,9,1,6,10,10,10,0,12,5,5,5,5,5,0,0,10,10,3,3,0,12,0,4,4,4,4,3,0,0,7,7,1,0,8,8,2,6,3,0,0,4,11,11,3,0,7,7,1,0,12},
                {1,6,10,10,10,0,12,5,5,5,5,5,0,0,10,10,3,3,0,12,0,4,4,4,4,3,0,0,7,7,1,0,8,8,2,6,3,0,0,4,11,11,3,0,7,7,1,0,12,0,0,6,6,6,12,9},
                {5,5,5,5,5,0,0,10,10,3,3,0,12,0,4,4,4,4,3,0,0,7,7,1,0,8,8,2,6,3,0,0,4,11,11,3,0,7,7,1,0,12,0,0,6,6,6,12,9,1,6,10,10,10,0,12},
                {10,10,3,3,0,12,0,4,4,4,4,3,0,0,7,7,1,0,8,8,2,6,3,0,0,4,11,11,3,0,7,7,1,0,12,0,0,6,6,6,12,9,1,6,10,10,10,0,12,5,5,5,5,5,0,0},
                {4,4,4,4,3,0,0,7,7,1,0,8,8,2,6,3,0,0,4,11,11,3,0,7,7,1,0,12,0,0,6,6,6,12,9,1,6,10,10,10,0,12,5,5,5,5,5,0,0,10,10,3,3,0,12,0},
                {7,7,1,0,8,8,2,6,3,0,0,4,11,11,3,0,7,7,1,0,12,0,0,6,6,6,12,9,1,6,10,10,10,0,12,5,5,5,5,5,0,0,10,10,3,3,0,12,0,4,4,4,4,3,0,0},
                {6,3,0,0,4,11,11,3,0,7,7,1,0,12,0,0,6,6,6,12,9,1,6,10,10,10,0,12,5,5,5,5,5,0,0,10,10,3,3,0,12,0,4,4,4,4,3,0,0,7,7,1,0,8,8,2},
                {3,0,7,7,1,0,12,0,0,6,6,6,12,9,1,6,10,10,10,0,12,5,5,5,5,5,0,0,10,10,3,3,0,12,0,4,4,4,4,3,0,0,7,7,1,0,8,8,2,6,3,0,0,4,11,11}};
        int i, j, k, l, x, signal;
        int bloques = 0, resto = 0;
        int indice = 0;
        int z = 0, w = 0;
        int desplazamiento;
        int hanpasado;
        //int hanpasado = 27;
        hanpasado = diaspasados;
        //System.out.println("Fecha inicial: " + diauno);
        bloques = hanpasado / LONGPATRON;
        String bloquesAsString = Integer.toString(bloques);
        //System.out.println("Bloques: " + bloquesAsString);
        resto = hanpasado % LONGPATRON;
        String restoAsString = Integer.toString(resto);
        //System.out.println("Desplazaminto último bloque: " + restoAsString);
        for (i = 0; i < GRUPOS; i++) {
            //System.out.println(tipoturno[i]);
            indice = patron[i][resto];
            resultado[z] = miembros[i];
            z++;
            resultado[z] = tipoturno[indice];
            z++;
        }
        //for (w = 0; w < 10; w++) System.out.println(resultado[w]);
        return resultado;
    }

}
