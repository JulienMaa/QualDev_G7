package com.iut.banque.test.controller;

import com.iut.banque.controller.DetailCompte;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Gestionnaire;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class DetailCompteTest {
    private BanqueFacade banqueFacadeMock;
    private Compte compteMock;
    private Gestionnaire gestionnaireMock;
    private Client clientMock;
    private String numeroCompte;
    private String montant;
    private DetailCompte detailCompte;

    private Double castMontant(String montant) {
        return Double.parseDouble(montant.trim());
    }

    @Before
    public void setUp() throws InsufficientFundsException, IllegalFormatException {
        banqueFacadeMock = Mockito.mock(BanqueFacade.class);
        compteMock = Mockito.mock(Compte.class);
        gestionnaireMock = Mockito.mock(Gestionnaire.class);
        clientMock = Mockito.mock(Client.class);

        numeroCompte = "12345";
        montant = "100";

        Map<String, Compte> comptesClient = new HashMap<>();
        comptesClient.put(numeroCompte, compteMock);

        detailCompte = new DetailCompte(banqueFacadeMock);
        detailCompte.setNumeroCompte(numeroCompte);
        detailCompte.setMontant(montant);

        doNothing().when(banqueFacadeMock).debiter(compteMock, castMontant(montant));
        doNothing().when(banqueFacadeMock).crediter(compteMock, castMontant(montant));
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(compteMock);
        when(banqueFacadeMock.getConnectedUser()).thenReturn(clientMock);
        when(compteMock.getNumeroCompte()).thenReturn(numeroCompte);
        when(clientMock.getAccounts()).thenReturn(comptesClient);
    }

    @Test
    public void testInitCompteCasNominal() {
        assertEquals("success", detailCompte.initCompte());
    }

    @Test
    public void testInitCompteAvecCompteInexistant() {
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(null);
        assertEquals("error", detailCompte.initCompte());
    }

    @Test
    public void testInitCompteAvecNumeroCompteNullOuVide() {
        detailCompte.setNumeroCompte(null);
        assertEquals("error", detailCompte.initCompte());

        detailCompte.setNumeroCompte("");
        assertEquals("error", detailCompte.initCompte());
    }

    @Test
    public void testGetCompteCasNominal() {
        assertEquals(compteMock, detailCompte.getCompte());
    }

    @Test
    public void testGetCompteAvecUtilisateurGestionnaire() {
        when(banqueFacadeMock.getConnectedUser()).thenReturn(gestionnaireMock);
        assertEquals(compteMock, detailCompte.getCompte());
    }

    @Test
    public void testGetCompteAvecUtilisateurClientSansCompte() {
        when(clientMock.getAccounts()).thenReturn(new HashMap<>());
        assertNull(detailCompte.getCompte());
    }

    @Test
    public void testGetCompteAvecUtilisateurNull() {
        when(banqueFacadeMock.getConnectedUser()).thenReturn(null);
        assertNull(detailCompte.getCompte());
    }

    @Test
    public void testGetCompteAvecErreurInitCompte() {
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(null);
        assertNull(detailCompte.getCompte());
    }

    @Test
    public void testDebitCasNominal() {
        assertEquals("success", detailCompte.debit());
    }

    @Test
    public void testDebitAvecCompteNull() {
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(null);
        assertEquals("error", detailCompte.debit());
    }

    @Test
    public void testDebitAvecMontantInvalide() {
        detailCompte.setMontant("cent");
        assertEquals("error", detailCompte.debit());
    }

    @Test
    public void testDebitAvecMontantInsuffisant() throws InsufficientFundsException, IllegalFormatException {
        doThrow(InsufficientFundsException.class).when(banqueFacadeMock).debiter(compteMock, castMontant(montant));
        assertEquals("NOTENOUGH_FUNDS", detailCompte.debit());
    }
    @Test
    public void testDebitAvecMontantNegatif() throws InsufficientFundsException, IllegalFormatException {
        detailCompte.setMontant("-100");
        doThrow(IllegalFormatException.class).when(banqueFacadeMock).debiter(compteMock, -castMontant(montant));
        assertEquals("NEGATIVEAMOUNT", detailCompte.debit());
    }

    @Test
    public void testCreditCasNominal() {
        assertEquals("success", detailCompte.credit());
    }

    @Test
    public void testCreditAvecCompteNull() {
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(null);
        assertEquals("error", detailCompte.credit());
    }

    @Test
    public void testCreditAvecMontantInvalide() {
        detailCompte.setMontant("cent");
        assertEquals("error", detailCompte.credit());
    }

    @Test
    public void testCreditAvecMontantNegatif() throws IllegalFormatException {
        detailCompte.setMontant("-100");
        doThrow(IllegalFormatException.class).when(banqueFacadeMock).crediter(compteMock, -castMontant(montant));
        assertEquals("NEGATIVEAMOUNT", detailCompte.credit());
    }

    @Test
    public void testGetErrorTechnicalError() {
        detailCompte.setError("TECHNICAL");
        assertEquals("Erreur interne. Vérifiez votre saisie puis réessayez. Contactez votre conseiller si le problème persiste.", detailCompte.getError());
    }

    @Test
    public void testGetErrorAvecErreurBusiness() {
        detailCompte.setError("BUSINESS");
        assertEquals("Fonds insuffisants.", detailCompte.getError());
    }

    @Test
    public void testGetErrorAvecErreurNegativeAmount() {
        detailCompte.setError("NEGATIVEAMOUNT");
        assertEquals("Veuillez entrer un montant positif.", detailCompte.getError());
    }

    @Test
    public void testGetErrorAvecErreurNegativeOverdraft() {
        detailCompte.setError("NEGATIVEOVERDRAFT");
        assertEquals("Veuillez entrer un découvert positif.", detailCompte.getError());
    }

    @Test
    public void testGetErrorAvecErreurIncompatibleOverdraft() {
        detailCompte.setError("INCOMPATIBLEOVERDRAFT");
        assertEquals("Le nouveau découvert est incompatible avec le solde actuel.", detailCompte.getError());
    }

    @Test
    public void testGetErrorAvecErreurEmpty() {
        detailCompte.setError("EMPTY");
        assertEquals("", detailCompte.getError());
    }

    @Test
    public void testGetErrorAvecErreurNull() {
        detailCompte.setError(null);
        assertEquals("", detailCompte.getError());
    }
}
