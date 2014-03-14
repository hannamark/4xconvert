
$(document).ready(function() {
$('.navbar-nav .dropdown').hover(function() {
  $(this).find('.dropdown-menu').first().stop(true, true).delay(0).slideDown(200);
}, function() {
  $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp(200)
});

   jQuery(function ($) {
        $('[rel=tooltip]').tooltip()
    });
});

//Multi-level Dropdown
$(function(){
	$(".dropdown-menu > li > a.trigger").on("mouseover",function(e){
		var current=$(this).next();
		var grandparent=$(this).parent().parent();
		if($(this).hasClass('left-caret')||$(this).hasClass('right-caret'))
			$(this).toggleClass('right-caret left-caret');
		grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
		grandparent.find(".sub-menu:visible").not(current).hide();
		current.toggle();
		e.stopPropagation();
	});
	$(".dropdown-menu > li > a:not(.trigger)").on("mouseoff",function(){
		var root=$(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
		root.find('.sub-menu:visible').hide();
	});
});

//Collapse / Expand
$('.expandcollapse').click(function() {

        var newstate = $(this).attr('state') ^ 1,
            icon = newstate ? "plus" : "minus",
            text = newstate ? "Expand" : "Collapse";
    
        // if state=0, show all the accordion divs within the same block (in this case, within the same section)
        if ( $(this).attr('state')==="0" ) {
            console.log('1');
            $(this).siblings('div').find('div.accordion-body.in').collapse('hide');
			$('a.accordion-toggle').each(function() {
				$(this).addClass('collapsed');
			});
        }
        // otherwise, collapse all the divs
        else {
            console.log('2');
            $(this).siblings('div').find('div.accordion-body:not(.in)').collapse('show');
			$('a.accordion-toggle').each(function() {
				
				$(this).removeClass('collapsed')
			});
        }

        $(this).html("<i class=\"fa-" + icon + "-circle\"></i> " + text +" All");

        $(this).attr('state',newstate)

    });

    $('a[data-toggle="tab"]').on('shown', function (e) {

        var myState = $(this).attr('state'),
            state = $('.expandcollapse').attr('state');

        if(myState != state) {
          toggleTab($(this).prop('hash'));
          $(this).attr('state',state);
        }

    })

    function toggleTab(id){

        $(id).find('.collapse').each(function() {
            $(this).collapse('toggle');
          });

    }
// Tooltip
	jQuery(function ($) {
        $('[rel=tooltip]').tooltip()
    });
	
// Popover
	jQuery(function ($) {
        $('[rel=popover]').popover()
    });
	


jQuery(function ($) {
	$('#popover').popover({
		container: 'body',
		trigger: "hover"
	});   
 });


// Increase/Decrease text size 

$('#incfont').click(function(){    
        curSize= parseInt($('#wrap').css('font-size')) + 2;
  if(curSize<=19)
        $('#wrap').css('font-size', curSize);
        });  
  $('#decfont').click(function(){    
        curSize= parseInt($('#wrap').css('font-size')) - 2;
  if(curSize>=11)
        $('#wrap').css('font-size', curSize);
}); 

// Show/Hide Search Criteria  
  
  function toggledisplay (it, box) {
var vis = (box.checked) ? "none" : "block";
document.getElementById(it).style.display = vis;
}

  /*
// Multi-select Dropdown  
	$('#multi-select').multiselect({
			        	includeSelectAllOption: true
			        });
	
// affix the navbar after scroll below header 
$('#nav-new').affix({
      offset: {
        top: $('header').height()+$('#nav-new').height()
      }
});	

//Date Picker
$(function() {
    $('.datetimepicker').datetimepicker({
      pickTime: false
    });
  });
*/
	