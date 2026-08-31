package com.sosha.config;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;
import com.sosha.core.security.TenantContext;
@Component
public class TenantInterceptor extends EmptyInterceptor {}
