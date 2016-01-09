package com.mirzaakhena.apps.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pathway extends ModelId {

//	@Column(length = 30)
//	private String nama;
	
	@ManyToOne
	private PathwayCatalog pathwayCatalog;

	@ManyToOne
	private Organisme organisme;

	@OneToMany(mappedBy = "pathway")
	private List<Reaksi> listReaksi;

}
