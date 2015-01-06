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

			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			String query = "INSERT INTO Player (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connexion.prepareStatement(query);
			preparedStatement.setString(0, firstname);
			preparedStatement.setString(1, lastname);
			preparedStatement.setString(2, email);
			preparedStatement.setBytes(3, password);
			
			int statut = preparedStatement.executeUpdate();

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
