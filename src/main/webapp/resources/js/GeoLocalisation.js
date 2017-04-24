// Déclaration de variable globales
var map; // Map Google
var task = setInterval(refreshMarker, 10000); // Demande de rafraichissement des markers toutes les 10 secondes
var bus; // le bus contenant la valise
var busMarker; // le marker du bus contenant la valise

/**
 * Fonction utilisée pour initialiser la map et mettre les marqueurs
 * @returns {undefined}
 */
function initMap() {
    
    // Initialisation de la map
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 41.980262, lng: -87.668999},
        mapTypeId: 'roadmap',
        zoom: 12
    });

    // Marker de l'arrêt du bus
    var busStopMarker = new google.maps.Marker({
        position: {lat: 41.984982, lng: -87.668999},
        map: map,
        title: 'BusStop',
        icon: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'
    });

    // Marker du bureau IAM
    var officeMarker = new google.maps.Marker({
        position: {lat: 41.980262, lng: -87.668452},
        map: map,
        title: 'Office',
        icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
    });

    refreshMarker(); // Appelle de la méthode allant rafraichir le marker du bus contenant la valise
}

/**
 * Fonction allant rechercher le bus contenant la valise, il s'agit du premier bus du tableau (le bus ayant donc l'attribut goodBus a true)
 * @returns {undefined}
 */
function getGoodBus() {
    bus = {
        lat: Number($("#form").find(".tableData").children().first().children().first().children().last().children().first().find(".latitude").text()),
        lng: Number($("#form").find(".tableData").children().first().children().first().children().last().children().first().find(".longitude").text())
    };
      
}

/**
 * Fonction allant créer le nouveau Marker pour le bus contenant la valise
 * la méthode afin de supprimer le Marker existant et la méthode allant rechercher les nouvelles coordonnées du bus sont également appellées
 * @returns {undefined}
 */
function refreshMarker() {
    clearMarker();
    getGoodBus();
    console.log(bus);
    busMarker = new google.maps.Marker({
        position: {lat: bus.lat, lng: bus.lng},
        map: map,
        title: 'Bus Potentiel',
        icon: 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png'
    });
    
}

/**
 * Méthode allant supprimer le Marker du bus contenant la valise sur la map
 * @returns {undefined}
 */
function clearMarker() {
    if(busMarker !== undefined){
        busMarker.setMap(null);
    }       
}

/**
 * Méthode appellée au chargement de la page afin d'initialiser la map
 * @param {type} param
 */
jQuery(document).ready(function () {
    initMap();
});
