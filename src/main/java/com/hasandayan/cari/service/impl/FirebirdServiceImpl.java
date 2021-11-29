package com.hasandayan.cari.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.firebirdsql.jdbc.field.TypeConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hasandayan.cari.entity.Company;
import com.hasandayan.cari.entity.CompanyMovement;
import com.hasandayan.cari.enums.CompanyGroup;
import com.hasandayan.cari.enums.MovementMode;
import com.hasandayan.cari.enums.MovementType;
import com.hasandayan.cari.service.CompanyMovementService;
import com.hasandayan.cari.service.CompanyService;
import com.hasandayan.cari.service.FirebirdService;

@Service("firebirdService")
public class FirebirdServiceImpl implements FirebirdService {

	private Connection connection; 

	private final String DB_DRIVER = "org.firebirdsql.jdbc.FBDriver";

	private final String DB_NAME = "jdbc:firebirdsql:localhost/3050:";

	private static final String DB_USERNAME = "SYSDBA";

	private static final String DB_PASSWORD = "masterkey";

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyMovementService companyMovementService;

	@Transactional
	@Override
	public void companyTransfer(String path) {

		String databaseFile = DB_NAME + path;

		try {

			Class.forName(DB_DRIVER);

			connection = DriverManager.getConnection(databaseFile, DB_USERNAME, DB_PASSWORD);

			connection.setAutoCommit(false);

			ResultSet rs = databaseQuery("SELECT ID, CARI_KODU, UNVANI, YETKILI, ADRES_1 FROM CARI_KART");

			List<Company> companies = new ArrayList<>();

			while (rs.next()) {
				Long id = rs.getLong("ID");
				String cariKodu = rs.getString("CARI_KODU");
				String unvani = rs.getString("UNVANI");
				String yetkili = rs.getString("YETKILI");
				String adres1 = rs.getString("ADRES_1");

				Company company = companyService.findByOldId(id);

				if (unvani != null && (cariKodu == null || cariKodu.length() < 2)) {
					company.setCompanyName(unvani.trim());

					company.setCompanyOfficial(yetkili);

					company.setCompanyGroup(CompanyGroup.CUSTOMER);

					company.setOldId(id);

					if (adres1 != null)
						company.setOtherInfo(adres1.trim());

					companies.add(company);
				}

			}

			connection.close();

			companies.forEach(company -> {
				companyService.save(company);
			});

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Override
	public void movementTransfer(String path) {

		String databaseFile = DB_NAME + path;
		List<CompanyMovement> companyMovements = new ArrayList<>();

		try {

			Class.forName(DB_DRIVER);

			connection = DriverManager.getConnection(databaseFile, DB_USERNAME, DB_PASSWORD);

			connection.setAutoCommit(false);

			ResultSet rs = databaseQuery("SELECT ID, CARI_KART_ID, TARIH, ACIKLAMA, EVRAK_TIPI, EVRAK_NO, BORC_ALACAK, TUTAR, VADE_TARIHI, BIRIM, MIKTAR, BIRIM_FIYAT, VADE FROM CARI_HAREKET");

			while (rs.next()) {

				Long id = rs.getLong("ID");
				Long cariKartId = rs.getLong("CARI_KART_ID");
				Date tarih = rs.getDate("TARIH");
				LocalDate ldTarih = tarih.toLocalDate();
				String aciklama = rs.getString("ACIKLAMA");
				String evrakTipi = rs.getString("EVRAK_TIPI");
				String evrakNo = rs.getString("EVRAK_NO");
				String borcAlacak = rs.getString("BORC_ALACAK");
				BigDecimal tutar = rs.getBigDecimal("TUTAR").setScale(2, RoundingMode.HALF_UP);
				Long vade = rs.getLong("VADE");
				Date vadeTarihi = rs.getDate("VADE_TARIHI");
				LocalDate ldVadeTarihi = vadeTarihi != null ? vadeTarihi.toLocalDate(): null;
				String birim = rs.getString("BIRIM");
				BigDecimal miktar = rs.getBigDecimal("MIKTAR").setScale(2, RoundingMode.HALF_UP);
				BigDecimal birimFiyat = rs.getBigDecimal("BIRIM_FIYAT").setScale(2, RoundingMode.HALF_UP);

				CompanyMovement companyMovement = companyMovementService.findByOldId(id);

				if (companyMovement.getId() == null) {
					
					Company company = companyService.findByOldId(cariKartId);
					
					companyMovement.setCompany(company);
				} else {
					
					companyMovement.setCompany(companyMovement.getCompany());
				}
				
				if (companyMovement.getCompany().getId() != null) {
					
					companyMovement.setMovementAmount(tutar);
					
					if (evrakNo != null)
						companyMovement.setMovementDescription(evrakNo.trim());
					
					companyMovement.setMovementMode(MovementMode.getByValue(evrakTipi));
					companyMovement.setMovementType(MovementType.getByShortValue(borcAlacak));
					companyMovement.setProcessDate(ldTarih);
					
					if (aciklama != null)
						companyMovement.setProductType(aciklama.trim());
					
					companyMovement.setQuantity(miktar);
					
					if (birim != null)
						companyMovement.setUnit(birim.trim());
					
					companyMovement.setUnitPrice(birimFiyat);
					companyMovement.setOldId(id);
					companyMovement.setTerm(vade);
					companyMovement.setTermDate(ldVadeTarihi);
					
					companyMovements.add(companyMovement);
				}
				
			}

			connection.close();
			
			companyMovements.forEach(companyMovement -> companyMovementService.save(companyMovement));
			
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private ResultSet databaseQuery(String query) {

		try {

			return connection.createStatement().executeQuery(query);

		} catch (TypeConversionException e) {

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
