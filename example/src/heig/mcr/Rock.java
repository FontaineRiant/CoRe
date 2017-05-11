package heig.mcr;

public class Rock {
    private boolean found;
    private boolean polished;
    private boolean appraised;

    public boolean isFound() {
        return found;
    }

    public boolean isPolished() {
        return polished;
    }

    public boolean isAppraised() {
        return appraised;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public void setPolished(boolean polished) {
        this.polished = polished;
    }

    public void setAppraised(boolean appraised) {
        this.appraised = appraised;
    }
};
