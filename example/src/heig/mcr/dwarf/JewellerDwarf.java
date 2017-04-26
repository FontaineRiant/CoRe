package heig.mcr.dwarf;

import heig.mcr.Rock;

/**
 * Project : example
 * Author(s) : Antoine Friant
 * Date : 26.04.17
 */
public class JewellerDwarf extends Dwarf {
    @Override
    public String getName() {
        return "Jeweller";
    }

    @Override
    protected boolean work(Rock rock) {
        if (rock.isFound()) {
            rock.setPolished(true);
            System.out.println(getName() + " : Je polis la pierre.");
            return true;
        } else {
            System.out.println(getName() + " : Cette pierre n'a pas encore été trouvée !");
            return false;
        }
    }
}
