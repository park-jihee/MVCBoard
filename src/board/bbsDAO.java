package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.User;

public class bbsDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	//mysql에 접속해 주는 부분
	public bbsDAO() {
		try {
			//localhost:3306 포트는 컴퓨터에 설치된 mysql주소
			String dbURL = "jdbc:mysql://localhost:3306/board?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String dbID = "root";
			String dbPW = "1010qkrwlgml";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPW);
		} catch (Exception e) {
			e.printStackTrace(); //오류출력
		}
	}
	
	public String getDate() {
		String sql = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int getNext() {
		String sql = "SELECT bID FROM BBS ORDER BY bID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) + 1;				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int write(String bTITLE, String userID, String bCONTENT) {
		String sql = "INSERT INTO BBS VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bTITLE);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bCONTENT);
			pstmt.setInt(6,1);
			
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<bbs> getList(int pageNumber){
		String sql = "SELECT * FROM BBS WHERE bID<? bAVAILABLE=1 ORDER BY bID DESC LIMIT 10";
		ArrayList<bbs> list = new ArrayList<bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()-(pageNumber-1)*10);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bbs bbs = new bbs();
				bbs.setbID(rs.getInt(1));
				bbs.setbTITLE(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setbDATE(rs.getString(4));
				bbs.setbCONTENT(rs.getString(5));
				bbs.setbAVAILABLE(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String sql = "SELECT * FROM BBS WHERE bID<? bAVAILABLE=1 ORDER BY bID DESC LIMIT 10";
		ArrayList<bbs> list = new ArrayList<bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()-(pageNumber-1)*10);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public bbs getbbs(int bID) {
		String sql = "SELECT * FROM BBS WHERE bID =?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bbs bbs = new bbs();
				bbs.setbID(rs.getInt(1));
				bbs.setbTITLE(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setbDATE(rs.getString(4));
				bbs.setbCONTENT(rs.getString(5));
				bbs.setbAVAILABLE(rs.getInt(6));

				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bID, String bTITLE, String bCONTENT) {
		String sql = "UPDATE BBS SET bTITLE=?, bCONTENT=?, WHERE bID=?";
		try {
			PreparedStatement pstmt  = conn.prepareStatement(sql);
			pstmt.setString(1, bTITLE);
			pstmt.setString(1, bCONTENT);
			pstmt.setInt(1, bID);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int delete(int bID) {
		String sql = "UPDATE BBS SET bAVAILABLE=0 WHERE bID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bID);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
