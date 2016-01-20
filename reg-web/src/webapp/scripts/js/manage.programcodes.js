// used by ProgramCodeAssignmentAction jsp files.
var cfrow = "";
var programCodeColumn = 5;
var trailsTable;
var participationTable;
var curStudyProtocol;
var curProgramCode;
var allProgramCodes = [];
var filteredProgramCodes = [];
var trialProgramCodeMap = {};
var tmparr = [];
var tmpS2Ds = [];
var curS2Ctrl = null;


// will remove program code currently associated with the given study
function removeProgramCodeFromTrialMap($, sp, pgc) {
    tmparr = [];
    $(trialProgramCodeMap[sp]).each(function(i, p){
        if (p.code != pgc) {
            tmparr.push(p);
        }
    });
    trialProgramCodeMap[sp] = tmparr;
}


// will add program code to the trial map
function addProgramCodeToTrialMap($, sp, pgc) {
    $(allProgramCodes).each(function(i, p){
        if (p.code == pgc) {
            console.log("pushing : " + p.code + " ==" +  pgc);
            trialProgramCodeMap[sp].push(p);
        }
    });

}

//will refresh the program codes currently selected in the funnel filter
function refreshFilteredProgramCodes($) {
    filteredProgramCodes = [];
    console.log("filteredProgramCodes < []") ;
    if ($("#fpgc-sel").val()) {
       $($("#fpgc-sel").val()).each(function(i, pgc){
           console.log(" pgc [ " + pgc + "]") ;
            $(allProgramCodes).each(function(i, p) {
                console.log("    check [ " + pgc + ", " + p.code + "]") ;
                if (pgc == p.code) {
                    console.log("filteredProgramCodes <<[" + p.code + "]") ;
                    filteredProgramCodes.push(p);
                }
            });
        });
    }
    console.log("filteredProgramCodes :" + filteredProgramCodes);
}

function showProgramCodeS2InRow($, sp) {

    //clear the content of select box
    $("#" + sp + "_trSel").empty();

    tmpS2Ds = [{id:"", text:""}];
    tmparr = [];

    $("#" + sp + "_tra").hide();
    console.log(" tmpS2Ds < []") ;

    //creating data source for s2
    $(trialProgramCodeMap[sp]).each(function(i, p){
        tmparr.push(p.code);
        console.log(" tmpS2Ds << [" + p.code + ", " + p.name + "disabled:true]") ;
        tmpS2Ds.push({id: p.code, text: p.name, disabled:true});
    });

    $(filteredProgramCodes).each(function(i, p){
        if ($.inArray(p.code, tmparr) < 0) {
            console.log(" tmpS2Ds << [" + p.code + ", " + p.name + "disabled:false]") ;
            tmpS2Ds.push({id: p.code, text: p.name});
        }
    });

    $("#" + sp + "_trDiv").show();
    return $("#" + sp + "_trSel").select2({
        data:tmpS2Ds,
        placeholder: "Program Code(s)",
        containerClass: sp + "_s2"
    });

}

// Will enable disable the assign/replace/remove buttons
function toggleMultiButtons($, grayOut) {

    $("button.multi").each(function(i, b){
        $(b).prop('disabled', grayOut);
    });
}

function pgcinit($) {
    allProgramCodes.sort();
    refreshFilteredProgramCodes($);
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
                className:"pgctd",
                data: function ( row, type, val, meta ) {
                    var snippet = "";
                    $(row.programCodes).each(function(i,pc) {
                        snippet = snippet +  '<a id="' + row.studyProtocolId + '_' + pc.code +'_a" href="#" rel="' + row.studyProtocolId + '" pc="' +  pc.code + '" class="pg btn-xs pgcrm ">' + pc.code + ' <span class="pg glyphicon glyphicon-remove"></span></a> ';

                    });

                    snippet = snippet + '<a id="' + row.studyProtocolId + '_tra" href="#" class="pgcssa" rel="' + row.studyProtocolId +'"><span class="glyphicon glyphicon-chevron-down"></span></a>';
                    snippet = snippet + '<div id="' + row.studyProtocolId + '_trDiv" class="pgcssd" style="display:none;"><select id="' + row.studyProtocolId + '_trSel" rel="' + row.studyProtocolId +'" ></select></div>';
                    return snippet;
                }}

        ],
        "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
            trialProgramCodeMap[aData.studyProtocolId] = [];
            $(aData.programCodes).each(function(i,pc) {
                trialProgramCodeMap[aData.studyProtocolId].push({id: pc.id, code:pc.code, name:pc.name});
            });

        }

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
        $('#cfProgramCode').trigger($.Event("change"));
    }

    $('#trialsTbl tbody').on('click', 'a.pgcrm', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(this, 'rel');
        curProgramCode = $.attr(this, 'pc');
        unAssignProgramCode($, $(this).parents("td"), curStudyProtocol, curProgramCode);
    } );

    //populate data for select2 and show it
    $('#trialsTbl tbody').on('click', 'a.pgcssa', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(this, 'rel');
        curS2Ctrl = showProgramCodeS2InRow($, curStudyProtocol);
        curS2Ctrl.on("select2:close", function(evt){
           curStudyProtocol = $.attr(this, 'rel');
           assignProgramCode($, this, $(this).parents("td"), curStudyProtocol, $(this).val());
        });


    } );


    $('#trialsTbl tbody').on('click', 'a.mypgp', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(evt.target, 'rel');
        openParticipationDialog($);
    } );



    //initially disable the assign-remove-replace buttons
    toggleMultiButtons($, true);

    //click on row should select/deselct the row
    $('#trialsTbl tbody').on('click', 'tr', function (evt) {
        evt.preventDefault();
        $(this).toggleClass('selected');

        //enable disable assign-remove-replace button(s)
        toggleMultiButtons($, $("tr.selected").length <= 0);
    } );

    //initialize funnel select
    $("#fpgc-sel").multiselect({
        nonSelectedText: 'Select Program Code(s)' ,
        enableFiltering: true,
        dropRight: true,
        filterPlaceholder: 'Search',
        onDropdownHide: function (evt) {
            $("#fpgc-div").hide();
            refreshFilteredProgramCodes($);
        }
    });



    //initialize the assign multiple popup multiselect
    $("#pgc-madd-sel").multiselect({
        nonSelectedText: 'Select Program Code(s)' ,
        enableFiltering: true,
        dropRight: false,
        filterPlaceholder: 'Search'
    });
    $("#pgc-mrm-sel").multiselect({
        nonSelectedText: 'Select Program Code(s)' ,
        enableFiltering: true,
        dropRight: false,
        filterPlaceholder: 'Search'
    });

    $("#pgc-mrpl-selone").multiselect({
        nonSelectedText: 'Select Program Code'
    });


    $("#pgc-mrpl-seltwo").multiselect({
        nonSelectedText: 'Select Program Code(s)' ,
        enableFiltering: true,
        dropRight: false,
        filterPlaceholder: 'Search'
    });

    //handle click on funnel icon
    $("#fpgc-icon-a").on('click', function (evt) {
        evt.preventDefault();
        $("#fpgc-div").toggle();
    });

}

//will open up the dialog to show multiple PG selection for assignment
function assignMultiple($) {
   //cleanup
   if ($('#pgc-madd-dialog').dialog("instance")) {
       $('#pgc-madd-dialog').dialog("destroy");
   }
    $("#pgc-madd-indicator").hide();

   //show dialog
    $("#pgc-madd-dialog").dialog({
        modal : true,
        autoOpen : true,
        width : $(window).width() * 0.5,
        height : $(window).height() * 0.5,
        buttons : [
            {
                id:"pgc-madd-dialog-ok",
                text:"OK",
                click: function() {
                    handlePopuInteractions($, $('#pgc-madd-sel').val(),[],
                        "pgc-madd",
                        "managePCAssignmentassignProgramCodesToTrials.action",
                        "Program Codes were successfully assigned",
                        "assign");
                }
            },
            {
                id:'pgc-madd-dialog-cancel',
                text:"Cancel",
                click: function() {
                    $("#pgc-madd-dialog").dialog("close");
                }
            }
        ]
    });

}

//will unassign program codes from multiple trials
function removeMultiple($) {
    //cleanup
    if ($('#pgc-mrm-dialog').dialog("instance")) {
        $('#pgc-mrm-dialog').dialog("destroy");
    }
    $('#pgc-mrm-indicator').hide();

    //empty out select box and add only the ones available on the trials
    $("#pgc-mrm-sel").empty();
    tmparr = [];
    $("#trialsTbl > tbody > tr.selected > td.pgctd > a.pg").each(function(i, a){
        if (tmparr.indexOf($(a).attr("pc")) < 0) {
            tmparr.push($(a).attr("pc"));
        }
    });
    $(tmparr.sort()).each(function(i, p){
        tmpS2Ds = $.grep(allProgramCodes, function(pgc, j){
           return pgc.code == p;
        });

        if (tmpS2Ds.length > -1) {
            $("#pgc-mrm-sel").append($("<option />",{value: tmpS2Ds[0].code, text: tmpS2Ds[0].name}));
        }
    });

    //initialize the assign multiple popup multiselect
    $("#pgc-mrm-sel").multiselect("rebuild");

    //show dialog
    $("#pgc-mrm-dialog").dialog({
        modal : true,
        autoOpen : true,
        width : $(window).width() * 0.5,
        height : $(window).height() * 0.5,
        buttons : [
            {
                id:"pgc-mrm-dialog-ok",
                text:"OK",
                click: function() {
                    handlePopuInteractions($, $('#pgc-mrm-sel').val(), [],
                        "pgc-mrm",
                        "managePCAssignmentunassignProgramCodesFromTrials.action",
                        "Program Codes were successfully unassigned",
                        "unassign");
                }
            },
            {
                id:"pgc-mrm-dialog-cancel",
                text:"Cancel",
                click: function() {
                    $("#pgc-mrm-dialog").dialog("close");
                }

            }
        ]
    });
}



//will replace program codes from multiple trials
function replaceMultiple($) {
    //cleanup
    if ($('#pgc-mrpl-dialog').dialog("instance")) {
        $('#pgc-mrpl-dialog').dialog("destroy");
    }
    $('#pgc-mrpl-indicator').hide();

    //empty out first select box and add only the ones available on the trials
    $("#pgc-mrpl-selone").empty();
    tmparr = [];
    $("#trialsTbl > tbody > tr.selected > td.pgctd > a.pg").each(function(i, a){
        if (tmparr.indexOf($(a).attr("pc")) < 0) {
            tmparr.push($(a).attr("pc"));
        }
    });
    $(tmparr.sort()).each(function(i, p){
        tmpS2Ds = $.grep(allProgramCodes, function(pgc, j){
            return pgc.code == p;
        });

        if (tmpS2Ds.length > -1) {
            $("#pgc-mrpl-selone").append($("<option />",{value: tmpS2Ds[0].code, text: tmpS2Ds[0].name}));
        }
    });

    //initialize the first select box
    $("#pgc-mrpl-selone").multiselect("rebuild");

    //empty out second select box and enable only the ones not in trials
    $("#pgc-mrpl-seltwo").empty();
    $(allProgramCodes).each(function(i, pgc){
        if (tmparr.indexOf(pgc.code) > -1){
            $("#pgc-mrpl-seltwo").append($("<option />",{value: pgc.code, text: pgc.name, disabled:true}));
        } else {
            $("#pgc-mrpl-seltwo").append($("<option />",{value: pgc.code, text: pgc.name}));
        }
    });

    //initialize the second select box
    $("#pgc-mrpl-seltwo").multiselect("rebuild");

   //show dialog
    $("#pgc-mrpl-dialog").dialog({
        modal : true,
        autoOpen : true,
        width : $(window).width() * 0.5,
        height : $(window).height() * 0.75,
        buttons : [
            {
                id:"pgc-mrpl-dialog-ok",
                text:"OK",
                click: function() {
                    handlePopuInteractions($, $('#pgc-mrpl-seltwo').val(), [$('#pgc-mrpl-selone').val()],
                        "pgc-mrpl",
                        "managePCAssignmentreplaceProgramCodesInTrials.action",
                        "Program Code was successfully replaced",
                        "replace");
                }
            },
            {
                id:"pgc-mrpl-dialog-cancel",
                text:"Cancel",
                click: function() {
                    $("#pgc-mrpl-dialog").dialog("close");
                }

            }
        ]
    });
}

//will open participation dialog
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
        buttons : [
            {
                id:'participation-close',
                text: "Close",
                click: function() {
                    $(this).dialog("close");
                }
            }
        ]
    });

    //show dialog
    $("#dialog-participation").dialog('open');

    //reload data in table
    participationTable.ajax.reload();
}

//will unassign the program-code for a specific trial
function unAssignProgramCode($, td, sp, pgc) {

    //insert indicator
    $(td).append('<img id="' + sp + "_" + pgc + '_img" src="../images/loading.gif"/>');
    $(td).append('<span id="' + sp + "_" + pgc + '_span" style ="display:none;" class="info">Code unassigned</span>');

    //call ajax function
    $.post("managePCAssignmentunassignProgramCode.action",
        {
            "studyProtocolId": sp,
            "pgcParam": pgc
        })
        .done(function (data) {
            $('#' + sp + '_' + pgc + '_a').remove();
            $('#' + sp + '_' + pgc + '_img').remove();
            removeProgramCodeFromTrialMap($,sp, pgc);
            //show confirmation
            $("#" + sp + "_" + pgc + "_span").show();
            $("#" + sp + "_" + pgc + "_span").delay(3000).hide('slow', function () {
                $("#" + sp + "_" + pgc + "_span").remove();
            });
        })
        .fail(function (jqXHR) {
            $('#' + sp + '_' + pgc + '_img').remove();
            showAjaxErrorOnPage($,jqXHR.getResponseHeader('msg'));
        });
}


//will assign the program-code for a specific trial
function assignProgramCode($, s2, td, sp, pgc) {
    //destroy the select2 and hide the div
    if( $("#" + sp + "_trSel").hasClass("select2-hidden-accessible")) {
        $(s2).select2("destroy");
    }

    $("#" + sp + "_trDiv").hide();

    if (pgc) {
        //show ajax indicator
        $(td).append('<img id="' + sp + "_" + pgc + '_img" src="../images/loading.gif"/>');

        //invoke ajax function
        $.post("managePCAssignmentassignProgramCode.action",
            {
                "studyProtocolId": sp,
                "pgcParam": pgc,
                "familyPoId": $("#familyPoId").val()
            })
            .done(function (data) {
                $('#' + sp + '_' + pgc + '_img').remove();
                addProgramCodeToTrialMap($,sp, pgc);
                //add the program code in the TD
                $('<a id="' + sp + '_' + pgc +'_a" href="#" rel="' + sp + '" pc="' +  pgc + '" class="pg btn-xs pgcrm ">' + pgc + ' <span class="pg glyphicon glyphicon-remove"></span></a> ').insertBefore("#" + sp + "_tra");
                //remove the indicator image
                $('#' + sp + '_' + pgc + '_img').remove();
                //show the down arrow
                $("#" + sp + "_tra").show();
            })
            .fail(function (jqXHR) {
                //remove indicator image and show the down arrow
                $('#' + sp + '_' + pgc + '_img').remove();
                $("#" + sp + "_tra").show();
                showAjaxErrorOnPage($,jqXHR.getResponseHeader('msg'));
            });


    }  else {
        //show the down arrow
        $("#" + sp + "_tra").show();
    }
}

//will handle the user interactions on assign/unassign/replace multiple popups
function handlePopuInteractions($, pgcListParamOne, pgcListParamTwo, containerId, ajaxAction, msg, action) {

    console.log(action + ": handlePopuInteractions [pgcListParamOne:" + pgcListParamOne + ", pgcListParamTwo:" + pgcListParamTwo + "]");
    //check if there are any values selected
    if (pgcListParamOne && pgcListParamTwo) {

        //find the study ids
        tmparr = [];
        $('tr.selected').each(function(i, tr){
            var sp = $(tr).attr("id").split("_")[1];
            //makesure the program code is really present in the study (to support - replace)
            var p = $.grep(trialProgramCodeMap[sp],function(pgc,i) {
                return pgcListParamOne.indexOf(pgc.code) > -1;
            });
            if (p) {
                tmparr.push(sp);
            }

        });

        //show the indicator
        $('#' +containerId+ "-indicator").show();

        console.log(action + ": handlePopuInteractions [studyProtocolList:" +tmparr.join(',') +
            ", pgcListParam:" + pgcListParamOne.join(",") +
            ", pgcParam:" + pgcListParamTwo.join(",") +
            ", familyPoId:" +  $("#familyPoId").val() );

        //invoke ajax function
        $.post(ajaxAction,
            {
                "studyProtocolIdListParam": tmparr.join(','),
                "pgcListParam": pgcListParamOne.join(","),
                "pgcParam": pgcListParamTwo.join(","),
                "familyPoId": $("#familyPoId").val()
            })
            .done(function (data) {
                //close the box
                $('#' +containerId+ "-dialog").dialog("close");

                //refresh the table
                trailsTable.ajax.reload();
                //show the info message
                showInfoMessageOnPage($, msg);

                //disable buttons
                toggleMultiButtons($, true);

            })
            .fail(function (jqXHR) {
                $(containerId + "-Errors").text(jqXHR.getResponseHeader('msg')) ;
                $(containerId + "-Errors").show();
            });
    }  else {
        //no values selected - just close the box
        $('#' +containerId+ "-dialog").dialog("close");
    }
}

// Will show the Ajax errors on the page
function showAjaxErrorOnPage($, msg) {
    $("#pgcErrorsMsg").text(msg);
    $("#pgcErrors").show();
    scroll(0, $("#pgcErrors").position().top);
}


// Will show the Ajax errors on the page
function showInfoMessageOnPage($, msg) {
    $("#pgcInfoMsg").text(msg);
    $("#pgcInfo").show().delay(5000).hide("slow");
    scroll(0, $("#pgcInfo").position().top);

}



