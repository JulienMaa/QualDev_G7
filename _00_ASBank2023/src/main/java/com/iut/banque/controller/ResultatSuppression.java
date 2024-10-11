package com.iut.banque.controller;

import com.opensymphony.xwork2.ActionSupport;

import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;

public class ResultatSuppression extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private transient Compte compte;
	private transient Client client;
	private String compteInfo;
	private String userInfo;
	private boolean error;
	private String errorMessage;
	private boolean isAccount;

	/**
	 * @return the isAccount
	 */
	@SuppressWarnings("unused")
	public boolean isAccount() {
		return isAccount;
	}

	/**
	 * @param isAccount
	 *            the isAccount to set
	 */
	@SuppressWarnings("unused")
	public void setAccount(boolean isAccount) {
		this.isAccount = isAccount;
	}

	/**
	 * @return the compte
	 */
	public Compte getCompte() {
		return compte;
	}

	/**
	 * @param compte
	 *            the compte to set
	 */
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the compteInfo
	 */
	@SuppressWarnings("unused")
	public String getCompteInfo() {
		return compteInfo;
	}

	/**
	 * @param compteInfo
	 *            the compteInfo to set
	 */
	@SuppressWarnings("unused")
	public void setCompteInfo(String compteInfo) {
		this.compteInfo = compteInfo;
	}

	/**
	 * @return the userInfo
	 */
	@SuppressWarnings("unused")
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	@SuppressWarnings("unused")
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the errorMessage
	 */
	@SuppressWarnings("unused")
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	@SuppressWarnings("unused")
	public void setErrorMessage(String errorMessage) {
		setError(!(errorMessage == null || errorMessage.isEmpty()));
		this.errorMessage = errorMessage;
	}

	/**
	 * @return si une erreur est parvenue
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param error erreur
	 */
	public void setError(boolean error) {
		this.error = error;
	}

}
