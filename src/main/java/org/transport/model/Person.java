package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "[person]", schema = "tpt")
@Entity(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String family;
    @Column(columnDefinition = "NVARCHAR(10)", name = "birth_date")
    private String birthDate;
    @Column(columnDefinition = "NVARCHAR(11)", name = "national_code")
    private String nationalCode;
    @Column(columnDefinition = "NVARCHAR(50)", name = "mobile_number")
    private String mobileNumber;
    @Column(columnDefinition = "BIT", name = "is_foreigners")
    private Boolean isForeigners;
    @Column(columnDefinition = "BIT", name = "is_company")
    private Boolean isCompany;
    @Column(columnDefinition = "NVARCHAR(50)", name = "company_national_code")
    private String companyNationalCode;
    @Column(columnDefinition = "NVARCHAR(10)", name = "establish_date")
    private String establishDate;
    @Column(columnDefinition = "NVARCHAR(50)", name = "manager_name")
    private String managerName;
    @Column(columnDefinition = "NVARCHAR(10)", name = "manager_national_code")
    private String managerNationalCode;
    @Column(columnDefinition = "NVARCHAR(10)", name = "manager_birth_date")
    private String managerBirthDate;
    @Column(columnDefinition = "NVARCHAR(50)", name = "manager_mobile_number")
    private String managerMobileNumber;
    @Column(columnDefinition = "NVARCHAR(50)", name = "id_number")
    private String idNumber;
}
