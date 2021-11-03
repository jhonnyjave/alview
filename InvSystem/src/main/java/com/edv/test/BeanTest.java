package com.edv.test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name = "beanTest")
public class BeanTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4470227877736774272L;
	private String saludoMenu = "Saludo desde menu, prueba de plantilla";

	public BeanTest() {
		// TODO Auto-generated constructor stub
	}

	public String getSaludoMenu() {
		return saludoMenu;
	}	

	public void setSaludoMenu(String saludoMenu) {
		this.saludoMenu = saludoMenu;
	}

}
