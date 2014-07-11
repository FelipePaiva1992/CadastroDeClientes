package br.com.mec.cadastrodecliente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListarActivity extends Activity{
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listar);
	        
	       
	        
	    }
	 
	 @Override
	protected void onResume() {		
		super.onResume();
		
		 SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
	        
	        //Tabela Clientes
	        StringBuilder sqlCliente = new StringBuilder();
	        sqlCliente.append("CREATE TABLE IF NOT EXISTS clientes(");
	        sqlCliente.append("_id INTEGER PRIMARY KEY,");
	        sqlCliente.append("nome VARCHAR(30),");
	        sqlCliente.append("email VALRCHAR(30));");
	        db.execSQL(sqlCliente.toString());
	        
	        Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);
	        
	        String[] from = {"_id","nome"};
	        int[] to = {R.id.txtID, R.id.txtNome1};
			SimpleCursorAdapter ad = new SimpleCursorAdapter(getBaseContext(), R.layout.listar_model, cursor, from, to, 0);
	        
	        ListView ltwDados = (ListView) findViewById(R.id.listView1);
	        
	        ltwDados.setAdapter(ad);
	        
	        ltwDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

					SQLiteCursor c = (SQLiteCursor) adapter.getAdapter().getItem(position);
					
					Intent it = new Intent(getBaseContext(),Editar.class);
					it.putExtra("id", c.getInt(0));
					startActivity(it);
					
				}
			});
	        
	        db.close();
	}

}
