package com.api.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String name;

	private String email;

	private String password;

	@Column(name = "phone_number")
	private String phonenumber;

	private String gender;

	@Column(name = "profile_image")
	private String profileImage;

	private String bio;

	private String country;

	@ManyToMany
	@JoinTable(name = "user_follows", joinColumns = @JoinColumn(name = "follower_Id"), inverseJoinColumns = @JoinColumn(name = "following_Id"))
	private Set<User> following = new HashSet<>();

	@ManyToMany(mappedBy = "following")
	private Set<User> followers = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Reel> reels = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	private Set<Story> stories = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Comment> comments = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "saved_Post")
	private Set<Post> savedPost = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "saved_Reel")
	private Set<Reel> savedReel = new HashSet<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public void follow(User user) {
		following.add(user);
		user.getFollowers().add(this);
	}

	public void unfollow(User user) {
		following.remove(user);
		user.getFollowers().remove(this);
	}

}
