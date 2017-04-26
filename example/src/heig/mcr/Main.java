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

        digger.setNext(banker);
        banker.setNext(jeweller);

        digger.handleRock(new Rock());
    }
}
