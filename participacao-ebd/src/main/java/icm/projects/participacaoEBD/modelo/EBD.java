package icm.projects.participacaoEBD.modelo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import icm.projects.participacaoEBD.util.JsonReader;

public class EBD {
	private String id;
	private Date data;
	private String titulo;
	String enderecoDeEnvio = "";

	
	private Date stringToDate(String data) {
		

		data = data.substring(0, 10);
		
		Date date;
		try {
			SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd");
			date = format.parse(data);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void carregaEBD(JSONObject json) {
		   JSONArray ebds = json.getJSONArray("ebds");
		   
		   for(int i = 0; i < ebds.length(); i++){
	           JSONObject ebd = ebds.getJSONObject(i);
	           
	           this.setId(ebd.getString("id"));
	           this.setTitulo(ebd.getString("titulo"));
	           this.setData(stringToDate(ebd.getString("data")));
	       }
		  
		
		
	}
	
	public String getEnderecoDeEnvio() {
		return enderecoDeEnvio;
	}

	public void setEnderecoDeEnvio(String enderecoDeEnvio) {
		this.enderecoDeEnvio = enderecoDeEnvio;
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public EBD(JSONObject json) {
		super();
		this.carregaEBD(json);
		// TODO Auto-generated constructor stub
	}
	
	public EBD(Igreja igreja) {
		super();
		try {
				this.carregaEBD(JsonReader.readJsonFromUrl(igreja.getUrlEbd()));
				this.setEnderecoDeEnvio(igreja.getUrlApiContribuicao());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	public EBD() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	

}
