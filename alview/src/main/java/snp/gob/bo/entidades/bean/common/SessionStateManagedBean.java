package snp.gob.bo.entidades.bean.common;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

@ManagedBean(name = "sessionState")
@SessionScoped
public class SessionStateManagedBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3362672102345912437L;
	// Variable que almacena el login del usuario
	private String login;
	private String pass = "";
	private String idSistema = "1";
	private Long idUsuario;
	private Long idEtapa;
	private Long idSigno;
	// Bandera que indica si el usuario esta autenticado
	private boolean authenticated;
	private final String pathTemplate = "/WEB-INF/facelets/templates/Template.xhtml";
	private final String pathTemplateLogin = "/WEB-INF/facelets/templates/TemplateLogin.xhtml";
	private String formatoFecha = "dd/MM/yyyy";
	private String formatoFechaHora = "dd/MM/yyyy HH:mm:ss";
	private String formatoFechaHoraSinSegundo = "dd/MM/yyyy HH:mm";
	private String formatoHora = "HH:mm:ss";
	private String paginaAnteriorDigitalizacion;
	private Boolean activaPantallaInicio = true;
	private String codigoPagina;
	private String navegaPagina = "";
	private Boolean muestraMenu = false;
	private Long idmodificacion;
	private Long idSolicitudRenovacion;
	private StreamedContent stream;
	private Date fechaIngresoSolicitud;

	// atributos session tipotramite y numeroformulario, modulo de ventanilla
	private String numeroFormularioSolicitud;
	private String tipoTramiteSolicitud;

	public SessionStateManagedBean() {
	}

	/**
	 * **********************
	 */

	// </editor-fold>

	/**
	 * @return the login
	 */
	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getIdSistema() {
		return this.idSistema;
	}

	public void setIdSistema(String idSistema) {
		this.idSistema = idSistema;
	}

	public Boolean getMuestraMenu() {
		return this.muestraMenu;
	}

	public void setMuestraMenu(Boolean muestraMenu) {
		this.muestraMenu = muestraMenu;
	}

	public Date getFechaIngresoSolicitud() {
		return this.fechaIngresoSolicitud;
	}

	public void setFechaIngresoSolicitud(Date fechaIngresoSolicitud) {
		this.fechaIngresoSolicitud = fechaIngresoSolicitud;
	}

	public String getNumeroFormulario() {
		return this.numeroFormularioSolicitud;
	}

	public void setNumeroFormulario(String numeroFormulario) {
		this.numeroFormularioSolicitud = numeroFormulario;
	}

	public String getTipoTramiteSolicitud() {
		return this.tipoTramiteSolicitud;
	}

	public void setTipoTramiteSolicitud(String tipoTramiteSolicitud) {
		this.tipoTramiteSolicitud = tipoTramiteSolicitud;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getPathTemplate() {
		return this.pathTemplate;
	}

	public String getPathTemplateLogin() {
		return this.pathTemplateLogin;
	}

	public boolean isAnyError() {
		return FacesContext.getCurrentInstance().getMessageList().size() > 0;
	}

	public String getFormatoFecha() {
		return this.formatoFecha;
	}

	public void setFormatoFecha(String formatoFecha) {
		this.formatoFecha = formatoFecha;
	}

	public String getFormatoFechaHora() {
		return this.formatoFechaHora;
	}

	public void setFormatoFechaHora(String formatoFechaHora) {
		this.formatoFechaHora = formatoFechaHora;
	}

	public String getFormatoHora() {
		return this.formatoHora;
	}

	public void setFormatoHora(String formatoHora) {
		this.formatoHora = formatoHora;
	}

	public String getFormatoFechaHoraSinSegundo() {
		return this.formatoFechaHoraSinSegundo;
	}

	public void setFormatoFechaHoraSinSegundo(String formatoFechaHoraSinSegundo) {
		this.formatoFechaHoraSinSegundo = formatoFechaHoraSinSegundo;
	}

	void setpagina(String pagina) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	String getpagina() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	public String getNavegaPagina() {
		return this.navegaPagina;
	}

	public void setNavegaPagina(String navegaPagina) {
		this.navegaPagina = navegaPagina;
	}

	public Boolean getActivaPantallaInicio() {
		return this.activaPantallaInicio;
	}

	public void setActivaPantallaInicio(Boolean activaPantallaInicio) {
		this.activaPantallaInicio = activaPantallaInicio;
	}

	public String getCodigoPagina() {
		return this.codigoPagina;
	}

	public void setCodigoPagina(String codigoPagina) {
		this.codigoPagina = codigoPagina;
	}

	public String getPaginaAnteriorDigitalizacion() {
		return this.paginaAnteriorDigitalizacion;
	}

	public void setPaginaAnteriorDigitalizacion(String paginaAnteriorDigitalizacion) {
		this.paginaAnteriorDigitalizacion = paginaAnteriorDigitalizacion;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdmodificacion() {
		return this.idmodificacion;
	}

	public void setIdmodificacion(Long idmodificacion) {
		this.idmodificacion = idmodificacion;
	}

	public Long getIdSigno() {
		return this.idSigno;
	}

	public void setIdSigno(Long idSigno) {
		this.idSigno = idSigno;
	}

	public Long getIdSolicitudRenovacion() {
		return this.idSolicitudRenovacion;
	}

	public void setIdSolicitudRenovacion(Long idSolicitudRenovacion) {
		this.idSolicitudRenovacion = idSolicitudRenovacion;
	}

	public StreamedContent getStream() {
		return this.stream;
	}

	public void setStream(StreamedContent stream) {
		this.stream = stream;
	}

	public Long getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(Long idEtapa) {
		this.idEtapa = idEtapa;
	}

}
