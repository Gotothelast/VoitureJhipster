package fr.it_akademy_voiturejhipster.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgenceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agence getAgenceSample1() {
        return new Agence().id(1L).nameAgence("nameAgence1").adressAgence("adressAgence1").telAgence(1L);
    }

    public static Agence getAgenceSample2() {
        return new Agence().id(2L).nameAgence("nameAgence2").adressAgence("adressAgence2").telAgence(2L);
    }

    public static Agence getAgenceRandomSampleGenerator() {
        return new Agence()
            .id(longCount.incrementAndGet())
            .nameAgence(UUID.randomUUID().toString())
            .adressAgence(UUID.randomUUID().toString())
            .telAgence(longCount.incrementAndGet());
    }
}
