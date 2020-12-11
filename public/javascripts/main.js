$(function() {

    $("#whaleTable").tablesorter({
        theme : "bootstrap",

        widthFixed: true,

        // widget code contained in the jquery.tablesorter.widgets.js file
        // use the zebra stripe widget if you plan on hiding any rows (filter widget)
        // the uitheme widget is NOT REQUIRED!
        widgets : [ "columns", "zebra" ],

        widgetOptions : {
            // using the default zebra striping class name, so it actually isn't included in the theme variable above
            // this is ONLY needed for bootstrap theming if you are using the filter widget, because rows are hidden
            zebra : ["even", "odd"],

            // class names added to columns when sorted
            columns: [ "primary", "secondary", "tertiary" ],

            // reset filters button
            filter_reset : ".reset",

            // extra css class name (string or array) added to the filter element (input or select)
            filter_cssFilter: [
                'form-control',
                'form-control',
                'form-control custom-select', // select needs custom class names :(
                'form-control',
                'form-control',
                'form-control',
                'form-control'
            ]

        }
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

    $("#observationTable").tablesorter({
        theme: 'bootstrap',
    });


    $('input#time').datetimepicker({
        mask:'9999/19/39 29:59'
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
    $(tag).keypress(() => validateWhaleInput(tag));
}