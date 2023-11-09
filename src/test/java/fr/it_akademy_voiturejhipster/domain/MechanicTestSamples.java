package fr.it_akademy_voiturejhipster.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MechanicTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Mechanic getMechanicSample1() {
        return new Mechanic().id(1L).motor("motor1").power(1).km(1);
    }

    public static Mechanic getMechanicSample2() {
        return new Mechanic().id(2L).motor("motor2").power(2).km(2);
    }

    public static Mechanic getMechanicRandomSampleGenerator() {
        return new Mechanic()
            .id(longCount.incrementAndGet())
            .motor(UUID.randomUUID().toString())
            .power(intCount.incrementAndGet())
            .km(intCount.incrementAndGet());
    }
}
