package com.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.web.vo.CommentVO;
import com.web.vo.FreeBoardVO;

@Repository
public class BoardDAO {

	private JdbcTemplate template;

	@Autowired
	public BoardDAO(JdbcTemplate template) {
		this.template = template;
	}
	
	//-------------------------------------------------------------------------------------------
	//---------------------------------------- 게시글 부분 ----------------------------------------
	//-------------------------------------------------------------------------------------------
	
	// 게시물끝번호를 가져와서 1를 더해서 리턴
	public int getLastBoardNum() {
		String sql = "select boardnum from board order by boardnum desc";

		List<Integer> list = template.query(sql, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1)+1;
			}
		});
		if (list.size() == 0)
			return 1;

		return list.get(0);
	}
	
	//조회수 증가
	public void readCountUp(int boardNum) {
		String sql = "update board set readcount = readcount + 1 where boardnum = ?";
		template.update(sql, boardNum);
	}
	
	// 페이징에 필요한 페이지 수를 반환하는 메서드
	public int getFreeBoardPageCount() {
		String sql = "select count(*) from board where status = 1";

		List<Integer> list = template.query(sql, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		
		if( list.get(0) == 0 ) return 1;
		if( list.get(0) % 11 == 0 ) return  list.get(0) / 11;
		return ( list.get(0) / 11 ) + 1;
	}
	
	//검색시에 카운트를 받아오는 메서드 ( 오버로딩하여 사용 )
	public int getFreeBoardPageCount(String type, String searchWord) {
		String sql = String.format("select count(*) from board where status = 1 and %s like '%s'", type, "%"+searchWord+"%");
		List<Integer> list = template.query(sql, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		
		if( list.get(0) == 0 ) return 1;
		if( list.get(0) % 11 == 0 ) return  list.get(0) / 11;
		return ( list.get(0) / 11 ) + 1;
	}
	
	// 게시글 글 목록 조회
	public List<FreeBoardVO> getFreeBoardList(int pageNum) {
		String sql = "select * FROM board where status = 1 order by boardnum desc limit ?, 11";

		List<FreeBoardVO> list = template.query(sql, new RowMapper<FreeBoardVO>() {

			@Override
			public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					FreeBoardVO bVo = new FreeBoardVO();
					bVo.setBoardNum(rs.getInt("boardnum"));
					bVo.setBoardTitle(rs.getString("boardtitle"));
					bVo.setMemberId(rs.getString("memberid"));
					bVo.setBoardDate(rs.getTimestamp("boarddate"));
					bVo.setReadCount(rs.getInt("readcount"));
					String timeMin = rs.getTimestamp("boarddate").toString().substring(0, 16);
					bVo.setTimeMin(timeMin);
					bVo.setRecommend(rs.getInt("recommend"));
					bVo.setComment(getCommentCount(rs.getInt("boardnum")));
				return bVo;
			}}, pageNum);
		
		return list;
	}
	
	// 최근 작성된 글 목록 조회
		public List<FreeBoardVO> getLatelyFreeBoardList() {
			String sql = "select * FROM board where status = 1 order by boardnum desc limit 0, 5";

			List<FreeBoardVO> list = template.query(sql, new RowMapper<FreeBoardVO>() {

				@Override
				public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						FreeBoardVO bVo = new FreeBoardVO();
						bVo.setBoardNum(rs.getInt("boardnum"));
						bVo.setBoardTitle(rs.getString("boardtitle"));
						bVo.setMemberId(rs.getString("memberid"));
						bVo.setBoardDate(rs.getTimestamp("boarddate"));
						bVo.setReadCount(rs.getInt("readcount"));
						String timeMin = rs.getTimestamp("boarddate").toString().substring(0, 16);
						bVo.setTimeMin(timeMin);
						bVo.setRecommend(rs.getInt("recommend"));
					return bVo;
				}});
			
			return list;
		}
		
		//오늘 날짜에 추천순으로 정렬해서 글 목록 조회
		public List<FreeBoardVO> getRecommendFreeBoardList() {
			String sql = "select * from board where date(boarddate) = ? and status = 1 order by recommend desc limit 0, 5";
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			String date = format.format(d); 
			
			List<FreeBoardVO> list = template.query(sql, new RowMapper<FreeBoardVO>() {

				@Override
				public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						FreeBoardVO bVo = new FreeBoardVO();
						bVo.setBoardNum(rs.getInt("boardnum"));
						bVo.setBoardTitle(rs.getString("boardtitle"));
						bVo.setMemberId(rs.getString("memberid"));
						bVo.setBoardDate(rs.getTimestamp("boarddate"));
						bVo.setReadCount(rs.getInt("readcount"));
						String timeMin = rs.getTimestamp("boarddate").toString().substring(0, 16);
						bVo.setTimeMin(timeMin);
						bVo.setRecommend(rs.getInt("recommend"));
					return bVo;
				}}, date);
			
			return list;
		}


	// 글 작성( 게시물 등록 )
	public int writeFreeBoard(FreeBoardVO bVo, int status) {
		String sql = "insert into board values(?, ?, ?, ?, ?, ?, ?, default, default, default)";

		int result = 0;
		try {
			result = template.update(sql, getLastBoardNum(), bVo.getBoardTitle(), bVo.getMemberId(), bVo.getBoardPwd(), bVo.getBoardContent(), bVo.getAnonymous(), status);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}
	
	// 글 삭제
	public int deleteFreeBoard(int boardNum) {
		String sql = "update board set status = 0 where boardnum = ?";
		return template.update(sql, boardNum);
	}
	
	// 게시글 상세보기
	public FreeBoardVO getFreeBoard(int boardNum) {
		String sql = "select * from board where boardNum = ?";

		List<FreeBoardVO> list = template.query(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, boardNum);
			}
			
		}, new RowMapper<FreeBoardVO>() {

			@Override
			public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				FreeBoardVO fbVo = new FreeBoardVO();
				fbVo.setBoardNum(rs.getInt("boardnum"));
				fbVo.setBoardTitle(rs.getString("boardtitle"));
				fbVo.setMemberId(rs.getString("memberid"));
				fbVo.setBoardPwd(rs.getString("boardpwd"));
				fbVo.setBoardContent(rs.getString("boardcontent"));
				fbVo.setBoardDate(rs.getTimestamp("boarddate"));
				fbVo.setReadCount(rs.getInt("readcount"));
				fbVo.setRecommend(rs.getInt("recommend"));
				fbVo.setAnonymous(rs.getInt("anonymous"));
				return fbVo;
			}
		});
		
		if(list.size() == 0) return null;
		
		return list.get(0);
	}
	
	public void updateBoard(FreeBoardVO fbVo) {
		String sql = "update board set boardtitle = ?, boardcontent = ? where boardnum = ?";
		template.update(sql, fbVo.getBoardTitle(), fbVo.getBoardContent(), fbVo.getBoardNum());
	}
	
	//게시글의 비밀번호를 확인
	public String checkBoardPwd(int boardNum) {
		String sql = "select boardpwd from board where boardnum = ?";
		
		List<String> list = template.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString(1);
			}
			
		}, boardNum);
		
		if(list.size() == 0 ) return null;
		
		return list.get(0);
	}
	
	//추천테이블에 추가, 추천게시글 번호 + 추천인 아이디
	public void recommendUp(int boardNum, String loginUser) {
		String sql = "insert into recommend values(?, ?, ?)";
		String recommendId = boardNum + loginUser;
		template.update(sql, recommendId, boardNum, loginUser);
		
		String sql2 = "update board set recommend = recommend + 1 where boardNum = ?";
		template.update(sql2, boardNum);
	}
	

	//해당 유저가 해당게시물에 추천을 했는지 체크하는 메서드 true = 추천안함, false = 추천함
	public boolean recommendCheck(int boardNum, String loginUser) {
		String recommendId = boardNum + loginUser;
		String sql = "select recommendid from recommend where recommendid = ?";
		List<String> list = template.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString(1);
			}
			
		}, recommendId);
		
		if( list.size() == 0 ) return true;
		
		return false;
	}
	
	//게시글 삭제
	public void deleteBoard(int boardNum) {
		String sql = "update board set status = 0 where boardNum = ?";
		template.update(sql, boardNum);
	}
	
	//게시글의 작성자를 얻어온다.
	public String getBoardMemberId(int boardNum) {
		String sql = "select memberid from board where boardnum = ?";
		List<String> list = template.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {

				return rs.getString(1);
			}
			
		}, boardNum);
		
		if ( list.size() == 0 ) return null;
		
		return list.get(0);
	}
	
	//검색기능
	public List<FreeBoardVO> searchBoard(String type, String searchWord, int pageNum) {
		String sql = String.format("select * from board where %s like '%s' and status = 1 order by boardnum desc limit %d, 11", type, "%"+searchWord+"%", pageNum);
		List<FreeBoardVO> list = template.query(sql, new RowMapper<FreeBoardVO>() {

			@Override
			public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				FreeBoardVO bVo = new FreeBoardVO();
				bVo.setBoardNum(rs.getInt("boardnum"));
				bVo.setBoardTitle(rs.getString("boardtitle"));
				bVo.setMemberId(rs.getString("memberid"));
				bVo.setBoardDate(rs.getTimestamp("boarddate"));
				bVo.setReadCount(rs.getInt("readcount"));
				String timeMin = rs.getTimestamp("boarddate").toString().substring(0, 16);
				bVo.setTimeMin(timeMin);
				bVo.setRecommend(rs.getInt("recommend"));
				bVo.setComment(getCommentCount(rs.getInt("boardnum")));
				return bVo;
			}
			
		});
		
		
		return list;
		
	}
	
	// 공지사항 리스트 조회
		public List<FreeBoardVO> getNoticeList(int pageNum) {
			String sql = "select * FROM board where status = 3 order by boardnum desc limit ?, 11";

			List<FreeBoardVO> list = template.query(sql, new RowMapper<FreeBoardVO>() {

				@Override
				public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						FreeBoardVO bVo = new FreeBoardVO();
						bVo.setBoardNum(rs.getInt("boardnum"));
						bVo.setBoardTitle(rs.getString("boardtitle"));
						bVo.setMemberId(rs.getString("memberid"));
						bVo.setBoardDate(rs.getTimestamp("boarddate"));
						bVo.setReadCount(rs.getInt("readcount"));
						String timeMin = rs.getTimestamp("boarddate").toString().substring(0, 16);
						bVo.setTimeMin(timeMin);
						bVo.setRecommend(rs.getInt("recommend"));
						bVo.setComment(getCommentCount(rs.getInt("boardnum")));
					return bVo;
				}}, pageNum);
			
			return list;
		}
		
		public FreeBoardVO getNotice() {
			String sql = "SELECT * FROM board where status = 3 ORDER by boarddate desc";
			
			List<FreeBoardVO> list = template.query(sql, new RowMapper<FreeBoardVO>() {

				@Override
				public FreeBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					FreeBoardVO bVo = new FreeBoardVO();
					bVo.setBoardNum(rs.getInt("boardnum"));
					bVo.setBoardTitle(rs.getString("boardtitle"));
					bVo.setMemberId(rs.getString("memberid"));
					bVo.setBoardDate(rs.getTimestamp("boarddate"));
					bVo.setReadCount(rs.getInt("readcount"));
					String timeMin = rs.getTimestamp("boarddate").toString().substring(0, 16);
					bVo.setTimeMin(timeMin);
					bVo.setRecommend(rs.getInt("recommend"));
					bVo.setComment(getCommentCount(rs.getInt("boardnum")));
					return bVo;
				}
			});
			
			return list.get(0);
		}
	//-------------------------------------------------------------------------------------------
	//---------------------------------------- 댓글 부분 ------------------------------------------
	//-------------------------------------------------------------------------------------------
	
	public int getCommentNext() {
		String sql = "select commentnext from comment order by commentnext desc";
		
		List<Integer> list = template.query(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getInt(1)+1;
			}
			
		});
		
		if( list.size() == 0 ) return 1;
		
		return list.get(0);
	}
	
	public String checkComment(String commentNum) {
		String sql = "select commentpwd from comment where commentnum = ?";
		
		List<String> list = template.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getString(1);
			}
			
		}, commentNum);
		
		if( list.size() == 0 ) return null;
		
		return list.get(0);
	}
	
	//댓글입력
	public int insertComment(CommentVO cVo, String commentNum) {
		String sql = "insert into comment values(?, ?, ?, ?, ?, default, ?, ?, default)";
		int result = template.update(sql, commentNum, cVo.getBoardNum(), getCommentNext(), cVo.getMemberId(),
				cVo.getCommentContent(), cVo.getCommentPwd(), cVo.getAnonymous());
		return result;
	}
	
	//댓글삭제
	public int deleteComment(String commentNum) {
		String sql = "update comment set status = 0 where commentnum = ?";
		return template.update(sql, commentNum);
	}
	
	//댓글조회
	public List<CommentVO> getCommentList(int boardNum) {
		String sql = "select * from comment where boardnum = ? and status = 1 order by boardnum";
		
		List<CommentVO> list = template.query(sql, new RowMapper<CommentVO>() {

			@Override
			public CommentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommentVO cVo = new CommentVO();
				cVo.setCommentNum(rs.getString("commentnum"));
				cVo.setBoardNum(rs.getInt("boardNum"));
				cVo.setCommentNext(rs.getInt("commentnext"));
				cVo.setMemberId(rs.getString("memberid"));
				cVo.setCommentContent(rs.getString("commentcontent"));
				cVo.setCommentDate(rs.getTimestamp("commentdate"));
				cVo.setCommentPwd(rs.getString("commentpwd"));
				cVo.setAnonymous(rs.getInt("anonymous"));
				return cVo;
			}
			
		}, boardNum);
		
		if( list.size() == 0 ) return null;
		
		return list;
	}
	
	public int getCommentCount(int boardNum) {
		String sql = "select count(*) from comment where boardnum = ? and status = 1";
		List<Integer> list = template.query(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getInt(1);
			}
			
		}, boardNum);
		
		return list.get(0);
	}
	
	//댓글의 작성자를 얻어온다.
		public String getCommentMemberId(String commentNum) {
			String sql = "select memberid from comment where commentnum = ?";
			List<String> list = template.query(sql, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {

					return rs.getString(1);
				}
				
			}, commentNum);
			
			if ( list.size() == 0 ) return null;
			
			return list.get(0);
		}

}
