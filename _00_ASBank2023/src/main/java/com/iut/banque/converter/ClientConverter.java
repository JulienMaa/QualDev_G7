package com.iut.banque.converter;

import java.util.Map;
import java.util.logging.Logger;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Client;

/**
 * Cette classe contient des méthodes permettant de convertir un client en
 * string et vice-versa. Elle est déclarée dans
 * «src/main/webapp/WEB-INF/classes/xwork-conversion.properties.

 * Grâce à cette classe il est possible de passer en paramètre d'une action
 * Struts l'identifiant d'un client (une string) et le contrôleur qui va
 * recevoir le paramètre n'a besoin que d'un setter prenant un objet de type
 * Client.
 */
public class ClientConverter extends StrutsTypeConverter {

	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * DAO utilisée pour récupérer les objets correspondants à l'id passé en
	 * paramètre de convertFromString.
	 */
	private final IDao dao;

	/**
	 * Constructeur avec paramètre pour le ClientConverter.

	 * Utilisé pour l'injection de dépendance.
	 * 
	 * @param dao
	 * 			la dao nécessaire pour LoginConverter
	 */
	public ClientConverter(IDao dao) {
		logger.info("=========================");
		logger.info("Création du convertisseur de client");
		this.dao = dao;
	}

	/**
	 * Permet la conversion automatique par Struts d'un tableau de chaine vers
	 * un Client
	 */
	@Override
	public Object convertFromString(Map context, String[] values, Class classe) {
        Client client = (Client) dao.getUserById(values[0]);
		if (client == null) {
			throw new TypeConversionException("Impossible de convertir la chaine suivante : " + values[0]);
		}
		return client;
	}

	/**
	 * Permet la conversion automatique par Struts d'un Client vers une chaine
	 */
	@Override
	public String convertToString(Map context, Object value) {
		Client client = (Client) value;
		return client == null ? null : client.getIdentity();
	}

}
