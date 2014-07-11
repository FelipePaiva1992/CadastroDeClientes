package br.com.mec.cadastrodecliente;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.mec.WSRest.ConexaoWSRest;
import br.com.mec.WSSoap.ConexaoWSSoap;
import br.com.mec.model.Funcionario;

public class Main extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
        
        //Tabela Clientes
        StringBuilder sqlCliente = new StringBuilder();
        sqlCliente.append("CREATE TABLE IF NOT EXISTS clientes(");
        sqlCliente.append("_id INTEGER PRIMARY KEY,");
        sqlCliente.append("nome VARCHAR(30),");
        sqlCliente.append("email VALRCHAR(30));");
        db.execSQL(sqlCliente.toString());
        
    }
    
    public void ListarClientesClick(View v){
    	Intent it = new Intent(getBaseContext(),ListarActivity.class);
    	startActivity(it);
    }
    
    public void InserirClientesClick(View v){
    	Intent it = new Intent(getBaseContext(),Inserir.class);
    	startActivity(it);
    }
    
    private ProgressDialog dialog;
    private Handler handler = new Handler();
    private static final String CATEGORIA = "appTeste";
    
    public void chamarWSClick(View v){
    	
    	dialog = ProgressDialog.show(this, "Exemplo", "Chamando o web service, por favor aguarde...", false, true);
    	
    	new Thread(){
    		
    		public void run() {
    				try{
    					
    					ConexaoWSSoap ws = new ConexaoWSSoap();
    					
    					ArrayList<ArrayList<String>> parameters = new ArrayList<ArrayList<String>>();
    					ArrayList<String> nameValue = new ArrayList<String>();
    					nameValue.add("idFuncionario");
    					nameValue.add("1");
    					parameters.add(nameValue);
    					
    					//chamar WS
    					final JSONArray result = ws.getResultSoap("listarFuncionarios",null);
    					    					
    					handler.post(new Runnable() {
    						
    						@Override
    						public void run() {
    							
    							LinearLayout layout = (LinearLayout) findViewById(R.id.layoutMain);
    							LinearLayout layoutResultado = (LinearLayout) findViewById(R.id.layoutResultado);
    							
    							layoutResultado.removeAllViews();
    							
    							for(int i = 0; i < result.length(); i++){
    								try {
										for(int j = 0; j < result.getJSONObject(i).length(); j++){
											TextView textView1 = new TextView(Main.this);
											textView1.setText(result.getJSONObject(i).getString(String.valueOf(j)));
											layoutResultado.addView(textView1);
										}
									} catch (JSONException e) {
										Log.e(CATEGORIA, e.getMessage());
									}
    								
    								TextView textView1 = new TextView(Main.this);
									textView1.setText("--||--");
									layoutResultado.addView(textView1);
    							}    							
    							setContentView(layout);			
    						}
    					});
    					
    				}catch(Exception e){
    					Log.i(CATEGORIA, e.getMessage());
    				}finally{
    					dialog.dismiss();
    				}
    				
    		}   
    		
    	}.start();
    }

    public void chamarWSRestClick(View v){
    	
    	dialog = ProgressDialog.show(this, "Exemplo", "Chamando o web service, por favor aguarde...", false, true);
    	
    	new Thread(){
    		
    		public void run() {
    				try{
    					ConexaoWSRest ws = new ConexaoWSRest();
    					
    					ArrayList<ArrayList<String>> parameters = new ArrayList<ArrayList<String>>();
    					ArrayList<String> nameValue = new ArrayList<String>();
    					nameValue.add("idEmpresa");
    					nameValue.add("1");
    					parameters.add(nameValue);
    					
    					//chamar WS
    					final List<Funcionario> result = ws.getListaFuncionarios("listFuncionarioByEmpresa", parameters);
    					
    					handler.post(new Runnable() {
    						
    						@Override
    						public void run() {
    							
    							LinearLayout layout = (LinearLayout) findViewById(R.id.layoutMain);
    							LinearLayout layoutResultado = (LinearLayout) findViewById(R.id.layoutResultadoCidades);
    							
    							layoutResultado.removeAllViews();
    							
    							for(int i = 0; i < result.size(); i++){
    								TextView textView1 = new TextView(Main.this);
        							textView1.setText(result.get(i).getNmFuncionario());
        							layoutResultado.addView(textView1);
    							}    							
    							setContentView(layout);			
    						}
    					});
    					
    				}catch(Exception e){
    					Log.e(CATEGORIA, e.getMessage());
    				}finally{
    					dialog.dismiss();
    				}
    				
    		}   
    		
    	}.start();
    }
    
}
