package com.iut.banque.facade;

import java.util.Map;

import com.iut.banque.constants.LoginConstants;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;

public class BanqueFacade {

	private final BanqueManager banqueManager;
	private final LoginManager loginManager;

	/**
	 * Constructeur de la facade sans paramètre
	 *
     */
	public BanqueFacade(LoginManager loginMng, BanqueManager banqueMng) {
		this.banqueManager = banqueMng;
		this.loginManager = loginMng;
	}

	/**
	 * Getter de l'utilisateur actuellement connecté à l'application
	 * 
	 * @return Utilisateur : celui qui est connecté
	 */
	public Utilisateur getConnectedUser() {
		return loginManager.getConnectedUser();
	}

	/**
	 * Tentative de connection.
	 * 
	 * @param userCde
	 *            : le String du user qui tente de s'identifier
	 * @param userPwd
	 *            : le String du mot de passe qui doit être vérifié
	 * @return int : correspondant à une constante issue de LoginConstants qui
	 *         prévient de l'état du login
	 */
	public int tryLogin(String userCde, String userPwd) {
		int result = loginManager.tryLogin(userCde, userPwd);
		if (result == LoginConstants.MANAGER_IS_CONNECTED) {
			banqueManager.loadAllClients();
		}
		return result;
	}

	/**
	 * Méthode pour créditer un compte d'un montant donné en paramètre
	 * 
	 * @param compte
	 *            : un objet de type Compte correspondant au compte qu'on veut
	 *            créditer
	 * @param montant
	 *            : un double correspondant au montant qu'on veut créditer
	 * @throws IllegalFormatException
	 *             si le param montant est négatif
	 */
	public void crediter(Compte compte, double montant) throws IllegalFormatException {
		this.banqueManager.crediter(compte, montant);
	}

	/**
	 * Méthode pour débiter un compte d'un montant donné en paramètre
	 * 
	 * @param compte
	 *            : un objet de type Compte correspondant au compte qu'on veut
	 *            débiter
	 * @param montant
	 *            : un double correspondant au montant qu'on veut débiter
	 * @throws InsufficientFundsException
	 *             dans le cas où le retrait n'est pas autorisé (dépassement de
	 *             découvert autorisé par exemple)
	 * @throws IllegalFormatException
	 *             : si le param montant est négatif
	 */
	public void debiter(Compte compte, double montant) throws InsufficientFundsException, IllegalFormatException {
		this.banqueManager.debiter(compte, montant);
	}

	/**
	 * Méthode pour récupérer une HashMap avec tous les clients
	 * 
	 * @return Map<String,Client> : la hashmap correspondant au résultat de la
	 *         demande
	 */
	public Map<String, Client> getAllClients() {
		return banqueManager.getAllClients();
	}

	/**
	 * Méthode pour déconnecter l'utilisateur.
	 */
	public void logout() {
		loginManager.logout();
	}

	/**
	 * Créer un compte sans découvert avec un solde de 0. L'utilisateur connecté
	 * doit être un gestionnaire.
	 * 
	 * @param numeroCompte
	 *            : le numéro du compte à créer
	 * @param client
	 *            : le client à qui appartient le compte
	 * @throws TechnicalException
	 *             : Si l'id fourni en param�tre est déjà assigné à un autre
	 *             compte de la base
	 * @throws IllegalFormatException
	 *             : si le numeroCompte n'est pas du bon format
	 */
	public void createAccount(String numeroCompte, Client client) throws TechnicalException, IllegalFormatException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.createAccount(numeroCompte, client);
		}
	}

	/**
	 * Créer un compte avec découvert avec un solde de 0. L'utilisateur connecté
	 * doit être un gestionnaire.
	 * 
	 * @param numeroCompte
	 *            : le numéro du compte à créer
	 * @param client
	 *            : le client à qui appartient le compte
	 * @param decouvertAutorise
	 *            : le decouvert autorise sur ce compte
	 * @throws TechnicalException
	 *             : Si l'id fourni en param�tre est déjà assigné à un autre
	 *             compte de la base
	 * @throws IllegalFormatException
	 *             : si le numeroCompte n'est pas du bon format
	 * @throws IllegalOperationException
	 * 			   : si decouvertAutorise est inférieur à 0
	 */
	public void createAccount(String numeroCompte, Client client, double decouvertAutorise)
			throws TechnicalException, IllegalFormatException, IllegalOperationException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.createAccount(numeroCompte, client, decouvertAutorise);
		}
	}

	/**
	 * Supprime un compte. L'utilisateur connect doit être un gestionnaire.
	 * 
	 * @param compte
	 *            : le compte à supprimer
	 * @throws IllegalOperationException
	 *             : si le compte n'a pas un solde de 0
	 * @throws TechnicalException
	 *             : si le compte est null ou si le compte n'est pas un compte
	 *             persistant.
	 */
	public void deleteAccount(Compte compte) throws IllegalOperationException, TechnicalException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.deleteAccount(compte);
		}
	}

	/**
	 * Cr�er un manager. L'utilisateur connecté doit être un gestionnaire.
	 *
	 * @param userId
	 *            String pour le userId à utiliser
	 * @param userPwd
	 *            String pour le password à utiliser
	 * @param nom
	 *            String pour le nom
	 * @param prenom
	 *            String pour le prenom
	 * @param adresse
	 *            String pour l'adresse
	 * @param male
	 *            boolean pour savoir si c'est un homme ou une femme
	 * @throws TechnicalException
	 *             : Si l'id fourni en paramètre est déjà assigné à un autre
	 *             utilisateur de la base
	 * @throws IllegalFormatException
	 * 			   : Si le format de l'un des paramètres est invalide
	 * @throws IllegalArgumentException
	 * 			   : Si l'un des paramètres est invalide
	 */
	public void createManager(String userId, String userPwd, String nom, String prenom, String adresse, boolean male)
			throws TechnicalException, IllegalArgumentException, IllegalFormatException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.createManager(userId, userPwd, nom, prenom, adresse, male);
		}
	}

	/**
	 * 
	 * L'utilisateur connecté doit être un gestionnaire.
	 * 
	 * @param userId
	 * 	 		: l'id du gestionnaire
	 * @param userPwd
	 * 	 		: le mot de passe du gestionnaire
	 * @param nom
	 * 	 		: le nom du gestionnaire
	 * @param prenom
	 * 			: le prenom du gestionnaire
	 * @param adresse
	 * 			: l'adresse du gestionnaire
	 * @param male
	 * 			: si le user est un homme ou non
	 * @param numeroClient
	 * 			: le numero de client du gestionnaire
	 * @throws IllegalOperationException
	 *             : si le numeroClient fourni en paramètre est déjà assigné à
	 *             un autre utilisateur de la base
	 * @throws TechnicalException
	 *             : Si l'id fourni en paramètre est déjà assigné à un autre
	 *             utilisateur de la base
	 * @throws IllegalFormatException
	 * 				: Si le format de l'un des paramètres est invalide
	 * @throws IllegalArgumentException
	 * 				: Si l'un des paramètres est invalide
	 */
	public void createClient(String userId, String userPwd, String nom, String prenom, String adresse, boolean male,
			String numeroClient)
			throws IllegalOperationException, TechnicalException, IllegalArgumentException, IllegalFormatException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.createClient(userId, userPwd, nom, prenom, adresse, male, numeroClient);
		}
	}

	/**
	 *
	 * L'utilisateur connecté doit être un gestionnaire.
	 *
	 * @throws IllegalOperationException
	 *             si le user est null ou si l'utilisateur n'est pas un
	 *             utilisateur persistant.
	 * @throws TechnicalException
	 *             si le manager à supprimer est le dernier dans la base
	 */
	public void deleteUser(Utilisateur u) throws IllegalOperationException, TechnicalException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.deleteUser(u);
		}
	}

	/**
	 * L'utilisateur connecté doit être un gestionnaire

	 * Charge la banqueManager avec une map de tous les clients
	 */
	public void loadClients() {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.loadAllClients();
		}
	}

	/**
	 * Méthode pour récupérer un objet compte basé sur son String identifiant
	 * 
	 * @param idCompte
	 *            : String correspondant à l'ID du compte qu'on veut récupérer
	 * @return Compte : objet correspondant à celui demandé
	 */
	public Compte getCompte(String idCompte) {
		return banqueManager.getAccountById(idCompte);
	}

	/**
	 * L'utilisateur connecté doit être un gestionnaire

	 * Méthode pour changer le découvert autorisé d'un compte
	 * 
	 * @param compte
	 *            : CompteAvecDecouvert correspondant au compte qu'on veut
	 *            modifier
	 * @param nouveauDecouvert
	 *            : double correspondant au nouveau montant de découvert qu'on
	 *            veut assigner
	 * @throws IllegalFormatException
	 * 		 	  : Si le format de l'un des paramètres est invalide
	 * @throws IllegalOperationException
	 * 			  : Si le nouveauDecouvert est le même que le decouvert actuel du compte
	 */
	public void changeDecouvert(CompteAvecDecouvert compte, double nouveauDecouvert) throws IllegalFormatException, IllegalOperationException {
		if (loginManager.getConnectedUser() instanceof Gestionnaire) {
			banqueManager.changeDecouvert(compte, nouveauDecouvert);
		}
	}
}
