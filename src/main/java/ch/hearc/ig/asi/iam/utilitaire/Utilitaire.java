package ch.hearc.ig.asi.iam.utilitaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author dimitri.mella
 * Classe Utilitaire utilisée pour parcourir les objets JSON et calculer la distance entre les bus et l'arrêt
 */
public class Utilitaire {

    /**
     * Méthode utilisée pour créer un objet JSON en fonction du buffer passé en paramètre
     * @param bufferedReader le buffer contenant un JSON
     * @return un objet JSON
     */
    public static JSONObject makeJSONObject(BufferedReader bufferedReader) {
        String line;
        StringBuilder jsonReadObject = new StringBuilder();

        try {
            while ((line = bufferedReader.readLine()) != null) {
                jsonReadObject.append(line);
            }
            JSONObject jsonObject = new JSONObject(jsonReadObject.toString());
            return jsonObject;
        } catch (IOException ex) {
            Logger.getLogger(Utilitaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Méthode utilisée pour calculer la distance entre un bus et l'arrêt du bus
     * @param latitude la latitude actuelle du bus
     * @return un BigDecimal indiquant la distance entre un bus et l'arrêt du bus
     */
    public static BigDecimal getDistance(Double latitude){
        BigDecimal latitude1 = new BigDecimal(latitude.toString());
        BigDecimal latitude2 = new BigDecimal("41.984982");
        BigDecimal res = latitude1.subtract(latitude2);
        res = res.multiply(new BigDecimal("69"));
        res = res.multiply(new BigDecimal("-1"));
        return res.abs();
    }
}
