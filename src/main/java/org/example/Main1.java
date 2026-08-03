package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Test {

    private String name;
}

public class Main1 {

    public static void main(String[] args) {

        Test t = new Test();

        t.setName("Amit");

        System.out.println(t.getName());
    }
}