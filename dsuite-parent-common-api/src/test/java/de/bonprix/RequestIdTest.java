/**
 *
 */
package de.bonprix;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author cthiel
 * @date 24.10.2016
 *
 */
public class RequestIdTest {

    @Test
    public void testGetRequestId() throws InterruptedException {
        final ThreadedRequestIdTester thread1 = new ThreadedRequestIdTester();
        final ThreadedRequestIdTester thread2 = new ThreadedRequestIdTester();

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertThat(thread1.requestId, not(equalTo(thread2.requestId)));
    }

    private class ThreadedRequestIdTester extends Thread {

        private String requestId;

        @Override
        public void run() {
            final String requestId1 = RequestId.getRequestId();
            final String requestId2 = RequestId.getRequestId();

            assertThat(requestId1, notNullValue());
            assertThat(requestId1, equalTo(requestId2));

            this.requestId = requestId1;
        }
    }
}
