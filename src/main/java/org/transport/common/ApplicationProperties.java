package org.transport.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    private static String serviceUrlAuthentication;

    public static String getServiceUrlAuthentication() {
        return serviceUrlAuthentication;
    }

    @Value("${serviceUrl.authentication}")
    public void setServiceUrlAuthentication(String serviceUrlAuthentication) {
        this.serviceUrlAuthentication = serviceUrlAuthentication;
    }
}
