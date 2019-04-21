package de.oglimmer.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.oglimmer.db.dao.CommunicationChannelDao;
import de.oglimmer.db.dao.PersonDao;
import de.oglimmer.db.entity.Person;
import de.oglimmer.web.transfer.EditWizard;
import de.oglimmer.web.util.DateConverter;
import de.oglimmer.web.util.ExtractId;
import de.oglimmer.web.util.ObjectToHex;

@WebServlet(urlPatterns = "/edit/*")
public class EditController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private PersonDao personDao = new PersonDao();
	private CommunicationChannelDao commChannelDao = new CommunicationChannelDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = ExtractId.getId(req, "edit");
		EditWizard wiz = new EditWizard(personDao.loadById(id), commChannelDao.loadByPerson(id));
		showScreen1(req, resp, wiz);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EditWizard wiz = (EditWizard) ObjectToHex.decode(req.getParameter("dataCache"));
		if (req.getParameter("btnCancel") != null) {
			Integer id = wiz.getEdit1().getId();
			if (id != null) {
				resp.sendRedirect(req.getContextPath() + "/view/" + id);
			} else {
				resp.sendRedirect(req.getContextPath() + "/list");
			}
			return;
		}
		if (req.getParameter("screen").equals("1")) {
			screen1(req, resp, wiz);
		} else if (req.getParameter("screen").equals("2")) {
			screen2(req, resp, wiz);
		} else {
			throw new RuntimeException("Illegal screen value " + req.getParameter("screen"));
		}
	}

	private void screen1(HttpServletRequest req, HttpServletResponse resp, EditWizard wiz)
			throws IOException, ServletException {
		boolean valid = transferDataScreen1(req, wiz);
		valid |= validateScreen1(req, wiz);
		if (!valid) {
			if (req.getParameter("btnSaveExit") != null) {
				save(req, resp, wiz);
			} else {
				showScreen2(req, resp, wiz);
			}
		} else {
			showScreen1(req, resp, wiz);
		}
	}

	private boolean validateScreen1(HttpServletRequest req, EditWizard wiz) {
		boolean notValid = false;
		if (wiz.getEdit1().getFirstname().trim().isEmpty()) {
			req.setAttribute("error_firstname", "Must not be empty!");
			notValid = true;
		}
		if (wiz.getEdit1().getSurname().trim().isEmpty()) {
			req.setAttribute("error_surname", "Must not be empty!");
			notValid = true;
		}
		if (!wiz.getEdit1().getBirthday().trim().isEmpty()) {
			if (!DateConverter.isValid(wiz.getEdit1().getBirthday().trim())) {
				req.setAttribute("error_birthday", "Must be a legal date");
				notValid = true;
			}
		}
		if (!wiz.getEdit1().getHeight().trim().isEmpty()) {
			try {
				Integer.parseInt(wiz.getEdit1().getHeight());
			} catch (NumberFormatException e) {
				req.setAttribute("error_height", "Must be a number");
				notValid = true;
			}
		}
		return notValid;
	}

	private void screen2(HttpServletRequest req, HttpServletResponse resp, EditWizard wiz)
			throws ServletException, IOException {
		transferDataScreen2(req, wiz);
		if (req.getParameter("btnBack") != null) {
			showScreen1(req, resp, wiz);
		} else if (req.getParameter("btnAddRow") != null) {
			wiz.getEdit2().addRow();
			showScreen2(req, resp, wiz);
		} else {
			save(req, resp, wiz);
		}
	}

	private void showScreen1(HttpServletRequest req, HttpServletResponse resp, EditWizard wiz)
			throws ServletException, IOException {
		req.setAttribute("dataCache", ObjectToHex.encode(wiz));
		req.setAttribute("edit", wiz.getEdit1());
		req.getRequestDispatcher("/WEB-INF/views/edit1.jsp").forward(req, resp);
	}

	private void showScreen2(HttpServletRequest req, HttpServletResponse resp, EditWizard wiz)
			throws ServletException, IOException {
		req.setAttribute("dataCache", ObjectToHex.encode(wiz));
		req.setAttribute("commChannels", wiz.getEdit2());
		req.getRequestDispatcher("/WEB-INF/views/edit2.jsp").forward(req, resp);
	}

	private boolean transferDataScreen1(HttpServletRequest req, EditWizard wiz) {
		boolean notValid = false;
		wiz.getEdit1().setFirstname(req.getParameter("firstname"));
		wiz.getEdit1().setSurname(req.getParameter("surname"));
		wiz.getEdit1().setStreet(req.getParameter("street"));
		wiz.getEdit1().setCity(req.getParameter("city"));
		wiz.getEdit1().setZip(req.getParameter("zip"));
		wiz.getEdit1().setBirthday(req.getParameter("birthday"));
		wiz.getEdit1().setHeight(req.getParameter("height"));
		return notValid;
	}

	private void transferDataScreen2(HttpServletRequest req, EditWizard wiz) {
		wiz.getEdit2().getCommunicationChannels().forEach(e -> {
			e.setData(req.getParameter("data_" + e.getId()));
			e.setType(req.getParameter("type_" + e.getId()));
		});
	}

	private void save(HttpServletRequest req, HttpServletResponse resp, EditWizard wiz) throws IOException {
		Person person = wiz.extractPerson();
		personDao.updateOrSave(person);
		if (wiz.getEdit2().getPersonId() == null) {
			wiz.getEdit2().initPersonId(person.getId());
		}
		commChannelDao.updateOrSave(wiz.extractCommunicationChannel());
		resp.sendRedirect(req.getContextPath() + "/view/" + person.getId());
	}

}
