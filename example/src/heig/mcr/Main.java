package heig.mcr;

import heig.mcr.dwarf.BankerDwarf;
import heig.mcr.dwarf.DiggerDwarf;
import heig.mcr.dwarf.Dwarf;
import heig.mcr.dwarf.JewellerDwarf;

/**
 * Project : example
 * Author(s) : Antoine Friant
 * Date : 26.04.17
 */
public class Main {
    public static void main(String[] args) {

        Dwarf digger = new DiggerDwarf();
        Dwarf jeweller = new JewellerDwarf();
        Dwarf banker = new BankerDwarf();

        System.out.println("Digger -> Jeweller -> Banker");
        digger.setNext(jeweller);
        jeweller.setNext(banker);

        digger.handleRock(new Rock());

        System.out.println("Digger -> Banker");
        digger.setNext(banker);
        banker.setNext(null);

        digger.handleRock(new Rock());

        System.out.println("Jeweller -> Banker");
        jeweller.handleRock(new Rock());
    }
}
