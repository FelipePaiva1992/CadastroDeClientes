package br.com.mec.cadastrodecliente;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Inserir extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void CadastrarClick(View v){
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
        		
        		if(db.insert("clientes", "_id", ctv) > 0){
        			Toast.makeText(getBaseContext(), "Sucesso ao cadastrar o cliente", Toast.LENGTH_SHORT).show();
        		} else {
        			Toast.makeText(getBaseContext(), "Erro ao cadastrar o cliente", Toast.LENGTH_SHORT).show();
        		}
    		}catch (Exception e){
    			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    		}
    		
    	}
    	
    	Intent it = new Intent(getBaseContext(),ListarActivity.class);
    	startActivity(it);
    }
    
}
