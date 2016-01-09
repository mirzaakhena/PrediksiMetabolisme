package com.mirzaakhena.apps.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reaksi extends ModelId {

	@Column(length = 30)
	private int nomor;

	@ManyToOne
	private Pathway pathway;

	@OneToMany(mappedBy = "reaksi")
	private List<ProteinSequence> listProteinSequence;

}
