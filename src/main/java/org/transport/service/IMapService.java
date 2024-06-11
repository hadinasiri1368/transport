package org.transport.service;

import org.transport.dto.Response.NeshanElementDto;

public interface IMapService {
    String getAddress(double latitude, double longitude) throws Exception;
    String getExceptionCode(int code);

    NeshanElementDto getDistanceWithTraffic(double fromLatitude, double fromLongitude,double toLatitude, double toLongitude) throws Exception;
    NeshanElementDto getDistanceWithoutTraffic(double fromLatitude, double fromLongitude,double toLatitude, double toLongitude) throws Exception;
}
