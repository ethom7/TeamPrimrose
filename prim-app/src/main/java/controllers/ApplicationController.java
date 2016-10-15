/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import ninja.Result;
import ninja.Results;
import ninja.FilterWith;
import ninja.session.Session;

import com.google.inject.Singleton;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.persistence.EntityManager;
import java.util.List;

import filter.LoginFilter;
import entity.User;


@Singleton
public class ApplicationController {

	@Inject
	Provider<EntityManager> entityManagerProvider;

	@FilterWith(LoginFilter.class)
    public Result login() {
        return Results.html();

    }

    public Result performLogin(User user, Session session) {
		String userId = user.getUserId();
		String pwd = user.getPassword();
		if (userExists(userId, pwd)) {
			session.put("userId", userId);
			return Results.html().template("/views/ApplicationController/result.ftl.html").render("msg", "Welcome " + user.getUserId());
		} else {
			session.clear();
		}
		return Results.html().template("/views/ApplicationController/result.ftl.html").render("msg", "Invalid user, please login again ");
    }
    
	private boolean userExists(String userId, String password) {
		EntityManager entityManager = entityManagerProvider.get();
		List<?> users = entityManager.createQuery("from User where userId = ?1 and password = ?2").setParameter(1, userId).setParameter(2, password).getResultList();
		return (users.size() > 0) ? true : false;
	}
}

