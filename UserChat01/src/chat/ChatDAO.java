package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DBManager;

public class ChatDAO {
	
	ChatDAO() {
	}

	private static ChatDAO instance = new ChatDAO();

	public static ChatDAO getInstance() {
		return instance;
	}

	
	//채팅리스트
	public ArrayList<ChatDTO> getChatListByID(String fromID,String toID,String chatID){
		ArrayList<ChatDTO> chatList = null;
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from chat where((fromID = ? AND toID =?) or (fromID =? and toID = ?)) and chatID > ? order by chatTime";
		try {
			ChatDTO cDTO = new ChatDTO();
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);// 자신이 봤던간에 보내던간에
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs=pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				cDTO.setChatID(rs.getInt("chatID"));
				cDTO.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setChatTime(rs.getString("chatTime").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				chatList.add(cDTO);			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null)rs.close();
				if(pstmt !=null)pstmt.close();
				if(conn !=null)conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	//최근 대화내용을 뽑아서 보여줌
	public ArrayList<ChatDTO> getChatListByRecent(String fromID,String toID,int number){
		ArrayList<ChatDTO> chatList = null;
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from chat where((fromID = ? AND toID =?) or (fromID =? and toID = ?)) and chatID > (select max(chatID) - ? from chat where (fromID = ? and toID = ?) or(fromID =? and toID=?)) order by chatTime";
		try {
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);// 자신이 봤던간에 보내던간에
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			pstmt.setString(6, fromID);
			pstmt.setString(7, toID);
			pstmt.setString(8, toID);
			pstmt.setString(9, fromID);
			rs=pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO cDTO = new ChatDTO();
				cDTO.setChatID(rs.getInt("chatID"));
				cDTO.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setChatTime(rs.getString("chatTime").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				chatList.add(cDTO);
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null)rs.close();
				if(pstmt !=null)pstmt.close();
				if(conn !=null)conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	
	
	public ArrayList<ChatDTO> getBox(String userID){
		ArrayList<ChatDTO> chatList = null;
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from chat where chatID in(select max(chatID) from chat where toID =? or fromID=? group by fromID,toID)";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);// 자신이 봤던간에 보내던간에
			rs=pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO cDTO = new ChatDTO();
				cDTO.setChatID(rs.getInt("chatID"));
				cDTO.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				cDTO.setChatTime(rs.getString("chatTime").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\n", "<br>"));
				chatList.add(cDTO);
			}
				for(int i =0; i < chatList.size(); i++) {
					ChatDTO x = chatList.get(i);
					for(int j = 0; j< chatList.size(); j++) {
						ChatDTO y = chatList.get(j);
						if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
							if(x.getChatID()<y.getChatID()) {
								chatList.remove(x);
								
								i--;
								break;	
							}else {
								chatList.remove(y);
								
								j--;
							}
						}
					}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null)rs.close();
				if(pstmt !=null)pstmt.close();
				if(conn !=null)conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	//메시지전송
	public int submit(String fromID,String toID,String chatContent){
		ArrayList<ChatDTO> chatList = null;
		Connection conn= null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into chat(chatID,fromID,toID,chatContent,chatRead) values(chat_sqe.nextval,?,?,?,0)"; //날자는 ?안씀 자동으로 db에서 생성
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);// 자신이 봤던간에 보내던간에
			pstmt.setString(3, chatContent);
			return pstmt.executeUpdate(); // insert성공시 1을반환
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBManager.close(conn,pstmt);
			}catch (Exception e) {
				
			}
		}
		return -1; //db오류;
	}
	
	public int readChat(String fromID, String toID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql = "update chat set chatRead = 1 where (fromID = ? and toID = ?)";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, toID);
			pstmt.setString(2, fromID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null )rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null)rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
	

	public int getAllUnreadChat(String userID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql = "select count(chatID) from chat where toID = ? and chatRead = 0";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs  = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("count(chatID)");
			}
			return 0; //받은 메시지 없음.
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null )rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null)rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
	
	// 대화별 안읽은 메시지 출력
	public int getUnreadChat(String fromID,String toID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql = "select count(chatID) from chat where fromID =? and toID = ? and chatRead = 0";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			rs  = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("count(chatID)");
			}
			return 0; //받은 메시지 없음.
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null )rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null)rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //데이터베이스 오류
	}
	
}
