package ru.otus;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

public class HelloOtus {
    public static void main(String... args) {
        try {
            splitGuava();
            joinerGuava();
            charMatcherGuava();
            System.out.println("\ncom.google.common.base: \n-Split,\n-Joiner,\n-CharMatcher");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Splitter
    static void splitGuava() {
        String input = "Fruit::Apple ,,Dessert::Waffle, Dairy Product::Cheese,,";
        Map<String, String> mapOfClassAndItem = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .withKeyValueSeparator("::")
                .split(input);
        assertThat(mapOfClassAndItem.get("Fruit")).isEqualTo("Apple");
        assertThat(mapOfClassAndItem.get("Dessert")).isEqualTo("Waffle");
        assertThat(mapOfClassAndItem.get("Dairy Product")).isEqualTo("Cheese");

    }

    //Joiner
    static void joinerGuava() {
        ImmutableMap<Object, Object> food = ImmutableMap.builder()
                .put("Fruit", "Apple")
                .put("Dessert", "Waffle")
                .put("Dairy Product", "Cheese")
                .build();
        String result = Joiner.on(",")
                .withKeyValueSeparator("::")
                .join(food);
        assertThat(result).isEqualTo("Fruit::Apple,Dessert::Waffle,Dairy Product::Cheese");
    }

    //CharMatcher
    static void charMatcherGuava() {
        String input = " hello world ";
        String output = CharMatcher.is(' ').trimAndCollapseFrom(input, '-');
        assertThat(output).isEqualTo("hello-world");
    }
}
