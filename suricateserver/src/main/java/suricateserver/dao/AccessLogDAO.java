package suricateserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import suricateserver.dto.AccessLogDTO;
import suricateserver.dto.AccessLogResponse;

public class AccessLogDAO {

	/**
	 * R�cup�ration des access logs.
	 * 
	 * @throws SQLException 
	 * @throws ResultsDAOException
	 */
	public static AccessLogResponse getAccessLog() throws SQLException {

		AccessLogResponse response = new AccessLogResponse();

		Connection connexion = null;

		try {

			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass);
			connexion = DriverManager.getConnection(DAOUtils.DB_URL, DAOUtils.DB_USER,
					DAOUtils.DB_PWD);

			Statement statement = connexion.createStatement();
			String req = "select * from accesslog";
			ResultSet set = statement.executeQuery(req);
			List<AccessLogDTO> accesslogs = new ArrayList<AccessLogDTO>();

			while (set.next()) {
				AccessLogDTO a = new AccessLogDTO();
				java.util.Date utilDate = new java.util.Date(set.getDate("create_date").getTime());
				a.setDate(utilDate);
				a.setContent(set.getString("content"));
				accesslogs.add(a);
			}
			response.setAccesslogs(accesslogs);
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
