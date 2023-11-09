package fr.it_akademy_voiturejhipster.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Car getCarSample1() {
        return new Car().id(1L).carName("carName1").carModel("carModel1").carReference("carReference1").carYear(1).carPrice(1);
    }

    public static Car getCarSample2() {
        return new Car().id(2L).carName("carName2").carModel("carModel2").carReference("carReference2").carYear(2).carPrice(2);
    }

    public static Car getCarRandomSampleGenerator() {
        return new Car()
            .id(longCount.incrementAndGet())
            .carName(UUID.randomUUID().toString())
            .carModel(UUID.randomUUID().toString())
            .carReference(UUID.randomUUID().toString())
            .carYear(intCount.incrementAndGet())
            .carPrice(intCount.incrementAndGet());
    }
}
