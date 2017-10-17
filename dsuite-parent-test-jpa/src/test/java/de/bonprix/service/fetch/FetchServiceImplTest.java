package de.bonprix.service.fetch;

import java.util.ArrayList;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;

public class FetchServiceImplTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private FetchServiceImpl fetchService;

	@Mock
	private FetchRepository fetchRepositoryMock;

	@Test
	private void findAllTest() {
		Mockito.when(this.fetchRepositoryMock.findAll(Mockito.any(FetchFilter.class), Mockito.any(FetchOptions.class)))
			.thenReturn(new ArrayList<>());

		FetchFilter filter = new FetchFilter();
		FetchOptions fetchOptions = new FetchOptions();
		this.fetchService.findAll(filter, fetchOptions);

		ArgumentCaptor<FetchFilter> fetchFilterCaptor = ArgumentCaptor.forClass(FetchFilter.class);
		Mockito.verify(this.fetchRepositoryMock)
			.findAll(fetchFilterCaptor.capture(), Mockito.eq(fetchOptions));

		FetchFilter capturedFetchFilter = fetchFilterCaptor.getValue();
		Assert.assertEquals(filter.getPage()
			.intValue(), capturedFetchFilter.getPage()
				.intValue());
		Assert.assertEquals(filter.getPageSize()
			.intValue(), capturedFetchFilter.getPageSize()
				.intValue());
	}

	@Test
	private void findByIdTest() {
		long id = 1L;

		Mockito.when(this.fetchRepositoryMock.findOne(Mockito.any(FetchFilter.class), Mockito.any(FetchOptions.class)))
			.thenReturn(getDefaultFetchEntity());

		FetchOptions fetchOptions = new FetchOptions();
		this.fetchService.findById(id, fetchOptions);

		ArgumentCaptor<FetchFilter> fetchFilterCaptor = ArgumentCaptor.forClass(FetchFilter.class);
		Mockito.verify(this.fetchRepositoryMock)
			.findOne(fetchFilterCaptor.capture(), Mockito.eq(fetchOptions));
		FetchFilter filter = fetchFilterCaptor.getValue();
		Assert.assertEquals(1, filter.getFetchIds()
			.size());
		Assert.assertEquals(id, filter.getFetchIds()
			.get(0)
			.longValue());
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void findByIdNotFoundTest() {
		long id = 1L;

		Mockito.when(this.fetchRepositoryMock.findOne(Mockito.any(FetchFilter.class), Mockito.any(FetchOptions.class)))
			.thenReturn(null);

		this.fetchService.findById(id, new FetchOptions());
	}

	@Test
	private void deleteByIdTest() {
		long id = 1L;

		FetchEntity fetchEntity = new FetchEntity();
		fetchEntity.setId(id);

		Mockito.when(this.fetchRepositoryMock.findOne(Mockito.any(FetchFilter.class)))
			.thenReturn(fetchEntity);

		this.fetchService.deleteById(id);

		Mockito.verify(this.fetchRepositoryMock)
			.delete(id);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteByIdNotFoundExceptionTest() {
		long id = 1L;

		this.fetchService.deleteById(id);
	}

	@Test
	private void createTest() {
		Fetch fetch = getDefaultFetch();

		Mockito.when(this.fetchRepositoryMock.save(Mockito.any(FetchEntity.class)))
			.thenReturn(getDefaultFetchEntity());

		this.fetchService.create(fetch);

		ArgumentCaptor<FetchEntity> fetchEntitiyCaptor = ArgumentCaptor.forClass(FetchEntity.class);
		Mockito.verify(this.fetchRepositoryMock)
			.save(fetchEntitiyCaptor.capture());

		FetchEntity capturedFetchEntity = fetchEntitiyCaptor.getValue();
		Assert.assertEquals(fetch.getId(), capturedFetchEntity.getId());
		Assert.assertEquals(fetch.getProperty(), capturedFetchEntity.getProperty());
		Assert.assertEquals(fetch.getOptlock(), capturedFetchEntity.getOptlock());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		Fetch fetch = getDefaultFetch();
		fetch.setId(1L);

		Mockito.when(this.fetchRepositoryMock.save(Mockito.any(FetchEntity.class)))
			.thenReturn(new FetchEntity());

		this.fetchService.create(fetch);
	}

	@Test
	private void updateTest() {
		Fetch Fetch = getDefaultFetch();
		Fetch.setId(1L);

		FetchEntity foundFetchEntity = new FetchEntity();
		Mockito.when(this.fetchRepositoryMock.findOne(Mockito.any(FetchFilter.class), Mockito.any(FetchOptions.class)))
			.thenReturn(foundFetchEntity);

		Mockito.when(this.fetchRepositoryMock.save(Mockito.any(FetchEntity.class)))
			.thenReturn(new FetchEntity());

		this.fetchService.update(Fetch.getId(), Fetch);

		Mockito.verify(this.fetchRepositoryMock)
			.findOne(Mockito.any(FetchFilter.class), Mockito.any(FetchOptions.class));

		Mockito.verify(this.fetchRepositoryMock)
			.save(foundFetchEntity);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateNotFoundExceptionTest() {
		Fetch fetch = getDefaultFetch();
		fetch.setId(999L);

		this.fetchService.update(fetch.getId(), fetch);
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		Fetch fetch = getDefaultFetch();

		this.fetchService.update(fetch.getId(), fetch);
	}

	private Fetch getDefaultFetch() {
		Fetch fetch = new Fetch();
		fetch.setProperty(999L);
		fetch.setOptlock(0L);
		return fetch;
	}

	private FetchEntity getDefaultFetchEntity() {
		FetchEntity fetchEntity = new FetchEntity();
		fetchEntity.setId(1L);
		fetchEntity.setOptlock(0L);
		return fetchEntity;
	}
}
