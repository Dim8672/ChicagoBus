package ch.hearc.ig.asi.iam.service;

import ch.hearc.ig.asi.iam.utilitaire.Utilitaire;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dimitri.mella Classe de service utilisée pour appeller les
 * webServices de Chicago BusTracker.
 */
@Singleton
public class WebService implements Serializable {

    /**
     * Méthode permettant de récupérer la liste des bus allant sur la route 22
     *
     * @return un tableau de JSONObject contenant la liste des bus
     */
    public ArrayList<JSONObject> getVehiclesOfRoad22() {
        ArrayList<JSONObject> results = new ArrayList<>();

        // Création de l'URL pour le WebServices
        StringBuilder sb = new StringBuilder("http://www.ctabustracker.com/bustime/api/v2/getvehicles?key=");
        sb.append("kEUhSzcsfbhrsBYaz5FW9AUmg");
        sb.append("&rt=22");
        sb.append("&format=json");

        // Appelle de la méthode qui fait l'appel HTTP
        BufferedReader bufferedReader = this.makeHttpCall(sb.toString());
        // Appelle de la méthode qui crée un objet JSON avec le résultat de la requête HTTP
        JSONObject jsonObject = Utilitaire.makeJSONObject(bufferedReader);

        jsonObject = jsonObject.getJSONObject("bustime-response");
        JSONArray vehicles = jsonObject.getJSONArray("vehicle");

        for (int i = 0; i < vehicles.length(); i++) {
            if (vehicles.getJSONObject(i).getString("des").equals("Howard")) {
                results.add(vehicles.getJSONObject(i));
            }
        }

        return results;
    }

    private BufferedReader makeHttpCall(String request) {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(
                request);
        getRequest.addHeader("accept", "application/json");

        try {
            HttpResponse response = httpClient.execute(getRequest); // Envoi de la demande au service REST
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            // Instanciation du buffer
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

           // httpClient.getConnectionManager().shutdown(); // Fermeture de la connexion

            return br;
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
}
