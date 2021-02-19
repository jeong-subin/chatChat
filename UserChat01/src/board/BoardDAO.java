package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DBManager;

public class BoardDAO {
	
	//게시물 등록
	public int boardWrite(String userID, String boardTitle, String boardContent, String boardFile,String boardRealFile){
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		String sql= "INSERT INTO BOARD (USERID, BOARDID, BOARDTITLE, BOARDCONTENT, BOARDDATE, BOARDHIT,BOARDFILE, BOARDREALFILE, BOARDGROUP, BOARDSEQUENCE, BOARDLEVEL,BOARDAVAILABLE) values(?,boardID_seq.nextval,?,?,sysdate,0,?,?,nvl((select max(BOARDGROUP)+1 from board),0),0,0,1)";
			
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
			while(rs.next()) {
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
				board.setBoardAvailable(rs.getInt("boardAvailable"));
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
	
	public ArrayList<BoardDTO> getList(){
		ArrayList<BoardDTO> boardList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;// 
		String sql= "select * from board order by boardGroup DESC, boardSequence ASC";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<BoardDTO>();
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
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
				board.setBoardAvailable(rs.getInt("boardAvailable"));
				boardList.add(board);
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
		return boardList;
		
	}
	public int hit(String boardId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql= "update board set boardHit = boardHit +1 where boardID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);
			pstmt.executeUpdate();
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
	
	public String getFile(String boardID) {
		BoardDTO board = new BoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select boardFile from board where boardID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				return rs.getString("boardFile");
			}
				
			return "";
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs !=null) rs.close();
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return "";
		
	}	
	public String getRealFile(String boardID) {
		BoardDTO board = new BoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sql= "select boardRealFile from board where boardID = ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				return rs.getString("boardRealFile");
			}
				
			return "";
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs !=null) rs.close();
					if(pstmt !=null) pstmt.close();
					if(conn !=null) conn.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return "";
		
	}	
	
	public int update(String userID, String boardID, String boardTitle, String boardContent, String boardFile,String boardRealFile){
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		String sql= "update BOARD set BOARDTITLE=?, BOARDCONTENT=?, BOARDFILE=?, BOARDREALFILE=? where boardID = ?";
			
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setString(3, boardFile);
			pstmt.setString(4, boardRealFile);
			pstmt.setInt(5, Integer.parseInt(boardID));
			
			pstmt.executeUpdate();
			
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
		System.out.println("555");
		return -1; // db오류
	}	

	public int delete(String boardID){
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		String sql= "UPDATE BOARD SET boardAvailable = 0 where boardID = ?";
			
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(boardID));
			
			pstmt.executeUpdate();
			
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
		 // db오류
		return 0;
	
	}	
	
	public int reply(String userID, String boardTitle, String boardContent, String boardFile,String boardRealFile, BoardDTO parent){
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		String sql= "INSERT INTO BOARD (USERID, BOARDID, BOARDTITLE, BOARDCONTENT, BOARDDATE, BOARDHIT,BOARDFILE, BOARDREALFILE, BOARDGROUP, BOARDSEQUENCE, BOARDLEVEL,BOARDAVAILABLE) values(?,boardID_seq.nextval,?,?,sysdate,0,?,?,?,?,?,1)";
			
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.setString(4, boardFile);
			pstmt.setString(5, boardRealFile);
			pstmt.setInt(6, parent.getBoardGroup());
			pstmt.setInt(7, parent.getBoardSequence()+1);
			pstmt.setInt(8, parent.getBoardLevel()+1);
			
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
	// 부모글을 입력을 받아서 
	public int replyUpdate(BoardDTO parent){
		Connection conn = null;
		PreparedStatement pstmt = null;

		
		String sql= "update board set boardSequence = boardSequence +1 where boardGroup = ? and boardSequence > ?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parent.getBoardGroup());
			pstmt.setInt(2, parent.getBoardSequence());
			pstmt.executeUpdate();
			
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
	
}
