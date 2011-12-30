/**
 * Interventions Filter of the Ad-Hoc Report
 * @input @global interventions Array of interventions [{'id':'', 'name':'', 'type':''}, ...]
 */

var hmTypesDropdown;
var hmTypesDropdownRev;

(function($) {
    $.interventionsFilter = {
        //******************************
        //** Search for Interventions **
        //******************************
        adjustQRHeaderAppearance : function() {
            $('.interventionrescol .quickresults_header A.ui-selectmenu').css({'height': '1.6em', 'margin': '0.16em 0.3em 0em 0em', 'padding-top': '0.35px'});
            $('.interventionrescol .quickresults_header A.ui-selectmenu .ui-selectmenu-status').css({'line-height': '1.1em', 'font-size':'0.9em'});
            $('.ui-selectmenu-menu .ui-widget LI A').css({'line-height': '1.1em', 'font-size':'0.9em'});
            $('.interventionrescol .quickresults_header A.ui-selectmenu').width( $('.interventionrescol .quickresults_header A.ui-selectmenu').width() * 1.1);
            $('.ui-selectmenu-menu .ui-widget').width( $('.ui-selectmenu-menu .ui-widget').width() * 1.1);
            $('.ui-selectmenu-menu .ui-widget').css({'height': ''});
            $('.interventionrescol INPUT#intervention').parent().css({'margin': '0em 0.3em 0em 0.5em'});
        },
        
        searchInterventions : function() {
            var self = this;
            $('.ui-autocomplete').hide();
            var searchTerm = $('#interventionsSection .interventionrescol input[type="text"]').val().toLowerCase();
            var type = $('#interventionType option:selected').val();
            var resultIndexes = InterventionsPkg.searchInterventions( searchTerm, type );
            self.buildInterventionList( searchTerm, resultIndexes, type );
            self.updateQuickresultsCount( searchTerm, type, false );
    
            $.each( $('#interventionslist LI'), function(index, value) {
                $(value).live('click',function (e) {
                    var id = $(this).attr('id').match(/interventions_list_id(\d+)/)[1];
                    var selectedElement = $('<div></div>');
                    selectedElement.attr('id', id);
                    selectedElement.append($(this).clone());
                    self.addToSelections( self.generateSelectionItemBlock(selectedElement), false );
                    self.updateSelections(e);
                });
            });
        },
        
        buildInterventionList : function( searchTerm, resultIndexes, type ) {
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
        },
        
        addToSelections : function( item, byType ) {
            var self = this;
            var total=0, distinct=0;
            $('#interventionsSection .selectionslist_body li div').each(function(index) {
                total++;
                var candidate = self.unescapeHTML($(this).html());
                if( item.html != candidate ) 
                    distinct++;
            });
            if( total == distinct ) {
                if( byType ) {
                    $('#interventionsSection .selectionslist_body').prepend('<li class="selected_intv_by_type" id=sbidi_' + hmTypesDropdownRev[item.id] +'><a href="#" title="Click to remove" /><div>' + item.html + '</div></li>');
                    $('#interventionTypes').append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
                } else {
                    $('#interventionsSection .selectionslist_body').prepend('<li id=sbidi_' + item.id +'><a href="#" title="Click to remove" /><div>' + item.html + '</div></li>');
                    $('#interventions').append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
                }
            }
        },
        
        updateSelections : function(e) {
            var self = this;
            $('#interventionsSection .selectionslist_body li a').bind('click',function (e) {
                var matches = $(this).parent().attr('id').match(/sbidi_(.+)/);
                $('#interventions option[value="'+matches[1]+'"]').remove();
                $('#interventionTypes option[value="'+hmTypesDropdown[matches[1]]+'"]').remove();
                $(this).parent().remove();
                self.updateSelectionCount();
                e.preventDefault();
            });     
            self.updateSelectionCount(); 
            if( e!=null)
                e.preventDefault();             
        },
        
        updateQuickresultsCount : function( searchTerm, type, bInit ) {
            if( bInit ) {
                $('#interventionsSection .quickresults_count').text( 'Hint: Press <Enter> when finished typing' ).show(); 
            } else {
                var count = $('#interventionsSection #interventionslist li').length;
                if( count == 1 ) 
                    $('#interventionsSection .quickresults_count').text( '' + count + ' result for "' + searchTerm + '" in ' + type ).show(); 
                else
                    $('#interventionsSection .quickresults_count').text( '' + count + ' results for "' + searchTerm + '" in ' + type ).show();
            }
        },   
        
        //************************************************
        //** Add Interventions to Selected (for Report) **
        //************************************************
        generateSelectionItemBlock : function( item ) {
            var name = $.trim(item.text());
            var innerHtml ='<span class="selectionFeaturedElement">'+name+'</span>';
            var id = item.attr('id');
            return { 'id':id, 'name':name, 'html':innerHtml };
        },
        
        updateSelectionCount : function() {
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
        },   
        
        //*******************************
        //** Add Interventions by Type **
        //*******************************
        updateAddByTypeButton : function() {
            var tip1 = 'Select Intervention Type from the drop-down on the left to enable adding by type';
            var tip2 = 'Add all intervention of the type %type% to your search selection';
        },

        generateTypeSelectionItemBlock : function( type ) {
            var name = 'All Interventions of the type ' + type;
            var innerHtml ='<span class="selectionFeaturedElement">'+name+'</span>';
            return { 'id':type, 'name':name, 'html':innerHtml };
        },
        
        //***********
        //** Reset **
        //***********
        resetInterventionFilter : function() {
            var self = this;
            $('#interventionslist').empty();
            $('#interventionsSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
            $('#interventionsSection input[id="intervention"]').val('Start typing a search term...');
            self.updateQuickresultsCount('','',true);
            $('#interventionsSection .selectionslist_body').empty();
            $('#interventions').empty();
            $('#interventionTypes').empty();
            self.updateSelectionCount();
        },

        //************************
        //** Save/Restore state **
        //************************
        saveState : function() {
            var stateObj = {
                'selection_boxes': $.map( $('li[id^=sbidi_]'), function(val,i) {
                    return $(val).attr('id');  
                })
            };
            var stateStr = $.toJSON(stateObj);
            setCookieForDays( "interventionsFilterState", stateStr, 2 );
        },

        restoreState : function() {
            var self = this;
            var stateStr = getAndDeleteCookie( 'interventionsFilterState' );
            var stateObj = $.evalJSON( stateStr );
            if( stateObj != null) {
                if( typeof(stateObj.selection_boxes) != 'undefined' && stateObj.selection_boxes != null ) {
                    $.each( $.evalJSON(stateObj.selection_boxes), function(index, value) {
                        var id = value.match(/sbidi_(.+)/)[1];
                        var byType = id.indexOf('type')==0;
                        if( byType ) {
                            var type = hmTypesDropdown[id];
                            self.addToSelections( self.generateTypeSelectionItemBlock(type), true );
                        } else {
                            var text = InterventionsPkg.getInterventionName(id);
                            var selectedElement = $('<div></div>').attr('id', id).text(text);
                            self.addToSelections( self.generateSelectionItemBlock(selectedElement), false );
                        }
                        self.updateSelections(null);
                    });
                }
            }
        },

        //*******************
        //** Miscellaneous **
        //*******************
        escapeHTML : function(html) {
            var escaped = html;
            var findReplace = [[/&/g, "&amp;"], [/</g, "&lt;"], [/>/g, "&gt;"], [/"/g, "&quot;"]]
            for(var item in findReplace)
                if(findReplace.hasOwnProperty(item))
                    escaped = escaped.replace(findReplace[item][0], findReplace[item][1]);  
            return escaped;
        },
    
        unescapeHTML : function(html) {
            var unescaped = html;
            var findReplace = [[/&amp;/g, "&"], [/&lt;/g, "<"], [/&gt;/g, ">"], [/&quot;/g, "\""]]
            for(var item in findReplace)
                if(findReplace.hasOwnProperty(item))
                    unescaped = unescaped.replace(findReplace[item][0], findReplace[item][1]);  
            return unescaped;
        }
    };
    
    //******************
    //** On DOM Ready **
    //******************
    $(function() {
        $.jstree._themes = viewerApp.stylePath + "/jstree/themes/";
        var ivf = $.interventionsFilter;
        
        //*****************************************
        //** Initialize widget global variables  **
        //*****************************************
        hmTypesDropdown = {};
        hmTypesDropdownRev = {};
        $.each( $('#interventionType option'), function(index, value) {
            hmTypesDropdown   ['type'+index]=$(value).val();
            hmTypesDropdownRev[$(value).val()]='type'+index;
        });
        
        // Clicking inside textboxes highlights the content
        $('input[type="text"]').bind('click',function (e) {
            $(this).focus();
            $(this).select();
            e.preventDefault();
        });
        
        $('#intervention_selections_count').hide();
        $('#interventionsSection .quickresults_count').show();
        $('#interventionsSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
        
        ivf.restoreState();

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
    
        //******************************
        //** Search for Interventions **
        //******************************
        $('#interventionType').selectmenu();
        ivf.adjustQRHeaderAppearance();
        
        $('#interventionsSection .interventionrescol input[type="text"]').autocomplete({
            source : function(request, response) {
                var type = $('#interventionType option:selected').val();
                var results = [];
                if( InterventionsPkg.intvDataDictionary.hasOwnProperty(type) )
                    results = $.ui.autocomplete.filter(InterventionsPkg.intvDataDictionary[type], request.term);
                response(results.slice(0, 8));
            }
        });
        
        $('#interventionsSection .search_inner_button').bind('click',function (e) {
            ivf.searchInterventions();
            e.preventDefault();
        });
    
        $('#interventionsSection .interventionrescol input[type="text"]').keypress(function(e) {
            if(e.keyCode == 13) {
                ivf.searchInterventions();
                e.preventDefault();
            }
        });
        
        //************************************************
        //** Add Interventions to Selected (for Report) **
        //************************************************
        $('.quickresults_header_buttons #add_all_intervention').bind('click',function (e) {
            $($('#interventionslist LI').get().reverse()).each( function() {
                var id = $(this).attr('id').match(/interventions_list_id(\d+)/)[1];
                var selectedElement = $(this).clone();
                selectedElement.attr('id', id);
                ivf.addToSelections( ivf.generateSelectionItemBlock(selectedElement), false );
            });
            ivf.updateSelections(e);
        });
        
        //*******************************
        //** Add Interventions by Type **
        //*******************************
        ivf.updateAddByTypeButton();
        
        $('.quickresults_header_buttons #add_type_intervention').bind('click',function (e) {
            var type = $('#interventionType option:selected').val();
            if( type != 'All' ) {
                ivf.addToSelections( ivf.generateTypeSelectionItemBlock(type), true );
                ivf.updateSelections(e);
            }
            e.preventDefault();             
        });
        
        //***********
        //** Reset **
        //***********
        $('.quickresults_header_buttons #reset_intervention').bind('click',function (e) {
            ivf.resetInterventionFilter();
            e.preventDefault();
        });
    });
})(jQuery);


InterventionsPkg = {
    intvData : {},
    intvNamesMap : {},
    intvDataDictionary : {},
    
    initInterventionsData : function() {
        this.intvData = {};
        this.intvNamesMap = {};
        for( i in interventions ) {
            if(!interventions.hasOwnProperty(i))
                continue;
            var intvItem = interventions[i];
            if( !this.intvData.hasOwnProperty( intvItem.type ))
                this.intvData[intvItem.type] = [{'id':intvItem.id, 'name':intvItem.name.toLowerCase()}];
            else
                this.intvData[intvItem.type].push({'id':intvItem.id, 'name':intvItem.name.toLowerCase()});

            this.intvNamesMap[intvItem.id] = intvItem.name.toLowerCase();
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
                this.intvData['All'].push({'id':hmNames[name].id, 'name':hmNames[name].name.toLowerCase()});
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
    },
    
    getInterventionName : function(id) {
        return this.intvNamesMap[id];
    }
};

InterventionsPkg.init();
