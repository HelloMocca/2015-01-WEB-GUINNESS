package org.nhnnext.guinness.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nhnnext.guinness.controller.notes.ReadNoteListServlet;
import org.nhnnext.guinness.exception.MakingObjectListFromJdbcException;
import org.nhnnext.guinness.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class AbstractDao {
	private static final Logger logger = LoggerFactory.getLogger(ReadNoteListServlet.class);
	
	static Gson gson = new Gson();
	static Type GroupList = new TypeToken<List<Group>>() {
	}.getType();

	protected Connection getConnection() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/GUINNESS";
		String id = "link413";
		String pw = "link413";
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, id, pw);
	}

	/**
	 * 
	 * @param sql
	 *            쿼리문
	 * @param parameters
	 *            sql 쿼리의 인자
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void queryNotForReturn(String sql, String... parameters) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement pstmt = setPreparedStatement(conn, sql, parameters);
		pstmt.executeUpdate();
		terminateResources(conn, pstmt);
	}

	/**
	 * 
	 * @param sql
	 *            쿼리문
	 * @param parameters
	 *            sql 쿼리의 인자
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int queryForCountReturn(String sql, String... parameters) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement pstmt = setPreparedStatement(conn, sql, parameters);
		ResultSet rs = pstmt.executeQuery();
		rs.last();
		int result = rs.getRow();
		terminateResources(conn, pstmt);
		return result;
	}

	/**
	 * 
	 * @param cls
	 *            리스트 객체의 클래스 타입
	 * @param params
	 *            cls의 생성
	 * @param sql
	 *            쿼리문
	 * @param parameters
	 *            sql 쿼리의 인자
	 * @return cls형 리스트 객체
	 * @throws MakingObjectListFromJdbcException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<?> queryForReturn(Class<?> cls, String[] params, String sql, String... parameters) throws SQLException,
			MakingObjectListFromJdbcException {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		PreparedStatement pstmt = setPreparedStatement(conn, sql, parameters);
		ResultSet rs = pstmt.executeQuery();
		List<?> array = getListObject(cls, params, rs);
		terminateResources(conn, pstmt, rs);
		return array;
	}

	private PreparedStatement setPreparedStatement(Connection conn, String sql, String... parameters)
			throws SQLException {
		int index = 1;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for (String parms : parameters)
			pstmt.setString(index++, parms);
		return pstmt;
	}

	private List<Object> getListObject(Class<?> cls, String[] paramsKey, ResultSet rs)
			throws MakingObjectListFromJdbcException {
		int sizeOfParam = paramsKey.length;
		List<Object> list = new ArrayList<Object>();
		try {
			Class<?>[] fields = new Class[sizeOfParam];
			for (int i = 0; i < sizeOfParam; i++)
				fields[i] = cls.getDeclaredField(paramsKey[i]).getType();
			Constructor<?> ct = cls.getConstructor(fields);
			Object[] paramsValue = new Object[sizeOfParam];

			while (rs.next()) {
				for (int indexOfcolumn = 0; indexOfcolumn < sizeOfParam; indexOfcolumn++) {
					switch (fields[indexOfcolumn].getSimpleName().toString()) {
					case "String":
						paramsValue[indexOfcolumn] = rs.getString(paramsKey[indexOfcolumn]);
						break;
					case "char":
						paramsValue[indexOfcolumn] = rs.getString(paramsKey[indexOfcolumn]).charAt(0);
						break;
					case "byte":
						paramsValue[indexOfcolumn] = rs.getByte(paramsKey[indexOfcolumn]);
						break;
					default:
						throw new MakingObjectListFromJdbcException();
					}
				}
				list.add(ct.newInstance(paramsValue));
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SQLException | NoSuchFieldException | SecurityException | NoSuchMethodException e) {
			throw new MakingObjectListFromJdbcException(e);
		}
		return list;
	}

	protected void terminateResources(Connection conn, PreparedStatement pstmt) throws SQLException {
		terminateResources(conn, pstmt, null);
	}

	protected void terminateResources(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
		if (conn != null)
			conn.close();
		if (pstmt != null)
			pstmt.close();
		if (rs != null)
			rs.close();
	}
}
