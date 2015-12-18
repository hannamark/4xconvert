// used by ProgramCodeAssignmentAction jsp files.
var cfrow = "";
var programCodeColumn = 5;
var trailsTable;
var participationTable;
var curStudyProtocol;

document.observe("dom:loaded", function() {
  pgcinit(jQuery);
});

function pgcinit($) {

    $("#familyPoId").on('change', function(evt) {
        $("#changeFamilyFrm").submit();
    });

    participationTable = $('#participationTbl').DataTable( {
        "dom": 't',
        serverSide: false,
        ajax: {
            url: "managePCAssignmentparticipation.action",
            type:"POST",
            data: function(d) {
                d.familyPoId = $("#familyPoId").val();
                d.studyProtocolId = curStudyProtocol;
            }
        },
        "columns" : [
            {width:"50%", data:"site"},
            {width:"50%", data: "investigator"}
        ],
        sProcessing: "Loading...",
        processing:true
    });

    trailsTable = $('#trialsTbl').DataTable( {
        "dom": 'lprftip<"row">B',
        "pagingType": "full_numbers",
        "order": [[ 0, "desc" ]],
        "oLanguage": {
            "sInfo": "Showing _START_ to _END_ of _TOTAL_",
            "sLengthMenu": "Show _MENU_",
            "oPaginate": {
                "sFirst": "<<",
                "sPrevious": "<",
                "sNext": ">",
                "sLast": ">>"
            }
        },
        "serverSide": false,
        sProcessing: "Loading...",
        processing:true,
        "ajax": {
            "url": "managePCAssignmentfindTrials.action",
            "type": "POST",
            "data": function ( d ) {
                d.familyPoId = $("#familyPoId").val();
            }
        },
        buttons: [
            'csv', 'excel'
        ],
        "columns": [
            {width:"15%", data: "identifiers"},
            {width: "34%", data: "title"},
            {width: "23%",data: function(row, type, val, meta){
                return row.leadOrganizationName + "<br>" + '<a href="#" class="mypgp" rel="' + row.studyProtocolId + '">Show my participation</a>';
            }},
            {width: "15%",data: "piFullName"},
            {width: "5%",data: "trialStatus"},
            {width:"8%",
                orderable: false ,
                data: function ( row, type, val, meta ) {
                    var snippet = "";
                    $(row.programCodes).each(function(i,pc) {
                        snippet = snippet +  '<a href="#" rel="' + row.studyProtocolId + '" class="btn-xs pgcrm ">' + pc + ' <span class="glyphicon glyphicon-remove"></span></a> ';
                    });
                    return snippet;
                }}

        ]
    });

    //insert a row above the table
    if (trailsTable.columns()[0]) {

        $(trailsTable.columns()[0].each(function(i, c){
            cfrow += '<td class="cf">';
            if(c == programCodeColumn) {
                cfrow += '<input type="text" id="cfProgramCode" class="cf" placeholder="Filter..." rel="' + c + '" />';

            }
            cfrow += ' </td>';

        }));

    }
    cfrow = '<tr class="cf">' + cfrow + '</tr>';

    //add few filter elements on the table
    $('#trialsTbl').find("thead").prepend(cfrow);

    // Apply the search
    $('input.cf').on('keyup change', function () {
        trailsTable.columns($(this).attr("rel")).search(this.value).draw();
        $('#pgcFilter').val(this.value);
    } );

    if ($('#pgcFilter').val() !== '') {
        $('#cfProgramCode').val($('#pgcFilter').val());
    }

    $('#trialsTbl tbody').on('click', 'a.pgcrm', function (evt) {
        evt.preventDefault();
    } );


    $('#trialsTbl tbody').on('click', 'a.mypgp', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(evt.target, 'rel');
        openParticipationDialog($);
    } );


}


function openParticipationDialog($) {

    //cleanup

    if ($("#dialog-participation").dialog("instance")) {
        $("#dialog-participation").dialog("destroy");
    }
    $("#participationTbl>tbody").empty();
    $("span.pgcpSite").first().html($('#familyPoId option:selected').text());

    //create dialog
    $("#dialog-participation").dialog({
        modal : true,
        autoOpen : false,
        width : $(window).width() * 0.6,
        buttons : {
            "Close" : function() {
                $(this).dialog("close");
            }
        }
    });

    //show dialog
    $("#dialog-participation").dialog('open');

    //reload data in table

    participationTable.ajax.reload();
}

