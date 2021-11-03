package com.edv.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="holaMundo")
@SessionScoped
public class HolaMundo {
	private String saludo="test de hcoarite";
	
	private String saludoMenu = "Saludo desde menu";

	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}	
	
	
//	public String inicio() {
//		System.out.println("pagina i");
//		return "faces/page/index.xhtml";
//	}
//	
//	public String pagina1() {
//		System.out.println("pagina p1");
//		return "faces/page/content.xhtml";
//	}
//	
//	public String pagina2() {
//		System.out.println("pagina p2");
//		return "faces/page/content2.xhtml";
//	}	
//	
//	public String pagina3() {
//		System.out.println("pagina p3");
//		return "faces/page/content3.xhtml";
//	}
	
	public String inicio() {
		System.out.println("pagina 1");
		return "inicio";
	}
	
	public String pagina1() {
		System.out.println("pagina 1");
		return "content";
	}
	
	public String pagina2() {
		System.out.println("pagina 1");
		return "content2";
	}	
	
	public String pagina3() {
		System.out.println("pagina 1");
		return "content3";
	}
	
	

}
