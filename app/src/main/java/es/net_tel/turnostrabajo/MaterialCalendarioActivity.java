package es.net_tel.turnostrabajo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Shows off the most basic usage
 */
public class MaterialCalendarioActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private static final String TAG = "CalendarioActivity";
    private int GRUPOS = 7;
    private int PORGRUPO = 2;
    private int LONGPATRON = 98;
    private int TIPOSTURNOS = 3;
    private int MAXYEAR = 0x834;
    private String diauno = "07/03/2016";

    @Bind(R.id.materialcalendarView)
    MaterialCalendarView calendario;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_calendario);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);

        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date fechaMinima = null;
        try {
            fechaMinima = sdformat.parse(diauno);
        } catch(Exception e) {
            e.printStackTrace();
        }

        calendario.setMinimumDate(fechaMinima);
        //Calendar calendar = Calendar.getInstance();
        calendario.setSelectedDate(calendar.getTime());

        calendario.setOnDateChangedListener(this);
        calendario.setOnMonthChangedListener(this);
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @OnClick(R.id.fab)
    void OnFabClicked() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        showDatePickerDialog(this, calendario.getSelectedDate(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String fecha_seleccionada;
                String[] resultado_turnos; // = new String[10];
                calendario.setSelectedDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
                long dias_desde = 1 + Daybetween("7/3/2016", getSelectedDatesString(), "dd/MM/yyyy");
                //Toast.makeText(getBaseContext(), "Días desde:\n\n" + (int) dias_desde, Toast.LENGTH_LONG).show();
                //Toast.makeText(getBaseContext(), "getselecteddate:\n\n" + getSelectedDatesString(), Toast.LENGTH_LONG).show();
                resultado_turnos = CalcularTurno((int) dias_desde);
                Intent i = new Intent(MaterialCalendarioActivity.this, TurnosActivity.class);
                i.putExtra("primeraKey", dias_desde);
                i.putExtra("segundaKey", getSelectedDatesString());
                i.putExtra("terceraKey", resultado_turnos);
                startActivity(i);

            }
        });
    }

    @OnCheckedChanged(R.id.check_text_appearance)
    void onTextAppearanceChecked(boolean checked) {
        if (checked) {
            calendario.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Large);
            calendario.setDateTextAppearance(R.style.TextAppearance_AppCompat_Medium);
            calendario.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        } else {
            calendario.setHeaderTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Header);
            calendario.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
            calendario.setWeekDayTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_WeekDay);
        }
        calendario.setShowOtherDates(checked ? MaterialCalendarView.SHOW_ALL : MaterialCalendarView.SHOW_NONE);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView calendario, @Nullable CalendarDay fecha_selec, boolean selected) {
        String fecha_seleccionada;
        String[] resultado_turnos = new String[10];
        calendario.setSelectedDate(CalendarDay.from(fecha_selec.getYear(), fecha_selec.getMonth(), fecha_selec.getDay()));
        long dias_desde = 1 + Daybetween("7/3/2016", getSelectedDatesString(), "dd/MM/yyyy");
        //Toast.makeText(getBaseContext(), "Días desde:\n\n" + (int) dias_desde, Toast.LENGTH_LONG).show();
        resultado_turnos = CalcularTurno((int) dias_desde);
        Intent i = new Intent(MaterialCalendarioActivity.this, TurnosActivity.class);
        i.putExtra("primeraKey", dias_desde);
        i.putExtra("segundaKey", getSelectedDatesString());
        i.putExtra("terceraKey", resultado_turnos);
        startActivity(i);

    }

    @Override
    public void onMonthChanged(MaterialCalendarView calendario, CalendarDay date) {
        //noinspection ConstantConditions
        if(getActionBar() != null) {
            getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
        }
    }

    private String getSelectedDatesString() {
        CalendarDay date = calendario.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    public static void showDatePickerDialog(Context context, CalendarDay day,
                                            DatePickerDialog.OnDateSetListener callback) {
        if (day == null) {
            day = CalendarDay.today();
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, android.R.style.Theme_Holo_Dialog, callback, day.getYear(), day.getMonth(), day.getDay()
        );
        //dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }

    public long Daybetween(String date1,String date2,String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
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
