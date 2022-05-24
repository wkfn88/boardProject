package com.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.web.vo.MemberVO;

@Repository
public class MemberDAO {
	
	private JdbcTemplate template;
	
	@Autowired
	public MemberDAO(JdbcTemplate template) {
		this.template = template;
	}
	
	//회원가입
	public int createMember(MemberVO mVo) {
		String sql = "insert into member value (?, ?, ?, default)";
		int result = 0;
		try {
			result = template.update(sql, mVo.getMemberId(), mVo.getMemberPwd(), mVo.getMemberEmail());
			return result;
		}catch(Exception e) {
			return -1;
		}
		
	}
	
	//아이디 조회 ture = 중복이 없다, false = 중복이 있다.
		public boolean isMemberId(String memberId) {
			String sql = "select memberid from member where memberid = ?";
			
			List<String> pwd = template.query(sql, new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, memberId);
				}
				
			}, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					return rs.getString(1);
				}
			});
			
			if( pwd.size() == 0 ) return true;
			
			return false;
		}
	
	//아이디로 비밀번호 조회
	public String getMemberPwd(String memberId) {
		String sql = "select memberpwd from member where memberid = ?";
		
		List<String> pwd = template.query(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, memberId);
			}
			
		}, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getString(1);
			}
		});
		
		if( pwd.size() == 0 ) return null;
		
		return pwd.get(0);
	}
	
	public MemberVO getMember(String memberId) {
		String sql = "select * from member where memberid = ?"; 
		
		List<MemberVO> list = template.query(sql, new RowMapper<MemberVO>() {

			@Override
			public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO mVo = new MemberVO();
				mVo.setMemberId(rs.getString("memberid"));
				mVo.setMemberPwd(rs.getString("memberpwd"));
				mVo.setMemberEmail(rs.getString("memberemail"));
				mVo.setStatus(rs.getInt("status"));
				return mVo;
			}
			
		}, memberId);
		
		if(list.size() == 0 ) return null;
		
		return list.get(0);
	}
}
