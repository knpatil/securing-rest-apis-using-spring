package io.kpatil.springsecurity.resolutions;

import javax.persistence.Id;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;

public class ReflectedUser {
	static Constructor defaultConstructor;
	static Constructor copyConstructor;
	static Field idColumnField;
	static Field usernameColumnField;
	static Field passwordColumnField;
	static Field enabledColumnField;
	static Field nameColumnField;
	static Field subscriptionColumnField;
	static Field userAuthorityCollectionField;
	static Field userFriendCollectionField;
	static Method grantAuthorityMethod;

	static {
		defaultConstructor = ReflectionSupport.getConstructor(User.class);
		if (defaultConstructor != null) defaultConstructor.setAccessible(true);
		copyConstructor = ReflectionSupport.getConstructor(User.class, User.class);
		idColumnField = ReflectionSupport.getDeclaredFieldHavingAnnotation(User.class, Id.class);
		usernameColumnField = ReflectionSupport.getDeclaredFieldByColumnName(User.class, "username");
		if (usernameColumnField != null) usernameColumnField.setAccessible(true);
		passwordColumnField = ReflectionSupport.getDeclaredFieldByColumnName(User.class, "password");
		if (passwordColumnField != null) passwordColumnField.setAccessible(true);
		enabledColumnField = ReflectionSupport.getDeclaredFieldByColumnName(User.class, "enabled");
		if (enabledColumnField != null) enabledColumnField.setAccessible(true);
		nameColumnField = ReflectionSupport.getDeclaredFieldByColumnName(User.class, "full_name");
		if (nameColumnField != null) nameColumnField.setAccessible(true);
		subscriptionColumnField = ReflectionSupport.getDeclaredFieldByColumnName(User.class, "subscription");
		if (subscriptionColumnField != null) subscriptionColumnField.setAccessible(true);
		userAuthorityCollectionField = ReflectionSupport.getDeclaredFieldByName(User.class, "userAuthorities");
		if (userAuthorityCollectionField != null) userAuthorityCollectionField.setAccessible(true);
		userFriendCollectionField = ReflectionSupport.getDeclaredFieldByName(User.class, "friends");
		try {
			grantAuthorityMethod = User.class.getDeclaredMethod("grantAuthority", String.class);
		} catch (Exception ignored) {
			// user hasn't added this method yet
		}
	}

	User user;

	public static ReflectedUser newInstance() {
		try {
			return new ReflectedUser((User) defaultConstructor.newInstance());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ReflectedUser copiedInstance(ReflectedUser user) {
		try {
			return new ReflectedUser((User) copyConstructor.newInstance(user.user));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ReflectedUser(User user) {
		this.user = user;
	}

	UUID getId() {
		return ReflectionSupport.getProperty(this.user, idColumnField);
	}

	String getUsername() {
		return ReflectionSupport.getProperty(this.user, usernameColumnField);
	}

	String getPassword() {
		return ReflectionSupport.getProperty(this.user, passwordColumnField);
	}

	String getFullName() { return ReflectionSupport.getProperty(this.user, nameColumnField); }

	String getSubscription() { return ReflectionSupport.getProperty(this.user, subscriptionColumnField); }

	Collection<UserAuthority> getUserAuthorities() {
		return ReflectionSupport.getProperty(this.user, userAuthorityCollectionField);
	}

	Collection<User> getFriends() { return ReflectionSupport.getProperty(this.user, userFriendCollectionField); }

	void grantAuthority(String authority) {
		try {
			grantAuthorityMethod.invoke(this.user, authority);
		} catch (Exception e) {
			throw new RuntimeException("Failed to call `grantAuthority` on " + this.user, e);
		}
	}
}
