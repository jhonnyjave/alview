/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snp.gob.bo.entidades.bean.archivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import snp.gob.bo.almodel.entidad.Dominio;
import snp.gob.bo.almodel.entidad.RegistroInventario;
import snp.gob.bo.almodel.entidad.Usuario;
import snp.gob.bo.almodel.servicio.ComunService;
import snp.gob.bo.almodel.servicio.DominioService;
import snp.gob.bo.almodel.servicio.RegistroAlmacenesService;
import snp.gob.bo.almodel.servicio.UsuarioPaginaService;
import snp.gob.bo.almodel.servicio.UsuarioService;
import snp.gob.bo.entidades.bean.common.AbstractManagedBean;

@ManagedBean(name = "archivoBean")
@ViewScoped
public class ArchivoBean extends AbstractManagedBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -583328959051819194L;

	@ManagedProperty(value = "#{usuarioService}")
	private UsuarioService usuarioService;

	@ManagedProperty(value = "#{comunService}")
	private ComunService comunService;

	@ManagedProperty(value = "#{dominioService}")
	private DominioService dominioService;

	@ManagedProperty(value = "#{registroAlmacenesService}")
	private RegistroAlmacenesService registroAlmacenesService;

	@ManagedProperty(value = "#{usuarioPaginaService}")
	private UsuarioPaginaService usuarioPaginaService;

	// variables de archivo
	public String subFondo;
	public List<Dominio> lstSubFondo = new ArrayList<>();
	public String seccion;
	public List<Dominio> lstSeccion = new ArrayList<>();
	public String serie;
	public List<Dominio> lstSerie = new ArrayList<>();
	public List<Dominio> lstCubierta = new ArrayList<>();
	// fin de variables de archivo

	// variables
	private Usuario usuario;
	private Boolean tieneAcceso;

	private Long idUsuarioSesion;
	private List<RegistroInventario> lstArchivo;
	private String tipoTramiteCombo = "";
	private Date fechaSistema;
	private Date fechaBuscar;
	private Date fechaRegistro;

	private Boolean paneluno = true;
	private Boolean paneldos;
	private Boolean paneltres;
	private Boolean panelcuatro;
	private Boolean panelcinco;
	private Boolean panelseis;
	private Boolean panelsiete;
	private Boolean panelocho;
	private RegistroInventario registroInventario;

	public ArchivoBean() {
		this.lstSubFondo = new ArrayList<Dominio>();
		this.lstSeccion = new ArrayList<Dominio>();
		this.lstSerie = new ArrayList<Dominio>();
		this.lstCubierta = new ArrayList<Dominio>();
		this.registroInventario = new RegistroInventario();
	}

	@PostConstruct
	public void initArchivoBean() {
		try {

			if (getIdUsuarioSession() != null) {
				this.usuario = this.usuarioService.buscaUsuarioPorIdUsuario(getIdUsuarioSession());
			}

			if (this.registroInventario.getIdRegistroInventario() == null) {
				this.registroInventario = new RegistroInventario();
			}

			this.fechaSistema = this.comunService.obtenerFechaHoraServidor(1L);
			this.fechaBuscar = this.fechaSistema;
			this.fechaRegistro = this.fechaSistema;
			this.lstSubFondo = this.dominioService.obtenerListadoDominio("subfondo");
			this.tieneAcceso = this.usuarioPaginaService.estadoBotonUsuario(this.usuario.getIdusuario(),
					"Eliminar y Editar");

			try {
			} catch (Exception ex) {
				Logger.getLogger(ArchivoBean.class.getName()).log(Level.SEVERE, null, ex);
			}

			this.idUsuarioSesion = super.getIdUsuarioSession();
			this.lstArchivo = this.registroAlmacenesService
					.listaArchivoPorFechas(this.comunService.obtenerFechaHoraServidor(1L), this.usuario,this.tieneAcceso);

		} catch (Exception e) {
		}
	}

	///// metodos de archivos
	public void listaDatosComboSeccion(String subFondoVista) throws Exception {
		this.lstSeccion = this.dominioService.ListaTipoTramiteReciboDominio("seccion", subFondoVista);
		if (subFondoVista.equals("DGE")) {
			this.paneluno = true;
			this.paneldos = this.paneltres = this.panelcuatro = this.panelcinco = this.panelseis = this.panelsiete = false;
		}

		if (this.lstSeccion.isEmpty()) {
			this.seccion = "";
			this.serie = "";
		}
		this.lstSerie = new ArrayList<>();
	}

	public void listaDatosComboSerie(String seccionVista) throws Exception {
		this.lstSerie = this.dominioService.ListaTipoTramiteReciboDominio("serie", seccionVista);
		if (this.lstSerie.isEmpty()) {
			this.serie = "";
		}
	}

	public void accion_guardaArchivo() throws Exception {

		if (validaCombos()) {
			this.registroInventario.setTipoSolicitud(this.tipoTramiteCombo);
			this.registroInventario.setSubFondo(this.subFondo);
			this.registroInventario.setSeccion(this.seccion);
			this.registroInventario.setSerie(this.serie);
			if (this.fechaRegistro != null) {
				this.registroInventario.setFechaRegistro(this.fechaRegistro);
			} else {
				this.registroInventario.setFechaRegistro(this.fechaSistema);
			}
			this.registroInventario.setIdUsuario(this.idUsuarioSesion);
			this.registroInventario.setEstado("AC");

			if (!this.subFondo.equals("") || !this.seccion.equals("") || !this.serie.equals("")) {
				this.registroAlmacenesService.guardarRegistroInventario(this.registroInventario, 1);
				accion_limpiarDatos();
				this.lstArchivo = this.registroAlmacenesService.listaArchivoPorFechas(this.fechaBuscar, this.usuario,this.tieneAcceso);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos fueron registrados", "correctamente"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar", "los datos administrativos"));
			}
		}
	}

	public void accion_modificaArchivo() throws Exception {
		this.registroInventario.setTipoSolicitud(this.tipoTramiteCombo);
		this.registroInventario.setSubFondo(this.subFondo);
		this.registroInventario.setSeccion(this.seccion);
		this.registroInventario.setSerie(this.serie);
		if (this.fechaRegistro != null) {
			this.registroInventario.setFechaModificacion(this.fechaRegistro);
		} else {
			this.registroInventario.setFechaModificacion(this.fechaSistema);
		}
		this.registroInventario.setIdUsuarioModificacion(this.idUsuarioSesion);
		if (!this.subFondo.equals("") || !this.seccion.equals("") || !this.serie.equals("")) {

			this.registroAlmacenesService.guardarRegistroInventario(this.registroInventario, 2);
			accion_limpiarDatos();
			this.lstArchivo = this.registroAlmacenesService.listaArchivoPorFechas(this.fechaBuscar, this.usuario,this.tieneAcceso);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Los datos fueron modificado", "correctamente"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar", "los datos administrativos"));
		}
	}

	public void accion_limpiarDatos() throws Exception {
		this.lstSerie.clear();
		this.lstSeccion.clear();
		this.lstSubFondo.clear();
		this.subFondo = "";
		this.seccion = "";
		this.serie = "";
		this.registroInventario = new RegistroInventario();
		this.lstSubFondo = this.dominioService.obtenerListadoDominio("subfondo");
	}

	public Boolean validaCombos() {
		if (!this.subFondo.equals("")) {
			if (!this.subFondo.equals("DGE")) {
				if (!this.seccion.equals("")) {
					return !this.serie.equals("");
				} else {
					return false;
				}
			} else {
				return true;
			}

		} else {
			return false;
		}
	}

	public void renderizaPaneles(String serieVista) throws Exception {
		if (serieVista.equals("AG") || serieVista.equals("PC") || serieVista.equals("RH") || serieVista.equals("GC")
				|| serieVista.equals("SABS") || serieVista.equals("DA")) {
			this.paneluno = true;
			this.paneldos = this.paneltres = this.panelcuatro = this.panelcinco = this.panelseis = this.panelsiete = this.panelocho = false;
		}
		if (serieVista.equals("SD") || serieVista.equals("OT")) {
			this.paneldos = true;
			this.paneluno = this.paneltres = this.panelcuatro = this.panelcinco = this.panelseis = this.panelsiete = this.panelocho = false;
		}
		if (serieVista.equals("TRAN") || serieVista.equals("CN") || serieVista.equals("CD") || serieVista.equals("LU")
				|| serieVista.equals("RF")) {
			this.paneltres = true;
			this.paneluno = this.paneldos = this.panelcuatro = this.panelcinco = this.panelseis = this.panelsiete = this.panelocho = false;
		}

		if (serieVista.equals("PU") || serieVista.equals("OP") || serieVista.equals("DE") || serieVista.equals("RE")
				|| serieVista.equals("DE") || serieVista.equals("AB") || serieVista.equals("AN")
				|| serieVista.equals("CA")) {
			this.panelcuatro = true;
			this.paneluno = this.paneldos = this.paneltres = this.panelcinco = this.panelseis = this.panelsiete = this.panelocho = false;
		}
		if (serieVista.equals("BA")) {
			this.panelcinco = true;
			this.paneluno = this.paneldos = this.paneltres = this.panelcuatro = this.panelseis = this.panelsiete = this.panelocho = false;
		}
		if (serieVista.equals("IN")) {
			this.panelseis = true;
			this.paneluno = this.paneldos = this.paneltres = this.panelcuatro = this.panelcinco = this.panelsiete = this.panelocho = false;
		}
		if (serieVista.equals("RENO")) {
			this.panelsiete = true;
			this.paneluno = this.paneldos = this.paneltres = this.panelcuatro = this.panelcinco = this.panelseis = this.panelocho = false;
		}
		if (serieVista.equals("CAN") || serieVista.equals("NU")) {
			this.panelocho = true;
			this.paneluno = this.paneldos = this.paneltres = this.panelcuatro = this.panelcinco = this.panelseis = this.panelsiete = false;
		}		
	}

	public void listaPorFechas() throws Exception {
		this.lstArchivo = this.registroAlmacenesService.listaArchivoPorFechas(this.fechaBuscar, this.usuario,this.tieneAcceso);
	}

	public void cargaValoresModificar(RegistroInventario registroInventarioVista) throws Exception {
		this.fechaRegistro = registroInventarioVista.getFechaRegistro();
		this.registroInventario = registroInventarioVista;
		this.subFondo = this.registroInventario.getSubFondo();
		this.seccion = this.registroInventario.getSeccion();
		this.serie = this.registroInventario.getSerie();
		listaDatosComboSeccion(this.subFondo);
		listaDatosComboSerie(this.seccion);

		if (this.subFondo.equals("DGE")) {
			this.paneluno = true;
			this.paneldos = this.paneltres = this.panelcuatro = this.panelcinco = this.panelseis = this.panelsiete = false;
		}
		renderizaPaneles(this.serie);
	}

	public void cargaValoresEliminar(RegistroInventario registroInventario) throws Exception {
		this.registroAlmacenesService.eliminaArchivo(registroInventario, this.usuario);
		this.lstArchivo = this.registroAlmacenesService.listaArchivoPorFechas(this.fechaBuscar, this.usuario,this.tieneAcceso);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminaron los datos", "correctamente"));
	}

	public String devuelveDescripcionSubFondo(RegistroInventario registroInventario) throws Exception {
		if (!registroInventario.getSubFondo().equals("")) {
			return this.dominioService.obtenerNombrePorCodigoDominio(registroInventario.getSubFondo(), "subfondo");
		}
		return "";
	}

	public String devuelveDescripcionSeccion(RegistroInventario registroInventario) throws Exception {
		if (!registroInventario.getSeccion().equals("")) {
			return (this.dominioService.obtenerNombrePorCodigoDominio(registroInventario.getSeccion(), "seccion"));
		}
		return "";
	}

	public String devuelveDescripcionSerie(RegistroInventario registroInventario) throws Exception {
		if (!registroInventario.getSerie().equals("")) {
			return (this.dominioService.obtenerNombrePorCodigoDominio(registroInventario.getSerie(), "serie"));
		}
		return "";
	}

	public String devuelveSm(RegistroInventario registroInventario) throws Exception {
		if (registroInventario.getNumeroSolicitud() != 0) {
			return registroInventario.getTipoSolicitud() + "-" + registroInventario.getNumeroSolicitud() + "-"
					+ registroInventario.getGestionSolicitud();
		}
		return "";
	}

	public String devuelveRegistro(RegistroInventario registroInventario) throws Exception {
		if (registroInventario.getNumeroRegistro() != 0) {
			return registroInventario.getNumeroRegistro() + "-" + registroInventario.getSerieRegistro();
		}
		return "";
	}

	public String devuelveResolucion(RegistroInventario registroInventario) throws Exception {
		if (registroInventario.getNumeroResolucion() != 0) {
			return registroInventario.getNumeroResolucion() + "/" + registroInventario.getGestionResolucion();
		}
		return "";
	}

	public String devuelvePublicacion(RegistroInventario registroInventario) throws Exception {
		if (registroInventario.getNumeroPublicacion() != 0) {
			return registroInventario.getNumeroPublicacion().toString();
		}
		return "";
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getTieneAcceso() {
		return this.tieneAcceso;
	}

	public void setTieneAcceso(Boolean tieneAcceso) {
		this.tieneAcceso = tieneAcceso;
	}

	public UsuarioService getUsuarioService() {
		return this.usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Long getIdUsuarioSesion() {
		return this.idUsuarioSesion;
	}

	public void setIdUsuarioSesion(Long idUsuarioSesion) {
		this.idUsuarioSesion = idUsuarioSesion;
	}

	public DominioService getDominioService() {
		return this.dominioService;
	}

	public void setDominioService(DominioService dominioService) {
		this.dominioService = dominioService;
	}

	public String getTipoTramiteCombo() {
		return this.tipoTramiteCombo;
	}

	public void setTipoTramiteCombo(String tipoTramiteCombo) {
		this.tipoTramiteCombo = tipoTramiteCombo;
	}

	public Date getFechaBuscar() {
		return this.fechaBuscar;
	}

	public void setFechaBuscar(Date fechaBuscar) {
		this.fechaBuscar = fechaBuscar;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	// gets and sets
	public String getSubFondo() {
		return this.subFondo;
	}

	public void setSubFondo(String subFondo) {
		this.subFondo = subFondo;
	}

	public List<Dominio> getLstSubFondo() throws Exception {
		return this.lstSubFondo;// =
								// dominioService.obtenerListadoDominio("subfondo");
	}

	public void setLstSubFondo(List<Dominio> lstSubFondo) {
		this.lstSubFondo = lstSubFondo;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public List<Dominio> getLstSeccion() throws Exception {
		return this.lstSeccion;// =
								// dominioService.obtenerListadoDominio("seccion");
	}

	public void setLstSeccion(List<Dominio> lstSeccion) {
		this.lstSeccion = lstSeccion;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public List<Dominio> getLstSerie() throws Exception {
		return this.lstSerie;// = dominioService.obtenerListadoDominio("serie");
	}

	public void setLstSerie(List<Dominio> lstSerie) {
		this.lstSerie = lstSerie;
	}

	public List<Dominio> getLstCubierta() throws Exception {
		return this.lstCubierta = this.dominioService.obtenerListadoDominio("tipo_cubierta");
	}

	public void setLstCubierta(List<Dominio> lstCubierta) {
		this.lstCubierta = lstCubierta;
	}

	public ComunService getComunService() {
		return this.comunService;
	}

	public void setComunService(ComunService comunService) {
		this.comunService = comunService;
	}

	public Boolean getPaneluno() {
		return this.paneluno;
	}

	public void setPaneluno(Boolean paneluno) {
		this.paneluno = paneluno;
	}

	public Boolean getPaneldos() {
		return this.paneldos;
	}

	public void setPaneldos(Boolean paneldos) {
		this.paneldos = paneldos;
	}

	public Boolean getPaneltres() {
		return this.paneltres;
	}

	public void setPaneltres(Boolean paneltres) {
		this.paneltres = paneltres;
	}

	public Boolean getPanelcuatro() {
		return this.panelcuatro;
	}

	public void setPanelcuatro(Boolean panelcuatro) {
		this.panelcuatro = panelcuatro;
	}

	public Boolean getPanelcinco() {
		return this.panelcinco;
	}

	public void setPanelcinco(Boolean panelcinco) {
		this.panelcinco = panelcinco;
	}

	public Boolean getPanelseis() {
		return this.panelseis;
	}

	public void setPanelseis(Boolean panelseis) {
		this.panelseis = panelseis;
	}

	public Boolean getPanelsiete() {
		return this.panelsiete;
	}

	public void setPanelsiete(Boolean panelsiete) {
		this.panelsiete = panelsiete;
	}

	public Boolean getPanelocho() {
		return panelocho;
	}

	public void setPanelocho(Boolean panelocho) {
		this.panelocho = panelocho;
	}

	public RegistroInventario getRegistroInventario() {
		return this.registroInventario;
	}

	public void setRegistroInventario(RegistroInventario registroInventario) {
		this.registroInventario = registroInventario;
	}

	public RegistroAlmacenesService getRegistroAlmacenesService() {
		return this.registroAlmacenesService;
	}

	public void setRegistroAlmacenesService(RegistroAlmacenesService registroAlmacenesService) {
		this.registroAlmacenesService = registroAlmacenesService;
	}

	public UsuarioPaginaService getUsuarioPaginaService() {
		return this.usuarioPaginaService;
	}

	public void setUsuarioPaginaService(UsuarioPaginaService usuarioPaginaService) {
		this.usuarioPaginaService = usuarioPaginaService;
	}

	public List<RegistroInventario> getLstArchivo() {
		return this.lstArchivo;
	}

	public void setLstArchivo(List<RegistroInventario> lstArchivo) {
		this.lstArchivo = lstArchivo;
	}
}
