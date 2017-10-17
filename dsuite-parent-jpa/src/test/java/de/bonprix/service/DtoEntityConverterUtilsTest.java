package de.bonprix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.service.DtoEntityConverterUtils;
import de.bonprix.service.entities.ExampleFetchOptions;
import de.bonprix.service.entities.ExampleLanguageContainer;
import de.bonprix.service.entities.ExampleLanguageContainerEntity;
import de.bonprix.service.entities.ExampleLanguageElement;
import de.bonprix.service.entities.ExampleLanguageElementEntity;

public class DtoEntityConverterUtilsTest extends BaseConfiguredUnitTest {

	@Test
	public void updateLanguageElementNullElementDtosEntities() {
		Set<ExampleLanguageElementEntity> exampleLanguageElementEntities = new HashSet<>();
		ExampleLanguageElementEntity elementEntity1 = new ExampleLanguageElementEntity();
		elementEntity1.setId(1L);
		elementEntity1.setLanguageId(1L);
		exampleLanguageElementEntities.add(elementEntity1);

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = new ExampleLanguageContainerEntity();
		exampleLanguageContainerEntity.setExampleLanguageElementEntities(exampleLanguageElementEntities);

		ExampleLanguageContainer exampleLanguageContainer = new ExampleLanguageContainer();

		DtoEntityConverterUtils.updateLanguageElementEntities(	exampleLanguageContainerEntity, exampleLanguageContainer,
																ExampleLanguageElementEntity.class);

		Assert.assertEquals(exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.size(), 1);
		Assert.assertEquals(exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.iterator()
			.next(), elementEntity1);
	}

	@Test
	public void updateLanguageElementEmptyElementDtosEntities() {
		Set<ExampleLanguageElementEntity> exampleLanguageElementEntities = new HashSet<>();
		ExampleLanguageElementEntity elementEntity1 = new ExampleLanguageElementEntity();
		elementEntity1.setId(1L);
		elementEntity1.setLanguageId(1L);
		exampleLanguageElementEntities.add(elementEntity1);

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = new ExampleLanguageContainerEntity();
		exampleLanguageContainerEntity.setExampleLanguageElementEntities(exampleLanguageElementEntities);

		ExampleLanguageContainer exampleLanguageContainer = new ExampleLanguageContainer();
		exampleLanguageContainer.setLanguageElements(new ArrayList<>());

		DtoEntityConverterUtils.updateLanguageElementEntities(	exampleLanguageContainerEntity, exampleLanguageContainer,
																ExampleLanguageElementEntity.class);

		Assert.assertEquals(exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.size(), 0);
	}

	@Test
	public void updateLanguageElementRemoveElementDtosEntities() {
		Set<ExampleLanguageElementEntity> exampleLanguageElementEntities = new HashSet<>();
		ExampleLanguageElementEntity elementEntity1 = new ExampleLanguageElementEntity();
		elementEntity1.setId(1L);
		elementEntity1.setLanguageId(1L);
		exampleLanguageElementEntities.add(elementEntity1);
		ExampleLanguageElementEntity elementEntity2 = new ExampleLanguageElementEntity();
		elementEntity2.setId(2L);
		elementEntity2.setLanguageId(2L);
		exampleLanguageElementEntities.add(elementEntity2);

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = new ExampleLanguageContainerEntity();
		exampleLanguageContainerEntity.setExampleLanguageElementEntities(exampleLanguageElementEntities);

		List<ExampleLanguageElement> exampleLanguageElements = new ArrayList<>();
		ExampleLanguageElement element1 = new ExampleLanguageElement();
		element1.setId(1L);
		element1.setLanguageId(1L);
		exampleLanguageElements.add(element1);

		ExampleLanguageContainer exampleLanguageContainer = new ExampleLanguageContainer();
		exampleLanguageContainer.setLanguageElements(exampleLanguageElements);

		DtoEntityConverterUtils.updateLanguageElementEntities(	exampleLanguageContainerEntity, exampleLanguageContainer,
																ExampleLanguageElementEntity.class);

		Assert.assertEquals(exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.size(), 1);
		Assert.assertEquals(exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.iterator()
			.next(), elementEntity1);
	}

	@Test
	public void updateLanguageElementAddElementDtosEntities() {
		ExampleLanguageContainerEntity exampleLanguageContainerEntity = new ExampleLanguageContainerEntity();

		List<ExampleLanguageElement> exampleLanguageElements = new ArrayList<>();
		ExampleLanguageElement element1 = new ExampleLanguageElement();
		element1.setId(1L);
		element1.setLanguageId(1L);
		exampleLanguageElements.add(element1);

		ExampleLanguageContainer exampleLanguageContainer = new ExampleLanguageContainer();
		exampleLanguageContainer.setLanguageElements(exampleLanguageElements);

		DtoEntityConverterUtils.updateLanguageElementEntities(	exampleLanguageContainerEntity, exampleLanguageContainer,
																ExampleLanguageElementEntity.class);

		ExampleLanguageElementEntity elementEntity1 = exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.iterator()
			.next();

		Assert.assertEquals(exampleLanguageContainerEntity.getExampleLanguageElementEntities()
			.size(), 1);
		Assert.assertEquals(elementEntity1.getId(), element1.getId());
		Assert.assertEquals(elementEntity1.getLanguageId(), element1.getLanguageId());
	}

	@Test
	public void convertToEntityTest() {
		Long id = 123L;

		ExampleLanguageContainer exampleLanguageContainer = Mockito.mock(ExampleLanguageContainer.class);
		Mockito.when(exampleLanguageContainer.getId())
			.thenReturn(id);

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = Mockito
			.spy(ExampleLanguageContainerEntity.class);

		exampleLanguageContainerEntity = DtoEntityConverterUtils.convertToEntity(	exampleLanguageContainerEntity,
																					exampleLanguageContainer);

		Mockito.verify(exampleLanguageContainer)
			.getId();
		Mockito.verify(exampleLanguageContainerEntity)
			.updateEntity(exampleLanguageContainer);

		Assert.assertEquals(exampleLanguageContainerEntity.getId(), id);
	}

	@Test
	public void convertToEntityWithoutInstanceTest() {
		Long id = 123L;

		ExampleLanguageContainer exampleLanguageContainer = Mockito.mock(ExampleLanguageContainer.class);
		Mockito.when(exampleLanguageContainer.getId())
			.thenReturn(id);

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = DtoEntityConverterUtils
			.convertToEntity(exampleLanguageContainer, ExampleLanguageContainerEntity.class);

		Mockito.verify(exampleLanguageContainer)
			.getId();

		Assert.assertEquals(exampleLanguageContainerEntity.getId(), id);
	}

	@Test
	public void convertToDtoTest() {
		Long id = 123L;

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = Mockito
			.mock(ExampleLanguageContainerEntity.class);
		Mockito.when(exampleLanguageContainerEntity.getId())
			.thenReturn(id);

		ExampleLanguageContainer exampleLanguageContainer = DtoEntityConverterUtils
			.convertToDto(exampleLanguageContainerEntity, ExampleLanguageContainer.class);

		Mockito.verify(exampleLanguageContainerEntity)
			.getId();
		Mockito.verify(exampleLanguageContainerEntity)
			.updateDto(exampleLanguageContainer);

		Assert.assertEquals(exampleLanguageContainer.getId(), id);
	}

	@Test
	public void convertToDtoWithFetchOptionsTest() {
		Long id = 123L;

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = Mockito
			.mock(ExampleLanguageContainerEntity.class);
		Mockito.when(exampleLanguageContainerEntity.getId())
			.thenReturn(id);

		ExampleFetchOptions exampleFetchOptions = new ExampleFetchOptions();

		ExampleLanguageContainer exampleLanguageContainer = DtoEntityConverterUtils
			.convertToDto(exampleLanguageContainerEntity, exampleFetchOptions, ExampleLanguageContainer.class);

		Mockito.verify(exampleLanguageContainerEntity)
			.getId();
		Mockito.verify(exampleLanguageContainerEntity)
			.updateDto(exampleLanguageContainer, exampleFetchOptions);
		Mockito.verify(exampleLanguageContainerEntity, Mockito.never())
			.updateDto(exampleLanguageContainer);

		Assert.assertEquals(exampleLanguageContainer.getId(), id);
	}

	@Test
	public void convertToLanguageElementDTOsTest() {
		Long id = 123L;
		Long languageId = 345L;

		ExampleLanguageContainer exampleLanguageContainer = new ExampleLanguageContainer();

		ExampleLanguageElementEntity exampleLanguageElementEntity = Mockito.mock(ExampleLanguageElementEntity.class);
		Mockito.when(exampleLanguageElementEntity.getId())
			.thenReturn(id);
		Mockito.when(exampleLanguageElementEntity.getLanguageId())
			.thenReturn(languageId);

		ExampleLanguageContainerEntity exampleLanguageContainerEntity = Mockito
			.mock(ExampleLanguageContainerEntity.class);
		Mockito.when(exampleLanguageContainerEntity.getLanguageElementEntities())
			.thenReturn(new HashSet<>(Arrays.asList(exampleLanguageElementEntity)));

		DtoEntityConverterUtils.convertToLanguageElementDTOs(	exampleLanguageContainerEntity, exampleLanguageContainer,
																ExampleLanguageElement.class);

		Mockito.verify(exampleLanguageElementEntity)
			.getId();
		Mockito.verify(exampleLanguageElementEntity)
			.getLanguageId();

		Assert.assertEquals(exampleLanguageContainer.getLanguageElements()
			.size(), 1);
		ExampleLanguageElement exampleLanguageElement = exampleLanguageContainer.getLanguageElements()
			.get(0);

		Mockito.verify(exampleLanguageElementEntity)
			.updateDto(exampleLanguageElement);

		Assert.assertEquals(exampleLanguageElement.getId(), id);
		Assert.assertEquals(exampleLanguageElement.getLanguageId(), languageId);
	}

	@Test
	public void convertToLanguageContainerDTOTest() {
		ExampleLanguageContainerEntity exampleLanguageContainerEntity = Mockito
			.mock(ExampleLanguageContainerEntity.class);

		ExampleLanguageContainer exampleLanguageContainer = DtoEntityConverterUtils
			.convertToLanguageContainerDTO(	exampleLanguageContainerEntity, ExampleLanguageContainer.class,
											ExampleLanguageElement.class);

		Mockito.verify(exampleLanguageContainerEntity)
			.updateDto(exampleLanguageContainer);
	}

	@Test
	public void convertToLanguageContainerDTOWithFetchOptionsTest() {
		ExampleLanguageContainerEntity exampleLanguageContainerEntity = Mockito
			.mock(ExampleLanguageContainerEntity.class);

		ExampleFetchOptions exampleFetchOptions = new ExampleFetchOptions();

		ExampleLanguageContainer exampleLanguageContainer = DtoEntityConverterUtils
			.convertToLanguageContainerDTO(	exampleLanguageContainerEntity, exampleFetchOptions,
											ExampleLanguageContainer.class, ExampleLanguageElement.class);

		Mockito.verify(exampleLanguageContainerEntity, Mockito.never())
			.updateDto(exampleLanguageContainer);
		Mockito.verify(exampleLanguageContainerEntity)
			.updateDto(exampleLanguageContainer, exampleFetchOptions);
	}

}
