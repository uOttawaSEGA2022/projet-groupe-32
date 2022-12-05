package com.example.mealerapp;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;


public class testsUnitaires {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkRepasName() {
        //Test1
        Repas repas = new Repas("Toast à l'avocat et au saumon fumé","Tartinade de cajou à l'aneth, œuf poché, oignons rouge et graines de sésame","Repas délicieux et nutritionnel", "Européenne", 25,"7f2kYe1NE6TmCtXoeiYQ7DNb9733");
        assertEquals("Vérifie le nom du repas", "Toast à l'avocat et au saumon fumé", repas.getRepasName());
    }

    @Test
    public void checkCuisineType() {
        Repas repas = new Repas("Toast à l'avocat et au saumon fumé","Tartinade de cajou à l'aneth, œuf poché, oignons rouge et graines de sésame","Repas délicieux et nutritionnel", "Européenne", 25,"7f2kYe1NE6TmCtXoeiYQ7DNb9733");
        assertEquals("Vérifie l'id du repas'", "Européenne", repas.getCuisineType());
    }

    @Test
    public void checkCookerId() {
        Repas repas = new Repas("Toast à l'avocat et au saumon fumé","Tartinade de cajou à l'aneth, œuf poché, oignons rouge et graines de sésame","Repas délicieux et nutritionnel", "Européenne", 25,"7f2kYe1NE6TmCtXoeiYQ7DNb9733");
        assertEquals("Vérifie l'id du cuisinier", "7f2kYe1NE6TmCtXoeiYQ7DNb9733", repas.getIdCuisinier());
    }

    @Test
    public void checkDemandeId() {
        Repas repas = new Repas("Toast à l'avocat et au saumon fumé","Tartinade de cajou à l'aneth, œuf poché, oignons rouge et graines de sésame","Repas délicieux et nutritionnel", "Européenne", 25,"7f2kYe1NE6TmCtXoeiYQ7DNb9733");
        Demande demande = new Demande("id", repas);
        assertEquals("Vérifie id demande", "id", demande.getIdClient()+"");
    }
}