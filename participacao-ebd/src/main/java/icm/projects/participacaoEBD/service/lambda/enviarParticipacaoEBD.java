package icm.projects.participacaoEBD.service.lambda;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonObject;

import icm.projects.participacaoEBD.ParticipacaoEBD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class enviarParticipacaoEBD implements RequestStreamHandler{

	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		//API para manipulacao de JSONs
        JsonObject responseJson = new JsonObject();
        JsonObject responseBody = new JsonObject();
        JsonObject headerJson = new JsonObject();
        
        String urlResposta  = "https://docs.google.com/document/export?format=txt&id=1c2CyPDuJB87XmnTchLR2PFqfSjqNzCy32jozXYfu7O8";
		String urlParticipantes = "https://docs.google.com/spreadsheets/d/1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ/export?format=csv&id=1zCY9DtKMQPF5s_YHXaM0TGFi8YKNdZdQja_22GnUeZQ&gid=532508908";
		String resultadoDoEnvio =  ParticipacaoEBD.enviarParticipacoes(urlResposta,urlParticipantes);

        //propriedade mensagem na resposta
        responseBody.addProperty("message", resultadoDoEnvio);

        // um header aleatório na resposta para API Gateway
        headerJson.addProperty("x-custom-header", "my custom header value");

        // Http status code 200 OK
        responseJson.addProperty("statusCode", 200);
        responseJson.add("headers", headerJson);
        responseJson.addProperty("body", responseBody.toString());

        // serializa o JSON para um OutputStream
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
		
	}

}
