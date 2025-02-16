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

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ListeCompteManagerTest {

    private BanqueFacade banqueFacadeMock;
    private ListeCompteManager listeCompteManager;
    private Compte compteMock;
    private Client clientMock;

    @Before
    public void setUp() {
        banqueFacadeMock = Mockito.mock(BanqueFacade.class);
        listeCompteManager = new ListeCompteManager(banqueFacadeMock);

        compteMock = Mockito.mock(Compte.class);
        clientMock = Mockito.mock(Client.class);

        listeCompteManager.setClient(clientMock);
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

    @Test
    public void testDeleteUserSuccess() throws TechnicalException, IllegalOperationException {
        when(clientMock.getIdentity()).thenReturn("Client123");

        doNothing().when(banqueFacadeMock).deleteUser(clientMock);

        String result = listeCompteManager.deleteUser();
        assertEquals("SUCCESS", result);

        verify(banqueFacadeMock, times(1)).deleteUser(clientMock);
    }

    @Test
    public void testDeleteUserWithTechnicalException() throws TechnicalException, IllegalOperationException {
        when(clientMock.getIdentity()).thenReturn("Client123");

        doThrow(TechnicalException.class).when(banqueFacadeMock).deleteUser(clientMock);

        String result = listeCompteManager.deleteUser();
        assertEquals("ERROR", result);

        verify(banqueFacadeMock, times(1)).deleteUser(clientMock);
    }

    @Test
    public void testDeleteUserWithIllegalOperationException() throws TechnicalException, IllegalOperationException {
        when(clientMock.getIdentity()).thenReturn("Client123");

        doThrow(IllegalOperationException.class).when(banqueFacadeMock).deleteUser(clientMock);

        String result = listeCompteManager.deleteUser();
        assertEquals("NONEMPTYACCOUNT", result);

        verify(banqueFacadeMock, times(1)).deleteUser(clientMock);
    }

    @Test
    public void testGetAllClients() {
        Map mockClientMap = mock(Map.class);
        when(banqueFacadeMock.getAllClients()).thenReturn(mockClientMap);

        Map<String, Client> result = listeCompteManager.getAllClients();

        assertEquals(mockClientMap, result);
        verify(banqueFacadeMock, times(1)).getAllClients();
    }
}
