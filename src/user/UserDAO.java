package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	//dao : 데이터베이스 접근 객체의 약자로서
	//실질적으로 db에서 회원정보를 불러오거나 db에 회원정보를 넣을 때
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//mysql에 접속해 주는 부분
	public UserDAO() {
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
	
	//로그인을 시도하는 함수
	public int login(String userID, String userPW) {
		String sql = "SELECT userPW FROM user WHERE userID=?";
		try {
			//pstmt : 정해진 sql문장을 db에 삽입하는 형식으로 인스턴스 가져옴
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).equals(userPW)) {
					return 1; //로긴 성공
				} else
					return 0; // 비밀번호 불일치
			}
			return -1; //아이디가 없음 오류
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류를 의미
	}
	
	public int join(User user) {
		String sql = "INSERT INTO USER VALUES (?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPW());
			pstmt.setString(3, user.getUserNAME());
			pstmt.setString(4, user.getUserGENDER());
			pstmt.setString(5, user.getUserEMAIL());
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
