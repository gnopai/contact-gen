package com.gnopai.contactgen.generator.phone;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.FieldGenerator;
import com.gnopai.contactgen.generator.random.Odds;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.phone.AreaCodes;
import com.gnopai.contactgen.statistics.util.WeightedList;
import com.google.inject.Inject;

import java.util.List;

public class AreaCodeGenerator implements FieldGenerator {
    static final Odds ODDS_OF_LOCAL_AREA_CODE = Odds.of(2).in(3);
    static final Odds ODDS_OF_STATE_VS_REMOTE_AREA_CODE = Odds.of(1).in(2);

    private final RandomGenerator randomGenerator;

    @Inject
    public AreaCodeGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public ContactData generate(ContactStatistics contactStatistics, ContactData contactData) {
        String areaCode = generateAreaCode(contactStatistics, contactData);
        return contactData.withPhoneAreaCode(areaCode);
    }

    private String generateAreaCode(ContactStatistics contactStatistics, ContactData contactData) {
        boolean useLocalAreaCode = randomGenerator.selectChance(ODDS_OF_LOCAL_AREA_CODE);
        if (useLocalAreaCode) {
            return generateLocalAreaCode(contactStatistics, contactData.getZipCode());
        }

        boolean useSemiLocalAreaCode = randomGenerator.selectChance(ODDS_OF_STATE_VS_REMOTE_AREA_CODE);
        if (useSemiLocalAreaCode) {
            return generateSemiLocalAreaCode(contactStatistics, contactData.getState());
        }

        return generateRemoteAreaCode(contactStatistics);
    }

    private String generateLocalAreaCode(ContactStatistics contactStatistics, String zipCode) {
        AreaCodes areaCodes = contactStatistics.getAreaCodes();
        List<String> areaCodesForZipCode = areaCodes.getAreaCodesForZipCode(zipCode);
        return randomGenerator.selectUniformlyDistributedListItem(areaCodesForZipCode);
    }

    private String generateSemiLocalAreaCode(ContactStatistics contactStatistics, State state) {
        AreaCodes areaCodes = contactStatistics.getAreaCodes();
        WeightedList<String> areaCodesForState = areaCodes.getAreaCodesForState(state);
        return randomGenerator.selectWeightDistributedListItem(areaCodesForState);
    }

    private String generateRemoteAreaCode(ContactStatistics contactStatistics) {
        State stateOfSomePreviousResidence = randomGenerator.selectWeightDistributedListItem(contactStatistics.getStates());
        return generateSemiLocalAreaCode(contactStatistics, stateOfSomePreviousResidence);
    }

    @Override
    public boolean requiresMoreData(ContactData contactData) {
        return !contactData.hasState() || !contactData.hasZipCode();
    }
}
