package com.mirzaakhena.apps.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.apps.model.ProteinSequence;

public interface ProteinSequenceDao extends JpaRepository<ProteinSequence, Long> {

}
