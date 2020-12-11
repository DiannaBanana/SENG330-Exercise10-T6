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

function doSampleJson(url, tag) {
    $.getJSON( url, function( data ) {
        $(tag).text(JSON.stringify(data));
    }).fail((data) => {$(tag).text(JSON.stringify(data.responseJSON));});
}

function filterInt(value) {
    if (/^[-+]?(\d+|Infinity)$/.test(value)) {
        return Number(value)
    } else {
        return NaN
    }
}

function validateWhaleInput(tag){
    if (parseInt(filterInt($(tag).val())) >= 0){
        $('#whale_submit').removeAttr('disabled');
    } else {
        $('#whale_submit').attr('disabled', 'disabled');
    }
}

function bindValidator(tag){
    $(tag).keyup(() => validateWhaleInput(tag));
}