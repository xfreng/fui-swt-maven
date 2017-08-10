package com.fui.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fui.common.utils.MD5Utils;
import com.fui.dao.user.UserMapper;
import com.fui.model.User;

@Service("userService")
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public JSONObject login(String userName, String password) {
		JSONObject json = new JSONObject();
		json.put("state", "1");
		User user = userMapper.findUserByCode(userName);
		if (user == null) {
			json.put("state", "0");
			json.put("message", "用户不存在");
		} else {
			if (!user.getPassword().equals(MD5Utils.encodeByMD5(password))) {
				json.put("state", "0");
				json.put("message", "用户密码错误");
			}
		}
		return json;
	}

	/**
	 * 分页查询用户信息
	 *
	 * @param params
	 *            查询条件
	 * @return 用户信息列表
	 */
	public List<User> getUserList(Map<String, Object> params) {
		return userMapper.getUserList(params);
	}
}
