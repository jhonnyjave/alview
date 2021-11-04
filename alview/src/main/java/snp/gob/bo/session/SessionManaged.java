/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snp.gob.bo.session;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import snp.gob.bo.almodel.entidad.listaMenu;

/**
 *
 * @author levi
 */
@ManagedBean(name = "sessionManaged")
@SessionScoped
public class SessionManaged implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7448965365589701082L;
	private List<listaMenu> listaUsuarioRolSesion;
	private Boolean apareceMenu = false;

	public List<listaMenu> getListaUsuarioRolSesion() {
		return this.listaUsuarioRolSesion;
	}

	public void setListaUsuarioRolSesion(List<listaMenu> listaUsuarioRolSesion) {
		this.listaUsuarioRolSesion = listaUsuarioRolSesion;
	}

	private String login;

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getApareceMenu() {
		return this.apareceMenu;
	}

	public void setApareceMenu(Boolean apareceMenu) {
		this.apareceMenu = apareceMenu;
	}

}
