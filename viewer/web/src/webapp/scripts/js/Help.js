var Help = {
    url : "/viewer/helpDocs/COPPAviewer/index.html?context=COPPAviewer&topic=",
    popHelp: function(topic) {
        if(topic.length < 1){
            topic=null;
        }
        //window.open (Help.url+topic, "Help", "status,scrollbars,resizable,width=800,height=500");
        WWHAPI_Object("/viewer/helpDocs/COPPAviewer", null);
        WWHAPI_DisplayHelpWithNavigation("COPPAviewer", topic);
        //alert(Help.url+topic);
    },
    insertHelp: function(topic)    {
        var ex = arguments[1] ? arguments[1] : "";
        var exst = arguments[2] ? arguments[2] : "";
        var htm = "<img "+ ex + " style=\"cursor:pointer;"+ exst + "\" src=\"images/help_icon.gif\" alt=\"help\" onclick=\"Help.popHelp(\'"+topic+"\');\" />";
        document.write(htm);
    },
    popHelpMain: function() {
        var topic = "Welcome";
        //window.open (Help.url+topic, "Help", "status,scrollbars,resizable,width=800,height=500");
        WWHAPI_Object("/viewer/helpDocs/COPPAviewer", null);
        WWHAPI_DisplayHelpWithNavigation("COPPAviewer", topic);
        //alert(Help.url+topic);
    }
}