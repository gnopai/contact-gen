package com.gnopai.contactgen;

import com.google.inject.Injector;
import com.gnopai.contactgen.generator.ContactGeneratorService;

import static com.google.inject.Guice.createInjector;


public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = createInjector(new ContactGeneratorModule());
        ContactGeneratorService contactGeneratorService = injector.getInstance(ContactGeneratorService.class);
        contactGeneratorService.generateContacts(100)
                .forEach(System.out::println);
    }
}
