package io.swiftwallet.yggdrasil.core;

/**
 * Created by gibugeorge on 23/01/2017.
 */
public class RoutingEngineController {

    private final Thread daemon;

    public RoutingEngineController() {

        daemon = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
