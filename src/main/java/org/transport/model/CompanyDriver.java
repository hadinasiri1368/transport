package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "company_driver", schema = "tpt")
@Entity(name = "companyDriver")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyDriver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_company_id")
    private Person company;
    @ManyToOne
    @JoinColumn(name = "f_driver_id")
    private Driver driver;
    @Column(columnDefinition = "NVARCHAR(50)", name = "f_request_status_id")
    private Long requestStatusId;

}
