package com.iut.banque.interfaces;

import java.util.Map;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;

@SuppressWarnings("unused")
public interface IDao {

	/**
	 * Méthode pour créer un compte avec découvert dans la base de données
	 *
     * @param solde
     *            : le solde du compte à créer
     * @param numeroCompte
     *            : le numéro du compte à créer
     * @param decouvertAutorise
     *            : le decouvert autorisé du compte
     * @param client
     *            : le client à qui appartient le compte
	 * @return CompteAvecDecouvert : l'objet compte qui a été implémenté dans la
	 *         base. Envoie une exception en cas d'erreur
	 * @throws TechnicalException
	 *             : si le numéro de compte existe déjà IllegalFormatException : si
	 *             l'appel du constructeur du CompteSansDecouvert échoue
	 * @throws IllegalOperationException
	 * 				: Si le solde est négatif
	 */
	CompteAvecDecouvert createCompteAvecDecouvert(double solde,
			String numeroCompte, double decouvertAutorise, Client client)
			throws TechnicalException, IllegalFormatException, IllegalOperationException;

	/**
	 * Méthode pour créer un compte sans découvert dans la base de données
	 *
     * @param solde
     *            : le solde du compte à créer
     * @param numeroCompte
     *            : le numéro du compte à créer
     * @param client
     *            : le client à qui appartient le compte
	 * @return CompteSansD�ecouvert : l'objet compte qui a été implémenté dans
	 *         la base. Envoie une exception en cas d'erreur
	 * @throws TechnicalException
	 *             : si le numéro de compte existe déjà IllegalFormatException : si
	 *             l'appel du constructeur du CompteSansDecouvert échoue
	 */
	CompteSansDecouvert createCompteSansDecouvert(double solde,
			String numeroCompte, Client client) throws TechnicalException,
			IllegalFormatException;

	/**
	 * Méthode pour mettre à jour un compte dans la base de données basé sur
	 * l'objet Compte passé en paramètre
	 * 
	 * @param c
	 *            : un objet de type Compte correspondant à celui qu'on veut
	 *            mettre à jour (qui peut être soit CompteAvecDecouvert ou
	 *            CompteSansDecouvert)
	 */
	void updateAccount(Compte c);

	/**
	 * Méthode pour supprimer un compte dans la base basé sur l'objet Compte
	 * passé en paramètre
	 * 
	 * @param c
	 *            : un objet de type Compte correspondant à celui qu'on veut
	 *            supprimer (qui peut être soit CompteAvecDecouvert ou
	 *            CompteSansDecouvert)
	 * @throws TechnicalException
	 *             , si le compte est null ou si le compte n'est pas un compte
	 *             persistant.
	 */
	void deleteAccount(Compte c) throws TechnicalException;

	/**
	 * Méthode pour récupérer sous forme de hashmap les comptes du client basé
	 * sur son ID passé en paramètre. La clé du map correspond au numéro de
	 * compte, et les données sont les objets Compte contenant les données
	 * 
	 * @param id
	 *            : String avec l'id de l'utilisateur a qui on veut récupérer la
	 *            liste des comptes
	 * @return Map<String, Compte> la liste des comptes du client si l'id passé
	 *         en paramètre était correct, null sinon
	 */
	Map<String, Compte> getAccountsByClientId(String id);

	/**
	 * Méthode pour récupérer un compte basé sur son identifiant
	 * 
	 * @param id
	 *            : String avec l'id du compte duquel on veut récupérer l'objet
	 *            Compte correspondant
	 * @return Compte : le compte correspondant à l'id du paramètre. Sera null
	 *         si l'id n'existe pas
	 */
	Compte getAccountById(String id);

	/**
     * Méthode pour créer un utilisateur (Client ou Gestionnaire)
     *
     * @param userId  String pour le userId à utiliser
     *
     * @param userPwd String pour le password à utiliser
     *
     * @param nom     String pour le nom
     *
     * @param prenom  String pour le prenom
     *
     * @param adresse String pour l'adresse
     *
     * @param male    boolean pour savoir si c'est un homme ou une femme
     *
     * @param numClient
     *                String pour le numero de client
     * @throws TechnicalException       : Si l'id fourni en paramètre est déjà assigné à un autre
     *                                  utilisateur de la base
     * @throws IllegalFormatException   : Si le numClient est négatif
     * @throws IllegalArgumentException : Si l'un des arguments est incorrect
     */
	void createUser(String nom, String prenom, String adresse,
                    boolean male, String userId, String userPwd,
                    String numClient) throws TechnicalException, IllegalArgumentException, IllegalFormatException;

	/**
	 * Méthode pour supprimer un utilisateur (Client ou Gestionnaire)
	 * 
	 * @param u
	 *            : un objet de type Utilisateur (Client ou Gestionnaire)
	 *            correpondant à celui qu'on veut supprimer
	 * @throws TechnicalException
	 *             , si le user est null ou si l'utilisateur n'est pas un
	 *             utilisateur persistant.
	 */
	void deleteUser(Utilisateur u) throws TechnicalException;

	/**
	 * Méthode pour mettre à jour un utilisateur (Client ou Gestionnaire)
	 * 
	 * @param u
	 *            : un objet de type Utilisateur (Client ou Gestionnaire)
	 *            correspondant à celui qu'on veut mettre à jour
	 */
	void updateUser(Utilisateur u);

	/**
	 * Méthode pour vérifier la connection de l'utilisateur. Confronte le mot de
	 * passe en paramètre à celui dans la base de données pour l'utilisateur
	 * passé en paramètre
	 * 
	 * @param userId
	 *            : un String correspondant au userId à qui on veut vérifier le
	 *            mot de passe
	 * @param userPwd
	 *            : un String correspondant au mot de passe qu'on veut
	 *            confronter avec le contenu de la base de donn�es
	 * @return Boolean : le résultat de la requête. Vrai si les mots de passes
	 *         correspondent, faux sinon
	 */
    boolean isUserAllowed(String userId, String userPwd);

	/**
	 * Méthode pour récupérer un utilisateur basé sur son identifiant
	 * 
	 * @param id
	 *            : un String correspondant à l'id de l'utilisateur auquel on
	 *            veut récupérer son objet Utilisateur correspondant
	 * @return Utilisateur : l'utilisateur correspondant à l'id du paramètre.
	 *         Sera null si l'id n'existe pas
	 */
	Utilisateur getUserById(String id);

	/**
	 * Méthode pour récupérer HashMap de tous les comptes de la banque
	 * 
	 * @return Map<String, Compte> : la HashMap correspondant à tous les comptes
	 *         de la banque. Le string est le numéro de compte, et le Compte,
	 *         son objet Compte associé
	 */
	Map<String, Client> getAllClients();

	/**
	 * Méthode pour récupérer HashMap de tous les gestionnaires de la banque
	 * 
	 * @return Map<String, Gestionnaire> : la HashMap correspondant à tous les
	 *         gestionnaires de la banque. Le string est le numéro de compte, et
	 *         le Gestionnaire, son objet Gestionnaire associé
	 */
	Map<String, Gestionnaire> getAllGestionnaires();

	/**
	 * Termine la session commencée lors de isUserAllowed
	 */
	void disconnect();
}
