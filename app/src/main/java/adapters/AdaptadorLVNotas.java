package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.davidrojomartin.anotaciones.R;

import java.util.ArrayList;


import entities.Nota;


public class AdaptadorLVNotas extends ArrayAdapter<Nota> {
	private Context context;
	private LayoutInflater inflater;
	private int filaxml;

	static class ViewHolder {
		TextView tvTitulo;
		TextView tvFecha;
	}

	public AdaptadorLVNotas(Context context, int resource, ArrayList<Nota> objects) {
		super(context, resource, objects);
		this.filaxml = resource;
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	public View getView(int position, View layoutFila, ViewGroup parent) {
		ViewHolder viewholder;

		View view = layoutFila;

		if(view == null){
			view = (View) inflater.inflate(filaxml, null);
			viewholder=new ViewHolder();

			viewholder.tvTitulo=(TextView)view.findViewById(R.id.tvTitulo);
			viewholder.tvFecha=(TextView)view.findViewById(R.id.tvFecha);

			view.setTag(viewholder);
		}

		Nota n = getItem(position);
        viewholder=(ViewHolder) view.getTag();

		viewholder.tvTitulo.setText(n.getTitulo());
		viewholder.tvFecha.setText(n.getFecha());

		return view;
	}

}