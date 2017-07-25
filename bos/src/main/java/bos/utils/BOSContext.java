package bos.utils;

import org.apache.struts2.ServletActionContext;

import bos.domain.User;

public class BOSContext {
	
	public static User getLoginUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}

}
