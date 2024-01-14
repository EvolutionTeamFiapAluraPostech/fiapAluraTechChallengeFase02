package br.com.digitalparking.user.model.entity;

import br.com.digitalparking.shared.model.entity.BaseEntity;
import br.com.digitalparking.vehicle.model.entity.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "user_management", name = "users")
public class User extends BaseEntity implements UserDetails {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String cpf;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(schema = "user_management", name = "users_authorities",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private List<Authority> authorities = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(schema = "user_management", name = "user_vehicles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "vehicle_id"))
  private List<Vehicle> vehicles = new ArrayList<>();

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  public boolean userHas(Vehicle vehicle) {
    if (this.getVehicles() == null || this.getVehicles().isEmpty()) {
      return false;
    }
    return this.getVehicles().stream()
        .anyMatch(userVehicle -> userVehicle.getLicensePlate().equals(vehicle.getLicensePlate()));
  }

  public void add(Vehicle vehicle) {
    if (this.getVehicles() == null) {
      this.setVehicles(new ArrayList<>());
    }
    this.vehicles.add(vehicle);
  }
}
