package suricateserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GuestDAO {

	public static String getGuestNameByCode(String code) throws SQLException {

		Connection connexion = null;
		
		String response = "";

		try {

			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			String req = "";
			if(code.split(":").length == 2) {
				String query = "select idguest from nfcaccess where nfccode like ?";
				PreparedStatement preparedStatement = connexion.prepareStatement(query);
				preparedStatement.setString(1, code);
				ResultSet setCode = preparedStatement.executeQuery();
				setCode.next();
				req = "select name from guest where idguest=" + setCode.getInt("idguest");
			} else {
				String query = "select idguest from pinaccess where pincode like ?";
				PreparedStatement preparedStatement = connexion.prepareStatement(query);
				preparedStatement.setString(1, code);
				ResultSet setCode = preparedStatement.executeQuery();
				setCode.next();
				req = "select name from guest where idguest=" + setCode.getInt("idguest");
			}
			
			Statement statement = connexion.createStatement();
			ResultSet set = statement.executeQuery(req);

			if (set.next()) {
				response = set.getString("name");

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
