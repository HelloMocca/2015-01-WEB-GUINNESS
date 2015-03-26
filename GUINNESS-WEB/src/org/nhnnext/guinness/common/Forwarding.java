package org.nhnnext.guinness.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Forwarding {
	public static void ForwardForError(HttpServletRequest req,
			HttpServletResponse resp, String errorMessage, String destination)
			throws ServletException, IOException {
		req.setAttribute("errorMessage", errorMessage);
		RequestDispatcher rd = req.getRequestDispatcher(destination);
		rd.forward(req, resp);
	}
}
