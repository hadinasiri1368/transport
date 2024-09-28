package org.transport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.transport.common.CommonUtils;
import org.transport.dto.Response.NeshanAddressDto;
import org.transport.dto.Response.NeshanExceptionDto;
import org.transport.dto.Response.NeshanDistanceDto;
import org.transport.dto.Response.NeshanElementDto;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class NeshanMapServiceImpl implements IMapService {

    @Value("${map.neshan.api-key}")
    private String apiKey;

    @Value("${map.neshan.base-url}")
    private String baseUrl;

    @Override
    public String getAddress(double latitude, double longitude) throws Exception {
        String url = baseUrl + String.format("/v5/reverse?lat=%s&lng=%s", latitude, longitude);
        log.info(String.format("send request to : %s", url));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Api-Key", List.of(apiKey));
        try {
            NeshanAddressDto neshanAddressDto = CommonUtils.callService(url, HttpMethod.GET, httpHeaders, null, NeshanAddressDto.class, null);
            return neshanAddressDto.getFormatted_address();
        } catch (Exception e) {
            NeshanExceptionDto neshanExceptionDto = null;
            try {
                Pattern pattern = Pattern.compile(":\\s+\"(\\{.*\\})\"");
                Matcher matcher = pattern.matcher(e.getMessage());
                if (matcher.find()) {
                    String json = matcher.group(1);
                    ObjectMapper objectMapper = new ObjectMapper();
                    neshanExceptionDto = objectMapper.readValue(json, NeshanExceptionDto.class);
                    log.info(String.format("neshan getAddress has error : [%s]", neshanExceptionDto));
                }
            } catch (Exception exception) {
                throw new RuntimeException("2001");
            }
            throw new RuntimeException(getExceptionCode(neshanExceptionDto.getCode()));
        }
    }

    private NeshanElementDto getDistance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude, Boolean withTraffic) throws Exception {
        String url = baseUrl + String.format("/v1/distance-matrix%s?type=car&origins=%s,%s&destinations=%s,%s", (withTraffic ? "" : "/no-traffic"), fromLatitude, fromLongitude, toLatitude, toLongitude);
        log.info(String.format("send request to : %s", url));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Api-Key", List.of(apiKey));
        try {
            NeshanDistanceDto neshanAddressDto = CommonUtils.callService(url, HttpMethod.GET, httpHeaders, null, NeshanDistanceDto.class, null);
            return neshanAddressDto.getRows().get(0).getElements().get(0);
        } catch (Exception e) {
            NeshanExceptionDto neshanExceptionDto = null;
            try {
                Pattern pattern = Pattern.compile(":\\s+\"(\\{.*\\})\"");
                Matcher matcher = pattern.matcher(e.getMessage());
                if (matcher.find()) {
                    String json = matcher.group(1);
                    ObjectMapper objectMapper = new ObjectMapper();
                    neshanExceptionDto = objectMapper.readValue(json, NeshanExceptionDto.class);
                    log.info(String.format("neshan getDistanceWithTraffic has error : [%s]", neshanExceptionDto));
                }
            } catch (Exception exception) {
                throw new RuntimeException("2001");
            }
            throw new RuntimeException(getExceptionCode(neshanExceptionDto.getCode()));
        }
    }

    @Override
    public NeshanElementDto getDistanceWithTraffic(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) throws Exception {
        return getDistance(fromLatitude, fromLongitude, toLatitude, toLongitude, true);
    }

    @Override
    public NeshanElementDto getDistanceWithoutTraffic(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) throws Exception {
        return getDistance(fromLatitude, fromLongitude, toLatitude, toLongitude, false);
    }

    @Override
    public String getExceptionCode(int code) {
        return switch (code) {
            case 470 -> String.valueOf(new RuntimeException("2029"));
            case 480 -> String.valueOf(new RuntimeException("2030"));
            case 481 -> String.valueOf(new RuntimeException("2031"));
            case 482 -> String.valueOf(new RuntimeException("2032"));
            case 483 -> String.valueOf(new RuntimeException("2033"));
            case 484 -> String.valueOf(new RuntimeException("2034"));
            case 485 -> String.valueOf(new RuntimeException("2035"));
            case 500 -> String.valueOf(new RuntimeException("2036"));
            default -> "";
        };
    }
}
