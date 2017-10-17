package de.bonprix.model;

/**
 * 
 * @author tmeissne
 *
 */
public class Example {

    Integer id;
    String name;

    public Example(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

}
