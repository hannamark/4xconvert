/**
 * Disease Filter of the Ad-Hoc Report
 * @input @global diseaseTree PDQ disease tree to run the name lookup against [{'id':'', 'name':'', 'parentId':''}, ...]
 */

var bJstreeOperationReady = true;
var jstreeNodeIdsToClose = [];
var jstreeNodeIdsToOpen = [];

function isJstreeOperationReady() {
    return bJstreeOperationReady;
}

function setJstreeOperationReady(bReady) {
    bJstreeOperationReady = bReady;
}

(function($) {
    $(function() {

        $.jstree._themes = viewerApp.stylePath + "/jstree/themes/";
        
        // Clicking inside textboxes highlights the content
        $('input[type="text"]').bind('click',function (e) {
            $(this).focus();
            $(this).select();
            e.preventDefault();
        });
        
        $('#disease_selections_count').hide();
        $('#diseasesSection .quickresults_count').show();
        $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
        
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
    
        //*******************************
        //** Search for Disease in PDQ **
        //*******************************
        $('#diseasesSection .diseaserescol input[type="text"]').autocomplete({
            source : function(request, response) {
                var results = $.ui.autocomplete.filter(FiveAmUtil.PDQPkg.pdqDataDictionary, request.term);
                response(results.slice(0, 8));
            }
        });
        
        $('#diseasesSection .search_inner_button').bind('click',function (e) {
            searchDiseases();
            e.preventDefault();
        });
    
        $('#diseasesSection .diseaserescol input[type="text"]').keypress(function(e) {
            if(e.keyCode == 13) {
                searchDiseases();
                e.preventDefault();
            }
        });
        
        function searchDiseases() {
            $('.ui-autocomplete').hide();
            var searchTerm = $('#diseasesSection .diseaserescol input[type="text"]').val().toLowerCase();
            var searchResultsPDQ = FiveAmUtil.PDQPkg.searchPDQ( searchTerm );
            var breadcrumbsPkg = FiveAmUtil.breadcrumbsPkg.createPackage( searchTerm, searchResultsPDQ, FiveAmUtil.PDQPkg.pdqData );
            $('#diseasebreadcrumbs').buildBreadcrumbs( breadcrumbsPkg );
            updateQuickresultsCount( searchTerm, false );
            $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
            adjustBreadcrumbAppearance();
    
            $.each([$('.breadcrumbElementText'), $('.breadcrumbFeaturedElementText')], function(index, value) {
                $(value).live('click',function (e) {
                    var id = $(this).parent().attr('id').match(/breadcrumb_box\d+_id(\d+)/)[1];
                    var selectedElement = $('<div></div>');
                    selectedElement.attr('id', id);
                    selectedElement.append($(this).parent().prevAll().clone().get().reverse());
                    selectedElement.append($(this).clone());
                    addToSelections(generateSelectionItemBlock(selectedElement));
                    updateSelections(e);
                });
            });
            
            $.each([$('.breadcrumbElementImageLink > a'), $('.breadcrumbFeaturedElementImageLink > a')], function(index, value) {
                $(value).live('click',function (e) {
                    var id = $(this).parent().parent().attr('id').match(/breadcrumb_box\d+_id(\d+)/)[1];
                    var parentIds = [];
                    $(this).parent().parent().prevAll('div[id*="breadcrumb_box"]').each( function(index2, value2) {
                        parentIds.push($(value2).attr('id').match(/breadcrumb_box\d+_id(\d+)/)[1]);
                    });
                    showTree( id, parentIds );
                    e.preventDefault();
                });
            });
        }
        
        function adjustBreadcrumbAppearance() {
            var regTextHeight = $('.breadcrumbElementText').outerHeight();
            $('.breadcrumbElementImageLink').height(regTextHeight);
    
            var featTextHeight = $('.breadcrumbFeaturedElementText').outerHeight();
            $('.breadcrumbFeaturedElementImageLink').height(featTextHeight);
    
            var regLinkHeight = $('.breadcrumbElementImageLink A').outerHeight();
            var regMarginTop = (regTextHeight-regLinkHeight)/2
            $('.breadcrumbElementImageLink A').css({'margin-top': ''+regMarginTop+'px'});
    
            var featLinkHeight = $('.breadcrumbFeaturedElementImageLink A').outerHeight();
            var featMarginTop = (featTextHeight-featLinkHeight)/2
            $('.breadcrumbFeaturedElementImageLink A').css({'margin-top': ''+featMarginTop+'px'});
        }
            
        function addToSelections(item) {
            var total=0, distinct=0;
            $('#diseasesSection .selectionslist_body li div').each(function(index) {
                total++;
                var candidate = unescapeHTML($(this).html());
                if( item.html != candidate ) 
                    distinct++;
            });
            if( total == distinct ) {
                $('#diseasesSection .selectionslist_body').prepend('<li id=selection_box_id' + item.id +'><a href="#" title="Click to remove" /><div>' + item.html + '</div></li>');
                $('#pdqDiseases').append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
            }
        }
        
        function updateSelections(e) {
            $('#diseasesSection .selectionslist_body li a').bind('click',function (e) {
                $('#pdqDiseases option[value="'+$(this).parent().attr('id').match(/selection_box_id(\d+)/)[1]+'"]').remove();
                $(this).parent().remove();
                updateSelectionCount();
                e.preventDefault();
            });     
            updateSelectionCount(); 
            e.preventDefault();             
        }
        
        function updateQuickresultsCount( searchTerm, bInit ) {
            if( bInit ) {
                $('#diseasesSection .quickresults_count').text( 'Hint: Press <Enter> when finished typing' ).show(); 
            } else {
                var count = $('#diseasesSection #diseasebreadcrumbs .breadcrumbItemPane').length;
                if( count == 1 ) 
                    $('#diseasesSection .quickresults_count').text( '' + count + ' result for "' + searchTerm + '"' ).show(); 
                else
                    $('#diseasesSection .quickresults_count').text( '' + count + ' results for "' + searchTerm + '"' ).show();
            }
        }   
        
        //****************
        //** Select All **
        //****************
        $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').bind('click',function (e) {
            $('#diseasesSection .quickresults_body input[type="checkbox"]').attr('checked', $(this).is(':checked'));   
        });
        
        //*******************************************
        //** Add Diseases to Selected (for Report) **
        //*******************************************
        $('.quickresults_header_buttons #add_all_disease').bind('click',function (e) {
            $($('.breadcrumbItemBox').get().reverse()).each( function() {
                var id = $(this).children().last().attr('id').match(/breadcrumb_box\d+_id(\d+)/)[1];
                var selectedElement = $(this).clone();
                selectedElement.attr('id', id);
                addToSelections(generateSelectionItemBlock(selectedElement));
            });
            updateSelections(e);
        });
        
        function generateSelectionItemBlock( item ) {
            var innerHtml = '<span class="selectionElementText">';
            $.each(item.children(), function(index, value) {
                if (index < item.children().length-1) {
                    innerHtml += $(this).text();
                    return true;
                }
                return false;
            });
            innerHtml += '</span><br>';
            if (innerHtml == '<span class="selectionElementText"></span><br>') {
                innerHtml = '';
            }    
            var name = $.trim(item.children().last().text());
            innerHtml += '<span class="selectionFeaturedElement">'+name+'</span>';
            var id = item.attr('id');
            return { 'id':id, 'name':name, 'html':innerHtml };
        }
        
        function updateSelectionCount() {
            var count = $('#diseasesSection .selectionslist_body li').length;
            if (count == 0) {
                $('#disease_selections_count').stop(true,true).hide()
                        .removeClass('selections_count_normal').removeClass('selections_count_highlight').addClass('selections_count_normal').text( 'no selections added' ); 
            } else {
                var newText = '';
                if (count == 1) {
                    newText = '1 selection added';
                } else {
                    newText = '' + count + ' selections added';
                }
                var oldText = $('#disease_selections_count').text();
                $('#disease_selections_count').text( newText ); 
                if (oldText != newText) {
                    $('#disease_selections_count').stop(true,true).hide()
                            .removeClass('selections_count_normal').removeClass('selections_count_highlight').addClass('selections_count_highlight')
                            .show().delay(1000).switchClass('selections_count_highlight', 'selections_count_normal', 1000); 
                }   
            } 
        }   
        
        //***********
        //** Reset **
        //***********
        $('.quickresults_header_buttons #reset_disease').bind('click',function (e) {
            resetDiseaseFilter();
            e.preventDefault();
        });
        
        function resetDiseaseFilter() {
            $('#diseasebreadcrumbs').empty();
            $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
            $('#diseasesSection input[id="disease"]').val('Start typing a search term...');
            updateQuickresultsCount('',true);
            $('#diseasesSection .selectionslist_body').empty();
            $('#pdqDiseases').empty();
            updateSelectionCount();
        }
        
        //***************************************
        //** PDQ Tree in separate pop-up dialog **
        //***************************************
        var pdqDialog = $('<div id="pdq_tree_dialog"></div>')
                .html( generatePDQTreeHtml() )
                .dialog({
                    autoOpen: false,
                    modal: false, 
                    title: 'PDQ Tree',
                    position: [30,5],
                    width: 570,
                    height: 300
                });
        $('.ui-dialog .ui-dialog-content').css({'padding': '.4em .3em .3em .3em'});
                
        $('#show_tree_disease').click(function(e) {
            showTree();
            e.preventDefault();
        });
        
        function showTree( id, parentIds ) {
            pdqDialog.dialog('open');
            $('#pdq_tree_dialog').prev().css({'background-color': '#FF8080', 'background-image': 'none', 'border': '1px solid #A06060'});
            $('.pdq-tree-highlight ins:first').each(function(index,value) {
                $(value).unwrap();
            });
            if( typeof(id) != 'undefined' ) {
                var thisNodeParentIds = $(parentIds).toArray().reverse();
                thisNodeParentIds = $.map( $(thisNodeParentIds), function(val,i) {
                    return '_' + $(thisNodeParentIds).toArray().slice(0,i+1).join('_');
                });
                
                var thisNodeId = (thisNodeParentIds.length>0 ? thisNodeParentIds[thisNodeParentIds.length-1] : '') + '_' + id;
                $(thisNodeParentIds).each(function() {
                    $('#pdq_tree').jstree("load_node_json", $('#ptid'+this), null, null, true);
                });

                var nodeIdsCurrentlyOpen = $.map( $('#pdq_tree').find('.jstree-open').toArray(), function(val,i) { 
                    return $(val).attr('id').match(/ptid([\d_]+)/)[1]; 
                });
                jstreeNodeIdsToClose = nodeIdsCurrentlyOpen.slice(0);
                for( var i in thisNodeParentIds ) { // We are going to close all open nodes except the ones that are parents of our to-be-highlighted node
                    if(!thisNodeParentIds.hasOwnProperty(i))
                        continue;
                    var parentId = thisNodeParentIds[i];
                    if( $.inArray(parentId, jstreeNodeIdsToClose) != -1 )
                        jstreeNodeIdsToClose.splice( jstreeNodeIdsToClose.indexOf(parentId), 1 );
                }
                jstreeNodeIdsToOpen = thisNodeParentIds.slice(0);
                for( var i in nodeIdsCurrentlyOpen ) { // We are going to open all parents of our to-be-highlighted node except those already open
                    if(!nodeIdsCurrentlyOpen.hasOwnProperty(i))
                        continue;
                    var nodeId = nodeIdsCurrentlyOpen[i];
                    if( $.inArray(nodeId, jstreeNodeIdsToOpen) != -1 )
                        jstreeNodeIdsToOpen.splice( jstreeNodeIdsToOpen.indexOf(nodeId), 1 );
                }
                
                setJstreeOperationReady(true);
                var interval = setInterval( function(){
                    if(jstreeNodeIdsToClose.length==0) {
                        if(jstreeNodeIdsToOpen.length==0) {
                            $('#pdq_tree').jstree("deselect_all");
                            $('#pdq_tree').jstree("select_node", $('#ptid'+thisNodeId));
                            var name = $.trim($('#ptid'+thisNodeId).children('a').text());
                            $('#ptid'+thisNodeId+' a:first').wrapInner('<span class="pdq-tree-highlight"></span>');
                            adjustPDQTreeDimensions();
                            clearInterval(interval);
                        } else {
                            if( isJstreeOperationReady() ) {
                                setJstreeOperationReady(false);
                                $('#pdq_tree').jstree("open_node", $('#ptid'+jstreeNodeIdsToOpen[0]), false, true); 
                            }
                        }
                    } else {
                        if( isJstreeOperationReady() ) {
                            setJstreeOperationReady(false);
                            $('#pdq_tree').jstree("close_node", $('#ptid'+jstreeNodeIdsToClose[0]), true); 
                        }
                    }
                }, 50 );
            }
            adjustPDQTreeDimensions();
        }
        
        function generatePDQTreeHtml() {
            var pdqTree = new NBTree();
            pdqTree.buildFromFlatData( FiveAmUtil.PDQPkg.pdqData );
            var jsTreeJsonData = pdqTree.generateJstreeJsonData();
            var pdqTree = $('<div id="pdq_tree"></div>');
            $(function () {
                $('#pdq_tree').jstree({ 
                    'plugins' : [ 'json_data', 'themes', 'ui', 'hotkeys' ],
                    'core' : { 'initially_open' : $.map( FiveAmUtil.PDQPkg.tree_initially_open, function(val,i) { return 'ptid'+val }) },
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
                            'bcItems' : [FiveAmUtil.breadcrumbsPkg.createBox( id, dataIds, FiveAmUtil.PDQPkg.pdqData )],
                            'searchTerm' : ''
                        };
                        var bcItemMarkup = $('<div></div>').buildBreadcrumbs( breadcrumbsPkg );
                        var selectedElement = bcItemMarkup.find('.breadcrumbItemBox');
                        selectedElement.attr('id', id);
                        addToSelections(generateSelectionItemBlock(selectedElement));
                        updateSelections(e);
                    } else { // Selection comes from click on breadcrumb image link
                        scrollPDQTreeNodeIntoView(data.rslt.obj);
                    }
                }).bind("after_open.jstree", function (e, data) { 
                    if( data.rslt.obj.context.nodeName.toLowerCase().indexOf('document')==-1 ) {
                        adjustPDQTreeDimensions();
                    } else {
                        jstreeNodeIdsToOpen.splice(0,1);
                        setJstreeOperationReady(true);
                    }
                }).bind("after_close.jstree", function (e, data) { 
                    if( data.rslt.obj.context.nodeName.toLowerCase().indexOf('document')==-1 ) {
                        adjustPDQTreeDimensions();
                    } else {
                        jstreeNodeIdsToClose.splice(0,1);
                        setJstreeOperationReady(true);
                    }
                });
            });
            return pdqTree;
        }
        
        function adjustPDQTreeDimensions() {
            var clientHeight = getRealPDQTreeClientHeight();
            var viewHeight = $('#pdq_tree_dialog').height();
            if( clientHeight < viewHeight )
                $('#pdq_tree').height(viewHeight);
            else
                $('#pdq_tree').height(clientHeight);
        }
        
        function scrollPDQTreeNodeIntoView( node ) {
            var clientHeight = getRealPDQTreeClientHeight();
            var viewHeight = $('#pdq_tree_dialog').height();
            var nodeTop = $(node).offset().top - $('#pdq_tree').offset().top;
            var scrollTop = $('#pdq_tree_dialog').scrollTop();
            if( viewHeight < clientHeight || nodeTop<scrollTop || nodeTop>scrollTop+viewHeight  ) {
                var newScrollTop =  Math.round( nodeTop - viewHeight/2 );
                newScrollTop = newScrollTop>=0 ? newScrollTop : 0; 
                $('#pdq_tree_dialog').scrollTop( newScrollTop );
            }
        }
        
        function getRealPDQTreeClientHeight() {
            var lastNode = $('#pdq_tree li:visible:last');
            var realHeight = 0;
            if($(lastNode).toArray().length > 0)
                realHeight = $(lastNode).offset().top + $(lastNode).height() - $('#pdq_tree').offset().top;
            return realHeight;
        }
    
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


FiveAmUtil = {
    breadcrumbsPkg : {
        clone : function (bcItem) {
            var newBCItem = [];
            if (bcItem == undefined && bcItem.length == undefined )
                return newBCItem;
            for ( var i=0 ; i < bcItem.length ; i++ )
                newBCItem.push(eval(bcItem[i]));                
            return newBCItem;
        },
    
        createBox : function( id, parentIds, pdqData ) {
            var bcItem = [];
            bcItem.push({'id': id, 'name': pdqData[id].name, 'isFeatured': true});
            for( var i=0; i<parentIds.length; i++ ) 
                bcItem.push({'id': parentIds[i], 'name': pdqData[parentIds[i]].name, 'isFeatured': false});
            return bcItem.reverse();
        },

        createPackage : function( searchTerm, searchResultsPDQ, pdqData ) {
            var bcItems = [];
            var populateBreadcrumbItem = function( bcItem, pdqData, pdqItemId, isFeatured, bcItems ) {
                var pdqItem = pdqData[ pdqItemId ];
                bcItem.push({'id': pdqItemId, 'name':pdqItem.name, 'isFeatured':isFeatured});
                if ( pdqItem.parentId != null && !(pdqItem.parentId.length==1 && pdqItem.parentId[0]==null) ) {
                    for (var i=0 ; i < pdqItem.parentId.length ; i++ ) {
                        populateBreadcrumbItem(FiveAmUtil.breadcrumbsPkg.clone(bcItem), pdqData, pdqItem.parentId[i], false, bcItems );
                    }
                } else {
                    bcItems.push(bcItem.reverse());
                }
            }; 
            for( var i=0; i<searchResultsPDQ.length; i++ ) {
                var bcItem = [];
                var pdqItemId = searchResultsPDQ[i];
                populateBreadcrumbItem( bcItem, pdqData, pdqItemId, true, bcItems );
            }
            bcItems.sort( function(o1,o2) {
                if( o1.length < o2.length ) 
                    return -1;
                else if( o1.length > o2.length )
                    return 1;
                else if( o1.name == searchTerm )
                    return -1;
                else if( o2.name == searchTerm )
                    return 1;
                else
                    return 0;
            });
            return {
                'bcItems' : bcItems,
                'searchTerm' : searchTerm
            };
        },
    },  
    
    PDQPkg: {
        pdqDataDictionary : [],
        
        initPdqData : function() {
            this.pdqData = {};
            for( i in diseaseTree ) {
                if(!diseaseTree.hasOwnProperty(i))
                    continue;
                var pdqItem = diseaseTree[i];
                if( !this.pdqData.hasOwnProperty( pdqItem.id ))
                    this.pdqData[pdqItem.id] = {'name':pdqItem.name.toLowerCase(), 'parentId':[pdqItem.parentId] };
                else
                    this.pdqData[pdqItem.id].parentId.push(pdqItem.parentId);
            }
        },
        
        initPdqDictionary : function() {
            var hmDictionary = {};
            for( var i in this.pdqData ) {
                if(!this.pdqData.hasOwnProperty(i))
                    continue;
                var pdqItem = this.pdqData[i];
                var words = pdqItem.name.split(' ');
                for( j in words )
                    if(words.hasOwnProperty(j))
                        hmDictionary[words[j]]=1;
            }
            for( word in hmDictionary )
                if(hmDictionary.hasOwnProperty(word))
                    this.pdqDataDictionary.push(word);
        },

        init : function() {
            this.initPdqData();
            this.initPdqDictionary();
        },  

        searchPDQ : function(term) {
            var ids=[];
            for( var i in this.pdqData ) {
                if(!this.pdqData.hasOwnProperty(i))
                    continue;
                var pdqItem = this.pdqData[i];
                if(pdqItem.name.indexOf(term)!=-1)
                    ids.push(i);                    
            }
            return ids;
        },

        // When PDQ data is presented as a tree which nodes will be opened on load  
        tree_initially_open: [ '_101', '_101_1873']     
    }
};

FiveAmUtil.PDQPkg.init();

