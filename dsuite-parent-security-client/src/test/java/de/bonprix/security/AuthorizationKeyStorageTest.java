/**
 *
 */
package de.bonprix.security;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class AuthorizationKeyStorageTest {

    @Test
    public void simpleSetAndGetTest() {
        final String authKey = "123456789";
        final String nestedAuthKey = "1234567890";

        AuthorizationKeyStorage.setAuthorizationKey(authKey);
        AuthorizationKeyStorage.setRootAuthorizationKey(nestedAuthKey);

        assertThat(AuthorizationKeyStorage.getAuthorizationKey(), equalTo(authKey));
        assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), equalTo(nestedAuthKey));
    }

    @Test
    public void threadedSetAndGetTest() {
        final String authKey = "123456789";
        final String nestedAuthKey = "1234567890";
        final String altAuthKey = "qwertzui";
        final String altNestedAuthKey = "asdfghjk";

        final ThreadedAuthKeyTester tester1 = new ThreadedAuthKeyTester(authKey, nestedAuthKey);
        final ThreadedAuthKeyTester tester2 = new ThreadedAuthKeyTester(altAuthKey, altNestedAuthKey);

        // step 1
        tester1.start();
        tester2.start();

        // step 2
        synchNotifiy(tester1);
        synchNotifiy(tester2);

        // step 3
        synchNotifiy(tester1);
        synchNotifiy(tester2);

        // step 4
        synchNotifiy(tester1);
        synchNotifiy(tester2);
    }

    private void synchNotifiy(final Object o) {
        synchronized (o) {
            o.notify();
        }
    }

    private class ThreadedAuthKeyTester extends Thread {

        private final String authKey;
        private final String nestedAuthKey;

        public ThreadedAuthKeyTester(final String authKey, final String nestedAuthKey) {
            super();
            this.authKey = authKey;
            this.nestedAuthKey = nestedAuthKey;
        }

        @Override
        public void run() {
            // step 1 - simply set the variables
            AuthorizationKeyStorage.setAuthorizationKey(this.authKey);
            AuthorizationKeyStorage.setRootAuthorizationKey(this.nestedAuthKey);

            waitForInterrupt();

            // step 2 - test if the variables are correct
            assertThat(AuthorizationKeyStorage.getAuthorizationKey(), equalTo(this.authKey));
            assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), equalTo(this.nestedAuthKey));

            waitForInterrupt();

            // step 3 - delete the variables
            AuthorizationKeyStorage.setAuthorizationKey(null);
            AuthorizationKeyStorage.setRootAuthorizationKey(null);

            waitForInterrupt();

            // step 4 - test if the variables are correctly empty
            assertThat(AuthorizationKeyStorage.getAuthorizationKey(), nullValue());
            assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), nullValue());
        }

        private void waitForInterrupt() {
            synchronized (this) {
                try {
                    this.wait();
                }
                catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
