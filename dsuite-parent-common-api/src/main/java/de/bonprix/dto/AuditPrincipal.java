package de.bonprix.dto;

public class AuditPrincipal extends Entity {
	private static final long serialVersionUID = 1L;
	
	private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
