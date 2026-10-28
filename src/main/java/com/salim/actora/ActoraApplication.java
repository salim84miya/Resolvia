package com.salim.actora;

import com.salim.actora.auth.entity.Role;
import com.salim.actora.auth.repository.RoleRepository;
import com.salim.actora.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActoraApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ActoraApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {


        repository.findByName("ROLE_"+ AppConstants.ADMIN_ROLE).ifPresentOrElse(role->{
                    System.out.println("Role "+AppConstants.ADMIN_ROLE+" already exists");
                }
                ,()->{
                    Role role = Role.builder()
                            .name("ROLE_"+AppConstants.ADMIN_ROLE)
                            .build();

                    repository.save(role);
                });

        repository.findByName("ROLE_"+AppConstants.USER_ROLE).ifPresentOrElse(role->{
            System.out.println("Role "+AppConstants.USER_ROLE+" already exists");
        },()->{
            Role role = Role.builder()
                    .name("ROLE_"+AppConstants.USER_ROLE)
                    .build();

            repository.save(role);
        });
    }
}
