package de.oglimmer.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.oglimmer.db.entity.CommunicationChannel;
import de.oglimmer.db.util.ConnectionProvider;

public class CommunicationChannelDao {

	public List<CommunicationChannel> loadByPerson(Integer id) {
		List<CommunicationChannel> resultList = new ArrayList<>();
		if (id != null) {
			try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
				try (PreparedStatement prst = con.prepareStatement(
						"select id, person_id, type, data from communicationchannel where person_id=?")) {
					prst.setInt(1, id);
					try (ResultSet rs = prst.executeQuery()) {
						while (rs.next()) {
							CommunicationChannel p = new CommunicationChannel();
							p.setId(rs.getInt("id"));
							p.setPersonId(rs.getInt("person_id"));
							p.setType(rs.getString("type"));
							p.setData(rs.getString("data"));
							resultList.add(p);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return resultList;
	}

	public void updateOrSave(List<CommunicationChannel> listCommChannels) {
		try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
			for (CommunicationChannel cc : listCommChannels) {
				if (cc.getId() < 1) {
					insert(con, cc);
				} else {
					update(con, cc);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void update(Connection con, CommunicationChannel cc) throws SQLException {
		try (PreparedStatement prst = con
				.prepareStatement("update communicationchannel set type=?, data=? where id = ?")) {
			prst.setString(1, cc.getType());
			prst.setString(2, cc.getData());
			prst.setInt(3, cc.getId());
			prst.executeUpdate();
		}
	}

	private void insert(Connection con, CommunicationChannel cc) throws SQLException {
		try (PreparedStatement prst = con
				.prepareStatement("insert into communicationchannel (person_id, type, data) values (?, ?, ?)")) {
			prst.setInt(1, cc.getPersonId());
			prst.setString(2, cc.getType());
			prst.setString(3, cc.getData());
			prst.executeUpdate();
		}
	}

	public void deleteByPerson(Integer id) {
		try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
			try (PreparedStatement prst = con.prepareStatement("delete from communicationchannel where person_id=?")) {
				prst.setInt(1, id);
				prst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
