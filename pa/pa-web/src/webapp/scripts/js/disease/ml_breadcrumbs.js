
//************************
//** Breadcrumbs Markup **
//************************
/*
<div class="breadcrumbItemPane">
	<input type="checkbox"/>				
	<div class="breadcrumbItemBox">	
		<div class="breadcrumbElement">	
			<div class="breadcrumbElementText">	
			<div class="breadcrumbElementImageLink">	
		</div>	
		<div class="breadcrumbElementSeparator"/>	
		<div class="breadcrumbElement">...</div>
		<div class="breadcrumbElementSeparator"/>	
		...	
		<div class="breadcrumbElementSeparator"/>	
		<div class="breadcrumbFeaturedElement">	
			<div class="breadcrumbFeaturedElementText">	
			<div class="breadcrumbFeaturedElementImageLink">	
		</div>	
	</div>									
</div>
*/   
    
(function($){
	$.fn.buildBreadcrumbs = function(settings){
		var breadcrumbsContainer = $(this);
		var localSettings = $.extend({}, $.fn.buildBreadcrumbs.defaults, settings);

		function _build(){
			var textTip = 'Add this item to your selections for the search';
			var imageTip = 'Click to open this item in the tree view';
			var imageFile = paApp.imagePath + '/tree_32.png';
						
			breadcrumbsContainer.empty();
			var bcItems = localSettings.bcItems;
			var term = localSettings.searchTerm;
			for( var i in bcItems ) {
				if(!bcItems.hasOwnProperty(i))
					continue;
				var bcItem = bcItems[i];
				var html = '<div class="breadcrumbItemPane"><div class="breadcrumbItemBox">';
				if( bcItem[0].isFeatured ) {
					html += '<div id="breadcrumb_box'+i+'_id'+bcItem[0].id+'" class="breadcrumbFeaturedElement">'+
								'<div class="breadcrumbFeaturedElementText" title="'+textTip+'">' + bcItem[0].name + '</div>';
					if (bcItem[0].hasChildren)
						html += '<div class="breadcrumbFeaturedElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>';
					html += '</div>';
				} else {
					html += '<div id="breadcrumb_box'+i+'_id'+bcItem[0].id+'" class="breadcrumbElement">'+
							'<div class="breadcrumbElementText" title="'+textTip+'">' + bcItem[0].name + '</div>'+
							'<div class="breadcrumbElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>'+
							'</div>';
				}
				for( var j=1; j<bcItem.length; j++ ) {
					html += '<div class="breadcrumbElementSeparator"> &gt; </div>';
					if( bcItem[j].isFeatured ) {
						html += '<div id="breadcrumb_box'+i+'_id'+bcItem[j].id+'" class="breadcrumbFeaturedElement">'+
								'<div class="breadcrumbFeaturedElementText" title="'+textTip+'">' + bcItem[j].name + '</div>';
						if (bcItem[j].hasChildren)
							html += '<div class="breadcrumbFeaturedElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>';
						html += '</div>';
					} else {
						html += '<div id="breadcrumb_box'+i+'_id'+bcItem[j].id+'" class="breadcrumbElement">'+
								'<div class="breadcrumbElementText" title="'+textTip+'">' + bcItem[j].name + '</div>'+
								'<div class="breadcrumbElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>'+
								'</div>';
					}
				}
				html += '</div></div>';
				breadcrumbsContainer.append(html);
			}
			
			$('.breadcrumbFeaturedElement').each( function() {
				$(this).html( $(this).html().replace(term,'<span class="breadcrumbHighlight">'+term+'</span>') );
			});			
		};
		
		//    Entry point
		_build();
		return breadcrumbsContainer;	
	};
	
	$.fn.buildBreadcrumbs.defaults = {
		showSpeed : 'fast',
		hideSpeed : ''
	};
})(jQuery);