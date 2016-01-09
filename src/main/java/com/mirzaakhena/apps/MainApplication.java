package com.mirzaakhena.apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Transactional;

import com.mirzaakhena.apps.dao.DataAllDao;
import com.mirzaakhena.apps.dao.OrganismeDao;
import com.mirzaakhena.apps.dao.PathwayCatalogDao;
import com.mirzaakhena.apps.dao.PathwayDao;
import com.mirzaakhena.apps.dao.ProteinSequenceDao;
import com.mirzaakhena.apps.dao.ReaksiDao;
import com.mirzaakhena.apps.model.Organisme;
import com.mirzaakhena.apps.model.Pathway;
import com.mirzaakhena.apps.model.PathwayCatalog;
import com.mirzaakhena.apps.model.ProteinSequence;
import com.mirzaakhena.apps.model.Reaksi;

@Slf4j
@EnableJpaRepositories
@EntityScan
@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Autowired
	DataAllDao dataAlldao;

	@Autowired
	ProteinSequenceDao proteinSequenceDao;

	@Autowired
	OrganismeDao organismeDao;

	@Autowired
	PathwayDao pathwayDao;

	@Autowired
	ReaksiDao reaksiDao;

	@Autowired
	PathwayCatalogDao pathwayCatalogDao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void init() {
		// loadToDBFromCSV();
		// normalizeDatabase();

		try {

			for (int i = 0; i < 5; i++) {
				String query = "" + "SELECT y.nama, MAX(y.jumlah_reaksi) " + "FROM (" + "SELECT x.path_nama nama, x.org_nama, COUNT(x.jumlah_reaksi) jumlah_reaksi " + "FROM (" + "SELECT organisme.nama org_nama ,pathway_catalog.nama path_nama, reaksi.nomor jumlah_reaksi " + "FROM `pathway_catalog` " + "JOIN `pathway` ON pathway.pathway_catalog_id=pathway_catalog.id " + "JOIN `organisme` ON organisme.id = pathway.organisme_id " + "JOIN `reaksi` ON reaksi.pathway_id=pathway.id " + "WHERE pathway_catalog.id=? " + "GROUP BY organisme.nama, reaksi.nomor) x GROUP BY x.org_nama) y";
				jdbcTemplate.query(query, new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						System.out.println(rs.getString(1) + ", " + rs.getInt(2));
					}
				}, i + 1);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private List<Organisme> getOtherOrganisme(List<Organisme> listO, Organisme organismeTarget) {
		return listO.stream().filter(x -> !x.equals(organismeTarget)).collect(Collectors.toList());
	}

	private void normalizeDatabase() {

		for (DataAll o : dataAlldao.findByGroupNama()) {
			Organisme organisme = new Organisme();
			organisme.setNama(o.getNama());
			organismeDao.save(organisme);

			for (DataAll p : dataAlldao.findByGroupPathway()) {
				Pathway pathway = new Pathway();

				PathwayCatalog pathwayCatalog = pathwayCatalogDao.findByNama(p.getPathway());

				if (pathwayCatalog == null) {
					pathwayCatalog = new PathwayCatalog();
					pathwayCatalog.setNama(p.getPathway());
					pathwayCatalogDao.save(pathwayCatalog);
				}

				pathway.setPathwayCatalog(pathwayCatalog);
				pathway.setOrganisme(organisme);
				pathwayDao.save(pathway);

				for (DataAll r : dataAlldao.findByReaksi(o.getNama(), p.getPathway())) {
					Reaksi reaksi = new Reaksi();
					reaksi.setNomor(r.getReaksi());
					reaksi.setPathway(pathway);
					reaksiDao.save(reaksi);

					for (DataAll s : dataAlldao.findByProteinSeq(o.getNama(), p.getPathway(), r.getReaksi())) {

						ProteinSequence ps = new ProteinSequence();
						ps.setProteinFile(s.getFilename());
						ps.setReaksi(reaksi);
						proteinSequenceDao.save(ps);

					}

				}

			}

		}

		System.out.println("MainApplication.insertNormalizeDatabase() done");
	}

	public void test1() {
		List<DataAll> listO = dataAlldao.findByGroupNama();
		for (DataAll oTg : listO) {

			System.out.println("target=" + oTg.getNama());

			List<DataAll> listT = dataAlldao.findByGroupNamaExclude(oTg.getNama());
			for (DataAll oTr : listT) {
				System.out.println(oTr.getNama());

				List<DataAll> listPathway = dataAlldao.findPathwaysFromNama(oTr.getNama());
				for (DataAll o : listPathway) {
					System.out.println(o.getPathway());

					List<DataAll> listReaksi = dataAlldao.findMaxReaksiFromPatwayAndNama(oTr.getNama(), o.getPathway(), new PageRequest(0, 1));
					if (listReaksi != null && !listReaksi.isEmpty()) {
						System.out.println(listReaksi.get(0).getReaksi());
					}

					break;
				}

				break;
			}

			break;
			// System.out.println("------");
		}

	}

	@Transactional(readOnly = false)
	private void loadToDBFromCSV() {

		try {
			Scanner scanner = new Scanner(new File("DatabaseProtein.csv"));
			while (scanner.hasNextLine()) {
				String[] col = scanner.nextLine().split(";");
				dataAlldao.save(new DataAll(col[0], col[1], Integer.parseInt(col[2]), col[3]));
			}
			scanner.close();
			System.out.println("MainApplication.loadToDBFromCSV() done");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
