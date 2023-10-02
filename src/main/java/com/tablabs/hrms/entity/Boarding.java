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

	@OneToOne(mappedBy = "boardingDeatails", cascade = CascadeType.ALL)
	private Employees employees;

	public Boarding() {
		super();
	}

	public Boarding(Long boardingId, String tenthDCertificate, String twelthCertificate, String degreeCertificate,
			String aadharCard, String panCard, String experienceCertificate, List<String> otherDocuments,
			Employees employees) {
		super();
		this.boardingId = boardingId;
		this.tenthDCertificate = tenthDCertificate;
		this.twelthCertificate = twelthCertificate;
		this.degreeCertificate = degreeCertificate;
		this.aadharCard = aadharCard;
		this.panCard = panCard;
		this.experienceCertificate = experienceCertificate;
		this.otherDocuments = otherDocuments;
		this.employees = employees;
	}

	public Long getBoardingId() {
		return boardingId;
	}

	public void setBoardingId(Long boardingId) {
		this.boardingId = boardingId;
	}

	public String getTenthDCertificate() {
		return tenthDCertificate;
	}

	public void setTenthDCertificate(String tenthDCertificate) {
		this.tenthDCertificate = tenthDCertificate;
	}

	public String getTwelthCertificate() {
		return twelthCertificate;
	}

	public void setTwelthCertificate(String twelthCertificate) {
		this.twelthCertificate = twelthCertificate;
	}

	public String getDegreeCertificate() {
		return degreeCertificate;
	}

	public void setDegreeCertificate(String degreeCertificate) {
		this.degreeCertificate = degreeCertificate;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	public String getExperienceCertificate() {
		return experienceCertificate;
	}

	public void setExperienceCertificate(String experienceCertificate) {
		this.experienceCertificate = experienceCertificate;
	}

	public List<String> getOtherDocuments() {
		return otherDocuments;
	}

	

	public void setOtherDocuments(List<String> otherDocuments) {
		this.otherDocuments = otherDocuments;
	}

	public Employees getEmployees() {
		return employees;
	}

	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

}
