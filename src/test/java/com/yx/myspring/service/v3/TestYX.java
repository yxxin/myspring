package com.yx.myspring.service.v3;

import com.yx.myspring.dao.v3.AccountDao;
import com.yx.myspring.dao.v3.ItemDao;

public class TestYX {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private String name;
	private int num;

	public TestYX(AccountDao accountDao, ItemDao itemDao, String name, int num) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.name = name;
		this.num = num;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public String getName() {
		return name;
	}

	public int getNum() {
		return num;
	}
}
