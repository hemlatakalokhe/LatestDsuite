package de.bonprix.service.fetch;

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

@ContextConfiguration(classes = { FetchServiceImpl.class, FetchRepository.class })
public class FetchServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {

	@Resource
	private FetchService fetchService;

	@Resource
	private EntityManager entityManager;

	@Test
	private void findAllTest() {
		List<Fetch> fetchs = this.fetchService.findAll(new FetchFilter(), new FetchOptions());

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(fetchs.size(), 2);
	}

	@Test
	private void findByIdTest() {
		Fetch fetch = this.fetchService.findById(1L, new FetchOptions());

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(fetch.getProperty()
			.intValue(), 123);
	}

	@Test
	private void createTest() {
		Fetch fetch = getDefaultFetch();

		Long createdFetchId = this.fetchService.create(fetch);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 1, 0, 0);

		Fetch createdFetch = this.fetchService.findById(createdFetchId, new FetchOptions());

		Assert.assertEquals(createdFetch.getProperty(), fetch.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		Fetch fetch = getDefaultFetch();
		fetch.setId(1L);

		this.fetchService.create(fetch);
	}

	@Test
	private void updateTest() {
		Fetch fetch = getDefaultFetch();

		Long createdFetchId = this.fetchService.create(fetch);
		Fetch createdFetch = this.fetchService.findById(createdFetchId, new FetchOptions());
		createdFetch.setProperty(-999L);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchService.update(createdFetch.getId(), createdFetch);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 1, 0);

		Fetch updatedFetch = this.fetchService.findById(createdFetchId, new FetchOptions());

		Assert.assertEquals(updatedFetch.getProperty(), createdFetch.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		Fetch fetch = getDefaultFetch();
		fetch.setId(null);

		this.fetchService.update(fetch.getId(), fetch);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateWithNotExistingIdTest() {
		Fetch fetch = getDefaultFetch();
		fetch.setId(999L);

		this.fetchService.update(fetch.getId(), fetch);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteTest() {
		Fetch fetch = getDefaultFetch();

		Long createdFetchId = this.fetchService.create(fetch);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.fetchService.deleteById(createdFetchId);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 1);

		this.fetchService.findById(createdFetchId, new FetchOptions());
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteNotExistingIdTest() {
		this.fetchService.deleteById(99999L);
	}

	private Fetch getDefaultFetch() {
		Fetch fetch = new Fetch();
		fetch.setProperty(999L);
		return fetch;
	}

}
