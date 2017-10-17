package de.bonprix.model;

public class FtOt {
    private final Integer ft;
    private final Integer ot;
    private final String name;

    public FtOt(final Integer ft, final Integer ot) {
        this.ft = ft;
        this.ot = ot;
        this.name = this.ft.toString() + '.' + this.ot.toString();
    }

    /**
     * @return the ft
     */
    public Integer getFt() {
        return this.ft;
    }

    /**
     * @return the ot
     */
    public Integer getOt() {
        return this.ot;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}
