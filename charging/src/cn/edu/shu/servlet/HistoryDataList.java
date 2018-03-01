package cn.edu.shu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.shu.entity.Data;
import cn.edu.shu.service.impl.DataAcqService;
import cn.edu.shu.utils.PageBean;

public class HistoryDataList extends HttpServlet {
private DataAcqService das=new DataAcqService();
private String uri="";
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.get current page 
		try {
			String currentPage=request.getParameter("currentPage");
			
			//System.out.println("page:"+pageCount);
			//写session
			HttpSession session=request.getSession();
//			String id=(String)session.getAttribute("id");
//			session.setAttribute("id", id);
			
			String startD=(String) session.getAttribute("startD");
			String endD=(String) session.getAttribute("endD");
			String pageCount=(String) session.getAttribute("pageCount");
//			System.out.println("pageCount:"+pageCount);
//			System.out.println("1.startD:"+startD);
//			System.out.println("1.endD:"+endD);
			//判空
			if(currentPage==null||"".equals(currentPage)){
				currentPage="1";
			}
			int curPage=Integer.parseInt(currentPage);
			PageBean <Data> pageBean=new PageBean<Data>();
			pageBean.setCurrentPage(curPage);
			if(startD==null||"".equals(startD)){
				startD="20160523222034001";
				session.setAttribute("startD", startD);
			}
			if(endD==null||"".equals(endD)){
				endD="20161208192906067";
				session.setAttribute("endD", endD);		
			}
			if(pageCount==null||"".equals(pageCount)){
				pageCount="1000";  //每页默认显示1000行
				session.setAttribute("pageCount", pageCount);	
			}
			pageBean.setPageCount(Integer.parseInt(pageCount));
			pageBean.setEndD(endD);
			pageBean.setStartD(startD);
			das.getH5yDataPage(pageBean);
			request.setAttribute("pageBean", pageBean);
			uri="/WEB-INF/historyDataList.jsp";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			uri="/WEB-INF/error.jsp";
		}
		request.getRequestDispatcher(uri).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
