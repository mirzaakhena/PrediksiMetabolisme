package com.mirzaakhena.apps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class DataAll {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 50)
	private String nama;

	@Column(length = 50)
	private String pathway;

	@Column
	private int reaksi;

	@Column(length = 50)
	private String filename;

	public DataAll(String nama, String pathway, int reaksi, String filename) {
		this.nama = nama;
		this.pathway = pathway;
		this.reaksi = reaksi;
		this.filename = filename;
	}
	
	public DataAll() {
	}

}
