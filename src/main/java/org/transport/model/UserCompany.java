package org.transport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user_company", schema = "tpt")
@Entity(name = "userCompany")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCompany extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_company_id")
    private Person company;
    @Column(columnDefinition = "DECIMAL(18,0)", name = "f_user_id")
    Long userId;
}
