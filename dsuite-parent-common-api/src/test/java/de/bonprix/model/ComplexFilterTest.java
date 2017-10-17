package de.bonprix.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * @author Ivan Slavchev
 */
public class ComplexFilterTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComplexFilterTest.class);

	@Test
	public void testComplexFilterJsonSerializationDeserialization() throws IOException {

		//----Set up----
		final ZonedDateTime time = ZonedDateTime.now();

		final ComplexFilter complexFilter = new ComplexFilter();
		complexFilter.addStringFilter(PropertyDescriptor.of("testProp1"), StringFilterOperation.CONTAINS, "asd");
		complexFilter.addCollectionFilter(PropertyDescriptor.of("testProp2"), CollectionFilterOperation.IN, Arrays.asList(1L, 2L));
		complexFilter.addNumberFilter(PropertyDescriptor.of("testProp3"), NumberFilterOperation.LESS_THAN, 1000L);
		complexFilter.addDateFilter(PropertyDescriptor.of("testProp4"), DateFilterOperation.BEFORE, time);

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		//----Call tested method----
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(byteArrayOutputStream, complexFilter);

		System.out.println(byteArrayOutputStream.toString());

		final ComplexFilter resultComplexFilter = mapper.readValue(byteArrayOutputStream.toByteArray(), ComplexFilter.class);

		//----Asserts----
		final JunctionFilter baseFilter = resultComplexFilter.getBaseJunctionFilter();

		assertThat(baseFilter.getOperation(), is(JunctionFilterOperation.AND)); //AND should be the default

		final Set<Filter> filters = baseFilter.getValue();

		assertThat(filters, hasItem(StringPropertyFilter.of(PropertyDescriptor.of("testProp1"), StringFilterOperation.CONTAINS, "asd")));
		assertThat(filters, hasItem(CollectionPropertyFilter.of(PropertyDescriptor.of("testProp2"), CollectionFilterOperation.IN, Arrays.asList(1L, 2L))));
		assertThat(filters, hasItem(NumberPropertyFilter.of(PropertyDescriptor.of("testProp3"), NumberFilterOperation.LESS_THAN, 1000L)));
		assertThat(filters, hasItem(DatePropertyFilter.of(PropertyDescriptor.of("testProp4"), DateFilterOperation.BEFORE, time)));
	}
}