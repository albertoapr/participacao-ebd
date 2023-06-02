package icm.projects.participacaoEBD.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBeanBuilder;

import icm.projects.participacaoEBD.util.EmailSender;
import icm.projects.participacaoEBD.util.JsonReader;
import icm.projects.participacaoEBD.util.TxtReader;

public class Igreja {
	@CsvBindByPosition(position = 0)	
	String nome;

	@CsvBindByPosition(position = 1)
	String pastor;
	
	
	@CsvBindByPosition(position = 2)
	String arquivoResposta;
	
	@CsvBindByPosition(position = 3)
	String arquivoDeParticipantes;
	
	@CsvBindByPosition(position = 4)
	String email;

	@CsvBindByPosition(position = 5)
	String senha;
	
	@CsvBindByPosition(position = 6)
	String urlEbd;
	
	@CsvBindByPosition(position = 7)
	String urlApiContribuicao;
	
	public Igreja() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Igreja(String nome, String pastor, String arquivoResposta, String arquivoDeParticipantes, String email,
			String senha) {
		super();
		this.nome = nome;
		this.pastor = pastor;
		this.arquivoResposta = arquivoResposta;
		this.arquivoDeParticipantes = arquivoDeParticipantes;
		this.email = email;
		this.senha = senha;
	}
	
	public String getUrlApiContribuicao() {
		return urlApiContribuicao;
	}

	public void setUrlApiContribuicao(String urlApiContribuicao) {
		this.urlApiContribuicao = urlApiContribuicao;
	}

	public String getUrlEbd() {
		return urlEbd;
	}

	public void setUrlEbd(String urlEbd) {
		this.urlEbd = urlEbd;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPastor() {
		return pastor;
	}

	public void setPastor(String pastor) {
		this.pastor = pastor;
	}

	public String getArquivoResposta() {
		return arquivoResposta;
	}

	public void setArquivoResposta(String arquivoResposta) {
		this.arquivoResposta = arquivoResposta;
	}

	public String getArquivoDeParticipantes() {
		return arquivoDeParticipantes;
	}

	public void setArquivoDeParticipantes(String arquivoDeParticipantes) {
		this.arquivoDeParticipantes = arquivoDeParticipantes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Igreja> carregaIgrejaFromUrl(String csvUrl) throws IllegalStateException, UnsupportedEncodingException {
		
			URL url ;
			BufferedReader reader;
        
	        List<Igreja> igrejas = null;
			try {
				url = new URL(csvUrl);
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
				igrejas = new CsvToBeanBuilder(reader)
		                .withType(Igreja.class)
		                .build()
		                .parse();
				
				//remove a 1� linha com o nome dos campos da planilha
				if (igrejas.get(0).getPastor().toUpperCase().equals("PASTOR"))
					igrejas.remove(0);
				
				
					
				return igrejas;
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	                 
	  		
	}
	
	public  void enviarParticipacoes() throws IOException{
		
		String retorno = "";
		String resultado = "";
		Set<String>	mailList = new HashSet<String>();
		Set<String>	mailListAdm = new HashSet<String>();
		EBD ebd = new EBD(this);
		String textoParticipacao = TxtReader.readTxtFromUrl(this.getArquivoResposta());
		ArrayList<Participante> participantes =  (ArrayList<Participante>) Participante.carregaParticipantesFromUrl(this.getArquivoDeParticipantes());
		
		Participacao participacao = new Participacao(ebd);
		participacao.setContribuicao(textoParticipacao);
		
		//provavelmente membro da igreja com cpf cadastrado
		participacao.setIdCategoria("2");
		
	    
		
	   
	  //Aqui enviamos a mesma participação para cada um 
	  //dos participantes que ajudaram na montagem das respostas
		System.out.println("Enviando participações de "+ this.getNome()+ ", pastor: "+this.getPastor());
		retorno+="Enviando participações de "+ this.getNome()+ ", pastor: "+this.getPastor()+"\n";
		int total = 0;
	   for (Participante participante:participantes) 
	   {
		   participacao.setParticipante(participante);
		   resultado = participacao.enviar();
		   
		   resultado = JsonReader.getMessage(resultado);
		   
		   
		   //resultado = "Sucesso";
		   if (resultado.contains("Sucesso"))
		   {
			   if (!participante.getEmail().isEmpty()) {
				   mailList.add(participante.getEmail());
				   
			   }
			   total++;
		   }
		   
		   //Se não for membro comum recebe email com log do processamento
		   if (!participante.getFuncao().equals("9"))
			   mailListAdm.add(participante.getEmail());
		   
		   System.out.println(participante.getNome()+": "+ resultado);
		   retorno  += participante.getNome()+": "+ resultado +"\n";
		   resultado = "";
		   
	   }
	   retorno += total +" participações enviadas";
	   String emails [] = mailList.toArray(new String[0]);
	   EmailSender.sendEmail(emails,"Sua participação da EBD foi enviada",textoParticipacao,this.getEmail(),this.getSenha());
	   emails = mailListAdm.toArray(new String[0]);
	   EmailSender.sendEmail(emails,"Envio das participações",retorno,this.getEmail(),this.getSenha());
	   

	}
		

	
}
