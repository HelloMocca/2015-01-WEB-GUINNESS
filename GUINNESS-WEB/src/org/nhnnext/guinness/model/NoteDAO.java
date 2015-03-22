package org.nhnnext.guinness.model;

import java.sql.SQLException;

public class NoteDAO extends AbstractDAO {

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
			terminateConnection();
		}
	}
}