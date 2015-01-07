package suricateserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import suricateserver.dto.NFCCreateAccessRequest;
import suricateserver.dto.NFCGetListResponse;
import suricateserver.dto.NFCVerifyAccessRequest;
import suricateserver.dto.NFCVerifyAccessResponse;

public class NFCAccessDAO {
	
	/**
	 * Insertion d'un badge NFC en base.
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void createNfcAccess(NFCCreateAccessRequest request)
			throws Exception {

		Connection connexion = null;
		
		try {
			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);
			
			/*
			 * création du guest
			 */
			String queryGuest = "INSERT INTO guest (name, photopath) VALUES (?, ?)";

			PreparedStatement preparedStatementGuest = connexion.prepareStatement(queryGuest, Statement.RETURN_GENERATED_KEYS);
			preparedStatementGuest.setString(1, request.getGuest());
			preparedStatementGuest.setString(2, "./"+request.getGuest());
			
			int statusGuest = preparedStatementGuest.executeUpdate();
			
			if (statusGuest == 0) {
				throw new Exception("Insertion Guest impossible");
			}
			
			ResultSet rs = preparedStatementGuest.getGeneratedKeys();
			int idGuest = 0;
			if(rs.next()) {
				idGuest = rs.getInt(1);
			} else {
				throw new Exception("Erreur lors de la récupération de idguest");
			}
			preparedStatementGuest.close();
			
			/*
			 * insertion du badge nfc
			 */
			String query = "INSERT INTO nfcaccess (nfccode, validity, validity_rule, idguest) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connexion.prepareStatement(query);
			preparedStatement.setString(1, request.getNfccode());
			preparedStatement.setString(2, request.getValidity());
			preparedStatement.setString(3, request.getValidity_rule());
			preparedStatement.setInt(4, idGuest);
			
			int statut = preparedStatement.executeUpdate();

			preparedStatement.close();
			if (statut == 0) {
				throw new Exception("Insertion NFC Acces impossible");
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
	
	public static NFCVerifyAccessResponse verify(NFCVerifyAccessRequest request) throws SQLException {

		NFCVerifyAccessResponse response = new NFCVerifyAccessResponse();

		Connection connexion = null;

		try {

			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			String query = "select validity, validity_rule from nfcaccess where nfccode like ?";
			PreparedStatement preparedStatement = connexion.prepareStatement(query);
			preparedStatement.setString(1, request.getNfccode());
			ResultSet set = preparedStatement.executeQuery();
			
			if(set.next()) {
				String validity = set.getString(1);
				validity = validity.trim();
				if("0".equals(validity)) {
					// accès permanent
					response.setResult(true);
					response.setMessage("Accès permanent autorisé");
				} else if("1".equals(validity)) {
					// accès temporaire
					response.setResult(true);
					response.setMessage("Accès temporaire autorisé");
				} else {
					response.setResult(false);
					response.setMessage("Date de validité dépassée");
				}
			} else {
				response.setResult(false);
				response.setMessage("Badge NFC invalide");
			}
			preparedStatement.close();
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
	
	public static NFCGetListResponse getList() throws SQLException {

		NFCGetListResponse response = new NFCGetListResponse();

		Connection connexion = null;

		try {

			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			String query = "select nfccode from nfcaccess where validity like \'%0%\' or validity like \'%1%\'";
			PreparedStatement preparedStatement = connexion.prepareStatement(query);
			ResultSet set = preparedStatement.executeQuery();

			HashMap<String, String> badges = new HashMap<String, String>();
			String nfccode = "";
			String[] tab = new String[2];
			while(set.next()) {
				nfccode = set.getString(1);
				tab = nfccode.split(":");
				badges.put(tab[0], tab[1]);
			}
			response.setBadges(badges);

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
