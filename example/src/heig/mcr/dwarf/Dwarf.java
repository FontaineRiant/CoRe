package heig.mcr.dwarf;

import heig.mcr.Rock;
import heig.mcr.RockHandler;

/**
 * Project : example
 * Author(s) : Antoine Friant
 * Date : 26.04.17
 */
public abstract class Dwarf implements RockHandler {
    protected RockHandler next;

    public abstract String getName();
    protected abstract boolean work(Rock rock);

    public void handleRock(Rock rock){
        if(work(rock)) {
            if (next == null) {
                System.out.println(getName() + " : C'est fini !");
            } else {
                next.handleRock(rock);
            }
        }
    }

    public void setNext(Dwarf next) {
        this.next = next;
    }
}
