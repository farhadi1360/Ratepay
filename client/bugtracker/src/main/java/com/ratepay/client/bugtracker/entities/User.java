package com.ratepay.client.bugtracker.entities;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ratepay.core.entity.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity
@NoArgsConstructor
@Getter
@Setter

public class User extends BaseEntity<Long> implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectManager")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "developers")
    private Set<Project> projectsWorkingOn = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer")
    private Set<Ticket> ticketsWorkingOn = new HashSet<>();

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole().name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addProjects(Project project) {
        projects.add(project);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Project> getProjectsWorkingOn() {
        return projectsWorkingOn;
    }

    public void setProjectsWorkingOn(Set<Project> projectsWorkingOn) {
        this.projectsWorkingOn = projectsWorkingOn;
    }

    public Set<Ticket> getTicketsWorkingOn() {
        return ticketsWorkingOn;
    }

    public void setTicketsWorkingOn(Set<Ticket> ticketsWorkingOn) {
        this.ticketsWorkingOn = ticketsWorkingOn;
    }

    public void addProjectsWorkingOn(Project project){
        this.projectsWorkingOn.add(project);
        project.addDeveloper(this);
    }

    public void removeProjectWorkingOn(Project project) {
        this.projectsWorkingOn.remove(project);
        project.removeDeveloper(this);
    }

    public void addTicketsWorkingOn(Ticket ticket) {
        this.ticketsWorkingOn.add(ticket);
    }

    public void removeTicketsWorkingOn(Ticket ticket) {
        this.ticketsWorkingOn.remove(ticket);
    }


    @Override
    public String getSelectTitle() {
        return username;
    }
}
