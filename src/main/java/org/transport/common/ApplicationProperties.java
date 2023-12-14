package org.transport.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    private static String serviceUrlAuthentication;
    private static String serviceUrlBasicData;

    public static String getServiceUrlAuthentication() {
        return serviceUrlAuthentication;
    }
    public static String getServiceUrlBasicData() {
        return serviceUrlBasicData;
    }

    @Value("${serviceUrl.authentication}")
    public void setServiceUrlAuthentication(String serviceUrlAuthentication) {
        this.serviceUrlAuthentication = serviceUrlAuthentication;
    }

    @Value("${serviceUrl.basicData}")
    public void setServiceUrlBasicData(String serviceUrlBasicData) {
        this.serviceUrlBasicData = serviceUrlBasicData;
    }
}
