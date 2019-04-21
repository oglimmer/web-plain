package de.oglimmer.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.oglimmer.db.dao.CommunicationChannelDao;
import de.oglimmer.db.dao.PersonDao;
import de.oglimmer.web.util.ExtractId;

@WebServlet(urlPatterns = "/view/*")
public class ViewController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private PersonDao personDao = new PersonDao();
	private CommunicationChannelDao commChannelDao = new CommunicationChannelDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = ExtractId.getId(req, "view");
		req.setAttribute("person", personDao.loadById(id));
		req.setAttribute("commChannels", commChannelDao.loadByPerson(id));
		req.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(req, resp);
	}

}
