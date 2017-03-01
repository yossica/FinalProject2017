package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
	private ArrayList<String> urlList=null;
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain fc) throws IOException, ServletException {
		//https://www.java-forums.org/java-servlet/86281-session-check-filter-redirect-page-index-jsp.html
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String url = request.getServletPath();
         
		boolean allowedRequest = false;

		if (urlList.contains(url)) {
			//kalau termasuk ke dalam login/user handler, tidak perlu cek session
			//allowed url bisa di cek di web.xml
			allowedRequest = true;
		}

		if (!allowedRequest) {
			HttpSession session = request.getSession(true);
			if (null == session
					|| session.getAttribute("username") == null) {
				//kalau belum ada session, redirect ke login
				response.sendRedirect("login.do");
			} else {
				fc.doFilter(req, res);
				return;
			}
		} else {
			fc.doFilter(req, res);
			return;
		}
	}

	public void init(FilterConfig fc) throws ServletException {
		String urls = fc.getInitParameter("avoid_urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList<String>();
  
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());  
        }		
	}

}
