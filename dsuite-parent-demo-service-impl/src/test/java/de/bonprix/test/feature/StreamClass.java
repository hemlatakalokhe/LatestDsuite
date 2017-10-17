package de.bonprix.test.feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StreamClass {
    private List<Product> productList;

    private static int i = 1;

    @BeforeMethod
    public void init() {
        this.productList = new ArrayList<>();
        this.productList.add(new Product(1, "Aiden", 12000.0f));
        this.productList.add(new Product(2, "Emma", 20000.0f));
        this.productList.add(new Product(3, "Jackob", 36000.0f));
        this.productList.add(new Product(4, "Amber", 10000.0f));
        this.productList.add(new Product(5, "Jayden", 41000.0f));
        this.productList.add(new Product(6, "Kylie", 20000.0f));
    }

    @Test
    public void test() {
        System.out.println((StreamClass.i++) + ".First Test");
        final List<Float> productPriceList = new ArrayList<>();
        for (final Product product : this.productList) {
            if (product.getPrice() < 30000.0f) {
                productPriceList.add(product.getPrice());
            }
        }
        System.out.println(productPriceList);
        System.out.println();
    }

    @Test
    public void testAveragePrice() {
        System.out.println((StreamClass.i++) + ".Average Price Test");
        final double avgPrice = this.productList.stream()
            .filter(product -> product.getPrice() < 30000.0f)
            .collect(Collectors.averagingDouble(product -> product.getPrice()));
        System.out.println(avgPrice);
        System.out.println();
    }

    @Test
    public void testCount() {
        System.out.println((StreamClass.i++) + ".Count Test");
        final long count = this.productList.stream()
            .filter(product -> product.getPrice() > 30000.0f)
            .count();
        System.out.println("Count is: " + count);
        System.out.println();
    }

    @Test
    public void testIterate() {
        System.out.println((StreamClass.i++) + ".Iteration Test");
        if (this.productList.iterator()
            .hasNext()) {
            this.productList.stream()
                .filter(product -> product.getName()
                    .length() > 4)
                .map(product -> product.getName())
                .forEach(System.out::println);
        }
        System.out.println();
    }

    @Test
    public void testListToMap() {
        System.out.println((StreamClass.i++) + ".List to Map Conversion Test");
        final Map<Object, Object> nameMap = this.productList.stream()
            .collect(Collectors.toMap(p -> p.getId(), p -> p.getName()));
        System.out.println("Name Map: " + nameMap);
        final Map<Object, Object> priceMap = this.productList.stream()
            .collect(Collectors.toMap(p -> p.getId(), p -> p.getPrice()));
        System.out.println("Price Map: " + priceMap);
        System.out.println();
    }

    @Test
    public void testListToSet() {
        System.out.println((StreamClass.i++) + ".List to Set Conversion Test");
        final Set<Float> priceSet = this.productList.stream()
            .filter(product -> product.getPrice() < 30000.0f)
            .map(product -> product.getPrice())
            .collect(Collectors.toSet());
        System.out.println("Price Set: " + priceSet);
        final Set<String> nameSet = this.productList.stream()
            .filter(product -> product.getPrice() < 30000.0f)
            .map(product -> product.getName())
            .collect(Collectors.toSet());
        System.out.println("Name Set: " + nameSet);
        System.out.println();
    }

    @Test
    public void testMethodInterface() {
        System.out.println((StreamClass.i++) + ".Method Iterface Test");
        final BiFunction<Integer, Integer, Integer> adder = new Arithmetic()::add;
        final int result = adder.apply(10, 20);
        System.out.println(result);
        System.out.println();
    }

    @Test
    public void testMinMax() {
        System.out.println((StreamClass.i++) + ".Min Max Test");
        final Product productA = this.productList.stream()
            .max((product1, product2) -> product1.getPrice() > product2.getPrice() ? 1 : -1)
            .get();
        System.out.println("Max Price: " + productA.getPrice());

        final Product productB = this.productList.stream()
            .min((product1, product2) -> product1.getPrice() > product2.getPrice() ? 1 : -1)
            .get();
        System.out.println("Min Price: " + productB.getPrice());
        System.out.println();
    }

    @Test
    public void testOptional() {
        System.out.println((StreamClass.i++) + ".optional Test");
        final List<String> members = Arrays.asList("Aiden", "Jackob", "Emma", "Martin");
        final Optional<String> optional = members.stream()
            .reduce((s1, s2) -> s1 + "#" + s2);
        System.out.println(optional);
        System.out.println();
    }

    @Test
    public void testParallelSorting() {
        System.out.println((StreamClass.i++) + ".Parallel Sorting Test");
        final int[] array = { 5, 2, 8, 4, 9, 12, 6, 47 };
        System.out.println("Before Sorting");
        for (final int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("After Sorting (Parallel Sort)");
        Arrays.parallelSort(array, 0, 4);
        for (final int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("After Sorting (Normal Sort)");
        Arrays.sort(array);
        ;
        for (final int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @Test
    public void testPredicate() {
        System.out.println();
        System.out.println((StreamClass.i++) + ".Predicate Test");
        final Predicate<String> predicate = Pattern.compile("^(.+)@(.+).com")
            .asPredicate();
        final List<String> emails = Arrays.asList("abc@abc.com", "xyz@example.com", "abc.com");

        final List<String> desiredEmails = emails.stream()
            .filter(predicate)
            .collect(Collectors.toList());

        System.out.println(desiredEmails);
        System.out.println();
    }

    @Test
    public void testReduce() {
        System.out.println((StreamClass.i++) + ".Reduce Test");
        final Float totalPrice = this.productList.stream()
            .map(product -> product.getPrice())
            .reduce(0.0f, (sum, price) -> sum + price);
        System.out.println("Total Price:" + totalPrice);

        final Float priceTotal = this.productList.stream()
            .map(product -> product.getPrice())
            .reduce(0.0f, Float::sum);
        System.out.println("Price Total:" + priceTotal);
        System.out.println();
    }

    @Test
    public void testReferencing() {
        System.out.println((StreamClass.i++) + ".Referencing Test");
        final List<Float> priceList = this.productList.stream()
            .filter(product -> product.getPrice() < 30000.0f)
            .map(Product::getPrice)
            .collect(Collectors.toList());
        System.out.println(priceList);
        System.out.println();
    }

    @Test
    public void testStringJoiner() {
        System.out.println((StreamClass.i++) + ".String Joiner Test");
        final StringJoiner joiner = new StringJoiner("_");
        joiner.add("Style");
        joiner.add("Edit");
        System.out.println(joiner);

        final StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        stringJoiner.add("ABC");
        stringJoiner.add("XYZ");
        stringJoiner.add("LMN");
        System.out.println(joiner.merge(stringJoiner));
        System.out.println();
    }

    @Test
    public void testSumByCollectorsMethod() {
        System.out.println((StreamClass.i++) + ".Collectors Method Test");
        final double price = this.productList.stream()
            .collect(Collectors.summingDouble(product -> product.getPrice()));
        System.out.println(price);
        System.out.println();
    }

    class Arithmetic {
        public int add(final int a, final int b) {
            return a + b;
        }
    }

    @Test
    public void testJavaFeature() {
        final Set<String> set = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("A", "B")));
        set.stream()
            .forEach(System.out::println);
        @SuppressWarnings("serial")
        final Set<String> set2 = Collections.unmodifiableSet(new HashSet<String>() {
            {
                add("A");
                add("B");
                add("C");
            }
        });
        System.out.println("Second set's data");
        set2.stream()
            .forEach(System.out::println);
    }

}
