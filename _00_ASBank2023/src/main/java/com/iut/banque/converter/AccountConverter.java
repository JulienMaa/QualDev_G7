package com.iut.banque.converter;

import java.util.Map;
import java.util.logging.Logger;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Compte;

/**
 * Cette classe contient des méthodes permettant de convertir un compte en
 * string et vice-versa. Elle est déclarée dans
 * «src/main/webapp/WEB-INF/classes/xwork-conversion.properties.

 * Grâce à cette classe il est possible de passer en paramètre d'une action
 * Struts le numéro d'un compte (une string) et le contrôleur qui va
 * recevoir le paramètre n'a besoin que d'un setter prenant un objet de type
 * Compte.
 */
public class AccountConverter extends StrutsTypeConverter {

	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * DAO utilisée pour récupérer les objets correspondants à l'id passé en
	 * paramètre de convertFromString.
	 */
	private final IDao dao;

	/**
	 * Constructeur avec paramètre pour le AccountConverter.

	 * Utilisé pour l'injection de dépendance.
	 *
	 * @param dao
	 * 			la dao nécessaire pour AccountConverter
	 */
	public AccountConverter(IDao dao) {
		logger.info("=========================");
		logger.info("Création du convertisseur de compte");
		this.dao = dao;
	}

	/**
	 * Permet la conversion automatique par Struts d'un tableau de chaine vers
	 * un Compte
	 */
	@Override
	public Object convertFromString(Map context, String[] values, Class classe) {
		Compte compte = dao.getAccountById(values[0]);
		if (compte == null) {
			throw new TypeConversionException("Impossible de convertir la chaine suivante : " + values[0]);
		}
		return compte;
	}

	/**
	 * Permet la conversion automatique par Struts d'un compte vers une chaine.
	 */
	@Override
	public String convertToString(Map context, Object value) {
		Compte compte = (Compte) value;
		return compte == null ? null : compte.getNumeroCompte();
	}

}
