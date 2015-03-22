package org.nhnnext.guinness.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractDAO {

	public void createGroup(Group group) {
		String sql = "insert into GROUPS values(?,?,?,DEFAULT,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, group.getGroupId());
			pstmt.setString(2, group.getGroupName());
			pstmt.setString(3, group.getGroupCaptainUserId());
			pstmt.setInt(4, group.isPublic());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
	}

	public void removeGroup(Group group) {
		String sql = "delete from GROUPS where groupId=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, group.getGroupId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
	}

	public void createGroupUser(String groupCaptainUserId, String groupId) {
		String sql = "insert into GROUPS_USERS values(?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupCaptainUserId);
			pstmt.setString(2, groupId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
	}

	public boolean checkExistGroupId(String groupId) {
		String sql = "select groupId from GROUPS where groupId=?";
		boolean result = false;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupId);
			rs = pstmt.executeQuery();
			rs.last();
			int rowcount = rs.getRow();
			if (rowcount != 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
		return result;
	}

	public Group findByGroupId(String groupId) {
		String sql = "select * from GROUPS where groupId = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupId);
			rs = pstmt.executeQuery();

			if (!rs.next())
				return null;

			return new Group(rs.getString("groupId"),
					rs.getString("groupName"),
					rs.getString("groupCaptainUserId"), rs.getInt("isPublic"));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
		return null;
	}

	public List<String> readGroupList(String userId) {
		String sql = "select A.groupId from GROUPS_USERS as A inner join USERS as B on A.userId = B.userId where A.userId =?";
		List<String> result = new ArrayList<String>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next())
				result.add(rs.getString("groupId"));
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
		return null;
	}
}
