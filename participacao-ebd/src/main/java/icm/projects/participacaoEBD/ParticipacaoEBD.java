package icm.projects.participacaoEBD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opencsv.bean.CsvToBeanBuilder;

import icm.projects.participacaoEBD.modelo.*;
import icm.projects.participacaoEBD.util.JsonReader;

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
	

	public static void main (String[] args) throws IOException {
		
		String resultado = null;
		System.out.println("Buscando EBD ...");
		JSONObject json = JsonReader.readJsonFromUrl("https://intregracao-site.presbiterio.org.br/api-ebd/parametros");
		String textoParticipacao = lerArquivo("respostas.txt");
		System.out.println("Carregando participates ...");
		ArrayList<Participante> participantes =  (ArrayList<Participante>) carregaParticipantes("participantes.csv");
		Participacao participacao = new Participacao();
		
		EBD ebd = new EBD(json);
		participacao.setEbd(ebd);
		participacao.setIdTrabalho("21");
		participacao.setIdCategoria("2");
		participacao.setContribuicao(textoParticipacao);
        
		
	   
	  //Aqui enviamos a mesma participação para cada um 
	  //dos participantes que ajudaram na montagem das respostas
		System.out.println("Enviando participações ...");
		int total = 0;
	   for (Participante participante:participantes) 
	   {
		   participacao.setParticipante(participante);
		 //  resultado = participacao.enviar();
		   total++;
		   System.out.println(participante.getNome()+": participação enviada ");
		 //  System.out.println(participante.getNome()+": "+ URLDecoder.decode(resultado, "UTF-8"));
	   }
		
	   System.out.println(total+" participações enviadas, pressione ESC para sair");

	}
}