package ch.hearc.ig.asi.iam.presentation.bean;

import ch.hearc.ig.asi.iam.business.Bus;
import ch.hearc.ig.asi.iam.service.WebService;
import ch.hearc.ig.asi.iam.utilitaire.Utilitaire;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.json.JSONObject;

/**
 *
 * @author dimitri.mella Bean utilisé pour afficher les informations retournées
 * par les webServices.
 */
@Named(value = "mapMarkers")
@ViewScoped
public class MapMarkers implements Serializable {

    private List<Bus> bus;

    @Inject
    WebService services;

    /**
     * Creates a new instance of MapMarkers
     */
    public MapMarkers() {
    }

    /**
     * Méthode appellée lors du chargement de la page afin d'afficher les deux
     * coordoonées statiques, soit le bureau et l'arrêt du bus
     */
    public void init() {
        this.bus = new ArrayList<>();
        this.makeArrayOfBus();
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }

    /**
     * Rafraichir les données des bus qui circulent
     */
    public void makeArrayOfBus() {
        List<JSONObject> listOfBus = services.getVehiclesOfRoad22();

        for (JSONObject jsonObject : listOfBus) {
            this.bus.add(new Bus(
                    jsonObject.getLong("vid"),
                    jsonObject.getString("des"),
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lon"),
                    Utilitaire.getDistance(jsonObject.getDouble("lat"))
            ));
        }

        this.findBus();
    }

    public void findBus() {
        Bus goodBus = null;
        for (int i = 0; i < this.bus.size(); i++) {
            if ((bus.get(i).getLatitude() > 41.984982)) {
                if(goodBus == null){
                    goodBus = bus.get(i);
                }
                if (bus.get(i).getDistance().compareTo(goodBus.getDistance()) == -1) {
                    goodBus = bus.get(i);
                }
            }
        }
        if(goodBus != null){
            goodBus.setGoodBus(true);
        }
    }
}
