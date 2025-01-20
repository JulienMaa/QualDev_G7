package com.iut.banque.test.controller;

import com.iut.banque.controller.ListeCompteManager;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ListeCompteManagerTest {

    private BanqueFacade banqueFacadeMock;
    private ListeCompteManager listeCompteManager;
    private Compte compteMock;

    @Before
    public void setUp() {
        banqueFacadeMock = Mockito.mock(BanqueFacade.class);
        listeCompteManager = new ListeCompteManager(banqueFacadeMock);


        compteMock = Mockito.mock(Compte.class);
        Client clientMock = Mockito.mock(Client.class);
    }

    @Test
    public void testDeleteAccountSuccess() throws TechnicalException, IllegalOperationException {
        when(compteMock.getNumeroCompte()).thenReturn("12345");
        listeCompteManager.setCompte(compteMock);

        doNothing().when(banqueFacadeMock).deleteAccount(compteMock);

        String result = listeCompteManager.deleteAccount();
        assertEquals("SUCCESS", result);
        verify(banqueFacadeMock, times(1)).deleteAccount(compteMock);
    }

    @Test
    public void testDeleteAccountIllegalOperation() throws TechnicalException, IllegalOperationException {
        when(compteMock.getNumeroCompte()).thenReturn("12345");
        listeCompteManager.setCompte(compteMock);

        doThrow(new IllegalOperationException()).when(banqueFacadeMock).deleteAccount(compteMock);

        String result = listeCompteManager.deleteAccount();
        assertEquals("NONEMPTYACCOUNT", result);
        verify(banqueFacadeMock, times(1)).deleteAccount(compteMock);
    }

    @Test
    public void testDeleteAccountTechnicalError() throws TechnicalException, IllegalOperationException {
        when(compteMock.getNumeroCompte()).thenReturn("12345");
        listeCompteManager.setCompte(compteMock);

        doThrow(new TechnicalException()).when(banqueFacadeMock).deleteAccount(compteMock);

        String result = listeCompteManager.deleteAccount();
        assertEquals("ERROR", result);
        verify(banqueFacadeMock, times(1)).deleteAccount(compteMock);
    }
}
