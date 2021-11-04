/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snp.gob.bo.entidades.bean.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import snp.gob.bo.almodel.entidad.Etapa;
import snp.gob.bo.almodel.entidad.listaMenu;
import snp.gob.bo.almodel.servicio.EtapaService;
import snp.gob.bo.almodel.servicio.ListaUsuarioRolService;
import snp.gob.bo.entidades.bean.common.AbstractManagedBean;
import snp.gob.bo.security.VerificaSesion;

/**
 *
 * @author levi
 */
@ManagedBean(name = "usuarioLogeadoController")
@ViewScoped
public class UsuarioLogeadoController extends AbstractManagedBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -297581804715631440L;

	/*
	 * @ManagedProperty("#{sessionManaged}") private SessionManaged sessionBean;
	 * 
	 * @ManagedProperty("#{sessionState}") private SessionStateManagedBean
	 * sessionState;
	 */
	@ManagedProperty(value = "#{listaUsuarioRolService}")
	private ListaUsuarioRolService listaUsuarioRolService;

	@ManagedProperty(value = "#{etapaService}")
	private EtapaService etapaService;

	@ManagedProperty(value = "#{verificaSesion}")
	private VerificaSesion verificaSesion;

	private List<listaMenu> listaUsuarioRol = new ArrayList<>();
	private listaMenu usuarioRol;

	private String login;
	private String pass;
	// Para pintar el combo de roles
	private boolean pintaCombo;
	private Long idEtapa;
	List<Etapa> lisEtapas;

	@PostConstruct
	public void init() {

		// se setea null par qe no pinte el menu de arriba , en la pantalla de
		// login
		super.setLoginSession(null);
		// System.out.println("super.getLoginSession()
		// initttttttttt::"+super.getLoginSession());
		if (super.getLoginSession() == null) {

		}

		this.pintaCombo = false;
		super.setMuestraMenu(Boolean.FALSE);
		// sessionState.setMuestraMenu(false);
	}

	public String ingresa() throws IOException, Exception {
		String contrasenia = this.listaUsuarioRolService.encriptarContrasena(this.login, this.pass);
		if (this.listaUsuarioRolService.existeUsuarioRol(this.login, contrasenia)) {

			// System.out.println("listaUsuarioRolService.obtieneIdUsuario(login,contrasenia)::"+listaUsuarioRolService.obtieneIdUsuario(login,contrasenia));
			this.lisEtapas = this.etapaService
					.listaEtapaXIdUsuario(this.listaUsuarioRolService.obtieneIdUsuario(this.login, contrasenia));
			// System.out.println("tamlisEtapas::"+lisEtapas.size());
			// System.out.println("pintaCombo"+pintaCombo);
			// System.out.println("super.getLoginSession()::"+super.getLoginSession());
			if (this.lisEtapas.size() > 1 && super.getLoginSession() == null) { // System.out.println("Entra
																				// aqui");
				this.pintaCombo = true;
				super.setLoginSession(this.login);
				// return "login";
				// Se retorna nulll para que se actulaize la misma pagina
				return null;
			} else {
				super.setLoginSession(this.login);
				super.setPass(contrasenia);
				super.setIdUsuarioSession(this.listaUsuarioRolService.obtieneIdUsuario(this.login, contrasenia));
				if (this.idEtapa == null) {
					super.setIdEtapaSession(this.lisEtapas.get(0).getIdEtapa());
				} else {
					super.setIdEtapaSession(this.idEtapa);
				}

				return "modulo";
			}

		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("USUARIO INCORRECTO", null));

			// System.out.println("NO EXISTE EL USUARIO EN BD");
			return "";
		}
	}

	/*
	 * public SessionStateManagedBean getSessionState() { return sessionState; }
	 * 
	 * public void setSessionState(SessionStateManagedBean sessionState) {
	 * this.sessionState = sessionState; }
	 */
	public listaMenu getUsuarioRol() {
		return this.usuarioRol;
	}

	public void setUsuarioRol(listaMenu usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

	public List<listaMenu> getListaUsuarioRol() {
		return this.listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<listaMenu> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	/*
	 * public SessionManaged getSessionBean() { return sessionBean; }
	 * 
	 * public void setSessionBean(SessionManaged sessionBean) { this.sessionBean
	 * = sessionBean; }
	 */

	public ListaUsuarioRolService getListaUsuarioRolService() {
		return this.listaUsuarioRolService;
	}

	public void setListaUsuarioRolService(ListaUsuarioRolService listaUsuarioRolService) {
		this.listaUsuarioRolService = listaUsuarioRolService;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPass() {
		return this.pass;
	}

	@Override
	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isPintaCombo() {
		return this.pintaCombo;
	}

	public void setPintaCombo(boolean pintaCombo) {
		this.pintaCombo = pintaCombo;
	}

	public EtapaService getEtapaService() {
		return this.etapaService;
	}

	public void setEtapaService(EtapaService etapaService) {
		this.etapaService = etapaService;
	}

	public Long getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(Long idEtapa) {
		this.idEtapa = idEtapa;
	}

	public List<Etapa> getLisEtapas() {
		return this.lisEtapas;
	}

	public void setLisEtapas(List<Etapa> lisEtapas) {
		this.lisEtapas = lisEtapas;
	}

	public VerificaSesion getVerificaSesion() {
		return this.verificaSesion;
	}

	public void setVerificaSesion(VerificaSesion verificaSesion) {
		this.verificaSesion = verificaSesion;
	}

}
