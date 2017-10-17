package de.bonprix.dto;

import java.io.Serializable;

@FunctionalInterface
public interface Identifiable extends Serializable {

	Long getId();

}
