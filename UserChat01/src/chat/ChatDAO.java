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
		String sql = "select * from chat where((fromID = ? AND toID =?) or (fromID =? and toID = ?)) and chatID > (select max(chatID) - ? from chat) order by chatTime";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);// 자신이 봤던간에 보내던간에
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
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
	//메시지전송
	public int submit(String fromID,String toID,String chatContent){
		ArrayList<ChatDTO> chatList = null;
		Connection conn= null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into chat(chatID,fromID,toID,chatContent) values(chat_sqe.nextval,?,?,?)"; //날자는 ?안씀 자동으로 db에서 생성
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
	
}
