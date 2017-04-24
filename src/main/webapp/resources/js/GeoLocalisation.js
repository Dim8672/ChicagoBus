var map;
var task = setInterval(refreshMarkers, 10000);
var bus;
var markers = [];

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

    refreshMarkers();
}

function getGoodBus() {
    bus = {
        lat: Number($("#form:coordTable").children().first().find(".latitude").text()),
        lng: Number($("#form:coordTable").children().first().find(".longitude").text())
    };
    
    console.log(bus);
    
}

function refreshMarkers() {
    clearMarkers();
    getGoodBus();

    var busMarker = new google.maps.Marker({
        position: {lat: bus.lat, lng: bus.lng},
        map: map,
        title: 'Bus Potentiel',
        icon: 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png'
    });
    
    markers.push(busMarker);
    
    showMarkers();
}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
    setMapOnAll(null);
}

// Shows any markers currently in the array.
function showMarkers() {
    setMapOnAll(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
    clearMarkers();
    markers = [];
}

jQuery(document).ready(function () {
    initMap();
});
