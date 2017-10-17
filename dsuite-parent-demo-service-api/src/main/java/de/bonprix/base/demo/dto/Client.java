package de.bonprix.base.demo.dto;

import de.bonprix.dto.Entity;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(intoPackage= "*.builder")
public class Client extends Entity
{

    private String clientName;

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return  this.clientName;
    }



}
