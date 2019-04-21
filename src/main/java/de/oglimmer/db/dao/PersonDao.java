package de.oglimmer.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import de.oglimmer.db.entity.Person;
import de.oglimmer.db.util.ConnectionProvider;
import de.oglimmer.db.util.SingleTableSQLBuilder;

public class PersonDao {

	public int loadNumberPersonsSummary(String searchFirstname, String searchSurname) {
		SingleTableSQLBuilder sql = SingleTableSQLBuilder.table("person").columns("count(*)");
		if (searchFirstname != null && !searchFirstname.isEmpty()) {
			sql.where("firstname like ?", searchFirstname);
		}
		if (searchSurname != null && !searchSurname.isEmpty()) {
			sql.where("surname like ?", searchSurname);
		}
		try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
			try (PreparedStatement prst = con.prepareStatement(sql.toString())) {
				sql.setParams(prst);
				try (ResultSet rs = prst.executeQuery()) {
					rs.next();
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Person> loadAllPersonsSummary(int page, String sort, String sortOrder, String searchFirstname,
			String searchSurname) {

		SingleTableSQLBuilder sql = SingleTableSQLBuilder.table("person").columns("id", "firstname", "surname")
				.limit((page - 1) * 10, 10);
		if (searchFirstname != null && !searchFirstname.isEmpty()) {
			sql.where("firstname like ?", searchFirstname);
		}
		if (searchSurname != null && !searchSurname.isEmpty()) {
			sql.where("surname like ?", searchSurname);
		}
		if (sort != null && !sort.isEmpty()) {
			sql.sort(sort, sortOrder);
		}

		List<Person> resultList = new ArrayList<>();
		try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
			String sqlString = sql.toString();
			try (PreparedStatement prst = con.prepareStatement(sqlString)) {
				sql.setParams(prst);
				try (ResultSet rs = prst.executeQuery()) {
					while (rs.next()) {
						Person p = new Person();
						p.setId(rs.getInt("id"));
						p.setFirstname(rs.getString("firstname"));
						p.setSurname(rs.getString("surname"));
						resultList.add(p);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return resultList;
	}

	public Person loadById(Integer id) {
		if (id != null) {
			try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
				try (PreparedStatement prst = con.prepareStatement(
						"select id, firstname, surname, street, zip, city, birthday, height from person where id = ?")) {
					prst.setInt(1, id);
					try (ResultSet rs = prst.executeQuery()) {
						while (rs.next()) {
							Person p = new Person();
							p.setId(rs.getInt("id"));
							p.setFirstname(rs.getString("firstname"));
							p.setSurname(rs.getString("surname"));
							p.setStreet(rs.getString("street"));
							p.setZip(rs.getString("zip"));
							p.setCity(rs.getString("city"));
							p.setBirthday(rs.getDate("birthday"));
							p.setHeight((Integer) rs.getObject("height"));
							return p;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public void updateOrSave(Person person) {
		try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
			if (person.getId() == null) {
				insert(person, con);
			} else {
				update(person, con);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void update(Person person, Connection con) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(
				"update person set firstname=?, surname=?, street=?, zip=?, city=?, birthday=?, height=? where id = ?")) {
			prst.setString(1, person.getFirstname());
			prst.setString(2, person.getSurname());
			prst.setString(3, person.getStreet());
			prst.setString(4, person.getZip());
			prst.setString(5, person.getCity());
			prst.setDate(6, person.getBirthday(), GregorianCalendar.getInstance());
			prst.setObject(7, person.getHeight());
			prst.setInt(8, person.getId());
			prst.executeUpdate();
		}
	}

	private void insert(Person person, Connection con) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(
				"insert into person (firstname, surname, street, zip, city, birthday, height) values (?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS)) {
			prst.setString(1, person.getFirstname());
			prst.setString(2, person.getSurname());
			prst.setString(3, person.getStreet());
			prst.setString(4, person.getZip());
			prst.setString(5, person.getCity());
			prst.setDate(6, person.getBirthday(), GregorianCalendar.getInstance());
			prst.setObject(7, person.getHeight());
			int affectedRows = prst.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = prst.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					person.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteById(Integer id) {
		try (Connection con = ConnectionProvider.INSTANCE.getConnection()) {
			try (PreparedStatement prst = con.prepareStatement("delete from person where id=?")) {
				prst.setInt(1, id);
				prst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
