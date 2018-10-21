package com.gnopai.contactgen;

import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.address.*;
import com.gnopai.contactgen.generator.dob.DateOfBirthGenerator;
import com.gnopai.contactgen.generator.gender.GenderGenerator;
import com.gnopai.contactgen.generator.name.FirstNameGenerator;
import com.gnopai.contactgen.generator.name.LastNameGenerator;
import com.gnopai.contactgen.generator.name.MiddleInitialGenerator;
import com.gnopai.contactgen.generator.phone.AreaCodeGenerator;
import com.gnopai.contactgen.generator.phone.PhoneExchangeGenerator;
import com.gnopai.contactgen.generator.phone.PhoneLineGenerator;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.generator.random.StandardRandomGenerator;
import com.gnopai.contactgen.statistics.StatisticsLoader;
import com.gnopai.contactgen.statistics.address.CityStatisticsLoader;
import com.gnopai.contactgen.statistics.address.StateStatisticsLoader;
import com.gnopai.contactgen.statistics.address.StreetNamesStatisticsLoader;
import com.gnopai.contactgen.statistics.address.ZipCodeStatisticsLoader;
import com.gnopai.contactgen.statistics.dob.DateOfBirthStatisticsLoader;
import com.gnopai.contactgen.statistics.name.FirstNamesStatisticsLoader;
import com.gnopai.contactgen.statistics.name.LastNamesStatisticsLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.List;
import java.util.Properties;

import static com.google.inject.name.Names.bindProperties;

public class ContactGeneratorModule extends AbstractModule {
    private static final Properties DEFAULT_PROPERTIES;
    static {
        DEFAULT_PROPERTIES = new Properties();
        DEFAULT_PROPERTIES.setProperty("decimal.scale", "10");
    }

    private final Properties properties;

    public ContactGeneratorModule(Properties properties) {
        this.properties = new Properties(DEFAULT_PROPERTIES);
        this.properties.putAll(properties);
    }

    public ContactGeneratorModule() {
        this(new Properties());
    }

    @Override
    protected void configure() {
        bindProperties(binder(), properties);
        bind(RandomGenerator.class).to(StandardRandomGenerator.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public List<StatisticsLoader> provideStatisticsLoaders(FirstNamesStatisticsLoader firstNamesStatisticsLoader,
                                                           LastNamesStatisticsLoader lastNamesStatisticsLoader,
                                                           StreetNamesStatisticsLoader streetNamesStatisticsLoader,
                                                           CityStatisticsLoader cityStatisticsLoader,
                                                           StateStatisticsLoader stateStatisticsLoader,
                                                           ZipCodeStatisticsLoader zipCodeStatisticsLoader,
                                                           DateOfBirthStatisticsLoader dateOfBirthStatisticsLoader) {
        return List.of(firstNamesStatisticsLoader, lastNamesStatisticsLoader,
                streetNamesStatisticsLoader, cityStatisticsLoader, stateStatisticsLoader,
                zipCodeStatisticsLoader, dateOfBirthStatisticsLoader);
    }

    @Provides
    @Singleton
    public List<FieldGenerator> provideFieldGenerators(GenderGenerator genderGenerator,
                                                       DateOfBirthGenerator dateOfBirthGenerator,
                                                       FirstNameGenerator firstNameGenerator,
                                                       MiddleInitialGenerator middleInitialGenerator,
                                                       LastNameGenerator lastNameGenerator,
                                                       HouseNumberGenerator houseNumberGenerator,
                                                       PreDirectionalGenerator preDirectionalGenerator,
                                                       StreetNameGenerator streetNameGenerator,
                                                       StreetSuffixGenerator streetSuffixGenerator,
                                                       PostDirectionalGenerator postDirectionalGenerator,
                                                       UnitGenerator unitGenerator,
                                                       StateGenerator stateGenerator,
                                                       CityGenerator cityGenerator,
                                                       ZipCodeGenerator zipCodeGenerator,
                                                       AreaCodeGenerator areaCodeGenerator,
                                                       PhoneExchangeGenerator phoneExchangeGenerator,
                                                       PhoneLineGenerator phoneLineGenerator) {
        return List.of(genderGenerator, dateOfBirthGenerator,
                firstNameGenerator, middleInitialGenerator, lastNameGenerator,
                houseNumberGenerator, preDirectionalGenerator, streetNameGenerator,
                streetSuffixGenerator, postDirectionalGenerator, unitGenerator,
                stateGenerator, cityGenerator, zipCodeGenerator,
                areaCodeGenerator, phoneExchangeGenerator, phoneLineGenerator);
    }
}
