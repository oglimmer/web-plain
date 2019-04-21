package de.oglimmer.web.transfer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import de.oglimmer.db.entity.CommunicationChannel;
import de.oglimmer.db.entity.Person;

public class EditWizard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Edit1 edit1;
	private Edit2 edit2;

	public EditWizard(Person person, List<CommunicationChannel> commChannel) {
		edit1 = new Edit1(person);
		edit2 = new Edit2(person != null ? person.getId() : null, commChannel);
	}

	public Person extractPerson() {
		return edit1.getAsPerson();
	}

	public List<CommunicationChannel> extractCommunicationChannel() {
		return edit2.getCommunicationChannels().stream().filter(e -> e.getData() != null && e.getType() != null)
				.filter(e -> e.getData().trim().length() > 0 && e.getType().trim().length() > 0)
				.collect(Collectors.toList());
	}

	public Edit1 getEdit1() {
		return edit1;
	}

	public Edit2 getEdit2() {
		return edit2;
	}

}
