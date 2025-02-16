package com.iut.banque.test.controller;

import com.iut.banque.constants.LoginConstants;
import com.iut.banque.controller.Connect;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ConnectTest {
    private Connect connect;

    @Before
    public void setUp() {
        BanqueFacade banqueFacadeMock = Mockito.mock(BanqueFacade.class);

        Gestionnaire administrateur = new Gestionnaire();

        when(banqueFacadeMock.tryLogin(anyString(), anyString())).thenReturn(LoginConstants.MANAGER_IS_CONNECTED);
        when(banqueFacadeMock.getConnectedUser()).thenReturn(administrateur);

        connect = new Connect(banqueFacadeMock);
    }

    @Test
    public void testLoginAdministrateur() {
        connect.setUserCde("adminCde");
        connect.setUserPwd("adminPwd");

        String result = connect.login();
        assertEquals("SUCCESSMANAGER", result);

        Utilisateur connectedUser = connect.getConnectedUser();
        assertNotNull(connectedUser);
        assertTrue(connectedUser instanceof Gestionnaire);
    }
}
