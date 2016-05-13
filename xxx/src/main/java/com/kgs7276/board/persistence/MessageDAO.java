package com.kgs7276.board.persistence;

import com.kgs7276.board.domain.MessageVO;

public interface MessageDAO {
	
	/*메시지 생성*/
	public void create(MessageVO message) throws Exception;
	
	/*메세지 읽기*/
	public MessageVO read(Integer mid) throws Exception;
	
	/*메시지 읽은 날짜 수정 */
	public void updateState(Integer mid) throws Exception;
	
}