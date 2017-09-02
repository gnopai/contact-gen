package com.gnopai.contactgen.generator.address;

import com.gnopai.contactgen.generator.ContactData;
import com.gnopai.contactgen.generator.random.RandomGenerator;
import com.gnopai.contactgen.model.State;
import com.gnopai.contactgen.statistics.ContactStatistics;
import com.gnopai.contactgen.statistics.util.WeightedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.gnopai.contactgen.model.State.OREGON;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StateGeneratorTest {
    @Mock RandomGenerator randomGenerator;
    @Mock WeightedList<State> states;

    @Test
    public void testGenerate() throws Exception {
        // GIVEN
        ContactStatistics contactStatistics = ContactStatistics.builder().states(states).build();
        when(randomGenerator.selectWeightDistributedListItem(states)).thenReturn(OREGON);

        StateGenerator testClass = new StateGenerator(randomGenerator);

        // WHEN
        ContactData result = testClass.generate(contactStatistics, ContactData.empty());

        // THEN
        assertEquals(OREGON, result.getState());
    }

}