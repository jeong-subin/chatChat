package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import board.BoardDTO;
import util.DBManager;

public class BoardDAO {
	
	//게시물 등록
	public int boardWrite(String userID, String boardTitle, String boardContent, String boardFile,String boardRealFile){
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		String sql= "INSERT INTO BOARD (USERID, BOARDID, BOARDTITLE, BOARDCONTENT, BOARDDATE, BOARDHIT,BOARDFILE, BOARDREALFILE, BOARDGROUP, BOARDSEQUENCE, BOARDLEVEL) values(?,boardID_seq.nextval,?,?,sysdate,0,?,?,nvl((select max(BOARDGROUP)+1 from board),0),0,0)";
			
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.setString(4, boardFile);
			pstmt.setString(5, boardRealFile);
			
			pstmt.executeUpdate();
			
		}catch (Exception e) {
				e.printStackTrace();
				System.out.println("222222");
				// TODO: handle exception
			}finally {
				try {
					System.out.println("33333");
				
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				System.out.println("44444");
				e.printStackTrace();
			}	
		}
		System.out.println("555");
		return -1; // db오류
	}	
	
	public BoardDTO getBoard(String boardID){
		BoardDTO board = new BoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select * from board where boardID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setUserID(rs.getString("userID"));
				board.setBoardID(rs.getInt("boardID"));
				board.setBoardTitle(rs.getString("boardTitle"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate"));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardFile(rs.getString("boardFile"));
				board.setBoardRealFile(rs.getString("boardRealFile"));
				board.setBoardGroup(rs.getInt("boardGroup"));
				board.setBoardSequence(rs.getInt("boardSequence"));
				board.setBoardLevel(rs.getInt("boardLevel"));
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
		return board;
		
	}	
}
