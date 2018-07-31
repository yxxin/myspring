package com.yx.myspring.service.v4;

import com.yx.myspring.beans.factory.annotation.Autowired;
import com.yx.myspring.dao.v4.AccountDao;
import com.yx.myspring.dao.v4.ItemDao;
import com.yx.myspring.stereotype.Component;

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

}
