package io.kpatil.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
        user.grantAuthority("resolution:read");
        user.grantAuthority("resolution:write");
        this.users.save(user);

        User readonly = new User();
        readonly.setId(UUID.randomUUID());
        readonly.setUsername("readonly");
        readonly.setPassword("{bcrypt}$2b$10$4O6iq2P44JRjHVfEcOIqz.08zNddVZr5TgrNZTNKELVC5PL3LvFhi");
        readonly.grantAuthority("resolution:read");
        this.users.save(readonly);

        User writeonly = new User();
        writeonly.setId(UUID.randomUUID());
        writeonly.setUsername("writeonly");
        writeonly.setPassword("{bcrypt}$2b$10$iJLQFbZMMsmRzOxABfN/AuL/zXEHjDlDsB4yW7gkQCGW22sw9tVN2");
        writeonly.grantAuthority("resolution:write");
        this.users.save(writeonly);
    }
}
