package heig.mcr.dwarf;

import heig.mcr.Rock;

/**
 * Project : example
 * Author(s) : Antoine Friant
 * Date : 26.04.17
 */
public class BankerDwarf extends Dwarf {
    @Override
    public String getName() {
        return "Banker";
    }

    @Override
    protected boolean work(Rock rock) {
        if (rock.isFound()) {
            rock.setAppraised(true);
            if (rock.isPolished()) {
                System.out.println(getName() + " : Cette gemme est précieuse.");
            } else {
                System.out.println(getName() + " : Ce caillou brut n'a aucune valeur.");
            }
            return true;
        } else {
            System.out.println(getName() + " : Cette pierre n'as pas encore été trouvée !");
            return false;
        }
    }
}
