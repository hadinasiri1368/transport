package org.transport.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.transport.dto.ExceptionDto;
import org.transport.dto.RoleDto;
import org.transport.dto.UserDto;
import org.transport.service.AuthenticationServiceProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
public class CommonUtils {
    private static AuthenticationServiceProxy authenticationServiceProxy;
    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        CommonUtils.messageSource = messageSource;
    }

    @Autowired
    public void setAuthenticationServiceProxy(AuthenticationServiceProxy authenticationServiceProxy) {
        CommonUtils.authenticationServiceProxy = authenticationServiceProxy;
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
        } else if (o instanceof List) {
            if (((List) o).isEmpty())
                return true;
        }
        return o == null ? true : false;
    }

    public static <E> E isNull(E expr1, E expr2) {
        return (!isNull(expr1)) ? expr1 : expr2;
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

    public static Double doubleValue(Object number) {
        if (isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).doubleValue();
        else
            try {
                return Double.valueOf(number.toString());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static Float floatValue(Object number) {
        if (isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).floatValue();
        else
            try {
                return Float.valueOf(number.toString());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static String getMessage(Exception e) {
        if (e.getCause() instanceof RuntimeException) {
            return ((RuntimeException) e.getCause()).getMessage();
        }
        return "2001";
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

    public static ExceptionDto getException(Exception exception) {
        try {
            String[] messageArray = exception.getMessage().split("]:");
            ObjectMapper objectMapper = new ObjectMapper();
            if (messageArray.length > 1) {
                return objectMapper.readValue(messageArray[1].replaceAll("\\[", ""), ExceptionDto.class);
            } else {
                return objectMapper.readValue(messageArray[0].replaceAll("\\[", ""), ExceptionDto.class);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getUserId(String token, String uuid) {
        if (isNull(token))
            return null;
        return longValue(authenticationServiceProxy.getUserId(token, uuid));
    }

    public static <T> Page<T> listPaging(List<T> aClass) {
        PageRequest pageRequest = PageRequest.ofSize(aClass.size());
        return listPaging(aClass, pageRequest);
    }

    public static <T> Page<T> listPaging(List<T> aClass, PageRequest pageRequest) {
        long countResult = aClass.size();
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, aClass.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        List<T> subList = aClass.subList(fromIndex, toIndex);
        return new PageImpl<>(subList, pageRequest, countResult);
    }

    public static ExceptionDto getException(SQLException exception) {
        if (exception.getMessage().toLowerCase().contains("duplicate key")) {
            return ExceptionDto.builder()
                    .errorCode(409)
                    .errorMessage(getMessage("2039"))
                    .build();
        } else {
            return ExceptionDto.builder()
                    .errorCode(409)
                    .errorMessage("2040")
                    .build();
        }
    }

    public static ExceptionDto getException(DataIntegrityViolationException exception) {
        if (exception.getMessage().toLowerCase().contains("duplicate key")) {
            return ExceptionDto.builder()
                    .errorCode(409)
                    .errorMessage(getMessage("2039"))
                    .build();
        } else {
            return ExceptionDto.builder()
                    .errorCode(409)
                    .errorMessage("2040")
                    .build();
        }
    }
}

