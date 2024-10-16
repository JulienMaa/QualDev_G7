package com.iut.banque.controller;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.facade.BanqueFacade;
import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;

public class CreerUtilisateur extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private final transient BanqueFacade banque;
	private String userId;
	private String nom;
	private String prenom;
	private String adresse;
	private String userPwd;
	private boolean male;
	private boolean client;
	private String numClient;
	private String message;
	private String result;
	public static final String ERROR = "ERROR";
	private static final Logger logger = Logger.getLogger(CreerUtilisateur.class.getName());

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse
	 *            the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the userPwd
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * @param userPwd
	 *            the userPwd to set
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * @return the male
	 */
	public boolean isMale() {
		return male;
	}

	/**
	 * @param male
	 *            the male to set
	 */
	public void setMale(boolean male) {
		this.male = male;
	}

	/**
	 * @return the user
	 */
	public boolean isClient() {
		return client;
	}

	/**
	 * @param client
	 *            the user to set
	 */
	public void setClient(boolean client) {
		this.client = client;
	}

	/**
	 * @return the numClient
	 */
	@SuppressWarnings("unused")
	public String getNumClient() {
		return numClient;
	}

	/**
	 * @param numClient
	 *            the numClient to set
	 */
	@SuppressWarnings("unused")
	public void setNumClient(String numClient) {
		this.numClient = numClient;
	}

	/**
	 * Constructeur sans paramêtre de CreerUtilisateur
	 */
	public CreerUtilisateur() {
		logger.info("In Constructor from CreerUtilisateur class ");
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		this.banque = (BanqueFacade) context.getBean("banqueFacade");
	}

	/**
	 * Renvoie Le message à afficher si la création d'un utilisateur vient
	 * d'être essayée.
	 *
	 * @return le message de l'action précédente
	 */
	@SuppressWarnings("unused")
	public String getMessage() {
		return message;
	}

	/**
	 * Setter du message provenant de l'action précedente.
	 *
	 * @param message message
	 */
	@SuppressWarnings("unused")
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Le result indique si l'utilisateur vient d'arriver sur la page ou a tenté
	 * la création d'un utilisateur précedemment.
	 *
	 * @return le status de l'action précedente.
	 */
	@SuppressWarnings("unused")
	public String getResult() {
		return result;
	}

	/**
	 * Setter du result de l'action précedente
	 *
	 * @param result result
	 */
	@SuppressWarnings("unused")
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Création d'un utilisateur.
	 *
	 * @return String : le status de l'action
	 */
	@SuppressWarnings("unused")
	public String creationUtilisateur() {
		try {
			if (client) {
				banque.createClient(userId, userPwd, nom, prenom, adresse, male, numClient);
			} else {
				banque.createManager(userId, userPwd, nom, prenom, adresse, male);
			}
			this.message = "Le nouvel utilisateur avec le user id '" + userId + "' a bien été crée.";
			this.result = "SUCCESS";
			return "SUCCESS";
		} catch (IllegalOperationException e) {
			this.message = "L'identifiant à déjà été assigné à un autre utilisateur de la banque.";
			this.result = ERROR;
			return ERROR;
		} catch (TechnicalException e) {
			this.message = "Le numéro de client est déjà assigné à un autre client.";
			this.result = ERROR;
			return ERROR;
		} catch (IllegalArgumentException e) {
			this.message = "Le format de l'identifiant est incorrect.";
			this.result = ERROR;
			return ERROR;
		} catch (IllegalFormatException e) {
			this.message = "Format du numéro de client incorrect.";
			this.result = ERROR;
			return ERROR;
		}
	}
}
