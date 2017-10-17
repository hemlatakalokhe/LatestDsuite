package de.bonprix.base.demo.service;

import org.springframework.transaction.annotation.Transactional;

import de.bonprix.RequestId;
import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.SecurityInfo;
import de.bonprix.base.demo.service.SecurityDemoService;
import de.bonprix.dto.AuditPrincipal;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.Principal;

@RestService
@Transactional
public class SecurityDemoServiceImpl implements SecurityDemoService {

	@Override
	public SecurityInfo get() {
		final Principal authenticatedPrincipal = PrincipalSecurityContext.getAuthenticatedPrincipal();
		final Principal rootPrincipal = PrincipalSecurityContext.getRootPrincipal();

		final AuditPrincipal authenticatedPrincipalDto = new AuditPrincipal();
		authenticatedPrincipalDto.setId(authenticatedPrincipal.getId());
		authenticatedPrincipalDto.setName(authenticatedPrincipal.getName());

		final AuditPrincipal rootPrincipalDto = new AuditPrincipal();
		rootPrincipalDto.setId(rootPrincipal.getId());
		rootPrincipalDto.setName(rootPrincipal.getName());

		final SecurityInfo info = new SecurityInfo();

		info.setAuthenticatedPrincipal(authenticatedPrincipalDto);
		info.setRootPrincipal(rootPrincipalDto);

		info.setRequestUuid(RequestId.getRequestId());

		return info;
	}

}
