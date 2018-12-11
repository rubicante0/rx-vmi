package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.RepositoryException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class PureChaos {
    private final Random random = new Random();
    private final double failure;
    private final double timeout;

    PureChaos(int failureProbability, int timeoutProbability) {
        this.failure = failureProbability / 100d;
        this.timeout = timeoutProbability / 100d;
    }

    void maybeChaos() throws RepositoryException {
        double prob = random.nextDouble();
        if (prob <= failure) {
            throw new RepositoryException("some random error, prob value: " + prob);
        }
        prob = random.nextDouble();
        if (prob <= timeout) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(random.nextInt(5)));
            } catch (InterruptedException e) {
            }
        }
    }
}
