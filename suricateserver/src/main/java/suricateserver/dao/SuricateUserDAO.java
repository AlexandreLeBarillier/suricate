package suricateserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import suricateserver.dto.SignUpRequest;

public class SuricateUserDAO {

	/**
	 * Insertion du nouvel utilisateur en base.
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void createUser(SignUpRequest request)
			throws Exception {

		Connection connexion = null;
		String firstname = request.getFirstname();
		String lastname = request.getLastname();
		String email = request.getEmail();
		byte[] password = request.getPassword();
		
		try {

			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			String query = "INSERT INTO suricateuser (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connexion.prepareStatement(query);
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setString(3, email);
			preparedStatement.setBytes(4, password);
			
			int statut = preparedStatement.executeUpdate();

			preparedStatement.close();
			
			if (statut == 0) {
				throw new Exception("Insertion impossible");
			}

		} catch (SQLException e) {
			throw new Exception(
					"Erreur SQL lors de l'insertion", e);
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
	}
}
