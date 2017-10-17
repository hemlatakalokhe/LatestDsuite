package de.bonprix.service.basic;

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

public class BasicServiceImplTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private BasicServiceImpl basicService;

	@Mock
	private BasicRepository basicRepositoryMock;

	@Test
	private void findAllTest() {
		Mockito.when(this.basicRepositoryMock.findAll(Mockito.any(BasicFilter.class)))
			.thenReturn(new ArrayList<>());

		BasicFilter filter = new BasicFilter();
		this.basicService.findAll(filter);

		ArgumentCaptor<BasicFilter> basicFilterCaptor = ArgumentCaptor.forClass(BasicFilter.class);
		Mockito.verify(this.basicRepositoryMock)
			.findAll(basicFilterCaptor.capture());

		BasicFilter capturedBasicFilter = basicFilterCaptor.getValue();
		Assert.assertEquals(filter.getPage()
			.intValue(), capturedBasicFilter.getPage()
				.intValue());
		Assert.assertEquals(filter.getPageSize()
			.intValue(), capturedBasicFilter.getPageSize()
				.intValue());
	}

	@Test
	private void findByIdTest() {
		long id = 1L;

		Mockito.when(this.basicRepositoryMock.findOne(Mockito.any(BasicFilter.class)))
			.thenReturn(getDefaultBasicEntity());

		this.basicService.findById(id);

		ArgumentCaptor<BasicFilter> basicFilterCaptor = ArgumentCaptor.forClass(BasicFilter.class);
		Mockito.verify(this.basicRepositoryMock)
			.findOne(basicFilterCaptor.capture());
		BasicFilter filter = basicFilterCaptor.getValue();
		Assert.assertEquals(1, filter.getBasicIds()
			.size());
		Assert.assertEquals(id, filter.getBasicIds()
			.get(0)
			.longValue());
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void findByIdNotFoundTest() {
		long id = 1L;

		Mockito.when(this.basicRepositoryMock.findOne(Mockito.any(BasicFilter.class)))
			.thenReturn(null);

		this.basicService.findById(id);
	}

	@Test
	private void deleteByIdTest() {
		long id = 1L;

		BasicEntity basicEntity = new BasicEntity();
		basicEntity.setId(id);

		Mockito.when(this.basicRepositoryMock.findOne(Mockito.any(BasicFilter.class)))
			.thenReturn(basicEntity);

		this.basicService.deleteById(id);

		Mockito.verify(this.basicRepositoryMock)
			.delete(id);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteByIdNotFoundExceptionTest() {
		long id = 1L;

		this.basicService.deleteById(id);
	}

	@Test
	private void createTest() {
		Basic basic = getDefaultBasic();

		Mockito.when(this.basicRepositoryMock.save(Mockito.any(BasicEntity.class)))
			.thenReturn(getDefaultBasicEntity());

		this.basicService.create(basic);

		ArgumentCaptor<BasicEntity> basicEntitiyCaptor = ArgumentCaptor.forClass(BasicEntity.class);
		Mockito.verify(this.basicRepositoryMock)
			.save(basicEntitiyCaptor.capture());

		BasicEntity capturedBasicEntity = basicEntitiyCaptor.getValue();
		Assert.assertEquals(basic.getId(), capturedBasicEntity.getId());
		Assert.assertEquals(basic.getProperty(), capturedBasicEntity.getProperty());
		Assert.assertEquals(basic.getOptlock(), capturedBasicEntity.getOptlock());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		Basic basic = getDefaultBasic();
		basic.setId(1L);

		Mockito.when(this.basicRepositoryMock.save(Mockito.any(BasicEntity.class)))
			.thenReturn(new BasicEntity());

		this.basicService.create(basic);
	}

	@Test
	private void updateTest() {
		Basic Basic = getDefaultBasic();
		Basic.setId(1L);

		BasicEntity foundBasicEntity = new BasicEntity();
		Mockito.when(this.basicRepositoryMock.findOne(Mockito.any(BasicFilter.class)))
			.thenReturn(foundBasicEntity);

		Mockito.when(this.basicRepositoryMock.save(Mockito.any(BasicEntity.class)))
			.thenReturn(new BasicEntity());

		this.basicService.update(Basic.getId(), Basic);

		Mockito.verify(this.basicRepositoryMock)
			.findOne(Mockito.any(BasicFilter.class));

		Mockito.verify(this.basicRepositoryMock)
			.save(foundBasicEntity);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateNotFoundExceptionTest() {
		Basic basic = getDefaultBasic();
		basic.setId(999L);

		this.basicService.update(basic.getId(), basic);
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		Basic basic = getDefaultBasic();

		this.basicService.update(basic.getId(), basic);
	}

	private Basic getDefaultBasic() {
		Basic basic = new Basic();
		basic.setProperty(999L);
		basic.setOptlock(0L);
		return basic;
	}

	private BasicEntity getDefaultBasicEntity() {
		BasicEntity basicEntity = new BasicEntity();
		basicEntity.setId(1L);
		basicEntity.setOptlock(0L);
		return basicEntity;
	}
}
