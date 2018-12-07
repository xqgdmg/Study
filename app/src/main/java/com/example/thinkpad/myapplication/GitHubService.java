package com.example.thinkpad.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Single;

/**
 * Created by Chris on 2018/11/28.
 */
public interface GitHubService {
    @GET("users/{user}/repos")
    Single<List<Repo>> listRepos(@Path("user") String user);
}
