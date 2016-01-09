package com.mirzaakhena.apps.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PathwayCatalog extends ModelId {

	@Column(length = 30, unique = true)
	private String nama;

}
