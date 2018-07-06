package com.yx.myspring.service.v2;

import com.yx.myspring.dao.v2.AccountDao;
import com.yx.myspring.dao.v2.ItemDao;

public class TestYX {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private String name;
	private int num;
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	public ItemDao getItemDao() {
		return itemDao;
	}
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
