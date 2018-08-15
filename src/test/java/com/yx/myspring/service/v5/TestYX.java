package com.yx.myspring.service.v5;

import com.yx.myspring.beans.factory.annotation.Autowired;
import com.yx.myspring.dao.v5.AccountDao;
import com.yx.myspring.dao.v5.ItemDao;
import com.yx.myspring.stereotype.Component;
import com.yx.myspring.util.MessageTracker;

@Component(value="yx1")
public class TestYX {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ItemDao itemDao;

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}
	
	public void test1() {
		System.out.println("testyx 1");
		MessageTracker.addMessage("testyx 1");
	}
	
	public void test1Exception() {
		throw new NullPointerException();
	}

}
