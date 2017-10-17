package de.bonprix.modules.theme.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;

public class TestIcon {

	static List<FontAwesome> icons = Collections.unmodifiableList(Arrays.asList(FontAwesome.values()));

	int iconCount = 0;

	public TestIcon(int startIndex) {
		this.iconCount = startIndex;
	}

	public Resource get() {
		return get(false, 32);
	}

	public Resource get(boolean isImage) {
		return get(isImage, 32);
	}

	public Resource get(boolean isImage, int imageSize) {
		if (!isImage) {
			if (++this.iconCount >= TestIcon.icons.size()) {
				this.iconCount = 0;
			}
			return TestIcon.icons.get(this.iconCount);
		}
		return new ThemeResource("../runo/icons/" + imageSize + "/document.png");
	}
}
