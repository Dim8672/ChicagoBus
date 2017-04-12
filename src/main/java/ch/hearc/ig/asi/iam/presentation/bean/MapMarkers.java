/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.asi.iam.presentation.bean;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author dimitri.mella
 */
@Named(value = "mapMarkers")
@ViewScoped
public class MapMarkers implements Serializable{

    private MapModel simpleModel;

    /**
     * Creates a new instance of MapMarkers
     */
    public MapMarkers() {
    }

    /**
     * Méthode appellée lors du chargement de la page afin d'afficher les deux coordoonées statiques, soit le bureau et l'arrêt du bus
     */
    public void init() {
        simpleModel = new DefaultMapModel();

        //Coordinates of the office and the bus stop
        LatLng coordOffice = new LatLng(41.980262, -87.668452);
        LatLng coordBusStop = new LatLng(41.984982, -87.668999);

        //Adding Markers for the office and the bus stop
        simpleModel.addOverlay(new Marker(coordOffice, "IAMOffice"));
        simpleModel.addOverlay(new Marker(coordBusStop, "BusStop"));
        
        this.refreshCoordinates();
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    /**
     * Rafraichir les données des bus qui circulent
     */
    public void refreshCoordinates(){
        
    }

}
