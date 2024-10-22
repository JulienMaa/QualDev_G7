package com.iut.banque.test.modele;

import static org.junit.Assert.*;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Banque;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;

public class BanqueTest {

    private Banque banque;
    private Client client;
    private CompteAvecDecouvert compteAvecDecouvert;

    @Before
    public void setUp() throws IllegalFormatException, IllegalOperationException {
        // Initialiser les objets nécessaires pour les tests
        banque = new Banque();
        client = new Client();
        compteAvecDecouvert = new CompteAvecDecouvert("FR0123456789", 100, 100, client);

        // Ajouter le client et le compte à la banque
        Map<String, Client> clients = new HashMap<>();
        clients.put(client.getNumeroClient(), client);
        banque.setClients(clients);

        Map<String, Compte> comptes = new HashMap<>();
        comptes.put(compteAvecDecouvert.getNumeroCompte(), compteAvecDecouvert);
        banque.setAccounts(comptes);
    }

    @Test
    public void testDebiterCompte() throws IllegalFormatException, InsufficientFundsException {
        double montantDebit = 200.0;
        banque.debiter(compteAvecDecouvert, montantDebit);
        assertEquals(800.0, compteAvecDecouvert.getSolde(), 0.001);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testDebiterCompteFondsInsuffisants() throws IllegalFormatException, InsufficientFundsException {
        banque.debiter(compteAvecDecouvert, 2000.0);
    }

    @Test
    public void testCrediterCompte() throws IllegalFormatException {
        double montantCredit = 300.0;
        banque.crediter(compteAvecDecouvert, montantCredit);
        assertEquals(1300.0, compteAvecDecouvert.getSolde(), 0.001);
    }

    @Test
    public void testDeleteUser() {
        String userId = client.getNumeroClient();
        banque.deleteUser(userId);
        assertNull(banque.getClients().get(userId));
    }

    @Test
    public void testChangeDecouvert() throws IllegalFormatException, IllegalOperationException {
        double nouveauDecouvert = 700.0;
        banque.changeDecouvert(compteAvecDecouvert, nouveauDecouvert);
        assertEquals(nouveauDecouvert, compteAvecDecouvert.getDecouvertAutorise(), 0.001);
    }

    @Test(expected = IllegalFormatException.class)
    public void testChangeDecouvertInvalide() throws IllegalFormatException, IllegalOperationException {
        banque.changeDecouvert(compteAvecDecouvert, -500.0);  // Découvert négatif invalide
    }
}
