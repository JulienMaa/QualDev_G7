package com.iut.banque.controller;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Gestionnaire;
import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;


public class DetailCompte extends ActionSupport {

	private static final long serialVersionUID = 1L;
	protected final transient BanqueFacade banque;
	private String montant;
	private String error;
	protected transient Compte compte;
	private static final String TECHNICAL_ERROR = "TECHNICAL";
	private static final String BUSINESS_ERROR = "BUSINESS";
	private static final String NEGATIVE_AMOUNT_ERROR = "NEGATIVEAMOUNT";
	private static final String NEGATIVE_OVERDRAFT_ERROR = "NEGATIVEOVERDRAFT";
	private static final String INCOMPATIBLE_OVERDRAFT_ERROR = "INCOMPATIBLEOVERDRAFT";
	private static final String NOTENOUGH_FUNDS = "NOTENOUGH_FUNDS";
	private static final Logger logger = Logger.getLogger(DetailCompte.class.getName());

	/**
	 * Constructeur du controlleur DetailCompte
	 * Récupère l'ApplicationContext
	 *  un nouvel objet DetailCompte avec une BanqueFacade provenant de
	 *         la factory
	 */
	public DetailCompte() {
		logger.info("In Constructor from DetailCompte class ");
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		this.banque = (BanqueFacade) context.getBean("banqueFacade");
	}

	/**
	 * Retourne sous forme de string le message d'erreur basé sur le champ
	 * "error" actuellement défini dans la classe
	 *
	 * @return String, le string avec le détail du message d'erreur
	 */
	public String getError() {
		switch (error) {
			case TECHNICAL_ERROR:
				return "Erreur interne. Vérifiez votre saisie puis réessayez. Contactez votre conseiller si le problème persiste.";
			case BUSINESS_ERROR:
				return "Fonds insuffisants.";
			case NEGATIVE_AMOUNT_ERROR:
				return "Veuillez entrer un montant positif.";
			case NEGATIVE_OVERDRAFT_ERROR:
				return "Veuillez entrer un découvert positif.";
			case INCOMPATIBLE_OVERDRAFT_ERROR:
				return "Le nouveau découvert est incompatible avec le solde actuel.";
			default:
				return "";
		}
	}

	/**
	 * Permet de définir le champ error de la classe avec le string passé en
	 * paramètre. Si jamais on passe un objet null, on adapte le string
	 * automatiquement en "EMPTY"
	 *
	 * @param error
	 *            : Un String correspondant à celui qu'on veut définir dans le
	 *            champ error
	 */
	public void setError(String error) {
		if (error == null) {
			this.error = "EMPTY";
		} else {
			this.error = error;
		}
	}

	/**
	 * Getter du champ montant
	 *
	 * @return String : valeur du champ montant
	 */
	public String getMontant() {
		return montant;
	}

	/**
	 * Setter du champ montant
	 *
	 * @param montant
	 *            un String correspondant au montant à définir
	 */
	public void setMontant(String montant) {
		this.montant = montant;
	}

	/**
	 * Getter du compte actuellement sélectionné. Récupère la liste des comptes
	 * de l'utilisateur connecté dans un premier temps. Récupère ensuite dans la
	 * HashMap la clé qui comporte le string provenant de idCompte. Renvoie donc
	 * null si le compte n'appartient pas à l'utilisateur
	 *
	 * @return Compte : l'objet compte après s'être assuré qu'il appartient à
	 *         l'utilisateur
	 */
	public Compte getCompte() {
		Object connectedUser = banque.getConnectedUser();

		if (connectedUser instanceof Gestionnaire) {
			return compte;
		}

		if (connectedUser instanceof Client && ((Client) connectedUser).getAccounts().containsKey(compte.getNumeroCompte())) {
			return compte;
		}

		return null;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	/**
	 * Méthode débit pour débter le compte considéré en cours
	 *
	 * @return String : Message correspondant à l'état du débit (si il a réussi
	 *         ou pas)
	 */
	@SuppressWarnings("unused")
	public String debit() {
		Compte compteDebit = getCompte();
		try {
			banque.debiter(compteDebit, Double.parseDouble(montant.trim()));
			return SUCCESS;
		} catch (NumberFormatException e) {
			//e.printStackTrace()
			return ERROR;
		} catch (InsufficientFundsException ife) {
			//ife.printStackTrace()
			return NOTENOUGH_FUNDS;
		} catch (IllegalFormatException e) {
			//e.printStackTrace()
			return NEGATIVE_AMOUNT_ERROR;
		}
	}

	/**
	 * Méthode crédit pour créditer le compte considéré en cours
	 *
	 * @return String : Message correspondant à l'état du crédit (si il a réussi
	 *         ou pas)
	 */
	@SuppressWarnings("unused")
	public String credit() {
		Compte compteCredit = getCompte();
		try {
			banque.crediter(compteCredit, Double.parseDouble(montant.trim()));
			return SUCCESS;
		} catch (NumberFormatException nfe) {
			//nfe.printStackTrace()
			return ERROR;
		} catch (IllegalFormatException e) {
			//e.printStackTrace()
			return NEGATIVE_AMOUNT_ERROR;
		}
	}
}
