var Help = {
	popHelp : function(topic) {
		if (topic == "usersGuide") {
			window.open(Help.ugUrl);
		} else {
			var windowUrl = Help.url;
			if (topic.length < 1) {
				topic = null;
			}
			if (topic != null) {
				windowUrl = windowUrl + "-" + topic;
			}
			window.open(windowUrl);
		}
	}
};
