package com.mirzaakhena.apps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProteinSequence extends ModelId {

	@Column(length = 60)
	private String proteinFile;

	@ManyToOne
	private Reaksi reaksi;

}
