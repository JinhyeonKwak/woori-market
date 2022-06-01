package com.mayy5.admin.type;

public enum PostType {

	NOTICE("공지사항"),
	FREE("자유게시판");

	private String value;

	PostType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
