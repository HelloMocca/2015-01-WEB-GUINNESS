package org.nhnnext.guinness.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class NoteDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/GUINNESS";
		String id = "link413";
		String pw = "link413";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url,id,pw);	
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void createNote(Note note) throws SQLException {
		String query = "insert into NOTES (noteText, targetDate, userId, groupId) values(?, ?, ?, ?)";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, note.getNoteText());
			pstmt.setString(2, note.getTargetDate());
			pstmt.setString(3, note.getUserId());
			pstmt.setString(4, note.getGroupId());
			pstmt.executeUpdate();
		} finally {
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}
	}

	public NoteList findByGroupId(String groupId, String targetDate) {
		String sql = "select * from NOTES, USERS where notes.userId = users.userId AND groupId = ? AND targetDate = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupId);
			pstmt.setString(2, targetDate);
			rs = pstmt.executeQuery();
			NoteList noteList = new NoteList();
			
			if (!rs.next()) {
				return null;
			} else {
				rs.beforeFirst();
				while(rs.next()) {
					noteList.getItems().add(new Note(rs.getString("noteId"),
							rs.getString("noteText"), rs.getString("targetDate"), 
							rs.getString("userId"), rs.getString("groupId"), rs.getString("userName")));
				}
			}
			return noteList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			terminateConnection();
		}
		return null;
	}
	
	private void terminateConnection() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
