package de.oglimmer.web.util;

import javax.servlet.http.HttpServletRequest;

public class ExtractId {

	public static Integer getId(HttpServletRequest req, String uriName) {
		String uri = req.getRequestURI();
		String searchToken = req.getContextPath() + "/" + uriName + "/";
		String idStr = uri.substring(uri.indexOf(searchToken) + searchToken.length());
		try {
			return Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
