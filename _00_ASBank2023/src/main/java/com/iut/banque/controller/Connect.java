package com.iut.banque.controller;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import com.iut.banque.constants.LoginConstants;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Utilisateur;
import java.util.logging.Logger;

public class Connect extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Connect.class.getName());
	private String userCde;
	private String userPwd;
	private static final String ERROR = "ERROR";
	private static final String SUCCESS = "SUCCESS";
	private static final String SUCCESS_MANAGER = "SUCCESSMANAGER";
	private final transient BanqueFacade banque;

	/**
	 * Constructeur de la classe Connect
	 *  Un objet de type Connect avec façade BanqueFacade provenant de sa
	 *         factory
	 */
	public Connect() {
		logger.info("In Constructor from Connect class ");
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		this.banque = (BanqueFacade) context.getBean("banqueFacade");

	}

	public Connect(BanqueFacade banqueFacade) {
		logger.info("In Constructor from Connect class ");
		this.banque = banqueFacade;
	}

	/**
	 * Méthode pour vérifier la connexion de l'utilisateur basé sur les
	 * paramêtres userCde et userPwd de cette classe
	 *
	 * @return String, le resultat du login; "SUCCESS" si réussi, "ERROR" si
	 *         échec
	 */
	@SuppressWarnings("unused")
	public String login() {
		logger.info("Essai de login - 20180512...");

		if (userCde == null || userPwd == null) {
			return ERROR;
		}
		userCde = userCde.trim();

		int loginResult;
		try {
			loginResult = banque.tryLogin(userCde, userPwd);
		} catch (Exception e) {
			//e.printStackTrace()
 			loginResult = LoginConstants.ERROR;
		}

		switch (loginResult) {
		case LoginConstants.USER_IS_CONNECTED:
			logger.info("user logged in");
			return SUCCESS;
		case LoginConstants.MANAGER_IS_CONNECTED:
			logger.info("manager logged in");
			return SUCCESS_MANAGER;
		case LoginConstants.LOGIN_FAILED:
			logger.warning("login failed");
			return ERROR;
		default:
			logger.severe("error");
			return ERROR;
		}
	}

	/**
	 * Getter du champ userCde
	 *
	 * @return String, le userCde de la classe
	 */
	@SuppressWarnings("unused")
	public String getUserCde() {
		return userCde;
	}

	/**
	 * Setter du champ userCde
	 *
	 * @param userCde
	 *            : String correspondant au userCode à établir
	 */
	@SuppressWarnings("unused")
	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}

	/**
	 * Getter du champ userPwd
	 *
	 * @return String, le userPwd de la classe
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * Setter du champ userPwd
	 *
	 * @param userPwd
	 *            : correspondant au pwdCde à établir
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * Getter du champ utilisateur (uilisé pour récupérer l'utilisateur
	 * actuellement connecté à l'application)
	 *
	 * @return Utilisateur, l'utilisateur de la classe
	 */
	@SuppressWarnings("unused")
	public Utilisateur getConnectedUser() {
		return banque.getConnectedUser();
	}

	/**
	 * Méthode qui va récupérer sous forme de map la liste des comptes du client
	 * actuellement connecté à l'application
	 *
	 * @return Map<String, Compte> correspondant à l'ID du compte et l'objet
	 *         Compte associé
	 */
	@SuppressWarnings("unused")
	public Map<String, Compte> getAccounts() {
		return ((Client) banque.getConnectedUser()).getAccounts();
	}

	public String logout() {
		logger.info("Logging out");
		banque.logout();
		return SUCCESS;
	}

}
