package de.bonprix.base.demo.dto;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;
@GeneratePojoBuilder(intoPackage= "*.builder")
public class Season extends Entity
{
    private String name;


    private String description;


    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return  this.description;
    }

}
