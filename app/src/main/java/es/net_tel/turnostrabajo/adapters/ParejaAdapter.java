package es.net_tel.turnostrabajo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import es.net_tel.turnostrabajo.R;

public class ParejaAdapter extends BaseAdapter {
    private Context mContexto;
    private LayoutInflater mInflater;
    private ArrayList<String> mDatos;

    public ParejaAdapter(Context context, ArrayList<String> items) {
        mContexto = context;
        mDatos = items;
        mInflater = (LayoutInflater) mContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<String> getData() {
        return mDatos;
    }

    @Override
    public int getCount() {
        return mDatos.size();
    }

    @Override
    public String getItem(int position) {
        return mDatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.pareja_info, parent, false);

        TextView filaTextView = (TextView) rowView.findViewById(R.id.fila_pareja);
        String mfilaTurno = (String) getItem(position);
        filaTextView.setText(mfilaTurno);
//        if (position % 2 == 1) {
//            filaTextView.setBackgroundColor(Color.BLUE);
//        } else {
//            //filaTextView.setBackgroundColor(Color.GRAY);
//            filaTextView.setTextColor(Color.BLACK);
//        }


        return rowView;
    }
}
