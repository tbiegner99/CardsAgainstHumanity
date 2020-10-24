package com.tj.cardsagainsthumanity.serializer.converter.packageImport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseImportRequestNormalizerTest {

    @Mock
    private DatabaseImportRequest databaseImportRequest;

    private DatabaseImportRequestNormalizer normalizer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        normalizer = new DatabaseImportRequestNormalizer();
        databaseImportRequest = setUpRequest();
    }

    private DatabaseImportRequest setUpRequest() throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   
        File testDataFile = new File(this.getClass().getClassLoader().getResource("testDatabaseImport.json").getFile());
        return mapper.readValue(testDataFile, DatabaseImportRequest.class);
    }

    @Test
    public void normalize() {
        Collection<NormalizedPackageImport> result = normalizer.normalize(databaseImportRequest);
        NormalizedPackageImport expectedResult = new NormalizedPackageImport();
        assertEquals(result.size(), 2);
        Iterator<NormalizedPackageImport> it = result.iterator();
        chekFirstPackage(it.next());
        checkSecondPackage(it.next());
    }

    private void chekFirstPackage(NormalizedPackageImport next) {
        CreatePackageRequest info = new CreatePackageRequest("Test Package 1", null, CardPackage.IconType.FONTAWESOME);
        assertEquals(info, next.getPackageInfo());

        assertContainsAllWhite(next, "card0", "card1");
        assertContainsAllBlack(next, "blackCard0", "blackCard1");
    }

    private void checkSecondPackage(NormalizedPackageImport next) {
        CreatePackageRequest info = new CreatePackageRequest("Test Package 2", "paw", CardPackage.IconType.FONTAWESOME);
        assertEquals(info, next.getPackageInfo());

        assertContainsAllWhite(next, "card2", "card3", "card4");
        assertContainsAllBlack(next, "blackCard2", "blackCard3", "blackCard4");
    }

    private void assertContainsAllBlack(NormalizedPackageImport next, String... cards) {
        assertContainsAllCards(next.getBlackCards(), cards);
    }

    private void assertContainsAllWhite(NormalizedPackageImport next, String... cards) {
        assertContainsAllCards(next.getWhiteCards(), cards);
    }

    private void assertContainsAllCards(Collection<CreateCardRequest> cardList, String... cardsToCheck) {
        Set<String> cardTexts = cardList
                .stream()
                .map(card -> card.getCardText())
                .collect(Collectors.toSet());
        assertEquals(cardTexts.size(), cardsToCheck.length);

        for (String card : cardsToCheck) {
            assertTrue("expected cards to contain: " + card, cardTexts.contains(card));
        }
    }

}