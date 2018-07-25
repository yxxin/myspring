package com.yx.myspring.service.v4;

import com.yx.myspring.dao.v3.AccountDao;
import com.yx.myspring.dao.v3.ItemDao;
import com.yx.myspring.stereotype.Component;

@Component(value="yx1")
public class TestYX {
	private AccountDao accountDao;
	private ItemDao itemDao;

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

}
