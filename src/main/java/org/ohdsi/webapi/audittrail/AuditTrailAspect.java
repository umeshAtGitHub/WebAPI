package org.ohdsi.webapi.audittrail;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.ohdsi.webapi.cohortsample.dto.CohortSampleDTO;
import org.ohdsi.webapi.person.PersonProfile;
import org.ohdsi.webapi.shiro.Entities.UserEntity;
import org.ohdsi.webapi.shiro.PermissionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@ConditionalOnProperty(value = "audit.trail.enabled", havingValue = "true")
public class AuditTrailAspect {

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private PermissionManager permissionManager;

    @Pointcut("@annotation(javax.ws.rs.GET)")
    public void restGetPointcut() {
    }
    @Pointcut("@annotation(javax.ws.rs.POST)")
    public void restPostPointcut() {
    }
    @Pointcut("@annotation(javax.ws.rs.PUT)")
    public void restPutPointcut() {
    }
    @Pointcut("@annotation(javax.ws.rs.DELETE)")
    public void restDeletePointcut() {
    }
    @Pointcut("execution(public * org.ohdsi.webapi.service.IRAnalysisResource+.*(..))")
    public void irResource() {
    }

    @Around("restGetPointcut() || restPostPointcut() || restPutPointcut() || restDeletePointcut() || irResource()")
    public Object auditLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        final HttpServletRequest request = getHttpServletRequest();

        if (request == null) { // system call
            return joinPoint.proceed();
        }

        final AuditTrailEntry entry = new AuditTrailEntry();
        entry.setCurrentUser(getCurrentUser());
        entry.setActionLocation(request.getHeader("Action-Location"));
        entry.setRequestUri(request.getRequestURI());

        final Object returnedObject = joinPoint.proceed();
        entry.setReturnedObject(returnedObject);

        auditTrailService.log(entry);

        return returnedObject;
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (final Exception e) {
            return null;
        }
    }

    private UserEntity getCurrentUser() {
        try {
            return permissionManager.getCurrentUser();
        } catch (final Exception e) {
            return null;
        }
    }
}