package fr.softeam.starpointsapp.service.dto;

import fr.softeam.starpointsapp.domain.Authority;
import fr.softeam.starpointsapp.domain.Community;
import fr.softeam.starpointsapp.domain.User;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO extends BasicUserDTO {

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private LocalDate entryDate;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;

    private Set<String> communities;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user, false);
    }

    public UserDTO(User user, boolean withCommunities) {
        this(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getActivated(), user.getLangKey(),
            user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()),
            user.getEntryDate());
        if (withCommunities) {
            setCommunities(user.getCommunities().stream().map(Community::getName).collect(Collectors.toSet()));
        }
    }

    public UserDTO(Long id, String login, String firstName, String lastName,
                   String email, boolean activated, String langKey,
                   Set<String> authorities, LocalDate entryDate) {

        super(id, login, firstName, lastName);
        this.email = email;
        this.entryDate = entryDate;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Set<String> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<String> communities) {
        this.communities = communities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", entryDate='" + entryDate + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            ", communities=" + communities +
            "}";
    }
}
