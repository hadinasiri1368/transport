package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "driver", schema = "tpt")
@Entity(name = "driver")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_person_id")
    private Person person;
    @Column(columnDefinition = "NVARCHAR(15)", name = "tracking_code")
    private String trackingCode;
    @Column(columnDefinition = "NVARCHAR(50)", name = "f_driver_license_type_id")
    private Long driverLicenseTypeId;
    @Column(columnDefinition = "NVARCHAR(10)", name = "driver_license_issue_date")
    private String driverLicenseIssueDate;
    @Column(columnDefinition = "NVARCHAR(50)", name = "driver_license_valid_duration")
    private Integer driverLicenseValidDuration;
}
