package de.bonprix.service.basic;

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

@ContextConfiguration(classes = { BasicServiceImpl.class, BasicRepository.class })
public class BasicServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {

	@Resource
	private BasicService basicService;

	@Resource
	private EntityManager entityManager;

	@Test
	private void findAllTest() {
		List<Basic> basics = this.basicService.findAll(new BasicFilter());

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(basics.size(), 2);
	}

	@Test
	private void findByIdTest() {
		Basic basic = this.basicService.findById(1L);

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);

		Assert.assertEquals(basic.getProperty()
			.intValue(), 123);
	}

	@Test
	private void createTest() {
		Basic basic = getDefaultBasic();

		Long createdBasicId = this.basicService.create(basic);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 1, 0, 0);

		Basic createdBasic = this.basicService.findById(createdBasicId);

		Assert.assertEquals(createdBasic.getProperty(), basic.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void createWithIdTest() {
		Basic basic = getDefaultBasic();
		basic.setId(1L);

		this.basicService.create(basic);
	}

	@Test
	private void updateTest() {
		Basic basic = getDefaultBasic();

		Long createdBasicId = this.basicService.create(basic);
		Basic createdBasic = this.basicService.findById(createdBasicId);
		createdBasic.setProperty(-999L);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicService.update(createdBasic.getId(), createdBasic);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 1, 0);

		Basic updatedBasic = this.basicService.findById(createdBasicId);

		Assert.assertEquals(updatedBasic.getProperty(), createdBasic.getProperty());
	}

	@Test(expectedExceptions = BadRequestException.class)
	private void updateWithoutIdTest() {
		Basic basic = getDefaultBasic();
		basic.setId(null);

		this.basicService.update(basic.getId(), basic);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void updateWithNotExistingIdTest() {
		Basic basic = getDefaultBasic();
		basic.setId(999L);

		this.basicService.update(basic.getId(), basic);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteTest() {
		Basic basic = getDefaultBasic();

		Long createdBasicId = this.basicService.create(basic);

		this.entityManager.flush();
		SQLStatementCountValidator.reset();

		this.basicService.deleteById(createdBasicId);
		this.entityManager.flush();

		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 1);

		this.basicService.findById(createdBasicId);
	}

	@Test(expectedExceptions = NotFoundException.class)
	private void deleteNotExistingIdTest() {
		this.basicService.deleteById(99999L);
	}

	private Basic getDefaultBasic() {
		Basic basic = new Basic();
		basic.setProperty(999L);
		return basic;
	}

}
