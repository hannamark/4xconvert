/**
 * Disease Filter of the Ad-Hoc Report
 * @input @global diseaseTree PDQ disease tree to run the name lookup against [{'id':'', 'name':'', 'parentId':''}, ...]
 */

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
	
		//******************************
		//** Disease Breadcrumbs Demo **
		//******************************
		//$('#diseasebreadcrumbs').buildBreadcrumbs( FiveAmUtil.breadcrumbsPkg.createDemoPackage() );
		        
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
			var searchTerm = $('#diseasesSection .diseaserescol input[type="text"]').val();
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
				if (count == 1)	{
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
		//** PDQ Tree in sepatate popup dialog **
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
/*				
				$(openNodeIds).each(function() {
					$('#pdq_tree').jstree("close_node", $('#ptid'+this)); 
				});
				$(treeParentIds).each(function() {
					$('#pdq_tree').jstree("open_node", $('#ptid'+this), false, true); 
				});
*/
				
				var thisId = (treeParentIds.length>0 ? treeParentIds[treeParentIds.length-1] : '') + '_' + id;
				$('#pdq_tree').jstree("deselect_all");
				$('#pdq_tree').jstree("select_node", $('#ptid'+thisId));
				var name = $.trim($('#ptid'+thisId).children('a').text());
				$('#ptid'+thisId).html( $('#ptid'+thisId).html().replace(name,'<span class="pdq-tree-highlight">'+name+'</span>') );
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
						//data.inst._fix_scroll(data.rslt.obj);
						scrollPDQTreeNodeIntoView(data.rslt.obj);
					}
				}).bind("open_node.jstree close_node.jstree", function (e, data) { 
					// 
				}).bind("after_open.jstree after_close.jstree", function (e, data) { 
					adjustPDQTreeDimensions();
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
					this.pdqData[pdqItem.id] = {'name':pdqItem.name, 'parentId':[pdqItem.parentId] };
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
		tree_initially_open: [ '_101', '_101_1873'],
		
		'pdqData' : {
 101:   { 'name':'Disease/diagnosis',             'parentId':null}, 
 103:   { 'name':'Cancer stage',             'parentId':[1873]},
 105:   { 'name':'Genetic condition',             'parentId':[101]},
 106:   { 'name':'Secondary related condition',             'parentId':[101]},
 107:   { 'name':'adjustment disorder',             'parentId':[1236]},
 108:   { 'name':'gastrointestinal complications',             'parentId':[1215]},
 109:   { 'name':'radiation fibrosis',             'parentId':[1251]},
 110:   { 'name':'chronic neutrophilic leukemia',             'parentId':[1893]},
 111:   { 'name':'chronic eosinophilic leukemia',             'parentId':[1893]},
 114:   { 'name':'Fanconi anemia',             'parentId':[105]},
 115:   { 'name':'Diamond-Blackfan anemia',             'parentId':[105]},
 116:   { 'name':'dyskeratosis congenita',             'parentId':[105]},
 117:   { 'name':'Shwachman-Diamond syndrome',             'parentId':[105]},
 118:   { 'name':'congenital amegakaryocytic thrombocytopenia',             'parentId':[105]},
 119:   { 'name':'thrombocytopenia-absent radius syndrome',             'parentId':[105]},
 120:   { 'name':'severe congenital neutropenia',             'parentId':[105]},
 121:   { 'name':'Pearson marrow-pancreas syndrome',             'parentId':[105]},
 122:   { 'name':'Revesz syndrome',             'parentId':[105]},
 123:   { 'name':'WT limb-blood syndrome',             'parentId':[105]},
 124:   { 'name':'IVIC syndrome',             'parentId':[105]},
 125:   { 'name':'radioulnar synostosis',             'parentId':[105]},
 126:   { 'name':'myelocerebellar disorder',             'parentId':[105]},
 127:   { 'name':'neoplasm of uncertain malignant potential',             'parentId':[2116]},
 128:   { 'name':'childhood chronic myelogenous leukemia',             'parentId':[997]},
 129:   { 'name':'spiritual concerns',             'parentId':[1215]},
 130:   { 'name':'type AB thymoma',             'parentId':[1968]},
 132:   { 'name':'type B2 thymoma',             'parentId':[1876]},
 133:   { 'name':'thymic carcinoma',             'parentId':[1968]},
 135:   { 'name':'myelodysplastic/myeloproliferative neoplasms',             'parentId':[511]},
 136:   { 'name':'atypical chronic myeloid leukemia, BCR-ABL1 negative',             'parentId':[1887, 135]},
 137:   { 'name':'myelodysplastic/myeloproliferative neoplasm, unclassifiable',             'parentId':[135]},
 138:   { 'name':'stage IIIC breast cancer',             'parentId':[454]},
 139:   { 'name':'indolent, stage II adult non-Hodgkin lymphoma',             'parentId':[1386]},
 140:   { 'name':'marginal zone lymphoma',             'parentId':[2003]},
 141:   { 'name':'stage I marginal zone lymphoma',             'parentId':[140, 1347]},
 142:   { 'name':'stage II marginal zone lymphoma',             'parentId':[140]},
 143:   { 'name':'contiguous stage II marginal zone lymphoma',             'parentId':[1351, 142]},
 144:   { 'name':'contiguous stage II small lymphocytic lymphoma',             'parentId':[1351, 909]},
 145:   { 'name':'noncontiguous stage II small lymphocytic lymphoma',             'parentId':[1364, 909]},
 146:   { 'name':'stage III marginal zone lymphoma',             'parentId':[1376, 140]},
 147:   { 'name':'stage IV marginal zone lymphoma',             'parentId':[140, 1379]},
 148:   { 'name':'noncontiguous stage II marginal zone lymphoma',             'parentId':[142, 1364]},
 149:   { 'name':'recurrent marginal zone lymphoma',             'parentId':[140, 1382]},
 150:   { 'name':'splenic marginal zone lymphoma',             'parentId':[140, 2005, 1386]},
 151:   { 'name':'nodal marginal zone B-cell lymphoma',             'parentId':[140, 1386, 2005]},
 152:   { 'name':'extranodal marginal zone B-cell lymphoma of mucosa-associated lymphoid tissue',             'parentId':[2005, 1386, 140]},
 153:   { 'name':'aggressive, stage II adult non-Hodgkin lymphoma',             'parentId':[1387]},
 154:   { 'name':'adult embryonal tumor',             'parentId':[2017]},
 155:   { 'name':'adult supratentorial primitive neuroectodermal tumor (PNET)',             'parentId':[154]},
 156:   { 'name':'adult giant cell glioblastoma',             'parentId':[396]},
 157:   { 'name':'adult gliosarcoma',             'parentId':[396]},
 159:   { 'name':'meningeal melanocytoma',             'parentId':[1878]},
 160:   { 'name':'adult grade I meningioma',             'parentId':[395]},
 161:   { 'name':'adult grade II meningioma',             'parentId':[395]},
 162:   { 'name':'adult grade III meningioma',             'parentId':[395]},
 163:   { 'name':'stage II colon cancer',             'parentId':[998]},
 164:   { 'name':'stage II cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[2055]},
 165:   { 'name':'recurrent Wilms tumor and other childhood kidney tumors',             'parentId':[2067]},
 166:   { 'name':'intraocular retinoblastoma',             'parentId':[2047]},
 167:   { 'name':'chronic lymphocytic leukemia',             'parentId':[1887]},
 169:   { 'name':'adult acute myeloid leukemia with t(8;21)(q22;q22)',             'parentId':[1879]},
 170:   { 'name':'adult acute myeloid leukemia with t(16;16)(p13;q22)',             'parentId':[1879]},
 171:   { 'name':'stage 0 chronic lymphocytic leukemia',             'parentId':[2051]},
 172:   { 'name':'adult acute myeloid leukemia with inv(16)(p13;q22)',             'parentId':[1879]},
 173:   { 'name':'adult acute myeloid leukemia with 11q23 (MLL) abnormalities',             'parentId':[1879]},
 174:   { 'name':'adult acute myeloid leukemia with t(15;17)(q22;q12)',             'parentId':[1879]},
 175:   { 'name':'extraocular retinoblastoma',             'parentId':[2047]},
 176:   { 'name':'recurrent retinoblastoma',             'parentId':[2047]},
 177:   { 'name':'adult erythroleukemia (M6a)',             'parentId':[601]},
 178:   { 'name':'adult pure erythroid leukemia (M6b)',             'parentId':[601]},
 179:   { 'name':'adult lymphoblastic lymphoma',             'parentId':[2003]},
 180:   { 'name':'stage III cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[2055]},
 181:   { 'name':'childhood grade III lymphomatoid granulomatosis',             'parentId':[2044]},
 182:   { 'name':'Waldenstrom macroglobulinemia',             'parentId':[2005, 2003, 1386]},
 183:   { 'name':'recurrent childhood grade III lymphomatoid granulomatosis',             'parentId':[181, 1634]},
 184:   { 'name':'stage I multiple myeloma',             'parentId':[1406]},
 185:   { 'name':'stage II multiple myeloma',             'parentId':[1406]},
 186:   { 'name':'stage III multiple myeloma',             'parentId':[1406]},
 187:   { 'name':'stage IV cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[2055]},
 188:   { 'name':'sexuality and reproductive issues',             'parentId':[1215]},
 189:   { 'name':'limited stage small cell lung cancer',             'parentId':[2052]},
 190:   { 'name':'extensive stage small cell lung cancer',             'parentId':[2052]},
 191:   { 'name':'recurrent small cell lung cancer',             'parentId':[2052]},
 192:   { 'name':'adult acute lymphoblastic leukemia',             'parentId':[1441]},
 193:   { 'name':'adult acute myeloid leukemia',             'parentId':[1442]},
 194:   { 'name':'thromboembolism',             'parentId':[1215]},
 195:   { 'name':'stage I and II childhood lymphoblastic lymphoma',             'parentId':[1898]},
 196:   { 'name':'stage I and II childhood large cell lymphoma',             'parentId':[2109]},
 197:   { 'name':'stage III and IV childhood large cell lymphoma',             'parentId':[2109]},
 198:   { 'name':'stage I and II childhood small noncleaved cell lymphoma',             'parentId':[1885]},
 199:   { 'name':'cervical cancer',             'parentId':[439, 889]},
 200:   { 'name':'chronic myelogenous leukemia',             'parentId':[492, 1887]},
 201:   { 'name':'stage 0 vulvar cancer',             'parentId':[2065]},
 202:   { 'name':'stage I vulvar cancer',             'parentId':[2065]},
 203:   { 'name':'stage II vulvar cancer',             'parentId':[2065]},
 204:   { 'name':'stage III vulvar cancer',             'parentId':[2065]},
 205:   { 'name':'stage IV vulvar cancer',             'parentId':[2065]},
 206:   { 'name':'recurrent vulvar cancer',             'parentId':[2065]},
 207:   { 'name':'vulvar cancer',             'parentId':[439, 889]},
 208:   { 'name':'multiple myeloma and other plasma cell neoplasms',             'parentId':[511]},
 209:   { 'name':'stage 0 cervical cancer',             'parentId':[2039]},
 210:   { 'name':'stage I childhood lymphoblastic lymphoma',             'parentId':[195]},
 211:   { 'name':'stage II childhood lymphoblastic lymphoma',             'parentId':[195]},
 212:   { 'name':'stage III childhood lymphoblastic lymphoma',             'parentId':[299]},
 213:   { 'name':'stage IV childhood lymphoblastic lymphoma',             'parentId':[299]},
 214:   { 'name':'stage I cervical cancer',             'parentId':[2039]},
 215:   { 'name':'recurrent childhood lymphoblastic lymphoma',             'parentId':[1898, 1634]},
 216:   { 'name':'vaginal cancer',             'parentId':[439, 889]},
 217:   { 'name':'stage 0 vaginal cancer',             'parentId':[2063]},
 218:   { 'name':'stage I vaginal cancer',             'parentId':[2063]},
 219:   { 'name':'oral complications of radiation therapy',             'parentId':[769]},
 220:   { 'name':'stage II vaginal cancer',             'parentId':[2063]},
 221:   { 'name':'stage III vaginal cancer',             'parentId':[2063]},
 222:   { 'name':'stage II cervical cancer',             'parentId':[2039]},
 223:   { 'name':'stage IVA vaginal cancer',             'parentId':[2063]},
 224:   { 'name':'stage IVB vaginal cancer',             'parentId':[2063]},
 225:   { 'name':'unusual cancers of childhood',             'parentId':[1513]},
 226:   { 'name':'recurrent vaginal cancer',             'parentId':[2063]},
 227:   { 'name':'gastrointestinal carcinoid tumor',             'parentId':[2166, 889, 444]},
 228:   { 'name':'localized gastrointestinal carcinoid tumor',             'parentId':[2066]},
 229:   { 'name':'regional gastrointestinal carcinoid tumor',             'parentId':[2066]},
 230:   { 'name':'metastatic gastrointestinal carcinoid tumor',             'parentId':[2066]},
 231:   { 'name':'recurrent gastrointestinal carcinoid tumor',             'parentId':[2066]},
 232:   { 'name':'stage III cervical cancer',             'parentId':[2039]},
 233:   { 'name':'renal cell carcinoma',             'parentId':[889, 517]},
 234:   { 'name':'malignant mesothelioma',             'parentId':[516, 889]},
 235:   { 'name':'localized malignant mesothelioma',             'parentId':[2018]},
 236:   { 'name':'clear cell sarcoma of the kidney',             'parentId':[517, 889]},
 237:   { 'name':'rhabdoid tumor of the kidney',             'parentId':[517, 889]},
 238:   { 'name':'advanced malignant mesothelioma',             'parentId':[2018]},
 239:   { 'name':'recurrent malignant mesothelioma',             'parentId':[2018]},
 240:   { 'name':'gastrointestinal stromal tumor',             'parentId':[889, 444]},
 241:   { 'name':'small lymphocytic lymphoma',             'parentId':[2003]},
 242:   { 'name':'stage IV cervical cancer',             'parentId':[2039]},
 243:   { 'name':'childhood central nervous system germ cell tumor',             'parentId':[1458, 2143]},
 244:   { 'name':'childhood acute myeloid leukemia/other myeloid malignancies',             'parentId':[1442, 1513]},
 246:   { 'name':'stage I penile cancer',             'parentId':[2035]},
 247:   { 'name':'mycosis fungoides/Sezary syndrome',             'parentId':[1514]},
 248:   { 'name':'stage I mycosis fungoides/Sezary syndrome',             'parentId':[1883]},
 249:   { 'name':'stage II mycosis fungoides/Sezary syndrome',             'parentId':[1883]},
 250:   { 'name':'stage III mycosis fungoides/Sezary syndrome',             'parentId':[1883]},
 251:   { 'name':'stage IV mycosis fungoides/Sezary syndrome',             'parentId':[1883]},
 252:   { 'name':'recurrent mycosis fungoides/Sezary syndrome',             'parentId':[1883]},
 253:   { 'name':'stage II penile cancer',             'parentId':[2035]},
 255:   { 'name':'stage I neuroendocrine carcinoma of the skin',             'parentId':[1882]},
 256:   { 'name':'stage II neuroendocrine carcinoma of the skin',             'parentId':[1882]},
 257:   { 'name':'stage III penile cancer',             'parentId':[2035]},
 258:   { 'name':'stage III neuroendocrine carcinoma of the skin',             'parentId':[1882]},
 259:   { 'name':'recurrent neuroendocrine carcinoma of the skin',             'parentId':[1882]},
 260:   { 'name':'stage IV penile cancer',             'parentId':[2035]},
 261:   { 'name':'neuroendocrine carcinoma',             'parentId':[1578]},
 262:   { 'name':'recurrent penile cancer',             'parentId':[2035]},
 265:   { 'name':'recurrent cervical cancer',             'parentId':[2039]},
 266:   { 'name':'grade 1 follicular lymphoma',             'parentId':[2003]},
 267:   { 'name':'grade 2 follicular lymphoma',             'parentId':[2003]},
 268:   { 'name':'adult diffuse small cleaved cell lymphoma',             'parentId':[2003]},
 269:   { 'name':'stage III colon cancer',             'parentId':[998]},
 270:   { 'name':'recurrent cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[2055]},
 271:   { 'name':'grade 3 follicular lymphoma',             'parentId':[2003]},
 272:   { 'name':'adult diffuse mixed cell lymphoma',             'parentId':[2003]},
 273:   { 'name':'adult diffuse large cell lymphoma',             'parentId':[2003]},
 274:   { 'name':'adult immunoblastic large cell lymphoma',             'parentId':[2003]},
 275:   { 'name':'adult Burkitt lymphoma',             'parentId':[2003]},
 276:   { 'name':'weight changes',             'parentId':[1215]},
 277:   { 'name':'hormonal changes',             'parentId':[1215]},
 278:   { 'name':'juvenile myelomonocytic leukemia',             'parentId':[135, 1887]},
 279:   { 'name':'insular thyroid cancer',             'parentId':[2059]},
 280:   { 'name':'stage I chronic lymphocytic leukemia',             'parentId':[2051]},
 281:   { 'name':'childhood spinal cord neoplasm',             'parentId':[2142]},
 282:   { 'name':'childhood atypical teratoid/rhabdoid tumor',             'parentId':[443]},
 283:   { 'name':'peripheral primitive neuroectodermal tumor of the kidney',             'parentId':[517, 889]},
 284:   { 'name':'stage IB cervical cancer',             'parentId':[214]},
 285:   { 'name':'stage II chronic lymphocytic leukemia',             'parentId':[2051]},
 286:   { 'name':'stage III chronic lymphocytic leukemia',             'parentId':[2051]},
 287:   { 'name':'stage IV chronic lymphocytic leukemia',             'parentId':[2051]},
 288:   { 'name':'stage IIB cervical cancer',             'parentId':[222]},
 290:   { 'name':'localized resectable adult primary liver cancer',             'parentId':[2020]},
 291:   { 'name':'localized unresectable adult primary liver cancer',             'parentId':[2020]},
 292:   { 'name':'post-transplant lymphoproliferative disorder',             'parentId':[1706]},
 293:   { 'name':'advanced adult primary liver cancer',             'parentId':[2020]},
 294:   { 'name':'recurrent adult primary liver cancer',             'parentId':[2020]},
 295:   { 'name':'recurrent childhood acute myeloid leukemia',             'parentId':[2041]},
 296:   { 'name':'stage IVB cervical cancer',             'parentId':[242]},
 297:   { 'name':'recurrent osteosarcoma',             'parentId':[2029]},
 298:   { 'name':'recurrent adult acute myeloid leukemia',             'parentId':[2015]},
 299:   { 'name':'stage III and IV childhood lymphoblastic lymphoma',             'parentId':[1898]},
 300:   { 'name':'stage III and IV childhood small noncleaved cell lymphoma',             'parentId':[1885]},
 301:   { 'name':'recurrent adult acute lymphoblastic leukemia',             'parentId':[2014]},
 302:   { 'name':'adult brain tumor',             'parentId':[889, 437]},
 303:   { 'name':'gastrinoma',             'parentId':[2123]},
 304:   { 'name':'recurrent adult brain tumor',             'parentId':[2017]},
 305:   { 'name':'relapsing chronic myelogenous leukemia',             'parentId':[997]},
 306:   { 'name':'refractory chronic lymphocytic leukemia',             'parentId':[2051]},
 307:   { 'name':'gestational trophoblastic tumor',             'parentId':[439, 889]},
 308:   { 'name':'recurrent gestational trophoblastic tumor',             'parentId':[2101]},
 309:   { 'name':'small intestine cancer',             'parentId':[889, 444]},
 310:   { 'name':'endometrial cancer',             'parentId':[1681, 889]},
 311:   { 'name':'stage 0 endometrial carcinoma',             'parentId':[2056]},
 312:   { 'name':'stage I endometrial carcinoma',             'parentId':[2056]},
 313:   { 'name':'stage II endometrial carcinoma',             'parentId':[2056]},
 314:   { 'name':'stage III endometrial carcinoma',             'parentId':[2056]},
 315:   { 'name':'stage IV endometrial carcinoma',             'parentId':[2056]},
 316:   { 'name':'recurrent endometrial carcinoma',             'parentId':[2056]},
 317:   { 'name':'small intestine adenocarcinoma',             'parentId':[2053]},
 318:   { 'name':'small intestine lymphoma',             'parentId':[2053]},
 319:   { 'name':'small intestine leiomyosarcoma',             'parentId':[2053]},
 320:   { 'name':'gallbladder cancer',             'parentId':[889, 444]},
 321:   { 'name':'localized gallbladder cancer',             'parentId':[2064]},
 322:   { 'name':'unresectable gallbladder cancer',             'parentId':[2064]},
 323:   { 'name':'stage IA cervical cancer',             'parentId':[214]},
 324:   { 'name':'recurrent gallbladder cancer',             'parentId':[2064]},
 325:   { 'name':'extrahepatic bile duct cancer',             'parentId':[889, 444]},
 326:   { 'name':'localized extrahepatic bile duct cancer',             'parentId':[2061]},
 327:   { 'name':'unresectable extrahepatic bile duct cancer',             'parentId':[2061]},
 328:   { 'name':'recurrent extrahepatic bile duct cancer',             'parentId':[2061]},
 329:   { 'name':'adult primary liver cancer',             'parentId':[445, 889]},
 330:   { 'name':'recurrent small intestine cancer',             'parentId':[2053]},
 331:   { 'name':'adrenocortical carcinoma',             'parentId':[1888, 889]},
 332:   { 'name':'stage I adrenocortical carcinoma',             'parentId':[2013]},
 333:   { 'name':'stage IV colon cancer',             'parentId':[998]},
 334:   { 'name':'stage IIA cervical cancer',             'parentId':[222]},
 335:   { 'name':'stage II adrenocortical carcinoma',             'parentId':[2013]},
 336:   { 'name':'stage III adrenocortical carcinoma',             'parentId':[2013]},
 337:   { 'name':'stage IV adrenocortical carcinoma',             'parentId':[2013]},
 338:   { 'name':'recurrent adrenocortical carcinoma',             'parentId':[2013]},
 339:   { 'name':'bladder cancer',             'parentId':[889, 509]},
 340:   { 'name':'stage 0 bladder cancer',             'parentId':[2028]},
 341:   { 'name':'stage IVA cervical cancer',             'parentId':[242]},
 342:   { 'name':'stage I bladder cancer',             'parentId':[2028]},
 343:   { 'name':'stage II bladder cancer',             'parentId':[2028]},
 344:   { 'name':'stage III bladder cancer',             'parentId':[2028]},
 345:   { 'name':'recurrent bladder cancer',             'parentId':[2028]},
 346:   { 'name':'stage IV bladder cancer',             'parentId':[2028]},
 347:   { 'name':'stage I malignant testicular germ cell tumor',             'parentId':[2057]},
 348:   { 'name':'stage II malignant testicular germ cell tumor',             'parentId':[2057]},
 349:   { 'name':'stage III malignant testicular germ cell tumor',             'parentId':[2057]},
 350:   { 'name':'skin cancer',             'parentId':[1889, 889]},
 351:   { 'name':'prostate cancer',             'parentId':[1897, 889]},
 352:   { 'name':'stage I prostate cancer',             'parentId':[2070]},
 353:   { 'name':'stage II prostate cancer',             'parentId':[2070]},
 354:   { 'name':'stage III prostate cancer',             'parentId':[2070]},
 355:   { 'name':'stage IV prostate cancer',             'parentId':[2070]},
 356:   { 'name':'recurrent prostate cancer',             'parentId':[2070]},
 357:   { 'name':'recurrent skin cancer',             'parentId':[2050]},
 358:   { 'name':'childhood diffuse large cell lymphoma',             'parentId':[2109]},
 359:   { 'name':'recurrent malignant testicular germ cell tumor',             'parentId':[2057]},
 360:   { 'name':'insulinoma',             'parentId':[2123]},
 361:   { 'name':'recurrent islet cell carcinoma',             'parentId':[2012]},
 362:   { 'name':'childhood immunoblastic large cell lymphoma',             'parentId':[2109]},
 363:   { 'name':'thymoma and thymic carcinoma',             'parentId':[1899]},
 364:   { 'name':'invasive thymoma and thymic carcinoma',             'parentId':[2019]},
 365:   { 'name':'noninvasive thymoma and thymic carcinoma',             'parentId':[2019]},
 366:   { 'name':'recurrent thymoma and thymic carcinoma',             'parentId':[2019]},
 367:   { 'name':'thyroid cancer',             'parentId':[889, 1888, 440]},
 368:   { 'name':'stage I papillary thyroid cancer',             'parentId':[819]},
 369:   { 'name':'stage II papillary thyroid cancer',             'parentId':[819]},
 370:   { 'name':'stage IV papillary thyroid cancer',             'parentId':[819]},
 371:   { 'name':'stage I follicular thyroid cancer',             'parentId':[820]},
 372:   { 'name':'stage II follicular thyroid cancer',             'parentId':[820]},
 373:   { 'name':'stage IV follicular thyroid cancer',             'parentId':[820]},
 374:   { 'name':'thyroid gland medullary carcinoma',             'parentId':[2059, 261]},
 375:   { 'name':'anaplastic thyroid cancer',             'parentId':[2059]},
 376:   { 'name':'recurrent thyroid cancer',             'parentId':[2059]},
 377:   { 'name':'Kaposi sarcoma',             'parentId':[889, 1889]},
 378:   { 'name':'pituitary tumor',             'parentId':[443, 889, 1888]},
 379:   { 'name':'ACTH-producing pituitary tumor',             'parentId':[2037]},
 380:   { 'name':'prolactin-producing pituitary tumor',             'parentId':[2037]},
 381:   { 'name':'growth hormone-producing pituitary tumor',             'parentId':[2037]},
 382:   { 'name':'recurrent pituitary tumor',             'parentId':[2037]},
 383:   { 'name':'intraocular melanoma',             'parentId':[889, 1891]},
 384:   { 'name':'iris melanoma',             'parentId':[2011]},
 385:   { 'name':'ciliary body and choroid melanoma, small size',             'parentId':[2011]},
 386:   { 'name':'ciliary body and choroid melanoma, medium/large size',             'parentId':[2011]},
 387:   { 'name':'extraocular extension melanoma',             'parentId':[2011]},
 388:   { 'name':'recurrent intraocular melanoma',             'parentId':[2011]},
 389:   { 'name':'TSH producing pituitary tumor',             'parentId':[2037]},
 390:   { 'name':'nonfunctioning pituitary tumor',             'parentId':[2037]},
 391:   { 'name':'adult brain stem glioma',             'parentId':[2017]},
 392:   { 'name':'adult ependymoma',             'parentId':[2112]},
 393:   { 'name':'adult craniopharyngioma',             'parentId':[2017]},
 394:   { 'name':'adult medulloblastoma',             'parentId':[154]},
 395:   { 'name':'adult meningioma',             'parentId':[2145, 1878]},
 396:   { 'name':'adult glioblastoma',             'parentId':[1410]},
 398:   { 'name':'breast cancer',             'parentId':[889, 2116]},
 399:   { 'name':'melanoma',             'parentId':[1889, 889]},
 400:   { 'name':'stage I melanoma',             'parentId':[2021]},
 401:   { 'name':'stage II melanoma',             'parentId':[2021]},
 402:   { 'name':'stage III melanoma',             'parentId':[2021]},
 403:   { 'name':'stage IV melanoma',             'parentId':[2021]},
 404:   { 'name':'recurrent melanoma',             'parentId':[2021]},
 406:   { 'name':'stage I breast cancer',             'parentId':[2033]},
 407:   { 'name':'nasopharyngeal cancer',             'parentId':[889, 440]},
 408:   { 'name':'stage I nasopharyngeal cancer',             'parentId':[2024]},
 409:   { 'name':'stage II nasopharyngeal cancer',             'parentId':[2024]},
 410:   { 'name':'stage III nasopharyngeal cancer',             'parentId':[2024]},
 411:   { 'name':'stage IV nasopharyngeal cancer',             'parentId':[2024]},
 412:   { 'name':'recurrent nasopharyngeal cancer',             'parentId':[2024]},
 413:   { 'name':'stage 0 paranasal sinus and nasal cavity cancer',             'parentId':[2079]},
 414:   { 'name':'stage 0 nasopharyngeal cancer',             'parentId':[2024]},
 415:   { 'name':'stage 0 oropharyngeal cancer',             'parentId':[2074]},
 416:   { 'name':'stage 0 laryngeal cancer',             'parentId':[1895]},
 417:   { 'name':'stage 0 hypopharyngeal cancer',             'parentId':[2076]},
 418:   { 'name':'adult extraskeletal chondrosarcoma',             'parentId':[1976]},
 419:   { 'name':'stage IIIA non-small cell lung cancer',             'parentId':[1240]},
 420:   { 'name':'stage IIIB non-small cell lung cancer',             'parentId':[1240]},
 421:   { 'name':'adult extraskeletal osteosarcoma',             'parentId':[1976]},
 422:   { 'name':'stage 0 non-small cell lung cancer',             'parentId':[2027]},
 423:   { 'name':'stage IV non-small cell lung cancer',             'parentId':[2027]},
 424:   { 'name':'metastatic squamous neck cancer with occult primary',             'parentId':[440, 889]},
 425:   { 'name':'salivary gland cancer',             'parentId':[440, 889]},
 426:   { 'name':'stage I salivary gland cancer',             'parentId':[2049]},
 427:   { 'name':'stage II salivary gland cancer',             'parentId':[2049]},
 428:   { 'name':'stage III salivary gland cancer',             'parentId':[2049]},
 429:   { 'name':'stage IV salivary gland cancer',             'parentId':[2049]},
 430:   { 'name':'recurrent salivary gland cancer',             'parentId':[2049]},
 431:   { 'name':'acute leukemia',             'parentId':[1196]},
 432:   { 'name':'adult angiosarcoma',             'parentId':[1976]},
 433:   { 'name':'stage II breast cancer',             'parentId':[2033]},
 434:   { 'name':'hypopharyngeal cancer',             'parentId':[440, 889]},
 436:   { 'name':'non-Hodgkin lymphoma',             'parentId':[1241]},
 437:   { 'name':'brain tumor',             'parentId':[443]},
 439:   { 'name':'female reproductive cancer',             'parentId':[2116]},
 440:   { 'name':'head and neck cancer',             'parentId':[2116]},
 441:   { 'name':'laryngeal cancer',             'parentId':[889, 440]},
 442:   { 'name':'oropharyngeal cancer',             'parentId':[889, 440]},
 443:   { 'name':'central nervous system neoplasm',             'parentId':[2144]},
 444:   { 'name':'gastrointestinal cancer',             'parentId':[2116]},
 445:   { 'name':'liver and intrahepatic biliary tract cancer',             'parentId':[444]},
 447:   { 'name':'unspecified childhood solid tumor, protocol specific',             'parentId':[1890, 1513]},
 448:   { 'name':'unspecified adult solid tumor, protocol specific',             'parentId':[889]},
 449:   { 'name':'childhood germ cell tumor',             'parentId':[1585, 1890, 1513]},
 450:   { 'name':'WDHA syndrome',             'parentId':[2117]},
 451:   { 'name':'somatostatinoma',             'parentId':[2123]},
 452:   { 'name':'pancreatic polypeptide tumor',             'parentId':[2123]},
 453:   { 'name':'glucagonoma',             'parentId':[2123]},
 454:   { 'name':'stage III breast cancer',             'parentId':[2033]},
 455:   { 'name':'urethral cancer',             'parentId':[509, 889]},
 456:   { 'name':'adult grade III lymphomatoid granulomatosis',             'parentId':[1392]},
 458:   { 'name':'childhood Langerhans cell histiocytosis',             'parentId':[2161, 1513]},
 459:   { 'name':'recurrent adult grade III lymphomatoid granulomatosis',             'parentId':[456]},
 460:   { 'name':'adult oligodendroglioma',             'parentId':[2113]},
 461:   { 'name':'hairy cell leukemia',             'parentId':[1196]},
 462:   { 'name':'chronic phase chronic myelogenous leukemia',             'parentId':[997]},
 463:   { 'name':'accelerated phase chronic myelogenous leukemia',             'parentId':[997]},
 464:   { 'name':'blastic phase chronic myelogenous leukemia',             'parentId':[997]},
 465:   { 'name':'meningeal chronic myelogenous leukemia',             'parentId':[997]},
 466:   { 'name':'classic Kaposi sarcoma',             'parentId':[955]},
 467:   { 'name':'immunosuppressive treatment related Kaposi sarcoma',             'parentId':[955]},
 468:   { 'name':'AIDS-related Kaposi sarcoma',             'parentId':[1191, 955]},
 469:   { 'name':'recurrent Kaposi sarcoma',             'parentId':[955]},
 470:   { 'name':'untreated adult acute lymphoblastic leukemia',             'parentId':[2014]},
 471:   { 'name':'untreated adult acute myeloid leukemia',             'parentId':[2015]},
 472:   { 'name':'untreated childhood acute myeloid leukemia and other myeloid malignancies',             'parentId':[2041]},
 473:   { 'name':'untreated childhood acute lymphoblastic leukemia',             'parentId':[2040]},
 474:   { 'name':'adult acute myeloid leukemia in remission',             'parentId':[2015]},
 475:   { 'name':'adult acute lymphoblastic leukemia in remission',             'parentId':[2014]},
 476:   { 'name':'childhood acute myeloid leukemia in remission',             'parentId':[2041]},
 477:   { 'name':'childhood acute lymphoblastic leukemia in remission',             'parentId':[2040]},
 478:   { 'name':'untreated metastatic squamous neck cancer with occult primary',             'parentId':[2022]},
 479:   { 'name':'recurrent metastatic squamous neck cancer with occult primary',             'parentId':[2022]},
 480:   { 'name':'stage IV breast cancer',             'parentId':[2033]},
 481:   { 'name':'basal cell carcinoma of the skin',             'parentId':[2050]},
 482:   { 'name':'stage IIIA breast cancer',             'parentId':[454]},
 485:   { 'name':'medullary ductal breast carcinoma with lymphocytic infiltrate',             'parentId':[1901]},
 486:   { 'name':'Burkitt lymphoma',             'parentId':[1885]},
 487:   { 'name':'thorax/respiratory cancer',             'parentId':[2116]},
 488:   { 'name':'duct cell adenocarcinoma of the pancreas',             'parentId':[1276]},
 489:   { 'name':'stage III follicular thyroid cancer',             'parentId':[820]},
 490:   { 'name':'stage III papillary thyroid cancer',             'parentId':[819]},
 491:   { 'name':'adult acute monoblastic leukemia and acute monocytic leukemia (M5)',             'parentId':[1970]},
 492:   { 'name':'chronic myeloproliferative disorders',             'parentId':[511]},
 494:   { 'name':'polycythemia vera',             'parentId':[1893]},
 495:   { 'name':'primary myelofibrosis',             'parentId':[1893]},
 496:   { 'name':'essential thrombocythemia',             'parentId':[1893]},
 497:   { 'name':'stage 0 lip and oral cavity cancer',             'parentId':[2072]},
 498:   { 'name':'Ewing sarcoma/peripheral primitive neuroectodermal tumor (PNET)',             'parentId':[1890, 518, 514, 1513]},
 499:   { 'name':'chondrosarcoma',             'parentId':[518]},
 500:   { 'name':'anal cancer',             'parentId':[889, 444]},
 501:   { 'name':'colorectal cancer',             'parentId':[444]},
 502:   { 'name':'ovarian undifferentiated adenocarcinoma',             'parentId':[1989]},
 503:   { 'name':'ovarian mixed epithelial carcinoma',             'parentId':[1989]},
 504:   { 'name':'breast cancer in situ',             'parentId':[2033]},
 506:   { 'name':'fever, sweats, and hot flashes',             'parentId':[1215]},
 507:   { 'name':'recurrent breast cancer',             'parentId':[2033]},
 509:   { 'name':'kidney/urinary cancer',             'parentId':[2116]},
 511:   { 'name':'hematopoietic/lymphoid cancer',             'parentId':[2116]},
 514:   { 'name':'muscle cancer',             'parentId':[1892]},
 516:   { 'name':'pleura cancer',             'parentId':[487]},
 517:   { 'name':'kidney tumor',             'parentId':[509]},
 518:   { 'name':'bone cancer',             'parentId':[1892]},
 520:   { 'name':'myelodysplastic syndromes',             'parentId':[511]},
 521:   { 'name':'gastric cancer',             'parentId':[889, 444]},
 522:   { 'name':'T-cell large granular lymphocyte leukemia',             'parentId':[1887]},
 523:   { 'name':'B-cell chronic lymphocytic leukemia',             'parentId':[1996]},
 524:   { 'name':'adult lymphocyte predominant Hodgkin lymphoma',             'parentId':[2001]},
 525:   { 'name':'adult lymphocyte depletion Hodgkin lymphoma',             'parentId':[2001]},
 526:   { 'name':'adult nodular sclerosis Hodgkin lymphoma',             'parentId':[2001]},
 527:   { 'name':'adult mixed cellularity Hodgkin lymphoma',             'parentId':[2001]},
 528:   { 'name':'chronic myelogenous leukemia, BCR-ABL1 positive',             'parentId':[809]},
 529:   { 'name':'adult alveolar soft-part sarcoma',             'parentId':[1976]},
 530:   { 'name':'adult epithelioid sarcoma',             'parentId':[1976]},
 531:   { 'name':'adult malignant fibrous histiocytoma',             'parentId':[1976]},
 532:   { 'name':'adult malignant hemangiopericytoma',             'parentId':[1976]},
 533:   { 'name':'adult malignant mesenchymoma',             'parentId':[1976]},
 534:   { 'name':'Philadelphia chromosome negative chronic myelogenous leukemia',             'parentId':[809]},
 535:   { 'name':'adult rhabdomyosarcoma',             'parentId':[1976]},
 536:   { 'name':'adenocarcinoma of the lung',             'parentId':[1981]},
 538:   { 'name':'ductal breast carcinoma in situ',             'parentId':[504, 1901]},
 539:   { 'name':'mucinous ductal breast carcinoma',             'parentId':[1901]},
 540:   { 'name':'invasive ductal breast carcinoma with predominant intraductal component',             'parentId':[1901]},
 541:   { 'name':'adenosquamous cell lung cancer',             'parentId':[1981]},
 542:   { 'name':'invasive ductal breast carcinoma',             'parentId':[1901]},
 543:   { 'name':'comedo ductal breast carcinoma',             'parentId':[1901]},
 544:   { 'name':'papillary ductal breast carcinoma',             'parentId':[1901]},
 545:   { 'name':'tubular ductal breast carcinoma',             'parentId':[1901]},
 547:   { 'name':'lobular breast carcinoma in situ',             'parentId':[504, 1902]},
 548:   { 'name':'invasive lobular breast carcinoma with predominant in situ component',             'parentId':[1902]},
 549:   { 'name':'invasive lobular breast carcinoma',             'parentId':[1902]},
 552:   { 'name':'Paget disease of the breast with invasive ductal carcinoma',             'parentId':[1903]},
 560:   { 'name':'L1 childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 561:   { 'name':'L2 childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 562:   { 'name':'L3 childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 563:   { 'name':'T-cell childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 564:   { 'name':'B-cell childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 566:   { 'name':'non-T, non-B, cALLa positive childhood acute lymphoblastic leukemia',             'parentId':[1912]},
 567:   { 'name':'non-T, non-B, cALLa negative childhood acute lymphoblastic leukemia',             'parentId':[1912]},
 568:   { 'name':'non-T, non-B, cALLa positive, pre-B childhood acute lymphoblastic leukemia',             'parentId':[1912]},
 569:   { 'name':'L1 adult acute lymphoblastic leukemia',             'parentId':[1966]},
 570:   { 'name':'L2 adult acute lymphoblastic leukemia',             'parentId':[1966]},
 571:   { 'name':'L3 adult acute lymphoblastic leukemia',             'parentId':[1966]},
 572:   { 'name':'T-cell adult acute lymphoblastic leukemia',             'parentId':[1966]},
 573:   { 'name':'B-cell adult acute lymphoblastic leukemia',             'parentId':[1966]},
 578:   { 'name':'TdT positive adult acute lymphoblastic leukemia',             'parentId':[1966]},
 580:   { 'name':'TdT negative childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 581:   { 'name':'TdT positive childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 588:   { 'name':'childhood hepatocellular carcinoma',             'parentId':[1994]},
 589:   { 'name':'adult primary hepatocellular carcinoma',             'parentId':[1974]},
 594:   { 'name':'embryonal childhood rhabdomyosarcoma',             'parentId':[1995]},
 595:   { 'name':'alveolar childhood rhabdomyosarcoma',             'parentId':[1995]},
 596:   { 'name':'pleomorphic childhood rhabdomyosarcoma',             'parentId':[1995]},
 597:   { 'name':'mixed childhood rhabdomyosarcoma',             'parentId':[1995]},
 598:   { 'name':'embryonal-botryoid childhood rhabdomyosarcoma',             'parentId':[1995]},
 601:   { 'name':'adult acute erythroid leukemia (M6)',             'parentId':[1970]},
 602:   { 'name':'acral lentiginous malignant melanoma',             'parentId':[1979]},
 604:   { 'name':'adult acute myeloblastic leukemia without maturation (M1)',             'parentId':[1970]},
 605:   { 'name':'adult acute myeloblastic leukemia with maturation (M2)',             'parentId':[1970]},
 606:   { 'name':'adult acute promyelocytic leukemia (M3)',             'parentId':[1970]},
 607:   { 'name':'squamous cell carcinoma of the esophagus',             'parentId':[1969]},
 608:   { 'name':'adult acute myelomonocytic leukemia (M4)',             'parentId':[1970]},
 609:   { 'name':'adenocarcinoma of the esophagus',             'parentId':[1969]},
 610:   { 'name':'adult acute monoblastic leukemia (M5a)',             'parentId':[491]},
 611:   { 'name':'adult acute eosinophilic leukemia',             'parentId':[1970]},
 612:   { 'name':'adult acute basophilic leukemia',             'parentId':[1970]},
 613:   { 'name':'adult acute megakaryoblastic leukemia (M7)',             'parentId':[1970]},
 614:   { 'name':'intestinal adenocarcinoma of the stomach',             'parentId':[1510]},
 615:   { 'name':'childhood acute lymphoblastic leukemia',             'parentId':[1513, 1441]},
 616:   { 'name':'childhood acute myeloblastic leukemia without maturation (M1)',             'parentId':[1993]},
 617:   { 'name':'adenocarcinoma of the colon',             'parentId':[794]},
 618:   { 'name':'diffuse adenocarcinoma of the stomach',             'parentId':[1510]},
 619:   { 'name':'childhood acute myeloblastic leukemia with maturation (M2)',             'parentId':[1993]},
 620:   { 'name':'squamous cell carcinoma of the anus',             'parentId':[1980]},
 621:   { 'name':'mixed adenocarcinoma of the stomach',             'parentId':[1510]},
 622:   { 'name':'mucinous adenocarcinoma of the colon',             'parentId':[617]},
 623:   { 'name':'signet ring adenocarcinoma of the colon',             'parentId':[617]},
 624:   { 'name':'childhood acute promyelocytic leukemia (M3)',             'parentId':[1993]},
 627:   { 'name':'childhood acute myelomonocytic leukemia (M4)',             'parentId':[1993]},
 628:   { 'name':'childhood acute monoblastic leukemia (M5a)',             'parentId':[1894]},
 629:   { 'name':'childhood acute monocytic leukemia (M5b)',             'parentId':[1894]},
 630:   { 'name':'childhood acute erythroleukemia (M6)',             'parentId':[1993]},
 631:   { 'name':'childhood acute eosinophilic leukemia',             'parentId':[1993]},
 632:   { 'name':'childhood acute basophilic leukemia',             'parentId':[1993]},
 633:   { 'name':'childhood acute megakaryocytic leukemia (M7)',             'parentId':[1993]},
 634:   { 'name':'adenocarcinoma of the rectum',             'parentId':[1998]},
 635:   { 'name':'adenocarcinoma of the gallbladder',             'parentId':[1963]},
 636:   { 'name':'mucinous adenocarcinoma of the rectum',             'parentId':[634]},
 638:   { 'name':'signet ring adenocarcinoma of the rectum',             'parentId':[634]},
 643:   { 'name':'adenocarcinoma of the extrahepatic bile duct',             'parentId':[1984]},
 644:   { 'name':'acinar cell adenocarcinoma of the pancreas',             'parentId':[1276]},
 645:   { 'name':'ovarian serous cystadenoma with proliferating activity',             'parentId':[2104]},
 646:   { 'name':'ovarian serous cystadenocarcinoma',             'parentId':[1989]},
 648:   { 'name':'ovarian mucinous cystadenocarcinoma',             'parentId':[1989]},
 649:   { 'name':'ovarian endometrioid adenocarcinoma',             'parentId':[1989]},
 650:   { 'name':'ovarian clear cell cystadenocarcinoma',             'parentId':[1989]},
 654:   { 'name':'endometrial adenocarcinoma',             'parentId':[1971]},
 655:   { 'name':'Paget disease of the vulva',             'parentId':[1978]},
 656:   { 'name':'endometrial adenosquamous cell carcinoma',             'parentId':[1971]},
 657:   { 'name':'cervical squamous cell carcinoma',             'parentId':[1990]},
 658:   { 'name':'cervical adenocarcinoma',             'parentId':[1990]},
 659:   { 'name':'cervical adenosquamous cell carcinoma',             'parentId':[1990]},
 660:   { 'name':'cervical small cell carcinoma',             'parentId':[1990]},
 661:   { 'name':'ovarian endometrioid tumor with proliferating activity',             'parentId':[2104]},
 662:   { 'name':'ovarian clear cell tumor with proliferating activity',             'parentId':[2104]},
 665:   { 'name':'epithelial mesothelioma',             'parentId':[1965]},
 666:   { 'name':'sarcomatous mesothelioma',             'parentId':[1965]},
 667:   { 'name':'transitional cell carcinoma of the bladder',             'parentId':[1983]},
 668:   { 'name':'squamous cell carcinoma of the bladder',             'parentId':[1983]},
 669:   { 'name':'adenocarcinoma of the bladder',             'parentId':[1983]},
 677:   { 'name':'metastatic squamous neck cancer with occult primary squamous cell carcinoma',             'parentId':[1972]},
 678:   { 'name':'salivary gland squamous cell carcinoma',             'parentId':[1959]},
 679:   { 'name':'nasopharyngeal squamous cell carcinoma',             'parentId':[1977]},
 684:   { 'name':'clear cell renal cell carcinoma',             'parentId':[1975]},
 685:   { 'name':'type B1 thymoma',             'parentId':[1876]},
 687:   { 'name':'type A thymoma',             'parentId':[1968]},
 689:   { 'name':'testicular embryonal carcinoma',             'parentId':[1973]},
 690:   { 'name':'testicular choriocarcinoma',             'parentId':[1973]},
 691:   { 'name':'testicular teratoma',             'parentId':[2131, 2129]},
 692:   { 'name':'testicular yolk sac tumor',             'parentId':[1973]},
 693:   { 'name':'testicular embryonal carcinoma and teratoma',             'parentId':[1973]},
 694:   { 'name':'testicular embryonal carcinoma and teratoma with seminoma',             'parentId':[1973]},
 695:   { 'name':'testicular embryonal carcinoma and yolk sac tumor',             'parentId':[1973]},
 696:   { 'name':'testicular embryonal carcinoma and yolk sac tumor with seminoma',             'parentId':[1973]},
 697:   { 'name':'testicular embryonal carcinoma and seminoma',             'parentId':[1973]},
 698:   { 'name':'testicular yolk sac tumor and teratoma',             'parentId':[1973]},
 699:   { 'name':'testicular yolk sac tumor and teratoma with seminoma',             'parentId':[1973]},
 700:   { 'name':'testicular choriocarcinoma and yolk sac tumor',             'parentId':[1973]},
 701:   { 'name':'testicular choriocarcinoma and embryonal carcinoma',             'parentId':[1973]},
 702:   { 'name':'testicular choriocarcinoma and teratoma',             'parentId':[1973]},
 703:   { 'name':'pancreatic beta islet cell adenoma',             'parentId':[2127]},
 704:   { 'name':'testicular choriocarcinoma and seminoma',             'parentId':[1973]},
 705:   { 'name':'pancreatic beta islet cell carcinoma',             'parentId':[2124, 2127]},
 706:   { 'name':'pancreatic G-cell adenoma',             'parentId':[2125]},
 707:   { 'name':'pancreatic G-cell carcinoma',             'parentId':[2125, 2124]},
 708:   { 'name':'pancreatic delta cell adenoma',             'parentId':[2128]},
 709:   { 'name':'pancreatic delta cell carcinoma',             'parentId':[2124, 2128]},
 710:   { 'name':'pancreatic alpha cell adenoma',             'parentId':[2126]},
 711:   { 'name':'pituitary basophilic adenoma',             'parentId':[379]},
 712:   { 'name':'pancreatic alpha cell carcinoma',             'parentId':[2124, 2126]},
 713:   { 'name':'prolactin secreting adenoma',             'parentId':[380]},
 714:   { 'name':'pituitary eosinophilic adenoma',             'parentId':[381]},
 715:   { 'name':'TSH secreting adenoma',             'parentId':[389]},
 716:   { 'name':'pituitary chromophobe adenoma',             'parentId':[390]},
 718:   { 'name':'salivary gland acinic cell tumor',             'parentId':[1957]},
 720:   { 'name':'low-grade salivary gland mucoepidermoid carcinoma',             'parentId':[1957]},
 722:   { 'name':'high-grade salivary gland mucoepidermoid carcinoma',             'parentId':[1959]},
 723:   { 'name':'salivary gland adenocarcinoma',             'parentId':[1959]},
 724:   { 'name':'salivary gland poorly differentiated carcinoma',             'parentId':[1959]},
 726:   { 'name':'salivary gland anaplastic carcinoma',             'parentId':[1959]},
 727:   { 'name':'salivary gland malignant mixed cell type tumor',             'parentId':[1959]},
 728:   { 'name':'salivary gland adenoid cystic carcinoma',             'parentId':[1959]},
 729:   { 'name':'endometrial adenoacanthoma',             'parentId':[1971]},
 730:   { 'name':'endometrial papillary serous carcinoma',             'parentId':[1971]},
 731:   { 'name':'endometrial clear cell carcinoma',             'parentId':[1971]},
 732:   { 'name':'plasma cell neoplasm',             'parentId':[511]},
 733:   { 'name':'untreated hairy cell leukemia',             'parentId':[2068]},
 734:   { 'name':'progressive hairy cell leukemia, initial treatment',             'parentId':[733]},
 735:   { 'name':'refractory hairy cell leukemia',             'parentId':[2068]},
 736:   { 'name':'lip and oral cavity cancer',             'parentId':[440, 889]},
 737:   { 'name':'stage I lip and oral cavity cancer',             'parentId':[2072]},
 738:   { 'name':'stage II lip and oral cavity cancer',             'parentId':[2072]},
 739:   { 'name':'stage III lip and oral cavity cancer',             'parentId':[2072]},
 740:   { 'name':'stage IV lip and oral cavity cancer',             'parentId':[2072]},
 741:   { 'name':'recurrent lip and oral cavity cancer',             'parentId':[2072]},
 742:   { 'name':'stage I hypopharyngeal cancer',             'parentId':[2076]},
 743:   { 'name':'stage II hypopharyngeal cancer',             'parentId':[2076]},
 744:   { 'name':'stage III hypopharyngeal cancer',             'parentId':[2076]},
 745:   { 'name':'stage IV hypopharyngeal cancer',             'parentId':[2076]},
 746:   { 'name':'recurrent hypopharyngeal cancer',             'parentId':[2076]},
 747:   { 'name':'squamous cell lung cancer',             'parentId':[1981]},
 748:   { 'name':'stage I laryngeal cancer',             'parentId':[1895]},
 749:   { 'name':'stage II laryngeal cancer',             'parentId':[1895]},
 750:   { 'name':'stage III laryngeal cancer',             'parentId':[1895]},
 751:   { 'name':'stage IV laryngeal cancer',             'parentId':[1895]},
 752:   { 'name':'recurrent laryngeal cancer',             'parentId':[1895]},
 753:   { 'name':'localized benign pheochromocytoma',             'parentId':[2036]},
 754:   { 'name':'regional pheochromocytoma',             'parentId':[2036]},
 755:   { 'name':'metastatic pheochromocytoma',             'parentId':[2036]},
 756:   { 'name':'recurrent pheochromocytoma',             'parentId':[2036]},
 757:   { 'name':'childhood cerebellar astrocytoma',             'parentId':[2165]},
 758:   { 'name':'paranasal sinus and nasal cavity cancer',             'parentId':[440, 889]},
 759:   { 'name':'stage I paranasal sinus and nasal cavity cancer',             'parentId':[2079]},
 760:   { 'name':'stage II paranasal sinus and nasal cavity cancer',             'parentId':[2079]},
 761:   { 'name':'stage III paranasal sinus and nasal cavity cancer',             'parentId':[2079]},
 762:   { 'name':'stage IV paranasal sinus and nasal cavity cancer',             'parentId':[2079]},
 763:   { 'name':'recurrent paranasal sinus and nasal cavity cancer',             'parentId':[2079]},
 764:   { 'name':'stage I oropharyngeal cancer',             'parentId':[2074]},
 765:   { 'name':'stage II oropharyngeal cancer',             'parentId':[2074]},
 766:   { 'name':'stage III oropharyngeal cancer',             'parentId':[2074]},
 767:   { 'name':'stage IV oropharyngeal cancer',             'parentId':[2074]},
 768:   { 'name':'recurrent oropharyngeal cancer',             'parentId':[2074]},
 769:   { 'name':'oral complications',             'parentId':[1215]},
 770:   { 'name':'ovarian stromal cancer',             'parentId':[1745, 889]},
 794:   { 'name':'cellular diagnosis, colon cancer',             'parentId':[1438]},
 796:   { 'name':'cellular diagnosis, prostate cancer',             'parentId':[351]},
 809:   { 'name':'cellular diagnosis, chronic myelogenous leukemia',             'parentId':[200]},
 813:   { 'name':'adult Hodgkin lymphoma',             'parentId':[1262]},
 814:   { 'name':'lung cancer',             'parentId':[487]},
 819:   { 'name':'papillary thyroid cancer',             'parentId':[2059]},
 820:   { 'name':'follicular thyroid cancer',             'parentId':[2059]},
 821:   { 'name':'recurrent urethral cancer',             'parentId':[2062]},
 822:   { 'name':'stage II childhood Hodgkin lymphoma',             'parentId':[2081]},
 823:   { 'name':'childhood Hodgkin lymphoma',             'parentId':[1513, 1262]},
 824:   { 'name':'stage I childhood Hodgkin lymphoma',             'parentId':[2081]},
 825:   { 'name':'stage III childhood Hodgkin lymphoma',             'parentId':[2081]},
 826:   { 'name':'stage IV childhood Hodgkin lymphoma',             'parentId':[2081]},
 827:   { 'name':'recurrent/refractory childhood Hodgkin lymphoma',             'parentId':[2081]},
 828:   { 'name':'childhood lymphocyte predominant Hodgkin lymphoma',             'parentId':[2004]},
 829:   { 'name':'childhood lymphocyte depletion Hodgkin lymphoma',             'parentId':[2004]},
 830:   { 'name':'childhood nodular sclerosis Hodgkin lymphoma',             'parentId':[2004]},
 831:   { 'name':'childhood mixed cellularity Hodgkin lymphoma',             'parentId':[2004]},
 833:   { 'name':'anterior urethral cancer',             'parentId':[2062]},
 834:   { 'name':'posterior urethral cancer',             'parentId':[2062]},
 835:   { 'name':'urethral cancer associated with invasive bladder cancer',             'parentId':[2062]},
 836:   { 'name':'metastatic transitional cell cancer of the renal pelvis and ureter',             'parentId':[2071]},
 837:   { 'name':'adult fibrosarcoma',             'parentId':[1976]},
 838:   { 'name':'childhood soft tissue sarcoma',             'parentId':[1736, 1890, 1513]},
 839:   { 'name':'nonmetastatic childhood soft tissue sarcoma',             'parentId':[2048]},
 840:   { 'name':'metastatic childhood soft tissue sarcoma',             'parentId':[2048]},
 841:   { 'name':'recurrent childhood soft tissue sarcoma',             'parentId':[2048]},
 843:   { 'name':'stage I gastric cancer',             'parentId':[2054]},
 844:   { 'name':'stage I small lymphocytic lymphoma',             'parentId':[241, 1347]},
 845:   { 'name':'stage I grade 1 follicular lymphoma',             'parentId':[1347, 266]},
 846:   { 'name':'stage I grade 2 follicular lymphoma',             'parentId':[267, 1347]},
 847:   { 'name':'stage I grade 3 follicular lymphoma',             'parentId':[271, 1349]},
 848:   { 'name':'stage I adult diffuse small cleaved cell lymphoma',             'parentId':[268, 1347]},
 849:   { 'name':'stage I adult diffuse mixed cell lymphoma',             'parentId':[1349, 272]},
 850:   { 'name':'stage I adult diffuse large cell lymphoma',             'parentId':[1349, 273]},
 851:   { 'name':'stage I adult immunoblastic large cell lymphoma',             'parentId':[1349, 274]},
 852:   { 'name':'stage I adult lymphoblastic lymphoma',             'parentId':[1349, 179]},
 853:   { 'name':'stage I adult Burkitt lymphoma',             'parentId':[275, 1349]},
 854:   { 'name':'adenocarcinoma of the prostate',             'parentId':[796]},
 855:   { 'name':'ovarian germ cell tumor',             'parentId':[1890, 1585, 889, 439]},
 856:   { 'name':'stage I ovarian germ cell tumor',             'parentId':[2031]},
 857:   { 'name':'stage II ovarian germ cell tumor',             'parentId':[2031]},
 858:   { 'name':'stage III ovarian germ cell tumor',             'parentId':[2031]},
 859:   { 'name':'stage IV ovarian germ cell tumor',             'parentId':[2031]},
 860:   { 'name':'recurrent ovarian germ cell tumor',             'parentId':[2031]},
 862:   { 'name':'extraosseous Ewing sarcoma/peripheral primitive neuroectodermal tumor',             'parentId':[1964]},
 863:   { 'name':'childhood fibrosarcoma',             'parentId':[2006]},
 864:   { 'name':'childhood synovial sarcoma',             'parentId':[2006]},
 865:   { 'name':'childhood malignant hemangiopericytoma',             'parentId':[2006]},
 866:   { 'name':'childhood liposarcoma',             'parentId':[2006]},
 867:   { 'name':'childhood alveolar soft-part sarcoma',             'parentId':[2006]},
 868:   { 'name':'childhood leiomyosarcoma',             'parentId':[2006]},
 869:   { 'name':'childhood neurofibrosarcoma',             'parentId':[2006]},
 870:   { 'name':'childhood angiosarcoma',             'parentId':[2006]},
 871:   { 'name':'childhood epithelioid sarcoma',             'parentId':[2006]},
 872:   { 'name':'childhood malignant fibrous histiocytoma of bone',             'parentId':[1890, 1513, 1427]},
 873:   { 'name':'childhood malignant mesenchymoma',             'parentId':[2006]},
 874:   { 'name':'recurrent adult non-Hodgkin lymphoma',             'parentId':[2005]},
 875:   { 'name':'stage II gastric cancer',             'parentId':[2054]},
 876:   { 'name':'refractory anemia',             'parentId':[2100]},
 877:   { 'name':'refractory anemia with ringed sideroblasts',             'parentId':[2100]},
 878:   { 'name':'refractory anemia with excess blasts',             'parentId':[2100]},
 879:   { 'name':'refractory anemia with excess blasts in transformation',             'parentId':[2100]},
 880:   { 'name':'chronic myelomonocytic leukemia',             'parentId':[135, 1887]},
 881:   { 'name':'chordoma',             'parentId':[889, 518]},
 882:   { 'name':'stage III gastric cancer',             'parentId':[2054]},
 883:   { 'name':'multicentric Castleman disease',             'parentId':[1778]},
 884:   { 'name':'pulmonary carcinoid tumor',             'parentId':[814, 889, 2166]},
 887:   { 'name':'squamous cell carcinoma of unknown primary',             'parentId':[2099]},
 888:   { 'name':'undifferentiated carcinoma of unknown primary',             'parentId':[2099]},
 889:   { 'name':'adult solid tumor',             'parentId':[890]},
 890:   { 'name':'solid tumor',             'parentId':[1185]},
 891:   { 'name':'transitional cell cancer of the renal pelvis and ureter',             'parentId':[509, 889]},
 892:   { 'name':'stage I adult non-Hodgkin lymphoma',             'parentId':[2005]},
 893:   { 'name':'stage II adult non-Hodgkin lymphoma',             'parentId':[2005]},
 894:   { 'name':'stage III adult non-Hodgkin lymphoma',             'parentId':[2005]},
 895:   { 'name':'stage IV adult non-Hodgkin lymphoma',             'parentId':[2005]},
 896:   { 'name':'uterine sarcoma',             'parentId':[889, 1736, 1681]},
 899:   { 'name':'ovarian embryonal carcinoma',             'parentId':[2009]},
 900:   { 'name':'ovarian polyembryoma',             'parentId':[2009]},
 901:   { 'name':'ovarian choriocarcinoma',             'parentId':[2009]},
 902:   { 'name':'ovarian teratoma',             'parentId':[2131, 2009]},
 903:   { 'name':'ovarian immature teratoma',             'parentId':[902]},
 904:   { 'name':'ovarian mature teratoma',             'parentId':[902]},
 905:   { 'name':'ovarian monodermal and highly specialized teratoma',             'parentId':[902]},
 906:   { 'name':'ovarian mixed germ cell tumor',             'parentId':[2009]},
 907:   { 'name':'stage IV gastric cancer',             'parentId':[2054]},
 908:   { 'name':'acute undifferentiated leukemia',             'parentId':[431]},
 909:   { 'name':'stage II small lymphocytic lymphoma',             'parentId':[241]},
 910:   { 'name':'stage II grade 1 follicular lymphoma',             'parentId':[266]},
 911:   { 'name':'stage II grade 2 follicular lymphoma',             'parentId':[267]},
 912:   { 'name':'stage II grade 3 follicular lymphoma',             'parentId':[271]},
 913:   { 'name':'stage II adult diffuse small cleaved cell lymphoma',             'parentId':[268]},
 914:   { 'name':'stage II adult diffuse mixed cell lymphoma',             'parentId':[272]},
 915:   { 'name':'stage II adult diffuse large cell lymphoma',             'parentId':[273]},
 916:   { 'name':'stage II adult immunoblastic large cell lymphoma',             'parentId':[274]},
 917:   { 'name':'stage II adult lymphoblastic lymphoma',             'parentId':[179]},
 918:   { 'name':'stage II adult Burkitt lymphoma',             'parentId':[275]},
 919:   { 'name':'stage III small lymphocytic lymphoma',             'parentId':[1376, 241]},
 920:   { 'name':'stage III grade 1 follicular lymphoma',             'parentId':[266, 1376]},
 921:   { 'name':'stage III grade 2 follicular lymphoma',             'parentId':[267, 1376]},
 922:   { 'name':'stage III grade 3 follicular lymphoma',             'parentId':[271, 1378]},
 923:   { 'name':'stage III adult diffuse small cleaved cell lymphoma',             'parentId':[268, 1376]},
 924:   { 'name':'stage III adult diffuse mixed cell lymphoma',             'parentId':[272, 1378]},
 925:   { 'name':'stage III adult diffuse large cell lymphoma',             'parentId':[1378, 273]},
 926:   { 'name':'stage III adult immunoblastic large cell lymphoma',             'parentId':[1378, 274]},
 927:   { 'name':'stage III adult lymphoblastic lymphoma',             'parentId':[179, 1378]},
 928:   { 'name':'stage III adult Burkitt lymphoma',             'parentId':[1378, 275]},
 929:   { 'name':'stage IV small lymphocytic lymphoma',             'parentId':[241, 1379]},
 930:   { 'name':'stage IV grade 1 follicular lymphoma',             'parentId':[1379, 266]},
 931:   { 'name':'stage IV grade 2 follicular lymphoma',             'parentId':[267, 1379]},
 932:   { 'name':'stage IV grade 3 follicular lymphoma',             'parentId':[271, 1381]},
 933:   { 'name':'stage IV adult diffuse small cleaved cell lymphoma',             'parentId':[1379, 268]},
 934:   { 'name':'stage IV adult diffuse mixed cell lymphoma',             'parentId':[1381, 272]},
 935:   { 'name':'stage IV adult diffuse large cell lymphoma',             'parentId':[1381, 273]},
 936:   { 'name':'stage IV adult immunoblastic large cell lymphoma',             'parentId':[1381, 274]},
 937:   { 'name':'stage IV adult lymphoblastic lymphoma',             'parentId':[1381, 179]},
 938:   { 'name':'stage IV adult Burkitt lymphoma',             'parentId':[275, 1381]},
 939:   { 'name':'recurrent small lymphocytic lymphoma',             'parentId':[241, 1382]},
 940:   { 'name':'recurrent grade 1 follicular lymphoma',             'parentId':[1382, 266]},
 941:   { 'name':'recurrent grade 2 follicular lymphoma',             'parentId':[1382, 267]},
 942:   { 'name':'recurrent grade 3 follicular lymphoma',             'parentId':[271, 1384]},
 943:   { 'name':'recurrent adult diffuse small cleaved cell lymphoma',             'parentId':[1382, 268]},
 944:   { 'name':'recurrent adult diffuse mixed cell lymphoma',             'parentId':[272, 1384]},
 945:   { 'name':'recurrent adult diffuse large cell lymphoma',             'parentId':[273, 1384]},
 946:   { 'name':'recurrent adult immunoblastic large cell lymphoma',             'parentId':[274, 1384]},
 947:   { 'name':'recurrent adult lymphoblastic lymphoma',             'parentId':[179, 1384]},
 948:   { 'name':'recurrent adult Burkitt lymphoma',             'parentId':[1384, 275]},
 949:   { 'name':'childhood infratentorial ependymoma',             'parentId':[1449]},
 955:   { 'name':'stage, Kaposi sarcoma',             'parentId':[377]},
 973:   { 'name':'stage I anal cancer',             'parentId':[2025]},
 981:   { 'name':'constipation, impaction, and bowel obstruction',             'parentId':[108]},
 986:   { 'name':'localized transitional cell cancer of the renal pelvis and ureter',             'parentId':[2071]},
 997:   { 'name':'stage, chronic myelogenous leukemia',             'parentId':[200]},
 998:   { 'name':'stage, colon cancer',             'parentId':[1438]},
 1016:   { 'name':'regional transitional cell cancer of the renal pelvis and ureter',             'parentId':[2071]},
 1028:   { 'name':'hydatidiform mole',             'parentId':[2101, 2002]},
 1037:   { 'name':'stage II anal cancer',             'parentId':[2025]},
 1042:   { 'name':'childhood brain stem glioma',             'parentId':[2042]},
 1044:   { 'name':'adult T-cell leukemia/lymphoma',             'parentId':[1241]},
 1045:   { 'name':'stage I squamous cell carcinoma of the lip and oral cavity',             'parentId':[2082, 737]},
 1046:   { 'name':'stage I basal cell carcinoma of the lip',             'parentId':[1958, 737]},
 1047:   { 'name':'stage I verrucous carcinoma of the oral cavity',             'parentId':[737, 2083]},
 1048:   { 'name':'stage I mucoepidermoid carcinoma of the oral cavity',             'parentId':[2084, 737]},
 1049:   { 'name':'stage I adenoid cystic carcinoma of the oral cavity',             'parentId':[737, 2085]},
 1050:   { 'name':'stage II squamous cell carcinoma of the lip and oral cavity',             'parentId':[738, 2082]},
 1051:   { 'name':'stage II basal cell carcinoma of the lip',             'parentId':[738, 1958]},
 1052:   { 'name':'stage II verrucous carcinoma of the oral cavity',             'parentId':[2083, 738]},
 1053:   { 'name':'stage II mucoepidermoid carcinoma of the oral cavity',             'parentId':[738, 2084]},
 1054:   { 'name':'stage II adenoid cystic carcinoma of the oral cavity',             'parentId':[738, 2085]},
 1055:   { 'name':'stage III squamous cell carcinoma of the lip and oral cavity',             'parentId':[739, 2082]},
 1056:   { 'name':'stage III basal cell carcinoma of the lip',             'parentId':[1958, 739]},
 1057:   { 'name':'stage III verrucous carcinoma of the oral cavity',             'parentId':[2083, 739]},
 1058:   { 'name':'stage III mucoepidermoid carcinoma of the oral cavity',             'parentId':[739, 2084]},
 1059:   { 'name':'stage III adenoid cystic carcinoma of the oral cavity',             'parentId':[739, 2085]},
 1060:   { 'name':'stage IV squamous cell carcinoma of the lip and oral cavity',             'parentId':[2082, 740]},
 1061:   { 'name':'stage IV basal cell carcinoma of the lip',             'parentId':[740, 1958]},
 1062:   { 'name':'stage IV verrucous carcinoma of the oral cavity',             'parentId':[740, 2083]},
 1063:   { 'name':'stage IV mucoepidermoid carcinoma of the oral cavity',             'parentId':[2084, 740]},
 1064:   { 'name':'stage IV adenoid cystic carcinoma of the oral cavity',             'parentId':[740, 2085]},
 1065:   { 'name':'recurrent squamous cell carcinoma of the lip and oral cavity',             'parentId':[2082, 741]},
 1066:   { 'name':'recurrent basal cell carcinoma of the lip',             'parentId':[1958, 741]},
 1067:   { 'name':'recurrent verrucous carcinoma of the oral cavity',             'parentId':[741, 2083]},
 1068:   { 'name':'recurrent mucoepidermoid carcinoma of the oral cavity',             'parentId':[2084, 741]},
 1069:   { 'name':'recurrent adenoid cystic carcinoma of the oral cavity',             'parentId':[741, 2085]},
 1070:   { 'name':'stage I squamous cell carcinoma of the oropharynx',             'parentId':[2086, 764]},
 1071:   { 'name':'stage I lymphoepithelioma of the oropharynx',             'parentId':[764, 2087]},
 1072:   { 'name':'stage II squamous cell carcinoma of the oropharynx',             'parentId':[2086, 765]},
 1073:   { 'name':'stage II lymphoepithelioma of the oropharynx',             'parentId':[2087, 765]},
 1074:   { 'name':'stage III squamous cell carcinoma of the oropharynx',             'parentId':[2086, 766]},
 1075:   { 'name':'stage III lymphoepithelioma of the oropharynx',             'parentId':[766, 2087]},
 1076:   { 'name':'stage IV squamous cell carcinoma of the oropharynx',             'parentId':[767, 2086]},
 1077:   { 'name':'stage IV lymphoepithelioma of the oropharynx',             'parentId':[2087, 767]},
 1078:   { 'name':'recurrent squamous cell carcinoma of the oropharynx',             'parentId':[768, 2086]},
 1079:   { 'name':'recurrent lymphoepithelioma of the oropharynx',             'parentId':[2087, 768]},
 1080:   { 'name':'stage I squamous cell carcinoma of the nasopharynx',             'parentId':[408, 679]},
 1081:   { 'name':'stage I lymphoepithelioma of the nasopharynx',             'parentId':[408, 1960]},
 1082:   { 'name':'stage II squamous cell carcinoma of the nasopharynx',             'parentId':[409, 679]},
 1083:   { 'name':'stage II lymphoepithelioma of the nasopharynx',             'parentId':[409, 1960]},
 1084:   { 'name':'stage III squamous cell carcinoma of the nasopharynx',             'parentId':[410, 679]},
 1085:   { 'name':'stage III lymphoepithelioma of the nasopharynx',             'parentId':[410, 1960]},
 1086:   { 'name':'stage IV squamous cell carcinoma of the nasopharynx',             'parentId':[679, 411]},
 1087:   { 'name':'stage IV lymphoepithelioma of the nasopharynx',             'parentId':[1960, 411]},
 1088:   { 'name':'recurrent squamous cell carcinoma of the nasopharynx',             'parentId':[679, 412]},
 1089:   { 'name':'recurrent lymphoepithelioma of the nasopharynx',             'parentId':[1960, 412]},
 1090:   { 'name':'stage I squamous cell carcinoma of the hypopharynx',             'parentId':[2088, 742]},
 1091:   { 'name':'stage II squamous cell carcinoma of the hypopharynx',             'parentId':[2088, 743]},
 1092:   { 'name':'stage III squamous cell carcinoma of the hypopharynx',             'parentId':[744, 2088]},
 1093:   { 'name':'stage IV squamous cell carcinoma of the hypopharynx',             'parentId':[2088, 745]},
 1094:   { 'name':'recurrent squamous cell carcinoma of the hypopharynx',             'parentId':[2088, 746]},
 1095:   { 'name':'stage I squamous cell carcinoma of the larynx',             'parentId':[2089, 748]},
 1096:   { 'name':'stage I verrucous carcinoma of the larynx',             'parentId':[2090, 748]},
 1097:   { 'name':'stage II squamous cell carcinoma of the larynx',             'parentId':[2089, 749]},
 1098:   { 'name':'stage II verrucous carcinoma of the larynx',             'parentId':[2090, 749]},
 1099:   { 'name':'stage III squamous cell carcinoma of the larynx',             'parentId':[2089, 750]},
 1100:   { 'name':'stage III verrucous carcinoma of the larynx',             'parentId':[2090, 750]},
 1101:   { 'name':'stage III anal cancer',             'parentId':[2025]},
 1102:   { 'name':'stage IV squamous cell carcinoma of the larynx',             'parentId':[751, 2089]},
 1103:   { 'name':'stage IV verrucous carcinoma of the larynx',             'parentId':[2090, 751]},
 1104:   { 'name':'recurrent squamous cell carcinoma of the larynx',             'parentId':[2089, 752]},
 1105:   { 'name':'recurrent verrucous carcinoma of the larynx',             'parentId':[2090, 752]},
 1106:   { 'name':'stage I squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[2091, 759]},
 1107:   { 'name':'stage I inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[2092, 759]},
 1108:   { 'name':'stage I midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[2093, 759]},
 1109:   { 'name':'stage I esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[759, 2094]},
 1110:   { 'name':'stage II squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[2091, 760]},
 1111:   { 'name':'stage II inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[2092, 760]},
 1112:   { 'name':'stage II midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[2093, 760]},
 1113:   { 'name':'stage II esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[2094, 760]},
 1114:   { 'name':'stage III squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[761, 2091]},
 1115:   { 'name':'stage III inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[2092, 761]},
 1116:   { 'name':'stage III midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[2093, 761]},
 1117:   { 'name':'stage III esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[2094, 761]},
 1118:   { 'name':'stage IV squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[762, 2091]},
 1119:   { 'name':'stage IV inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[762, 2092]},
 1120:   { 'name':'stage IV midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[762, 2093]},
 1121:   { 'name':'stage IV esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[2094, 762]},
 1122:   { 'name':'recurrent squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[2091, 763]},
 1123:   { 'name':'recurrent inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[763, 2092]},
 1124:   { 'name':'recurrent midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[2093, 763]},
 1125:   { 'name':'recurrent esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[2094, 763]},
 1126:   { 'name':'stage I adult T-cell leukemia/lymphoma',             'parentId':[1044]},
 1127:   { 'name':'stage II adult T-cell leukemia/lymphoma',             'parentId':[1044]},
 1128:   { 'name':'stage III adult T-cell leukemia/lymphoma',             'parentId':[1044]},
 1129:   { 'name':'stage IV adult T-cell leukemia/lymphoma',             'parentId':[1044]},
 1130:   { 'name':'recurrent adult T-cell leukemia/lymphoma',             'parentId':[1044]},
 1131:   { 'name':'secondary acute myeloid leukemia',             'parentId':[1442]},
 1134:   { 'name':'de novo myelodysplastic syndromes',             'parentId':[2096]},
 1135:   { 'name':'AIDS-related lymphoma',             'parentId':[1191, 1241]},
 1136:   { 'name':'recurrent gastric cancer',             'parentId':[2054]},
 1137:   { 'name':'inflammatory breast cancer',             'parentId':[2033]},
 1139:   { 'name':'tumors metastatic to brain',             'parentId':[2097]},
 1140:   { 'name':'lung metastases',             'parentId':[2097]},
 1141:   { 'name':'liver metastases',             'parentId':[2097]},
 1142:   { 'name':'bone metastases',             'parentId':[2097]},
 1143:   { 'name':'skin metastases',             'parentId':[889, 2097]},
 1144:   { 'name':'leptomeningeal metastases',             'parentId':[443, 2097]},
 1145:   { 'name':'malignant pericardial effusion',             'parentId':[2097]},
 1146:   { 'name':'malignant pleural effusion',             'parentId':[2097]},
 1147:   { 'name':'malignant ascites',             'parentId':[2097]},
 1149:   { 'name':'newly diagnosed carcinoma of unknown primary',             'parentId':[2098]},
 1152:   { 'name':'recurrent transitional cell cancer of the renal pelvis and ureter',             'parentId':[2071]},
 1153:   { 'name':'cloacogenic carcinoma of the anus',             'parentId':[1980]},
 1154:   { 'name':'basaloid carcinoma of the anus',             'parentId':[1980]},
 1155:   { 'name':'childhood high-grade cerebral astrocytoma',             'parentId':[1318]},
 1156:   { 'name':'childhood oligodendroglioma',             'parentId':[2165]},
 1158:   { 'name':'nonmetastatic gestational trophoblastic tumor',             'parentId':[2101]},
 1159:   { 'name':'low risk metastatic gestational trophoblastic tumor',             'parentId':[2101]},
 1160:   { 'name':'high risk metastatic gestational trophoblastic tumor',             'parentId':[2101]},
 1161:   { 'name':'adult anaplastic astrocytoma',             'parentId':[1410]},
 1162:   { 'name':'non-small cell lung cancer',             'parentId':[814, 889]},
 1163:   { 'name':'cholangiocarcinoma of the gallbladder',             'parentId':[1963]},
 1164:   { 'name':'cholangiocarcinoma of the extrahepatic bile duct',             'parentId':[1984]},
 1165:   { 'name':'carcinoma of the appendix',             'parentId':[889, 444]},
 1167:   { 'name':'stage I adult Hodgkin lymphoma',             'parentId':[2069]},
 1168:   { 'name':'small cell lung cancer',             'parentId':[814, 889]},
 1169:   { 'name':'fallopian tube cancer',             'parentId':[889, 439]},
 1170:   { 'name':'primary peritoneal cavity cancer',             'parentId':[889, 439]},
 1171:   { 'name':'childhood supratentorial primitive neuroectodermal tumor',             'parentId':[1666]},
 1172:   { 'name':'childhood choroid plexus tumor',             'parentId':[2042]},
 1173:   { 'name':'stage I uterine sarcoma',             'parentId':[1886]},
 1174:   { 'name':'stage II uterine sarcoma',             'parentId':[1886]},
 1175:   { 'name':'stage III uterine sarcoma',             'parentId':[1886]},
 1176:   { 'name':'stage IV uterine sarcoma',             'parentId':[1886]},
 1177:   { 'name':'recurrent uterine sarcoma',             'parentId':[1886]},
 1179:   { 'name':'uterine carcinosarcoma',             'parentId':[2103]},
 1180:   { 'name':'uterine leiomyosarcoma',             'parentId':[2103]},
 1181:   { 'name':'endometrial stromal sarcoma',             'parentId':[2148, 2103]},
 1182:   { 'name':'adult acute monocytic leukemia (M5b)',             'parentId':[491]},
 1183:   { 'name':'childhood meningioma',             'parentId':[2042, 2145]},
 1184:   { 'name':'radiation enteritis',             'parentId':[1251]},
 1185:   { 'name':'malignant neoplasm',             'parentId':[1873]}, 
 1186:   { 'name':'large cell lung cancer',             'parentId':[1981]},
 1187:   { 'name':'borderline ovarian surface epithelial-stromal tumor',             'parentId':[127]},
 1188:   { 'name':'adult primary cholangiocellular carcinoma',             'parentId':[1974]},
 1189:   { 'name':'occult non-small cell lung cancer',             'parentId':[2027]},
 1190:   { 'name':'recurrent carcinoma of unknown primary',             'parentId':[2098]},
 1191:   { 'name':'AIDS-related malignancies',             'parentId':[1185]},
 1192:   { 'name':'previously treated myelodysplastic syndromes',             'parentId':[2096]},
 1193:   { 'name':'secondary myelodysplastic syndromes',             'parentId':[2096]},
 1194:   { 'name':'ovarian sarcoma',             'parentId':[889, 1736, 1745]},
 1195:   { 'name':'adult leiomyosarcoma',             'parentId':[1976]},
 1196:   { 'name':'leukemia',             'parentId':[511]},
 1197:   { 'name':'adult myxopapillary ependymoma',             'parentId':[2112]},
 1198:   { 'name':'adult anaplastic ependymoma',             'parentId':[2112]},
 1199:   { 'name':'adult anaplastic oligodendroglioma',             'parentId':[2113]},
 1200:   { 'name':'adult mixed glioma',             'parentId':[2017]},
 1201:   { 'name':'adult pineal parenchymal tumor',             'parentId':[2017]},
 1202:   { 'name':'adult central nervous system germ cell tumor',             'parentId':[2095, 2143]},
 1203:   { 'name':'primary central nervous system non-Hodgkin lymphoma',             'parentId':[2177, 436]},
 1204:   { 'name':'skin integrity changes secondary to cutaneous metastases',             'parentId':[1215]},
 1205:   { 'name':'sleep disorders',             'parentId':[1215]},
 1206:   { 'name':'localized unresectable neuroblastoma',             'parentId':[2026]},
 1207:   { 'name':'stage I non-small cell lung cancer',             'parentId':[2027]},
 1208:   { 'name':'adult liposarcoma',             'parentId':[1976]},
 1209:   { 'name':'squamous cell carcinoma of the vulva',             'parentId':[1978]},
 1211:   { 'name':'stage II non-small cell lung cancer',             'parentId':[2027]},
 1212:   { 'name':'male breast cancer',             'parentId':[398]},
 1213:   { 'name':'lymphedema',             'parentId':[1215]},
 1214:   { 'name':'placental-site gestational trophoblastic tumor',             'parentId':[2101]},
 1215:   { 'name':'cancer-related problem/condition',             'parentId':[1185]},
 1216:   { 'name':'anemia',             'parentId':[1238]},
 1217:   { 'name':'cerebral edema',             'parentId':[1215]},
 1218:   { 'name':'drug/agent toxicity by tissue/organ',             'parentId':[1562]},
 1219:   { 'name':'cardiac toxicity',             'parentId':[1524, 1218]},
 1220:   { 'name':'drug extravasation',             'parentId':[1215]},
 1221:   { 'name':'neutropenia',             'parentId':[1238]},
 1222:   { 'name':'neurotoxicity',             'parentId':[1218]},
 1223:   { 'name':'urinary tract toxicity',             'parentId':[1218, 1528]},
 1224:   { 'name':'fatigue',             'parentId':[1215]},
 1225:   { 'name':'hypercalcemia of malignancy',             'parentId':[1557, 1861]},
 1226:   { 'name':'hypoglycemia',             'parentId':[1215]},
 1227:   { 'name':'infection',             'parentId':[1215]},
 1228:   { 'name':'long-term effects secondary to cancer therapy in children',             'parentId':[1708]},
 1229:   { 'name':'nausea and vomiting',             'parentId':[1215]},
 1230:   { 'name':'malnutrition',             'parentId':[1215]},
 1231:   { 'name':'anorexia',             'parentId':[1230]},
 1232:   { 'name':'cachexia',             'parentId':[1230, 276]},
 1233:   { 'name':'pain',             'parentId':[1215]},
 1234:   { 'name':'perioperative/postoperative complications',             'parentId':[1215]},
 1235:   { 'name':'poor vascular access',             'parentId':[1215]},
 1236:   { 'name':'psychosocial effects of cancer and its treatment',             'parentId':[1215]},
 1237:   { 'name':'sexual dysfunction and infertility',             'parentId':[188]},
 1238:   { 'name':'bone marrow suppression',             'parentId':[1215]},
 1239:   { 'name':'thrombocytopenia',             'parentId':[1238]},
 1240:   { 'name':'stage III non-small cell lung cancer',             'parentId':[2027]},
 1241:   { 'name':'lymphoma',             'parentId':[511]},
 1242:   { 'name':'previously untreated childhood rhabdomyosarcoma',             'parentId':[2046]},
 1243:   { 'name':'pancreatic cancer',             'parentId':[444, 889]},
 1244:   { 'name':'osteoporosis',             'parentId':[1215]},
 1245:   { 'name':'pulmonary complications',             'parentId':[1215]},
 1246:   { 'name':'childhood brain tumor',             'parentId':[1890, 437, 1513]},
 1247:   { 'name':'superior vena cava syndrome',             'parentId':[1215]},
 1248:   { 'name':'isolated plasmacytoma of bone',             'parentId':[2038]},
 1249:   { 'name':'graft versus host disease',             'parentId':[1215]},
 1250:   { 'name':'skin reactions secondary to radiation therapy',             'parentId':[1251]},
 1251:   { 'name':'radiation toxicity',             'parentId':[1562]},
 1252:   { 'name':'prolymphocytic leukemia',             'parentId':[1196]},
 1253:   { 'name':'diarrhea',             'parentId':[108]},
 1256:   { 'name':'AIDS-related peripheral/systemic lymphoma',             'parentId':[2105]},
 1257:   { 'name':'AIDS-related primary CNS lymphoma',             'parentId':[1203, 2105]},
 1258:   { 'name':'AIDS-related diffuse large cell lymphoma',             'parentId':[2106]},
 1259:   { 'name':'AIDS-related immunoblastic large cell lymphoma',             'parentId':[2106]},
 1260:   { 'name':'AIDS-related small noncleaved cell lymphoma',             'parentId':[2106]},
 1261:   { 'name':'childhood medulloblastoma',             'parentId':[1666]},
 1262:   { 'name':'Hodgkin lymphoma',             'parentId':[1241]},
 1263:   { 'name':'HIV-associated Hodgkin lymphoma',             'parentId':[2106]},
 1264:   { 'name':'metastatic cancer',             'parentId':[890, 1896, 2116]},
 1265:   { 'name':'desmoid tumor',             'parentId':[1896, 1892]},
 1266:   { 'name':'osteosarcoma',             'parentId':[1427, 889, 1890, 1513]},
 1267:   { 'name':'adult pilocytic astrocytoma',             'parentId':[1410]},
 1268:   { 'name':'adult subependymoma',             'parentId':[2112]},
 1269:   { 'name':'adult diffuse astrocytoma',             'parentId':[1410]},
 1270:   { 'name':'adult ependymoblastoma',             'parentId':[154]},
 1271:   { 'name':'adult pineocytoma',             'parentId':[1201]},
 1272:   { 'name':'adult pineoblastoma',             'parentId':[1201]},
 1273:   { 'name':'adult anaplastic meningioma',             'parentId':[162]},
 1274:   { 'name':'adult papillary meningioma',             'parentId':[162]},
 1275:   { 'name':'adult meningeal hemangiopericytoma',             'parentId':[1878]},
 1276:   { 'name':'adenocarcinoma of the pancreas',             'parentId':[1986]},
 1277:   { 'name':'hypotension',             'parentId':[1215]},
 1278:   { 'name':'stage II adult Hodgkin lymphoma',             'parentId':[2069]},
 1279:   { 'name':'localized osteosarcoma',             'parentId':[2029]},
 1280:   { 'name':'AIDS-related diffuse mixed cell lymphoma',             'parentId':[2106]},
 1281:   { 'name':'AIDS-related diffuse small cleaved cell lymphoma',             'parentId':[2106]},
 1282:   { 'name':'adult malignant fibrous histiocytoma of bone',             'parentId':[1427]},
 1283:   { 'name':'extramedullary plasmacytoma',             'parentId':[2038]},
 1284:   { 'name':'refractory multiple myeloma',             'parentId':[1406]},
 1285:   { 'name':'metastatic osteosarcoma',             'parentId':[2029]},
 1286:   { 'name':'AIDS-related lymphoblastic lymphoma',             'parentId':[2106]},
 1287:   { 'name':'primary systemic amyloidosis',             'parentId':[1709]},
 1288:   { 'name':'childhood desmoplastic small round cell tumor',             'parentId':[2006]},
 1289:   { 'name':'stage IIIB breast cancer',             'parentId':[454]},
 1290:   { 'name':'veno-occlusive disease',             'parentId':[1215]},
 1293:   { 'name':'neuroblastoma',             'parentId':[2144, 1890, 1513]},
 1294:   { 'name':'adult neurofibrosarcoma',             'parentId':[1976]},
 1295:   { 'name':'adult acute minimally differentiated myeloid leukemia (M0)',             'parentId':[1970]},
 1296:   { 'name':'childhood acute minimally differentiated myeloid leukemia (M0)',             'parentId':[1993]},
 1297:   { 'name':'intraocular lymphoma',             'parentId':[1891, 436]},
 1299:   { 'name':'stage I childhood small noncleaved cell lymphoma',             'parentId':[198]},
 1300:   { 'name':'stage I childhood large cell lymphoma',             'parentId':[196]},
 1301:   { 'name':'stage II childhood small noncleaved cell lymphoma',             'parentId':[198]},
 1302:   { 'name':'stage II childhood large cell lymphoma',             'parentId':[196]},
 1303:   { 'name':'stage III childhood small noncleaved cell lymphoma',             'parentId':[300]},
 1304:   { 'name':'stage III childhood large cell lymphoma',             'parentId':[197]},
 1305:   { 'name':'stage IV childhood small noncleaved cell lymphoma',             'parentId':[300]},
 1306:   { 'name':'stage IV childhood large cell lymphoma',             'parentId':[197]},
 1307:   { 'name':'recurrent childhood small noncleaved cell lymphoma',             'parentId':[1885, 1634]},
 1308:   { 'name':'recurrent childhood large cell lymphoma',             'parentId':[1634, 2109]},
 1310:   { 'name':'transitional care planning',             'parentId':[1215]},
 1311:   { 'name':'delirium',             'parentId':[1215]},
 1312:   { 'name':'suicide',             'parentId':[1236]},
 1313:   { 'name':'recurrent non-small cell lung cancer',             'parentId':[2027]},
 1314:   { 'name':'hyperuricemia',             'parentId':[1215]},
 1315:   { 'name':'tumor lysis syndrome',             'parentId':[1215]},
 1316:   { 'name':'stage 0 colon cancer',             'parentId':[998]},
 1317:   { 'name':'untreated childhood brain stem glioma',             'parentId':[1042]},
 1318:   { 'name':'childhood cerebral astrocytoma/malignant glioma',             'parentId':[2165]},
 1319:   { 'name':'recurrent childhood brain stem glioma',             'parentId':[1497, 1042]},
 1320:   { 'name':'untreated childhood supratentorial primitive neuroectodermal tumor',             'parentId':[1171]},
 1321:   { 'name':'recurrent childhood supratentorial primitive neuroectodermal tumor',             'parentId':[1171, 1497]},
 1322:   { 'name':'untreated childhood visual pathway glioma',             'parentId':[1447]},
 1323:   { 'name':'recurrent childhood visual pathway glioma',             'parentId':[1497, 1447]},
 1324:   { 'name':'untreated childhood cerebellar astrocytoma',             'parentId':[757]},
 1325:   { 'name':'recurrent childhood cerebellar astrocytoma',             'parentId':[1497, 757]},
 1326:   { 'name':'recurrent childhood cerebral astrocytoma',             'parentId':[1497, 1318]},
 1327:   { 'name':'untreated childhood medulloblastoma',             'parentId':[1261]},
 1328:   { 'name':'recurrent childhood medulloblastoma',             'parentId':[1497, 1261]},
 1329:   { 'name':'recurrent childhood acute lymphoblastic leukemia',             'parentId':[2040]},
 1330:   { 'name':'cognitive/functional effects',             'parentId':[1215]},
 1331:   { 'name':'stage I pancreatic cancer',             'parentId':[2032]},
 1332:   { 'name':'anxiety disorder',             'parentId':[1236]},
 1333:   { 'name':'depression',             'parentId':[1236]},
 1334:   { 'name':'stage III adult Hodgkin lymphoma',             'parentId':[2069]},
 1335:   { 'name':'stage II pancreatic cancer',             'parentId':[2032]},
 1336:   { 'name':'Cockayne syndrome',             'parentId':[105]},
 1337:   { 'name':'childhood low-grade cerebral astrocytoma',             'parentId':[1318]},
 1338:   { 'name':'pruritus',             'parentId':[1215]},
 1339:   { 'name':'stage III pancreatic cancer',             'parentId':[2032]},
 1340:   { 'name':'mantle cell lymphoma',             'parentId':[2003]},
 1341:   { 'name':'stage IV pancreatic cancer',             'parentId':[2032]},
 1342:   { 'name':'ovarian carcinosarcoma',             'parentId':[1989]},
 1343:   { 'name':'stage 0 gastric cancer',             'parentId':[2054]},
 1344:   { 'name':'post-traumatic stress disorder',             'parentId':[1236]},
 1345:   { 'name':'stage 0 melanoma',             'parentId':[2021]},
 1347:   { 'name':'indolent, stage I adult non-Hodgkin lymphoma',             'parentId':[892, 1386]},
 1348:   { 'name':'stage I mantle cell lymphoma',             'parentId':[1349, 1340]},
 1349:   { 'name':'aggressive, stage I adult non-Hodgkin lymphoma',             'parentId':[1387, 892]},
 1350:   { 'name':'contiguous stage II adult non-Hodgkin lymphoma',             'parentId':[893]},
 1351:   { 'name':'indolent, contiguous stage II adult non-Hodgkin lymphoma',             'parentId':[1350, 139]},
 1352:   { 'name':'contiguous stage II grade 1 follicular lymphoma',             'parentId':[910, 1351]},
 1353:   { 'name':'contiguous stage II grade 2 follicular lymphoma',             'parentId':[1351, 911]},
 1354:   { 'name':'contiguous stage II grade 3 follicular lymphoma',             'parentId':[912, 1356]},
 1355:   { 'name':'contiguous stage II adult diffuse small cleaved cell lymphoma',             'parentId':[913, 1351]},
 1356:   { 'name':'aggressive, contiguous stage II adult non-Hodgkin lymphoma',             'parentId':[1350, 153]},
 1357:   { 'name':'contiguous stage II mantle cell lymphoma',             'parentId':[1390, 1356]},
 1358:   { 'name':'contiguous stage II adult diffuse mixed cell lymphoma',             'parentId':[914, 1356]},
 1359:   { 'name':'contiguous stage II adult immunoblastic large cell lymphoma',             'parentId':[1356, 916]},
 1360:   { 'name':'contiguous stage II adult diffuse large cell lymphoma',             'parentId':[915, 1356]},
 1361:   { 'name':'contiguous stage II adult Burkitt lymphoma',             'parentId':[918, 1356]},
 1362:   { 'name':'contiguous stage II adult lymphoblastic lymphoma',             'parentId':[1356, 917]},
 1363:   { 'name':'noncontiguous stage II adult non-Hodgkin lymphoma',             'parentId':[893]},
 1364:   { 'name':'indolent, noncontiguous stage II adult non-Hodgkin lymphoma',             'parentId':[139, 1363]},
 1365:   { 'name':'noncontiguous stage II grade 1 follicular lymphoma',             'parentId':[1364, 910]},
 1366:   { 'name':'noncontiguous stage II grade 2 follicular lymphoma',             'parentId':[911, 1364]},
 1367:   { 'name':'noncontiguous stage II grade 3 follicular lymphoma',             'parentId':[912, 1370]},
 1368:   { 'name':'noncontiguous stage II adult diffuse small cleaved cell lymphoma',             'parentId':[1364, 913]},
 1369:   { 'name':'noncontiguous stage II mantle cell lymphoma',             'parentId':[1370, 1390]},
 1370:   { 'name':'aggressive, noncontiguous stage II adult non-Hodgkin lymphoma',             'parentId':[153, 1363]},
 1371:   { 'name':'noncontiguous stage II adult diffuse mixed cell lymphoma',             'parentId':[1370, 914]},
 1372:   { 'name':'noncontiguous stage II adult immunoblastic large cell lymphoma',             'parentId':[1370, 916]},
 1373:   { 'name':'noncontiguous stage II adult diffuse large cell lymphoma',             'parentId':[1370, 915]},
 1374:   { 'name':'noncontiguous stage II adult Burkitt lymphoma',             'parentId':[918, 1370]},
 1375:   { 'name':'noncontiguous stage II adult lymphoblastic lymphoma',             'parentId':[1370, 917]},
 1376:   { 'name':'indolent, stage III adult non-Hodgkin lymphoma',             'parentId':[1386, 894]},
 1377:   { 'name':'stage III mantle cell lymphoma',             'parentId':[1378, 1340]},
 1378:   { 'name':'aggressive, stage III adult non-Hodgkin lymphoma',             'parentId':[1387, 894]},
 1379:   { 'name':'indolent, stage IV adult non-Hodgkin lymphoma',             'parentId':[1386, 895]},
 1380:   { 'name':'stage IV mantle cell lymphoma',             'parentId':[1340, 1381]},
 1381:   { 'name':'aggressive, stage IV adult non-Hodgkin lymphoma',             'parentId':[895, 1387]},
 1382:   { 'name':'indolent, recurrent adult non-Hodgkin lymphoma',             'parentId':[1386, 874]},
 1383:   { 'name':'recurrent mantle cell lymphoma',             'parentId':[1384, 1340]},
 1384:   { 'name':'aggressive, recurrent adult non-Hodgkin lymphoma',             'parentId':[1387, 874]},
 1386:   { 'name':'indolent, adult non-Hodgkin lymphoma',             'parentId':[2111]},
 1387:   { 'name':'aggressive, adult non-Hodgkin lymphoma',             'parentId':[2111]},
 1388:   { 'name':'angioimmunoblastic T-cell lymphoma',             'parentId':[1241]},
 1389:   { 'name':'anaplastic large cell lymphoma',             'parentId':[1241]},
 1390:   { 'name':'stage II mantle cell lymphoma',             'parentId':[1340, 153]},
 1391:   { 'name':'squamous cell carcinoma of the skin',             'parentId':[2050]},
 1392:   { 'name':'adult non-Hodgkin lymphoma',             'parentId':[436]},
 1393:   { 'name':'familial adenomatous polyposis',             'parentId':[105, 1778]},
 1394:   { 'name':'hereditary breast/ovarian cancer (BRCA1, BRCA2)',             'parentId':[105]},
 1395:   { 'name':'hereditary non-polyposis colon cancer',             'parentId':[105]},
 1396:   { 'name':'hereditary retinoblastoma',             'parentId':[105]},
 1397:   { 'name':'hereditary Wilms tumor',             'parentId':[105]},
 1398:   { 'name':'Li-Fraumeni syndrome',             'parentId':[105]},
 1399:   { 'name':'neurofibromatosis type 1',             'parentId':[105]},
 1400:   { 'name':'von Hippel-Lindau syndrome',             'parentId':[105]},
 1401:   { 'name':'hereditary multiple melanoma',             'parentId':[105]},
 1402:   { 'name':'multiple endocrine neoplasia',             'parentId':[105]},
 1403:   { 'name':'hot flashes',             'parentId':[1433, 506]},
 1404:   { 'name':'loss, grief, and bereavement',             'parentId':[1236]},
 1405:   { 'name':'stage IV adult Hodgkin lymphoma',             'parentId':[2069]},
 1406:   { 'name':'multiple myeloma',             'parentId':[2038]},
 1407:   { 'name':'recurrent pancreatic cancer',             'parentId':[2032]},
 1408:   { 'name':'Wilms tumor and other childhood kidney tumors',             'parentId':[1890, 1513, 517]},
 1409:   { 'name':'recurrent adult Hodgkin lymphoma',             'parentId':[2069]},
 1410:   { 'name':'adult astrocytic tumors',             'parentId':[2017]},
 1414:   { 'name':'xeroderma pigmentosum',             'parentId':[105]},
 1415:   { 'name':'childhood rhabdomyosarcoma',             'parentId':[1890, 1736, 1513]},
 1416:   { 'name':'rectal cancer',             'parentId':[889, 501]},
 1417:   { 'name':'recurrent childhood rhabdomyosarcoma',             'parentId':[2046]},
 1418:   { 'name':'stage 0 rectal cancer',             'parentId':[2045]},
 1419:   { 'name':'untreated childhood visual pathway and hypothalamic glioma',             'parentId':[1447]},
 1420:   { 'name':'recurrent childhood visual pathway and hypothalamic glioma',             'parentId':[1447, 1497]},
 1421:   { 'name':'depression and suicide',             'parentId':[1236]},
 1422:   { 'name':'adult choroid plexus tumor',             'parentId':[2017]},
 1423:   { 'name':'stage I rectal cancer',             'parentId':[2045]},
 1424:   { 'name':'bronchoalveolar cell lung cancer',             'parentId':[1981]},
 1425:   { 'name':'refractory cytopenia with multilineage dysplasia',             'parentId':[2100]},
 1426:   { 'name':'substance abuse disorder',             'parentId':[1215]},
 1427:   { 'name':'osteosarcoma/malignant fibrous histiocytoma of bone',             'parentId':[518]},
 1428:   { 'name':'cancer genetics',             'parentId':[105]},
 1429:   { 'name':'Brenner tumor',             'parentId':[1989]},
 1431:   { 'name':'stage II rectal cancer',             'parentId':[2045]},
 1432:   { 'name':'islet cell carcinoma',             'parentId':[2123, 261]},
 1433:   { 'name':'menopausal symptoms',             'parentId':[1215]},
 1434:   { 'name':'previously treated childhood rhabdomyosarcoma',             'parentId':[2046]},
 1435:   { 'name':'localized Ewing sarcoma/peripheral primitive neuroectodermal tumor',             'parentId':[2060]},
 1436:   { 'name':'metastatic Ewing sarcoma/peripheral primitive neuroectodermal tumor',             'parentId':[2060]},
 1437:   { 'name':'recurrent Ewing sarcoma/peripheral primitive neuroectodermal tumor',             'parentId':[2060]},
 1438:   { 'name':'colon cancer',             'parentId':[889, 501]},
 1439:   { 'name':'stage III rectal cancer',             'parentId':[2045]},
 1440:   { 'name':'stage IV rectal cancer',             'parentId':[2045]},
 1441:   { 'name':'acute lymphocytic leukemia',             'parentId':[431]},
 1442:   { 'name':'acute myeloid leukemia',             'parentId':[2182]},
 1443:   { 'name':'recurrent colon cancer',             'parentId':[998]},
 1444:   { 'name':'childhood supratentorial ependymoma',             'parentId':[1449]},
 1445:   { 'name':'childhood craniopharyngioma',             'parentId':[2042]},
 1446:   { 'name':'recurrent rectal cancer',             'parentId':[2045]},
 1447:   { 'name':'childhood visual pathway and hypothalamic glioma',             'parentId':[2165]},
 1448:   { 'name':'stage IV anal cancer',             'parentId':[2025]},
 1449:   { 'name':'childhood ependymoma',             'parentId':[2042]},
 1450:   { 'name':'newly diagnosed childhood ependymoma',             'parentId':[1449]},
 1451:   { 'name':'recurrent childhood ependymoma',             'parentId':[1449, 1497]},
 1452:   { 'name':'trichothiodystrophy',             'parentId':[105]},
 1453:   { 'name':'recurrent anal cancer',             'parentId':[2025]},
 1454:   { 'name':'childhood extracranial germ cell tumor',             'parentId':[449]},
 1455:   { 'name':'childhood teratoma',             'parentId':[2131, 449]},
 1456:   { 'name':'childhood malignant testicular germ cell tumor',             'parentId':[2130, 1973]},
 1457:   { 'name':'childhood malignant ovarian germ cell tumor',             'parentId':[2009, 2130]},
 1458:   { 'name':'childhood extragonadal germ cell tumor',             'parentId':[449, 2095]},
 1459:   { 'name':'recurrent childhood malignant germ cell tumor',             'parentId':[449]},
 1460:   { 'name':'stage 0 anal cancer',             'parentId':[2025]},
 1461:   { 'name':'type 2 papillary renal cell carcinoma',             'parentId':[1569]},
 1462:   { 'name':'familial renal oncocytoma',             'parentId':[105]},
 1463:   { 'name':'Birt Hogg Dube syndrome',             'parentId':[105]},
 1464:   { 'name':'iron overload',             'parentId':[1215]},
 1465:   { 'name':'esophageal cancer',             'parentId':[889, 444]},
 1466:   { 'name':'stage I and II childhood liver cancer',             'parentId':[2043]},
 1467:   { 'name':'stage I colon cancer',             'parentId':[998]},
 1468:   { 'name':'stage 0 esophageal cancer',             'parentId':[2058]},
 1470:   { 'name':'stage I esophageal cancer',             'parentId':[2058]},
 1471:   { 'name':'adult synovial sarcoma',             'parentId':[1976]},
 1472:   { 'name':'childhood non-Hodgkin lymphoma',             'parentId':[436, 1513]},
 1473:   { 'name':'stage II esophageal cancer',             'parentId':[2058]},
 1474:   { 'name':'adult soft tissue sarcoma',             'parentId':[889, 1736]},
 1475:   { 'name':'stage I adult soft tissue sarcoma',             'parentId':[2023]},
 1476:   { 'name':'stage II adult soft tissue sarcoma',             'parentId':[2023]},
 1477:   { 'name':'stage III adult soft tissue sarcoma',             'parentId':[2023]},
 1478:   { 'name':'stage IV adult soft tissue sarcoma',             'parentId':[2023]},
 1479:   { 'name':'recurrent adult soft tissue sarcoma',             'parentId':[2023]},
 1480:   { 'name':'stage III esophageal cancer',             'parentId':[2058]},
 1481:   { 'name':'stage I renal cell cancer',             'parentId':[2016]},
 1482:   { 'name':'stage II renal cell cancer',             'parentId':[2016]},
 1483:   { 'name':'stage III renal cell cancer',             'parentId':[2016]},
 1484:   { 'name':'stage IV esophageal cancer',             'parentId':[2058]},
 1485:   { 'name':'stage IV renal cell cancer',             'parentId':[2016]},
 1486:   { 'name':'recurrent renal cell cancer',             'parentId':[2016]},
 1487:   { 'name':'localized parathyroid cancer',             'parentId':[2034]},
 1488:   { 'name':'metastatic parathyroid cancer',             'parentId':[2034]},
 1489:   { 'name':'recurrent parathyroid cancer',             'parentId':[2034]},
 1490:   { 'name':'stage IIIA anal cancer',             'parentId':[1101]},
 1491:   { 'name':'ovarian epithelial cancer',             'parentId':[1745]},
 1492:   { 'name':'stage I ovarian epithelial cancer',             'parentId':[2030]},
 1493:   { 'name':'stage II ovarian epithelial cancer',             'parentId':[2030]},
 1494:   { 'name':'stage III ovarian epithelial cancer',             'parentId':[2030]},
 1495:   { 'name':'stage IV ovarian epithelial cancer',             'parentId':[2030]},
 1496:   { 'name':'recurrent ovarian epithelial cancer',             'parentId':[2030]},
 1497:   { 'name':'recurrent childhood brain tumor',             'parentId':[2042]},
 1498:   { 'name':'localized resectable neuroblastoma',             'parentId':[2026]},
 1499:   { 'name':'regional neuroblastoma',             'parentId':[2026]},
 1500:   { 'name':'stage IIIB anal cancer',             'parentId':[1101]},
 1501:   { 'name':'disseminated neuroblastoma',             'parentId':[2026]},
 1502:   { 'name':'stage 4S neuroblastoma',             'parentId':[2026]},
 1503:   { 'name':'recurrent neuroblastoma',             'parentId':[2026]},
 1504:   { 'name':'childhood liver cancer',             'parentId':[1890, 445, 1513]},
 1505:   { 'name':'stage I childhood liver cancer',             'parentId':[1466]},
 1506:   { 'name':'stage II childhood liver cancer',             'parentId':[1466]},
 1507:   { 'name':'stage III childhood liver cancer',             'parentId':[2043]},
 1508:   { 'name':'stage IV childhood liver cancer',             'parentId':[2043]},
 1509:   { 'name':'recurrent childhood liver cancer',             'parentId':[2043]},
 1510:   { 'name':'adenocarcinoma of the stomach',             'parentId':[1991]},
 1511:   { 'name':'recurrent esophageal cancer',             'parentId':[2058]},
 1512:   { 'name':'childhood hepatoblastoma',             'parentId':[1994]},
 1513:   { 'name':'childhood cancer',             'parentId':[1185]},
 1514:   { 'name':'cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[1560]},
 1515:   { 'name':'stage I cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[2055]},
 1516:   { 'name':'retinoblastoma',             'parentId':[1890, 1891, 1513]},
 1517:   { 'name':'stage I Wilms tumor',             'parentId':[2067]},
 1518:   { 'name':'stage II Wilms tumor',             'parentId':[2067]},
 1519:   { 'name':'stage III Wilms tumor',             'parentId':[2067]},
 1520:   { 'name':'stage IV Wilms tumor',             'parentId':[2067]},
 1521:   { 'name':'stage V Wilms tumor',             'parentId':[2067]},
 1522:   { 'name':'mast cell leukemia',             'parentId':[2295, 431]},
 1523:   { 'name':'long-term effects secondary to cancer therapy in adults',             'parentId':[1708]},
 1524:   { 'name':'cardiovascular complications',             'parentId':[1215]},
 1525:   { 'name':'obesity',             'parentId':[276]},
 1526:   { 'name':'poor performance status',             'parentId':[1215]},
 1527:   { 'name':'fecal incontinence',             'parentId':[108]},
 1528:   { 'name':'urinary complications',             'parentId':[1215]},
 1529:   { 'name':'urinary incontinence',             'parentId':[1528]},
 1530:   { 'name':'neurofibromatosis type 2',             'parentId':[105]},
 1531:   { 'name':'palmar-plantar erythrodysesthesia',             'parentId':[1215]},
 1532:   { 'name':'dermatofibrosarcoma protuberans',             'parentId':[1976, 2006]},
 1535:   { 'name':'tobacco use disorder',             'parentId':[1215]},
 1537:   { 'name':'adult nasal type extranodal NK/T-cell lymphoma',             'parentId':[2180, 1387, 1392]},
 1538:   { 'name':'childhood nasal type extranodal NK/T-cell lymphoma',             'parentId':[2044]},
 1539:   { 'name':'hypothyroidism',             'parentId':[1215]},
 1540:   { 'name':'hyperthyroidism',             'parentId':[1215]},
 1541:   { 'name':'mucositis',             'parentId':[219, 1707]},
 1542:   { 'name':'dermatologic complications',             'parentId':[1215]},
 1543:   { 'name':'secondary myelofibrosis',             'parentId':[1215]},
 1544:   { 'name':'hemoptysis',             'parentId':[1215]},
 1545:   { 'name':'dysphagia',             'parentId':[108]},
 1546:   { 'name':'acute myeloid leukemia/transient myeloproliferative disorder',             'parentId':[244]},
 1547:   { 'name':'seizure',             'parentId':[1215]},
 1548:   { 'name':'nevoid basal cell carcinoma syndrome',             'parentId':[2119, 105]},
 1549:   { 'name':'renal failure',             'parentId':[1528]},
 1550:   { 'name':'xerostomia',             'parentId':[219, 1707]},
 1551:   { 'name':'malignant giant cell tumor of bone',             'parentId':[518]},
 1552:   { 'name':'adult subependymal giant cell astrocytoma',             'parentId':[1410]},
 1553:   { 'name':'childhood subependymal giant cell astrocytoma',             'parentId':[2165]},
 1554:   { 'name':'tuberous sclerosis complex',             'parentId':[105]},
 1555:   { 'name':'adult desmoplastic small round cell tumor',             'parentId':[1976]},
 1556:   { 'name':'hepatic complications',             'parentId':[108]},
 1557:   { 'name':'paraneoplastic syndrome',             'parentId':[1215]},
 1558:   { 'name':'dehydration',             'parentId':[1215]},
 1559:   { 'name':'musculoskeletal complications',             'parentId':[1215]},
 1560:   { 'name':'cutaneous lymphoma',             'parentId':[1241, 1889, 436]},
 1561:   { 'name':'cutaneous B-cell non-Hodgkin lymphoma',             'parentId':[1560]},
 1562:   { 'name':'therapy-related toxicity',             'parentId':[1215]},
 1563:   { 'name':'chemotherapeutic agent toxicity',             'parentId':[1562]},
 1564:   { 'name':'ototoxicity',             'parentId':[1218]},
 1567:   { 'name':'conjunctival melanoma',             'parentId':[2120]},
 1569:   { 'name':'papillary renal cell carcinoma',             'parentId':[1975]},
 1570:   { 'name':'hereditary papillary renal cell carcinoma',             'parentId':[105]},
 1571:   { 'name':'hereditary clear cell renal cell carcinoma',             'parentId':[1854]},
 1572:   { 'name':'type 1 papillary renal cell carcinoma',             'parentId':[1569]},
 1573:   { 'name':'Ewing sarcoma',             'parentId':[1964]},
 1574:   { 'name':'Ewing sarcoma of bone',             'parentId':[1573]},
 1575:   { 'name':'extraosseous Ewing sarcoma',             'parentId':[862, 1573]},
 1576:   { 'name':'peripheral primitive neuroectodermal tumor',             'parentId':[1964]},
 1577:   { 'name':'Askin tumor',             'parentId':[1576]},
 1578:   { 'name':'neuroendocrine tumor',             'parentId':[1888]},
 1585:   { 'name':'germ cell tumor',             'parentId':[2116]},
 1586:   { 'name':'ovarian yolk sac tumor',             'parentId':[2009]},
 1590:   { 'name':'adult teratoma',             'parentId':[2131]},
 1591:   { 'name':'testicular immature teratoma',             'parentId':[691]},
 1592:   { 'name':'testicular mature teratoma',             'parentId':[691]},
 1593:   { 'name':'sickle cell disease',             'parentId':[105]},
 1594:   { 'name':'childhood myelodysplastic syndromes',             'parentId':[2096, 244]},
 1595:   { 'name':'eccrine carcinoma of the skin',             'parentId':[2050]},
 1596:   { 'name':'childhood favorable prognosis Hodgkin lymphoma',             'parentId':[2004]},
 1598:   { 'name':'adult favorable prognosis Hodgkin lymphoma',             'parentId':[2001]},
 1599:   { 'name':'adult unfavorable prognosis Hodgkin lymphoma',             'parentId':[2001]},
 1600:   { 'name':'hyperglycemia',             'parentId':[1215]},
 1601:   { 'name':'adult pineal gland astrocytoma',             'parentId':[1410]},
 1602:   { 'name':'benign teratoma',             'parentId':[2131]},
 1605:   { 'name':'stage I extragonadal non-seminomatous germ cell tumor',             'parentId':[2135]},
 1607:   { 'name':'stage II extragonadal non-seminomatous germ cell tumor',             'parentId':[2135]},
 1608:   { 'name':'stage III extragonadal non-seminomatous germ cell tumor',             'parentId':[2135]},
 1609:   { 'name':'stage IV extragonadal non-seminomatous germ cell tumor',             'parentId':[2135]},
 1610:   { 'name':'recurrent extragonadal non-seminomatous germ cell tumor',             'parentId':[2135]},
 1613:   { 'name':'stage I extragonadal seminoma',             'parentId':[2137]},
 1614:   { 'name':'stage II extragonadal seminoma',             'parentId':[2137]},
 1615:   { 'name':'stage III extragonadal seminoma',             'parentId':[2137]},
 1616:   { 'name':'stage IV extragonadal seminoma',             'parentId':[2137]},
 1617:   { 'name':'recurrent extragonadal seminoma',             'parentId':[2137]},
 1619:   { 'name':'recurrent extragonadal germ cell tumor',             'parentId':[2138]},
 1620:   { 'name':'childhood pineoblastoma',             'parentId':[1670]},
 1621:   { 'name':'recurrent childhood pineoblastoma',             'parentId':[1620, 1497]},
 1622:   { 'name':'untreated childhood pineoblastoma',             'parentId':[1620]},
 1624:   { 'name':'stage I borderline ovarian surface epithelial-stromal tumor',             'parentId':[2139]},
 1625:   { 'name':'stage II borderline ovarian surface epithelial-stromal tumor',             'parentId':[2139]},
 1626:   { 'name':'stage III borderline ovarian surface epithelial-stromal tumor',             'parentId':[2139]},
 1627:   { 'name':'stage IV borderline ovarian surface epithelial-stromal tumor',             'parentId':[2139]},
 1628:   { 'name':'recurrent borderline ovarian surface epithelial-stromal tumor',             'parentId':[2139]},
 1629:   { 'name':'childhood nodular lymphocyte predominant Hodgkin lymphoma',             'parentId':[2004]},
 1630:   { 'name':'adult nodular lymphocyte predominant Hodgkin lymphoma',             'parentId':[2001]},
 1631:   { 'name':'congenital mesoblastic nephroma',             'parentId':[1890, 517]},
 1632:   { 'name':'childhood renal cell carcinoma',             'parentId':[2016]},
 1633:   { 'name':'stage 0 penile cancer',             'parentId':[2035]},
 1634:   { 'name':'recurrent childhood non-Hodgkin lymphoma',             'parentId':[2044]},
 1635:   { 'name':'alopecia',             'parentId':[1542]},
 1636:   { 'name':'metastatic intraocular melanoma',             'parentId':[2011]},
 1638:   { 'name':'recurrent adult malignant fibrous histiocytoma of bone',             'parentId':[2140]},
 1639:   { 'name':'localized adult malignant fibrous histiocytoma of bone',             'parentId':[2140]},
 1640:   { 'name':'metastatic adult malignant fibrous histiocytoma of bone',             'parentId':[2140]},
 1642:   { 'name':'metastatic childhood malignant fibrous histiocytoma of bone',             'parentId':[2141]},
 1643:   { 'name':'recurrent childhood malignant fibrous histiocytoma of bone',             'parentId':[2141]},
 1644:   { 'name':'localized childhood malignant fibrous histiocytoma of bone',             'parentId':[2141]},
 1645:   { 'name':'childhood anaplastic large cell lymphoma',             'parentId':[2044]},
 1646:   { 'name':'stage I childhood anaplastic large cell lymphoma',             'parentId':[1645]},
 1647:   { 'name':'stage II childhood anaplastic large cell lymphoma',             'parentId':[1645]},
 1648:   { 'name':'stage III childhood anaplastic large cell lymphoma',             'parentId':[1645]},
 1649:   { 'name':'stage IV childhood anaplastic large cell lymphoma',             'parentId':[1645]},
 1650:   { 'name':'recurrent childhood anaplastic large cell lymphoma',             'parentId':[1634, 1645]},
 1651:   { 'name':'Philadelphia chromosome positive childhood precursor acute lymphoblastic leukemia',             'parentId':[1992]},
 1652:   { 'name':'Philadelphia chromosome positive adult precursor acute lymphoblastic leukemia',             'parentId':[1966]},
 1654:   { 'name':'adult spinal cord neoplasm',             'parentId':[2142]},
 1658:   { 'name':'childhood grade I meningioma',             'parentId':[1183]},
 1659:   { 'name':'childhood grade II meningioma',             'parentId':[1183]},
 1660:   { 'name':'childhood grade III meningioma',             'parentId':[1183]},
 1662:   { 'name':'cystic nephroma',             'parentId':[517]},
 1664:   { 'name':'recurrent adult spinal cord neoplasm',             'parentId':[1654]},
 1665:   { 'name':'recurrent childhood spinal cord neoplasm',             'parentId':[281]},
 1666:   { 'name':'childhood embryonal tumor',             'parentId':[2042]},
 1667:   { 'name':'childhood ependymoblastoma',             'parentId':[1666]},
 1668:   { 'name':'childhood high-grade cerebellar astrocytoma',             'parentId':[757]},
 1669:   { 'name':'childhood low-grade cerebellar astrocytoma',             'parentId':[757]},
 1670:   { 'name':'childhood pineal parenchymal tumor',             'parentId':[2042]},
 1671:   { 'name':'vascular access device complications',             'parentId':[1215]},
 1672:   { 'name':'male erectile disorder',             'parentId':[1237]},
 1673:   { 'name':'lymphopenia',             'parentId':[1238]},
 1674:   { 'name':'bronchiolitis obliterans',             'parentId':[1245]},
 1675:   { 'name':'spinal cord compression',             'parentId':[1215]},
 1676:   { 'name':'dyspnea',             'parentId':[1215]},
 1677:   { 'name':'renal toxicity',             'parentId':[1218, 1528]},
 1678:   { 'name':'solar radiation-related skin melanoma',             'parentId':[1979]},
 1679:   { 'name':'mucosal melanoma',             'parentId':[1979]},
 1680:   { 'name':'hereditary leiomyomatosis and renal cell cancer',             'parentId':[105]},
 1681:   { 'name':'uterine corpus cancer',             'parentId':[439]},
 1683:   { 'name':'peritoneal carcinomatosis',             'parentId':[1170]},
 1685:   { 'name':'metastatic carcinoma of unknown primary',             'parentId':[2098]},
 1686:   { 'name':'adenocarcinoma of the gastroesophageal junction',             'parentId':[444]},
 1687:   { 'name':'Cowden syndrome',             'parentId':[2119, 105]},
 1688:   { 'name':'childhood central nervous system choriocarcinoma',             'parentId':[243]},
 1690:   { 'name':'recurrent childhood central nervous system embryonal tumor',             'parentId':[2150]},
 1691:   { 'name':'childhood central nervous system yolk sac tumor',             'parentId':[243]},
 1692:   { 'name':'childhood central nervous system teratoma',             'parentId':[243]},
 1693:   { 'name':'childhood central nervous system germinoma',             'parentId':[243]},
 1694:   { 'name':'childhood central nervous system mixed germ cell tumor',             'parentId':[243]},
 1695:   { 'name':'childhood medulloepithelioma',             'parentId':[1666]},
 1696:   { 'name':'untreated childhood cerebral astrocytoma',             'parentId':[1318]},
 1697:   { 'name':'recurrent childhood subependymal giant cell astrocytoma',             'parentId':[1553, 1497]},
 1698:   { 'name':'untreated childhood subependymal giant cell astrocytoma',             'parentId':[1553]},
 1699:   { 'name':'BRCA1 mutation carrier',             'parentId':[105]},
 1700:   { 'name':'BRCA2 mutation carrier',             'parentId':[105]},
 1701:   { 'name':'noncutaneous extranodal lymphoma',             'parentId':[436]},
 1702:   { 'name':'hepatosplenic T-cell lymphoma',             'parentId':[1701]},
 1703:   { 'name':'dysgeusia',             'parentId':[1215]},
 1704:   { 'name':'acute myeloid leukemia with multilineage dysplasia following myelodysplastic syndrome',             'parentId':[1131]},
 1706:   { 'name':'lymphoproliferative disorder',             'parentId':[511]},
 1707:   { 'name':'oral complications of chemotherapy',             'parentId':[769]},
 1708:   { 'name':'long-term effects of cancer treatment',             'parentId':[1215]},
 1709:   { 'name':'monoclonal immunoglobulin deposition disease',             'parentId':[2038]},
 1710:   { 'name':'light chain deposition disease',             'parentId':[1709]},
 1711:   { 'name':'carcinoid syndrome',             'parentId':[1557]},
 1712:   { 'name':'childhood mixed glioma',             'parentId':[2378]},
 1713:   { 'name':'transfusional hemosiderosis',             'parentId':[1464]},
 1714:   { 'name':'hereditary intestinal polyposis syndrome',             'parentId':[105]},
 1715:   { 'name':'malignant fungating wound',             'parentId':[1215]},
 1716:   { 'name':'ophthalmologic complications',             'parentId':[1215]},
 1717:   { 'name':'cancer survivor',             'parentId':[1215]},
 1719:   { 'name':'periampullary adenocarcinoma',             'parentId':[444]},
 1720:   { 'name':'fistula',             'parentId':[1215]},
 1721:   { 'name':'spinal cord metastases',             'parentId':[2097]},
 1724:   { 'name':'hormone receptor/growth factor receptor-positive breast cancer',             'parentId':[398]},
 1725:   { 'name':'estrogen receptor-negative breast cancer',             'parentId':[2154]},
 1726:   { 'name':'estrogen receptor-positive breast cancer',             'parentId':[1724]},
 1727:   { 'name':'progesterone receptor-negative breast cancer',             'parentId':[2154]},
 1728:   { 'name':'progesterone receptor-positive breast cancer',             'parentId':[1724]},
 1729:   { 'name':'HER2-negative breast cancer',             'parentId':[2154]},
 1730:   { 'name':'HER2-positive breast cancer',             'parentId':[1724]},
 1731:   { 'name':'triple-negative breast cancer',             'parentId':[2154]},
 1736:   { 'name':'soft tissue sarcoma',             'parentId':[2158]},
 1738:   { 'name':'peripheral T-cell lymphoma',             'parentId':[436]},
 1741:   { 'name':'bacterial infection',             'parentId':[1227]},
 1742:   { 'name':'fungal infection',             'parentId':[1227]},
 1743:   { 'name':'viral infection',             'parentId':[1227]},
 1745:   { 'name':'ovarian neoplasm',             'parentId':[439, 889]},
 1746:   { 'name':'cholestasis',             'parentId':[1215]},
 1747:   { 'name':'metabolic syndrome',             'parentId':[1215]},
 1748:   { 'name':'spinal bone metastases',             'parentId':[2097]},
 1749:   { 'name':'myelodysplastic syndrome with isolated del(5q)',             'parentId':[2163]},
 1751:   { 'name':'adult acute myeloid leukemia with del(5q)',             'parentId':[1879]},
 1753:   { 'name':'hormone-resistant prostate cancer',             'parentId':[351]},
 1754:   { 'name':'onycholysis',             'parentId':[1215]},
 1755:   { 'name':'adenomatous polyp',             'parentId':[1864, 1778]},
 1756:   { 'name':'gonadotroph adenoma',             'parentId':[2037]},
 1757:   { 'name':'radiation retinopathy',             'parentId':[1251]},
 1758:   { 'name':'smoldering multiple myeloma',             'parentId':[1406]},
 1759:   { 'name':'arthralgia',             'parentId':[1559]},
 1760:   { 'name':'methemoglobinemia',             'parentId':[1215]},
 1761:   { 'name':'tongue cancer',             'parentId':[440]},
 1762:   { 'name':'Bloom syndrome',             'parentId':[105]},
 1763:   { 'name':'familial carcinoid syndrome',             'parentId':[105]},
 1764:   { 'name':'Carney complex',             'parentId':[105]},
 1765:   { 'name':'tylosis with esophageal cancer',             'parentId':[105]},
 1766:   { 'name':'hereditary diffuse gastric cancer',             'parentId':[105]},
 1767:   { 'name':'hereditary pancreatic cancer',             'parentId':[105]},
 1768:   { 'name':'osteochondromatosis',             'parentId':[105]},
 1769:   { 'name':'hereditary paraganglioma',             'parentId':[105]},
 1770:   { 'name':'hereditary prostate cancer',             'parentId':[105]},
 1771:   { 'name':'Rothmund-Thomson syndrome',             'parentId':[105]},
 1772:   { 'name':'familial testicular germ cell tumor',             'parentId':[105]},
 1773:   { 'name':'Werner syndrome',             'parentId':[105]},
 1774:   { 'name':'multiple endocrine neoplasia type 1',             'parentId':[105]},
 1775:   { 'name':'multiple endocrine neoplasia type 2',             'parentId':[105]},
 1776:   { 'name':'systemic scleroderma',             'parentId':[1823]},
 1777:   { 'name':'central nervous system metastases',             'parentId':[2097]},
 1778:   { 'name':'precancerous condition',             'parentId':[101]},
 1779:   { 'name':'high-grade squamous intraepithelial lesion',             'parentId':[1778]},
 1780:   { 'name':'recurrent grade I lymphomatoid granulomatosis',             'parentId':[1783]},
 1781:   { 'name':'recurrent grade II lymphomatoid granulomatosis',             'parentId':[1783]},
 1782:   { 'name':'actinic keratosis',             'parentId':[1778]},
 1783:   { 'name':'lymphomatoid granulomatosis (grades I and II)',             'parentId':[1778]},
 1784:   { 'name':'grade I lymphomatoid granulomatosis',             'parentId':[1783]},
 1785:   { 'name':'grade II lymphomatoid granulomatosis',             'parentId':[1783]},
 1786:   { 'name':'low-grade squamous intraepithelial lesion',             'parentId':[1865, 1778]},
 1787:   { 'name':'atypical squamous cells of undetermined significance',             'parentId':[1778, 1865]},
 1788:   { 'name':'monoclonal gammopathy of undetermined significance',             'parentId':[1865, 1778]},
 1789:   { 'name':'unicentric Castleman disease',             'parentId':[1864]},
 1790:   { 'name':'lung papilloma',             'parentId':[1865, 1778]},
 1791:   { 'name':'oral leukoplakia',             'parentId':[1778]},
 1792:   { 'name':'ataxia-telangiectasia',             'parentId':[1818]},
 1793:   { 'name':'squamous lung dysplasia',             'parentId':[1778]},
 1794:   { 'name':'angiomyolipoma',             'parentId':[1865]},
 1795:   { 'name':'paroxysmal nocturnal hemoglobinuria',             'parentId':[1215]},
 1796:   { 'name':'plexiform neurofibroma',             'parentId':[1778]},
 1797:   { 'name':'hemophagocytic lymphohistiocytosis',             'parentId':[1864]},
 1798:   { 'name':'aplastic anemia',             'parentId':[1865]},
 1799:   { 'name':'Barrett esophagus',             'parentId':[1865, 1778]},
 1800:   { 'name':'benign prostatic hyperplasia',             'parentId':[1865]},
 1801:   { 'name':'spinal cord neurofibroma',             'parentId':[1865]},
 1802:   { 'name':'benign giant cell tumor of bone',             'parentId':[1865]},
 1803:   { 'name':'lymphangioleiomyomatosis',             'parentId':[1864]},
 1805:   { 'name':'healthy, no evidence of disease',             'parentId':[2169]},
 1806:   { 'name':'health status unknown',             'parentId':[2169]},
 1807:   { 'name':'giant hypertrophic gastritis',             'parentId':[1778]},
 1808:   { 'name':'epidermolysis bullosa',             'parentId':[105]},
 1809:   { 'name':'idiopathic thrombocytopenic purpura',             'parentId':[1810]},
 1810:   { 'name':'thrombocytopenic purpura',             'parentId':[1864]},
 1811:   { 'name':'cutaneous mastocytosis',             'parentId':[2295]},
 1812:   { 'name':'urticaria pigmentosa/maculopapular cutaneous mastocytosis',             'parentId':[1811]},
 1813:   { 'name':'human papilloma virus infection',             'parentId':[1778, 1864]},
 1814:   { 'name':'diffuse hyperplastic perilobar nephroblastomatosis',             'parentId':[1778, 1864]},
 1815:   { 'name':'HTLV-1 infection',             'parentId':[1864]},
 1816:   { 'name':'hepatitis B infection',             'parentId':[1864]},
 1817:   { 'name':'HIV infection',             'parentId':[1864]},
 1818:   { 'name':'congenital combined immunodeficiency',             'parentId':[1822, 105]},
 1819:   { 'name':'Wiskott-Aldrich syndrome',             'parentId':[1818]},
 1820:   { 'name':'severe combined immunodeficiency',             'parentId':[1818]},
 1821:   { 'name':'Epstein-Barr virus infection',             'parentId':[1864]},
 1822:   { 'name':'immune system disorder',             'parentId':[1864]},
 1823:   { 'name':'autoimmune disorder',             'parentId':[1822]},
 1824:   { 'name':'localized scleroderma',             'parentId':[1823]},
 1825:   { 'name':'atypical ductal breast hyperplasia',             'parentId':[1778, 1865]},
 1826:   { 'name':'hepatitis C infection',             'parentId':[1864]},
 1827:   { 'name':'benign respiratory tract neoplasm',             'parentId':[1865]},
 1828:   { 'name':'cytomegalovirus infection',             'parentId':[1864]},
 1829:   { 'name':'recurrent respiratory papillomatosis',             'parentId':[1865, 1778]},
 1830:   { 'name':'systemic lupus erythematosus',             'parentId':[1823]},
 1831:   { 'name':'monoclonal B-cell lymphocytosis',             'parentId':[1778]},
 1832:   { 'name':'aggressive systemic mastocytosis',             'parentId':[1778]},
 1833:   { 'name':'indolent systemic mastocytosis',             'parentId':[1864]},
 1834:   { 'name':'smoldering systemic mastocytosis',             'parentId':[1833]},
 1835:   { 'name':'cervical intraepithelial neoplasia',             'parentId':[1778, 1865]},
 1836:   { 'name':'cervical intraepithelial neoplasia grade 3',             'parentId':[1835]},
 1837:   { 'name':'cervical intraepithelial neoplasia grade 2',             'parentId':[1835]},
 1838:   { 'name':'cervical intraepithelial neoplasia grade 1',             'parentId':[1835]},
 1839:   { 'name':'prostatic intraepithelial neoplasia',             'parentId':[1778]},
 1840:   { 'name':'high grade prostatic intraepithelial neoplasia',             'parentId':[1839]},
 1841:   { 'name':'low grade prostatic intraepithelial neoplasia',             'parentId':[1865, 1839]},
 1842:   { 'name':'atypical endometrial hyperplasia',             'parentId':[1778, 1865]},
 1843:   { 'name':'ductal intraepithelial neoplasia',             'parentId':[1778]},
 1844:   { 'name':'Lambert-Eaton myasthenic syndrome',             'parentId':[1215]},
 1845:   { 'name':'actinic cheilitis',             'parentId':[1778]},
 1846:   { 'name':'MonoMAC syndrome',             'parentId':[1864]},
 1847:   { 'name':'atypical glandular cell of undetermined significance',             'parentId':[1778, 1865]},
 1848:   { 'name':'atypical endocervical glandular cell of undetermined significance',             'parentId':[1778, 1865]},
 1849:   { 'name':'atypical small acinar proliferation of the prostate',             'parentId':[1865, 1778]},
 1850:   { 'name':'hereditary breast/ovarian cancer - BRCA1',             'parentId':[105]},
 1851:   { 'name':'hereditary breast/ovarian cancer - BRCA2',             'parentId':[105]},
 1852:   { 'name':'familial chordoma',             'parentId':[105]},
 1853:   { 'name':'familial Hodgkin lymphoma',             'parentId':[105]},
 1854:   { 'name':'hereditary renal cell cancer',             'parentId':[105]},
 1855:   { 'name':'acoustic schwannoma',             'parentId':[302]},
 1856:   { 'name':'acute lung injury',             'parentId':[1245]},
 1858:   { 'name':'testicular lymphoma',             'parentId':[1897, 1392]},
 1859:   { 'name':'chlamydia mucosa-associated lymphoid tissue (MALT) lymphoma of the ocular adnexae',             'parentId':[152, 1891]},
 1860:   { 'name':'adenovirus infection',             'parentId':[1227]},
 1861:   { 'name':'hypercalcemia',             'parentId':[1215]},
 1862:   { 'name':'pancreatic cyst',             'parentId':[1215]},
 1863:   { 'name':'stage IV neuroendocrine carcinoma of the skin',             'parentId':[1882]},
 1864:   { 'name':'nonneoplastic condition',             'parentId':[101]},
 1865:   { 'name':'nonmalignant neoplasm',             'parentId':[1873]},
 1867:   { 'name':'infantile hemangioma',             'parentId':[1868]},
 1868:   { 'name':'hemangioma',             'parentId':[1870]},
 1869:   { 'name':'cardiovascular neoplasm',             'parentId':[2116, 1865]},
 1870:   { 'name':'nonmalignant cardiovascular neoplasm',             'parentId':[1865]},
 1871:   { 'name':'neuropathy',             'parentId':[1215]},
 1872:   { 'name':'peripheral neuropathy',             'parentId':[1871]},
 1873:   { 'name':'Neoplasm diagnosis',             'parentId':[101]},
 1874:   { 'name':'cutaneous leiomyoma',             'parentId':[127]},
 1875:   { 'name':'uterine leiomyomata',             'parentId':[127]},
 1876:   { 'name':'type B thymoma',             'parentId':[1968]},
 1877:   { 'name':'combined thymoma',             'parentId':[1968]},
 1878:   { 'name':'adult meningeal tumor',             'parentId':[2017]},
 1879:   { 'name':'cytogenetic abnormalities, adult acute myeloid leukemia',             'parentId':[193]},
 1880:   { 'name':'penile cancer',             'parentId':[1897, 889]},
 1881:   { 'name':'neuroendocrine carcinoma of the skin',             'parentId':[261, 1889]},
 1882:   { 'name':'stage, neuroendocrine carcinoma of the skin',             'parentId':[1881]},
 1883:   { 'name':'stage, mycosis fungoides/Sezary syndrome',             'parentId':[247]},
 1884:   { 'name':'malignant testicular germ cell tumor',             'parentId':[2129, 1890, 1897, 889]},
 1885:   { 'name':'childhood small noncleaved cell lymphoma',             'parentId':[2044]},
 1886:   { 'name':'stage, uterine sarcoma',             'parentId':[896]},
 1887:   { 'name':'chronic leukemia',             'parentId':[1196]},
 1888:   { 'name':'endocrine cancer',             'parentId':[2116]},
 1889:   { 'name':'skin tumor',             'parentId':[2116]},
 1890:   { 'name':'childhood solid tumor',             'parentId':[890]},
 1891:   { 'name':'eye cancer',             'parentId':[2116]},
 1892:   { 'name':'musculoskeletal cancer',             'parentId':[2116]},
 1893:   { 'name':'stage, chronic myeloproliferative disorders',             'parentId':[492]},
 1894:   { 'name':'childhood acute monoblastic leukemia and acute monocytic leukemia (M5)',             'parentId':[1993]},
 1895:   { 'name':'stage, laryngeal cancer',             'parentId':[441]},
 1896:   { 'name':'unclassified/other cancer',             'parentId':[2116]},
 1897:   { 'name':'male reproductive cancer',             'parentId':[2116]},
 1898:   { 'name':'childhood lymphoblastic lymphoma',             'parentId':[2044]},
 1899:   { 'name':'mediastinal cancer',             'parentId':[487]},
 1900:   { 'name':'pheochromocytoma',             'parentId':[889, 2155]},
 1901:   { 'name':'ductal breast carcinoma',             'parentId':[1961]},
 1902:   { 'name':'lobular breast carcinoma',             'parentId':[1961]},
 1903:   { 'name':'Paget disease of the breast',             'parentId':[1961]},
 1904:   { 'name':'Paget disease of the breast with intraductal carcinoma',             'parentId':[1903]},
 1905:   { 'name':'lymphocyte-like type small cell lung cancer',             'parentId':[1999]},
 1906:   { 'name':'intermediate type small cell lung cancer',             'parentId':[1999]},
 1907:   { 'name':'polygonal type small cell lung cancer',             'parentId':[1906]},
 1908:   { 'name':'fusiform type small cell lung cancer',             'parentId':[1906]},
 1909:   { 'name':'combined type small cell lung cancer',             'parentId':[1999]},
 1910:   { 'name':'differentiated adrenocortical carcinoma',             'parentId':[1962]},
 1911:   { 'name':'anaplastic adrenocortical carcinoma',             'parentId':[1962]},
 1912:   { 'name':'non-T, non-B childhood acute lymphoblastic leukemia',             'parentId':[1992]},
 1913:   { 'name':'non-T, non-B adult acute lymphoblastic leukemia',             'parentId':[1966]},
 1914:   { 'name':'non-T, non-B, cALLa positive adult acute lymphoblastic leukemia',             'parentId':[1913]},
 1915:   { 'name':'non-T, non-B, cALLa positive, pre-B adult acute lymphoblastic leukemia',             'parentId':[1913]},
 1916:   { 'name':'non-T, non-B, cALLa negative adult acute lymphoblastic leukemia',             'parentId':[1913]},
 1917:   { 'name':'TdT negative adult acute lymphoblastic leukemia',             'parentId':[1966]},
 1918:   { 'name':'fibrosarcomatous osteosarcoma',             'parentId':[1985]},
 1919:   { 'name':'chondrosarcomatous osteosarcoma',             'parentId':[1985]},
 1920:   { 'name':'osteoblastic osteosarcoma',             'parentId':[1985]},
 1921:   { 'name':'telangiectatic osteosarcoma',             'parentId':[1985]},
 1922:   { 'name':'mixed osteosarcoma',             'parentId':[1985]},
 1923:   { 'name':'anaplastic osteosarcoma',             'parentId':[1985]},
 1924:   { 'name':'epithelial predominant Wilms tumor',             'parentId':[1967]},
 1925:   { 'name':'blastema predominant Wilms tumor',             'parentId':[1967]},
 1926:   { 'name':'stromal predominant Wilms tumor',             'parentId':[1967]},
 1927:   { 'name':'mixed cell type Wilms tumor',             'parentId':[1967]},
 1928:   { 'name':'lentigo maligna malignant melanoma',             'parentId':[1979]},
 1929:   { 'name':'superficial spreading malignant melanoma',             'parentId':[1979]},
 1930:   { 'name':'nodular malignant melanoma',             'parentId':[1979]},
 1931:   { 'name':'scirrhous carcinoma of the colon',             'parentId':[794]},
 1932:   { 'name':'carcinoma simplex of the colon',             'parentId':[794]},
 1933:   { 'name':'anaplastic carcinoma of the gallbladder',             'parentId':[1963]},
 1934:   { 'name':'adenocarcinoma with squamous metaplasia of the gallbladder',             'parentId':[1963]},
 1935:   { 'name':'scirrhous carcinoma of the rectum',             'parentId':[1998]},
 1936:   { 'name':'carcinoma simplex of the rectum',             'parentId':[1998]},
 1937:   { 'name':'squamous cell carcinoma of the gallbladder',             'parentId':[1963]},
 1938:   { 'name':'ovarian mucinous cystadenoma with proliferating activity',             'parentId':[2104]},
 1939:   { 'name':'vaginal squamous cell carcinoma',             'parentId':[1982]},
 1940:   { 'name':'vaginal adenocarcinoma',             'parentId':[1982]},
 1941:   { 'name':'vaginal clear cell adenocarcinoma',             'parentId':[1982]},
 1942:   { 'name':'chorioadenoma destruens',             'parentId':[2002]},
 1943:   { 'name':'uterine choriocarcinoma',             'parentId':[2002]},
 1944:   { 'name':'spindle cell intraocular melanoma',             'parentId':[2000]},
 1945:   { 'name':'spindle cell A type intraocular melanoma',             'parentId':[1944]},
 1946:   { 'name':'spindle cell B type intraocular melanoma',             'parentId':[1944]},
 1947:   { 'name':'nonspindle cell intraocular melanoma',             'parentId':[2000]},
 1948:   { 'name':'epithelioid cell intraocular melanoma',             'parentId':[1947]},
 1949:   { 'name':'mixed cell type intraocular melanoma',             'parentId':[1947]},
 1950:   { 'name':'necrotic intraocular melanoma',             'parentId':[1947]},
 1951:   { 'name':'parathyroid chief cell adenoma',             'parentId':[1987]},
 1952:   { 'name':'parathyroid transitional clear cell adenoma',             'parentId':[1987]},
 1953:   { 'name':'parathyroid mixed cell type adenoma',             'parentId':[1987]},
 1954:   { 'name':'penile squamous cell carcinoma',             'parentId':[1988]},
 1955:   { 'name':'type B3 thymoma',             'parentId':[1876]},
 1956:   { 'name':'testicular seminoma',             'parentId':[1973]},
 1957:   { 'name':'low-grade salivary gland carcinoma',             'parentId':[1997]},
 1958:   { 'name':'lip basal cell carcinoma',             'parentId':[2073]},
 1959:   { 'name':'high-grade salivary gland carcinoma',             'parentId':[1997]},
 1960:   { 'name':'nasopharyngeal lymphoepithelioma',             'parentId':[1977]},
 1961:   { 'name':'cellular diagnosis, breast cancer',             'parentId':[398]},
 1962:   { 'name':'cellular diagnosis, adrenocortical carcinoma',             'parentId':[331]},
 1963:   { 'name':'cellular diagnosis, gallbladder cancer',             'parentId':[320]},
 1964:   { 'name':'cellular diagnosis, Ewing sarcoma/peripheral primitive neuroectodermal tumor',             'parentId':[498]},
 1965:   { 'name':'cellular diagnosis, malignant mesothelioma',             'parentId':[234]},
 1966:   { 'name':'cellular diagnosis, adult acute lymphoblastic leukemia',             'parentId':[192]},
 1967:   { 'name':'cellular diagnosis, Wilms tumor',             'parentId':[1408]},
 1968:   { 'name':'cellular diagnosis, thymoma and thymic carcinoma',             'parentId':[363]},
 1969:   { 'name':'cellular diagnosis, esophageal cancer',             'parentId':[1465]},
 1970:   { 'name':'cellular diagnosis, adult acute myeloid leukemia',             'parentId':[193]},
 1971:   { 'name':'cellular diagnosis, endometrial carcinoma',             'parentId':[310]},
 1972:   { 'name':'cellular diagnosis, metastatic squamous neck cancer with occult primary',             'parentId':[424]},
 1973:   { 'name':'cellular diagnosis, malignant testicular germ cell tumor',             'parentId':[1884]},
 1974:   { 'name':'cellular diagnosis, adult primary liver cancer',             'parentId':[329]},
 1975:   { 'name':'cellular diagnosis, renal cell carcinoma',             'parentId':[233]},
 1976:   { 'name':'cellular diagnosis, adult soft tissue sarcoma',             'parentId':[1474]},
 1977:   { 'name':'cellular diagnosis, nasopharyngeal cancer',             'parentId':[407]},
 1978:   { 'name':'cellular diagnosis, vulvar cancer',             'parentId':[207]},
 1979:   { 'name':'cellular diagnosis, melanoma',             'parentId':[399]},
 1980:   { 'name':'cellular diagnosis, anal cancer',             'parentId':[500]},
 1981:   { 'name':'cellular diagnosis, non-small cell lung cancer',             'parentId':[1162]},
 1982:   { 'name':'cellular diagnosis, vaginal cancer',             'parentId':[216]},
 1983:   { 'name':'cellular diagnosis, bladder cancer',             'parentId':[339]},
 1984:   { 'name':'cellular diagnosis, extrahepatic bile duct cancer',             'parentId':[325]},
 1985:   { 'name':'cellular diagnosis, osteosarcoma',             'parentId':[1266]},
 1986:   { 'name':'cellular diagnosis, pancreatic cancer',             'parentId':[1243]},
 1987:   { 'name':'cellular diagnosis, parathyroid cancer',             'parentId':[2108]},
 1988:   { 'name':'cellular diagnosis, penile cancer',             'parentId':[1880]},
 1989:   { 'name':'cellular diagnosis, ovarian epithelial cancer',             'parentId':[1491]},
 1990:   { 'name':'cellular diagnosis, cervical cancer',             'parentId':[199]},
 1991:   { 'name':'cellular diagnosis, gastric cancer',             'parentId':[521]},
 1992:   { 'name':'cellular diagnosis, childhood acute lymphoblastic leukemia',             'parentId':[615]},
 1993:   { 'name':'cellular diagnosis, childhood acute myeloid leukemia',             'parentId':[244]},
 1994:   { 'name':'cellular diagnosis, childhood liver cancer',             'parentId':[1504]},
 1995:   { 'name':'cellular diagnosis, childhood rhabdomyosarcoma',             'parentId':[1415]},
 1996:   { 'name':'cellular diagnosis, chronic lymphocytic leukemia',             'parentId':[167]},
 1997:   { 'name':'cellular diagnosis, salivary gland cancer',             'parentId':[425]},
 1998:   { 'name':'cellular diagnosis, rectal cancer',             'parentId':[1416]},
 1999:   { 'name':'cellular diagnosis, small cell lung cancer',             'parentId':[1168]},
 2000:   { 'name':'cellular diagnosis, intraocular melanoma',             'parentId':[383]},
 2001:   { 'name':'cellular diagnosis, adult Hodgkin lymphoma',             'parentId':[813]},
 2002:   { 'name':'cellular diagnosis, gestational trophoblastic tumor',             'parentId':[307]},
 2003:   { 'name':'cellular diagnosis, adult non-Hodgkin lymphoma',             'parentId':[1392]},
 2004:   { 'name':'cellular diagnosis, childhood Hodgkin lymphoma',             'parentId':[823]},
 2005:   { 'name':'stage, adult non-Hodgkin lymphoma',             'parentId':[1392]},
 2006:   { 'name':'cellular diagnosis, childhood soft tissue sarcoma',             'parentId':[838]},
 2007:   { 'name':'carcinoma of unknown primary',             'parentId':[1896, 889]},
 2008:   { 'name':'adenocarcinoma of unknown primary',             'parentId':[2099]},
 2009:   { 'name':'cellular diagnosis, ovarian germ cell tumor',             'parentId':[855]},
 2010:   { 'name':'ovarian dysgerminoma',             'parentId':[2009]},
 2011:   { 'name':'stage, intraocular melanoma',             'parentId':[383]},
 2012:   { 'name':'stage/type, islet cell carcinoma',             'parentId':[1432]},
 2013:   { 'name':'stage, adrenocortical carcinoma',             'parentId':[331]},
 2014:   { 'name':'stage, adult acute lymphoblastic leukemia',             'parentId':[192]},
 2015:   { 'name':'stage, adult acute myeloid leukemia',             'parentId':[193]},
 2016:   { 'name':'stage, renal cell cancer',             'parentId':[233]},
 2017:   { 'name':'stage/type, adult brain tumor',             'parentId':[302]},
 2018:   { 'name':'stage, malignant mesothelioma',             'parentId':[234]},
 2019:   { 'name':'stage, thymoma and thymic carcinoma',             'parentId':[363]},
 2020:   { 'name':'stage, adult primary liver cancer',             'parentId':[329]},
 2021:   { 'name':'stage, melanoma',             'parentId':[399]},
 2022:   { 'name':'stage, metastatic squamous neck cancer with occult primary',             'parentId':[424]},
 2023:   { 'name':'stage, adult soft tissue sarcoma',             'parentId':[1474]},
 2024:   { 'name':'stage, nasopharyngeal cancer',             'parentId':[407]},
 2025:   { 'name':'stage, anal cancer',             'parentId':[500]},
 2026:   { 'name':'stage, neuroblastoma',             'parentId':[1293]},
 2027:   { 'name':'stage, non-small cell lung cancer',             'parentId':[1162]},
 2028:   { 'name':'stage, bladder cancer',             'parentId':[339]},
 2029:   { 'name':'stage, osteosarcoma',             'parentId':[1266]},
 2030:   { 'name':'stage, ovarian epithelial cancer',             'parentId':[1491]},
 2031:   { 'name':'stage, ovarian germ cell tumor',             'parentId':[855]},
 2032:   { 'name':'stage, pancreatic cancer',             'parentId':[1243]},
 2033:   { 'name':'stage, breast cancer',             'parentId':[398]},
 2034:   { 'name':'stage, parathyroid cancer',             'parentId':[2108]},
 2035:   { 'name':'stage, penile cancer',             'parentId':[1880]},
 2036:   { 'name':'stage, pheochromocytoma',             'parentId':[1900]},
 2037:   { 'name':'stage/type, pituitary tumor',             'parentId':[378]},
 2038:   { 'name':'stage, plasma cell neoplasm',             'parentId':[208]},
 2039:   { 'name':'stage, cervical cancer',             'parentId':[199]},
 2040:   { 'name':'stage, childhood acute lymphoblastic leukemia',             'parentId':[615]},
 2041:   { 'name':'stage, childhood acute myeloid leukemia',             'parentId':[244]},
 2042:   { 'name':'stage/type, childhood brain tumor',             'parentId':[1246]},
 2043:   { 'name':'stage, childhood liver cancer',             'parentId':[1504]},
 2044:   { 'name':'stage/cell type, childhood non-Hodgkin lymphoma',             'parentId':[1472]},
 2045:   { 'name':'stage, rectal cancer',             'parentId':[1416]},
 2046:   { 'name':'stage, childhood rhabdomyosarcoma',             'parentId':[1415]},
 2047:   { 'name':'stage, retinoblastoma',             'parentId':[1516]},
 2048:   { 'name':'stage, childhood soft tissue sarcoma',             'parentId':[838]},
 2049:   { 'name':'stage, salivary gland cancer',             'parentId':[425]},
 2050:   { 'name':'stage/cell type, skin cancer',             'parentId':[350]},
 2051:   { 'name':'stage, chronic lymphocytic leukemia',             'parentId':[167]},
 2052:   { 'name':'stage, small cell lung cancer',             'parentId':[1168]},
 2053:   { 'name':'stage/cell type, small intestine cancer',             'parentId':[309]},
 2054:   { 'name':'stage, gastric cancer',             'parentId':[521]},
 2055:   { 'name':'stage, cutaneous T-cell non-Hodgkin lymphoma',             'parentId':[1514]},
 2056:   { 'name':'stage, endometrial carcinoma',             'parentId':[310]},
 2057:   { 'name':'stage, malignant testicular germ cell tumor',             'parentId':[1884]},
 2058:   { 'name':'stage, esophageal cancer',             'parentId':[1465]},
 2059:   { 'name':'stage/cell type, thyroid cancer',             'parentId':[367]},
 2060:   { 'name':'stage, Ewing sarcoma/peripheral primitive neuroectodermal tumor',             'parentId':[498]},
 2061:   { 'name':'stage, extrahepatic bile duct cancer',             'parentId':[325]},
 2062:   { 'name':'stage, urethral cancer',             'parentId':[455]},
 2063:   { 'name':'stage, vaginal cancer',             'parentId':[216]},
 2064:   { 'name':'stage, gallbladder cancer',             'parentId':[320]},
 2065:   { 'name':'stage, vulvar cancer',             'parentId':[207]},
 2066:   { 'name':'stage, gastrointestinal carcinoid tumor',             'parentId':[227]},
 2067:   { 'name':'stage, Wilms tumor and other childhood kidney tumors',             'parentId':[1408]},
 2068:   { 'name':'stage, hairy cell leukemia',             'parentId':[461]},
 2069:   { 'name':'stage, adult Hodgkin lymphoma',             'parentId':[813]},
 2070:   { 'name':'stage, prostate cancer',             'parentId':[351]},
 2071:   { 'name':'stage, transitional cell cancer of the renal pelvis and ureter',             'parentId':[891]},
 2072:   { 'name':'stage, lip and oral cavity cancer',             'parentId':[736]},
 2073:   { 'name':'cellular diagnosis, lip and oral cavity cancer',             'parentId':[736]},
 2074:   { 'name':'stage, oropharyngeal cancer',             'parentId':[442]},
 2075:   { 'name':'cellular diagnosis, oropharyngeal cancer',             'parentId':[442]},
 2076:   { 'name':'stage, hypopharyngeal cancer',             'parentId':[434]},
 2077:   { 'name':'cellular diagnosis, hypopharyngeal cancer',             'parentId':[434]},
 2078:   { 'name':'cellular diagnosis, laryngeal cancer',             'parentId':[441]},
 2079:   { 'name':'stage, paranasal sinus and nasal cavity cancer',             'parentId':[758]},
 2080:   { 'name':'cellular diagnosis, paranasal sinus and nasal cavity cancer',             'parentId':[758]},
 2081:   { 'name':'stage, childhood Hodgkin lymphoma',             'parentId':[823]},
 2082:   { 'name':'lip and oral cavity squamous cell carcinoma',             'parentId':[2073]},
 2083:   { 'name':'oral cavity verrucous carcinoma',             'parentId':[2073]},
 2084:   { 'name':'oral cavity mucoepidermoid carcinoma',             'parentId':[2073]},
 2085:   { 'name':'oral cavity adenoid cystic carcinoma',             'parentId':[2073]},
 2086:   { 'name':'oropharyngeal squamous cell carcinoma',             'parentId':[2075]},
 2087:   { 'name':'oropharyngeal lymphoepithelioma',             'parentId':[2075]},
 2088:   { 'name':'hypopharyngeal squamous cell carcinoma',             'parentId':[2077]},
 2089:   { 'name':'laryngeal squamous cell carcinoma',             'parentId':[2078]},
 2090:   { 'name':'laryngeal verrucous carcinoma',             'parentId':[2078]},
 2091:   { 'name':'paranasal sinus and nasal cavity squamous cell carcinoma',             'parentId':[2080]},
 2092:   { 'name':'paranasal sinus and nasal cavity inverted papilloma',             'parentId':[2080]},
 2093:   { 'name':'paranasal sinus and nasal cavity midline lethal granuloma',             'parentId':[2080]},
 2094:   { 'name':'paranasal sinus and nasal cavity esthesioneuroblastoma',             'parentId':[2080]},
 2095:   { 'name':'extragonadal germ cell tumor',             'parentId':[1585]},
 2096:   { 'name':'stage, myelodysplastic syndromes',             'parentId':[520]},
 2097:   { 'name':'site, metastatic cancer',             'parentId':[1264]},
 2098:   { 'name':'stage, carcinoma of unknown primary',             'parentId':[2007]},
 2099:   { 'name':'cellular diagnosis, carcinoma of unknown primary',             'parentId':[2007]},
 2100:   { 'name':'cellular diagnosis, myelodysplastic syndromes',             'parentId':[520]},
 2101:   { 'name':'stage, gestational trophoblastic tumor',             'parentId':[307]},
 2102:   { 'name':'pseudomyxoma peritonei',             'parentId':[1683]},
 2103:   { 'name':'cellular diagnosis, uterine sarcoma',             'parentId':[896]},
 2104:   { 'name':'cellular diagnosis, borderline ovarian surface epithelial-stromal tumor',             'parentId':[1187]},
 2105:   { 'name':'site, AIDS-related lymphoma',             'parentId':[1135]},
 2106:   { 'name':'cellular diagnosis, AIDS-related lymphoma',             'parentId':[1135]},
 2107:   { 'name':'Hodgkin lymphoma during pregnancy',             'parentId':[1262]},
 2108:   { 'name':'parathyroid cancer',             'parentId':[1888, 889, 440]},
 2109:   { 'name':'childhood large cell lymphoma',             'parentId':[2044]},
 2110:   { 'name':'non-Hodgkin lymphoma during pregnancy',             'parentId':[436]},
 2111:   { 'name':'indolent or aggressive adult non-Hodgkin lymphoma',             'parentId':[1392]},
 2112:   { 'name':'adult ependymal tumors',             'parentId':[2017]},
 2113:   { 'name':'adult oligodendroglial tumors',             'parentId':[2017]},
 2114:   { 'name':'phyllodes tumor',             'parentId':[1961]},
 2115:   { 'name':'phosphaturic mesenchymal tumor',             'parentId':[1896]},
 2116:   { 'name':'body system/site cancer',             'parentId':[1185]},
 2117:   { 'name':'neoplastic syndrome',             'parentId':[2116]},
 2118:   { 'name':'Richter syndrome',             'parentId':[2117]},
 2119:   { 'name':'hereditary neoplastic syndrome',             'parentId':[2117]},
 2120:   { 'name':'malignant conjunctival neoplasm',             'parentId':[1891]},
 2121:   { 'name':'conjunctival Kaposi sarcoma',             'parentId':[2120]},
 2122:   { 'name':'conjunctival squamous cell carcinoma',             'parentId':[2120]},
 2123:   { 'name':'islet cell tumor',             'parentId':[1578]},
 2124:   { 'name':'cellular diagnosis, islet cell carcinoma',             'parentId':[1432]},
 2125:   { 'name':'cellular diagnosis, gastrinoma',             'parentId':[303]},
 2126:   { 'name':'cellular diagnosis, glucagonoma',             'parentId':[453]},
 2127:   { 'name':'cellular diagnosis, insulinoma',             'parentId':[360]},
 2128:   { 'name':'cellular diagnosis, somatostatinoma',             'parentId':[451]},
 2129:   { 'name':'testicular germ cell tumor',             'parentId':[1585]},
 2130:   { 'name':'childhood gonadal germ cell tumor',             'parentId':[449]},
 2131:   { 'name':'teratoma',             'parentId':[1585]},
 2132:   { 'name':'childhood unfavorable prognosis Hodgkin lymphoma',             'parentId':[2004]},
 2133:   { 'name':'malignant extragonadal germ cell tumor',             'parentId':[2095]},
 2134:   { 'name':'malignant extragonadal non-seminomatous germ cell tumor',             'parentId':[2133]},
 2135:   { 'name':'stage, extragonadal non-seminomatous germ cell tumor',             'parentId':[2134]},
 2136:   { 'name':'extragonadal seminoma',             'parentId':[2133]},
 2137:   { 'name':'stage, extragonadal seminoma',             'parentId':[2136]},
 2138:   { 'name':'stage, extragonadal germ cell tumor',             'parentId':[2095]},
 2139:   { 'name':'stage, borderline ovarian surface epithelial-stromal tumor',             'parentId':[1187]},
 2140:   { 'name':'stage, adult malignant fibrous histiocytoma of bone',             'parentId':[1282]},
 2141:   { 'name':'stage, childhood malignant fibrous histiocytoma of bone',             'parentId':[872]},
 2142:   { 'name':'spinal cord neoplasm',             'parentId':[443]},
 2143:   { 'name':'central nervous system germ cell tumor',             'parentId':[443]},
 2144:   { 'name':'nervous system neoplasm',             'parentId':[2116]},
 2145:   { 'name':'meningioma',             'parentId':[443]},
 2146:   { 'name':'peripheral nervous system neoplasm',             'parentId':[2144]},
 2147:   { 'name':'pleuropulmonary blastoma',             'parentId':[487]},
 2148:   { 'name':'cellular diagnosis, endometrial sarcoma',             'parentId':[310]},
 2149:   { 'name':'soft tissue metastases',             'parentId':[2097]},
 2150:   { 'name':'childhood central nervous system embryonal tumor',             'parentId':[243]},
 2151:   { 'name':'osteolytic lesions of multiple myeloma',             'parentId':[2038]},
 2152:   { 'name':'sebaceous gland carcinoma',             'parentId':[1889]},
 2153:   { 'name':'solitary pulmonary nodule',             'parentId':[2162]},
 2154:   { 'name':'hormone receptor/growth factor receptor-negative breast cancer',             'parentId':[398]},
 2155:   { 'name':'paraganglioma',             'parentId':[1578]},
 2156:   { 'name':'extra-adrenal paraganglioma',             'parentId':[2155]},
 2157:   { 'name':'adult epithelioid hemangioendothelioma',             'parentId':[1976]},
 2158:   { 'name':'soft tissue tumor',             'parentId':[2116]},
 2159:   { 'name':'childhood epithelioid hemangioendothelioma',             'parentId':[2006]},
 2160:   { 'name':'adult Langerhans cell histiocytosis',             'parentId':[2161]},
 2161:   { 'name':'Langerhans cell histiocytosis',             'parentId':[511]},
 2162:   { 'name':'pulmonary nodule',             'parentId':[127]},
 2163:   { 'name':'cytogenetic abnormalities, myelodysplastic syndromes',             'parentId':[520]},
 2164:   { 'name':'hormone-resistant breast cancer',             'parentId':[398]},
 2165:   { 'name':'childhood astrocytoma',             'parentId':[2378]},
 2166:   { 'name':'carcinoid tumor',             'parentId':[1578]},
 2167:   { 'name':'ureter cancer',             'parentId':[509]},
 2168:   { 'name':'malignant cardiovascular neoplasm',             'parentId':[2116]},
 2169:   { 'name':'other health status',             'parentId':[101]},
 2170:   { 'name':'central nervous system leukemia',             'parentId':[443, 1196]},
 2171:   { 'name':'meningeal leukemia',             'parentId':[2170]},
 2172:   { 'name':'cerebral leukemia',             'parentId':[2170]},
 2173:   { 'name':'secondary central nervous system lymphoma',             'parentId':[443]},
 2174:   { 'name':'secondary central nervous system Hodgkin lymphoma',             'parentId':[2173, 1262]},
 2175:   { 'name':'secondary central nervous system non-Hodgkin lymphoma',             'parentId':[2173, 436]},
 2176:   { 'name':'primary central nervous system Hodgkin lymphoma',             'parentId':[1262, 2177]},
 2177:   { 'name':'primary central nervous system lymphoma',             'parentId':[889, 443]},
 2178:   { 'name':'acute leukemias of ambiguous lineage',             'parentId':[431]},
 2179:   { 'name':'myeloid/NK-cell acute leukemia',             'parentId':[2178]},
 2180:   { 'name':'mature T-cell and NK-cell neoplasms',             'parentId':[511]},
 2181:   { 'name':'aggressive NK-cell leukemia',             'parentId':[2180]},
 2182:   { 'name':'acute myeloid leukemia and related precursor neoplams',             'parentId':[431]},
 2183:   { 'name':'blastic plasmacytoid dendritic cell neoplasm',             'parentId':[2182]},
 2184:   { 'name':'hemophilia A',             'parentId':[1864]},
 2185:   { 'name':'hyperparathyroidism',             'parentId':[1864]},
 2186:   { 'name':'ulcerative colitis-associated low-grade dysplasia',             'parentId':[1864]},
 2187:   { 'name':'stage I thymoma',             'parentId':[2019]},
 2188:   { 'name':'stage II thymoma',             'parentId':[2019]},
 2189:   { 'name':'stage III thymoma',             'parentId':[2019]},
 2190:   { 'name':'stage IV thymoma',             'parentId':[2019]},
 2191:   { 'name':'stage IVA thymoma',             'parentId':[2190]},
 2192:   { 'name':'stage IVB thymoma',             'parentId':[2190]},
 2194:   { 'name':'stage III and stage IV thymoma',             'parentId':[2019]},
 2195:   { 'name':'intraductal papillary mucinous neoplasm of the pancreas',             'parentId':[1986]},
 2196:   { 'name':'malignant biliary obstruction',             'parentId':[1215, 2097]},
 2197:   { 'name':'second-hand tobacco smoke exposure',             'parentId':[1215]},
 2198:   { 'name':'stage IA breast cancer',             'parentId':[406]},
 2199:   { 'name':'stage IB breast cancer',             'parentId':[406]},
 2200:   { 'name':'hernia',             'parentId':[1215]},
 2201:   { 'name':'hypocalcemia',             'parentId':[1215]},
 2202:   { 'name':'stage IA melanoma',             'parentId':[400]},
 2203:   { 'name':'stage IB melanoma',             'parentId':[400]},
 2204:   { 'name':'stage IIA melanoma',             'parentId':[401]},
 2205:   { 'name':'stage IIB melanoma',             'parentId':[401]},
 2206:   { 'name':'stage IIC melanoma',             'parentId':[401]},
 2207:   { 'name':'stage IIIA melanoma',             'parentId':[402]},
 2208:   { 'name':'stage IIIB melanoma',             'parentId':[402]},
 2209:   { 'name':'stage IIIC melanoma',             'parentId':[402]},
 2210:   { 'name':'stage IA non-small cell lung cancer',             'parentId':[1207]},
 2211:   { 'name':'stage IB non-small cell lung cancer',             'parentId':[1207]},
 2212:   { 'name':'stage IIA non-small cell lung cancer',             'parentId':[1211]},
 2213:   { 'name':'stage IIB non-small cell lung cancer',             'parentId':[1211]},
 2214:   { 'name':'stage IA pancreatic cancer',             'parentId':[1331]},
 2215:   { 'name':'stage IB pancreatic cancer',             'parentId':[1331]},
 2216:   { 'name':'stage IIA pancreatic cancer',             'parentId':[1335]},
 2217:   { 'name':'stage IIB pancreatic cancer',             'parentId':[1335]},
 2218:   { 'name':'stage IVB follicular thyroid cancer',             'parentId':[373]},
 2219:   { 'name':'stage IVC follicular thyroid cancer',             'parentId':[373]},
 2220:   { 'name':'stage IVC papillary thyroid cancer',             'parentId':[370]},
 2221:   { 'name':'stage IVB papillary thyroid cancer',             'parentId':[370]},
 2222:   { 'name':'stage IVA papillary thyroid cancer',             'parentId':[370]},
 2223:   { 'name':'Crohn disease-associated dysplasia',             'parentId':[1864]},
 2224:   { 'name':'stage IVA follicular thyroid cancer',             'parentId':[373]},
 2225:   { 'name':'diabetes mellitus',             'parentId':[1864]},
 2226:   { 'name':'borderline papillary serous cystadenoma',             'parentId':[1865]},
 2227:   { 'name':'ovarian papillary serous carcinoma',             'parentId':[1989]},
 2228:   { 'name':'hyponatremia',             'parentId':[1215]},
 2229:   { 'name':'Leydig cell tumor',             'parentId':[439, 1897]},
 2230:   { 'name':'hypertension',             'parentId':[1864]},
 2231:   { 'name':'stage IIA prostate cancer',             'parentId':[353]},
 2232:   { 'name':'stage IIB prostate cancer',             'parentId':[353]},
 2233:   { 'name':'stage IIA colon cancer',             'parentId':[163]},
 2234:   { 'name':'stage IIB colon cancer',             'parentId':[163]},
 2235:   { 'name':'stage IIC colon cancer',             'parentId':[163]},
 2236:   { 'name':'stage IIIA colon cancer',             'parentId':[269]},
 2237:   { 'name':'stage IIIB colon cancer',             'parentId':[269]},
 2238:   { 'name':'stage IIIC colon cancer',             'parentId':[269]},
 2239:   { 'name':'stage IVA colon cancer',             'parentId':[333]},
 2240:   { 'name':'stage IVB colon cancer',             'parentId':[333]},
 2241:   { 'name':'stage IIA rectal cancer',             'parentId':[1431]},
 2242:   { 'name':'stage IIB rectal cancer',             'parentId':[1431]},
 2243:   { 'name':'stage IIC rectal cancer',             'parentId':[1431]},
 2244:   { 'name':'stage IIIA rectal cancer',             'parentId':[1439]},
 2245:   { 'name':'stage IIIB rectal cancer',             'parentId':[1439]},
 2246:   { 'name':'stage IIIC rectal cancer',             'parentId':[1439]},
 2247:   { 'name':'stage IVA rectal cancer',             'parentId':[1440]},
 2248:   { 'name':'stage IVB rectal cancer',             'parentId':[1440]},
 2249:   { 'name':'Hurler syndrome',             'parentId':[1864]},
 2250:   { 'name':'multiple sclerosis',             'parentId':[1864]},
 2251:   { 'name':'condyloma acuminatum',             'parentId':[1864]},
 2252:   { 'name':'bone Paget disease',             'parentId':[1864]},
 2253:   { 'name':'thrombotic thrombocytopenic purpura',             'parentId':[1215]},
 2254:   { 'name':'stage IIIA penile cancer',             'parentId':[257]},
 2255:   { 'name':'stage IIIB penile cancer',             'parentId':[257]},
 2256:   { 'name':'stage IVA salivary gland cancer',             'parentId':[429]},
 2257:   { 'name':'stage IVB salivary gland cancer',             'parentId':[429]},
 2258:   { 'name':'stage IVC salivary gland cancer',             'parentId':[429]},
 2259:   { 'name':'stage IVA esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[1121]},
 2260:   { 'name':'stage IVB esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[1121]},
 2261:   { 'name':'stage IVC esthesioneuroblastoma of the paranasal sinus and nasal cavity',             'parentId':[1121]},
 2262:   { 'name':'stage IVA inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[1119]},
 2263:   { 'name':'stage IVB inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[1119]},
 2264:   { 'name':'stage IVC inverted papilloma of the paranasal sinus and nasal cavity',             'parentId':[1119]},
 2265:   { 'name':'stage IVA midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[1120]},
 2266:   { 'name':'stage IVB midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[1120]},
 2267:   { 'name':'stage IVC midline lethal granuloma of the paranasal sinus and nasal cavity',             'parentId':[1120]},
 2268:   { 'name':'stage IVA squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[1118]},
 2269:   { 'name':'stage IVC squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[1118]},
 2270:   { 'name':'stage IVB squamous cell carcinoma of the paranasal sinus and nasal cavity',             'parentId':[1118]},
 2271:   { 'name':'stage IV fallopian tube cancer',             'parentId':[2272]},
 2272:   { 'name':'stage, fallopian tube cancer',             'parentId':[1169]},
 2273:   { 'name':'stage IA fallopian tube cancer',             'parentId':[2274]},
 2274:   { 'name':'stage I fallopian tube cancer',             'parentId':[2272]},
 2275:   { 'name':'stage IB fallopian tube cancer',             'parentId':[2274]},
 2276:   { 'name':'stage IC fallopian tube cancer',             'parentId':[2274]},
 2277:   { 'name':'stage IIA fallopian tube cancer',             'parentId':[2278]},
 2278:   { 'name':'stage II fallopian tube cancer',             'parentId':[2272]},
 2279:   { 'name':'stage IIB fallopian tube cancer',             'parentId':[2278]},
 2280:   { 'name':'stage IIC fallopian tube cancer',             'parentId':[2278]},
 2281:   { 'name':'stage III fallopian tube cancer',             'parentId':[2272]},
 2282:   { 'name':'stage IIIA fallopian tube cancer',             'parentId':[2281]},
 2283:   { 'name':'stage IIIB fallopian tube cancer',             'parentId':[2281]},
 2284:   { 'name':'stage IIIC fallopian tube cancer',             'parentId':[2281]},
 2285:   { 'name':'recurrent fallopian tube cancer',             'parentId':[2272]},
 2286:   { 'name':'stage, primary peritoneal cavity cancer',             'parentId':[1170]},
 2287:   { 'name':'stage I primary peritoneal cavity cancer',             'parentId':[2286]},
 2288:   { 'name':'stage II primary peritoneal cavity cancer',             'parentId':[2286]},
 2289:   { 'name':'stage III primary peritoneal cavity cancer',             'parentId':[2286]},
 2290:   { 'name':'stage IV primary peritoneal cavity cancer',             'parentId':[2286]},
 2291:   { 'name':'recurrent primary peritoneal cavity cancer',             'parentId':[2286]},
 2292:   { 'name':'peritoneal cavity metastasis',             'parentId':[2097]},
 2293:   { 'name':'lobular neoplasia',             'parentId':[1778]},
 2294:   { 'name':'lymphocele',             'parentId':[1215]},
 2295:   { 'name':'mastocytosis',             'parentId':[1893]},
 2296:   { 'name':'systemic mastocytosis',             'parentId':[2295]},
 2297:   { 'name':'mast cell sarcoma',             'parentId':[2295, 1736]},
 2298:   { 'name':'extracutaneous mastocytoma',             'parentId':[2295]},
 2299:   { 'name':'stage IA primary peritoneal cavity cancer',             'parentId':[2287]},
 2300:   { 'name':'stage IB primary peritoneal cavity cancer',             'parentId':[2287]},
 2301:   { 'name':'stage IC primary peritoneal cavity cancer',             'parentId':[2287]},
 2302:   { 'name':'stage IIA primary peritoneal cavity cancer',             'parentId':[2288]},
 2303:   { 'name':'stage IIB primary peritoneal cavity cancer',             'parentId':[2288]},
 2304:   { 'name':'stage IIC primary peritoneal cavity cancer',             'parentId':[2288]},
 2305:   { 'name':'stage IIIA primary peritoneal cavity cancer',             'parentId':[2289]},
 2306:   { 'name':'stage IIIC primary peritoneal cavity cancer',             'parentId':[2289]},
 2307:   { 'name':'stage IIIB primary peritoneal cavity cancer',             'parentId':[2289]},
 2308:   { 'name':'stage IB ovarian epithelial cancer',             'parentId':[1492]},
 2309:   { 'name':'stage IA ovarian epithelial cancer',             'parentId':[1492]},
 2310:   { 'name':'stage IC ovarian epithelial cancer',             'parentId':[1492]},
 2311:   { 'name':'stage IIA ovarian epithelial cancer',             'parentId':[1493]},
 2312:   { 'name':'stage IIB ovarian epithelial cancer',             'parentId':[1493]},
 2313:   { 'name':'stage IIC ovarian epithelial cancer',             'parentId':[1493]},
 2314:   { 'name':'stage IIIA ovarian epithelial cancer',             'parentId':[1494]},
 2315:   { 'name':'stage IIIB ovarian epithelial cancer',             'parentId':[1494]},
 2316:   { 'name':'stage IIIC ovarian epithelial cancer',             'parentId':[1494]},
 2317:   { 'name':'stage IB ovarian germ cell tumor',             'parentId':[856]},
 2318:   { 'name':'stage IA ovarian germ cell tumor',             'parentId':[856]},
 2319:   { 'name':'stage IC ovarian germ cell tumor',             'parentId':[856]},
 2320:   { 'name':'stage IIB ovarian germ cell tumor',             'parentId':[857]},
 2321:   { 'name':'stage IIA ovarian germ cell tumor',             'parentId':[857]},
 2322:   { 'name':'stage IIC ovarian germ cell tumor',             'parentId':[857]},
 2323:   { 'name':'stage IIIA ovarian germ cell tumor',             'parentId':[858]},
 2324:   { 'name':'stage IIIB ovarian germ cell tumor',             'parentId':[858]},
 2325:   { 'name':'stage IIIC ovarian germ cell tumor',             'parentId':[858]},
 2326:   { 'name':'stage IVA squamous cell carcinoma of the larynx',             'parentId':[1102]},
 2327:   { 'name':'stage IVB squamous cell carcinoma of the larynx',             'parentId':[1102]},
 2328:   { 'name':'stage IVC squamous cell carcinoma of the larynx',             'parentId':[1102]},
 2329:   { 'name':'stage IVC verrucous carcinoma of the larynx',             'parentId':[1103]},
 2330:   { 'name':'stage IVA verrucous carcinoma of the larynx',             'parentId':[1103]},
 2331:   { 'name':'stage IVB verrucous carcinoma of the larynx',             'parentId':[1103]},
 2332:   { 'name':'stage IVA squamous cell carcinoma of the lip and oral cavity',             'parentId':[1060]},
 2333:   { 'name':'stage IVB squamous cell carcinoma of the lip and oral cavity',             'parentId':[1060]},
 2334:   { 'name':'stage IVC squamous cell carcinoma of the lip and oral cavity',             'parentId':[1060]},
 2335:   { 'name':'stage IVC adenoid cystic carcinoma of the oral cavity',             'parentId':[1064]},
 2336:   { 'name':'stage IVB adenoid cystic carcinoma of the oral cavity',             'parentId':[1064]},
 2337:   { 'name':'stage IVA adenoid cystic carcinoma of the oral cavity',             'parentId':[1064]},
 2338:   { 'name':'stage IVC basal cell carcinoma of the lip',             'parentId':[1061]},
 2339:   { 'name':'stage IVB basal cell carcinoma of the lip',             'parentId':[1061]},
 2340:   { 'name':'stage IVA basal cell carcinoma of the lip',             'parentId':[1061]},
 2341:   { 'name':'stage IVC mucoepidermoid carcinoma of the oral cavity',             'parentId':[1063]},
 2342:   { 'name':'stage IVB mucoepidermoid carcinoma of the oral cavity',             'parentId':[1063]},
 2343:   { 'name':'stage IVA mucoepidermoid carcinoma of the oral cavity',             'parentId':[1063]},
 2344:   { 'name':'stage IVA verrucous carcinoma of the oral cavity',             'parentId':[1062]},
 2345:   { 'name':'stage IVB verrucous carcinoma of the oral cavity',             'parentId':[1062]},
 2346:   { 'name':'stage IVC verrucous carcinoma of the oral cavity',             'parentId':[1062]},
 2347:   { 'name':'stage IVB lymphoepithelioma of the oropharynx',             'parentId':[1077]},
 2348:   { 'name':'stage IVA lymphoepithelioma of the oropharynx',             'parentId':[1077]},
 2349:   { 'name':'stage IVC lymphoepithelioma of the oropharynx',             'parentId':[1077]},
 2350:   { 'name':'stage IVC squamous cell carcinoma of the oropharynx',             'parentId':[1076]},
 2351:   { 'name':'stage IVB squamous cell carcinoma of the oropharynx',             'parentId':[1076]},
 2352:   { 'name':'stage IVA squamous cell carcinoma of the oropharynx',             'parentId':[1076]},
 2353:   { 'name':'metastatic extrahepatic bile duct cancer',             'parentId':[2061]},
 2354:   { 'name':'metastatic gallbladder cancer',             'parentId':[2064]},
 2355:   { 'name':'epithelioid trophoblastic tumor',             'parentId':[2002]},
 2356:   { 'name':'Erdheim-Chester disease',             'parentId':[1864]},
 2357:   { 'name':'stage IA malignant mesothelioma',             'parentId':[2359]},
 2358:   { 'name':'stage IB malignant mesothelioma',             'parentId':[2359]},
 2359:   { 'name':'stage I malignant mesothelioma',             'parentId':[235]},
 2360:   { 'name':'stage II malignant mesothelioma',             'parentId':[238]},
 2361:   { 'name':'stage III malignant mesothelioma',             'parentId':[238]},
 2362:   { 'name':'stage IV malignant mesothelioma',             'parentId':[238]},
 2363:   { 'name':'stage IA endometrial carcinoma',             'parentId':[312]},
 2364:   { 'name':'stage IB endometrial carcinoma',             'parentId':[312]},
 2365:   { 'name':'stage IIIA endometrial carcinoma',             'parentId':[314]},
 2366:   { 'name':'stage IIIB endometrial carcinoma',             'parentId':[314]},
 2367:   { 'name':'stage IIIC endometrial carcinoma',             'parentId':[314]},
 2368:   { 'name':'stage IVA endometrial carcinoma',             'parentId':[315]},
 2369:   { 'name':'stage IVB endometrial carcinoma',             'parentId':[315]},
 2370:   { 'name':'stage IA gastric cancer',             'parentId':[843]},
 2371:   { 'name':'stage IB gastric cancer',             'parentId':[843]},
 2372:   { 'name':'stage IIIB gastric cancer',             'parentId':[882]},
 2373:   { 'name':'stage IIIA gastric cancer',             'parentId':[882]},
 2374:   { 'name':'stage IIIC gastric cancer',             'parentId':[882]},
 2375:   { 'name':'stage IIA gastric cancer',             'parentId':[875]},
 2376:   { 'name':'stage IIB gastric cancer',             'parentId':[875]},
 2377:   { 'name':'Crohn disease',             'parentId':[1864]},
 2378:   { 'name':'childhood astrocytoma or other tumor of glial origin',             'parentId':[2042]},
 2379:   { 'name':'childhood low-grade untreated astrocytoma or other tumor of glial origin',             'parentId':[2378]},
 2380:   { 'name':'childhood high-grade untreated astrocytoma or other tumor of glial origin',             'parentId':[2378]},
 2381:   { 'name':'childhood recurrent astrocytoma or other tumor of glial origin',             'parentId':[2378]}
		},
	}
};

FiveAmUtil.PDQPkg.init();




