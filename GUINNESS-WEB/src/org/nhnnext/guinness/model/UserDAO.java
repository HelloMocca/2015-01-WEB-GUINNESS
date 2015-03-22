package org.nhnnext.guinness.model;

import java.sql.SQLException;

public class UserDAO extends AbstractDAO {

	public Boolean createUser(User user) throws SQLException {
		String sql = "insert into USERS values(?,?,?,?,default)";
		String userId = user.getUserId();
		UserDAO userDao = new UserDAO();

		if (userDao.readUser(userId) != null) {
			System.out.println("존재하는 userId 입니다!");
			return false;
		}

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getUserPassword());
			pstmt.setString(4, null);
			pstmt.executeUpdate();
		} finally {
			terminateConnection();
		}
		return true;
	}

	public User readUser(String userId) throws SQLException {
		String sql = "select * from USERS where userId=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getString("userId"),
						rs.getString("userName"), rs.getString("userPassword"));
				return user;
			}
		} finally {
			terminateConnection();
		}
		return null;
	}
}
