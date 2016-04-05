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
    private int GRUPOS = 7;
    private int PORGRUPO = 2;
    private int LONGPATRON = 98;
    private int TIPOSTURNOS = 3;
    private int MAXYEAR = 0x834;
    private String diauno = "07/03/2016";

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
                String[] resultado_turnos = new String[10];
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
        String[][] miembros = {{"Antonia", "Paqui"}, {"Chon", "Mariló"}, {"Fran", "Mara"}, {"Eli", "Mónica"}, {"Ismael", "Carmen"}, {"Mª del Carmen", "Marta"}, {"Sandra", "Sonia"}};
        String[] resultado = new String[10];
        //String [] resultado_tarde = new resultado_tarde [4];
        //String [] resultado_noche = new resultado_noche [2];
        int[][] patron = {{5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 6, 7, 6, 7, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 3, 2, 3, 4, 7, 4, 7, 4, 7, 4, 7, 4, 7, 5, 7, 5, 7, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 1, 3, 1, 3, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 5, 4, 5, 4, 1, 7, 1, 7, 1, 7, 1, 7, 1, 7, 1, 6, 1, 6, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 2, 4, 2, 4},
                {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 6, 7, 6, 7, 4, 7, 4, 7, 4, 7, 4, 7, 4, 7, 2, 3, 2, 3, 3, 6, 3, 6, 3, 6, 3, 6, 3, 6, 5, 7, 5, 7, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 1, 3, 1, 3, 1, 7, 1, 7, 1, 7, 1, 7, 1, 7, 5, 4, 5, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 1, 6, 1, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 2, 4, 2, 4},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 1, 1, 1, 1}};
        int i, j, k, l, x, signal;
        int bloques = 0, resto = 0;
        int indice = 0;
        int z = 0, w = 0;
        int desplazamiento;
        int hanpasado;
        //int hanpasado = 27;
        hanpasado = diaspasados;
        //System.out.println("Fecha inicial: " + diauno);
        bloques = hanpasado / (LONGPATRON / PORGRUPO);
        String bloquesAsString = Integer.toString(bloques);
        //System.out.println("Bloques: " + bloquesAsString);
        resto = hanpasado % (LONGPATRON / PORGRUPO);
        String restoAsString = Integer.toString(resto);
        //System.out.println("Desplazaminto último bloque: " + restoAsString);
        indice = (resto == 0) ? LONGPATRON - PORGRUPO : resto * PORGRUPO - PORGRUPO;
        for (i = 0; i < TIPOSTURNOS; i++) {
            //System.out.println(tipoturno[i]);
            for (j = 0; j < PORGRUPO; j++) {
                x = patron[i][indice + j] - 1;
                signal = -1;
                for (l = 0; l < j; l++) {
                    if (patron[i][indice + l] - 1 == x) signal = 1;
                }
                if (signal == -1) {
                    for (k = 0; k < PORGRUPO; k++) {
                        //System.out.println(miembros[x][k]);
                        resultado[z] = miembros[x][k];
                        z++;
                    }
                }
            }
        }
        //for (w = 0; w < 10; w++) System.out.println(resultado[w]);
        return resultado;
    }

}
