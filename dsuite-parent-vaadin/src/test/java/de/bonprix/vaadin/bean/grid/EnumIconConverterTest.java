package de.bonprix.vaadin.bean.grid;

import java.util.Locale;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.vaadin.server.Resource;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.vaadin.bean.util.EnumIconProvider;

public class EnumIconConverterTest extends BaseConfiguredUnitTest {

	private enum TestEnum {
		ENUM_ONE;
	}

	@Mock
	private EnumIconProvider<TestEnum> enumIconProvider;

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testNoDefaultConstructor() {
		new EnumIconConverter<>(EnumIconProviderWithNoDefaultConstructor.class);
	}

	@Test
	public void testEnumIconProvider() {
		EnumIconConverter<TestEnum> converter = new EnumIconConverter<>(this.enumIconProvider);
		converter.convertToPresentation(TestEnum.ENUM_ONE, Resource.class, Locale.GERMAN);
		Mockito	.verify(this.enumIconProvider)
				.getIcon(Mockito.any());
	}

	private class EnumIconProviderWithNoDefaultConstructor implements EnumIconProvider<TestEnum> {

		@SuppressWarnings("unused")
		public EnumIconProviderWithNoDefaultConstructor(Object noDefaultConstructor) {

		}

		@Override
		public Resource getIcon(TestEnum theEnum) {
			return null;
		}

		@Override
		public Class<TestEnum> getModelType() {
			return null;
		}

	}

}
