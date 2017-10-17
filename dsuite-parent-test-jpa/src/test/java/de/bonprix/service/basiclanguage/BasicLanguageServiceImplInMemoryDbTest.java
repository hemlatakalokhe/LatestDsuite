package de.bonprix.service.basiclanguage;

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

@ContextConfiguration(classes = { BasicLanguageServiceImpl.class, BasicLanguageRepository.class })
public class BasicLanguageServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {

	@Resource
	private BasicLanguageService basicLanguageService;

	@Resource
	private EntityManager entityManager;

	@Test
	private void findAllTest() {
		List<BasicLanguage> basicLanguages = this.basicLanguageService.findAll(new BasicLanguageFilter());

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(basicLanguages.size(), 2);

		Assert.assertEquals(basicLanguages.get(0)
			.getLanguageElements()
			.size(), 1);
	}

	@Test
	private void findByIdTest() {
		BasicLanguage basicLanguage = this.basicLanguageService.findById(1L);

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(basicLanguage.getLanguageElements()
			.size(), 1);
		Assert.assertEquals(basicLanguage.getLanguageElements()
			.size(), 1);
	}

	@Test
	private void createTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.basicLanguageService.create(basicLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 3, 0, 0);

		BasicLanguage createdExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);

		Assert.assertEquals(createdExampleLanguage.getProperty(), basicLanguage.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();
		basicLanguage.setId(1L);

		this.basicLanguageService.create(basicLanguage);
	}

	@Test
	private void updateTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.basicLanguageService.create(basicLanguage);
		BasicLanguage createdExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);
		createdExampleLanguage.setProperty(-999L);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 1, 0);

		BasicLanguage updatedExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test
	private void updateEmptyExampleLanguageLanguagesTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.basicLanguageService.create(basicLanguage);
		BasicLanguage createdExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);
		createdExampleLanguage.getLanguageElements()
			.clear();

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 2);

		BasicLanguage updatedExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test
	private void updateNullExampleLanguageLanguagesTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.basicLanguageService.create(basicLanguage);
		BasicLanguage createdExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);
		createdExampleLanguage.setLanguageElements(null);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		BasicLanguage updatedExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test
	private void updateRemoveExampleLanguageLanguagesTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.basicLanguageService.create(basicLanguage);
		BasicLanguage createdExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);
		createdExampleLanguage.getLanguageElements()
			.remove(0);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicLanguageService.update(createdExampleLanguage.getId(), createdExampleLanguage);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 1);

		BasicLanguage updatedExampleLanguage = this.basicLanguageService.findById(createdExampleLanguageId);

		Assert.assertEquals(updatedExampleLanguage.getProperty(), createdExampleLanguage.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();
		basicLanguage.setId(null);

		this.basicLanguageService.update(basicLanguage.getId(), basicLanguage);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateWithNotExistingIdTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();
		basicLanguage.setId(999L);

		this.basicLanguageService.update(basicLanguage.getId(), basicLanguage);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteTest() {
		BasicLanguage basicLanguage = getDefaultExampleLanguage();

		Long createdExampleLanguageId = this.basicLanguageService.create(basicLanguage);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicLanguageService.deleteById(createdExampleLanguageId);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 3);

		this.basicLanguageService.findById(createdExampleLanguageId);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteNotExistingIdTest() {
		this.basicLanguageService.deleteById(99999L);
	}

	private BasicLanguage getDefaultExampleLanguage() {
		BasicLanguageLanguage basicLanguageLanguage1 = new BasicLanguageLanguage();
		basicLanguageLanguage1.setLanguageId(1L);
		basicLanguageLanguage1.setName("ExampleLanguageLanguage_1");
		BasicLanguageLanguage basicLanguageLanguage2 = new BasicLanguageLanguage();
		basicLanguageLanguage2.setLanguageId(2L);
		basicLanguageLanguage2.setName("ExampleLanguageLanguage_2");
		BasicLanguage basicLanguage = new BasicLanguage();
		basicLanguage.setProperty(999L);
		basicLanguage.setLanguageElements(Arrays.asList(basicLanguageLanguage1, basicLanguageLanguage2));
		return basicLanguage;
	}

}
