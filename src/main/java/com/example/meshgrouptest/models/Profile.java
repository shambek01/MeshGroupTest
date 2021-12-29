package com.example.meshgrouptest.models;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import java.math.BigDecimal;
import javax.persistence.*;

@Data
@Entity
@Table(name = "profiles")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(scale = 2)
    @Type(type = "big_decimal")
    private BigDecimal cash;
    @Column(scale = 2)
    @Type(type = "big_decimal")
    private BigDecimal initialCash;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
