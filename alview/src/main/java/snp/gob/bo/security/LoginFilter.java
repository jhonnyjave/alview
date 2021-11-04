/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snp.gob.bo.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import snp.gob.bo.almodel.entidad.listaMenu;

/**
 *
 * @author levi
 */

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			// check whether session variable is set
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession();

			if (ses.getAttribute("listaUsuarioRol") != null) {
				System.out.println("La sesion no es nula , tam de la lista:"
						+ ((List<listaMenu>) ses.getAttribute("listaUsuarioRol")).size());
			} else {
				System.out.println("ES nula la sesion");
			}

			// allow user to proccede if url is login.xhtml or user logged in or
			// user is accessing any page in //public folder
			String reqURI = req.getRequestURI();
			String reqPath = req.getPathInfo();
			System.out.println("el url path es::" + reqPath);
			System.out.println("el uri  es::" + reqURI);
			if (reqPath.endsWith("/login.xhtml")) {
				chain.doFilter(request, response);

			}

			else {
				if (comparaLista((List<listaMenu>) ses.getAttribute("listaUsuarioRol"), reqPath)
						|| reqPath.endsWith("/modulo.xhtml")) {
					chain.doFilter(request, response);
				} else {
					res.sendRedirect(req.getContextPath() + "/home.xhtml"); // Anonymous
																			// user.
																			// Redirect
																			// to
																			// login
																			// page*/
				}

			}
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
	}

	public boolean comparaLista(List<listaMenu> lista, String reqPath) {
		boolean opcion = true;
		for (int i = 0; i <= lista.size() - 1; i++) {
			if (!reqPath.endsWith(lista.get(i).getUrl())) {
				opcion = false;
			}
		}
		return opcion;

	}

	@Override
	public void destroy() {

	}

}
