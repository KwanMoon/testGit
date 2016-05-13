package com.kgs7276.board.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO {
	
	private static final String namespace="com.kgs7276.board.mapper.PointMapper";
	
	@Inject
	private SqlSession session;

	@Override
	public void updatePoint(String uid, int upoint) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("upoint", upoint);		
		
		session.update(namespace + ".updatePoint", map);		

	}

}
