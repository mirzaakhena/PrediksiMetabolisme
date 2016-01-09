package com.mirzaakhena.apps.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.apps.model.PathwayCatalog;

public interface PathwayCatalogDao extends JpaRepository<PathwayCatalog, Long> {

	PathwayCatalog findByNama(String pathway);

}
