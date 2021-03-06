package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.DBManager;

public class UserDAO {
	

	
	public int login(String userID,String userPassword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select * from user1 where userID=?";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("userPassword").equals(userPassword)) {
					return 1; //로그인 성공
				}
				return 2; // 비밀번호가 틀림
			}else {
				return 0; // 해당 사용자가 존재하지 않음
			}
				
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally {
				try {
					if(rs !=null) rs.close();
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return -1; // db오류
	}
	// 중복체크
	public int registerCheck(String userID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select * from user1 where userID=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next() || userID.equals("")) {
				return 0; //이미 존재하는 회원
				}
				return 1; //가입 가능한 회원 아이디
			
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally {
				try {
					if(rs !=null) rs.close();
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return -1; // db오류
	}	
	//회원가입
	public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql= "INSERT INTO USER1 VALUES(?,?,?,?,?,?,?)";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setInt(4,Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.setString(7, userProfile);
			return pstmt.executeUpdate();
		
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally {
				try {
				
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return -1; // db오류
	}	
	
	public UserDTO getUser(String userID){
		UserDTO user = new UserDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select * from user1 where userID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next() || userID.equals("")) {
				user.setUserID(userID);
				user.setUserName(rs.getString("userName"));
				user.setUserPassword(rs.getString("userPassword"));
				user.setUserEmail(rs.getString("userEmail"));
				user.setUserAge(rs.getInt("userAge"));
				user.setUserGender(rs.getString("userGender"));
				user.setUserProfile(rs.getString("userProfile"));
				}
				
			
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally {
				try {
					if(rs !=null) rs.close();
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return user;
		
	}	
	
	public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql= "update  user1 set userPassword = ?, userName=?, userAge=?, userGender=?, userEmail=? where userID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPassword);
			pstmt.setString(2, userName);
			pstmt.setInt(3, Integer.parseInt(userAge));
			pstmt.setString(4,userGender);
			pstmt.setString(5, userEmail);
			pstmt.setString(6, userID);
			
			return pstmt.executeUpdate();
		
			}catch (Exception e) {
				e.printStackTrace();
		
			}finally {
				try {
				
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return -1; // db오류
	}	
	
	
	public int profile(String userID,String userProfile){
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql= "update  user1 set  userProfile = ? where userID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userProfile);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate();
		
			}catch (Exception e) {
				e.printStackTrace();
		
			}finally {
				try {
				
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return -1; // db오류
	}	
	
	public String getProfile(String userID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select userProfile from user1 where userID=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("userProfile") == null) {
					return "http://localhost:8090/UserChat01/images/icon.png";
				}
				return "http://localhost:8090/UserChat01/upload/"+rs.getString("userProfile");
				}
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally {
				try {
					if(rs !=null) rs.close();
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return "http://localhost:8090/UserChat01/images/icon.png";
	}	
}
				
		


