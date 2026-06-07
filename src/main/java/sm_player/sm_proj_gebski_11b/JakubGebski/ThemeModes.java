package sm_player.sm_proj_gebski_11b.JakubGebski;

public enum ThemeModes {
    BRIGHT("bright"),
    DARK("dark");
    private final String value;
    ThemeModes(String s) {
        this.value=s;
    }

    public String toString(){return this.value;}
}
