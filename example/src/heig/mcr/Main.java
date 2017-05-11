package heig.mcr;

import heig.mcr.dwarf.BankerDwarf;
import heig.mcr.dwarf.DiggerDwarf;
import heig.mcr.dwarf.Dwarf;
import heig.mcr.dwarf.JewellerDwarf;

public class Main {
    public static void main(String[] args) {

        Dwarf digger = new DiggerDwarf();
        Dwarf jeweller = new JewellerDwarf();
        Dwarf banker = new BankerDwarf();

        //Exemple 1
        //Creation de la chaine de responsabilité, au run time
        System.out.println("Digger -> Jeweller -> Banker");
        digger.setNext(jeweller);
        jeweller.setNext(banker);

        //Point d'entrée de la chaine
        digger.handleRock(new Rock());

        //Exemple 2
        System.out.println("Digger -> Banker");
        digger.setNext(banker);
        banker.setNext(null);

        digger.handleRock(new Rock());

        //Exemple 3
        System.out.println("Jeweller -> Banker");
        jeweller.handleRock(new Rock());
    }
}
