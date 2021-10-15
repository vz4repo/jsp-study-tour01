package comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import connection.DBConnect;

public class commentDAO {
  DBConnect db = new DBConnect();

  // 댓글 추가
  public void commentInsert(commentDTO dto) {
    Connection conn = db.getConnection();
    PreparedStatement pstmt = null;
    String sql = "insert into maria_study.comment (num,userId,contents) values (?,?,?)";

    try {
      pstmt = conn.prepareStatement(sql);

      // 바인딩
      pstmt.setString(1, dto.getNum());
      pstmt.setString(2, dto.getUserId());
      pstmt.setString(3, dto.getContents());

      // 실행
      pstmt.execute();


    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      db.dbClose(pstmt, conn);
    }
  }

  // 전체갯수
  public int getTotalCount() {
    int n = 0;
    Connection conn = db.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select count(*) from maria_study.comment";

    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        n = rs.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      db.dbClose(rs, pstmt, conn);
    }
    return n;
  }

  // 전체 댓글 출력
  public List<commentDTO> getAllAnswer(String num) {
    List<commentDTO> list = new Vector<commentDTO>();
    Connection conn = db.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select * from maria_study.comment where num=? order by idx";

    try {
      pstmt = conn.prepareStatement(sql);

      // 바인딩
      pstmt.setString(1, num);

      // 실행
      rs = pstmt.executeQuery();

      while (rs.next()) {
        commentDTO dto = new commentDTO();
        dto.setIdx(rs.getString("idx"));
        dto.setNum(rs.getString("num"));
        dto.setUserId(rs.getString("userId"));
        dto.setContents(rs.getString("contents"));
        dto.setWriteday(rs.getTimestamp("writeday"));

        // list 추가
        list.add(dto);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      db.dbClose(rs, pstmt, conn);
    }
    return list;
  }

  // 페이지에서 필요한 만큼만 리턴
  public List<commentDTO> getcommentList(int start, int perpage) {
    List<commentDTO> list = new Vector<commentDTO>();
    Connection conn = db.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "select * from maria_study.comment order by num desc limit ?,?";

    try {
      pstmt = conn.prepareStatement(sql);

      // 바인딩
      pstmt.setInt(1, start);
      pstmt.setInt(2, perpage);

      // 실행
      rs = pstmt.executeQuery();

      while (rs.next()) {
        commentDTO dto = new commentDTO();
        dto.setIdx(rs.getString("idx"));
        dto.setNum(rs.getString("num"));
        dto.setUserId(rs.getString("userId"));
        dto.setContents(rs.getString("contents"));
        dto.setWriteday(rs.getTimestamp("writeday"));

        // list에 추가
        list.add(dto);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      db.dbClose(rs, pstmt, conn);
    }
    return list;
  }

  // 삭제
  public void deleteAnswer(String idx) {
    Connection conn = db.getConnection();
    PreparedStatement pstmt = null;
    String sql = "delete from maria_study.comment where idx=?";

    try {
      pstmt = conn.prepareStatement(sql);

      // 바인딩
      pstmt.setString(1, idx);

      // 실행
      pstmt.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      db.dbClose(pstmt, conn);
    }
  }
}
