package com.mirzaakhena.apps.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.apps.model.Pathway;

public interface PathwayDao extends JpaRepository<Pathway, Long> {


}
