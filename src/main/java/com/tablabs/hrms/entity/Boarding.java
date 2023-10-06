package com.tablabs.hrms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tablabs.hrms.util.StringListConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boarding")
public class Boarding {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardingId;
	private String tenthDCertificate;
	private String twelthCertificate;
	private String degreeCertificate;
	private String aadharCard;
	private String panCard;
	private String experienceCertificate;
	@Convert(converter = StringListConverter.class)
	private List<String> otherDocuments;

//	@OneToOne(mappedBy = "boardingDeatails", cascade = CascadeType.ALL)
//	private Employees employees;

}
