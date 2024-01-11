package org.transport.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.transport.dto.UserDto;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Component
public class CommonUtils {
    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        CommonUtils.messageSource = messageSource;
    }

    public static Long getUserId(String token) {
        try {
            String url = ApplicationProperties.getServiceUrlAuthentication() + "/getUserId";
            url += "?token=" + token;
            return callService(url, HttpMethod.GET, null, null, Long.class, null);
        } catch (Exception e) {
            log.error("checkValidation error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getToken(HttpServletRequest request) {
        if (CommonUtils.isNull(request.getHeader("Authorization")))
            return null;
        return request.getHeader("Authorization").replaceAll("Bearer ", "");
    }

    public static boolean isNull(Object o) {
        if (o instanceof String) {
            if (o == null ||
                    ((String) o).isEmpty() ||
                    ((String) o).isBlank() ||
                    ((String) o).length() == 0 ||
                    ((String) o).toLowerCase().trim().equals("null")
            )
                return true;
            return false;
        }
        return o == null ? true : false;
    }

    public static <E> E isNull(E expr1, E expr2) {
        return (!isNull(expr1)) ? expr1 : expr2;
    }

    public static String checkValidation(String token, String targetUrl) {
        try {
            String url = ApplicationProperties.getServiceUrlAuthentication() + "/checkValidationToken";
            url += "?token=" + token + "&url=" + targetUrl;
            String returnValue = callService(url, HttpMethod.GET, null, null, String.class, null);
            if (returnValue.equals("token is ok"))
                return null;
            return returnValue;
        } catch (Exception e) {
            log.error("checkValidation error: " + e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static UserDto getUser(String token) {
        try {
            String url = ApplicationProperties.getServiceUrlAuthentication() + "/getUser";
            url += "?token=" + token;
            Map returnValue = callService(url, HttpMethod.GET, null, null, Map.class, null);
            return ObjectMapperUtils.map(returnValue, UserDto.class);
        } catch (Exception e) {
            log.error("getUser error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T callService(String url, HttpMethod httpMethod, HttpHeaders headers, Object body, Class<T> aClass, Map<String, Object> params) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(body, headers);
        if (CommonUtils.isNull(params))
            params = new HashMap<>();
        HttpEntity<T> response = restTemplate.exchange(url, httpMethod, httpEntity, aClass, params);
        return response.getBody();
    }

    public static <T> List<T> callService(String url, HttpMethod httpMethod, HttpHeaders headers, Object body, Map<String, Object> params, Class<T> aClass) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(body, headers);
        if (CommonUtils.isNull(params))
            params = new HashMap<>();
        HttpEntity<List> response = restTemplate.exchange(url, httpMethod, httpEntity, List.class, params);
        return ObjectMapperUtils.mapAll(response.getBody(), aClass);
    }

    public static Long longValue(Object number) {
        if (isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).longValue();
        else
            try {
                return Long.valueOf(number.toString().trim());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static String getMessage(Exception e) {
        if (e.getCause() instanceof RuntimeException) {
            return ((RuntimeException) e.getCause()).getMessage();
        }
        return "unknown.exception";
    }

    public static String getMessage(String key) {
        return messageSource.getMessage(key, null, null);
    }

    public static void setNull(Object entity) throws Exception {
        Class cls = Class.forName(entity.getClass().getName());
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1, field.getName().length());
            Method m = entity.getClass().getMethod("get" + name);
            Object o = m.invoke(entity);
            if (CommonUtils.isNull(o)) {
                Method method = entity.getClass().getMethod("set" + name, field.getType());
                method.invoke(entity, field.getType().cast(null));
            }
        }
    }
}

