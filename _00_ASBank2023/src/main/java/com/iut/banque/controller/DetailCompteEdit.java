package com.iut.banque.controller;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.CompteAvecDecouvert;
import java.util.logging.Logger;

public class DetailCompteEdit extends DetailCompte {

	private static final long serialVersionUID = 1L;
	private String decouvertAutorise;
	private static final Logger logger = Logger.getLogger(DetailCompteEdit.class.getName());

	/**
	 * Constructeur sans argument de DetailCompteEdit
	 */
	public DetailCompteEdit() {
		super();
		logger.info("======================================");
		logger.info("Dans le constructeur DetailCompteEdit");
	}

	public DetailCompteEdit(BanqueFacade banqueFacade) {
		super(banqueFacade);
		logger.info("======================================");
		logger.info("Dans le constructeur DetailCompteEdit");
	}

	/**
	 * @return the decouvertAutorise
	 */
	public String getDecouvertAutorise() {
		return decouvertAutorise;
	}

	/**
	 * @param decouvertAutorise
	 *            the decouvertAutorise to set
	 */
	public void setDecouvertAutorise(String decouvertAutorise) {
		this.decouvertAutorise = decouvertAutorise;
	}

	/**
	 * Permet le changement de découvert d'un compte avec découvert.
	 * 
	 * @return le status de l'action
	 */
	@SuppressWarnings("unused")
	public String changementDecouvert() {
		compte = getCompte();
		if (!(compte instanceof CompteAvecDecouvert)) {
			return "ERROR";
		}

		try {
			double decouvert = Double.parseDouble(decouvertAutorise);
			banque.changeDecouvert((CompteAvecDecouvert) compte, decouvert);
			return "SUCCESS";
		} catch (NumberFormatException nfe) {
			return "ERROR";
		} catch (IllegalFormatException e) {
			return "NEGATIVEOVERDRAFT";
		} catch (IllegalOperationException e) {
			return "INCOMPATIBLEOVERDRAFT";
		}
	}
}
