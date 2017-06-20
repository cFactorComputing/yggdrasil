package io.swiftwallet.yggdrasil.main;

import in.cfcomputing.odin.core.bootstrap.config.annotations.OdinConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Created by gibugeorge on 22/01/2017.
 */
@OdinConfiguration
public class YggdrasilMain {

    public static void main(String[] args) {
        final SpringApplication yggdrasil = new SpringApplication(YggdrasilMain.class);
        yggdrasil.run(args);
    }
}
