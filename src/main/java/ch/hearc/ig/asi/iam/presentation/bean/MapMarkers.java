package ch.hearc.ig.asi.iam.presentation.bean;

import ch.hearc.ig.asi.iam.business.ApplicationState;
import ch.hearc.ig.asi.iam.business.Bus;
import ch.hearc.ig.asi.iam.service.WebService;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dimitri.mella Bean utilisé pour afficher les informations retournées
 * par les webServices.
 */
@Named(value = "mapMarkers")
@ViewScoped
public class MapMarkers implements Serializable {

    private List<Bus> bus; // Liste contenant les bus qui circulent sur la ligne 22
    private Bus goodBus; // Attribut utilisé pour stocker le bon bus que l'on tracke
    private String applicationState; // Attribut utilisé pour stocker l'état de l'application

    @Inject
    WebService services;

    /**
     * Creates a new instance of MapMarkers
     */
    public MapMarkers() {
    }

    /**
     * Méthode appellée lors du chargement de la page qui va mettre l'état de base
     * de l'application et chercher la liste des bus
     */
    public void init() {
        this.bus = new ArrayList<>();
        goodBus = null;
        applicationState = ApplicationState.BUSNOTFINDED.name(); // Etat de base qui est bus non trouvé
        this.makeArrayOfBus();
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }

    public Bus getGoodBus() {
        return goodBus;
    }

    public void setGoodBus(Bus goodBus) {
        this.goodBus = goodBus;
    }

    public String getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(String applicationState) {
        this.applicationState = applicationState;
    }

    /**
     * Méthode utilisée afin de rafraichir les données des bus qui circulent sur
     * la route 22
     */
    public void makeArrayOfBus() {
        try {
            List<JSONObject> listOfBus = services.getVehiclesOfRoad22(); // Appel des WebServices
            this.bus.clear();
            // Peuplement de la liste des bus en fonction du résultat des WebServices
            for (JSONObject jsonObject : listOfBus) {
                this.bus.add(new Bus(
                        jsonObject.getLong("vid"),
                        jsonObject.getString("des"),
                        jsonObject.getDouble("lat"),
                        jsonObject.getDouble("lon"),
                        new BigDecimal("0") // Une distance à 0 est initialisé pour chaque bus, comme l'API de Google Distance est utilisée pour calculer la distance, l'appel se fait seulement pour le bus qui est tracké afin de limiter les appels (licence limitée)
                ));
            }

            // Test afin de savoir si le bon bus a déjà été découvert, si non : on le trouve, autrement on le reprend dans notre nouveau tableau de bus
            if (goodBus == null) {
                this.findBus();
            } else {
                for (Bus bus : this.bus) {
                    if (bus.getId().toString().equals(this.goodBus.getId().toString())) {
                        bus.setGoodBus(true);
                        bus.setDistance(services.retrieveDistance(bus.getLatitude(), bus.getLongitude()));
                        this.goodBus = bus;
                    }
                }
                // Test afin de déterminer si le bus est proche de l'arrêt, si oui : une alerte est envoyée à l'utilisateur 
                if ((this.goodBus.getDistance().compareTo(new BigDecimal("0.482803"))) == -1 && (this.goodBus.getDirection().equals("Harrison"))) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('dlg2').show();");
                }
            }
            // Appelle de la fonction de rafraichissement de la carte en JavaScript
            RequestContext.getCurrentInstance().execute("refreshMarker();");
        } catch (Exception ex) {
            this.applicationState = ApplicationState.SERVICESUNAVAILABLE.name(); // Si les services Web sont indisponible, l'état de l'application passe à indisponible
        }

    }

    /**
     * Méthode utilisée pour trouver le bus contenant la valise, concrétement il
     * s'agit du bus ayant une latitude supérieur à la latitude de l'arrêt où
     * l'utilisateur est descendu et étant le plus proche de cet arrêt
     */
    public void findBus() throws IOException {
        for (int i = 0; i < this.bus.size(); i++) {
            if (bus.get(i).getDirection().equals("Howard")) {
                if ((bus.get(i).getLatitude() > 41.984982)) {
                    if (goodBus == null) {
                        goodBus = bus.get(i);
                    }
                    if (bus.get(i).getDistance().compareTo(goodBus.getDistance()) == -1) {
                        goodBus = bus.get(i);
                    }
                }
            }
        }
        if (goodBus != null) {
            goodBus.setGoodBus(true);
            goodBus.setDistance(services.retrieveDistance(goodBus.getLatitude(), goodBus.getLongitude()));
            this.applicationState = ApplicationState.BUSFINDED.name(); // L'état de l'application change, le bus a été trouvé
        }
    }
}
