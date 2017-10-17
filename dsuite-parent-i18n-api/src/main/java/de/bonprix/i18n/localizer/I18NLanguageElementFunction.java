package de.bonprix.i18n.localizer;

import java.util.function.Function;

import de.bonprix.dto.I18NLanguageElement;

@FunctionalInterface
public interface I18NLanguageElementFunction<ELEMENT extends I18NLanguageElement> extends Function<ELEMENT, String> {

}