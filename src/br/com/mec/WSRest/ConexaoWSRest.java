package br.com.mec.WSRest;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import br.com.mec.model.Cidades;
import br.com.mec.model.Funcionario;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConexaoWSRest {

    private static final String URL_WS = "http://portalacqua.sytes.net:8100/WSRamaisRest/api/ramais/";
	private static final String CATEGORIA = "appTeste";
    
    public List<Cidades> getListaCidades(String METHOD_NAME,ArrayList<ArrayList<String>> param){

    	String[] resposta;
    	
    	if(param != null){
    		String METHOD_PARAMETERS = montarURLParam(param);
    		resposta = new WebServiceCidades().get(URL_WS + METHOD_NAME + METHOD_PARAMETERS);	
    		Log.i(CATEGORIA, URL_WS + METHOD_NAME + METHOD_PARAMETERS);
		}else{
			resposta = new WebServiceCidades().get(URL_WS + METHOD_NAME);
		}	    
	     
	    String jsonArrayString = resposta[1].toString();
	     
	    if (resposta[0].equals("200")) {
	        ArrayList<Cidades> listaCidades = new ArrayList<Cidades>();
	        JsonParser parser = new JsonParser();
	        JsonObject object = parser.parse(jsonArrayString).getAsJsonObject();
	        
	        JsonArray arrayCidades = object.getAsJsonArray("cidade");
	         
	        for (int i = 0; i < arrayCidades.size(); i++) {
	        	JsonObject jsonObject =  (JsonObject) arrayCidades.get(i);
	        	Cidades cidades = new Cidades();
	        	cidades.setIdCidade(jsonObject.get("idCidade").toString());
	        	cidades.setNmCidade(jsonObject.get("nmCidade").toString());
	        	listaCidades.add(cidades);        	
	        }
	        return listaCidades;
	     }else{
	    	 return null;
	     }
    }
    
    public List<Funcionario> getListaFuncionarios(String METHOD_NAME,ArrayList<ArrayList<String>> param){

    	String[] resposta;
    	
    	if(param != null){
    		String METHOD_PARAMETERS = montarURLParam(param);
    		resposta = new WebServiceCidades().get(URL_WS + METHOD_NAME + METHOD_PARAMETERS);	
    		Log.i(CATEGORIA, URL_WS + METHOD_NAME + METHOD_PARAMETERS);
		}else{
			resposta = new WebServiceCidades().get(URL_WS + METHOD_NAME);
		}	    
	     
	    String jsonArrayString = resposta[1].toString();
	     
	    if (resposta[0].equals("200")) {
	        ArrayList<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
	        JsonParser parser = new JsonParser();
	        JsonObject object = parser.parse(jsonArrayString).getAsJsonObject();
	        
	        JsonArray arrayCidades = object.getAsJsonArray("funcionario");
	         
	        for (int i = 0; i < arrayCidades.size(); i++) {
	        	JsonObject jsonObject =  (JsonObject) arrayCidades.get(i);
	        	Funcionario funcionario = new Funcionario();
	        	funcionario.setIdFuncionario(jsonObject.get("idFuncionario").toString());
	        	funcionario.setNmFuncionario(jsonObject.get("nmFuncionario").toString());
	        	listaFuncionarios.add(funcionario);        	
	        }
	        return listaFuncionarios;
	     }else{
	    	 return null;
	     }
    }
    
    private String montarURLParam(ArrayList<ArrayList<String>> param){
    	String URL = "?";
    	for(int i = 0; i < param.size(); i++){
    		URL += param.get(i).get(0) + "=" + Long.valueOf(param.get(i).get(1));
    		URL += "&";
    	}
    	URL = URL.substring (0, URL.length() - 1);
    	return URL;
    }
}