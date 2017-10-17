package de.bonprix.base.demo.dto;

import de.bonprix.dto.AuditPrincipal;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(
    intoPackage = "*.builder")
public class SecurityInfo {

    private AuditPrincipal authenticatedPrincipal;
    private AuditPrincipal rootPrincipal;

    private String requestUuid;

    public String getRequestUuid() {
        return this.requestUuid;
    }

    public void setRequestUuid(final String requestUuid) {
        this.requestUuid = requestUuid;
    }

    public AuditPrincipal getAuthenticatedPrincipal() {
        return this.authenticatedPrincipal;
    }

    public void setAuthenticatedPrincipal(final AuditPrincipal authenticatedPrincipal) {
        this.authenticatedPrincipal = authenticatedPrincipal;
    }

    public AuditPrincipal getRootPrincipal() {
        return this.rootPrincipal;
    }

    public void setRootPrincipal(final AuditPrincipal rootPrincipal) {
        this.rootPrincipal = rootPrincipal;
    }

}
