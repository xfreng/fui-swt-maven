package com.fui.test;

import com.fui.common.utils.SpringMvcUtils;
import com.fui.model.Organization;
import com.fui.service.OrganizationService;

public class App {

	public static void main(String[] args) {
		App app = new App();
		app.testAdd();
	}

	public void testAdd() {
		OrganizationService organizationService = SpringMvcUtils.get(OrganizationService.class);

		Organization record = new Organization();
		record.setId(1005L);
		record.setCode("test2");
		record.setName("test2");
		organizationService.addOrganization(record);
	}
}
