package com.mirzaakhena.apps.dao;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mirzaakhena.apps.DataAll;

public interface DataAllDao extends JpaRepository<DataAll, Long> {

	@Query("FROM DataAll WHERE nama=?1")
	Stream<DataAll> findByNama(String nama);

	@Query("FROM DataAll GROUP BY nama")
	List<DataAll> findByGroupNama();

	@Query("FROM DataAll WHERE nama != ?1 GROUP BY nama")
	List<DataAll> findByGroupNamaExclude(String namaTarget);

	@Query("FROM DataAll WHERE nama = ?1 GROUP BY pathway")
	List<DataAll> findPathwaysFromNama(String nama);

	@Query("FROM DataAll WHERE nama != ?1 AND pathway = ?2 ORDER BY reaksi DESC")
	List<DataAll> findMaxReaksiFromPatwayAndNama(String nama, String pathway, Pageable pageable);

	@Query("FROM DataAll GROUP BY pathway")
	List<DataAll> findByGroupPathway();

	@Query("FROM DataAll WHERE nama=?1 AND pathway=?2")
	List<DataAll> findByReaksi(String nama, String pathway);

	@Query("FROM DataAll WHERE nama=?1 AND pathway=?2 AND reaksi=?3")
	List<DataAll> findByProteinSeq(String nama, String pathway, int reaksi);

}
