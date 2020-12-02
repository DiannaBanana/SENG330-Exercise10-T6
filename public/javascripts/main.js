$(function() {
    $("#observationTable").tablesorter({
        theme: 'jui',
    });
});

function redirect(uri){
    window.location.href=uri;
}

function init() {

    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
}