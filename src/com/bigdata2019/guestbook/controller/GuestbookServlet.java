package com.bigdata2019.guestbook.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("a");
		if("deleteform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
		} else if("add".equals(action)) {
			request.setCharacterEncoding("UTF-8");
			
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			String contents = request.getParameter("contents");
			
			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(pwd);
			vo.setContents(contents);
			
			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);
			
			response.sendRedirect("/guestbook2/gb");
		} else if("delete".equals(action)) {
			request.setCharacterEncoding("UTF-8");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String inputPwd = request.getParameter("pwd");
			
			GuestbookDao vo = new GuestbookDao();
			String dbPwd = vo.getPwd(no);
			String parseInputPwd = vo.getInputPwd(inputPwd);
			
			if(dbPwd.equals(parseInputPwd)) {
				vo.delete(no);
			}
			
			response.sendRedirect("/guestbook2/gb")
		} else {
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.findAll();
			
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp")
			rd.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
