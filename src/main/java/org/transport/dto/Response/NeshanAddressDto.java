package org.transport.dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NeshanAddressDto {
    private String status;
    private String formatted_address;
    private String route_name;
    private String route_type;
    private String neighbourhood;
    private String city;
    private String state;
    private String place;
    private Integer municipality_zone;
    private Boolean in_traffic_zone;
    private Boolean in_odd_even_zone;
    private String village;
    private String county;
    private String district;
}
