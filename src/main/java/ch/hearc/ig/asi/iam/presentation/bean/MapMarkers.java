package ch.hearc.ig.asi.iam.presentation.bean;

import ch.hearc.ig.asi.iam.business.Bus;
import ch.hearc.ig.asi.iam.service.WebService;
import ch.hearc.ig.asi.iam.utilitaire.Utilitaire;
import java.io.Serializable;
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

    private List<Bus> bus; // Liste contenant les bus qui vont dans la direction du Nord
    private Bus goodBus; // Attribut utilisé pour stocker le bon bus que l'on tracke

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
        goodBus = null;
        this.makeArrayOfBus();
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }

    /**
     * Rafraichir les données des bus qui circulent sur la route 22
     */
    public void makeArrayOfBus() {
        List<JSONObject> listOfBus = services.getVehiclesOfRoad22(); // Appel des WebServices
        this.bus.clear();
        // Peuplement de la liste des bus en fonction du résultat des WebServices
        for (JSONObject jsonObject : listOfBus) {
            this.bus.add(new Bus(
                    jsonObject.getLong("vid"),
                    jsonObject.getString("des"),
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lon"),
                    Utilitaire.getDistance(jsonObject.getDouble("lat"))
            ));
        }

        // Test afin de savoir si le bon bus a déjà été découvert, si non : on le trouve, autrement on le reprend dans notre nouveau tableau de bus
        if(goodBus == null){
            this.findBus();
        } else {
            for(Bus bus : this.bus){
                if (bus.getId().toString().equals(this.goodBus.getId().toString())){
                    bus.setGoodBus(true);
                }
            }
        }
    }

    /**
     * Méthode utilisée pour trouver le bus contenant la valise, concrétement il s'agit du bus ayant une latitude supérieur à la latitude de l'arrêt où l'utilisateur est descendu
     * et étant le plus proche de cet arrêt
     */
    public void findBus() {
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
