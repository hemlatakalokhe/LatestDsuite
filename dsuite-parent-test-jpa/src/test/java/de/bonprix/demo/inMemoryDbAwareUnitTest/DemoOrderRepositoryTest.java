package de.bonprix.demo.inMemoryDbAwareUnitTest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.sqlcount.SQLStatementCountValidator;
import de.bonprix.test.InMemoryDbAwareUnitTest;

/***
 * 
 * @author vbaghdas
 */

public class DemoOrderRepositoryTest extends InMemoryDbAwareUnitTest {

	@Autowired
	private DemoOrderRepository orderRep;

	@Test(priority = 1)
	public void testFindById() {
		final DemoOrder o = this.orderRep.findOne(2016698758L);

		MatcherAssert.assertThat(o, Matchers.notNullValue());
		MatcherAssert.assertThat(o.getId(), Matchers.equalTo(2016698758L));
		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
	}

	@Test(priority = 2)
	public void testSave() {
		Assert.assertEquals(this.orderRep	.findAll()
											.size(),
							6);

		DemoOrder newDemoOrder = new DemoOrder();
		newDemoOrder.setId(1234L);
		this.orderRep.save(newDemoOrder);

		Assert.assertEquals(this.orderRep	.findAll()
											.size(),
							7);
		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(2, 1, 0, 0);
	}

	@Test(priority = 3)
	public void testSaveAndFlush() {
		Assert.assertEquals(this.orderRep	.findAll()
											.size(),
							6);

		DemoOrder newDemoOrder = new DemoOrder();
		newDemoOrder.setId(1234L);
		this.orderRep.saveAndFlush(newDemoOrder);

		Assert.assertEquals(this.orderRep	.findAll()
											.size(),
							7);
		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(2, 1, 0, 0);
	}

	@Test(priority = 4)
	public void testFindAll() {
		Assert.assertEquals(this.orderRep	.findAll()
											.size(),
							6);
		SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
	}

}
