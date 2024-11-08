package com.iut.banque.test.modele;

import static org.junit.Assert.*;

import com.iut.banque.exceptions.IllegalFormatException;
import org.junit.Test;

import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import org.junit.Before;

public class ClientTest {

	private Client c;

	/**
	 * Configuration commune avant chaque test.
	 * Ici, un client par défaut est créé pour être utilisé dans les tests.
	 */
	@Before
	public void setUp() throws IllegalFormatException {
		c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
	}

	/**
	 * Tests successifs de la méthode de vérification du format de numéro de client
	 */
	@Test
	public void testMethodeCheckFormatUserIdClientCorrect() {
		String strClient = "a.utilisateur928";
		assertTrue("Le format correct a été refusé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientCommencantParUnChiffre() {
		String strClient = "32a.abc1";
		assertFalse("Le format commençant par un chiffre a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientCommencantParPlusieursLettres() {
		String strClient = "aaa.abc1";
		assertFalse("Le format commençant par plusieurs lettres a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientSansPointSeparateur() {
		String strClient = "abc1";
		assertFalse("Le format sans point séparateur a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientChaineVide() {
		String strClient = "";
		assertFalse("Le format vide a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientSansLettresApresLePointSeparateur() {
		String strClient = "a.138";
		assertFalse("Le format sans lettres après le point a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecUneSeuleLettreApresLePointSeparateur() {
		String strClient = "a.a1";
		assertTrue("Le format valide a été refusé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecCaractereSpecial() {
		String strClient = "a.bcdé1";
		assertFalse("Le format avec caractère spécial a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecTrailingZeros() {
		String strClient = "a.abc01";
		assertFalse("Le format avec des zéros en fin a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecPlusieursPointsSeparateurs() {
		String strClient = "a.ab.c1";
		assertFalse("Le format avec plusieurs points a été validé: " + strClient, Client.checkFormatUserIdClient(strClient));
	}

	/**
	 * Tests successifs de la méthode de vérification du format du numéro de client
	 */
	@Test
	public void testMethodeCheckFormatNumeroClientCorrect() {
		String strClient = "1234567890";
		assertTrue("Le numéro de client correct a été refusé: " + strClient, Client.checkFormatNumeroClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecLettre() {
		String strClient = "12a456789";
		assertFalse("Le numéro de client avec une lettre a été validé: " + strClient, Client.checkFormatNumeroClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecCaractereSpecial() {
		String strClient = "12#456789";
		assertFalse("Le numéro de client avec un caractère spécial a été validé: " + strClient, Client.checkFormatNumeroClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecMoinsDeNeufChiffres() {
		String strClient = "12345678";
		assertFalse("Le numéro de client avec moins de neuf chiffres a été validé: " + strClient, Client.checkFormatNumeroClient(strClient));
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecPlusDeDixChiffres() {
		String strClient = "12345678901";
		assertFalse("Le numéro de client avec plus de dix chiffres a été validé: " + strClient, Client.checkFormatNumeroClient(strClient));
	}

	/**
	 * Tests de la méthode possedeComptesADecouvert
	 */
	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecQueDesComptesSansDecouvert() {
		try {
			c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			assertFalse("La méthode aurait dû renvoyer faux", c.possedeComptesADecouvert());
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientSansComptes() {
		try {
			assertFalse("La méthode aurait dû renvoyer faux", c.possedeComptesADecouvert());
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecUnCompteADecouvertParmiPlusieursTypesDeComptes() {
		try {
			c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567893", 1000, 100, c));
			assertTrue("La méthode aurait dû renvoyer vrai", c.possedeComptesADecouvert());
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecPlusieursComptesADecouvertParmiPlusieursTypesDeComptes() {
		try {
			c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567893", 1000, 100, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567893", -4242, 5000, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 1000.01, c));
			assertTrue("La méthode aurait dû renvoyer vrai", c.possedeComptesADecouvert());
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecUnUniqueCompteADecouvert() {
		try {
			c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
			assertTrue("La méthode aurait dû renvoyer vrai", c.possedeComptesADecouvert());
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	// Tests pour la méthode getComptesAvecSoldeNonNul()

	@Test
	public void testMethodeGetCompteAvecSoldeNonNulAvecDeuxComptesAvecSoldeNul() {
		try {
			c.addAccount(new CompteAvecDecouvert("FR1234567890", 0, 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			assertEquals("La méthode a renvoyé un ou plusieurs comptes avec un solde nul", 0, c.getComptesAvecSoldeNonNul().size());
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	@Test
	public void testMethodeGetCompteAvecSoldeNonNulAvecUnCompteSansDecouvertAvecSoldeNonNul() {
		try {
			c.addAccount(new CompteAvecDecouvert("FR1234567890", 0, 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 1, c));
			assertNotNull("La méthode n'a pas renvoyé le compte avec solde non nul", c.getComptesAvecSoldeNonNul().get("FR1234567891"));
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}

	@Test
	public void testMethodeGetCompteAvecSoldeNonNulAvecUnCompteAvecDecouvertAvecSoldeNonNul() {
		try {
			c.addAccount(new CompteAvecDecouvert("FR1234567890", 1, 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			assertNotNull("La méthode n'a pas renvoyé le compte avec solde non nul", c.getComptesAvecSoldeNonNul().get("FR1234567890"));
		} catch (Exception e) {
			fail("Exception récupérée: " + e.getMessage());
		}
	}
}
