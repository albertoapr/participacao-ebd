package icm.projects.participacaoEBD.modelo;

import java.io.IOException;

import com.opencsv.bean.CsvBindByPosition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import icm.projects.participacaoEBD.util.JsonReader;

public class Participante {
	private final String URL_CPF = "https://intregracao-site.presbiterio.org.br/api-ebd/consultacpf/";
	@CsvBindByPosition(position = 0)	
	String nome;

	@CsvBindByPosition(position = 1)
	String cpf;

	@CsvBindByPosition(position = 2)
	String email;

	@CsvBindByPosition(position = 3)
	String telefone;
	
	
	@CsvBindByPosition(position = 4)
	String funcao;
	
	@CsvBindByPosition(position = 5)
	String trabalho;
	
	Participacao participacao;

	






public Participacao getParticipacao() {
		return participacao;
	}







	public void setParticipacao(Participacao participacao) {
		this.participacao = participacao;
	}







public Participante() {
		super();
		// TODO Auto-generated constructor stub
	}







//String [][] participacao = new String [12][2];

Participante (String nome,String cpf, String telefone, String email,String funcao, String trabalho){
	this.nome = nome;
	this.telefone = telefone;
	this.cpf = cpf;
	this.email = email;
	this.funcao = funcao;
	this.trabalho = trabalho;
	
	
	
}

/*
 * Atualiza nome, email e celular do participantes consultanto no presbitério*/
public void atualizar() {
	
	
	String urlConculta = URL_CPF + this.getCpf() ;
	
	try {
		JSONObject json = JsonReader.readJsonFromUrl(urlConculta);
		this.setNome(json.getString("nome"));
        this.setEmail(json.getString("email"));
        this.setTelefone(json.getString("celular"));
       /* 
        this.setPastor(json.getString("pastor"));
        this.setIgreja(json.getString("igreja"));
        this.setCidade(json.getString("cidade"));
        this.setUf(json.getString("uf"));
   */
		
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	
       
	
}









public String getNome() {
	return nome;
}



public void setNome(String nome) {
	this.nome = nome;
}



public String getCpf() {
	return cpf;
}



public void setCpf(String cpf) {
	this.cpf = cpf;
}



public String getEmail() {
	return email;
}



public void setEmail(String email) {
	this.email = email;
}



public String getTelefone() {
	return telefone;
}


public void setTelefone(String telefone) {
	this.telefone = telefone;
}


public void setFuncao(String funcao) {
	this.funcao = funcao;
}

public String getFuncao() {
	// TODO Auto-generated method stub
	return funcao;
}

public void setTrabalho(String trabalho) {
	this.trabalho = trabalho;
}

public String getTrabalho() {
	return trabalho;
}













}
