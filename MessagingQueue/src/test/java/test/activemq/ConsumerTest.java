package test.activemq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.jms.JMSException;
import javax.naming.NamingException;

import activemq.Consumer;
import activemq.Producer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConsumerTest {

    private static Producer producerPointToPoint,
            producerOnlyOneConsumer, producerNoTimingDependencies,
            producerAcknowledgeProcessing;
    private static Consumer consumerPointToPoint,
            consumer1OnlyOneConsumer, consumer2OnlyOneConsumer,
            consumerNoTimingDependencies, consumer1AcknowledgeProcessing,
            consumer2AcknowledgeProcessing;

    @BeforeClass
    public static void setUpBeforeClass()
            throws JMSException, NamingException {
        producerPointToPoint = new Producer();
        producerPointToPoint.create("producer-pointtopoint",
                "pointtopoint.q");

        producerOnlyOneConsumer = new Producer();
        producerOnlyOneConsumer.create("producer-onlyoneconsumer",
                "onlyoneconsumer.q");

        producerNoTimingDependencies = new Producer();
        producerNoTimingDependencies.create(
                "producer-notimingdependencies", "notimingdependencies.q");

        producerAcknowledgeProcessing = new Producer();
        producerAcknowledgeProcessing.create(
                "producer-acknowledgeprocessing", "acknowledgeprocessing.q");

        consumerPointToPoint = new Consumer();
        consumerPointToPoint.create("consumer-pointtopoint",
                "pointtopoint.q");

        consumer1OnlyOneConsumer = new Consumer();
        consumer1OnlyOneConsumer.create("consumer1-onlyoneconsumer",
                "onlyoneconsumer.q");

        consumer2OnlyOneConsumer = new Consumer();
        consumer2OnlyOneConsumer.create("consumer2-onlyoneconsumer",
                "onlyoneconsumer.q");

        // consumerNoTimingDependencies

        consumer1AcknowledgeProcessing = new Consumer();
        consumer1AcknowledgeProcessing.create(
                "consumer1-acknowledgeprocessing", "acknowledgeprocessing.q");

        consumer2AcknowledgeProcessing = new Consumer();
        consumer2AcknowledgeProcessing.create(
                "consumer2-acknowledgeprocessing", "acknowledgeprocessing.q");
    }

    @AfterClass
    public static void tearDownAfterClass() throws JMSException {
        producerPointToPoint.closeConnection();
        producerOnlyOneConsumer.closeConnection();
        producerNoTimingDependencies.closeConnection();
        producerAcknowledgeProcessing.closeConnection();

        consumerPointToPoint.closeConnection();
        consumer1OnlyOneConsumer.closeConnection();
        consumer2OnlyOneConsumer.closeConnection();
        consumerNoTimingDependencies.closeConnection();
        // consumer1AcknowledgeProcessing
        consumer2AcknowledgeProcessing.closeConnection();
    }

    /**
     * The testGreeting() test case verifies the correct working of the getGreeting() method of the Consumer class.
     */
    @Test
    public void testGetGreeting() {
        try {
            producerPointToPoint.sendName("John", "Snow");

            String greeting = consumerPointToPoint.getGreeting(1000, true);
            assertEquals("Hello John Snow!", greeting);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }

    /**
     * The testOnlyOneConsumer() test case will verify that a message is read by only one consumer.
     * The first consumer will receive the greeting and the second consumer will receive nothing.
     *
     * @throws InterruptedException
     */
    @Test
    public void testOnlyOneConsumer() throws InterruptedException {
        try {
            producerOnlyOneConsumer.sendName("Ish", "Mishra");

            //The first consumer will receive the greeting
            String greeting1 =
                    consumer1OnlyOneConsumer.getGreeting(1000, true);
            assertEquals("Hello Ish Mishra!", greeting1);

            Thread.sleep(1000);

            //the second consumer will receive nothing.
            String greeting2 =
                    consumer2OnlyOneConsumer.getGreeting(1000, true);
            // each message has only one consumer
            assertEquals("no greeting", greeting2);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }

    }

    /**
     * The testNoTimingDependencies() test case illustrates that the consumer can successfully receive a message
     * even if that consumer is created after the message was sent.
     */
    @Test
    public void testNoTimingDependencies() {
        try {
            producerNoTimingDependencies.sendName("Black", "Panther");

            // a receiver can fetch the message whether or not it was running
            // when the client sent the message
            consumerNoTimingDependencies = new Consumer();
            consumerNoTimingDependencies.create(
                    "consumer-notimingdependencies", "notimingdependencies.q");

            String greeting =
                    consumerNoTimingDependencies.getGreeting(1000, true);
            assertEquals("Hello Black Panther!", greeting);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }

    /**
     * The testAcknowledgeProcessing() test case will verify that a message is not removed by the JMS provider
     * in case it was not acknowledged by the consumer.
     *
     * In order to simulate this, we first call the getGreeting() method
     * with the acknowledge parameter set to false. Then the getGreeting() method is called a second time and as the first
     * call did not acknowledge the message it is still available on the queue.
     *
     * @throws InterruptedException
     */
    @Test
    public void testAcknowledgeProcessing()
            throws InterruptedException {
        try {
            producerAcknowledgeProcessing.sendName("Sherlock", "Holmes");

            // consume the message without an acknowledgment
            String greeting1 =
                    consumer1AcknowledgeProcessing.getGreeting(1000, false);
            assertEquals("Hello Sherlock Holmes!", greeting1);

            // close the MessageConsumer so the broker knows there is no
            // acknowledgment
            consumer1AcknowledgeProcessing.closeConnection();

            String greeting2 =
                    consumer2AcknowledgeProcessing.getGreeting(1000, true);
            assertEquals("Hello Sherlock Holmes!", greeting2);

        } catch (JMSException e) {
            fail("a JMS Exception occurred");
        }
    }
}