/**
 * 
 */
package de.bonprix.base.demo.dto;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author h.kalokhe
 *
 */

@GeneratePojoBuilder(intoPackage = "*.builder")
public class Planperiod extends Entity 
{
	private static final long serialVersionUID = 1L;
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
