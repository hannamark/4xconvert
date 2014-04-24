/* affix the navbar after scroll below header */

var stickyHeaderTop = null ;

jQuery(window).scroll(function(){
        if( jQuery(window).scrollTop() > stickyHeaderTop ) {
                jQuery('#nav').css({position: 'fixed', top: '-1px'});
                jQuery('#stickyalias').css('display', 'block');
        } else {
                jQuery('#nav').css({position: 'static', top: '0px'});
                jQuery('#stickyalias').css('display', 'none');
        }
});
		
/* animate the Bootstrap dropdown transition */

jQuery(document).ready(function() {
	if(jQuery('#nav').length >0) {
		stickyHeaderTop = jQuery('#nav').offset().top;
	} else {
		stickyHeaderTop = 0;
	}
	
	jQuery('.navbar-nav .dropdown').hover(function() {
	  jQuery(this).find('.dropdown-menu').first().stop(true, true).delay(0).slideDown(200);
	}, function() {
	  jQuery(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp(200)
	});

   jQuery(function (jQuery) {
        jQuery('[rel=tooltip]').tooltip()
    });
});

//Multi-level Dropdown
jQuery(function(){
	jQuery(".dropdown-menu > li > a.trigger").on("mouseover",function(e){
		var current=jQuery(this).next();
		var grandparent=jQuery(this).parent().parent();
		if(jQuery(this).hasClass('left-caret')||jQuery(this).hasClass('right-caret'))
			jQuery(this).toggleClass('right-caret left-caret');
		grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
		grandparent.find(".sub-menu:visible").not(current).hide();
		current.toggle();
		e.stopPropagation();
	});
	jQuery(".dropdown-menu > li > a:not(.trigger)").on("mouseoff",function(){
		var root=jQuery(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
		root.find('.sub-menu:visible').hide();
	});
});

//Collapse / Expand
jQuery(function() {
	jQuery('.expandcollapse').click(function() {

        var newstate = jQuery(this).attr('state') ^ 1,
            icon = newstate ? "plus" : "minus",
            text = newstate ? "Expand" : "Collapse";
    
        // if state=0, show all the accordion divs within the same block (in this case, within the same section)
        if ( jQuery(this).attr('state')==="0" ) {
            console.log('1');
            jQuery(this).siblings('div').find('div.accordion-body.in').collapse('hide');
			jQuery('a.accordion-toggle').each(function() {
				jQuery(this).addClass('collapsed');
			});
        }
        // otherwise, collapse all the divs
        else {
            console.log('2');
            jQuery(this).siblings('div').find('div.accordion-body:not(.in)').collapse('show');
			jQuery('a.accordion-toggle').each(function() {
				
				jQuery(this).removeClass('collapsed')
			});
        }

        jQuery(this).html("<i class=\"fa-" + icon + "-circle\"></i> " + text +" All");

        jQuery(this).attr('state',newstate)

    });


    jQuery('a[data-toggle="tab"]').on('shown', function (e) {

        var myState = jQuery(this).attr('state'),
            state = jQuery('.expandcollapse').attr('state');

        if(myState != state) {
          toggleTab(jQuery(this).prop('hash'));
          jQuery(this).attr('state',state);
        }

    });
});

    function toggleTab(id){
        jQuery(id).find('.collapse').each(function() {
            jQuery(this).collapse('toggle');
          });
    }
// Tooltip
	jQuery(function (jQuery) {
        jQuery('[rel=tooltip]').tooltip()
    });
	
// Popover
	jQuery(function (jQuery) {
        jQuery('[rel=popover]').popover()
    });
	
// Date Picker
jQuery(function() {
    jQuery('.datetimepicker').datetimepicker({
      pickTime: false
    });
  });

jQuery(function() {
    jQuery('.datetimepicker').on('changeDate', function(ev){
    	jQuery(this).datetimepicker('hide');
    });
  });



jQuery(function (jQuery) {
	jQuery('#popover').popover({
		container: 'body',
		trigger: "hover"
	});   
 });


// Increase/Decrease text size 

jQuery('#incfont').click(function(){    
        curSize= parseInt(jQuery('#wrap').css('font-size')) + 2;
  if(curSize<=19)
        jQuery('#wrap').css('font-size', curSize);
        });  
  jQuery('#decfont').click(function(){    
        curSize= parseInt(jQuery('#wrap').css('font-size')) - 2;
  if(curSize>=11)
        jQuery('#wrap').css('font-size', curSize);
}); 

// Show/Hide Search Criteria  
  
 function toggledisplay (it, box) {
	var vis = (box.checked) ? "none" : "block";
	document.getElementById(it).style.display = vis;
}

// Multi-select Dropdown  
	jQuery('#multi-select').multiselect({
	        	includeSelectAllOption: true
	        });
	

 /*
 bootstrap 3.0 with prototype.js breaks for tooltip, popover, dropdown menu
 adding this function to check for bootstrap namespace for the event for these items
 The issue is explained over here: https://github.com/twbs/bootstrap/issues/6921
 */
(function() {
    var isBootstrapEvent = false;
    if (window.jQuery) {
        var all = jQuery('*');
        jQuery.each(['hide.bs.dropdown', 
            'hide.bs.collapse', 
            'hide.bs.modal', 
            'hide.bs.tooltip',
            'hide.bs.popover'], function(index, eventName) {
            all.on(eventName, function( event ) {
                isBootstrapEvent = true;
            });
        });
    }
    var originalHide = Element.hide;
    Element.addMethods({
        hide: function(element) {
            if(isBootstrapEvent) {
                isBootstrapEvent = false;
                return element;
            }
            return originalHide(element);
        }
    });
})();

