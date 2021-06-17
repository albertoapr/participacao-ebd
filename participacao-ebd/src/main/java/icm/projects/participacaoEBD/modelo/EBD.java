package icm.projects.participacaoEBD.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class EBD {
	private String id;
	private Date data;
	private String titulo;
	String enderecoDeEnvio = "https://intregracao-site.presbiterio.org.br/api-ebd/cadastro-contribuicao";

	
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
