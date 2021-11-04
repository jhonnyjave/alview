/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snp.gob.bo.security;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import snp.gob.bo.entidades.bean.common.AbstractManagedBean;

/**
 *
 * @author levi
 */
@ManagedBean
@ViewScoped
public class VerificaSesion extends AbstractManagedBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2263277718516035387L;

	public void verifica() throws IOException {
		// System.out.println("el getLoginSession::"+super.getLoginSession());
		// System.out.println("el getPass()::"+super.getPass());
		// System.out.println("el
		// getIdUsuarioSession()::"+super.getIdUsuarioSession());

		// CharSequence cs1 = "login.xhtml";
		// HttpServletRequest request = (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext().getRequest();
		// String url = request.getPathInfo();
		// System.out.println("path::"+request.getPathInfo());
		// if (url.contains(cs1)) {
		// System.out.println("Es index");
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		// } else {

		if (super.getLoginSession() == null || super.getLoginSession().equals("")) {

			FacesContext.getCurrentInstance().getExternalContext().redirect("/giview/login/login.xhtml");
		}
		// }

		// System.out.println("la url es::"+url);
		// System.out.println("path:"+request.getContextPath());
		// System.out.println("name:"+request.getLocalName());
		// System.out.println("info:"+request.getPathInfo());
		// System.out.println("getContentType():"+request.getContentType());
		// System.out.println("getLocalAddr():"+request.getLocalAddr());
		// System.out.println("getRemoteAddr():"+request.getRemoteAddr());
		// System.out.println("getRequestURI():"+request.getRequestURI());

	}

	public void verificaLogin() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

}
