package de.bonprix.service.fetchlanguage;

import java.util.ArrayList;
import java.util.HashSet;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;

public class FetchLanguageServiceImplTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private FetchLanguageServiceImpl fetchLanguageService;

	@Mock
	private FetchLanguageRepository fetchLanguageRepositoryMock;

	@Test
	private void findAllTest() {
		Mockito.when(this.fetchLanguageRepositoryMock.findAll(	Mockito.any(FetchLanguageFilter.class),
																Mockito.any(FetchLanguageFetchOptions.class)))
			.thenReturn(new ArrayList<>());

		FetchLanguageFilter filter = new FetchLanguageFilter();
		FetchLanguageFetchOptions fetchOptions = new FetchLanguageFetchOptions();
		this.fetchLanguageService.findAll(filter, fetchOptions);

		ArgumentCaptor<FetchLanguageFilter> fetchLanguageFilterCaptor = ArgumentCaptor
			.forClass(FetchLanguageFilter.class);
		Mockito.verify(this.fetchLanguageRepositoryMock)
			.findAll(fetchLanguageFilterCaptor.capture(), Mockito.eq(fetchOptions));

		FetchLanguageFilter capturedFetchLanguageFilter = fetchLanguageFilterCaptor.getValue();
		Assert.assertEquals(filter.getPage()
			.intValue(), capturedFetchLanguageFilter.getPage()
				.intValue());
		Assert.assertEquals(filter.getPageSize()
			.intValue(), capturedFetchLanguageFilter.getPageSize()
				.intValue());
	}

	@Test
	private void findByIdTest() {
		long id = 1L;

		Mockito.when(this.fetchLanguageRepositoryMock.findOne(	Mockito.any(FetchLanguageFilter.class),
																Mockito.any(FetchLanguageFetchOptions.class)))
			.thenReturn(getDefaultFetchLanguageEntity());

		FetchLanguageFetchOptions fetchOptions = new FetchLanguageFetchOptions();
		this.fetchLanguageService.findById(id, fetchOptions);

		ArgumentCaptor<FetchLanguageFilter> fetchLanguageFilterCaptor = ArgumentCaptor
			.forClass(FetchLanguageFilter.class);
		Mockito.verify(this.fetchLanguageRepositoryMock)
			.findOne(fetchLanguageFilterCaptor.capture(), Mockito.eq(fetchOptions));
		FetchLanguageFilter filter = fetchLanguageFilterCaptor.getValue();
		Assert.assertEquals(1, filter.getFetchLanguageIds()
			.size());
		Assert.assertEquals(id, filter.getFetchLanguageIds()
			.get(0)
			.longValue());
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void findByIdNotFoundTest() {
		long id = 1L;

		Mockito.when(this.fetchLanguageRepositoryMock.findOne(	Mockito.any(FetchLanguageFilter.class),
																Mockito.any(FetchLanguageFetchOptions.class)))
			.thenReturn(null);

		this.fetchLanguageService.findById(id, new FetchLanguageFetchOptions());
	}

	@Test
	private void deleteByIdTest() {
		long id = 1L;

		FetchLanguageEntity fetchLanguageEntity = new FetchLanguageEntity();
		fetchLanguageEntity.setId(id);

		Mockito.when(this.fetchLanguageRepositoryMock.findOne(Mockito.any(FetchLanguageFilter.class)))
			.thenReturn(fetchLanguageEntity);

		this.fetchLanguageService.deleteById(id);

		Mockito.verify(this.fetchLanguageRepositoryMock)
			.delete(id);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteByIdNotFoundExceptionTest() {
		long id = 1L;

		this.fetchLanguageService.deleteById(id);
	}

	@Test
	private void createTest() {
		FetchLanguage fetchLanguage = getDefaultFetchLanguage();

		Mockito.when(this.fetchLanguageRepositoryMock.save(Mockito.any(FetchLanguageEntity.class)))
			.thenReturn(getDefaultFetchLanguageEntity());

		this.fetchLanguageService.create(fetchLanguage);

		ArgumentCaptor<FetchLanguageEntity> fetchLanguageEntitiyCaptor = ArgumentCaptor
			.forClass(FetchLanguageEntity.class);
		Mockito.verify(this.fetchLanguageRepositoryMock)
			.save(fetchLanguageEntitiyCaptor.capture());

		FetchLanguageEntity capturedFetchLanguageEntity = fetchLanguageEntitiyCaptor.getValue();
		Assert.assertEquals(fetchLanguage.getId(), capturedFetchLanguageEntity.getId());
		Assert.assertEquals(fetchLanguage.getProperty(), capturedFetchLanguageEntity.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		FetchLanguage fetchLanguage = getDefaultFetchLanguage();
		fetchLanguage.setId(1L);

		Mockito.when(this.fetchLanguageRepositoryMock.save(Mockito.any(FetchLanguageEntity.class)))
			.thenReturn(new FetchLanguageEntity());

		this.fetchLanguageService.create(fetchLanguage);
	}

	@Test
	private void updateTest() {
		FetchLanguage fetchLanguage = getDefaultFetchLanguage();
		fetchLanguage.setId(1L);

		FetchLanguageEntity foundFetchLanguageEntity = new FetchLanguageEntity();
		Mockito.when(this.fetchLanguageRepositoryMock.findOne(	Mockito.any(FetchLanguageFilter.class),
																Mockito.any(FetchLanguageFetchOptions.class)))
			.thenReturn(foundFetchLanguageEntity);

		Mockito.when(this.fetchLanguageRepositoryMock.save(Mockito.any(FetchLanguageEntity.class)))
			.thenReturn(new FetchLanguageEntity());

		this.fetchLanguageService.update(fetchLanguage.getId(), fetchLanguage);

		Mockito.verify(this.fetchLanguageRepositoryMock)
			.findOne(Mockito.any(FetchLanguageFilter.class), Mockito.any(FetchLanguageFetchOptions.class));

		Mockito.verify(this.fetchLanguageRepositoryMock)
			.save(foundFetchLanguageEntity);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateNotFoundExceptionTest() {
		FetchLanguage fetchLanguage = getDefaultFetchLanguage();
		fetchLanguage.setId(999L);

		this.fetchLanguageService.update(fetchLanguage.getId(), fetchLanguage);
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		FetchLanguage fetchLanguage = getDefaultFetchLanguage();

		this.fetchLanguageService.update(fetchLanguage.getId(), fetchLanguage);
	}

	private FetchLanguage getDefaultFetchLanguage() {
		FetchLanguage fetchLanguage = new FetchLanguage();
		fetchLanguage.setProperty(999L);
		return fetchLanguage;
	}

	private FetchLanguageEntity getDefaultFetchLanguageEntity() {
		FetchLanguageEntity fetchLanguageEntity = new FetchLanguageEntity();
		fetchLanguageEntity.setId(1L);
		fetchLanguageEntity.setLanguageElementEntities(new HashSet<>());
		return fetchLanguageEntity;
	}
}
