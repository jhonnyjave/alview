/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snp.gob.bo.entidades.bean.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import snp.gob.bo.almodel.entidad.listaMenu;
import snp.gob.bo.almodel.servicio.ListaUsuarioRolService;
import snp.gob.bo.entidades.bean.common.AbstractManagedBean;

/**
 *
 * @author victor
 */
@ManagedBean
@ViewScoped
public class ModuloBean extends AbstractManagedBean implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -6661605235696671629L;

	@ManagedProperty(value = "#{listaUsuarioRolService}")
	private ListaUsuarioRolService listaUsuarioRolService;

	private List<listaMenu> listaUsuarioRol = new ArrayList<listaMenu>();
	boolean btnSigno = true;

	@PostConstruct
	public void init() {
		System.out.println("Entra a ModuloBean con login session:" + super.getLoginSession());

	}

	public void saveMessage() {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Successful", "Your message: "));
		context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
	}

	public String seteaSesionMenu(String idSistema) throws Exception {
		this.listaUsuarioRol.clear();
		super.setIdSistema(idSistema);
		System.out.println("ID sistema aqui::" + super.getIdSistema());
		this.listaUsuarioRol = this.listaUsuarioRolService.getUsuarioRol(super.getLoginSession(), super.getPass(),
				super.getIdSistema());
		System.out.println("tam listaUsuarioRol::" + this.listaUsuarioRol.size());
		if (this.listaUsuarioRol.size() != 0) {
			if (idSistema.equals("2")) {
				// setera la variable de sesion para que la pagina se renderice
				// por el tipo de modulo
				setCodigoPaginaSession("RECA");
			} else if (idSistema.equals("1")) {
				setCodigoPaginaSession("REGS");
			} else if (idSistema.equals("3")) {
				setCodigoPaginaSession("NOTI");
			} else if (idSistema.equals("4")) {
				setCodigoPaginaSession("DIGI");
			}

			super.setMuestraMenu(true);
			return "/login/signosPrincipal.xhtml";
		} else {
			System.out.println("No ES USUARIO CORRECTO");
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null,
					new FacesMessage("No Tiene Permisos", "Comuniquese con el administrador de sistemas"));

			return "";
		}

	}

	public ListaUsuarioRolService getListaUsuarioRolService() {
		return this.listaUsuarioRolService;
	}

	public void setListaUsuarioRolService(ListaUsuarioRolService listaUsuarioRolService) {
		this.listaUsuarioRolService = listaUsuarioRolService;
	}

	public List<listaMenu> getListaUsuarioRol() {
		return this.listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<listaMenu> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	public void cambiarEstadoSignoFalse() {
		this.btnSigno = false;
		System.out.println("cambiado b");
	}

	public void cambiarEstadoSignoTrue() {
		this.btnSigno = true;
		System.out.println("cambiado a");
	}

	public boolean isBtnSigno() {
		return this.btnSigno;
	}

	public void setBtnSigno(boolean btnSigno) {
		this.btnSigno = btnSigno;
	}

}
