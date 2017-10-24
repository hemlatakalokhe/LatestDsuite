package de.bonprix.test.serviceImpl;

import java.util.Optional;

import org.testng.annotations.Test;

public class TestOptionalClass {

    @SuppressWarnings("static-access")
    @Test
    public void testFeatures() {

        final String[] array = new String[10];
        array[2] = "abc";
        final Optional<String> optional = Optional.of(array[2]);
        final Optional<String> opt = Optional.ofNullable(array[4]);

        System.out.println("isPresent(): " + optional.isPresent());
        System.out.println("empty(): " + optional.empty());
        System.out.println("orElse(): " + opt.orElse("xyz"));
        System.out.println("ofNullable().isPrsent(): " + opt.isPresent());
        optional.ifPresent(System.out::println);
        System.out.println("ofNullable().orElse(): " + opt.orElse(optional.get()));
        System.out.println("filter(): " + opt.filter((s) -> s.equals("abc")));

        final Optional<Optional<String>> stringOptional = Optional.of(Optional.of(array[2]));
        System.out.println("map(): " + optional.map(String::toUpperCase));
        System.out.println("flatMap(): " + stringOptional.flatMap(s -> s.map(String::toUpperCase)));
        System.out.println(optional.get());
    }

}
