package snp.gob.bo.entidades.bean.common;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import org.primefaces.model.StreamedContent;

@ManagedBean
@NoneScoped
public class AbstractManagedBean {

	// 3. Inyeccion de un Bean
	@ManagedProperty(value = "#{sessionState}")
	private SessionStateManagedBean sessionManagedBean;

	public SessionStateManagedBean getSessionManagedBean() {
		return this.sessionManagedBean;
	}

	public void setSessionManagedBean(SessionStateManagedBean sessionManagedBean) {
		this.sessionManagedBean = sessionManagedBean;
	}

	public String getLoginSession() {
		return getSessionManagedBean().getLogin();
	}

	public void setLoginSession(String login) {
		getSessionManagedBean().setLogin(login);
	}

	public Long getIdEtapaSession() {
		return getSessionManagedBean().getIdEtapa();
	}

	public void setIdEtapaSession(Long idEtapa) {
		getSessionManagedBean().setIdEtapa(idEtapa);
	}

	public StreamedContent getStreamSession() {
		return getSessionManagedBean().getStream();
	}

	public void setStreamSession(StreamedContent stream) {
		getSessionManagedBean().setStream(stream);
	}

	// metodo que realiza la eliminación de variables que fueron creadas.
	public void eliminarVariablesSession() {
		this.sessionManagedBean.setNumeroFormulario(null);
		this.sessionManagedBean.setTipoTramiteSolicitud(null);
		this.sessionManagedBean.setNavegaPagina(null);
		setIdSignoSession(null);
	}

	/**
	 * *************************
	 */
	// </editor-fold>
	public String getPaginaAnteriorDigitalizacion() {
		return this.sessionManagedBean.getPaginaAnteriorDigitalizacion();
	}

	public void setPaginaAnteriorDigitalizacion(String paginaAnteriorDigitalizacion) {
		this.sessionManagedBean.setPaginaAnteriorDigitalizacion(paginaAnteriorDigitalizacion);
	}

	public Boolean getActivaPantallaInicioSession() {
		return getSessionManagedBean().getActivaPantallaInicio();
	}

	public void setActivaPantallaInicioSession(Boolean activaPantallaInicio) {
		getSessionManagedBean().setActivaPantallaInicio(activaPantallaInicio);
	}

	public void setCodigoPaginaSession(String codigoPagina) {
		getSessionManagedBean().setCodigoPagina(codigoPagina);
	}

	public String getCodigoPaginaSession() {
		return getSessionManagedBean().getCodigoPagina();
	}

	public String getNavegaPagina() {
		return this.sessionManagedBean.getNavegaPagina();
	}

	public void setNavegaPagina(String navegaPagina) {
		this.sessionManagedBean.setNavegaPagina(navegaPagina);
	}

	public String getPass() {
		return this.sessionManagedBean.getPass();
	}

	public void setPass(String pass) {
		this.sessionManagedBean.setPass(pass);
	}

	public Boolean getMuestraMenu() {
		return this.sessionManagedBean.getMuestraMenu();
	}

	public void setMuestraMenu(Boolean muestraMenu) {
		this.sessionManagedBean.setMuestraMenu(muestraMenu);
	}

	public String getIdSistema() {
		return this.sessionManagedBean.getIdSistema();
	}

	public void setIdSistema(String idSistema) {
		this.sessionManagedBean.setIdSistema(idSistema);
	}

	public String getNumeroFormularioSolicitudSession() {
		return getSessionManagedBean().getNumeroFormulario();
	}

	public void setNumeroFormularioSolicitudSession(String numeroFormulario) {
		this.getSessionManagedBean().setNumeroFormulario(numeroFormulario);
	}

	public String getTipoTramiteSolicitudSession() {
		return getSessionManagedBean().getTipoTramiteSolicitud();
	}

	public void setTipoTramiteSolicitudSession(String tipoTramiteSolicitud) {
		this.getSessionManagedBean().setTipoTramiteSolicitud(tipoTramiteSolicitud);
	}

	public Long getIdUsuarioSession() {
		return getSessionManagedBean().getIdUsuario();
	}

	public void setIdUsuarioSession(Long idUsuario) {
		this.getSessionManagedBean().setIdUsuario(idUsuario);
	}

	public Long getIdmodificacion() {
		return this.sessionManagedBean.getIdmodificacion();
	}

	public void setIdmodificacion(Long idmodificacion) {
		this.sessionManagedBean.setIdmodificacion(idmodificacion);
	}

	public Long getidRenSolicitudRenovacionSession() {
		return this.sessionManagedBean.getIdSolicitudRenovacion();
	}

	public void setidRenSolicitudRenovacionSession(Long idRenSolicitudRenovacion) {
		this.sessionManagedBean.setIdSolicitudRenovacion(idRenSolicitudRenovacion);
	}

	public Long getIdSignoSession() {
		return getSessionManagedBean().getIdSigno();
	}

	public void setIdSignoSession(Long idSigno) {
		this.getSessionManagedBean().setIdSigno(idSigno);
	}

	// variable session para la fecha Ingreso session
	public Date getFechaIngresoSolicitudSession() {
		return getSessionManagedBean().getFechaIngresoSolicitud();
	}

	public void setFechaIngresoSolicitudSession(Date fechaIngresoSolicitud) {
		this.getSessionManagedBean().setFechaIngresoSolicitud(fechaIngresoSolicitud);
	}
}
