package adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import entities.Nota;

public class AdaptadorBD {
	private static final String BASEDATOS = "carrito.sqlite";
	private static final int VERSION = 1;

	private static final String SQLCREAR1 ="create table notas (id integer primary key autoincrement, titulo text not null, fecha text not null, body text not null)";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase bd;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, BASEDATOS, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(SQLCREAR1);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS notas");

			onCreate(db);
		}
	}

	public AdaptadorBD(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
        
	}

	// ---abrir la base de datos---
	public void abrirW() throws SQLException {
		bd = DBHelper.getWritableDatabase();
	}

	public void abrirR() throws SQLException {
		bd = DBHelper.getReadableDatabase();
	}

	// ---cerrar la base de datos---
	public void cerrar() {
		DBHelper.close();
	}

	public ArrayList<Nota> obtenerNotas() {
		abrirR();
		Cursor cursor = bd.rawQuery("SELECT * FROM notas", null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		cerrar();

		ArrayList<Nota>lista=new ArrayList<>();
		while(!cursor.isAfterLast()){
			Nota n=new Nota();
			n.setId(cursor.getString(0));
			n.setTitulo(cursor.getString(1));
			n.setFecha(cursor.getString(2));
			n.setTexto(cursor.getString(3));
			lista.add(n);

			cursor.moveToNext();
		}
		cursor.close();

		return lista;
	}

	public void borrarNota(Nota n) {
		abrirR();

		bd.execSQL("DELETE FROM notas WHERE id=" + n.getId());
		cerrar();
	}

	public Nota updateNota(Nota n){
		abrirR();
		bd.execSQL("UPDATE notas SET titulo = '" + n.getTitulo() + "', body = '" + n.getTexto() + "' WHERE id=" + n.getId());

		Cursor cursor = bd.rawQuery("SELECT * FROM notas WHERE id="+n.getId(), null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		Nota nueva = new Nota();
		nueva.setId(cursor.getString(0));
		nueva.setTitulo(cursor.getString(1));
		nueva.setFecha(cursor.getString(2));
		nueva.setTexto(cursor.getString(3));

		cerrar();

		return nueva;
	}

	public Nota crearNota(){
		abrirR();
		bd.execSQL("INSERT INTO notas VALUES (null, '' ,CURRENT_TIMESTAMP, '' )");
		Cursor cursor = bd.rawQuery("SELECT * from notas WHERE titulo=''", null);
		cursor.moveToFirst();

		Nota n = new Nota();
		n.setId(cursor.getString(0));
		n.setTitulo(cursor.getString(1));
		n.setFecha(cursor.getString(2));
		n.setTexto(cursor.getString(3));

		cerrar();

		return n;
	}
}