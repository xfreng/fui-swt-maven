package com.fui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fui.dao.organization.OrganizationMapper;
import com.fui.model.Organization;

@Service("organizationService")
public class OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	public void addOrganization(Organization record) {
		this.organizationMapper.insert(record);
	}
}