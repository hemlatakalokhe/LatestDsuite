package de.bonprix.service.basiclanguage;

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

public class BasicLanguageServiceImplTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private BasicLanguageServiceImpl basicLanguageService;

	@Mock
	private BasicLanguageRepository basicLanguageRepositoryMock;

	@Test
	private void findAllTest() {
		Mockito.when(this.basicLanguageRepositoryMock.findAll(Mockito.any(BasicLanguageFilter.class)))
			.thenReturn(new ArrayList<>());

		BasicLanguageFilter filter = new BasicLanguageFilter();
		this.basicLanguageService.findAll(filter);

		ArgumentCaptor<BasicLanguageFilter> basicLanguageFilterCaptor = ArgumentCaptor
			.forClass(BasicLanguageFilter.class);
		Mockito.verify(this.basicLanguageRepositoryMock)
			.findAll(basicLanguageFilterCaptor.capture());

		BasicLanguageFilter capturedBasicLanguageFilter = basicLanguageFilterCaptor.getValue();
		Assert.assertEquals(filter.getPage()
			.intValue(), capturedBasicLanguageFilter.getPage()
				.intValue());
		Assert.assertEquals(filter.getPageSize()
			.intValue(), capturedBasicLanguageFilter.getPageSize()
				.intValue());
	}

	@Test
	private void findByIdTest() {
		long id = 1L;

		Mockito.when(this.basicLanguageRepositoryMock.findOne(Mockito.any(BasicLanguageFilter.class)))
			.thenReturn(getDefaultBasicLanguageEntity());

		this.basicLanguageService.findById(id);

		ArgumentCaptor<BasicLanguageFilter> basicLanguageFilterCaptor = ArgumentCaptor
			.forClass(BasicLanguageFilter.class);
		Mockito.verify(this.basicLanguageRepositoryMock)
			.findOne(basicLanguageFilterCaptor.capture());
		BasicLanguageFilter filter = basicLanguageFilterCaptor.getValue();
		Assert.assertEquals(1, filter.getBasicLanguageIds()
			.size());
		Assert.assertEquals(id, filter.getBasicLanguageIds()
			.get(0)
			.longValue());
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void findByIdNotFoundTest() {
		long id = 1L;

		Mockito.when(this.basicLanguageRepositoryMock.findOne(Mockito.any(BasicLanguageFilter.class)))
			.thenReturn(null);

		this.basicLanguageService.findById(id);
	}

	@Test
	private void deleteByIdTest() {
		long id = 1L;

		BasicLanguageEntity basicLanguageEntity = new BasicLanguageEntity();
		basicLanguageEntity.setId(id);

		Mockito.when(this.basicLanguageRepositoryMock.findOne(Mockito.any(BasicLanguageFilter.class)))
			.thenReturn(basicLanguageEntity);

		this.basicLanguageService.deleteById(id);

		Mockito.verify(this.basicLanguageRepositoryMock)
			.delete(id);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteByIdNotFoundExceptionTest() {
		long id = 1L;

		this.basicLanguageService.deleteById(id);
	}

	@Test
	private void createTest() {
		BasicLanguage basicLanguage = getDefaultBasicLanguage();

		Mockito.when(this.basicLanguageRepositoryMock.save(Mockito.any(BasicLanguageEntity.class)))
			.thenReturn(getDefaultBasicLanguageEntity());

		this.basicLanguageService.create(basicLanguage);

		ArgumentCaptor<BasicLanguageEntity> basicLanguageEntitiyCaptor = ArgumentCaptor
			.forClass(BasicLanguageEntity.class);
		Mockito.verify(this.basicLanguageRepositoryMock)
			.save(basicLanguageEntitiyCaptor.capture());

		BasicLanguageEntity capturedBasicLanguageEntity = basicLanguageEntitiyCaptor.getValue();
		Assert.assertEquals(basicLanguage.getId(), capturedBasicLanguageEntity.getId());
		Assert.assertEquals(basicLanguage.getProperty(), capturedBasicLanguageEntity.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		BasicLanguage basicLanguage = getDefaultBasicLanguage();
		basicLanguage.setId(1L);

		Mockito.when(this.basicLanguageRepositoryMock.save(Mockito.any(BasicLanguageEntity.class)))
			.thenReturn(new BasicLanguageEntity());

		this.basicLanguageService.create(basicLanguage);
	}

	@Test
	private void updateTest() {
		BasicLanguage basicLanguage = getDefaultBasicLanguage();
		basicLanguage.setId(1L);

		BasicLanguageEntity foundBasicLanguageEntity = new BasicLanguageEntity();
		Mockito.when(this.basicLanguageRepositoryMock.findOne(Mockito.any(BasicLanguageFilter.class)))
			.thenReturn(foundBasicLanguageEntity);

		Mockito.when(this.basicLanguageRepositoryMock.save(Mockito.any(BasicLanguageEntity.class)))
			.thenReturn(new BasicLanguageEntity());

		this.basicLanguageService.update(basicLanguage.getId(), basicLanguage);

		Mockito.verify(this.basicLanguageRepositoryMock)
			.findOne(Mockito.any(BasicLanguageFilter.class));

		Mockito.verify(this.basicLanguageRepositoryMock)
			.save(foundBasicLanguageEntity);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateNotFoundExceptionTest() {
		BasicLanguage basicLanguage = getDefaultBasicLanguage();
		basicLanguage.setId(999L);

		this.basicLanguageService.update(basicLanguage.getId(), basicLanguage);
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		BasicLanguage basicLanguage = getDefaultBasicLanguage();

		this.basicLanguageService.update(basicLanguage.getId(), basicLanguage);
	}

	private BasicLanguage getDefaultBasicLanguage() {
		BasicLanguage basicLanguage = new BasicLanguage();
		basicLanguage.setProperty(999L);
		return basicLanguage;
	}

	private BasicLanguageEntity getDefaultBasicLanguageEntity() {
		BasicLanguageEntity basicLanguageEntity = new BasicLanguageEntity();
		basicLanguageEntity.setId(1L);
		basicLanguageEntity.setLanguageElementEntities(new HashSet<>());
		return basicLanguageEntity;
	}
}
