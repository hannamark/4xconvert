var Help = {
	url : "/accrual/helpDocs/coppaAccrual/index.html?context=coppaAccrual&topic=",
	popHelp: function(topic) {
		if(topic.length < 1){
			topic=null;
		}
		//window.open (Help.url+topic, "Help", "status,scrollbars,resizable,width=800,height=500");
		WWHAPI_Object("/accrual/helpDocs/coppaAccrual", null);
		WWHAPI_DisplayHelpWithNavigation("coppaAccrual", topic);
		//alert(Help.url+topic);
	},
	insertHelp: function(topic)	{
		var ex = arguments[1] ? arguments[1] : "";
		var exst = arguments[2] ? arguments[2] : "";
		var htm = "<img "+ ex + " style=\"cursor:pointer;"+ exst + "\" src=\"images/help_icon.gif\" alt=\"help\" onclick=\"Help.popHelp(\'"+topic+"\');\" />";
		document.write(htm);
	},
	popHelpMain: function() {
		var topic = "Welcome";
		//window.open (Help.url+topic, "Help", "status,scrollbars,resizable,width=800,height=500");
		WWHAPI_Object("/accrual/helpDocs/coppaAccrual", null);
		WWHAPI_DisplayHelpWithNavigation("coppaAccrual", topic);
		//alert(Help.url+topic);
	}
    
}