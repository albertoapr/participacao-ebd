package icm.projects.participacaoEBD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opencsv.bean.CsvToBeanBuilder;

import icm.projects.participacaoEBD.modelo.*;
import icm.projects.participacaoEBD.util.JsonReader;
import icm.projects.participacaoEBD.util.TxtReader;

public class ParticipacaoEBD {


	public static String lerArquivo(String pathFile) throws IOException
	{
		 String texto =null;
		String arquivo = getFilePath(pathFile); 
		FileInputStream inputStream = new FileInputStream(arquivo);
	    try {
	         texto = IOUtils.toString(inputStream);
	    } finally {
	        inputStream.close();
	    }	
	    return texto;
	}
	
	 
	
	
	public static String getFilePath(String fileName) {
		  // Determine where the input file is; assuming it's in the same directory as the jar
        
        File jarFile;
        
		try {
			jarFile = new File(ParticipacaoEBD.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			String inputFilePath = URLDecoder.decode(jarFile.getParent(), "UTF-8") + File.separator + fileName;
			
			return inputFilePath;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Participante> carregaParticipantes(String arquivoParticipantes) throws IllegalStateException, UnsupportedEncodingException {
		
	        // Determine where the input file is; assuming it's in the same directory as the jar
	        List<Participante> participantes = null;
			try {
				String inputFilePath = getFilePath(arquivoParticipantes);
				participantes = new CsvToBeanBuilder(new FileReader(inputFilePath))
		                .withType(Participante.class)
		                .build()
		                .parse();
				return participantes;
			}  catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	                 
	  		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Participante> carregaParticipantesFromUrl(String csvUrl) throws IllegalStateException, UnsupportedEncodingException {
		
			URL url ;
			BufferedReader reader;
        
	        List<Participante> participantes = null;
			try {
				url = new URL(csvUrl);
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
				participantes = new CsvToBeanBuilder(reader)
		                .withType(Participante.class)
		                .build()
		                .parse();
				return participantes;
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	                 
	  		
	}
	
public static String enviarParticipacoes(String urlResposta, String urlParticipantes) throws IOException{
	
	String retorno = "";
	String resultado = "";
	
	String urlEBD = "https://intregracao-site.presbiterio.org.br/api-ebd/parametros";
	JSONObject json = JsonReader.readJsonFromUrl(urlEBD);
	String textoParticipacao = TxtReader.readTxtFromUrl(urlResposta);
	ArrayList<Participante> participantes =  (ArrayList<Participante>) carregaParticipantesFromUrl(urlParticipantes);
	
	Participacao participacao = new Participacao();
	EBD ebd = new EBD(json);
	participacao.setEbd(ebd);
	
	//provavelmente membro da igreja com cpf cadastrado
	participacao.setIdCategoria("2");
	participacao.setContribuicao(textoParticipacao);
    
	
   
  //Aqui enviamos a mesma participação para cada um 
  //dos participantes que ajudaram na montagem das respostas
	System.out.println("Enviando participações ...");
	int total = 0;
   for (Participante participante:participantes) 
   {
	   participacao.setParticipante(participante);
	   resultado = participacao.enviar();
	   if (resultado.contains("Sucesso"))
		   total++;
	   retorno  += participante.getNome()+": "+ resultado +"\n";
	   resultado = "";
	   
   }
   retorno += total +" participações enviados";
	
   
	
	return retorno;
}
	public static void main (String[] args) throws IOException {
		
		String urlResposta  = "https://docs.google.com/document/export?format=txt&id=1c2CyPDuJB87XmnTchLR2PFqfSjqNzCy32jozXYfu7O8";
		String urlParticipantes = "https://docs.google.com/spreadsheets/d/1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ/export?format=csv&id=1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ&gid=532508908";
		
	   System.out.println(enviarParticipacoes(urlResposta,urlParticipantes));

	}
}