package de.bonprix.dto;

/**
 * An object with id and name implementing {@link NamedObject}.
 */

public class NamedEntity extends Entity implements NamedObject {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public NamedEntity() {
        super();
    }

    public NamedEntity(final Long id, final String name) {
        super();
        setId(id);
        setName(name);
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
