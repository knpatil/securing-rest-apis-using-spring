package io.kpatil.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
    private final ResolutionRepository resolutions;
    private final UserRepository users;

    public ResolutionInitializer(ResolutionRepository resolutions, UserRepository users) {
        this.resolutions = resolutions;
        this.users = users;
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("Creating initial resolutions ...");

        this.resolutions.save(new Resolution("Read War and Peace", "user"));
        this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
        this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

        System.out.println("Initializing user 'knpatil' ...");

        User user = new User("knpatil",
                "{bcrypt}$2a$10$jImZs8GVoSkZeJ2Wmh7UY" +
                        ".6OrKNze//U3QwEIek4meWLgbPzKde36");
        this.users.save(user);
    }
}
