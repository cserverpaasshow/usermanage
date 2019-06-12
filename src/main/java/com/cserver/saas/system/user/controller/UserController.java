package com.cserver.saas.system.user.controller;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cserver.saas.system.user.entity.User;
import com.cserver.saas.system.user.service.UserService;
import com.cserver.saas.system.user.util.JsonUtil;


@Controller
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	/***
	 * 修改
	 * @param model
	 * @param request
	 * @param rs
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateObjectUserController.action")
	public void updateObject(@ModelAttribute User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isSuccess = "Y";
		String message = "修改成功";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sameName", user.getName());
		map.put("notOwnId", user.getId());
		List<Object> list = userService.searchList(map);
		if(list.size() > 0) {
			isSuccess = "N";
			message = "名称重复";
		} else {
			user.setUpdateTime(new Date());
			User userUpdate = (User) userService.getObject(user.getId());
			BeanUtils.copyProperties(user, userUpdate, getNullPropertyNames(user));
			userService.updateObject(userUpdate);
		}
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("isSuccess", isSuccess);
		responseMap.put("message", message);
		responseWriter(responseMap, response);
	}
	/***
	 * 跳转修改页面
	 * @param model
	 * @param request
	 * @param rs
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/toUpdatePageUserController.action")
	public String toUpdatePage(ModelMap model, HttpServletRequest request,
			HttpServletResponse rs) throws IOException {
		String id = request.getParameter("id");
		User user = (User) userService.getObject(id);
		model.addAttribute("user", user);
		return "userUpdate";
	}
	/***
	 * 详情页面
	 * @param model
	 * @param request
	 * @param rs
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/toViewObjectUserController.action")
	public String toViewObject(ModelMap model, HttpServletRequest request,
			HttpServletResponse rs) throws IOException {
		String id = request.getParameter("id");
		User user = (User) userService.getObject(id);
		model.addAttribute("user", user);
		return "userDetail";
	}
	/***
	 * 删除
	 * @param model
	 * @param request
	 * @param rs
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteObjectUserController.action")
	public void deleteObject(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String isSuccess = "Y";
		String message = "删除成功";
		String id = request.getParameter("id");
		userService.deleteObject(id);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("isSuccess", isSuccess);
		responseMap.put("message", message);
		responseWriter(responseMap, response);
	}
	/***
	 * 新增
	 * @param model
	 * @param request
	 * @param rs
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/addObjectUserController.action")
	public void addObject(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String isSuccess = "Y";
		String message = "添加成功";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sameName", user.getName());
		List<Object> list = userService.searchList(map);
		if(list.size() > 0) {
			isSuccess = "N";
			message = "名称重复";
		}else {
			user = setInitParam(user, request); 
			userService.addObject(user);
		}
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("isSuccess", isSuccess);
		responseMap.put("message", message);
		responseWriter(responseMap, response);
	}
	/***
	 * 查询
	 * @param model
	 * @param request
	 * @param rs
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping("/searchListUserController.action")
	public void searchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = getInitPageMap(request);
		String name = request.getParameter("name");
		if(null != name) {
			name = java.net.URLDecoder.decode(name,"UTF-8");//转码
			map.put("name", name);
		}
		List<Object> userList = userService.searchList(map);
		long totalRows = userService.searchCounts(map);
		JsonUtil.returnJson(userList, totalRows, response);
	}
	private User setInitParam(User user, HttpServletRequest request) {
		user.setId(System.currentTimeMillis() + "");
		user.setUpdateTime(new Date());
		return user;
	}
	
}
