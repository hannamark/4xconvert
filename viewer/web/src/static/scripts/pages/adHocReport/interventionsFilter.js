/**
 * Interventions Filter of the Ad-Hoc Report
 * @input @global interventions Array of interventions [{'id':'', 'name':'', 'type':''}, ...]
 */

var hmTypesDropdown;
var hmTypesDropdownRev;

(function($) {
    $(function() {
        
        //$("a[href='#']").attr('href','##');
        $.jstree._themes = viewerApp.stylePath + "/jstree/themes/";
        
        // Clicking inside textboxes highlights the content
        $('input[type="text"]').bind('click',function (e) {
            $(this).focus();
            $(this).select();
            e.preventDefault();
        });
        
        $('#intervention_selections_count').hide();
        $('#interventionsSection .quickresults_count').show();
        $('#interventionsSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
        
        //**********
        //** Tabs **
        //**********
        $('#reporttabs li a').hover(
            function () {
                $(this).animate({left:20}, 300, function (){
                    $(this).animate({left:0}, 50);
                });
            }, 
            function () {
            }
        );
    
        $('ul#reporttabs li a').bind('click',function (e) {
            var linkIndex = $('ul#reporttabs li a').index(this);
            $('ul#reporttabs li a').removeClass('reporttab-active');
            $('.reporttab:visible').hide();
            $('.reporttab:eq('+linkIndex+')').show();
            $(this).addClass('reporttab-active');
            e.preventDefault();
        });
        
        $('.reporttab:first').show();   
        $('#reporttabs li a:first').addClass('reporttab-active');
    
        //*********************
        //** Category Toggle **
        //*********************
        $('.categorywrapper h2 a').bind('click',function (e) {
            $(this).parents('.categorywrapper').find('.category').slideToggle('slow', function() {});
            e.preventDefault();
        });
           
        $('.category:eq(1)').show();    
        
        if( $('.quickresults').height() != 0 )
            $('.selectionslist').height( $('.quickresults').height() - $('.selectionslist_body').padding().top - $('.selectionslist_body').padding().bottom );
    
        //*****************************************
        //** Initialize widget global variables  **
        //*****************************************
        hmTypesDropdown = {};
        hmTypesDropdownRev = {};
        $.each( $('#interventionType option'), function(index, value) {
            hmTypesDropdown   ['type'+index]=$(value).val();
            hmTypesDropdownRev[$(value).val()]='type'+index;
        });
        
        //******************************
        //** Search for Interventions **
        //******************************
        $('#interventionType').selectmenu();
        adjustQRHeaderAppearance();
        
        $('#interventionsSection .interventionrescol input[type="text"]').autocomplete({
            source : function(request, response) {
                var type = $('#interventionType option:selected').val();
                var results = [];
                if( InterventionsPkg.intvDataDictionary.hasOwnProperty(type) )
                    results = $.ui.autocomplete.filter(InterventionsPkg.intvDataDictionary[type], request.term);
                response(results.slice(0, 8));
            }
        });
        
        function adjustQRHeaderAppearance() {
            $('.interventionrescol .quickresults_header A.ui-selectmenu').css({'height': '1.6em', 'margin': '0.16em 0.3em 0em 0em', 'padding-top': '0.35px'});
            $('.interventionrescol .quickresults_header A.ui-selectmenu .ui-selectmenu-status').css({'line-height': '1.1em', 'font-size':'0.9em'});
            $('.ui-selectmenu-menu .ui-widget LI A').css({'line-height': '1.1em', 'font-size':'0.9em'});
            $('.interventionrescol .quickresults_header A.ui-selectmenu').width( $('.interventionrescol .quickresults_header A.ui-selectmenu').width() * 1.1);
            $('.ui-selectmenu-menu .ui-widget').width( $('.ui-selectmenu-menu .ui-widget').width() * 1.1);
            $('.ui-selectmenu-menu .ui-widget').css({'height': ''});
            $('.interventionrescol INPUT#intervention').parent().css({'margin': '0em 0.3em 0em 0.5em'});
        }

        $('#interventionsSection .search_inner_button').bind('click',function (e) {
            searchInterventions();
            e.preventDefault();
        });
    
        $('#interventionsSection .interventionrescol input[type="text"]').keypress(function(e) {
            if(e.keyCode == 13) {
                searchInterventions();
                e.preventDefault();
            }
        });
        
        function searchInterventions() {
            $('.ui-autocomplete').hide();
            var searchTerm = $('#interventionsSection .interventionrescol input[type="text"]').val();
            var type = $('#interventionType option:selected').val();
            var resultIndexes = InterventionsPkg.searchInterventions( searchTerm, type );
            buildInterventionList( searchTerm, resultIndexes, type );
            updateQuickresultsCount( searchTerm, type, false );
    
            $.each( $('#interventionslist LI'), function(index, value) {
                $(value).live('click',function (e) {
                    var id = $(this).attr('id').match(/interventions_list_id(\d+)/)[1];
                    var selectedElement = $('<div></div>');
                    selectedElement.attr('id', id);
                    selectedElement.append($(this).clone());
                    addToSelections( generateSelectionItemBlock(selectedElement), false );
                    updateSelections(e);
                });
            });
        }
        
        function buildInterventionList( searchTerm, resultIndexes, type ) {
            $('#interventionslist').empty();
            for( var i in resultIndexes ) {
                if(resultIndexes.hasOwnProperty(i)) {
                    var index = resultIndexes[i];
                    var id   = InterventionsPkg.intvData[type][index].id;
                    var name = InterventionsPkg.intvData[type][index].name;
                    var html = name.replace(searchTerm,'<span class="pdq-tree-highlight">'+searchTerm+'</span>')
                    $('<li></li>').html(html).attr('id','interventions_list_id'+id).attr('title','Click to add this item to your selections for the search').appendTo('#interventionslist');
                }
            }
        }
        
        function addToSelections( item, byType ) {
            var total=0, distinct=0;
            $('#interventionsSection .selectionslist_body li div').each(function(index) {
                total++;
                var candidate = unescapeHTML($(this).html());
                if( item.html != candidate ) 
                    distinct++;
            });
            if( total == distinct ) {
                if( byType ) {
                    $('#interventionsSection .selectionslist_body').prepend('<li class="selected_intv_by_type" id=selection_box_id' + hmTypesDropdownRev[item.id] +'><a href="#" title="Click to remove" /><div>' + item.html + '</div></li>');
                    $('#interventionTypes').append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
                } else {
                    $('#interventionsSection .selectionslist_body').prepend('<li id=selection_box_id' + item.id +'><a href="#" title="Click to remove" /><div>' + item.html + '</div></li>');
                    $('#interventions').append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
                }
            }
        }
        
        function updateSelections(e) {
            $('#interventionsSection .selectionslist_body li a').bind('click',function (e) {
                var matches = $(this).parent().attr('id').match(/selection_box_id(.+)/);
                $('#interventions option[value="'+matches[1]+'"]').remove();
                $('#interventionTypes option[value="'+hmTypesDropdown[matches[1]]+'"]').remove();
                $(this).parent().remove();
                updateSelectionCount();
                e.preventDefault();
            });     
            updateSelectionCount(); 
            e.preventDefault();             
        }
        
        function updateQuickresultsCount( searchTerm, type, bInit ) {
            if( bInit ) {
                $('#interventionsSection .quickresults_count').text( 'Hint: Press <Enter> when finished typing' ).show(); 
            } else {
                var count = $('#interventionsSection #interventionslist li').length;
                if( count == 1 ) 
                    $('#interventionsSection .quickresults_count').text( '' + count + ' result for "' + searchTerm + '" in ' + type ).show(); 
                else
                    $('#interventionsSection .quickresults_count').text( '' + count + ' results for "' + searchTerm + '" in ' + type ).show();
            }
        }   
        
        //************************************************
        //** Add Interventions to Selected (for Report) **
        //************************************************
        $('.quickresults_header_buttons #add_all_intervention').bind('click',function (e) {
            $($('#interventionslist LI').get().reverse()).each( function() {
                var id = $(this).attr('id').match(/interventions_list_id(\d+)/)[1];
                var selectedElement = $(this).clone();
                selectedElement.attr('id', id);
                addToSelections( generateSelectionItemBlock(selectedElement), false );
            });
            updateSelections(e);
        });
        
        function generateSelectionItemBlock( item ) {
            var name = $.trim(item.text());
            var innerHtml ='<span class="selectionFeaturedElement">'+name+'</span>';
            var id = item.attr('id');
            return { 'id':id, 'name':name, 'html':innerHtml };
        }
        
        function updateSelectionCount() {
            var count = $('#interventionsSection .selectionslist_body li').length;
            if( count == 0 )
                $('#intervention_selections_count').stop(true,true).hide()
                        .removeClass('selections_count_normal').removeClass('selections_count_highlight').addClass('selections_count_normal').text( 'no selections added' ); 
            else {
                var newText = '';
                if( count == 1 )    
                    newText = '1 selection added';
                else 
                    newText = '' + count + ' selections added';
                
                var oldText = $('#intervention_selections_count').text();
                $('#intervention_selections_count').text( newText );    
                if( oldText != newText ) {
                    $('#intervention_selections_count').stop(true,true).hide()
                            .removeClass('selections_count_normal').removeClass('selections_count_highlight').addClass('selections_count_highlight')
                            .show().delay(1000).switchClass('selections_count_highlight', 'selections_count_normal', 1000); 
                }   
            } 
        }   
        
        //*******************************
        //** Add Interventions by Type **
        //*******************************
        function updateAddByTypeButton() {
            var tip1 = 'Select Intervention Type from the drop-down on the left to enable adding by type';
            var tip2 = 'Add all intervention of the type %type% to your search selection';
        }

        updateAddByTypeButton();
        
        $('.quickresults_header_buttons #add_type_intervention').bind('click',function (e) {
            var type = $('#interventionType option:selected').val();
            if( type != 'All' ) {
                addToSelections( generateTypeSelectionItemBlock(type), true );
                updateSelections(e);
            }
            e.preventDefault();             
        });
        
        function generateTypeSelectionItemBlock( type ) {
            var name = 'All Interventions of the type ' + type;
            var innerHtml ='<span class="selectionFeaturedElement">'+name+'</span>';
            return { 'id':type, 'name':name, 'html':innerHtml };
        }
        
        //***********
        //** Reset **
        //***********
        $('.quickresults_header_buttons #reset_intervention').bind('click',function (e) {
            resetInterventionFilter();
            e.preventDefault();
        });
        
        function resetInterventionFilter() {
            $('#interventionslist').empty();
            $('#interventionsSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
            $('#interventionsSection input[id="intervention"]').val('Start typing a search term...');
            updateQuickresultsCount('','',true);
            $('#interventionsSection .selectionslist_body').empty();
            $('#interventions').empty();
            $('#interventionTypes').empty();
            updateSelectionCount();
        }
        
/*      
        //*************************************************
        //** Interventions Tree in sepatate popup dialog **
        //*************************************************
        var pdqDialog = $('<div id="pdq_tree_dialog"></div>')
                .html( generateInterventionsTreeHtml() )
                .dialog({
                    autoOpen: false,
                    modal: false, 
                    title: 'Interventions Tree',
                    position: [30,5],
                    width: 570,
                    height: 300
                });
        $('.ui-dialog .ui-dialog-content').css({'padding': '.4em .3em .3em .3em'});
                
        $('#show_tree_intervention').click(function(e) {
            showTree();
            e.preventDefault();
        });
        
        function showTree( id, parentIds ) {
            pdqDialog.dialog('open');
            $('#pdq_tree_dialog').prev().css({'background-color': '#FF8080', 'background-image': 'none', 'border': '1px solid #A06060'});
            $('.pdq-tree-highlight').each(function(index,value) {
                var node = $(value).parents('a:first');
                var name = $.trim($(node).text());
                $(node).html( $(node).html().replace('<span class="pdq-tree-highlight">'+name+'</span>',name) );
            });
            if( typeof(id) != 'undefined' ) {
                var openNodeIds = $.map( $('#pdq_tree').find('.jstree-open').toArray(), function(val,i) { 
                    return $(val).attr('id').match(/ptid([\d_]+)/)[1]; 
                });
                var treeParentIds = $(parentIds).toArray().reverse();
                treeParentIds = $.map( $(treeParentIds), function(val,i) {
                    return '_' + $(treeParentIds).toArray().slice(0,i+1).join('_');
                });
                for( var i in treeParentIds ) { // If some of the parents of our to-be-highlighted node are already open we are not going to close them unlike the rest of open nodes
                    if(!treeParentIds.hasOwnProperty(i))
                        continue;
                    var parentId = treeParentIds[i];
                    if( $.inArray(parentId, openNodeIds) != -1 )
                        openNodeIds.splice( openNodeIds.indexOf(parentId), 1 );
                }
                var thisId = (treeParentIds.length>0 ? treeParentIds[treeParentIds.length-1] : '') + '_' + id;
                $('#pdq_tree').jstree("deselect_all");
                $('#pdq_tree').jstree("select_node", $('#ptid'+thisId));
                var name = $.trim($('#ptid'+thisId).children('a').text());
                $('#ptid'+thisId).html( $('#ptid'+thisId).html().replace(name,'<span class="pdq-tree-highlight">'+name+'</span>') );
            }
            adjustInterventionsTreeDimensions();
        }
        
        function generateInterventionsTreeHtml() {
            var pdqTree = new NBTree();
            pdqTree.buildFromFlatData( InterventionsPkg.intvData );
            var jsTreeJsonData = pdqTree.generateJstreeJsonData();
            var pdqTree = $('<div id="pdq_tree"></div>');
            $(function () {
                $('#pdq_tree').jstree({ 
                    'plugins' : [ 'json_data', 'themes', 'ui', 'hotkeys' ],
                    'core' : { 'initially_open' : $.map( InterventionsPkg.tree_initially_open, function(val,i) { return 'ptid'+val }) },
                    'json_data' : jsTreeJsonData,
                    'themes' : { 'theme' : 'default-p' },
                    'ui' : {'select_limit' : 1, 'selected_parent_close' : 'select_parent' }
                }).bind("select_node.jstree", function (e, data) { 
                    if ( typeof(data.rslt.e) != "undefined") { // Selection generated by mouse click
                        var id = data.rslt.obj.attr('id').match(/ptid([\d_]+)/)[1];
                        var dataIds = id.substring(1).split('_');
                        id = dataIds[dataIds.length-1];
                        dataIds.splice(dataIds.length-1,1);
                        dataIds.reverse();
                        var breadcrumbsPkg = {
                            'bcItems' : [breadcrumbsPkg.createBox( id, dataIds, InterventionsPkg.intvData )],
                            'searchTerm' : ''
                        };
                        var bcItemMarkup = $('<div></div>').buildBreadcrumbs( breadcrumbsPkg );
                        var selectedElement = bcItemMarkup.find('.breadcrumbItemBox');
                        selectedElement.attr('id', id);
                        addToSelections( generateSelectionItemBlock(selectedElement), false );
                        updateSelections(e);
                    } else { // Selection comes from click on breadcrumb image link
                        //data.inst._fix_scroll(data.rslt.obj);
                        scrollInterventionsTreeNodeIntoView(data.rslt.obj);
                    }
                }).bind("open_node.jstree close_node.jstree", function (e, data) { 
                    // 
                }).bind("after_open.jstree after_close.jstree", function (e, data) { 
                    adjustInterventionsTreeDimensions();
                });
            });
            return pdqTree;
        }
        
        function adjustInterventionsTreeDimensions() {
            var clientHeight = getRealInterventionsTreeClientHeight();
            var viewHeight = $('#pdq_tree_dialog').height();
            if( clientHeight < viewHeight )
                $('#pdq_tree').height(viewHeight);
            else
                $('#pdq_tree').height(clientHeight);
        }
        
        function scrollInterventionsTreeNodeIntoView( node ) {
            var clientHeight = getRealInterventionsTreeClientHeight();
            var viewHeight = $('#pdq_tree_dialog').height();
            var nodeTop = $(node).offset().top - $('#pdq_tree').offset().top;
            var scrollTop = $('#pdq_tree_dialog').scrollTop();
            if( viewHeight < clientHeight || nodeTop<scrollTop || nodeTop>scrollTop+viewHeight  ) {
                var newScrollTop =  Math.round( nodeTop - viewHeight/2 );
                newScrollTop = newScrollTop>=0 ? newScrollTop : 0; 
                $('#pdq_tree_dialog').scrollTop( newScrollTop );
            }
        }
        
        function getRealInterventionsTreeClientHeight() {
            var lastNode = $('#pdq_tree li:visible:last');
            var realHeight = 0;
            if($(lastNode).toArray().length > 0)
                realHeight = $(lastNode).offset().top + $(lastNode).height() - $('#pdq_tree').offset().top;
            return realHeight;
        }
*/      
    
        //*******************
        //** Miscellaneous **
        //*******************
        function escapeHTML(html) {
            var escaped = html;
            var findReplace = [[/&/g, "&amp;"], [/</g, "&lt;"], [/>/g, "&gt;"], [/"/g, "&quot;"]]
            for(var item in findReplace)
                if(findReplace.hasOwnProperty(item))
                    escaped = escaped.replace(findReplace[item][0], findReplace[item][1]);  
            return escaped;
        }
    
        function unescapeHTML(html) {
            var unescaped = html;
            var findReplace = [[/&amp;/g, "&"], [/&lt;/g, "<"], [/&gt;/g, ">"], [/&quot;/g, "\""]]
            for(var item in findReplace)
                if(findReplace.hasOwnProperty(item))
                    unescaped = unescaped.replace(findReplace[item][0], findReplace[item][1]);  
            return unescaped;
        }
    });
})(jQuery);


InterventionsPkg = {
    intvData : {},
    intvDataDictionary : {},
    
    initInterventionsData : function() {
        this.intvData = {};
        for( i in interventions ) {
            if(!interventions.hasOwnProperty(i))
                continue;
            var intvItem = interventions[i];
            if( !this.intvData.hasOwnProperty( intvItem.type ))
                this.intvData[intvItem.type] = [{'id':intvItem.id, 'name':intvItem.name}];
            else
                this.intvData[intvItem.type].push({'id':intvItem.id, 'name':intvItem.name});
        }
        
        var hmNames = {};
        for( var type in this.intvData ) {
            if(this.intvData.hasOwnProperty(type)) {
                var data = this.intvData[type];
                for( var i in data ) {
                    if(data.hasOwnProperty(i)) {
                        var intvItem = data[i];
                        if(!hmNames.hasOwnProperty(intvItem.name))
                            hmNames[intvItem.name] = intvItem;
                    }
                }
            }
        }

        this.intvData['All']=[];
        for( var name in hmNames ) 
            if(hmNames.hasOwnProperty(name)) 
                this.intvData['All'].push({'id':hmNames[name].id, 'name':hmNames[name].name});
    },
    
    initInterventionsDictionary : function() {
        this.intvDataDictionary = {};
        for( var type in this.intvData ) {
            if(this.intvData.hasOwnProperty(type)) {
                this.intvDataDictionary[type]=[];
                var data = this.intvData[type];
                var hmDictionary = {};
                for( var i in data ) {
                    if(data.hasOwnProperty(i)) {
                        var intvItem = data[i];
                        var words = intvItem.name.split(' ');
                        for( j in words )
                            if(words.hasOwnProperty(j))
                                hmDictionary[words[j]]=1;
                    }
                }
                for( word in hmDictionary )
                    if(hmDictionary.hasOwnProperty(word))
                        this.intvDataDictionary[type].push(word);
            }
        }
    },

    init : function() {
        this.initInterventionsData();
        this.initInterventionsDictionary();
    },  

    searchInterventions : function(term,type) {
        var ids=[];
        
        for( var i in this.intvData[type] ) {
            if(!this.intvData[type].hasOwnProperty(i))
                continue;
            var intvItem = this.intvData[type][i];
            if(intvItem.name.indexOf(term)!=-1)
                ids.push(i);                    
        }
        return ids;
    }
};

InterventionsPkg.init();
