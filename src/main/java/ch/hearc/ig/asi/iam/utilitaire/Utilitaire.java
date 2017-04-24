/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class Utilitaire {

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
    
    public static BigDecimal getDistance(Double latitude){
        BigDecimal latitude1 = new BigDecimal(latitude.toString());
        BigDecimal latitude2 = new BigDecimal("41.984982");
        BigDecimal res = latitude1.subtract(latitude2);
        res = res.multiply(new BigDecimal("69"));
        res = res.multiply(new BigDecimal("-1"));
        return res.abs();
    }
}
