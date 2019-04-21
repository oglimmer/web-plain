package de.oglimmer.web.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.oglimmer.db.entity.CommunicationChannel;

public class Edit2 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer personId;
	private List<CommunicationChannel> list;

	public Edit2(Integer personId, List<CommunicationChannel> loadedCommChannel) {
		this.personId = personId;
		if (loadedCommChannel != null) {
			this.list = loadedCommChannel;
		} else {
			this.list = new ArrayList<>();
		}
		addRow();
	}

	public List<CommunicationChannel> getCommunicationChannels() {
		return list;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void addRow() {
		CommunicationChannel cc = new CommunicationChannel();
		Optional<Integer> currentMinId = list.stream().map(c -> c.getId()).reduce(Math::min);
		cc.setId(currentMinId.isPresent() ? currentMinId.get() - 1 : 0);
		cc.setPersonId(personId);
		this.list.add(cc);
	}

	public void initPersonId(Integer id) {
		list.forEach(e -> e.setPersonId(id));
	}

}
