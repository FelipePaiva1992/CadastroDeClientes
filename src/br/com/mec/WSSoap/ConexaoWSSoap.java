package br.com.mec.WSSoap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class ConexaoWSSoap {

	private static final String CATEGORIA = "appTeste";
	private static final String NAMESPACE = "http://controller/";
	private static final String URL = "http://portalacqua.sytes.net:8101/WSRamaisSoap/ramais?WSDL";
	
	//FALTA PARAMETROS
	public JSONArray getResultSoap(String METHOD_NAME,ArrayList<ArrayList<String>> parameters){
		
		JSONArray jArrayResultado = null;
		 
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				Log.i(CATEGORIA, "Adicionando Parametros");
				request.addProperty(parameters.get(i).get(0), Long.valueOf(parameters.get(i).get(1)));
			}			
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
	  
			try {
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				Log.i(CATEGORIA, "Chamando WS");
				androidHttpTransport.call(SOAP_ACTION, envelope);
				Log.i(CATEGORIA, "Chamamou WS");
				
				
				
				//O ERRO ESTA AQUI
				Log.i(CATEGORIA, "Start Cast");
				
				SoapObject response = null;
				response= (SoapObject) envelope.bodyIn;
				
				Log.i(CATEGORIA, "End Cast");
				
				
				
				String[] resultados = new String[response.getPropertyCount()];
				jArrayResultado = new JSONArray();
				
				
				for(int i = 0; i < resultados.length; i++){
					
					JSONObject jsonObject = new JSONObject();
					SoapObject soapObject = (SoapObject) response.getProperty(i);
					
					for( int j = 0; j < soapObject.getPropertyCount(); j++){
						jsonObject.put(String.valueOf(j),soapObject.getProperty(j).toString());
					}
					
					jArrayResultado.put(jsonObject);
					
				}				
				
			}catch (Exception e) {
				Log.e(CATEGORIA, e.getMessage());
			}
	
			return jArrayResultado;
	}
	


}