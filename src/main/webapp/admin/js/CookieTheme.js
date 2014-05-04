Ext.onReady(function() {
	var cp = new Ext.state.CookieProvider();
	var AdStatETheme = cp.get("AdStatEThemeCSS");
	if(!AdStatETheme)
		AdStatETheme = 'blue';
		
	var i,
        a,
        links = document.getElementsByTagName("link"),
        len = links.length;
    for (i = 0; i < len; i++) {
        a = links[i];
        if (a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
            a.disabled = true;
            if (a.getAttribute("title") == AdStatETheme) a.disabled = false;
        }
    }	
});