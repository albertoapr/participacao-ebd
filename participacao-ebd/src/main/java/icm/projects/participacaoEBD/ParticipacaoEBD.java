package icm.projects.participacaoEBD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import icm.projects.participacaoEBD.modelo.Igreja;

public class ParticipacaoEBD {
    public static void processarEnvio() throws IllegalStateException, IOException {
    	String urlIgreja = "https://docs.google.com/spreadsheets/d/1qlEHTzsmAWxsCrOfflpYgEE9tS2Mtp1UElvxOhXshl0/export?format=csv&id=1qlEHTzsmAWxsCrOfflpYgEE9tS2Mtp1UElvxOhXshl0&gid=0";
		List<Igreja> igrejas = new ArrayList<Igreja>(Igreja.carregaIgrejaFromUrl(urlIgreja));
		for (Igreja igreja:igrejas)
			igreja.enviarParticipacoes();
    }

	public static void main (String[] args) throws IOException {
		
		processarEnvio();

	}
}