package code;

public class Settings {
    
    private boolean doAnimation = true;
    private int tileSize = 7;

    public boolean isDoAnimation() {
        return doAnimation;
    }
    
    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
    
}
