package icm.projects.participacaoEBD.modelo;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import icm.projects.participacaoEBD.modelo.EBD;

public class Participacao {
	private EBD ebd;
	private Participante participante;
	private String idParticipacao; //id da participacao semanal
	private String idCategoria = "2";//categoria da participacao: participacao, experiência
    private String idTrabalho = "21"; //(21 - individual)grupo da igreja que estudou nessa participacao: individua, jovens
    private String contribuicao; //resumo das respostas
    public Participacao(String idParticipacao, String idCategoria) {
		super();
		this.idParticipacao = idParticipacao;
		this.idCategoria = idCategoria;
	}

	public Participacao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Participacao(EBD ebd) {
		super();
		this.setEbd(ebd);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
		result = prime * result + ((idParticipacao == null) ? 0 : idParticipacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participacao other = (Participacao) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		if (idParticipacao == null) {
			if (other.idParticipacao != null)
				return false;
		} else if (!idParticipacao.equals(other.idParticipacao))
			return false;
		return true;
	}

	public String getIdParticipacao() {
		return idParticipacao;
	}

	public void setIdParticipacao(String idParticipacao) {
		this.idParticipacao = idParticipacao;
	}

	public String getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getIdTrabalho() {
		return idTrabalho;
	}

	public void setIdTrabalho(String idTrabalho) {
		this.idTrabalho = idTrabalho;
	}

	public String getContribuicao() {
		return contribuicao;
	}

	public void setContribuicao(String contribuicao) {
		this.contribuicao = contribuicao;
	}

	public EBD getEbd() {
		return ebd;
	}

	public void setEbd(EBD ebd) {
		this.ebd = ebd;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	
	public String [][] getParametros(){
		String [][] parametros = new String [15][2];
		
		
		parametros[0][0] = "categoria_id";  
		parametros[0][1] = this.getIdCategoria();
		
		parametros[1][0] = "celular";  
		parametros[1][1] = this.getParticipante().getTelefone();
		
		parametros[2][0] = "cidade";  
		parametros[2][1] = "Mesquita";
		
		
		parametros[3][0] = "contribuicao";  
		parametros[3][1] = this.getContribuicao();
		parametros[4][0] = "cpf";  
		parametros[4][1] = this.getParticipante().getCpf();
		
		parametros[5][0] = "denominacao_id";  
		parametros[5][1] = "21";
		
		parametros[6][0] = "denominacao_outras";  
		parametros[6][1] = "";
		
		parametros[7][0] = "email";  
		parametros[7][1] = this.getParticipante().getEmail();
		
		parametros[8][0] = "funcao";  
		parametros[8][1] = "";
		
		parametros[9][0] = "funcao_id";  
		parametros[9][1] = this.getParticipante().getFuncao();
		
		parametros[10][0] = "nome";  
		parametros[10][1] = this.getParticipante().getNome();
		
		parametros[11][0] = "trabalho_id";
		parametros[11][1] = this.getParticipante().getTrabalho();
		
		parametros[12][0] = "uf";  
		parametros[12][1] = "RJ";
		
		parametros[13][0] = "ebd_id";  
		parametros[13][1] = this.getEbd().getId();
		
		parametros[14][0] = "aceite_termo";  
		parametros[14][1] = "1";
		
		return parametros;
	}

	
public  String enviar () {
		
		String endereco = this.getEbd().getEnderecoDeEnvio();
	    String jsonResponse = null;
	    if (endereco!=null) {
	        try {
	           
	           URL object = new URL(endereco);
	           HttpURLConnection connection = (HttpURLConnection) object.openConnection();
	           connection.setReadTimeout(60 * 1000);
	           connection.setConnectTimeout(60 * 1000);
	           
	           connection.setDoOutput(true);
	           connection.getOutputStream().write(this.montaParametros());
	           int responseCode = connection.getResponseCode();
	           String responseMsg = connection.getResponseMessage();

	            if (responseCode == 200) {
	                InputStream inputStr = connection.getInputStream();
	                String encoding = connection.getContentEncoding() == null ? "UTF-8"
	                        : connection.getContentEncoding();
	               jsonResponse = IOUtils.toString(inputStr, encoding);
	          
	            }
	            else
	            	jsonResponse = responseMsg;
	        } catch (Exception e) {
	            e.printStackTrace();

	        }
	    }
	    return jsonResponse;
	    
	}

public  byte[] montaParametros() {
	byte[]  postDataBytes =null;
	String[][] parametros = this.getParametros();
	
	try {
	Map<String,Object> params = new LinkedHashMap<String, Object>();
        
	for (int i = 0; i <= 14; i++) {
		if (parametros[i][1] != null)
			params.put(parametros[i][0],parametros[i][1]);
	}

    
        

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        postDataBytes = postData.toString().getBytes("UTF-8");
      
	} catch (UnsupportedEncodingException e) {
		
		e.printStackTrace();
	}
	return postDataBytes;
}




}
