package de.oglimmer.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.oglimmer.db.dao.PersonDao;

@WebServlet(urlPatterns = "/list")
public class ListController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private PersonDao personDao = new PersonDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String searchFirstname = req.getParameter("searchFirstname");
		String searchSurname = req.getParameter("searchSurname");
		String sort = req.getParameter("sort");
		String sortOrder = req.getParameter("sortOrder");
		String sortSurname = req.getParameter("sortSurname");
		String sortFirstname = req.getParameter("sortFirstname");
		int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
		if (req.getParameter("btnPage") != null) {
			page = Integer.parseInt(req.getParameter("btnPage"));
		}

		int numRecords = personDao.loadNumberPersonsSummary(searchFirstname, searchSurname);

		int pageStart = Math.max(1, page - 3);
		int pageEnd = Math.max(1, Math.min(page + 3, (int) Math.ceil(numRecords / 10f)));
		page = Math.min(page, pageEnd); 
		pageStart = Math.min(page, pageStart); 

		if (sortSurname != null) {
			if (sort.equals("surname")) {
				if (sortOrder.equals("asc")) {
					sortOrder = "desc";
				} else {
					sortOrder = "asc";
				}
			} else {
				sort = "surname";
				sortOrder = "asc";
			}
		}
		if (sortFirstname != null) {
			if (sort.equals("firstname")) {
				if (sortOrder.equals("asc")) {
					sortOrder = "desc";
				} else {
					sortOrder = "asc";
				}
			} else {
				sort = "firstname";
				sortOrder = "asc";
			}
		}
		req.setAttribute("pageStart", pageStart);
		req.setAttribute("pageEnd", pageEnd);
		req.setAttribute("page", page);
		req.setAttribute("searchFirstname", searchFirstname);
		req.setAttribute("searchSurname", searchSurname);
		req.setAttribute("sort", sort);
		req.setAttribute("sortOrder", sortOrder);
		req.setAttribute("allPersons",
				personDao.loadAllPersonsSummary(page, sort, sortOrder, searchFirstname, searchSurname));
		req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
	}

}
