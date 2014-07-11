package br.com.mec.cadastrodecliente;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);
        
        Intent it= getIntent();
        
        int id = it.getIntExtra("id", 0);
        
        SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
        
        Cursor cursor = db.rawQuery("SELECT * FROM clientes WHERE _id = ?", new String[]{String.valueOf(id)});
        
        if(cursor.moveToFirst()){
        	EditText txtNome = (EditText) findViewById(R.id.txtNome);
        	EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        	
        	txtNome.setText(cursor.getString(1));
        	txtEmail.setText(cursor.getString(2));
        	
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void AtualizarClick(View v){
    	EditText txtNome = (EditText) findViewById(R.id.txtNome);
    	EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
    	 
    	if(txtNome.getText().toString().length() <= 0){
    		txtNome.setError("Preencher Campo Nome");
    		txtNome.requestFocus();
    	}else if(txtEmail.getText().toString().length() <= 0){
    		txtEmail.setError("Preencher Campo Email");
    		txtEmail.requestFocus();
    	} else {
    		try{
    			SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
        		
        		ContentValues ctv = new ContentValues();
        		ctv.put("nome", txtNome.getText().toString());
        		ctv.put("email", txtEmail.getText().toString());
        		
        		Intent it= getIntent();
        	        
    	        int id = it.getIntExtra("id", 0);
        		
        		if(db.update("clientes", ctv, "_id=?", new String[]{String.valueOf(id)}) > 0){
        			Toast.makeText(getBaseContext(), "Sucesso ao atualizar o cliente", Toast.LENGTH_SHORT).show();
        		} else {
        			Toast.makeText(getBaseContext(), "Erro ao atualizar o cliente", Toast.LENGTH_SHORT).show();
        		}
    		}catch (Exception e){
    			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    		}
    		
    	}
    	
    	Intent it = new Intent(getBaseContext(),ListarActivity.class);
    	startActivity(it);
    	
    }
    
    public void ApagarClick(View v){
    		try{
    			final SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
        		
        		Intent it= getIntent();
        	        
    	        final int id = it.getIntExtra("id", 0);
    	        
    	        Builder msg = new Builder(Editar.this);
    	        msg.setMessage("Deseja apagar este cliente?");
    	        msg.setNegativeButton("Não", null);
    	        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(db.delete("clientes", "_id=?", new String[]{String.valueOf(id)}) > 0){
		        			Toast.makeText(getBaseContext(), "Sucesso ao apagar o cliente", Toast.LENGTH_SHORT).show();
		        		} else {
		        			Toast.makeText(getBaseContext(), "Erro ao apagar o cliente", Toast.LENGTH_SHORT).show();
		        		}	
						
						Intent it = new Intent(getBaseContext(),ListarActivity.class);
			        	startActivity(it);
					}
					
				});
        		
    	        msg.show();
        		
    		}catch (Exception e){
    			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    		}		
    		
    		
    	}
}
