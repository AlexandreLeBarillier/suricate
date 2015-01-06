package suricateserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import suricateserver.dto.InformationResponse;

public class InformationDAO {
	/**
	 * Récupération de la version du serveur.
	 * 
	 * @throws SQLException 
	 * @throws ResultsDAOException
	 */
	public static InformationResponse getInformation() throws SQLException {

		InformationResponse response = new InformationResponse();

		Connection connexion = null;

		try {

			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			Statement statement = connexion.createStatement();
			String req = "select * from version";
			ResultSet set = statement.executeQuery(req);

			while (set.next()) {
				response.setContent(set.getString("numero"));
				break;

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (connexion != null)
				try {
					connexion.close();
				} catch (SQLException ignore) {
					/*
					 * nothing to do...
					 */
				}
		}

		return response;
	}
}
