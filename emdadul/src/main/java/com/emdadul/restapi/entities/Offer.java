package com.emdadul.restapi.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity

@Data
public class Offer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotEmpty(message = "Name is mandatory")
    private String title;
	
	@NotEmpty(message = "Description is mandatory")
    private String description;
	
	@NotNull(message = "Price is mandatory")
    private Double price;
	
	@Temporal(TemporalType.DATE)
    @Column(name="EXPIREDATE")
    private Date expire_date;
    
    
    public Offer() {

	}

	public Offer(Long id,String title, String description, Double price, Date expire_date) {
		super();
		this.id=id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.expire_date = expire_date;
	}
}