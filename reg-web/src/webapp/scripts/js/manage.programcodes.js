// used by ProgramCodeAssignmentAction jsp files.
var cfrow = "";
var programCodeColumn = 5;
var trailsTable;
jQuery(function($) {

    $("#familyPoId").on('change', function(evt) {
        $("#changeFamilyFrm").submit();
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
            {width: "23%",data: "leadOrganizationName"},
            {width: "15%",data: "piFullName"},
            {width: "5%",data: "trialStatus"},
            {width:"8%",
             orderable: false ,
             data: function ( row, type, val, meta ) {
               var snippet = "";
                $(row.programCodes).each(function(i,pc) {
                    snippet = snippet +  '<a href="#" onclick="removePgCode(' + row.studyProtocolId + ')" class="btn-xs pgcrm ">' + pc + ' <span class="glyphicon glyphicon-remove"></span></a> ';
                });
                return snippet;
            }}

        ]
    });

    //insert a row above the table
    $(trailsTable.columns()[0].each(function(i, c){
        cfrow += '<td class="cf">';
        if(c == programCodeColumn) {
            cfrow += '<input type="text" id="cfProgramCode" class="cf" placeholder="Filter..." rel="' + c + '" />';

        }
        cfrow += ' </td>';

    }));
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


})(jQuery);
