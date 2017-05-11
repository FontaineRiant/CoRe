package heig.mcr.dwarf;

import heig.mcr.Rock;

public class DiggerDwarf extends Dwarf {
    @Override
    public String getName() {
        return "Digger";
    }

    @Override
    protected boolean work(Rock rock) {
        rock.setFound(true);
        System.out.println(getName() + " : J'ai trouvé une pierre qui brille !");
        return true;
    }
}
