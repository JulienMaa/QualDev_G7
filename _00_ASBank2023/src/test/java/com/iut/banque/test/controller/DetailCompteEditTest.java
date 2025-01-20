package com.iut.banque.test.controller;

import com.iut.banque.controller.DetailCompteEdit;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import com.iut.banque.modele.Gestionnaire;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DetailCompteEditTest {

    private BanqueFacade banqueFacadeMock;
    private CompteAvecDecouvert compteAvecDecouvertMock;
    private CompteSansDecouvert compteSansDecouvertMock;
    private Compte compteMock;
    private String numeroCompte;
    private String decouvertAutorise;
    private DetailCompteEdit detailCompteEdit;

    private Double castDecouvertAutorise(String decouvertAutorise) {
        return Double.parseDouble(decouvertAutorise.trim());
    }

    @Before
    public void setUp() throws IllegalFormatException, IllegalOperationException {
        banqueFacadeMock = Mockito.mock(BanqueFacade.class);
        compteAvecDecouvertMock = Mockito.mock(CompteAvecDecouvert.class);
        compteSansDecouvertMock = Mockito.mock(CompteSansDecouvert.class);
        compteMock = Mockito.mock(Compte.class);
        Gestionnaire gestionnaireMock = Mockito.mock(Gestionnaire.class);

        numeroCompte = "12345";
        decouvertAutorise = "500";

        detailCompteEdit = new DetailCompteEdit(banqueFacadeMock);
        detailCompteEdit.setNumeroCompte(numeroCompte);
        detailCompteEdit.setDecouvertAutorise(decouvertAutorise);

        doNothing().when(banqueFacadeMock).changeDecouvert(
                compteAvecDecouvertMock, castDecouvertAutorise(decouvertAutorise));
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(compteAvecDecouvertMock);
        when(banqueFacadeMock.getConnectedUser()).thenReturn(gestionnaireMock);
        when(compteAvecDecouvertMock.getNumeroCompte()).thenReturn(numeroCompte);
    }

    @Test
    public void testChangementDecouvertCasNominal() {
        assertEquals("SUCCESS", detailCompteEdit.changementDecouvert());
    }

    @Test
    public void testChangementDecouvertSansCompteAvecDecouvert() {
        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(compteSansDecouvertMock);
        when(compteSansDecouvertMock.getNumeroCompte()).thenReturn(numeroCompte);
        assertEquals("ERROR", detailCompteEdit.changementDecouvert());

        when(banqueFacadeMock.getCompte(numeroCompte)).thenReturn(compteMock);
        when(compteMock.getNumeroCompte()).thenReturn(numeroCompte);
        assertEquals("ERROR", detailCompteEdit.changementDecouvert());
    }

    @Test
    public void testChangementDecouvertAvecMontantInvalide() {
        detailCompteEdit.setDecouvertAutorise("cinq cent");
        assertEquals("ERROR", detailCompteEdit.changementDecouvert());
    }

    @Test
    public void testChangementDecouvertMontantNegatif() throws IllegalFormatException, IllegalOperationException {
        detailCompteEdit.setDecouvertAutorise("-500");
        doThrow(IllegalFormatException.class).when(banqueFacadeMock).changeDecouvert(
                compteAvecDecouvertMock, -castDecouvertAutorise(decouvertAutorise));
        assertEquals("NEGATIVEOVERDRAFT", detailCompteEdit.changementDecouvert());
    }

    @Test
    public void testChangementDecouvertIncompatibleOverdraft() throws IllegalOperationException, IllegalFormatException {
        doThrow(IllegalOperationException.class).when(banqueFacadeMock).changeDecouvert(
                compteAvecDecouvertMock, castDecouvertAutorise(decouvertAutorise));
        assertEquals("INCOMPATIBLEOVERDRAFT", detailCompteEdit.changementDecouvert());
    }
}
