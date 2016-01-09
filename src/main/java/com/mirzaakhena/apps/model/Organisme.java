package com.mirzaakhena.apps.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Organisme extends ModelId {

	@Column(length = 30)
	private String nama;

	@OneToMany(mappedBy = "organisme")
	private List<Pathway> listPathway;

	@Override
	public String toString() {
		return nama;
	}
	
}
