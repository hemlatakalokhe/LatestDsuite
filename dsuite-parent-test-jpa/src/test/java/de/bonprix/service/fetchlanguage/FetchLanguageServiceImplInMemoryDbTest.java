package de.bonprix.service.fetchlanguage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.sqlcount.SQLStatementCountValidator;
import de.bonprix.test.InMemoryDbAwareUnitTest;

@ContextConfiguration(classes = { FetchLanguageServiceImpl.class, FetchLanguageRepository.class })
public class FetchLanguageServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {

	@Resource
	private FetchLanguageService fetchLanguageService;

	@Resource
	private EntityManager entityManager;

	@Test
	private void findAllTest() {
		List<FetchLanguage> fetchLanguages = this.fetchLanguageService.findAll(	new FetchLanguageFilter(),
																				new FetchLanguageFetchOptions());

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(fetchLanguages.size(), 2);

		Assert.assertEquals(fetchLanguages.get(0)
			.getLanguageElements()
			.size(), 1);
	}

	@Test
	private void findByIdTest() {
		FetchLanguage fetchLanguage = this.fetchLanguageService.findById(1L, new FetchLanguageFetchOptions());

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(fetchLanguage.getLanguageElements()
			.size(), 1);
		Assert.assertEquals(fetchLanguage.getLanguageElements()
			.size(), 1);
	}

	@Test
	private void createTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.fetchLanguageService.create(fetchLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 3, 0, 0);

		FetchLanguage createdExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());

		Assert.assertEquals(createdExampleLanguage.getProperty(), fetchLanguage.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();
		fetchLanguage.setId(1L);

		this.fetchLanguageService.create(fetchLanguage);
	}

	@Test
	private void updateTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.fetchLanguageService.create(fetchLanguage);
		FetchLanguage createdExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());
		createdExampleLanguage.setProperty(-999L);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 1, 0);

		FetchLanguage updatedExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test
	private void updateEmptyExampleLanguageLanguagesTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.fetchLanguageService.create(fetchLanguage);
		FetchLanguage createdExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());
		createdExampleLanguage.getLanguageElements()
			.clear();

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 2);

		FetchLanguage updatedExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test
	private void updateNullExampleLanguageLanguagesTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.fetchLanguageService.create(fetchLanguage);
		FetchLanguage createdExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());
		createdExampleLanguage.setLanguageElements(null);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		FetchLanguage updatedExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test
	private void updateRemoveExampleLanguageLanguagesTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.fetchLanguageService.create(fetchLanguage);
		FetchLanguage createdExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());
		createdExampleLanguage.getLanguageElements()
			.remove(0);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 1);

		FetchLanguage updatedExampleLanguage = this.fetchLanguageService.findById(	createdExampleLanguageId,
																					new FetchLanguageFetchOptions());

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();
		fetchLanguage.setId(null);

		this.fetchLanguageService.update(fetchLanguage.getId(), fetchLanguage);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateWithNotExistingIdTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();
		fetchLanguage.setId(999L);

		this.fetchLanguageService.update(fetchLanguage.getId(), fetchLanguage);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteTest() {
		FetchLanguage fetchLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.fetchLanguageService.create(fetchLanguage);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchLanguageService.deleteById(createdExampleLanguageId);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 3);

		this.fetchLanguageService.findById(createdExampleLanguageId, new FetchLanguageFetchOptions());
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteNotExistingIdTest() {
		this.fetchLanguageService.deleteById(99999L);
	}

	private FetchLanguage getDefaultExampleLanguage() {
		FetchLanguageLanguage fetchLanguageLanguage1 = new FetchLanguageLanguage();
		fetchLanguageLanguage1.setLanguageId(1L);
		fetchLanguageLanguage1.setName("ExampleLanguageLanguage_1");
		FetchLanguageLanguage fetchLanguageLanguage2 = new FetchLanguageLanguage();
		fetchLanguageLanguage2.setLanguageId(2L);
		fetchLanguageLanguage2.setName("ExampleLanguageLanguage_2");
		FetchLanguage fetchLanguage = new FetchLanguage();
		fetchLanguage.setProperty(999L);
		fetchLanguage.setLanguageElements(Arrays.asList(fetchLanguageLanguage1, fetchLanguageLanguage2));
		return fetchLanguage;
	}

}
