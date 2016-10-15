package filter;

import ninja.Results;
import ninja.Result;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Context;

import entity.User;

public class LoginFilter implements Filter {
	
	public Result filter(FilterChain chain, Context context) {
		String userId = context.getSession().get("userId");
		if (userId != null) {
			return Results.html().template("/views/ApplicationController/result.ftl.html").render("msg", "You are logged in!");
		}
		
		return chain.next(context);
	}
	
}
