package com.yw.loop.bean;

public class LoopBean {
	private String id;// ������id
	private int resId;// ͼƬ��Դid
	private String text;// ����

	public LoopBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoopBean(String id, int resId, String text) {
		super();
		this.id = id;
		this.resId = resId;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
